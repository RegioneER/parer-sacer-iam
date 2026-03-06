package it.eng.saceriam.web.action;

import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.VerificaRuoliAbstractAction;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloTableBean;
import it.eng.saceriam.web.ejb.AmministrazioneRuoliEjb;
import it.eng.saceriam.web.ejb.AmministrazioneUtentiEjb;
import it.eng.saceriam.web.ejb.ComboEjb;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.security.Secure;
import java.math.BigDecimal;
import javax.ejb.EJB;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author S257421
 */
public class VerificaRuoliAction extends VerificaRuoliAbstractAction {

    private static final Logger log = LoggerFactory.getLogger(VerificaRuoliAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/ComboEjb")
    private ComboEjb comboEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneUtentiEjb")
    private AmministrazioneUtentiEjb amministrazioneUtentiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/AmministrazioneRuoliEjb")
    private AmministrazioneRuoliEjb amministrazioneRuoliEjb;

    @Override
    public void initOnClick() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject triggerFiltriRuoliNm_applicOnTrigger() throws EMFError {
        getForm().getFiltriRuoli().post(getRequest());
        BigDecimal idApplic = getForm().getFiltriRuoli().getNm_applic().parse();
        if (idApplic == null) {
            getForm().getFiltriRuoli().getNm_ruolo().setDecodeMap(new DecodeMap());
            getForm().getFiltriRuoli().getNm_ruolo_2().setDecodeMap(new DecodeMap());
        } else {
            PrfRuoloTableBean prfRuoliViewBean = amministrazioneUtentiEjb
                    .getPrfRuoliUtenteTableBean(idApplic, "", "PERSONA_FISICA");
            DecodeMap ruoliMap = DecodeMap.Factory.newInstance(prfRuoliViewBean, "id_ruolo",
                    "nm_ruolo");
            getForm().getFiltriRuoli().getNm_ruolo().clear();
            getForm().getFiltriRuoli().getNm_ruolo().setDecodeMap(ruoliMap);
            getForm().getFiltriRuoli().getNm_ruolo_2().clear();
            getForm().getFiltriRuoli().getNm_ruolo_2().setDecodeMap(ruoliMap);
        }
        return getForm().getFiltriRuoli().asJSON();
    }

    @Override
    public JSONObject triggerFiltriRuoliNm_ruoloOnTrigger() throws EMFError {
        getForm().getFiltriRuoli().post(getRequest());
        return getForm().getFiltriRuoli().asJSON();
    }

    @Override
    public JSONObject triggerFiltriRuoliNm_ruolo_2OnTrigger() throws EMFError {
        getForm().getFiltriRuoli().post(getRequest());
        return getForm().getFiltriRuoli().asJSON();
    }

    @Override
    public void insertDettaglio() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadDettaglio() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void undoDettaglio() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveDettaglio() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void elencoOnClick() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.VERIFICA_RUOLI;
    }

    @Override
    public void process() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reloadAfterGoBack(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getControllerName() {
        return Application.Actions.VERIFICA_RUOLI;
    }

    @Override
    public void calcolaDifferenze() throws EMFError {
    }

    /**
     * Carica la pagina di lista dei ruoli
     *
     * @throws EMFError errore generico
     */
    @Secure(action = "Menu.AmministrazioneSistema.VerificaRuoli")
    public void verificaRuoliPage() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneSistema.VerificaRuoli");

        getForm().getFiltriRuoli().getNm_applic().clear();
        getForm().getFiltriRuoli().getNm_applic().setEditMode();
        getForm().getFiltriRuoli().getNm_ruolo().clear();
        getForm().getFiltriRuoli().getNm_ruolo().setEditMode();
        getForm().getFiltriRuoli().getNm_ruolo_2().clear();
        getForm().getFiltriRuoli().getNm_ruolo_2().setEditMode();
        getForm().getFiltriRuoli().getCalcolaDifferenze().setEditMode();
        getForm().getFiltriRuoli().getNm_applic()
                .setDecodeMap(comboEjb.getMappaApplicAbilitate(getUser().getIdUtente(), true));
        getForm().getFiltriRuoli().getNm_ruolo().setDecodeMap(new DecodeMap());
        getForm().getFiltriRuoli().getNm_ruolo_2().setDecodeMap(new DecodeMap());
        forwardToPublisher(Application.Publisher.VERIFICA_RUOLI);
    }

    /**
     * Recupera la struttura ad albero delle differenze tra i permessi di due ruoli per una
     * specifica applicazione, restituendo il risultato in formato JSON.
     *
     * @throws EMFError errore generico durante l'elaborazione
     */
    public void getAlberoJson() throws EMFError {
        BigDecimal idApplic = getForm().getFiltriRuoli().getNm_applic().parse();
        BigDecimal idRuolo = getForm().getFiltriRuoli().getNm_ruolo().parse();
        BigDecimal idRuolo2 = getForm().getFiltriRuoli().getNm_ruolo_2().parse();
        JSONObject oggettoRitorno = amministrazioneRuoliEjb.calcolaDifferenze(idApplic, idRuolo,
                idRuolo2);
        log.info("json=" + oggettoRitorno.toString());
        redirectToAjax(oggettoRitorno);
    }

}
