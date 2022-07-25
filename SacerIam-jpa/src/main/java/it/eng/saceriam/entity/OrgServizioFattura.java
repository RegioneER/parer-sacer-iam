package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the ORG_SERVIZIO_FATTURA database table.
 *
 */
@Entity
@Table(name = "ORG_SERVIZIO_FATTURA")
@NamedQuery(name = "OrgServizioFattura.findAll", query = "SELECT o FROM OrgServizioFattura o")
public class OrgServizioFattura implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idServizioFattura;
    private BigDecimal aaServizioFattura;
    private Date dtErog;
    private BigDecimal imServizioFattura;
    private BigDecimal qtScaglioneServizioFattura;
    private BigDecimal qtUnitServizioFattura;
    private OrgCdIva orgCdIva;
    private OrgFatturaEnte orgFatturaEnte;
    private OrgServizioErog orgServizioErog;
    private String nmServizioFattura;
    private String ntServizioFattura;
    private BigDecimal imIva;

    public OrgServizioFattura() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_SERVIZIO_FATTURA") // @SequenceGenerator(name =
                                                                           // "ORG_SERVIZIO_FATTURA_IDSERVIZIOFATTURA_GENERATOR",
                                                                           // sequenceName = "SORG_SERVIZIO_FATTURA",
                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_SERVIZIO_FATTURA_IDSERVIZIOFATTURA_GENERATOR")
    @Column(name = "ID_SERVIZIO_FATTURA")
    public Long getIdServizioFattura() {
        return this.idServizioFattura;
    }

    public void setIdServizioFattura(Long idServizioFattura) {
        this.idServizioFattura = idServizioFattura;
    }

    @Column(name = "IM_SERVIZIO_FATTURA")
    public BigDecimal getImServizioFattura() {
        return this.imServizioFattura;
    }

    public void setImServizioFattura(BigDecimal imServizioFattura) {
        this.imServizioFattura = imServizioFattura;
    }

    @Column(name = "AA_SERVIZIO_FATTURA")
    public BigDecimal getAaServizioFattura() {
        return aaServizioFattura;
    }

    public void setAaServizioFattura(BigDecimal aaServizioFattura) {
        this.aaServizioFattura = aaServizioFattura;
    }

    @Column(name = "DT_EROG", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDtErog() {
        return dtErog;
    }

    public void setDtErog(Date dtErog) {
        this.dtErog = dtErog;
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

    // bi-directional many-to-one association to OrgServizioErog
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CD_IVA")
    public OrgCdIva getOrgCdIva() {
        return orgCdIva;
    }

    public void setOrgCdIva(OrgCdIva orgCdIva) {
        this.orgCdIva = orgCdIva;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FATTURA_ENTE")
    public OrgFatturaEnte getOrgFatturaEnte() {
        return this.orgFatturaEnte;
    }

    public void setOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
        this.orgFatturaEnte = orgFatturaEnte;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVIZIO_EROGATO")
    public OrgServizioErog getOrgServizioErog() {
        return this.orgServizioErog;
    }

    public void setOrgServizioErog(OrgServizioErog orgServizioErog) {
        this.orgServizioErog = orgServizioErog;
    }

    @Column(name = "NM_SERVIZIO_FATTURA")
    public String getNmServizioFattura() {
        return nmServizioFattura;
    }

    public void setNmServizioFattura(String nmServizioFattura) {
        this.nmServizioFattura = nmServizioFattura;
    }

    @Column(name = "NT_SERVIZIO_FATTURA")
    public String getNtServizioFattura() {
        return ntServizioFattura;
    }

    public void setNtServizioFattura(String ntServizioFattura) {
        this.ntServizioFattura = ntServizioFattura;
    }

    @Column(name = "IM_IVA")
    public BigDecimal getImIva() {
        return this.imIva;
    }

    public void setImIva(BigDecimal imIva) {
        this.imIva = imIva;
    }

}
