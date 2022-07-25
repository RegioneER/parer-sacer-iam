package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the ORG_V_CREA_SERV_FATT_BY_FATT database table.
 */
@Entity
@Table(name = "ORG_V_CREA_SERV_FATT_BY_FATT")
@NamedQuery(name = "OrgVCreaServFattByFatt.findAll", query = "SELECT o FROM OrgVCreaServFattByFatt o")
public class OrgVCreaServFattByFatt implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal aaServizioFattura;

    private Date dtErog;

    private BigDecimal idCdIva;

    private BigDecimal imServizioFattura;

    private String nmServizioFattura;

    private String ntServizioFattura;

    private BigDecimal qtScaglioneServizioFattura;

    private BigDecimal qtUnitServizioFattura;

    public OrgVCreaServFattByFatt() {
    }

    @Column(name = "AA_SERVIZIO_FATTURA")
    public BigDecimal getAaServizioFattura() {
        return this.aaServizioFattura;
    }

    public void setAaServizioFattura(BigDecimal aaServizioFattura) {
        this.aaServizioFattura = aaServizioFattura;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EROG")
    public Date getDtErog() {
        return this.dtErog;
    }

    public void setDtErog(Date dtErog) {
        this.dtErog = dtErog;
    }

    @Column(name = "ID_CD_IVA")
    public BigDecimal getIdCdIva() {
        return this.idCdIva;
    }

    public void setIdCdIva(BigDecimal idCdIva) {
        this.idCdIva = idCdIva;
    }

    @Column(name = "IM_SERVIZIO_FATTURA")
    public BigDecimal getImServizioFattura() {
        return this.imServizioFattura;
    }

    public void setImServizioFattura(BigDecimal imServizioFattura) {
        this.imServizioFattura = imServizioFattura;
    }

    @Column(name = "NM_SERVIZIO_FATTURA")
    public String getNmServizioFattura() {
        return this.nmServizioFattura;
    }

    public void setNmServizioFattura(String nmServizioFattura) {
        this.nmServizioFattura = nmServizioFattura;
    }

    @Column(name = "NT_SERVIZIO_FATTURA")
    public String getNtServizioFattura() {
        return this.ntServizioFattura;
    }

    public void setNtServizioFattura(String ntServizioFattura) {
        this.ntServizioFattura = ntServizioFattura;
    }

    @Column(name = "QT_SCAGLIONE_SERVIZIO_FATTURA")
    public BigDecimal getQtScaglioneServizioFattura() {
        return this.qtScaglioneServizioFattura;
    }

    public void setQtScaglioneServizioFattura(BigDecimal qtScaglioneServizioFattura) {
        this.qtScaglioneServizioFattura = qtScaglioneServizioFattura;
    }

    @Column(name = "QT_UNIT_SERVIZIO_FATTURA")
    public BigDecimal getQtUnitServizioFattura() {
        return this.qtUnitServizioFattura;
    }

    public void setQtUnitServizioFattura(BigDecimal qtUnitServizioFattura) {
        this.qtUnitServizioFattura = qtUnitServizioFattura;
    }

    private OrgVCreaServFattByFattId orgVCreaServFattByFattId;

    @EmbeddedId()
    public OrgVCreaServFattByFattId getOrgVCreaServFattByFattId() {
        return orgVCreaServFattByFattId;
    }

    public void setOrgVCreaServFattByFattId(OrgVCreaServFattByFattId orgVCreaServFattByFattId) {
        this.orgVCreaServFattByFattId = orgVCreaServFattByFattId;
    }
}
