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
public class ConstDecModelloComunic {

    public enum TiComunic {
        ESIBIZIONE, NOTIFICA
    }

    public enum TiOggettoQuery {
        COMP, DOC, RICH_GEST_USER, UNITA_DOC, UTENTE
    }

    public enum TiStatoTrigComunic {
        DA_NOTIFICARE_PASSWORD, DA_NOTIFICARE_USERID, DA_NOTIFICARE_CESSAZIONE, DA_NOTIFICARE_EVASIONE,
        DA_NOTIFICARE_RIATTIVAZIONE
    }
}
