package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_V_CALC_DT_EROG database table.
 * 
 */
@Entity
@Table(name = "ORG_V_CALC_DT_EROG")
@NamedQuery(name = "OrgVCalcDtErog.findAll", query = "SELECT o FROM OrgVCalcDtErog o")
public class OrgVCalcDtErog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date dtDecAccordo;
    private Date dtErog;
    private Date dtScadAccordo;
    private BigDecimal idServizioErogato;
    private String nmServizioErogato;

    public OrgVCalcDtErog() {
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_DEC_ACCORDO")
    public Date getDtDecAccordo() {
        return this.dtDecAccordo;
    }

    public void setDtDecAccordo(Date dtDecAccordo) {
        this.dtDecAccordo = dtDecAccordo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_EROG")
    public Date getDtErog() {
        return this.dtErog;
    }

    public void setDtErog(Date dtErog) {
        this.dtErog = dtErog;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_SCAD_ACCORDO")
    public Date getDtScadAccordo() {
        return this.dtScadAccordo;
    }

    public void setDtScadAccordo(Date dtScadAccordo) {
        this.dtScadAccordo = dtScadAccordo;
    }

    @Id
    @Column(name = "ID_SERVIZIO_EROGATO")
    public BigDecimal getIdServizioErogato() {
        return this.idServizioErogato;
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
        this.idServizioErogato = idServizioErogato;
    }

    @Column(name = "NM_SERVIZIO_EROGATO")
    public String getNmServizioErogato() {
        return this.nmServizioErogato;
    }

    public void setNmServizioErogato(String nmServizioErogato) {
        this.nmServizioErogato = nmServizioErogato;
    }

}