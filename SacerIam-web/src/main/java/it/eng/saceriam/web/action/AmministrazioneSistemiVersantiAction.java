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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneSistemiVersantiAbstractAction;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersArkRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersanteRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersanteUserRefRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbienteEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamTableBean;
import it.eng.saceriam.slite.gen.viewbean.AplVRicSistemaVersanteRowBean;
import it.eng.saceriam.slite.gen.viewbean.AplVRicSistemaVersanteTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteNonConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVTreeOrganizIamTableBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.web.ejb.SistemiVersantiEjb;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.base.BaseElements;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.message.Message;
import it.eng.spagoLite.message.MessageBox;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Gilioli_P
 */
public class AmministrazioneSistemiVersantiAction extends AmministrazioneSistemiVersantiAbstractAction {

    private static final Logger log = LoggerFactory.getLogger(AmministrazioneSistemiVersantiAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/SistemiVersantiEjb")
    private SistemiVersantiEjb sistemiVersantiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiEjb")
    private EntiConvenzionatiEjb entiConvenzionatiEjb;

    @EJB(mappedName = "java:app/SacerIam-ejb/UserHelper")
    UserHelper userHelper;

    @Override
    public void initOnClick() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    @Override
    public void loadDettaglio() throws EMFError {
        try {
            /*
             * Se l'evento per il quale passo in loadDettaglio NON è inserimento e cancellazione (per i quali non ha
             * senso caricare dei dati...)
             */
            if (!getNavigationEvent().equals(ListAction.NE_DETTAGLIO_DELETE)
                    && !getNavigationEvent().equals(ListAction.NE_DETTAGLIO_INSERT)) {
                /* Se riguarda la lista dei Sistemi Versanti */
                if (getTableName().equals(getForm().getListaSistemiVersanti().getName())) {
                    // Recupero l'id del sistema versante
                    initSistemaVersante();
                    BigDecimal idSistemaVersante = ((AplVRicSistemaVersanteRowBean) getForm().getListaSistemiVersanti()
                            .getTable().getCurrentRow()).getIdSistemaVersante();
                    loadDettaglioSistemaVersante(idSistemaVersante);
                }
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
    }

    private void initSistemaVersante() {
        List<String> listaTipiEntiAbilitati = new ArrayList<>();
        listaTipiEntiAbilitati.add("FORNITORE_ESTERNO");
        listaTipiEntiAbilitati.add("SOGGETTO_ATTUATORE");
        OrgVRicEnteNonConvenzTableBean entiNonConvenzionati = entiConvenzionatiEjb
                .getOrgVRicEnteNonConvenzAbilListTableBean(BigDecimal.valueOf(getUser().getIdUtente()),
                        listaTipiEntiAbilitati);
        getForm().getSistemaVersanteDetail().getId_ente_siam()
                .setDecodeMap(DecodeMap.Factory.newInstance(entiNonConvenzionati, "id_ente_siam", "nm_ente_siam"));

        // Date precompilate
        String dataOdierna = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        getForm().getSistemaVersanteDetail().getDt_ini_val().setValue(dataOdierna);
        Calendar cal = Calendar.getInstance();
        cal.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
        String dataFine = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
        getForm().getSistemaVersanteDetail().getDt_fine_val().setValue(dataFine);

        // Parte non cessato grazie alle date preimpostate
        getForm().getSistemaVersanteDetail().getFl_cessato().setValue("0");
    }

    private void loadDettaglioSistemaVersante(BigDecimal idSistemaVersante) throws EMFError, ParerUserError {
        // Recupero il sistema versante da DB
        // AplSistemaVersanteRowBean sistemaVersante =
        // sistemiVersantiEjb.getAplSistemaVersanteRowBean(idSistemaVersante);
        AplVRicSistemaVersanteRowBean sistemaVersante = sistemiVersantiEjb
                .getAplVRicSistemaVersanteRowBean(idSistemaVersante, getUser().getIdUtente());
        // Lo caccio dentro i campi di dettaglio
        getForm().getSistemaVersanteDetail().copyFromBean(sistemaVersante);
        getForm().getStruttureVersantiList().getDl_composito_organiz()
                .setExternalLinkParamApplic(entiConvenzionatiEjb.getSacerUrlForStruttureVersanti());
        // Tabella con le strutture versanti che hanno utenti cui è associato il sistema versante
        getForm().getStruttureVersantiList()
                .setTable(sistemiVersantiEjb.getAplVLisOrganizUsoSisVersTableBean(idSistemaVersante));
        getForm().getStruttureVersantiList().getTable().setPageSize(10);
        getForm().getStruttureVersantiList().getTable().first();
        // Recupero gli utenti archivisti
        getForm().getUtentiArchivistiList()
                .setTable(sistemiVersantiEjb.getAplSistemaVerArkRifTableBean(idSistemaVersante));
        getForm().getUtentiArchivistiList().getTable().setPageSize(10);
        getForm().getUtentiArchivistiList().getTable().first();
        // Recupero i referenti della ditta produttrice
        getForm().getReferentiDittaProduttriceList()
                .setTable(sistemiVersantiEjb.getAplSistemaVeranteUserRefTableBean(idSistemaVersante));
        getForm().getReferentiDittaProduttriceList().getTable().setPageSize(10);
        getForm().getReferentiDittaProduttriceList().getTable().first();
    }

    @Override
    public void undoDettaglio() throws EMFError {
        if (getTableName().equals(getForm().getListaSistemiVersanti().getName())) {
            // Ricavo il vecchio status e imposto il nuovo
            Status status = getForm().getSistemaVersanteDetail().getStatus();
            getForm().getListaSistemiVersanti().setStatus(BaseElements.Status.view);
            getForm().getSistemaVersanteDetail().setStatus(BaseElements.Status.view);
            getForm().getSistemaVersanteDetail().setViewMode();
            // Se sono in insert, rimuovo la riga "preparata" e torno indietro...
            if (status.equals(BaseElements.Status.insert)) {
                getForm().getListaSistemiVersanti().remove();
                goBack();
            } // ... se invece sono in update, rimango dove sono ricarico i vecchi dati
            else {
                loadDettaglio();
                forwardToPublisher(getLastPublisher());
            }
        }
    }

    @Override
    public void insertDettaglio() throws EMFError {
        try {
            if (getTableName().equals(getForm().getListaSistemiVersanti().getName())) {
                // Resetto ed imposto in editMode i campi di dettaglio
                getForm().getSistemaVersanteDetail().reset();
                getForm().getStruttureVersantiList().clear();
                getForm().getUtentiArchivistiList().clear();
                getForm().getReferentiDittaProduttriceList().clear();
                getForm().getSistemaVersanteDetail().setEditMode();
                initSistemaVersante();
                // Inizializzo il record da inserire nella lista
                getForm().getListaSistemiVersanti().getTable().add(new AplVRicSistemaVersanteRowBean());
                // Imposto in modalità inserimento la lista e il dettaglio
                getForm().getListaSistemiVersanti().setStatus(BaseElements.Status.insert);
                getForm().getSistemaVersanteDetail().setStatus(BaseElements.Status.insert);
                forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
            } else if (getTableName().equals(getForm().getUtentiArchivistiList().getName())) {
                final BigDecimal idSistemaVersante = getForm().getSistemaVersanteDetail().getId_sistema_versante()
                        .parse();
                BigDecimal idUserIamCor = new BigDecimal(getUser().getIdUtente());
                // Inizializzo la form per andare alla ricerca utenti amministratori inserendo i valori trovati
                AmministrazioneUtentiForm amministrazioneUtentiForm = new AmministrazioneUtentiForm();
                amministrazioneUtentiForm.getUtentiArchivistiPerSistemaVersante().getId_sistema_versante()
                        .setValue(idSistemaVersante.toPlainString());
                // Recupero gli enti di appartenzenza di tipo conservatore e amministratore e i rispettivi enti
                // collegati cui l'utente corrente è abilitato
                OrgEnteSiamTableBean enteSiamTableBean = entiConvenzionatiEjb
                        .getOrgEnteSiamCollegUserAbilTableBeanByTiEnteConvenz(idUserIamCor,
                                ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE,
                                ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE);
                amministrazioneUtentiForm.getFiltriUtenti().getId_ente_convenz_appart()
                        .setDecodeMap(DecodeMap.Factory.newInstance(enteSiamTableBean, "id_ente_siam", "nm_ente_siam"));
                if (enteSiamTableBean.size() == 1) {
                    String[] valoriEnte = new String[] { "" + enteSiamTableBean.getRow(0).getIdEnteSiam() };
                    amministrazioneUtentiForm.getFiltriUtenti().getId_ente_convenz_appart().setValues(valoriEnte);
                }
                // Recupero gli ambienti enti di appartenzenza di tipo conservatore e amministratore e i rispettivi
                // ambienti degli enti collegati cui l'utente corrente è abilitato
                OrgAmbienteEnteConvenzTableBean ambienteEnteConvenzTableBean = entiConvenzionatiEjb
                        .getOrgAmbientiEnteConvenzCollegUserAbilTableBeanByTiEnteConvenz(idUserIamCor,
                                ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE,
                                ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE);
                amministrazioneUtentiForm.getFiltriUtenti().getId_amb_ente_convenz_appart()
                        .setDecodeMap(DecodeMap.Factory.newInstance(ambienteEnteConvenzTableBean,
                                "id_ambiente_ente_convenz", "nm_ambiente_ente_convenz"));
                if (enteSiamTableBean.size() == 1) {
                    String[] valoriAmbiente = new String[] {
                            "" + enteSiamTableBean.getRow(0).getIdAmbienteEnteConvenz() };
                    amministrazioneUtentiForm.getFiltriUtenti().getId_amb_ente_convenz_appart()
                            .setValues(valoriAmbiente);
                }
                redirectToAction(Application.Actions.AMMINISTRAZIONE_UTENTI, "?operation=ricercaUtentiArchivistiPage",
                        amministrazioneUtentiForm);
            } else if (getTableName().equals(getForm().getReferentiDittaProduttriceList().getName())) {
                final BigDecimal idSistemaVersante = getForm().getSistemaVersanteDetail().getId_sistema_versante()
                        .parse();
                // Inizializzo la form per andare alla ricerca utenti inserendo i valori trovati
                AmministrazioneUtentiForm amministrazioneUtentiForm = new AmministrazioneUtentiForm();
                amministrazioneUtentiForm.getReferentiDittaProduttricePerSistemaVersante().getId_sistema_versante()
                        .setValue(idSistemaVersante.toPlainString());
                redirectToAction(Application.Actions.AMMINISTRAZIONE_UTENTI, "?operation=ricercaUtentiPage",
                        amministrazioneUtentiForm);
            }
        } catch (ParerUserError e) {
            getMessageBox().addError(e.getDescription());
        }
    }

    @Override
    public void updateListaSistemiVersanti() throws EMFError {
        if (getTableName().equals(getForm().getListaSistemiVersanti().getName())) {
            // Imposto in modalità modifica la lista e il dettaglio
            getForm().getListaSistemiVersanti().setStatus(BaseElements.Status.update);
            getForm().getSistemaVersanteDetail().setStatus(BaseElements.Status.update);
            getForm().getSistemaVersanteDetail().setEditMode();
            forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
        }
    }

    @Override
    public void saveDettaglio() throws EMFError {
        if (getTableName().equals(getForm().getListaSistemiVersanti().getName())) {
            salvaSistemaVersante();
        }
    }

    private void salvaSistemaVersante() throws EMFError {
        // Validazione formale dei campi
        if (getForm().getSistemaVersanteDetail().postAndValidate(getRequest(), getMessageBox())) {
            // Recupero i campi
            String denominazione = getForm().getSistemaVersanteDetail().getNm_sistema_versante().parse();
            String descrizione = getForm().getSistemaVersanteDetail().getDs_sistema_versante().parse();
            String versione = getForm().getSistemaVersanteDetail().getCd_versione().parse();
            BigDecimal idEnteSiam = getForm().getSistemaVersanteDetail().getId_ente_siam().parse();
            String dsEmail = getForm().getSistemaVersanteDetail().getDs_email().parse();
            String dsNote = getForm().getSistemaVersanteDetail().getDs_note().parse();
            String flPec = getForm().getSistemaVersanteDetail().getFl_pec().parse();
            String flIntegrazione = getForm().getSistemaVersanteDetail().getFl_integrazione().parse();
            String flAssociaPersonaFisica = getForm().getSistemaVersanteDetail().getFl_associa_persona_fisica().parse();
            long idUserIam = getUser().getIdUtente();
            Date dtIniVal = getForm().getSistemaVersanteDetail().getDt_ini_val().parse();
            Date dtFineVal = getForm().getSistemaVersanteDetail().getDt_fine_val().parse();

            // Controlli ordine validità date
            String errore = null;
            if ((errore = DateUtil.ucContrDate("", dtIniVal, dtFineVal)) != null) {
                getMessageBox().addError(errore);
            }

            if (!getMessageBox().hasError()) {
                try {
                    // Se sono in inserimento
                    if (getForm().getListaSistemiVersanti().getStatus().equals(BaseElements.Status.insert)) {
                        // Inserisco il record
                        Long idSistemaVersante = sistemiVersantiEjb.insertSistemaVersante(denominazione, descrizione,
                                versione, idEnteSiam, dsEmail, flPec, flIntegrazione, flAssociaPersonaFisica, dtIniVal,
                                dtFineVal, dsNote);
                        getForm().getSistemaVersanteDetail().getId_sistema_versante()
                                .setValue(idSistemaVersante.toString());
                        AplSistemaVersanteRowBean row = new AplSistemaVersanteRowBean();
                        getForm().getSistemaVersanteDetail().copyToBean(row);
                        getForm().getListaSistemiVersanti().getTable().last();
                        getForm().getListaSistemiVersanti().getTable().add(row);
                        getMessageBox().addInfo("Sistema versante inserito con successo!");
                    } // Se sono in aggiornamento
                    else if (getForm().getListaSistemiVersanti().getStatus().equals(BaseElements.Status.update)) {
                        BigDecimal idSistemaVersante = getForm().getSistemaVersanteDetail().getId_sistema_versante()
                                .parse();
                        sistemiVersantiEjb.updateSistemaVersante(idSistemaVersante, denominazione, descrizione,
                                versione, idEnteSiam, dsEmail, flPec, flIntegrazione, flAssociaPersonaFisica, idUserIam,
                                dtIniVal, dtFineVal, dsNote);
                        getMessageBox().addInfo("Sistema versante modificato con successo!");
                    }

                    BigDecimal idSistemaVersante = getForm().getSistemaVersanteDetail().getId_sistema_versante()
                            .parse();
                    if (idSistemaVersante != null) {
                        loadDettaglioSistemaVersante(idSistemaVersante);
                    }
                    getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                    // Rimetto tutto in viewMode
                    getForm().getListaSistemiVersanti().setStatus(BaseElements.Status.view);
                    getForm().getSistemaVersanteDetail().setStatus(BaseElements.Status.view);
                    getForm().getSistemaVersanteDetail().setViewMode();
                } catch (ParerUserError | IncoherenceException ex) {
                    getMessageBox().addError(ex.getMessage());
                    log.error("Eccezione", ex);
                }
            }
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
        /* Se riguarda la lista dei Sistemi Versanti */
        if (getTableName().equals(getForm().getListaSistemiVersanti().getName())) {
            forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
        }
    }

    @Override
    public void elencoOnClick() throws EMFError {
        goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.RICERCA_SISTEMI_VERSANTI;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
        try {

            if (publisherName.equals(Application.Publisher.RICERCA_SISTEMI_VERSANTI)) {
                int rowIndex;
                int pageSize;
                if (getForm().getListaSistemiVersanti().getTable() != null) {
                    rowIndex = getForm().getListaSistemiVersanti().getTable().getCurrentRowIndex();
                    pageSize = getForm().getListaSistemiVersanti().getTable().getPageSize();
                } else {
                    rowIndex = 0;
                    pageSize = 10;
                }
                AplVRicSistemaVersanteTableBean sistemiVersantiTB = sistemiVersantiEjb
                        .getAplVRicSistemaVersanteTableBean(getForm().getFiltriSistemiVersanti(),
                                getUser().getIdUtente());
                getForm().getListaSistemiVersanti().setTable(sistemiVersantiTB);
                getForm().getListaSistemiVersanti().getTable().setPageSize(pageSize);
                getForm().getListaSistemiVersanti().getTable().setCurrentRowIndex(rowIndex);
            } else if (publisherName.equals(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE)) {
                BaseRowInterface currentRow = getForm().getListaSistemiVersanti().getTable().getCurrentRow();
                BigDecimal idSistemaVersante = currentRow.getBigDecimal("id_sistema_versante");
                if (idSistemaVersante != null) {
                    loadDettaglioSistemaVersante(idSistemaVersante);
                }
                getForm().getSistemaVersanteDetail().setViewMode();
                getForm().getSistemaVersanteDetail().setStatus(Status.view);
                getForm().getSistemaVersanteDetail().setStatus(Status.view);
            }
        } catch (ParerUserError e) {
            getMessageBox().addError(e.getDescription());
            forwardToPublisher(getLastPublisher());
        } catch (EMFError e) {
            log.error("Errore nel ricaricamento della pagina " + publisherName, e);
            getMessageBox().addError("Errore nel ricaricamento della pagina " + publisherName);
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public String getControllerName() {
        return Application.Actions.AMMINISTRAZIONE_SISTEMI_VERSANTI;
    }

    @Override
    public void ricercaSistemiVersanti() throws EMFError {
        getForm().getFiltriSistemiVersanti().post(getRequest());
        if (getForm().getFiltriSistemiVersanti().validate(getMessageBox())) {
            AplVRicSistemaVersanteTableBean sistemiVersantiTB = sistemiVersantiEjb
                    .getAplVRicSistemaVersanteTableBean(getForm().getFiltriSistemiVersanti(), getUser().getIdUtente());
            getForm().getListaSistemiVersanti().setTable(sistemiVersantiTB);
            getForm().getListaSistemiVersanti().getTable().setPageSize(10);
            getForm().getListaSistemiVersanti().getTable().first();
        }
        forwardToPublisher(Application.Publisher.RICERCA_SISTEMI_VERSANTI);
    }

    @Secure(action = "Menu.AmministrazioneSistema.GestioneSistemiVersanti")
    public void ricercaSistemiVersantiPage() throws EMFError {
        try {
            getUser().getMenu().reset();
            getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneSistemiVersanti");
            /* Preparo i filtri */
            getForm().getFiltriSistemiVersanti().clear();
            // Elimino gli eventuali attributi in sessione
            removeSessionAttributes();
            /* Carico la combo delle organizzazioni in base all'utente corrente */
            UsrVTreeOrganizIamTableBean tabellaOrganiz = sistemiVersantiEjb.getOrganizUltimoLivelloSacer();
            DecodeMap mappaOrganizApplic = new DecodeMap();
            mappaOrganizApplic.populatedMap(tabellaOrganiz, "id_organiz_applic", "dl_composito_organiz");
            getForm().getFiltriSistemiVersanti().getId_organiz_applic().setDecodeMap(mappaOrganizApplic);
            // Carico i nomi degli archivisti di riferimento dei sistemi versanti già presenti sul sistema
            getForm().getFiltriSistemiVersanti().getArchivista().setDecodeMap(DecodeMap.Factory
                    .newInstance(entiConvenzionatiEjb.getUtentiArchivisti(), "id_user_iam", "nm_userid"));
            //
            getForm().getFiltriSistemiVersanti().getFl_cessato_filtro()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
            getForm().getFiltriSistemiVersanti().getFl_integrazione_filtro()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
            getForm().getFiltriSistemiVersanti().getFl_associa_persona_fisica_filtro()
                    .setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
            // Metto i filtri editabili
            getForm().getFiltriSistemiVersanti().setEditMode();
            // Inizializzo la lista risultato
            getForm().getListaSistemiVersanti().setTable(new AplVRicSistemaVersanteTableBean());
            getForm().getListaSistemiVersanti().getTable().setPageSize(10);
            getForm().getListaSistemiVersanti().getTable().first();
            getForm().getListaSistemiVersanti().setStatus(BaseElements.Status.view);
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
        forwardToPublisher(Application.Publisher.RICERCA_SISTEMI_VERSANTI);
    }

    @Override
    public void deleteListaSistemiVersanti() throws EMFError {
        getMessageBox().clear();
        getMessageBox().setViewMode(MessageBox.ViewMode.alert);
        int riga = getForm().getListaSistemiVersanti().getTable().getCurrentRowIndex();
        // Recupero l'id del record da cancellare
        BigDecimal idSistemaVersante = ((AplVRicSistemaVersanteRowBean) getForm().getListaSistemiVersanti().getTable()
                .getCurrentRow()).getIdSistemaVersante();
        int eliminati = sistemiVersantiEjb.deleteSistemaVersante(idSistemaVersante);
        if (eliminati == 1) {
            // Rimuovo dalla lista il record
            getForm().getListaSistemiVersanti().getTable().remove(riga);
            // Comunico la lieta novella
            getMessageBox().addInfo("Sistema versante eliminato col successo!");
            // Redirezione a seconda che mi trovi nella pagina di ricerca o di dettaglio
            if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE)) {
                goBack();
            } else {
                forwardToPublisher(getLastPublisher());
            }
        } else {
            getMessageBox().addError(
                    "Il sistema versante non può essere eliminato perchè usato per almeno un utente oppure da utenti cessati ma che hanno precedentemente effettuato versamenti");
            forwardToPublisher(getLastPublisher());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione utenti archivisti">
    @Override
    public void deleteUtentiArchivistiList() throws EMFError {
        AplSistemaVersArkRifRowBean currentRow = (AplSistemaVersArkRifRowBean) getForm().getUtentiArchivistiList()
                .getTable().getCurrentRow();
        BigDecimal idSistemaVersArkRif = currentRow.getIdSistemaVersArkRif();
        int riga = getForm().getUtentiArchivistiList().getTable().getCurrentRowIndex();
        getSession().setAttribute("idSistemaVersArkRif", idSistemaVersArkRif);
        getSession().setAttribute("riga", riga);
        String utenteDaEliminare = currentRow.getString("nm_userid");
        getRequest().setAttribute("utenteDaEliminare", utenteDaEliminare);
        getRequest().setAttribute("customDeleteUtentiArkMessageBox", true);
        forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
    }

    @Override
    public void confermaCancUtenteArk() {
        BigDecimal idSistemaVersArkRif = (BigDecimal) getSession().getAttribute("idSistemaVersArkRif");
        int riga = (Integer) getSession().getAttribute("riga");
        if (!getMessageBox().hasError() && idSistemaVersArkRif != null) {
            try {
                if (!getMessageBox().hasError()) {
                    sistemiVersantiEjb.deleteAplSistemaVersArkRif(idSistemaVersArkRif);
                    getForm().getUtentiArchivistiList().getTable().remove(riga);
                    // Elimino gli attributi in sessione
                    removeSessionAttributes();
                    //
                    getForm().getUtentiArkCustomMessageButtonList().setViewMode();
                    getMessageBox().addInfo("Utente archivista eliminato con successo dal sistema versante");
                    getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError("L'utente archivista non pu\u00F2 essere eliminato: " + ex.getDescription());
            }
        } else {
            getMessageBox().addError("L'utente archivista non pu\u00F2 essere eliminato");
        }

        forwardToPublisher(getLastPublisher());
    }

    @Override
    public void annullaCancUtenteArk() {
        // Nascondo i bottoni con javascript disattivato
        getForm().getUtentiArkCustomMessageButtonList().setViewMode();
        // Elimino gli attributi in sessione
        removeSessionAttributes();
        forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gestione referente ditta produttrice">
    @Override
    public void deleteReferentiDittaProduttriceList() throws EMFError {
        AplSistemaVersanteUserRefRowBean currentRow = (AplSistemaVersanteUserRefRowBean) getForm()
                .getReferentiDittaProduttriceList().getTable().getCurrentRow();
        BigDecimal idSistemaVersanteUserRef = currentRow.getIdSistemaVersanteUserRef();
        int riga = getForm().getReferentiDittaProduttriceList().getTable().getCurrentRowIndex();
        getSession().setAttribute("idSistemaVersanteUserRef", idSistemaVersanteUserRef);
        getSession().setAttribute("riga", riga);
        String utenteDaEliminare = currentRow.getString("nm_userid");
        getRequest().setAttribute("utenteDaEliminare", utenteDaEliminare);
        getRequest().setAttribute("customDeleteReferentiDittaProduttriceMessageBox", true);
        forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
    }

    @Override
    public void confermaCancReferenteDittaProduttrice() {
        BigDecimal idSistemaVersanteUserRef = (BigDecimal) getSession().getAttribute("idSistemaVersanteUserRef");
        int riga = (Integer) getSession().getAttribute("riga");
        if (!getMessageBox().hasError() && idSistemaVersanteUserRef != null) {
            try {
                if (!getMessageBox().hasError()) {
                    sistemiVersantiEjb.deleteAplSistemaVersanteUserRef(idSistemaVersanteUserRef);
                    getForm().getReferentiDittaProduttriceList().getTable().remove(riga);
                    // Elimino gli attributi in sessione
                    removeSessionAttributes();
                    //
                    getForm().getReferentiDittaProduttriceCustomMessageButtonList().setViewMode();
                    getMessageBox().addInfo("Referente ditta produttice eliminato con successo dal sistema versante");
                    getMessageBox().setViewMode(MessageBox.ViewMode.plain);
                }
            } catch (ParerUserError ex) {
                getMessageBox()
                        .addError("Il referente ditta produttrice del sistema versante non pu\u00F2 essere eliminato: "
                                + ex.getDescription());
            }
        } else {
            getMessageBox()
                    .addError("Il referente ditta produttrice del sistema versante non pu\u00F2 essere eliminato");
        }

        forwardToPublisher(getLastPublisher());
    }

    @Override
    public void annullaCancReferenteDittaProduttrice() throws Throwable {
        // Nascondo i bottoni con javascript disattivato
        getForm().getReferentiDittaProduttriceCustomMessageButtonList().setViewMode();
        // Elimino gli attributi in sessione
        removeSessionAttributes();
        forwardToPublisher(Application.Publisher.DETTAGLIO_SISTEMA_VERSANTE);
    }
    // </editor-fold>

    private void removeSessionAttributes() {
        getSession().removeAttribute("idSistemaVersArkRif");
        getSession().removeAttribute("idSistemaVersanteUserRef");
        getSession().removeAttribute("riga");
    }

    @Override
    public JSONObject triggerSistemaVersanteDetailId_ente_siamOnTrigger() throws EMFError {
        getForm().getSistemaVersanteDetail().post(getRequest());
        BigDecimal idEnteSiam = getForm().getSistemaVersanteDetail().getId_ente_siam().parse();
        try {
            if (idEnteSiam != null) {
                OrgEnteSiamRowBean enteSiam = entiConvenzionatiEjb.getOrgEnteConvenzRowBean(idEnteSiam);
                getForm().getSistemaVersanteDetail().getDs_via_sede_legale().setValue(enteSiam.getDsViaSedeLegale());
                getForm().getSistemaVersanteDetail().getDs_citta_sede_legale()
                        .setValue(enteSiam.getDsCittaSedeLegale());
                getForm().getSistemaVersanteDetail().getCd_cap_sede_legale().setValue(enteSiam.getCdCapSedeLegale());
            } else {
                getForm().getSistemaVersanteDetail().getDs_via_sede_legale().setValue("");
                getForm().getSistemaVersanteDetail().getDs_citta_sede_legale().setValue("");
                getForm().getSistemaVersanteDetail().getCd_cap_sede_legale().setValue("");
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
        return getForm().getSistemaVersanteDetail().asJSON();
    }

    @Override
    public JSONObject triggerSistemaVersanteDetailDt_ini_valOnTrigger() throws EMFError {
        getForm().getSistemaVersanteDetail().post(getRequest());
        Date dtIniVal = getForm().getSistemaVersanteDetail().getDt_ini_val().parse();
        Date dtFineVal = getForm().getSistemaVersanteDetail().getDt_fine_val().parse();
        Date dataOdierna = DateUtil.getDataOdiernaNoTime();
        if (dtIniVal != null && dtFineVal != null) {
            if (dtIniVal.compareTo(dataOdierna) <= 0 && dtFineVal.compareTo(dataOdierna) >= 0) {
                getForm().getSistemaVersanteDetail().getFl_cessato().setValue("0");
            } else {
                getForm().getSistemaVersanteDetail().getFl_cessato().setValue("1");
            }
        } else {
            Message msg = new Message(Message.MessageLevel.ERR,
                    "Attenzione: data inizio e/o data fine validità non valorizzate per poter valorizzare in tempo reale il flag cessato");
            return getForm().getSistemaVersanteDetail().asJSON(msg);
        }
        return getForm().getSistemaVersanteDetail().asJSON();
    }

    @Override
    public JSONObject triggerSistemaVersanteDetailDt_fine_valOnTrigger() throws EMFError {
        return triggerSistemaVersanteDetailDt_ini_valOnTrigger();
    }

}
