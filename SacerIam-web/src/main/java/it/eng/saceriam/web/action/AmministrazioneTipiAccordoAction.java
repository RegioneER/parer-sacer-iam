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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import it.eng.saceriam.slite.gen.action.AmministrazioneTipiAccordoAbstractAction;
import it.eng.saceriam.slite.gen.form.AmministrazioneTariffariForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneTipiAccordoForm;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoAccordoTableBean;
import it.eng.saceriam.util.DateUtil;
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
public class AmministrazioneTipiAccordoAction extends AmministrazioneTipiAccordoAbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(AmministrazioneTipiAccordoAction.class);

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
                if (getTableName().equals(getForm().getListaTipiAccordo().getName())) {
                    initTipoAccordoDetail();
                    OrgTipoAccordoRowBean currentRow = (OrgTipoAccordoRowBean) getForm().getListaTipiAccordo()
                            .getTable().getCurrentRow();
                    loadDettaglioTipoAccordo(currentRow.getIdTipoAccordo());
                }
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
    }

    @Override
    public void undoDettaglio() throws EMFError {
        if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_TIPO_ACCORDO)
                && getForm().getListaTipiAccordo().getStatus().equals(Status.update)) {
            OrgTipoAccordoRowBean currentRow = (OrgTipoAccordoRowBean) getForm().getListaTipiAccordo().getTable()
                    .getCurrentRow();
            getForm().getTipoAccordoDetail().copyFromBean(currentRow);
            getForm().getTipoAccordoDetail().setViewMode();
            getForm().getTipoAccordoDetail().setStatus(Status.view);
            getForm().getTipoAccordoDetail().getLogEventi().setEditMode();
            getForm().getListaTipiAccordo().setStatus(Status.view);
            forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_ACCORDO);
        } else {
            goBack();
        }
    }

    @Override
    public void insertDettaglio() throws EMFError {
        try {
            if (getTableName().equals(getForm().getListaTipiAccordo().getName())) {
                // Pulisco i campi
                getForm().getTipoAccordoDetail().clear();
                // Inizializzo le combo
                initTipoAccordoDetail();
                // Precompilo la data inizio con la data corrente
                DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
                getForm().getTipoAccordoDetail().getDt_istituz()
                        .setValue(formato.format(Calendar.getInstance().getTime()));
                // Metto in editMode
                getForm().getTipoAccordoDetail().setEditMode();
                // Setto tutto in status insert
                getForm().getTipoAccordoDetail().setStatus(Status.insert);
                getForm().getListaTipiAccordo().setStatus(Status.insert);
                // Rimando alla pagina di dettaglio
                forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_ACCORDO);
            } else if (getTableName().equals(getForm().getListaTariffario().getName())) {
                getSession().setAttribute("idTipoAccordo",
                        getForm().getTipoAccordoDetail().getId_tipo_accordo().parse());
                redirectToTariffariPage();
            }
        } catch (ParerUserError ex) {
            getMessageBox().addError(ex.getDescription());
        }
    }

    @Override
    public void saveDettaglio() throws EMFError {
        if (getTableName().equals(getForm().getListaTipiAccordo().getName())
                || getTableName().equals(getForm().getTipoAccordoDetail().getName())) {
            saveTipoAccordo();
        }
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
        if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)
                || getNavigationEvent().equals(ListAction.NE_DETTAGLIO_UPDATE)
                || getNavigationEvent().equals(ListAction.NE_NEXT) || getNavigationEvent().equals(ListAction.NE_PREV)) {
            if (getTableName().equals(getForm().getListaTipiAccordo().getName())) {
                forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_ACCORDO);
            }
        }

        if (getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW) || getNavigationEvent().equals(ListAction.NE_NEXT)
                || getNavigationEvent().equals(ListAction.NE_PREV)) {
            // Tariffario: passa all'altra action
            if (getTableName().equals(getForm().getListaTariffario().getName())) {
                redirectToTariffariPage();
            }
        }

    }

    private void redirectToTariffariPage() throws EMFError {
        AmministrazioneTariffariForm form = new AmministrazioneTariffariForm();
        form.getListaTariffario().setFilterValidRecords(getForm().getListaTariffario().isFilterValidRecords());
        redirectToPage(Application.Actions.AMMINISTRAZIONE_TARIFFARI, form, form.getListaTariffario().getName(),
                getForm().getListaTariffario().getTable(), getNavigationEvent());
        getSession().setAttribute("daTipoAccordo", true);
    }

    private void redirectToPage(final String action, BaseForm form, String listToPopulate, BaseTableInterface<?> table,
            String event) throws EMFError {
        ((it.eng.spagoLite.form.list.List<SingleValueField<?>>) form.getComponent(listToPopulate)).setTable(table);
        redirectToAction(action, "?operation=listNavigationOnClick&navigationEvent=" + event + "&table="
                + listToPopulate + "&riga=" + table.getCurrentRowIndex(), form);
    }

    @Override
    public void elencoOnClick() throws EMFError {
        goBack();
    }

    @Override
    protected String getDefaultPublsherName() {
        return Application.Publisher.RICERCA_TIPI_ACCORDO;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
        try {
            if (publisherName.equals(Application.Publisher.RICERCA_TIPI_ACCORDO)) {
                int rowIndex;
                int pageSize;
                if (getForm().getListaTipiAccordo().getTable() != null) {
                    rowIndex = getForm().getListaTipiAccordo().getTable().getCurrentRowIndex();
                    pageSize = getForm().getListaTipiAccordo().getTable().getPageSize();
                } else {
                    rowIndex = 0;
                    pageSize = 10;
                }
                String cdTipoAccordo = getForm().getFiltriTipiAccordi().getCd_tipo_accordo().parse();
                String dsTipoAccordo = getForm().getFiltriTipiAccordi().getDs_tipo_accordo().parse();
                String flPagamento = getForm().getFiltriTipiAccordi().getFl_pagamento().parse();
                String isAttivo = getForm().getFiltriTipiAccordi().getIs_attivo().parse();
                OrgTipoAccordoTableBean table = entiConvenzionatiEjb.getOrgTipoAccordoTableBean(cdTipoAccordo,
                        dsTipoAccordo, flPagamento, isAttivo);
                getForm().getListaTipiAccordo().setTable(table);
                getForm().getListaTipiAccordo().getTable().setPageSize(pageSize);
                getForm().getListaTipiAccordo().getTable().setCurrentRowIndex(rowIndex);
            } else if (publisherName.equals(Application.Publisher.DETTAGLIO_TIPO_ACCORDO)) {
                BigDecimal idTipoAccordo = getForm().getTipoAccordoDetail().getId_tipo_accordo().parse();
                loadDettaglioTipoAccordo(idTipoAccordo);
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
        return Application.Actions.AMMINISTRAZIONE_TIPI_ACCORDO;
    }

    // <editor-fold defaultstate="collapsed" desc="Gestione ricerca tipi accordo">
    @Secure(action = "Menu.Fatturazione.GestioneTipoAccordo")
    public void ricercaTipiAccordoPage() {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.Fatturazione.GestioneTipoAccordo");

        getForm().getFiltriTipiAccordi().reset();
        getForm().getFiltriTipiAccordi().setEditMode();
        getForm().getFiltriTipiAccordi().getFl_pagamento().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getFiltriTipiAccordi().getIs_attivo().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());

        // Azzero la lista risultato
        getForm().getListaTipiAccordo().setTable(null);

        forwardToPublisher(Application.Publisher.RICERCA_TIPI_ACCORDO);
    }

    @Override
    public void ricercaTipiAccordi() throws EMFError {
        if (getForm().getFiltriTipiAccordi().postAndValidate(getRequest(), getMessageBox())) {
            String cdTipoAccordo = getForm().getFiltriTipiAccordi().getCd_tipo_accordo().parse();
            String dsTipoAccordo = getForm().getFiltriTipiAccordi().getDs_tipo_accordo().parse();
            String flPagamento = getForm().getFiltriTipiAccordi().getFl_pagamento().parse();
            String isAttivo = getForm().getFiltriTipiAccordi().getIs_attivo().parse();
            try {
                OrgTipoAccordoTableBean table = entiConvenzionatiEjb.getOrgTipoAccordoTableBean(cdTipoAccordo,
                        dsTipoAccordo, flPagamento, isAttivo);
                getForm().getListaTipiAccordo().setTable(table);
                getForm().getListaTipiAccordo().getTable().setPageSize(10);
                getForm().getListaTipiAccordo().getTable().first();
            } catch (ParerUserError pue) {
                getMessageBox().addError(pue.getDescription());
            }
        }
        forwardToPublisher(getLastPublisher());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Gestione dettaglio tipo accordo">
    private void initTipoAccordoDetail() throws ParerUserError {
        getForm().getTipoAccordoDetail().getFl_pagamento().setDecodeMap(ComboGetter.getMappaGenericFlagSiNo());
        getForm().getTipoAccordoDetail().getCd_algo_tariffario().setDecodeMap(ComboGetter.getMappaCdAlgoTariffario());
        getForm().getTipoAccordoDetail().getCd_algo_tariffario().setValue("NO_CLASSE_ENTE");
    }

    private void loadDettaglioTipoAccordo(BigDecimal idTipoAccordo) throws ParerUserError, EMFError {
        OrgTipoAccordoRowBean detailRow = entiConvenzionatiEjb.getOrgTipoAccordoRowBean(idTipoAccordo);
        getForm().getTipoAccordoDetail().copyFromBean(detailRow);

        getForm().getTipoAccordoDetail().setViewMode();
        if (getNavigationEvent() != null && getNavigationEvent().equals(ListAction.NE_DETTAGLIO_VIEW)) {
            getForm().getTipoAccordoDetail().getLogEventi().setEditMode();
        } else {
            getForm().getTipoAccordoDetail().getLogEventi().setViewMode();
        }
        getForm().getTipoAccordoDetail().setStatus(Status.view);
        getForm().getListaTipiAccordo().setStatus(Status.view);

        // Popolamento lista tariffari ammessi
        getForm().getListaTariffario().setTable(entiConvenzionatiEjb.getOrgTariffarioTableBean(idTipoAccordo));
        getForm().getListaTariffario().getTable().setPageSize(10);
        getForm().getListaTariffario().getTable().first();
    }

    @Override
    public void deleteListaTipiAccordo() throws EMFError {
        BaseRowInterface currentRow = getForm().getListaTipiAccordo().getTable().getCurrentRow();
        BigDecimal idTipoAccordo = currentRow.getBigDecimal("id_tipo_accordo");
        int riga = getForm().getListaTipiAccordo().getTable().getCurrentRowIndex();
        // Eseguo giusto un controllo per verificare che io stia prendendo la riga giusta se sono nel dettaglio
        if (getLastPublisher().equals(Application.Publisher.DETTAGLIO_TIPO_ACCORDO)) {
            if (!idTipoAccordo.equals(getForm().getTipoAccordoDetail().getId_tipo_accordo().parse())) {
                getMessageBox().addError("Eccezione imprevista nell'eliminazione del tipo accordo");
            }
        }

        if (!getMessageBox().hasError() && idTipoAccordo != null) {
            try {
                /* Controlla che sul tipo accordo */
                if (entiConvenzionatiEjb.checkEsistenzaAccordiPerTipoAccordo(idTipoAccordo)) {
                    getMessageBox().addError(
                            "Il tipo accordo non può essere eliminato: esistono degli accordi definiti su di esso");
                }

                if (!getMessageBox().hasError()) {
                    /*
                     * Codice aggiuntivo per il logging...
                     */
                    LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                            getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
                    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                    if (param.getNomePagina().equalsIgnoreCase(Application.Publisher.DETTAGLIO_TIPO_ACCORDO)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarDelete());
                    } else {
                        AmministrazioneTipiAccordoForm form = getForm();
                        param.setNomeAzione(
                                SpagoliteLogUtil.getDetailActionNameDelete(form, form.getListaTipiAccordo()));
                    }
                    entiConvenzionatiEjb.deleteOrgTipoAccordo(param, idTipoAccordo);
                    getForm().getListaTipiAccordo().getTable().remove(riga);
                    getMessageBox().addInfo("Tipo accordo eliminato con successo");
                    getMessageBox().setViewMode(ViewMode.plain);
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError("Il tipo accordo non pu\u00F2 essere eliminato: " + ex.getDescription());
            }
        }
        if (!getMessageBox().hasError() && getLastPublisher().equals(Application.Publisher.DETTAGLIO_TIPO_ACCORDO)) {
            goBack();
        } else {
            forwardToPublisher(getLastPublisher());
        }
    }

    @Override
    public void deleteTipoAccordoDetail() throws EMFError {
        deleteListaTipiAccordo();
    }

    private void saveTipoAccordo() throws EMFError {
        if (getForm().getTipoAccordoDetail().postAndValidate(getRequest(), getMessageBox())) {
            try {
                String cdTipoAccordo = getForm().getTipoAccordoDetail().getCd_tipo_accordo().parse();
                String dsTipoAccordo = getForm().getTipoAccordoDetail().getDs_tipo_accordo().parse();
                String flPagamento = getForm().getTipoAccordoDetail().getFl_pagamento().parse();
                String cdAlgoTariffario = getForm().getTipoAccordoDetail().getCd_algo_tariffario().parse();
                Date dtIniVal = getForm().getTipoAccordoDetail().getDt_istituz().parse();
                Date dtFineVal = getForm().getTipoAccordoDetail().getDt_soppres().parse();

                // Se la data fine val è nulla si assume valore 31/12/2444
                if (dtFineVal == null) {
                    SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH);
                    Calendar cal = Calendar.getInstance();
                    cal.set(2444, Calendar.DECEMBER, 31);
                    dtFineVal = cal.getTime();
                    getForm().getTipoAccordoDetail().getDt_soppres().setValue(df.format(dtFineVal));
                }

                if (dtIniVal.after(dtFineVal)) {
                    getMessageBox().addError("Attenzione: data di inizio validità superiore a data di fine validità");
                }

                if (!getMessageBox().hasError()) {
                    // Salva il tipo accordo
                    /*
                     * Codice aggiuntivo per il logging...
                     */
                    LogParam param = SpagoliteLogUtil.getLogParam(paramHelper.getParamApplicApplicationName(),
                            getUser().getUsername(), SpagoliteLogUtil.getPageName(this));
                    param.setTransactionLogContext(sacerLogEjb.getNewTransactionLogContext());
                    if (getForm().getListaTipiAccordo().getStatus().equals(Status.insert)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarInsert());
                        Long idTipoAccordo = entiConvenzionatiEjb.saveTipoAccordo(param, cdTipoAccordo, dsTipoAccordo,
                                flPagamento, cdAlgoTariffario, dtIniVal, dtFineVal);
                        if (idTipoAccordo != null) {
                            getForm().getTipoAccordoDetail().getId_tipo_accordo().setValue(idTipoAccordo.toString());
                        }
                        OrgTipoAccordoRowBean row = new OrgTipoAccordoRowBean();
                        getForm().getTipoAccordoDetail().copyToBean(row);
                        getForm().getListaTipiAccordo().getTable().last();
                        getForm().getListaTipiAccordo().getTable().add(row);
                    } else if (getForm().getListaTipiAccordo().getStatus().equals(Status.update)) {
                        param.setNomeAzione(SpagoliteLogUtil.getToolbarUpdate());
                        BigDecimal idTipoAccordo = getForm().getTipoAccordoDetail().getId_tipo_accordo().parse();
                        entiConvenzionatiEjb.saveTipoAccordo(param, idTipoAccordo, cdTipoAccordo, dsTipoAccordo,
                                flPagamento, cdAlgoTariffario, dtIniVal, dtFineVal);
                    }
                    BigDecimal idTipoAccordo = getForm().getTipoAccordoDetail().getId_tipo_accordo().parse();
                    if (idTipoAccordo != null) {
                        loadDettaglioTipoAccordo(idTipoAccordo);
                    }
                    getForm().getTipoAccordoDetail().setViewMode();
                    getForm().getListaTipiAccordo().setStatus(Status.view);
                    getForm().getTipoAccordoDetail().setStatus(Status.view);
                    getMessageBox().addInfo("Tipo accordo salvato con successo");
                    getMessageBox().setViewMode(ViewMode.plain);
                }
            } catch (ParerUserError ex) {
                getMessageBox().addError(ex.getDescription());
            }
        }
        forwardToPublisher(Application.Publisher.DETTAGLIO_TIPO_ACCORDO);
    }

    @Override
    public void updateListaTipiAccordo() throws EMFError {
        getForm().getTipoAccordoDetail().setEditMode();
        getForm().getListaTipiAccordo().setStatus(Status.update);
        getForm().getTipoAccordoDetail().getLogEventi().setViewMode();
        getForm().getTipoAccordoDetail().setStatus(Status.update);
    }

    @Override
    public void updateTipoAccordoDetail() throws EMFError {
        updateListaTipiAccordo();
    }

    @Override
    public void updateListaTariffario() throws EMFError {
        redirectToTariffariPage();
    }

    @Override
    public void deleteListaTariffario() throws EMFError {
        redirectToTariffariPage();
    }
    // </editor-fold>

    @Override
    public void logEventi() throws EMFError {
        GestioneLogEventiForm form = new GestioneLogEventiForm();
        form.getOggettoDetail().getNmApp().setValue(paramHelper.getParamApplicApplicationName());
        form.getOggettoDetail().getNm_tipo_oggetto().setValue(SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO);
        form.getOggettoDetail().getIdOggetto()
                .setValue(getForm().getTipoAccordoDetail().getId_tipo_accordo().getValue());
        redirectToAction(it.eng.parer.sacerlog.slite.gen.Application.Actions.GESTIONE_LOG_EVENTI,
                "?operation=inizializzaLogEventi", form);
    }

    @Override
    protected void postLoad() {
        super.postLoad();
        Object ogg = getForm();
        if (ogg instanceof AmministrazioneTipiAccordoForm) {
            AmministrazioneTipiAccordoForm form = (AmministrazioneTipiAccordoForm) ogg;
            if (form.getListaTipiAccordo().getStatus().equals(Status.view)) {
                form.getTipoAccordoDetail().getLogEventi().setEditMode();
            } else {
                form.getTipoAccordoDetail().getLogEventi().setViewMode();
            }
        }
    }

}
