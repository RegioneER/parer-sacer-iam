package it.eng.saceriam.web.action;

import it.eng.parer.jboss.timer.service.JbossTimerEjb;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.Application;
import it.eng.saceriam.slite.gen.action.GestioneJobAbstractAction;
import it.eng.saceriam.slite.gen.viewbean.LogVVisLastSchedRowBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.web.helper.GestioneJobHelper;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.form.fields.impl.Button;
import it.eng.spagoLite.form.fields.impl.CheckBox;
import it.eng.spagoLite.form.fields.impl.Input;
import it.eng.spagoLite.message.Message;
import it.eng.spagoLite.message.Message.MessageLevel;
import it.eng.spagoLite.message.MessageBox.ViewMode;
import it.eng.spagoLite.security.Secure;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gilioli_P
 */
public class GestioneJobAction extends GestioneJobAbstractAction {

    @EJB(mappedName = "java:app/SacerIam-ejb/GestioneJobHelper")
    private GestioneJobHelper gestioneJobHelper;

    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    private ParamHelper paramHelper;

    @EJB(mappedName = "java:app/JbossTimerWrapper-ejb/JbossTimerEjb")
    private JbossTimerEjb jbossTimerEjb;

    private static final Logger LOG = LoggerFactory.getLogger(GestioneJobAction.class);

    private enum OPERAZIONE {
        START("lancio il timer"), ESECUZIONE_SINGOLA("esecuzione singola"), STOP("stop");

        protected String desc;

        OPERAZIONE(String desc) {
            this.desc = desc;
        }

        public String description() {
            return desc;
        }
    };

    @Secure(action = "Menu.AmministrazioneSistema.GestioneJob")
    @Override
    public void initOnClick() throws EMFError {
        getUser().getMenu().reset();
        getUser().getMenu().select("Menu.AmministrazioneSistema.GestioneJob");

        // <editor-fold defaultstate="collapsed" desc="UI Gestione job di replica utenti">
        Timestamp dataAttivazioneJob = getActivationDateJob(Constants.NomiJob.REPLICA_UTENTI.name());
        StatoJob replicaUtenti = new StatoJob(Constants.NomiJob.REPLICA_UTENTI.name(),
                getForm().getReplicaUtenti().getFl_data_accurata(),
                getForm().getReplicaUtenti().getStartReplicaUtenti(),
                getForm().getReplicaUtenti().getStartOnceReplicaUtenti(),
                getForm().getReplicaUtenti().getStopReplicaUtenti(),
                getForm().getReplicaUtenti().getDt_prossima_attivazione(), getForm().getReplicaUtenti().getAttivo(),
                getForm().getReplicaUtenti().getDt_reg_log_job_ini(), dataAttivazioneJob);

        gestisciStatoJob(replicaUtenti);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="UI Gestione job di scadenza fatture">
        dataAttivazioneJob = getActivationDateJob(Constants.NomiJob.SCADENZA_FATTURE.name());
        StatoJob scadenzaFatture = new StatoJob(Constants.NomiJob.SCADENZA_FATTURE.name(),
                getForm().getScadenzaFatture().getFl_data_accurata(),
                getForm().getScadenzaFatture().getStartScadenzaFatture(),
                getForm().getScadenzaFatture().getStartOnceScadenzaFatture(),
                getForm().getScadenzaFatture().getStopScadenzaFatture(),
                getForm().getScadenzaFatture().getDt_prossima_attivazione(), getForm().getScadenzaFatture().getAttivo(),
                getForm().getScadenzaFatture().getDt_reg_log_job_ini(), dataAttivazioneJob);

        gestisciStatoJob(scadenzaFatture);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="UI Gestione job per l'inizializzazione dei LOG">
        String nomeApplicazione = paramHelper.getParamApplicApplicationName();
        dataAttivazioneJob = getActivationDateJob(
                it.eng.parer.sacerlog.job.Constants.NomiJob.INIZIALIZZAZIONE_LOG.name() + "_" + nomeApplicazione);
        StatoJob inizializzazioneLog = new StatoJob(
                it.eng.parer.sacerlog.job.Constants.NomiJob.INIZIALIZZAZIONE_LOG.name(),
                getForm().getInizializzazioneLog().getFl_data_accurata(), null,
                getForm().getInizializzazioneLog().getStartOnceInizializzazioneLog(), null,
                getForm().getInizializzazioneLog().getDt_prossima_attivazione(),
                getForm().getInizializzazioneLog().getAttivo(),
                getForm().getInizializzazioneLog().getDt_reg_log_job_ini(), dataAttivazioneJob);
        gestisciStatoJob(inizializzazioneLog);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="UI Gestione job allineamento LOG">
        dataAttivazioneJob = getActivationDateJob(
                it.eng.parer.sacerlog.job.Constants.NomiJob.ALLINEAMENTO_LOG.name() + "_" + nomeApplicazione);
        StatoJob allineamentoLog = new StatoJob(it.eng.parer.sacerlog.job.Constants.NomiJob.ALLINEAMENTO_LOG.name(),
                getForm().getAllineamentoLog().getFl_data_accurata(),
                getForm().getAllineamentoLog().getStartAllineamentoLog(),
                getForm().getAllineamentoLog().getStartOnceAllineamentoLog(),
                getForm().getAllineamentoLog().getStopAllineamentoLog(),
                getForm().getAllineamentoLog().getDt_prossima_attivazione(), getForm().getAllineamentoLog().getAttivo(),
                getForm().getAllineamentoLog().getDt_reg_log_job_ini(), dataAttivazioneJob);

        gestisciStatoJob(allineamentoLog);
        // </editor-fold>

        forwardToPublisher(Application.Publisher.GESTIONE_JOB);
    }

