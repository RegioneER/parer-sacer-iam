package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the PRF_V_RUOLO database table.
 *
 */
// @Entity
// @Table(name = "PRF_V_RUOLO")
public class PrfVRuolo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dsApplic;
    private String dsRuolo;
    private BigDecimal idRuolo;
    private BigDecimal idUsoRuoloApplic;
    private String nmApplic;
    private String nmRuolo;

    public PrfVRuolo() {
    }

    public PrfVRuolo(BigDecimal idRuolo, String nmRuolo, String dsRuolo, String dsApplic) {
        this.idRuolo = idRuolo;
        this.nmRuolo = nmRuolo;
        this.dsRuolo = dsRuolo;
        this.dsApplic = dsApplic;
    }

    @Column(name = "DS_APPLIC")
    public String getDsApplic() {
        return this.dsApplic;
    }

    public void setDsApplic(String dsApplic) {
        this.dsApplic = dsApplic;
    }

    @Column(name = "DS_RUOLO")
    public String getDsRuolo() {
        return this.dsRuolo;
    }

    public void setDsRuolo(String dsRuolo) {
        this.dsRuolo = dsRuolo;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
        return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        this.idRuolo = idRuolo;
    }

    @Id
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
}