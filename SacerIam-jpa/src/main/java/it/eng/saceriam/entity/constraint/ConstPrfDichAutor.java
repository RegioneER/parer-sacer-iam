package it.eng.saceriam.entity.constraint;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Bonora_L
 */
public class ConstPrfDichAutor {

    public enum TiDichAutor {
        ENTRY_MENU("M"), AZIONE("A"), SERVIZIO_WEB("S"), PAGINA("P");

        private final String value;

        private TiDichAutor(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static TiDichAutor getTiDichAutor(String value) {
            for (TiDichAutor dich : TiDichAutor.values()) {
                if (StringUtils.equalsIgnoreCase(value, dich.getValue())) {
                    return dich;
                }
            }
            return null;
        }
    }

    public enum TiScopoDichAutor {

        ALL_ABILITAZIONI, ALL_ABILITAZIONI_CHILD, UNA_ABILITAZIONE
    }
}
