package it.eng.saceriam.web.action;

import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.GestioneAmbitoTerritorialeAbstractAction;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritTableBean;
import it.eng.saceriam.web.ejb.GestioneAmbitoTerritorialeEjb;
import it.eng.saceriam.web.util.WebConstants;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.table.BaseTable;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.security.Secure;
import java.math.BigDecimal;
import javax.ejb.EJB;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GestioneAmbitoTerritorialeAction extends GestioneAmbitoTerritorialeAbstractAction {

    private static final Logger log = LoggerFactory.getLogger(GestioneAmbitoTerritorialeAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/GestioneAmbitoTerritorialeEjb")
    private GestioneAmbitoTerritorialeEjb gestioneAmbitoTerritorialeEjb;

    @Override
    public void initOnClick() throws EMFError {
    }

    @Override
    public void loadDettaglio() throws EMFError {
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
    }

    @Secure(action = "Menu.AmministrazioneSistema.GestioneAmbitoTerritoriale")
    public void gestioneAmbitoTerritoriale() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneAmbitoTerritoriale");

        getForm().getAmbitoTerritoriale().setStatus(Status.insert);
        getForm().getAmbitoTerritoriale().setViewMode();

        DecodeMap tipiMap = new DecodeMap();
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();

        for (Enum tipo : WebConstants.TipoAmbitoTerritoriale.values()) {
            br.setString("ti_ambito_territ", tipo.name());
            bt.add(br);
        }
        tipiMap.populatedMap(bt, "ti_ambito_territ", "ti_ambito_territ");
        getForm().getAmbitoTerritoriale().getTi_ambito_territ().setDecodeMap(tipiMap);

        OrgAmbitoTerritTableBean table = gestioneAmbitoTerritorialeEjb.getOrgAmbitoTerritTableBean(null);
        getForm().getGestAmbTree().setTable(table);

        forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
    }

    /**
     * Ritorna il default publisher di questa action
     *
     * @return La stringa relativa al publisher di default
     */
    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.GESTIONE_AMBITO_TERRITORIALE;
    }

    @Override
    public void process() throws EMFError {
    }

    @Override
    public void insertDettaglio() throws EMFError {
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
    }

    @Override
    public void undoDettaglio() throws EMFError {
    }

    @Override
    public void saveDettaglio() throws EMFError {
    }

    @Override
    public void elencoOnClick() throws EMFError {
        if (getLastPublisher().equals("/amministrazioneSistema/gestioneAmbitoTerritoriale")) {
            forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
        } else {
            goBack();
        }
    }

    @Override
    public String getControllerName() {
        return Application.Actions.GESTIONE_AMBITO_TERRITORIALE;
    }

    @Override
    public JSONObject triggerAmbitoTerritorialeTi_ambito_territOnTrigger() throws EMFError {
        getForm().getAmbitoTerritoriale().post(getRequest());

        populateIdAmbitoTerritPadreComboBox(getForm().getAmbitoTerritoriale().getTi_ambito_territ().parse());

        return getForm().getAmbitoTerritoriale().asJSON();
    }

    public void createNode() {
        String publisher = getLastPublisher();
        if (publisher.equals(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE)) {
            getForm().getAmbitoTerritoriale().clear();
            getForm().getAmbitoTerritoriale().setEditMode();
            getForm().getAmbitoTerritoriale().setStatus(Status.insert);
            forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
        }
    }

    public void confRemove() {

        getRequest().setAttribute("confRemove", true);
        String publisher = getLastPublisher();

        if (publisher.equals(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE)) {
            forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
        }
    }

    public void deleteNode() throws EMFError {

        String publisher = getLastPublisher();
        getMessageBox().clear();

        if (publisher.equals(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE)) {
            String cdAmbitoTerrit = getForm().getAmbitoTerritoriale().getCd_ambito_territ().parse();
            if (StringUtils.isNotBlank(cdAmbitoTerrit)) {
                // popup per conferma
                try {
                    gestioneAmbitoTerritorialeEjb
                            .deleteOrgAmbitoTerrit(getForm().getAmbitoTerritoriale().getCd_ambito_territ().parse());
                } catch (ParerUserError ex) {
                    log.error("Errore", ex);
                    getMessageBox().addError(ex.getDescription());
                }
            } else {
                getMessageBox().addError("Selezionare un nodo da eliminare");
            }

            OrgAmbitoTerritTableBean table = gestioneAmbitoTerritorialeEjb.getOrgAmbitoTerritTableBean(null);
            getForm().getGestAmbTree().setTable(table);
            getForm().getAmbitoTerritoriale().clear();
            forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
        }

    }

    public void moveNode() {
        getMessageBox().clear();

        String publisher = getLastPublisher();

        BigDecimal nodeId = new BigDecimal(getRequest().getParameter("nodeId"));
        BigDecimal nodeDestId = null;
        // se l'idPadre è nullo, non lo inserisco
        if (!getRequest().getParameter("nodeDestId").equals("0")) {
            nodeDestId = new BigDecimal(getRequest().getParameter("nodeDestId"));
        }

        if (publisher.equals(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE)) {

            getForm().getAmbitoTerritoriale().setViewMode();

            try {
                gestioneAmbitoTerritorialeEjb.moveOrgAmbitoTerritorialeNode(nodeId, nodeDestId);
            } catch (ParerUserError ie) {
                getMessageBox().addError(ie.getDescription());
            }

            // Ricarico l'albero con le nuove modifiche
            OrgAmbitoTerritTableBean table = gestioneAmbitoTerritorialeEjb.getOrgAmbitoTerritTableBean(null);
            getForm().getGestAmbTree().setTable(table);
            forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);

        }
    }

    public void loadNode() {

        String publisher = getLastPublisher();

        if (publisher.equals(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE)) {

            getForm().getAmbitoTerritoriale().setViewMode();
            OrgAmbitoTerritRowBean orgAmbitoTerritRowBean = gestioneAmbitoTerritorialeEjb
                    .getOrgAmbitoTerritRowBean(getRequest().getParameter("nodeName"));
            populateIdAmbitoTerritPadreComboBox(orgAmbitoTerritRowBean.getTiAmbitoTerrit());

            try {
                getForm().getAmbitoTerritoriale().copyFromBean(orgAmbitoTerritRowBean);
                if (orgAmbitoTerritRowBean.getTiAmbitoTerrit().equals("REGIONE/STATO")) {
                    getForm().getAmbitoTerritoriale().getTi_ambito_territ().setValue("REGIONE_STATO");
                }
            } catch (EMFError ex) {
                log.error("Errore", ex);
                getForm().getAmbitoTerritoriale().clear();
            }
            getForm().getAmbitoTerritoriale().getId_to_update()
                    .setValue(orgAmbitoTerritRowBean.getIdAmbitoTerrit().toString());

            forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
        }
    }

    public void updateNode() throws EMFError {

        String publisher = getLastPublisher();

        if (publisher.equals(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE)) {

            if (getForm().getAmbitoTerritoriale().getId_to_update().parse() == null) {
                getMessageBox().addError("Selezionare un nodo");
            } else {
                getForm().getAmbitoTerritoriale().setEditMode();
                getForm().getAmbitoTerritoriale().setStatus(Status.update);
                getSession().setAttribute("idToUpdate", getForm().getAmbitoTerritoriale().getId_to_update().parse());
            }
            forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
        }
    }

    @Override
    public void salvaAmbitoTerritoriale() throws EMFError {
        getMessageBox().clear();
        getForm().getAmbitoTerritoriale().post(getRequest());

        if (getForm().getAmbitoTerritoriale().validate(getMessageBox())) {
            // Invece di usare il copyToBean, creo dinamicamente il rowBean per colpa di REGIONE/STATO
            // DANNATO SLASH
            String cdAmbitoTerrit;
            String tiAmbitoTerrit;
            OrgAmbitoTerritRowBean orgAmbitoTerritRowBean = new OrgAmbitoTerritRowBean();
            if ((cdAmbitoTerrit = getForm().getAmbitoTerritoriale().getCd_ambito_territ().parse()) == null) {
                getMessageBox().addError("Inserire nome ambito territoriale</br>");
            } else {
                orgAmbitoTerritRowBean.setCdAmbitoTerrit(cdAmbitoTerrit);
            }
            if ((tiAmbitoTerrit = getForm().getAmbitoTerritoriale().getTi_ambito_territ().parse()) == null) {
                getMessageBox().addError("Inserire tipo ambito territoriale</br>");
            } else {
                if ((!tiAmbitoTerrit.equals(WebConstants.TipoAmbitoTerritoriale.REGIONE_STATO.name()))
                        && (getForm().getAmbitoTerritoriale().getId_ambito_territ_padre().parse() == null)) {
                    getMessageBox().addError("Inserire nodo padre</br>");
                } else if (tiAmbitoTerrit.equals(WebConstants.TipoAmbitoTerritoriale.REGIONE_STATO.name())) {
                    orgAmbitoTerritRowBean.setTiAmbitoTerrit("REGIONE/STATO");
                } else {
                    orgAmbitoTerritRowBean.setTiAmbitoTerrit(tiAmbitoTerrit);
                    orgAmbitoTerritRowBean.setIdAmbitoTerritPadre(
                            getForm().getAmbitoTerritoriale().getId_ambito_territ_padre().parse());
                }
            }

            try {
                if (getMessageBox().isEmpty()) {
                    if (getForm().getAmbitoTerritoriale().getStatus().equals(Status.insert)) {
                        gestioneAmbitoTerritorialeEjb.insertOrgAmbitoTerritoriale(orgAmbitoTerritRowBean);
                    } else if (getForm().getAmbitoTerritoriale().getStatus().equals(Status.update)) {
                        BigDecimal idToUpdate = (BigDecimal) getSession().getAttribute("idToUpdate");
                        gestioneAmbitoTerritorialeEjb.updateOrgAmbitoTerritoriale(idToUpdate, orgAmbitoTerritRowBean);
                    }
                    getForm().getAmbitoTerritoriale().setViewMode();
                    getForm().getAmbitoTerritoriale().clear();
                    OrgAmbitoTerritTableBean table = gestioneAmbitoTerritorialeEjb.getOrgAmbitoTerritTableBean(null);
                    getForm().getGestAmbTree().setTable(table);
                }
            } catch (ParerUserError ie) {
                getMessageBox().addError(ie.getDescription());
            }
        }

        forwardToPublisher(Application.Publisher.GESTIONE_AMBITO_TERRITORIALE);
    }

    public void populateIdAmbitoTerritPadreComboBox(String tipo) {
        OrgAmbitoTerritTableBean tableBean = new OrgAmbitoTerritTableBean();

        if (tipo != null) {
            if (tipo.equals(WebConstants.TipoAmbitoTerritoriale.PROVINCIA.name())) {
                tableBean = gestioneAmbitoTerritorialeEjb.getOrgAmbitoTerritTableBean("REGIONE/STATO");
            }
            if (tipo.equals(WebConstants.TipoAmbitoTerritoriale.FORMA_ASSOCIATA.name())) {
                tableBean = gestioneAmbitoTerritorialeEjb.getOrgAmbitoTerritTableBean("PROVINCIA");
            }
        }
        DecodeMap map = new DecodeMap();
        map.populatedMap(tableBean, "id_ambito_territ", "cd_ambito_territ");
        getForm().getAmbitoTerritoriale().getId_ambito_territ_padre().setDecodeMap(map);
    }

}
