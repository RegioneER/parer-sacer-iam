package it.eng.saceriam.web.action;

import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneClasseEnteAbstractAction;
import it.eng.saceriam.slite.gen.tablebean.OrgClasseEnteConvenzTableBean;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.security.Secure;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
public class AmministrazioneClasseEnteAction extends AmministrazioneClasseEnteAbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(AmministrazioneClasseEnteAction.class);

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
