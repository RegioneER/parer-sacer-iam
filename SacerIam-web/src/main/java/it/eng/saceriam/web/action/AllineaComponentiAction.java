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

import static it.eng.spagoCore.configuration.ConfigProperties.StandardProperty.PROFILER_APP_CHARSET;
import static it.eng.spagoCore.configuration.ConfigProperties.StandardProperty.PROFILER_APP_MAX_FILE_SIZE;
import static it.eng.spagoCore.configuration.ConfigProperties.StandardProperty.PROFILER_APP_MAX_REQUEST_SIZE;
import static it.eng.spagoCore.configuration.ConfigProperties.StandardProperty.PROFILER_APP_UPLOAD_DIR;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.AllineaComponentiAbstractAction;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.web.ejb.AuthEjb;
import it.eng.saceriam.web.helper.AllineaComponentiHelper;
import it.eng.saceriam.ws.dto.FileBinario;
import it.eng.spagoCore.configuration.ConfigSingleton;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoIFace.Values;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;
import it.eng.spagoLite.form.fields.SingleValueField;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;

public class AllineaComponentiAction extends AllineaComponentiAbstractAction {

    private static final Logger log = LoggerFactory.getLogger(AllineaComponentiAction.class);

    @EJB(mappedName = "java:app/SacerIam-ejb/AuthEjb")
    private AuthEjb auth;
    @EJB(mappedName = "java:app/SacerIam-ejb/AllineaComponentiHelper")
    private AllineaComponentiHelper allineaComponentiHelper;

    /**
     * Esegue l'upload dei file forniti dalla form.
     *
     * Dato che la form è multipart, il framework non chiama il metodo invioDati() relativo al bottone omonimo. Per
     * questo motivo in questo metodo eseguiamo anche il codice relativo al bottone (controllo e parsing dei dati).
     *
     * @throws EMFError
     *             errore generico
     */
    @Override
    public void process() throws EMFError {
        boolean isMultipart = ServletFileUpload.isMultipartContent(getRequest());
        int sizeMb = 1024 * 1024;

        if (isMultipart) {
            try {
                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();

                // maximum size that will be stored in memory
                factory.setSizeThreshold(sizeMb);
                // the location for saving data that is larger than
                factory.setRepository(
                        new File(ConfigSingleton.getInstance().getStringValue(PROFILER_APP_UPLOAD_DIR.name())));
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                // maximum size before a FileUploadException will be thrown
                upload.setSizeMax(ConfigSingleton.getInstance().getLongValue(PROFILER_APP_MAX_REQUEST_SIZE.name()));
                upload.setFileSizeMax(ConfigSingleton.getInstance().getLongValue(PROFILER_APP_MAX_FILE_SIZE.name()));
                List fileItems = upload.parseRequest(getRequest());
                try {
                    invioDati(fileItems, fileItems.iterator(),
                            ConfigSingleton.getInstance().getStringValue(PROFILER_APP_CHARSET.name()));
                } catch (EMFError e) {
                    getMessageBox().addError(e.getDescription());
                    getMessageBox().setViewMode(ViewMode.plain);
                } catch (SQLException | IOException e) {
                    getMessageBox().addError(e.getMessage());
                    getMessageBox().setViewMode(ViewMode.plain);
                }
                forwardToPublisher(Application.Publisher.ALLINEA_COMPONENTI);
            } catch (Exception ex) {
                log.error("Eccezione nell'upload dei file", ex);
                getMessageBox().addError("Eccezione nell'upload dei file", ex);
            }
        }

    }

    @Override
    public String getControllerName() {
        return Application.Actions.ALLINEA_COMPONENTI;
    }

    @Override
    protected String getDefaultPublsherName() {
        // return Application.Publisher.AMMINISTRAZIONE_CONFIG_LIST;
        return Application.Actions.ALLINEA_COMPONENTI;
    }

    /**
     * Apre la pagina di allineamento componenti applicazione
     *
     * @throws EMFError
     *             errore generico
     */
    @Secure(action = "Menu.AmministrazioneSistema.AllineaComponentiApplicazione")
    public void allineaComponentiApplicazione() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneSistema.AllineaComponentiApplicazione");

