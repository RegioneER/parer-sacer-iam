package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the PRF_V_CALC_AUTOR_RUOLO database table.
 */
@Entity
@Table(name = "PRF_V_CALC_AUTOR_RUOLO")
@NamedQuery(name = "PrfVCalcAutorRuolo.findAll", query = "SELECT p FROM PrfVCalcAutorRuolo p")
public class PrfVCalcAutorRuolo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAutor;

    private BigDecimal idUsoRuoloApplic;

    private String nmApplic;

    private String nmRuolo;

    public PrfVCalcAutorRuolo() {
    }

    @Column(name = "DS_AUTOR")
    public String getDsAutor() {
        return this.dsAutor;
    }

    public void setDsAutor(String dsAutor) {
        this.dsAutor = dsAutor;
    }

    @Column(name = "ID_USO_RUOLO_APPLIC")
    public BigDecimal getIdUsoRuoloApplic() {
        return this.idUsoRuoloApplic;
    }

    public void setIdUsoRuoloApplic(BigDecimal idUsoRuoloApplic) {
        this.idUsoRuoloApplic = idUsoRuoloApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
        return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
        this.nmRuolo = nmRuolo;
    }

    private PrfVCalcAutorRuoloId prfVCalcAutorRuoloId;

    @EmbeddedId()
    public PrfVCalcAutorRuoloId getPrfVCalcAutorRuoloId() {
        return prfVCalcAutorRuoloId;
    }

    public void setPrfVCalcAutorRuoloId(PrfVCalcAutorRuoloId prfVCalcAutorRuoloId) {
        this.prfVCalcAutorRuoloId = prfVCalcAutorRuoloId;
    }
}
