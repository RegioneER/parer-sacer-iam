/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        LOG_APPLIC, REPLICA_UTENTI, ALLINEA_RUOLI, IAM, ENTE_CONVENZ, MAX_RESULT, UTENTE, REPLICA_UTENTE, SACER_RECUP
    }

    public enum NmParamApplic {
        REGIONE_DEFAULT, MAX_RESULT_RICERCA_LOG_EVENTI, LOG_ATTIVO, MAX_ASYNC_JOBS, MIN_USERS_PER_LIST,
        MAX_USERS_PER_LIST_ON_DB, STRUT_UNITA_DOC_ACCORDO, CD_CAPITOLO_DEFAULT, CD_COGE_DEFAULT,
        AZIONE_INIZIALIZZAZIONE_LOG, DESTINATARIO_ALLINEA_RUOLI_1, URL_ALLINEA_RUOLI_1, DESTINATARIO_ALLINEA_RUOLI_2,
        URL_ALLINEA_RUOLI_2, CHIAMANTE_ALLINEA_RUOLI, TIMEOUT_ALLINEA_RUOLI, USER_ALLINEA_RUOLI, PSW_ALLINEA_RUOLI,
        NM_APPLIC, SERVER_NAME_SYSTEM_PROPERTY, NUM_OLD_PSW, NUM_GIORNI_ESPONI_SCAD_PSW, PSW_RECUP_UD, TIMEOUT_RECUP_UD,
        URL_RECUP_UD, USERID_RECUP_UD, VERSIONE_XML_RECUP_UD, NM_ENTE_UNITA_DOC_ACCORDO, NM_STRUT_UNITA_DOC_ACCORDO,
        NM_ENTE_FATT_ATTIVE, NM_STRUT_FATT_ATTIVE, NM_TIPOUD_FATT_ATTIVE
    }

}