        // Inizializzo la combo coi valori delle applicazioni gestite
        getForm().getFiltriAllineaComponenti().clear();
        getForm().getFiltriAllineaComponenti().setEditMode();
        AplApplicTableBean applicTableBean = allineaComponentiHelper.getAplApplicTableBean();
        DecodeMap mappaApplicazioni = new DecodeMap();
        mappaApplicazioni.populatedMap(applicTableBean, "nm_applic", "nm_applic");
        getForm().getFiltriAllineaComponenti().getNm_applic().setDecodeMap(mappaApplicazioni);

        getForm().getBottoni().getAttivaAllineamento().setEditMode();
        forwardToPublisher(Application.Publisher.ALLINEA_COMPONENTI);
    }

    /**
     * Controlla i file di cui si è eseguito l'upload e verifica che siano nel formato atteso. Una volta verificato
     * esegue il parsing e inserisce i dati su db.
     *
     * @param fileItems
     *            la lista dei file
     * @param tmpIterator
     *            l'iterator sulla lista
     * @param charset
     *            il charset da utilizzare per il parsing
     * 
     * @throws EMFError
     *             errore generico
     * @throws SQLException
     *             errore esecuzione query
     * @throws EJBTransactionRolledbackException
     *             errore generico
     * @throws Exception
     *             errore generico
     */
    public void invioDati(List fileItems, Iterator tmpIterator, String charset)
            throws EMFError, SQLException, EJBTransactionRolledbackException, Exception {

        DiskFileItem tmpFileItem;
        FileBinario tmpFileBinario;
        List<FileBinario> listaFiles = new ArrayList<>();

        try {
            // Verifica strutturale dei campi di tipo file e memorizzazione degli stessi nella sessione finta
            String nmApplicazione;
            String cdVersioneOld;
            String cdVersioneNew;
            while (tmpIterator.hasNext()) {
                tmpFileItem = (DiskFileItem) tmpIterator.next();
                // Se l'item NON è un campo tradizionale
                // ma è di tipo multipart
                if (!tmpFileItem.isFormField()) {
                    // Ricavo tutti i dati del file binario
                    long sizeInBytes = tmpFileItem.getSize();
                    String fileName = tmpFileItem.getName();
                    if (sizeInBytes > 0 && fileName.length() > 0) {
                        tmpFileBinario = new FileBinario();
                        tmpFileBinario.setId(tmpFileItem.getFieldName());
                        if (tmpFileItem.isInMemory()) {
                            tmpFileBinario.setInMemoria(true);
                            tmpFileBinario.setDati(tmpFileItem.get());
                            tmpFileBinario.setDimensione(sizeInBytes);
                        } else {
                            tmpFileBinario.setInMemoria(false);
                            tmpFileBinario.setFileSuDisco(tmpFileItem.getStoreLocation());
                            tmpFileBinario.setDimensione(sizeInBytes);
                        }
                        listaFiles.add(tmpFileBinario);
                    }
                } else {
                    // Altrimenti prendi il valore del campo (dunque NON gestisco in maniera
                    // "tradizionale" attraverso il meccanismo di post(getRequest())
                    if (!tmpFileItem.getFieldName().startsWith(Values.OPERATION)) {
                        SingleValueField field = ((SingleValueField) getForm().getFiltriAllineaComponenti()
                                .getComponent(tmpFileItem.getFieldName()));
                        field.setValue(tmpFileItem.getString());
                    }
                }
            }

            nmApplicazione = getForm().getFiltriAllineaComponenti().getNm_applic().parse();
            cdVersioneOld = getForm().getFiltriAllineaComponenti().getCd_versione_comp().parse();
            cdVersioneNew = getForm().getFiltriAllineaComponenti().getCd_versione_comp_new().parse();

            if (StringUtils.isBlank(nmApplicazione)) {
                getMessageBox().addWarning("Applicazione non selezionata");
                getMessageBox().setViewMode(ViewMode.plain);
            } else if (StringUtils.isBlank(cdVersioneOld)) {
                getMessageBox().addWarning("Versione corrente componenti inesistente");
                getMessageBox().setViewMode(ViewMode.plain);
            } else if (StringUtils.isBlank(cdVersioneNew)) {
                getMessageBox().addWarning("Inserire nuova versione componenti");
                getMessageBox().setViewMode(ViewMode.plain);
            } else if (listaFiles.size() != 4) {
                getMessageBox().addWarning("Non sono stati inviati tutti i file necessari per l'operazione");
                getMessageBox().setViewMode(ViewMode.plain);
            } else {
                // Determino eventuali azioni e pagine non più presenti nel CSV come condizione per proseguire
                String controlliCancellazione = auth.checkAzioniPagineDaCancellare(listaFiles, charset, nmApplicazione);
                // Object[] controlliCancellazione = auth.checkAzioniPagineDaCancellare(listaFiles, charset,
                // nmApplicazione);

                if (controlliCancellazione != null) {
                    Object[] attributiAllineamentoComponenti = new Object[5];
                    attributiAllineamentoComponenti[0] = listaFiles;
                    attributiAllineamentoComponenti[1] = charset;
                    attributiAllineamentoComponenti[2] = nmApplicazione;
                    attributiAllineamentoComponenti[3] = cdVersioneNew;
                    attributiAllineamentoComponenti[4] = fileItems;
                    getSession().setAttribute("attributiAllineamentoComponenti", attributiAllineamentoComponenti);
                    getRequest().setAttribute("customBoxAllineamentoComponenti", controlliCancellazione);
                    // getRequest().setAttribute("listaPagine", controlliCancellazione[0]);
                    // getRequest().setAttribute("listaAzioni", controlliCancellazione[1]);

                } else {
                    eseguiAllineamentoComponenti(listaFiles, charset, nmApplicazione, cdVersioneNew, fileItems);
                }

                // // Leggi e parsa tutti e 4 i file CSV, salvandoli nelle relative tabelle
                // boolean serviziModificati = auth.readCsvFiles(listaFiles, charset, nmApplicazione);
                //
                // // Allineo le autorizzazioni dei ruoli relative a entrate a menù, pagine, azioni e servizi
                // auth.authorizeApplication(nmApplicazione, cdVersioneNew, serviziModificati);
                // getMessageBox().addInfo("Componenti applicazione " + nmApplicazione + " allineati con successo");
                // getMessageBox().setViewMode(ViewMode.plain);
                // getForm().getFiltriAllineaComponenti().clear();
            }
            // tmpIterator = fileItems.iterator();
            // while (tmpIterator.hasNext()) {
            // tmpFileItem = (DiskFileItem) tmpIterator.next();
            // tmpFileItem.delete();
            // }
            //
            // getSession().removeAttribute("uploadDir");
            // getSession().removeAttribute("maxRequestSize");
            // getSession().removeAttribute("maxFileSize");
            // getSession().removeAttribute("charset");

        } catch (IOException ex) {
            log.error("Errore nella lettura dei file: " + ex.getMessage(), ex);
            throw new EMFError(EMFError.ERROR, "Eccezione in lettura dei file: " + ex.getMessage(), ex);
        } catch (ParerInternalError ex) {
            getMessageBox().addError(ex.getDescription());
        }
    }

    private void eseguiAllineamentoComponenti(List<FileBinario> listaFiles, String charset, String nmApplicazione,
            String cdVersioneNew, List fileItems) throws Exception {
        // Leggi e parsa tutti e 4 i file CSV, salvandoli nelle relative tabelle
        boolean serviziModificati = auth.readCsvFiles(listaFiles, charset, nmApplicazione);

        // Allineo le autorizzazioni dei ruoli relative a entrate a menù, pagine, azioni e servizi
        auth.authorizeApplication(nmApplicazione, cdVersioneNew, serviziModificati);
        getMessageBox().addInfo("Componenti applicazione " + nmApplicazione + " allineati con successo");
        getMessageBox().setViewMode(ViewMode.plain);
        getForm().getFiltriAllineaComponenti().clear();

        cleanAll(fileItems);

        // tmpIterator = fileItems.iterator();
        // while (tmpIterator.hasNext()) {
        // tmpFileItem = (DiskFileItem) tmpIterator.next();
        // tmpFileItem.delete();
        // }
        //
        // getSession().removeAttribute("uploadDir");
        // getSession().removeAttribute("maxRequestSize");
        // getSession().removeAttribute("maxFileSize");
        // getSession().removeAttribute("charset");
    }

    private void cleanAll(List fileItems) {
        Iterator tmpIterator = fileItems.iterator();
        while (tmpIterator.hasNext()) {
            DiskFileItem tmpFileItem = (DiskFileItem) tmpIterator.next();
            tmpFileItem.delete();
        }

        getSession().removeAttribute("uploadDir");
        getSession().removeAttribute("maxRequestSize");
        getSession().removeAttribute("maxFileSize");
        getSession().removeAttribute("charset");
        getSession().removeAttribute("attributiAllineamentoComponenti");
    }

    public void confermaAllineamentoComponenti() {
        if (getSession().getAttribute("attributiAllineamentoComponenti") != null) {
            Object[] attributiAllineamentoComponenti = (Object[]) getSession()
                    .getAttribute("attributiAllineamentoComponenti");
            List<FileBinario> listaFiles = (List<FileBinario>) attributiAllineamentoComponenti[0];
            String charset = (String) attributiAllineamentoComponenti[1];
            String nmApplicazione = (String) attributiAllineamentoComponenti[2];
            String cdVersioneNew = (String) attributiAllineamentoComponenti[3];
            List fileItems = (List) attributiAllineamentoComponenti[4];
            try {
                eseguiAllineamentoComponenti(listaFiles, charset, nmApplicazione, cdVersioneNew, fileItems);
            } catch (EMFError ex) {
                log.error(ex.getMessage());
                getMessageBox().addError(ex.getDescription());
            } catch (Exception ex) {
                log.error("Errore nella lettura dei file", ex);
                getMessageBox().addError("Errore nella lettura dei file");
            }
        }
        forwardToPublisher(Application.Publisher.ALLINEA_COMPONENTI);
    }

    public void annullaAllineamentoComponenti() {
        if (getSession().getAttribute("attributiAllineamentoComponenti") != null) {
            Object[] attributiAllineamentoComponenti = (Object[]) getSession()
                    .getAttribute("attributiAllineamentoComponenti");
            List fileItems = (List) attributiAllineamentoComponenti[4];
            cleanAll(fileItems);
        }
        forwardToPublisher(Application.Publisher.ALLINEA_COMPONENTI);
    }

    @Override
    public void attivaAllineamento() throws EMFError {
        // Vatti a cuccare il metodo process() perchè è lui che si occupa
        // dell'invio dati
    }

    @Override
    public void initOnClick() throws EMFError {
    }

    @Override
    public void insertDettaglio() throws EMFError {
    }

    @Override
    public void loadDettaglio() throws EMFError {
    }

    @Override
    public void undoDettaglio() throws EMFError {
    }

    @Override
    public void saveDettaglio() throws EMFError {
    }

    @Override
    public void dettaglioOnClick() throws EMFError {
    }

    @Override
    public void elencoOnClick() throws EMFError {
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
    }

    public void triggerFiltriAllineaComponentiNm_applicOnTrigger() throws EMFError {
        getForm().getFiltriAllineaComponenti().post(getRequest());
        String nmApplic = getForm().getFiltriAllineaComponenti().getNm_applic().parse();
        if (StringUtils.isNotBlank(nmApplic)) {
            AplApplicRowBean aplApplicRowBean = allineaComponentiHelper.getAplApplicRowBean(nmApplic);
            if (aplApplicRowBean != null) {
                getForm().getFiltriAllineaComponenti().getCd_versione_comp()
                        .setValue(aplApplicRowBean.getCdVersioneComp());
            } else {
                getForm().getFiltriAllineaComponenti().getCd_versione_comp().setValue(null);
            }
        } else {
            getForm().getFiltriAllineaComponenti().getCd_versione_comp().setValue(null);
        }
        redirectToAjax(getForm().getFiltriAllineaComponenti().asJSON());
    }
}
