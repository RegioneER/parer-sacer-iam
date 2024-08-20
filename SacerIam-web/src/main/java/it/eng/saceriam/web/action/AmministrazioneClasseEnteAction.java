/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.web.action;

import javax.ejb.EJB;

import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneClasseEnteAbstractAction;
import it.eng.saceriam.slite.gen.tablebean.OrgClasseEnteConvenzTableBean;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Gilioli_P
 */
public class AmministrazioneClasseEnteAction extends AmministrazioneClasseEnteAbstractAction {

    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiEjb")
    private EntiConvenzionatiEjb entiConvenzionatiEjb;

    @Override
    public void initOnClick() throws EMFError {
    }

    @Override
    public void loadDettaglio() throws EMFError {
    }

    @Override
    public void undoDettaglio() throws EMFError {
    }

    @Override
    public void insertDettaglio() throws EMFError {
    }

    @Override
    public void saveDettaglio() throws EMFError {
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
    }

    @Override
    public void elencoOnClick() throws EMFError {
        goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.RICERCA_CLASSE_ENTE_CONVENZIONATO;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
    }

    @Override
    public String getControllerName() {
        return Application.Actions.AMMINISTRAZIONE_CLASSE_ENTE;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione Classe ente convenzionato">
    @Secure(action = "Menu.AmministrazioneSistema.GestioneClasseEnteConvenzionato")
    public void ricercaClasseEnteConvenzionatoPage() {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneClasseEnteConvenzionato");

        getForm().getFiltriClassiEnti().reset();
        getForm().getFiltriClassiEnti().setEditMode();

        // Azzero la lista risultato
        getForm().getListaClassiEnti().setTable(null);

        forwardToPublisher(Application.Publisher.RICERCA_CLASSE_ENTE_CONVENZIONATO);
    }

    @Override
    public void ricercaClassiEnti() throws EMFError {
        if (getForm().getFiltriClassiEnti().postAndValidate(getRequest(), getMessageBox())) {
            String cdClasseEnteConvenz = getForm().getFiltriClassiEnti().getCd_classe_ente_convenz().parse();
            String dsClasseEnteConvenz = getForm().getFiltriClassiEnti().getDs_classe_ente_convenz().parse();
            try {
                OrgClasseEnteConvenzTableBean table = entiConvenzionatiEjb
                        .getOrgClasseEnteConvenzTableBean(cdClasseEnteConvenz, dsClasseEnteConvenz);
                getForm().getListaClassiEnti().setTable(table);
                getForm().getListaClassiEnti().getTable().setPageSize(10);
                getForm().getListaClassiEnti().getTable().first();
            } catch (ParerUserError pue) {
                getMessageBox().addError(pue.getDescription());
            }
        }
        forwardToPublisher(getLastPublisher());
    }
    // </editor-fold>

}
