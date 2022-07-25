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
public class ConstUsrRichGestUser {

    public enum TiStatoRichGestUser {
        DA_COMPLETARE, DA_EVADERE, EVASA
    }

    public enum TiRichGestUser {
        COMUNICAZIONE_ONLINE("COMUNICAZIONE ON LINE"), COMUNICAZIONE_PROTOCOLLATA("COMUNICAZIONE PROTOCOLLATA"),
        EMAIL("EMAIL");

        private String descrizione;

        public String getDescrizione() {
            return descrizione;
        }

        private TiRichGestUser(String descrizione) {
            this.descrizione = descrizione;
        }
    }

    public enum TiUserRich {
        UTENTE_CODIF, UTENTE_NO_CODIF
    }
}
