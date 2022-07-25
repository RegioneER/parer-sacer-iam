package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ORG_V_CREA_FATTURA_BY_ANNO database table.
 */
@Entity
@Table(name = "ORG_V_CREA_FATTURA_BY_ANNO")
@NamedQuery(name = "OrgVCreaFatturaByAnno.findAll", query = "SELECT o FROM OrgVCreaFatturaByAnno o")
public class OrgVCreaFatturaByAnno implements Serializable {

    private static final long serialVersionUID = 1L;

    // private String cdFattura;
    private Date dtDecAccordo;

    private BigDecimal imTotDaPagare;

    private BigDecimal imTotFattura;

    private BigDecimal imTotIva;

    // private BigDecimal pgFattura;

    public OrgVCreaFatturaByAnno() {
    }

    // @Column(name = "CD_FATTURA")
    // public String getCdFattura() {
    // return this.cdFattura;
    // }
    //
    // public void setCdFattura(String cdFattura) {
    // this.cdFattura = cdFattura;
    // }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DEC_ACCORDO")
    public Date getDtDecAccordo() {
        return this.dtDecAccordo;
    }

    public void setDtDecAccordo(Date dtDecAccordo) {
        this.dtDecAccordo = dtDecAccordo;
    }

    @Column(name = "IM_TOT_DA_PAGARE")
    public BigDecimal getImTotDaPagare() {
        return this.imTotDaPagare;
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
        this.imTotDaPagare = imTotDaPagare;
    }

    @Column(name = "IM_TOT_FATTURA")
    public BigDecimal getImTotFattura() {
        return this.imTotFattura;
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
        this.imTotFattura = imTotFattura;
    }

    @Column(name = "IM_TOT_IVA")
    public BigDecimal getImTotIva() {
        return this.imTotIva;
    }

    public void setImTotIva(BigDecimal imTotIva) {
        this.imTotIva = imTotIva;
    }

    // @Column(name = "PG_FATTURA")
    // public BigDecimal getPgFattura() {
    // return this.pgFattura;
    // }
    //
    // public void setPgFattura(BigDecimal pgFattura) {
    // this.pgFattura = pgFattura;
    // }

    private OrgVCreaFatturaByAnnoId orgVCreaFatturaByAnnoId;

    @EmbeddedId()
    public OrgVCreaFatturaByAnnoId getOrgVCreaFatturaByAnnoId() {
        return orgVCreaFatturaByAnnoId;
    }

    public void setOrgVCreaFatturaByAnnoId(OrgVCreaFatturaByAnnoId orgVCreaFatturaByAnnoId) {
        this.orgVCreaFatturaByAnnoId = orgVCreaFatturaByAnnoId;
    }
}