    /**
     * Cuore della classe: qui è definita la logica STANDARD degli stati dei job a livello di <b>interfaccia web<b>. Per
     * i job che devono implementare una logica non standard non è consigliabile utilizzare questo metodo. Si è cercato
     * di mantenere una simmetria tra esposizione/inibizione dei controlli grafici.
     *
     * @param statoJob
     *            Rappresentazione dello stato <b>a livello di interfaccia grafica</b> del job.
     * 
     * @throws EMFError
     *             in caso di errore generale
     */
    private void gestisciStatoJob(StatoJob statoJob) throws EMFError {
        // se non è ancora passato un minuto da quando è stato premuto un pulsante non posso fare nulla
        boolean operazioneInCorso = jbossTimerEjb.isEsecuzioneInCorso(statoJob.getNomeJob());

        statoJob.getFlagDataAccurata().setViewMode();
        statoJob.getFlagDataAccurata().setValue("L'operazione richiesta verrà effettuata entro il prossimo minuto.");
        statoJob.getStart().setHidden(operazioneInCorso);
        statoJob.getEsecuzioneSingola().setHidden(operazioneInCorso);
        statoJob.getStop().setHidden(operazioneInCorso);
        statoJob.getDataProssimaAttivazione().setHidden(operazioneInCorso);

        statoJob.getFlagDataAccurata().setHidden(!operazioneInCorso);
        if (operazioneInCorso) {
            return;
        }

        // Posso operare sulla pagina
        Date nextActivation = jbossTimerEjb.getDataProssimaAttivazione(statoJob.getNomeJob());
        boolean dataAccurata = jbossTimerEjb.isDataProssimaAttivazioneAccurata(statoJob.getNomeJob());
        DateFormat formato = new SimpleDateFormat(DateUtil.DATE_FORMAT_SLASH_TIME);

        /*
         * Se il job è già schedulato o in esecuzione singola nascondo il pulsante Start/esecuzione singola, mostro Stop
         * e visualizzo la prossima attivazione. Viceversa se è fermo mostro Start e nascondo Stop
         */
        if (nextActivation != null) {
            statoJob.getStart().setViewMode();
            statoJob.getEsecuzioneSingola().setViewMode();
            statoJob.getStop().setEditMode();
            statoJob.getDataProssimaAttivazione().setValue(formato.format(nextActivation));
        } else {
            statoJob.getStart().setEditMode();
            statoJob.getEsecuzioneSingola().setEditMode();
            statoJob.getStop().setViewMode();
            statoJob.getDataProssimaAttivazione().setValue(null);
        }

        boolean flagHidden = nextActivation == null || dataAccurata;
        // se la data c'è ma non è accurata non visualizzare la "data prossima attivazione"
        statoJob.getDataProssimaAttivazione().setHidden(!flagHidden);

        if (statoJob.getDataAttivazione() != null) {
            statoJob.getCheckAttivo().setChecked(true);
            statoJob.getDataRegistrazioneJob()
                    .setValue(formato.format(new Date(statoJob.getDataAttivazione().getTime())));
        } else {
            statoJob.getCheckAttivo().setChecked(false);
            statoJob.getDataRegistrazioneJob().setValue(null);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="UI Classe che mappa lo stato dei job">
    /**
     * Astrazione dei componenti della pagina utilizzati per i "box" dei job.
     *
     * @author Snidero_L
     */
    private static final class StatoJob {

        private final String nomeJob;
        private final Input<String> flagDataAccurata;
        private final Button<String> start;
        private final Button<String> esecuzioneSingola;
        private final Button<String> stop;
        private final Input<Timestamp> dataProssimaAttivazione;
        private final CheckBox<String> checkAttivo;
        private final Input<Timestamp> dataRegistrazioneJob;
        private final Timestamp dataAttivazione;

        // Mi serve per evitare una null pointer Exception
        private static final Button<String> NULL_BUTTON = new Button<String>(null, "EMPTY_BUTTON", "Pulsante vuoto",
                null, null, null, false, true, true, false);

        public StatoJob(String nomeJob, Input<String> flagDataAccurata, Button<String> start,
                Button<String> esecuzioneSingola, Button<String> stop, Input<Timestamp> dataProssimaAttivazione,
                CheckBox<String> checkAttivo, Input<Timestamp> dataRegistrazioneJob, Timestamp dataAttivazione) {
            this.nomeJob = nomeJob;
            this.flagDataAccurata = flagDataAccurata;
            this.start = start;
            this.esecuzioneSingola = esecuzioneSingola;
            this.stop = stop;
            this.dataProssimaAttivazione = dataProssimaAttivazione;
            this.checkAttivo = checkAttivo;
            this.dataRegistrazioneJob = dataRegistrazioneJob;
            this.dataAttivazione = dataAttivazione;
        }

        public String getNomeJob() {
            return nomeJob;
        }

        public Input<String> getFlagDataAccurata() {
            return flagDataAccurata;
        }

        public Button<String> getStart() {
            if (start == null) {
                return NULL_BUTTON;
            }
            return start;
        }

        public Button<String> getEsecuzioneSingola() {
            return esecuzioneSingola;
        }

        public Button<String> getStop() {
            if (stop == null) {
                return NULL_BUTTON;
            }
            return stop;
        }

        public Input<Timestamp> getDataProssimaAttivazione() {
            return dataProssimaAttivazione;
        }

        public CheckBox<String> getCheckAttivo() {
            return checkAttivo;
        }

        public Input<Timestamp> getDataRegistrazioneJob() {
            return dataRegistrazioneJob;
        }

        public Timestamp getDataAttivazione() {
            return dataAttivazione;
        }
    }
    // </editor-fold>

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
        return Application.Publisher.GESTIONE_JOB;
    }

    @Override
    public void process() throws EMFError {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getControllerName() {
        return Application.Actions.GESTIONE_JOB;
    }

    /**
     * Returns the activation date of job otherwise <code>null</code>
     *
     * @param jobName
     *            the job name
     * 
     * @return Timestamp of activation date
     * 
     * @throws EMFError
     */
    private Timestamp getActivationDateJob(String jobName) throws EMFError {
        Timestamp res = null;
        LogVVisLastSchedRowBean rb = gestioneJobHelper.getLogVVisLastSched(jobName);

        if (rb.getFlJobAttivo() != null) {
            if (rb.getFlJobAttivo().equals("1")) {
                res = rb.getDtRegLogJobIni();
            }
        }

        return res;
    }

    @Override
    public void reloadAfterGoBack(String publisherName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void startReplicaUtenti() throws EMFError {
        esegui(Constants.NomiJob.REPLICA_UTENTI.name(), "Replica utenti", null, OPERAZIONE.START);
    }

    @Override
    public void startOnceReplicaUtenti() throws EMFError {
        esegui(Constants.NomiJob.REPLICA_UTENTI.name(), "Replica utenti", null, OPERAZIONE.ESECUZIONE_SINGOLA);
    }

    @Override
    public void stopReplicaUtenti() throws EMFError {
        esegui(Constants.NomiJob.REPLICA_UTENTI.name(), "Replica utenti", null, OPERAZIONE.STOP);
    }

    @Override
    public void startOnceInizializzazioneLog() throws EMFError {
        String nomeApplicazione = paramHelper.getParamApplicApplicationName();
        esegui(it.eng.parer.sacerlog.job.Constants.NomiJob.INIZIALIZZAZIONE_LOG.name(), "Inizializzazione Log",
                nomeApplicazione, OPERAZIONE.ESECUZIONE_SINGOLA);
    }

    @Override
    public void startAllineamentoLog() throws EMFError {
        String nomeApplicazione = paramHelper.getParamApplicApplicationName();
        esegui(it.eng.parer.sacerlog.job.Constants.NomiJob.ALLINEAMENTO_LOG.name(), "Allineamento Log",
                nomeApplicazione, OPERAZIONE.START);
    }

    @Override
    public void startOnceAllineamentoLog() throws EMFError {
        String nomeApplicazione = paramHelper.getParamApplicApplicationName();
        esegui(it.eng.parer.sacerlog.job.Constants.NomiJob.ALLINEAMENTO_LOG.name(), "Allineamento Log",
                nomeApplicazione, OPERAZIONE.ESECUZIONE_SINGOLA);
    }

    @Override
    public void stopAllineamentoLog() throws EMFError {
        String nomeApplicazione = paramHelper.getParamApplicApplicationName();
        esegui(it.eng.parer.sacerlog.job.Constants.NomiJob.ALLINEAMENTO_LOG.name(), "Allineamento Log",
                nomeApplicazione, OPERAZIONE.STOP);
    }

    // <editor-fold defaultstate="collapsed" desc="Esecuzione di un job STANDARD">
    /**
     * Esegui una delle seguenti operazioni:
     * <ul>
     * <li>{@link OPERAZIONE#START}</li>
     * <li>{@link OPERAZIONE#ESECUZIONE_SINGOLA}</li>
     * <li>{@link OPERAZIONE#STOP}</li>
     * </ul>
     *
     * @param nomeJob
     *            nome del job
     * @param descrizioneJob
     *            descrizione (che comparirà sul LOG) del job
     * @param nomeApplicazione
     *            nome dell'applicazione. <b>Obbligatorio per i job che elaborano i LOG "PREMIS"</b>
     * @param operazione
     *            una delle tre operazioni dell'enum
     * 
     * @throws EMFError
     *             Errore di esecuzione
     */
    private void esegui(String nomeJob, String descrizioneJob, String nomeApplicazione, OPERAZIONE operazione)
            throws EMFError {
        // Messaggio sul logger di sistema
        StringBuilder info = new StringBuilder(descrizioneJob);
        info.append(": ").append(operazione.description()).append(" [").append(nomeJob);
        if (nomeApplicazione != null) {
            info.append("_").append(nomeApplicazione);
        }
        info.append("]");
        LOG.info(info.toString());

        String message = "Errore durante la schedulazione del job";

        switch (operazione) {
        case START:
            jbossTimerEjb.start(nomeJob, null);
            message = descrizioneJob + ": job correttamente schedulato";
            break;
        case ESECUZIONE_SINGOLA:
            jbossTimerEjb.esecuzioneSingola(nomeJob, null);
            message = descrizioneJob + ": job correttamente schedulato per esecuzione singola";
            break;
        case STOP:
            jbossTimerEjb.stop(nomeJob);
            message = descrizioneJob + ": schedulazione job annullata";
            break;
        }

        // Segnalo l'avvenuta operazione sul job
        getMessageBox().addMessage(new Message(MessageLevel.INF, message));
        getMessageBox().setViewMode(ViewMode.plain);
        // Risetto la pagina rilanciando l'initOnClick
        initOnClick();
    }
    // </editor-fold>

    @Override
    public void startOnceScadenzaFatture() throws EMFError {
        esegui(Constants.NomiJob.SCADENZA_FATTURE.name(), "Scadenza fatture", null, OPERAZIONE.ESECUZIONE_SINGOLA);
    }

    @Override
    public void startScadenzaFatture() throws EMFError {
        esegui(Constants.NomiJob.SCADENZA_FATTURE.name(), "Scadenza fatture", null, OPERAZIONE.START);
    }

    @Override
    public void stopScadenzaFatture() throws EMFError {
        esegui(Constants.NomiJob.SCADENZA_FATTURE.name(), "Scadenza fatture", null, OPERAZIONE.STOP);
    }

}
