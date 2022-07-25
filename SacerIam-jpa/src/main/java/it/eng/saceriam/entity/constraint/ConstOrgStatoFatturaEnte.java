package it.eng.saceriam.entity.constraint;

/**
 *
 * @author gilioli_p
 */
public class ConstOrgStatoFatturaEnte {

    public enum TiStatoFatturaEnte {

        CALCOLATA("CALCOLATA"), EMESSA("EMESSA"), INSOLUTA("INSOLUTA"), PAGATA("PAGATA"),
        PAGATA_PARZIALMENTE("PAGATA PARZIALMENTE"), SOLLECITATA("SOLLECITATA"), STORNATA("STORNATA");

        private String descrizione;

        private TiStatoFatturaEnte(String descrizione) {
            this.descrizione = descrizione;
        }

        public String getDescrizione() {
            return descrizione;
        }
    }
}
