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

package it.eng.saceriam.entity.constraint;

/**
 *
 * @author gilioli_p
 */
public class ConstIamParamApplic {

    // Costanti per il log dei login ws e la disattivazione automatica utenti
    public static final String IDP_MAX_TENTATIVI_FALLITI = "MAX_TENTATIVI_FALLITI";
    public static final String IDP_MAX_GIORNILOGGER = "MAX_GIORNILOGGER";
    public static final String IDP_QRY_DISABLE_USER = "QRY_DISABLE_USER";
    public static final String IDP_QRY_VERIFICA_DISATTIVAZIONE_UTENTE = "QRY_VERIFICA_DISATTIVAZIONE_UTENTE";
    public static final String IDP_QRY_REGISTRA_EVENTO_UTENTE = "QRY_REGISTRA_EVENTO_UTENTE";

    public enum TiParamApplic {
	LOG_APPLIC, REPLICA_UTENTI, ALLINEA_RUOLI, IAM, ENTE_CONVENZ, MAX_RESULT, UTENTE,
	REPLICA_UTENTE, SACER_RECUP
    }

    public enum NmParamApplic {
	REGIONE_DEFAULT, MAX_RESULT_RICERCA_LOG_EVENTI, LOG_ATTIVO, MAX_ASYNC_JOBS,
	MIN_USERS_PER_LIST, MAX_USERS_PER_LIST_ON_DB, STRUT_UNITA_DOC_ACCORDO, CD_CAPITOLO_DEFAULT,
	CD_COGE_DEFAULT, AZIONE_INIZIALIZZAZIONE_LOG, DESTINATARIO_ALLINEA_RUOLI_1,
	URL_ALLINEA_RUOLI_1, DESTINATARIO_ALLINEA_RUOLI_2, URL_ALLINEA_RUOLI_2,
	CHIAMANTE_ALLINEA_RUOLI, TIMEOUT_ALLINEA_RUOLI, USER_ALLINEA_RUOLI, PSW_ALLINEA_RUOLI,
	NM_APPLIC, SERVER_NAME_SYSTEM_PROPERTY, NUM_OLD_PSW, NUM_GIORNI_ESPONI_SCAD_PSW,
	PSW_RECUP_UD, TIMEOUT_RECUP_UD, URL_RECUP_UD, USERID_RECUP_UD, VERSIONE_XML_RECUP_UD,
	NM_ENTE_UNITA_DOC_ACCORDO, NM_STRUT_UNITA_DOC_ACCORDO, NM_ENTE_FATT_ATTIVE,
	NM_STRUT_FATT_ATTIVE, NM_TIPOUD_FATT_ATTIVE, URL_KEYCLOAK, KEYCLOAK_CLIENT_SECRET,
	KEYCLOAK_CLIENT_ID, KEYCLOAK_USER_PROVIDER_ID, NUM_ANNI_CANC_REPLICHE_UT,
	TIMEOUT_REPLICA_UTENTE, TIPI_ACCORDO_CALC_STORAGE_PROD, TIPI_ACCORDO_CALC_STORAGE_PROD_FATT,
	USERID_RECUP_INFO, PSW_RECUP_INFO, URL_RECUP_HELP
    }

}
