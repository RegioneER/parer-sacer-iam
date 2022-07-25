package it.eng.saceriam.entity.constraint;

/**
 *
 * @author gilioli_p
 */
public class ConstUsrStatoUser {

    public enum TiStatotUser {

        ATTIVO("ATTIVO"), CESSATO("CESSATO"), DA_NOTIFICARE_PASSWORD("DA NOTIFICARE_PASSWORD"),
        DA_NOTIFICARE_USERID("DA NOTIFICARE_USERID"), DA_ATTIVARE("DA_ATTIVARE"),
        DA_NOTIFICARE_CESSAZIONE("DA_NOTIFICARE_CESSAZIONE"),
        DA_NOTIFICARE_RIATTIVAZIONE("DA_NOTIFICARE_RIATTIVAZIONE"), DISATTIVO("DISATTIVO");

        private String descrizione;

        public String getDescrizione() {
            return descrizione;
        }

        private TiStatotUser(String descrizione) {
            this.descrizione = descrizione;
        }

        public static TiStatotUser[] getEnums(TiStatotUser... vals) {
            return vals;
        }

        public static TiStatotUser[] getComboFiltriRicercaUtente() {
            return getEnums(ATTIVO, DISATTIVO);
        }
    }

}
