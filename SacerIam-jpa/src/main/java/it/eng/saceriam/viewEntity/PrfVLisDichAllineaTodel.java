package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the PRF_V_LIS_DICH_ALLINEA_TODEL database table.
 *
 */
@Entity
@Table(name = "PRF_V_LIS_DICH_ALLINEA_TODEL")
@NamedQuery(name = "PrfVLisDichAllineaTodel.findAll", query = "SELECT p FROM PrfVLisDichAllineaTodel p")
public class PrfVLisDichAllineaTodel implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idDichAutor;
    private BigDecimal idRuolo;

    public PrfVLisDichAllineaTodel() {
    }

    @Id
    @Column(name = "ID_DICH_AUTOR")
    public BigDecimal getIdDichAutor() {
        return this.idDichAutor;
    }

    public void setIdDichAutor(BigDecimal idDichAutor) {
        this.idDichAutor = idDichAutor;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
        return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        this.idRuolo = idRuolo;
    }

}
