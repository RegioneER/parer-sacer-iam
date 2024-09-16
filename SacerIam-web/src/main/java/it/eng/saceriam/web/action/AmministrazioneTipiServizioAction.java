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

import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.slite.gen.form.GestioneLogEventiForm;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.parer.sacerlog.util.web.SpagoliteLogUtil;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AmministrazioneTipiServizioAbstractAction;
import it.eng.saceriam.slite.gen.form.AmministrazioneTariffariForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneTipiServizioForm;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoServizioRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoServizioTableBean;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.web.util.ComboGetter;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.actions.form.ListAction;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.BaseTableInterface;
import it.eng.spagoLite.form.base.BaseElements.Status;
import it.eng.spagoLite.form.base.BaseForm;
import it.eng.spagoLite.form.fields.SingleValueField;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

/**
 *
 * @author Gilioli_P
 */
@SuppressWarnings({ "unchecked" })
public class AmministrazioneTipiServizioAction extends AmministrazioneTipiServizioAbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(AmministrazioneTipiServizioAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/EntiConvenzionatiEjb")
    private EntiConvenzionatiEjb entiConvenzionatiEjb;
    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    ParamHelper paramHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;

    @Override
    public void initOnClick() throws EMFError {
    }

    @Override
    public void loadDettaglio() throws EMFError {
        try {
            if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
                    || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
                    || getNavigationEvent().equals(ListAction.NE_NEXT)
                    || getNavigationEvent().equals(ListAction.NE_PREV)) {
                if (getTableName().equals(getForm().getListaTipiServizio().getName())) {
                    initTipoServizioDetail();
                    OrgTipoServizioRowBean currentRow = (OrgTipoServizioRowBean) getForm().getListaTipiServizio()
                            .getTable().getCurrentRow();
                    loadDettaglioTipoServizio(currentRow.getIdTipoServizio());
                }
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
    }

    @Override
    public void undoDettaglio() throws EMFError {
        if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO)
                && getForm().getListaTipiServizio().getStatus().equals(Status.update)) {
            OrgTipoServizioRowBean currentRow = (OrgTipoServizioRowBean) getForm().getListaTipiServizio().getTable()
                    .getCurrentRow();
            getForm().getTipoServizioDetail().copyFromBean(currentRow);
            getForm().getTipoServizioDetail().setViewMode();
            getForm().getTipoServizioDetail().setStatus(Status.view);
            getForm().getListaTipiServizio().setStatus(Status.view);
            getForm().getTipoServizioDetail().getLogEventi().setEditMode();
            forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO);
        } else {
            goBack();
        }
    }

    @Override
    public void insertDettaglio() throws EMFError {
        try {
            if (getTableName().equals(getForm().getListaTipiServizio().getName())) {
                // Pulisco i campi
                getForm().getTipoServizioDetail().clear();
                // Inizializzo le combo
                initTipoServizioDetail();
                // Metto in editMode
                getForm().getTipoServizioDetail().setEditMode();
                // Setto tutto in status insert
                getForm().getTipoServizioDetail().setStatus(Status.insert);
                getForm().getListaTipiServizio().setStatus(Status.insert);
                // Rimando alla pagina di dettaglio
                forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO);
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
    }

    @Override
    public void saveDettaglio() throws EMFError {
        if (getTableName().equals(getForm().getListaTipiServizio().getName())
                || getTableName().equals(getForm().getTipoServizioDetail().getName())) {
            saveTipoServizio();
        }
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
        if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
                || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
                || getNavigationEvent().equals(ListAction.NE_NEXT) || getNavigationEvent().equals(ListAction.NE_PREV)) {
            if (getTableName().equals(getForm().getListaTipiServizio().getName())) {
                forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO);
            } else if (getTableName().equals(getForm().getListaTariffeTipoServizio().getName())) {
                redirectToTariffaPage();
            }
        }
    }

    @Override
    public void elencoOnClick() throws EMFError {
        goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.RICERCA_TIPI_SERVIZIO;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
        try {
            if (publisherName.equals(Application.Publisher.RICERCA_TIPI_SERVIZIO)) {
                int rowIndex;
                int pageSize;
                if (getForm().getListaTipiServizio().getTable() != null) {
                    rowIndex = getForm().getListaTipiServizio().getTable().getCurrentRowIndex();
                    pageSize = getForm().getListaTipiServizio().getTable().getPageSize();
                } else {
                    rowIndex = 0;
                    pageSize = 10;
                }
                String cdTipoServizio = getForm().getFiltriTipiServizio().getCd_tipo_servizio().parse();
                String tiClasseTipoServizio = getForm().getFiltriTipiServizio().getTi_classe_tipo_servizio().parse();
                String tipoFatturazione = getForm().getFiltriTipiServizio().getTipo_fatturazione().parse();
                OrgTipoServizioTableBean table = entiConvenzionatiEjb.getOrgTipoServizioTableBean(cdTipoServizio,
                        tiClasseTipoServizio, tipoFatturazione);
                getForm().getListaTipiServizio().setTable(table);
                getForm().getListaTipiServizio().getTable().setPageSize(pageSize);
                getForm().getListaTipiServizio().getTable().setCurrentRowIndex(rowIndex);
            } else if (publisherName.equals(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO)) {
                postLoad();
            }
        } catch (ParerUserError e) {
            getMessageBox().addError(e.getDescription());
            forwardToPublisher(getLastPublisher());
        } catch (EMFError e) {
            logger.error("Errore nel ricaricamento della pagina " + publisherName, e);
            getMessageBox().addError("Errore nel ricaricamento della pagina " + publisherName);
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public String getControllerName() {
        return Application.Actions.AMMINISTRAZIONE_TIPI_SERVIZIO;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione ricerca tipi servizio">
    @Secure(action = "Menu.Fatturazione.GestioneTipoServizio")
    public void ricercaTipiServizioPage() {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.Fatturazione.GestioneTipoServizio");

        getForm().getFiltriTipiServizio().reset();
        getForm().getFiltriTipiServizio().setEditMode();
        getForm().getFiltriTipiServizio().getTi_classe_tipo_servizio()
                .setDecodeMap(ComboGetter.getMappaTiClasseTipoServizio());
        getForm().getFiltriTipiServizio().getTipo_fatturazione().setDecodeMap(ComboGetter.getMappaTipoFatturazione());

        // Azzero la lista risultato
        getForm().getListaTipiServizio().setTable(null);

        forwardToPublisher(Application.Publisher.RICERCA_TIPI_SERVIZIO);
    }

    @Override
    public void ricercaTipiServizio() throws EMFError {
        if (getForm().getFiltriTipiServizio().postAndValidate(getRequest(), getMessageBox())) {
            String cdTipoServizio = getForm().getFiltriTipiServizio().getCd_tipo_servizio().parse();
            String tiClasseTipoServizio = getForm().getFiltriTipiServizio().getTi_classe_tipo_servizio().parse();
            String tipoFatturazione = getForm().getFiltriTipiServizio().getTipo_fatturazione().parse();
            try {
                OrgTipoServizioTableBean table = entiConvenzionatiEjb.getOrgTipoServizioTableBean(cdTipoServizio,
                        tiClasseTipoServizio, tipoFatturazione);
                getForm().getListaTipiServizio().setTable(table);
                getForm().getListaTipiServizio().getTable().setPageSize(10);
                getForm().getListaTipiServizio().getTable().first();
            } catch (ParerUserError pue) {
                getMessageBox().addError(pue.getDescription());
            }
        }
        forwardToPublisher(getLastPublisher());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio tipo accordo">
    private void initTipoServizioDetail() throws ParerUserError {
        getForm().getTipoServizioDetail().getTi_classe_tipo_servizio()
                .setDecodeMap(ComboGetter.getMappaTiClasseTipoServizio());
        getForm().getTipoServizioDetail().getTipo_fatturazione().setDecodeMap(ComboGetter.getMappaTipoFatturazione());
        getForm().getTipoServizioDetail().getFl_tariffa_accordo().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getTipoServizioDetail().getFl_tariffa_annualita().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
    }

    private void loadDettaglioTipoServizio(BigDecimal idTipoServizio) throws ParerUserError, EMFError {
        OrgTipoServizioRowBean detailRow = entiConvenzionatiEjb.getOrgTipoServizioRowBean(idTipoServizio);
        getForm().getTipoServizioDetail().copyFromBean(detailRow);

        getForm().getTipoServizioDetail().setViewMode();
        getForm().getTipoServizioDetail().setStatus(Status.view);
        getForm().getListaTipiServizio().setStatus(Status.view);

        // Popolamento lista tariffe tipo servizio
        getForm().getListaTariffeTipoServizio()
                .setTable(entiConvenzionatiEjb.getOrgTariffaByTipoServizioTableBean(idTipoServizio));
        getForm().getListaTariffeTipoServizio().getTable().setPageSize(10);
        //
        getForm().getListaTariffeAccordiTipoServizio()
                .setTable(entiConvenzionatiEjb.getOrgTariffaAccordoByTipoServizioTableBean(idTipoServizio));
        getForm().getListaTariffeTipoServizio().getTable().setPageSize(10);
        getForm().getListaTariffeTipoServizio().getTable().first();
        //
        getForm().getListaTariffeAnnualitaTipoServizio()
                .setTable(entiConvenzionatiEjb.getOrgTariffaAnnualitaByTipoServizioTableBean(idTipoServizio));
        getForm().getListaTariffeTipoServizio().getTable().setPageSize(10);
        getForm().getListaTariffeTipoServizio().getTable().first();

        getForm().getListaTariffeTipoServizio().getTable().first();
        if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)) {
            getForm().getTipoServizioDetail().getLogEventi().setEditMode();
        } else {
            getForm().getTipoServizioDetail().getLogEventi().setViewMode();
        }
    }

    @Override
    public void deleteListaTipiServizio() throws EMFError {
        BaseRowInterface currentRow = getForm().getListaTipiServizio().getTable().getCurrentRow();
        BigDecimal idTipoServizio = currentRow.getBigDecimal("id_tipo_servizio");
        int riga = getForm().getListaTipiServizio().getTable().getCurrentRowIndex();
        // Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono nel dettaglio
        if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO)) {
            if (!idTipoServizio.equals(getForm().getTipoServizioDetail().getId_tipo_servizio().parse())) {
                getMessageBox().addError("Eccezione imprevista nell'eliminazione del tipo servizio");
            }
        }

        if (!getMessageBox().hasError() && idTipoServizio != null) {
            try {
                /* Controlla che sul tipo accordo */
                if (entiConvenzionatiEjb.checkEsistenzaServiziErogatiPerTipoServizio(idTipoServizio)) {
                    getMessageBox().addError(
                            "Il tipo servizio non può essere eliminato: esistono dei servizi erogati associati ad esso");
                }

                if (!getMessageBox().hasError()) {
                    /*
                     * Codice aggiuntivo per il logging...
                     */
                    LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                            getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
                    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                    if (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
                    } else {
                        AmministrazioneTipiServizioForm form = getForm();
                        param.setNomeAzione(
                                SpagoliteLogUtil.getDetailActionNameDelete(getForm(), form.getListaTipiServizio()));
                    }
                    entiConvenzionatiEjb.deleteOrgTipoServizio(param, idTipoServizio);
                    getForm().getListaTipiServizio().getTable().remove(riga);
                    getMessageBox().addInfo("Tipo servizio eliminato con successo");
                    getMessageBox().setViewMode(ViewMode.plain);
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError("Il tipo servizio non pu\u00F2 essere eliminato: " + ex.getDescription());
            }
        }
        if (!getMessageBox().hasError() && getLastPublisher().equals(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO)) {
            goBack();
        } else {
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public void deleteTipoServizioDetail() throws EMFError {
        deleteListaTipiServizio();
    }

    private void saveTipoServizio() throws EMFError {
        if (getForm().getTipoServizioDetail().postAndValidate(getRequest(), getMessageBox())) {
            try {
                String cdTipoServizio = getForm().getTipoServizioDetail().getCd_tipo_servizio().parse();
                String dsTipoServizio = getForm().getTipoServizioDetail().getDs_tipo_servizio().parse();
                String tiClasseTipoServizio = getForm().getTipoServizioDetail().getTi_classe_tipo_servizio().parse();
                String tipoFatturazione = getForm().getTipoServizioDetail().getTipo_fatturazione().parse();
                String flTariffaAccordo = getForm().getTipoServizioDetail().getFl_tariffa_accordo().parse();
                String flTariffaAnnualita = getForm().getTipoServizioDetail().getFl_tariffa_annualita().parse();

                // // Controllo che il campo Tempistiche di fatturazione sia nella forma corretta gg/mm
                // if (ggFatturazione != null) {
                // Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[012])");
                // Matcher matcher = pattern.matcher(ggFatturazione);
                // if (!matcher.matches()) {
                // getMessageBox().addError(
                // "Le tempistiche di fatturazione devono essere inserite nel formato gg/mm <br/>");
                // }
                // }

                // Controllo che il tipo servizio sia imposto o su accordo o su annualità ma non entrambi
                if (flTariffaAccordo != null && flTariffaAccordo.equals("1") && flTariffaAnnualita != null
                        && flTariffaAnnualita.equals("1")) {
                    getMessageBox().addError(
                            "E' possibile impostare il rimborso costi solo a livello di accordo o a livello di annualità, ma non su entrambi <br/>");
                }

                if (!getMessageBox().hasError()) {
                    /*
                     * Codice aggiuntivo per il logging...
                     */
                    LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                            getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
                    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                    // Salva il tipo servizio
                    if (getForm().getListaTipiServizio().getStatus().equals(Status.insert)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
                        Long idTipoServizio = entiConvenzionatiEjb.saveTipoServizio(param, cdTipoServizio,
                                dsTipoServizio, tiClasseTipoServizio, tipoFatturazione, flTariffaAccordo,
                                flTariffaAnnualita);
                        if (idTipoServizio != null) {
                            getForm().getTipoServizioDetail().getId_tipo_servizio().setValue(idTipoServizio.toString());
                        }
                        OrgTipoServizioRowBean row = new OrgTipoServizioRowBean();
                        getForm().getTipoServizioDetail().copyToBean(row);
                        getForm().getListaTipiServizio().getTable().last();
                        getForm().getListaTipiServizio().getTable().add(row);
                    } else if (getForm().getListaTipiServizio().getStatus().equals(Status.update)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
                        BigDecimal idTipoServizio = getForm().getTipoServizioDetail().getId_tipo_servizio().parse();
                        entiConvenzionatiEjb.saveTipoAccordo(param, idTipoServizio, cdTipoServizio, dsTipoServizio,
                                tiClasseTipoServizio, tipoFatturazione, flTariffaAccordo, flTariffaAnnualita);
                    }
                    BigDecimal idTipoServizio = getForm().getTipoServizioDetail().getId_tipo_servizio().parse();
                    if (idTipoServizio != null) {
                        loadDettaglioTipoServizio(idTipoServizio);
                    }
                    getForm().getTipoServizioDetail().setViewMode();
                    getForm().getListaTipiServizio().setStatus(Status.view);
                    getForm().getTipoServizioDetail().setStatus(Status.view);
                    getMessageBox().addInfo("Tipo servizio salvato con successo");
                    getMessageBox().setViewMode(ViewMode.plain);
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError(ex.getDescription());
            }
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_SERVIZIO);
    }

    @Override
    public void updateListaTipiServizio() throws EMFError {
        getForm().getTipoServizioDetail().setEditMode();
        getForm().getTipoServizioDetail().getLogEventi().setViewMode();
        getForm().getListaTipiServizio().setStatus(Status.update);
        getForm().getTipoServizioDetail().setStatus(Status.update);
    }

    @Override
    public void updateTipoServizioDetail() throws EMFError {
        updateListaTipiServizio();
    }

    private void redirectToTariffaPage() throws EMFError {
        AmministrazioneTariffariForm form = new AmministrazioneTariffariForm();
        form.getListaTariffe().setFilterValidRecords(getForm().getListaTariffeTipoServizio().isFilterValidRecords());
        form.getListaTariffe().setUserOperations(true, false, false, false);
        getSession().setAttribute("isFromTipoServizio", true);
        redirectToPage(Application.Actions.AMMINISTRAZIONE_TARIFFARI, form, form.getListaTariffe().getName(),
                getForm().getListaTariffeTipoServizio().getTable(), getNavigationEvent());
    }

    private void redirectToPage(final String action, BaseForm form, String listToPopulate, BaseTableInterface<?> table,
            String event) throws EMFError {
        ((it.eng.spagoLite.form.list.List<SingleValueField<?>>) form.getComponent(listToPopulate)).setTable(table);
        redirectToAction(action, "?operation=listNavigationOnClick&navigationEvent=" + event + "&table="
                + listToPopulate + "&riga=" + table.getCurrentRowIndex(), form);
    }
    // </editor-fold>

    @Override
    public void logEventi() throws EMFError {
        GestioneLogEventiForm form = new GestioneLogEventiForm();
        form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
        form.getOggettoDetail().getNm_tipo_oggetto().setValue(SacerLogConstants.TIPO_OGGETTO_TIPO_SERVIZIO);
        form.getOggettoDetail().getIdOggetto()
                .setValue(getForm().getTipoServizioDetail().getId_tipo_servizio().getValue());
        redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
                "?operation=inizializzaLogEventi", form);
    }

    @Override
    protected void postLoad() {
        super.postLoad();
        Object ogg = getForm();
        if (ogg instanceof AmministrazioneTipiServizioForm) {
            AmministrazioneTipiServizioForm form = (AmministrazioneTipiServizioForm) ogg;
            if (form.getListaTipiServizio().getStatus().equals(Status.view)) {
                form.getTipoServizioDetail().getLogEventi().setEditMode();
            } else {
                form.getTipoServizioDetail().getLogEventi().setViewMode();
            }
        }
    }

}
