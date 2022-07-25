package it.eng.saceriam.entity.constraint;

/**
 *
 * @author DiLorenzo_F
 */
public class ConstOrgCollegEntiConvenz {

    private ConstOrgCollegEntiConvenz() {
    }

    /**
     * Tipo collegamento ti_colleg IN ('GERARCHICO', 'RETICOLARE')
     */
    public enum TiColleg {
        GERARCHICO, RETICOLARE
    }
}
