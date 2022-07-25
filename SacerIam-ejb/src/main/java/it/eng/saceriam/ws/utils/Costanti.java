package it.eng.saceriam.ws.utils;

/**
 *
 * @author Fioravanti_F
 */
public class Costanti {

    //
    public static final String WS_INSERIMENTO_ORGANIZZAZIONE_VRSN = "1.0";
    public static final String WS_MODIFICA_ORGANIZZAZIONE_VRSN = "1.0";
    public static final String WS_CANCELLA_ORGANIZZAZIONE_VRSN = "1.0";
    public static final String WS_RESTITUZIONE_NEWS_APPLICAZIONE_VRSN = "1.0";
    public static final String WS_STATUS_MONITOR_VRSN = "1.0";

    //
    public static final String[] WS_INSERIMENTO_ORGANIZZAZIONE_COMP = { "1.0" };
    public static final String[] WS_MODIFICA_ORGANIZZAZIONE_COMP = { "1.0" };
    public static final String[] WS_CANCELLA_ORGANIZZAZIONE_COMP = { "1.0" };
    public static final String[] WS_RESTITUZIONE_NEWS_APPLICAZIONE_COMP = { "1.0" };
    public static final String[] WS_STATUS_MONITOR_COMP = { "1.0" };

    //
    public static final String WS_INSERIMENTO_ORGANIZZAZIONE = "InserimentoOrganizzione";
    public static final String WS_MODIFICA_ORGANIZZAZIONE = "ModificaOrganizzione";
    public static final String WS_CANCELLA_ORGANIZZAZIONE = "CancellaOrganizzione";
    public static final String WS_RESTITUZIONE_NEWS_APPLICAZIONE = "RestituzioneNewsApplicazione";
    public static final String WS_STATUS_MONITOR_NOME = "StatusMonitor";

    public enum AttribDatiSpecDataType {

        ALFANUMERICO, NUMERICO, DATA, DATETIME
    }

    public enum ModificatoriWS {
        // TAG_VERIFICA_FORMATI_OLD,
        // TAG_VERIFICA_FORMATI_1_25,
        // TAG_MIGRAZIONE,
        // TAG_DATISPEC_EXT,
        // TAG_ESTESI_1_3_OUT // ID documento, tag Versatore
    }
}
