package it.eng.saceriam.entity.constraint;

/**
 *
 * @author DiLorenzo_F
 */
public class ConstOrgAccordoEnte {

    private ConstOrgAccordoEnte() {
    }

    /**
     * Tipo scopo accordo ti_scopo_accordo IN ('AMMINISTRAZIONE', 'CONSERVAZIONE', 'GESTIONE')
     */
    public enum TiScopoAccordo {
        AMMINISTRAZIONE, CONSERVAZIONE, GESTIONE
    }
}
