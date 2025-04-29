/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.common;

/**
 *
 * @author Gilioli_P
 */
public class Constants {

    // Costanti Gestione Job
    public static final String DATE_FORMAT_JOB = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public enum EsitoServizio {

	OK, KO, WARN, NO_RISPOSTA
    }

    public enum TiOperReplic {

	INS, MOD, CANC
    }

    // Costanti per i tipi di Help On Line
    public enum TiHelpOnLine {
	HELP_PAGINA, HELP_RICERCA_DIPS, INFO_PRIVACY
    }

    public enum TipiRegLogJob {

	ERRORE, FINE_SCHEDULAZIONE, INIZIO_SCHEDULAZIONE
    }

    public enum NomiJob {

	REPLICA_UTENTI, ALLINEAMENTO_LOG, INIZIALIZZAZIONE_LOG, WS_MONITORAGGIO_STATUS,
	SCADENZA_FATTURE, ELIMINA_REPLICHE_UTENTI;

	public static NomiJob[] getEnums(NomiJob... vals) {
	    return vals;
	}

	public static NomiJob[] getComboSchedulazioniJob() {
	    return getEnums(REPLICA_UTENTI, ALLINEAMENTO_LOG, INIZIALIZZAZIONE_LOG,
		    WS_MONITORAGGIO_STATUS, SCADENZA_FATTURE, ELIMINA_REPLICHE_UTENTI);
	}
    }

    public enum EsitoVersamento {

	POSITIVO, NEGATIVO, WARNING
    }

    public enum TiStatoReplic {

	DA_REPLICARE, REPLICA_IN_TIMEOUT, REPLICA_IN_ERRORE, REPLICA_OK, REPLICA_NON_POSSIBILE,
	REPLICA_IN_CORSO
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
