package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the PRF_V_CHK_ALLINEA_RUOLO database table.
 *
 */
@Entity
@Table(name = "PRF_V_CHK_ALLINEA_RUOLO")
@NamedQuery(name = "PrfVChkAllineaRuolo.findAll", query = "SELECT p FROM PrfVChkAllineaRuolo p")
public class PrfVChkAllineaRuolo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String flAllineamentoParziale;
    private BigDecimal idRuolo;
    private String nmApplic;

    public PrfVChkAllineaRuolo() {
    }

    @Column(name = "FL_ALLINEAMENTO_PARZIALE", columnDefinition = "char(1)")
    public String getFlAllineamentoParziale() {
        return this.flAllineamentoParziale;
    }

    public void setFlAllineamentoParziale(String flAllineamentoParziale) {
        this.flAllineamentoParziale = flAllineamentoParziale;
    }

    @Id
    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
        return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        this.idRuolo = idRuolo;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

}
