package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the ORG_V_VIS_STATO_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_V_VIS_STATO_ACCORDO")
@NamedQuery(name = "OrgVVisStatoAccordo.findAll", query = "SELECT o FROM OrgVVisStatoAccordo o")
public class OrgVVisStatoAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idEnteConvenz;
    private BigDecimal idAccordoEnte;
    private String nmEnteConvenz;
    private String tiStatoAccordo;

    public OrgVVisStatoAccordo() {
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    @Id
    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
        return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "TI_STATO_ACCORDO")
    public String getTiStatoAccordo() {
        return this.tiStatoAccordo;
    }

    public void setTiStatoAccordo(String tiStatoAccordo) {
        this.tiStatoAccordo = tiStatoAccordo;
    }

}
