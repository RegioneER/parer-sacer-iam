package it.eng.saceriam.entity.constraint;

/**
 *
 * @author Bonora_L
 */
public class ConstOrgAmbitoTerrit {

    public enum TiAmbitoTerrit {
        FORMA_ASSOCIATA("FORMA_ASSOCIATA"), PROVINCIA("PROVINCIA"), REGIONE_STATO("REGIONE/STATO");

        String tiAmbitoString;

        private TiAmbitoTerrit(String tiAmbitoString) {
            this.tiAmbitoString = tiAmbitoString;
        }

        @Override
        public String toString() {
            return tiAmbitoString;
        }
    }

}
