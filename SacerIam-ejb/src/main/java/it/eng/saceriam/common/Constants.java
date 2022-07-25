package it.eng.saceriam.common;

/**
 *
 * @author Gilioli_P
 */
public class Constants {

    public enum EsitoServizio {

        OK, KO, WARN, NO_RISPOSTA
    }

    public enum TiOperReplic {

        INS, MOD, CANC
    }

    // Costanti per i tipi di Help On Line
    public enum TiHelpOnLine {
        HELP_PAGINA, HELP_RICERCA_DIPS
    }

    public enum TipiRegLogJob {

        ERRORE, FINE_SCHEDULAZIONE, INIZIO_SCHEDULAZIONE
    }

    public enum NomiJob {

        REPLICA_UTENTI, WS_MONITORAGGIO_STATUS, SCADENZA_FATTURE
    }

    public enum EsitoVersamento {

        POSITIVO, NEGATIVO, WARNING
    }

    public enum TiStatoReplic {

        DA_REPLICARE, REPLICA_IN_TIMEOUT, REPLICA_IN_ERRORE, REPLICA_OK, REPLICA_NON_POSSIBILE, REPLICA_IN_CORSO
    }

    public enum TiUsoRuo {

        DEF, DICH
    }

    public enum NmParamApplic {

        MAX_ASYNC_JOBS, MIN_USERS_PER_LIST, MAX_USERS_PER_LIST_ON_DB
        // , SERVER_NAME_SYSTEM_PROPERTY
    }

    // vista da cui recuperare i valori
    public enum TipoIamVGetValAppart {
        AMBIENTEENTECONVENZ, ENTECONVENZ, APPLIC;

        public static TipoIamVGetValAppart next(TipoIamVGetValAppart last) {
            switch (last) {
            case ENTECONVENZ:
                return AMBIENTEENTECONVENZ;
            case AMBIENTEENTECONVENZ:
                return APPLIC;
            default:
                return null;
            }
        }
    }

}
