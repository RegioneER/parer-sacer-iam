package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_PAGAM_FATTURA_ENTE database table.
 * 
 */
@Entity
@Table(name = "ORG_PAGAM_FATTURA_ENTE")
@NamedQuery(name = "OrgPagamFatturaEnte.findAll", query = "SELECT o FROM OrgPagamFatturaEnte o")
public class OrgPagamFatturaEnte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idPagamFatturaEnte;
    private String cdProvvPagam;
    private String cdRevPagam;
    private Date dtPagam;
    private BigDecimal imPagam;
    private String ntPagam;
    private BigDecimal pgPagam;
    private OrgFatturaEnte orgFatturaEnte;

    public OrgPagamFatturaEnte() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_PAGAM_FATTURA_ENTE") // @SequenceGenerator(name =
                                                                             // "ORG_PAGAM_FATTURA_ENTE_IDPAGAMFATTURAENTE_GENERATOR",
                                                                             // sequenceName =
                                                                             // "SORG_PAGAM_FATTURA_ENTE",
                                                                             // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_PAGAM_FATTURA_ENTE_IDPAGAMFATTURAENTE_GENERATOR")
    @Column(name = "ID_PAGAM_FATTURA_ENTE")
    public Long getIdPagamFatturaEnte() {
        return this.idPagamFatturaEnte;
    }

    public void setIdPagamFatturaEnte(Long idPagamFatturaEnte) {
        this.idPagamFatturaEnte = idPagamFatturaEnte;
    }

    @Column(name = "CD_PROVV_PAGAM")
    public String getCdProvvPagam() {
        return this.cdProvvPagam;
    }

    public void setCdProvvPagam(String cdProvvPagam) {
        this.cdProvvPagam = cdProvvPagam;
    }

    @Column(name = "CD_REV_PAGAM")
    public String getCdRevPagam() {
        return this.cdRevPagam;
    }

    public void setCdRevPagam(String cdRevPagam) {
        this.cdRevPagam = cdRevPagam;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_PAGAM")
    public Date getDtPagam() {
        return this.dtPagam;
    }

    public void setDtPagam(Date dtPagam) {
        this.dtPagam = dtPagam;
    }

    @Column(name = "IM_PAGAM")
    public BigDecimal getImPagam() {
        return this.imPagam;
    }

    public void setImPagam(BigDecimal imPagam) {
        this.imPagam = imPagam;
    }

    @Column(name = "NT_PAGAM")
    public String getNtPagam() {
        return this.ntPagam;
    }

    public void setNtPagam(String ntPagam) {
        this.ntPagam = ntPagam;
    }

    @Column(name = "PG_PAGAM")
    public BigDecimal getPgPagam() {
        return this.pgPagam;
    }

    public void setPgPagam(BigDecimal pgPagam) {
        this.pgPagam = pgPagam;
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

}