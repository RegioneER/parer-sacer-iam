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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.GestioneAccessiFallitiAbstractAction;
import it.eng.saceriam.slite.gen.viewbean.LogVRicAccessiTableBean;
import it.eng.saceriam.web.ejb.AmministrazioneUtentiEjb;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Gilioli_P
 */
public class GestioneAccessiFallitiAction extends GestioneAccessiFallitiAbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(GestioneAccessiFallitiAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiEjb")
    private AmministrazioneUtentiEjb amministrazioneUtentiEjb;

    @Secure(action = "Menu.Logging.GestioneAccessiFalliti")
    public void ricercaAccessiFallitiPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.Logging.GestioneAccessiFalliti");
        // Pulisco
        getForm().getFiltriAccessiFalliti().reset();
        getForm().getFiltriAccessiFalliti().setEditMode();
        getForm().getFiltriAccessiFalliti().getRicercaAccessiFalliti().setEditMode();
        initFiltriAccessiFalliti();
        getForm().getAccessiFallitiList().clear();
        getForm().getAccessiFallitiList().setStatus(Status.view);
        forwardToPublisher(Application.Publisher.RICERCA_ACCESSI_FALLITI);
    }

    /**
     * Ricerca accessi falliti in base ai filtri passati in ingresso
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void ricercaAccessiFalliti() throws EMFError {
        getForm().getFiltriAccessiFalliti().post(getRequest());
        Date dtEventoDa = getForm().getFiltriAccessiFalliti().getDt_evento_da().parse();
        Date dtEventoA = getForm().getFiltriAccessiFalliti().getDt_evento_a().parse();
        Date[] dateDecorate = decorateDate(dtEventoDa, dtEventoA);
        String nmUserid = getForm().getFiltriAccessiFalliti().getNm_userid().parse();
        String tipoEvento = getForm().getFiltriAccessiFalliti().getTipo_evento().parse();
        String inclUtentiAutomi = getForm().getFiltriAccessiFalliti().getIncl_utenti_automi().parse();
        String inclLoginOK = getForm().getFiltriAccessiFalliti().getIncl_login_OK().parse();
        String nome = getForm().getFiltriAccessiFalliti().getNm_nome_user().parse();
        String cognome = getForm().getFiltriAccessiFalliti().getNm_cognome_user().parse();
        String cf = getForm().getFiltriAccessiFalliti().getCd_fisc_user().parse();
        String email = getForm().getFiltriAccessiFalliti().getDs_email_user().parse();
        String idEsterno = getForm().getFiltriAccessiFalliti().getCd_id_esterno().parse();
        LogVRicAccessiTableBean loginEventoTableBean = new LogVRicAccessiTableBean();
        try {
            loginEventoTableBean = amministrazioneUtentiEjb.getLogVRicAccessiTableBean(dateDecorate[0], dateDecorate[1],
                    nmUserid, tipoEvento, inclUtentiAutomi, inclLoginOK, nome, cognome, cf, email, idEsterno);
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
        getForm().getAccessiFallitiList().setTable(loginEventoTableBean);
        getForm().getAccessiFallitiList().getTable().first();
        getForm().getAccessiFallitiList().getTable().setPageSize(10);
        getForm().getAccessiFallitiList().setStatus(Status.view);
        forwardToPublisher(Application.Publisher.RICERCA_ACCESSI_FALLITI);
    }

    private void initFiltriAccessiFalliti() {
        Calendar dtFallimentoDa = Calendar.getInstance();
        dtFallimentoDa.set(Calendar.HOUR_OF_DAY, 23);
        dtFallimentoDa.set(Calendar.MINUTE, 59);
        dtFallimentoDa.set(Calendar.SECOND, 59);
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dtFallimentoDaStr = formato.format(dtFallimentoDa.getTime());
        getForm().getFiltriAccessiFalliti().getDt_evento_da().setValue(dtFallimentoDaStr);
        getForm().getFiltriAccessiFalliti().getTipo_evento().setDecodeMap(ComboGetter.getMappaTipoEvento());
    }

    private Date[] decorateDate(Date dtFallimentoDa, Date dtFallimentoA) {
        Date[] dateDecorate = new Date[2];
        Calendar dtFallimentoDaCal = Calendar.getInstance();
        Calendar dtFallimentoACal = Calendar.getInstance();

        if (dtFallimentoDa == null) {
            dtFallimentoDaCal.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        } else {
            dtFallimentoDaCal.setTime(dtFallimentoDa);
            dtFallimentoDaCal.set(Calendar.HOUR_OF_DAY, 0);
            dtFallimentoDaCal.set(Calendar.MINUTE, 0);
            dtFallimentoDaCal.set(Calendar.SECOND, 0);
        }
        dtFallimentoDa = dtFallimentoDaCal.getTime();

        if (dtFallimentoA == null) {
            // Change date
        } else {
            Calendar oggiCal = Calendar.getInstance();
            oggiCal.set(Calendar.HOUR_OF_DAY, 0);
            oggiCal.set(Calendar.MINUTE, 0);
            oggiCal.set(Calendar.SECOND, 0);
            dtFallimentoACal.setTime(dtFallimentoA);
            if (dtFallimentoACal.before(oggiCal)) {
                dtFallimentoACal.set(Calendar.HOUR_OF_DAY, 23);
                dtFallimentoACal.set(Calendar.MINUTE, 59);
                dtFallimentoACal.set(Calendar.SECOND, 59);
            }
        }
        dtFallimentoA = dtFallimentoACal.getTime();

        dateDecorate[0] = dtFallimentoDa;
        dateDecorate[1] = dtFallimentoA;
        return dateDecorate;
    }

    @Override
    public void initOnClick() throws EMFError {
    }

    @Override
    public void loadDettaglio() throws EMFError {
    }

    @Override
    public void undoDettaglio() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void insertDettaglio() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void saveDettaglio() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
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
        return Application.Publisher.RICERCA_ACCESSI_FALLITI;
    }

    @Override
    public void reloadAfterGoBack(String string) {
    }

    @Override
    public String getControllerName() {
        return Application.Actions.GESTIONE_ACCESSI_FALLITI;
    }
}
