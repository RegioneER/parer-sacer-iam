package it.eng.saceriam.entity.constraint;

/**
 *
 * @author DiLorenzo_F
 */
public class ConstOrgGestioneAccordo {

    private ConstOrgGestioneAccordo() {
    }

    /**
     * Tipo trasmissione tipo_trasmissione IN ('COMUNICAZIONE_PROTOCOLLATA', 'EMAIL')
     */
    public enum TipoTrasmissione {
        COMUNICAZIONE_PROTOCOLLATA, EMAIL
    }
}
