package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_FATTURA_ENTE database table.
 *
 */
@Entity
@Table(name = "ORG_FATTURA_ENTE")
@NamedQuery(name = "OrgFatturaEnte.findAll", query = "SELECT o FROM OrgFatturaEnte o")
public class OrgFatturaEnte implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idFatturaEnte;
    private BigDecimal aaEmissFattura;
    private BigDecimal aaEmissNotaCredito;
    private BigDecimal aaFattura;
    private String cdEmisFattura;
    private String cdEmisNotaCredito;
    private String cdFattura;
    private String cdFatturaSap;
    private String cdRegistroEmisFattura;
    private String cdRegistroEmisNotaCredito;
    private String dsTestoFattura;
    private Date dtEmisFattura;
    private Date dtEmisNotaCredito;
    private Date dtScadFattura;
    private String flDaRiemis;
    private BigDecimal idStatoFatturaEnteCor;
    private BigDecimal imTotDaPagare;
    private BigDecimal imTotFattura;
    private BigDecimal imTotIva;
    private String ntEmisFattura;
    private String ntEmisNotaCredito;
    private String ntFattura;
    private BigDecimal pgFattura;
    private OrgEnteSiam orgEnteSiam;
    private OrgFatturaEnte orgFatturaEnte;
    private List<OrgFatturaEnte> orgFatturaEntes = new ArrayList<>();
    private List<OrgServizioFattura> orgServizioFatturas = new ArrayList<>();
    private List<OrgStatoFatturaEnte> orgStatoFatturaEntes = new ArrayList<>();
    private List<OrgPagamFatturaEnte> orgPagamFatturaEntes = new ArrayList<>();
    private List<OrgSollecitoFatturaEnte> orgSollecitoFatturaEntes = new ArrayList<>();
    private List<OrgModifFatturaEnte> orgModifFatturaEntes = new ArrayList<>();
    private String nmEnte;
    private String nmStrut;

    public OrgFatturaEnte() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_FATTURA_ENTE") // @SequenceGenerator(name =
                                                                       // "ORG_FATTURA_ENTE_IDFATTURAENTE_GENERATOR",
                                                                       // sequenceName = "SORG_FATTURA_ENTE",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_FATTURA_ENTE_IDFATTURAENTE_GENERATOR")
    @Column(name = "ID_FATTURA_ENTE")
    public Long getIdFatturaEnte() {
        return this.idFatturaEnte;
    }

    public void setIdFatturaEnte(Long idFatturaEnte) {
        this.idFatturaEnte = idFatturaEnte;
    }

    @Column(name = "AA_EMISS_FATTURA")
    public BigDecimal getAaEmissFattura() {
        return this.aaEmissFattura;
    }

    public void setAaEmissFattura(BigDecimal aaEmissFattura) {
        this.aaEmissFattura = aaEmissFattura;
    }

    @Column(name = "AA_FATTURA")
    public BigDecimal getAaFattura() {
        return this.aaFattura;
    }

    public void setAaFattura(BigDecimal aaFattura) {
        this.aaFattura = aaFattura;
    }

    @Column(name = "CD_EMIS_FATTURA")
    public String getCdEmisFattura() {
        return this.cdEmisFattura;
    }

    public void setCdEmisFattura(String cdEmisFattura) {
        this.cdEmisFattura = cdEmisFattura;
    }

    @Column(name = "CD_REGISTRO_EMIS_FATTURA")
    public String getCdRegistroEmisFattura() {
        return this.cdRegistroEmisFattura;
    }

    public void setCdRegistroEmisFattura(String cdRegistroEmisFattura) {
        this.cdRegistroEmisFattura = cdRegistroEmisFattura;
    }

    @Column(name = "DS_TESTO_FATTURA")
    public String getDsTestoFattura() {
        return dsTestoFattura;
    }

    public void setDsTestoFattura(String dsTestoFattura) {
        this.dsTestoFattura = dsTestoFattura;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EMIS_FATTURA")
    public Date getDtEmisFattura() {
        return dtEmisFattura;
    }

    public void setDtEmisFattura(Date dtEmisFattura) {
        this.dtEmisFattura = dtEmisFattura;
    }

    @Column(name = "ID_STATO_FATTURA_ENTE_COR")
    public BigDecimal getIdStatoFatturaEnteCor() {
        return this.idStatoFatturaEnteCor;
    }

    public void setIdStatoFatturaEnteCor(BigDecimal idStatoFatturaEnteCor) {
        this.idStatoFatturaEnteCor = idStatoFatturaEnteCor;
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

    @Column(name = "PG_FATTURA")
    public BigDecimal getPgFattura() {
        return this.pgFattura;
    }

    public void setPgFattura(BigDecimal pgFattura) {
        this.pgFattura = pgFattura;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FATTURA_ENTE_STORNATA")
    public OrgFatturaEnte getOrgFatturaEnte() {
        return this.orgFatturaEnte;
    }

    public void setOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
        this.orgFatturaEnte = orgFatturaEnte;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @OneToMany(mappedBy = "orgFatturaEnte")
    public List<OrgFatturaEnte> getOrgFatturaEntes() {
        return this.orgFatturaEntes;
    }

    public void setOrgFatturaEntes(List<OrgFatturaEnte> orgFatturaEntes) {
        this.orgFatturaEntes = orgFatturaEntes;
    }

    // bi-directional many-to-one association to OrgServizioFattura
    @OneToMany(mappedBy = "orgFatturaEnte")
    public List<OrgServizioFattura> getOrgServizioFatturas() {
        return this.orgServizioFatturas;
    }

    public void setOrgServizioFatturas(List<OrgServizioFattura> orgServizioFatturas) {
        this.orgServizioFatturas = orgServizioFatturas;
    }

    public OrgServizioFattura addOrgServizioFattura(OrgServizioFattura orgServizioFattura) {
        getOrgServizioFatturas().add(orgServizioFattura);
        orgServizioFattura.setOrgFatturaEnte(this);

        return orgServizioFattura;
    }

    public OrgServizioFattura removeOrgServizioFattura(OrgServizioFattura orgServizioFattura) {
        getOrgServizioFatturas().remove(orgServizioFattura);
        orgServizioFattura.setOrgFatturaEnte(null);

        return orgServizioFattura;
    }

    // bi-directional many-to-one association to OrgStatoFatturaEnte
    @OneToMany(mappedBy = "orgFatturaEnte", cascade = CascadeType.PERSIST)
    public List<OrgStatoFatturaEnte> getOrgStatoFatturaEntes() {
        return this.orgStatoFatturaEntes;
    }

    public void setOrgStatoFatturaEntes(List<OrgStatoFatturaEnte> orgStatoFatturaEntes) {
        this.orgStatoFatturaEntes = orgStatoFatturaEntes;
    }

    public OrgStatoFatturaEnte addOrgStatoFatturaEnte(OrgStatoFatturaEnte orgStatoFatturaEnte) {
        getOrgStatoFatturaEntes().add(orgStatoFatturaEnte);
        orgStatoFatturaEnte.setOrgFatturaEnte(this);

        return orgStatoFatturaEnte;
    }

    public OrgStatoFatturaEnte removeOrgStatoFatturaEnte(OrgStatoFatturaEnte orgStatoFatturaEnte) {
        getOrgStatoFatturaEntes().remove(orgStatoFatturaEnte);
        orgStatoFatturaEnte.setOrgFatturaEnte(null);

        return orgStatoFatturaEnte;
    }

    // bi-directional many-to-one association to OrgPagamFatturaEnte
    @OneToMany(mappedBy = "orgFatturaEnte", cascade = CascadeType.PERSIST)
    public List<OrgPagamFatturaEnte> getOrgPagamFatturaEntes() {
        return this.orgPagamFatturaEntes;
    }

    public void setOrgPagamFatturaEntes(List<OrgPagamFatturaEnte> orgPagamFatturaEntes) {
        this.orgPagamFatturaEntes = orgPagamFatturaEntes;
    }

    // bi-directional many-to-one association to OrgSollecitoFatturaEnte
    @OneToMany(mappedBy = "orgFatturaEnte", cascade = CascadeType.PERSIST)
    public List<OrgSollecitoFatturaEnte> getOrgSollecitoFatturaEntes() {
        return this.orgSollecitoFatturaEntes;
    }

    public void setOrgSollecitoFatturaEntes(List<OrgSollecitoFatturaEnte> orgSollecitoFatturaEntes) {
        this.orgSollecitoFatturaEntes = orgSollecitoFatturaEntes;
    }

    // bi-directional many-to-one association to OrgModifFatturaEnte
    @OneToMany(mappedBy = "orgFatturaEnte")
    public List<OrgModifFatturaEnte> getOrgModifFatturaEntes() {
        return this.orgModifFatturaEntes;
    }

    public void setOrgModifFatturaEntes(List<OrgModifFatturaEnte> orgModifFatturaEntes) {
        this.orgModifFatturaEntes = orgModifFatturaEntes;
    }

    @Column(name = "AA_EMISS_NOTA_CREDITO")
    public BigDecimal getAaEmissNotaCredito() {
        return aaEmissNotaCredito;
    }

    public void setAaEmissNotaCredito(BigDecimal aaEmissNotaCredito) {
        this.aaEmissNotaCredito = aaEmissNotaCredito;
    }

    @Column(name = "CD_EMIS_NOTA_CREDITO")
    public String getCdEmisNotaCredito() {
        return cdEmisNotaCredito;
    }

    public void setCdEmisNotaCredito(String cdEmisNotaCredito) {
        this.cdEmisNotaCredito = cdEmisNotaCredito;
    }

    @Column(name = "CD_FATTURA")
    public String getCdFattura() {
        return cdFattura;
    }

    public void setCdFattura(String cdFattura) {
        this.cdFattura = cdFattura;
    }

    @Column(name = "CD_FATTURA_SAP")
    public String getCdFatturaSap() {
        return cdFatturaSap;
    }

    public void setCdFatturaSap(String cdFatturaSap) {
        this.cdFatturaSap = cdFatturaSap;
    }

    @Column(name = "CD_REGISTRO_EMIS_NOTA_CREDITO")
    public String getCdRegistroEmisNotaCredito() {
        return cdRegistroEmisNotaCredito;
    }

    public void setCdRegistroEmisNotaCredito(String cdRegistroEmisNotaCredito) {
        this.cdRegistroEmisNotaCredito = cdRegistroEmisNotaCredito;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EMIS_NOTA_CREDITO")
    public Date getDtEmisNotaCredito() {
        return dtEmisNotaCredito;
    }

    public void setDtEmisNotaCredito(Date dtEmisNotaCredito) {
        this.dtEmisNotaCredito = dtEmisNotaCredito;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SCAD_FATTURA")
    public Date getDtScadFattura() {
        return dtScadFattura;
    }

    public void setDtScadFattura(Date dtScadFattura) {
        this.dtScadFattura = dtScadFattura;
    }

    @Column(name = "FL_DA_RIEMIS", columnDefinition = "char(1)")
    public String getFlDaRiemis() {
        return flDaRiemis;
    }

    public void setFlDaRiemis(String flDaRiemis) {
        this.flDaRiemis = flDaRiemis;
    }

    @Column(name = "IM_TOT_DA_PAGARE")
    public BigDecimal getImTotDaPagare() {
        return imTotDaPagare;
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
        this.imTotDaPagare = imTotDaPagare;
    }

    @Column(name = "NT_EMIS_FATTURA")
    public String getNtEmisFattura() {
        return ntEmisFattura;
    }

    public void setNtEmisFattura(String ntEmisFattura) {
        this.ntEmisFattura = ntEmisFattura;
    }

    @Column(name = "NT_EMIS_NOTA_CREDITO")
    public String getNtEmisNotaCredito() {
        return ntEmisNotaCredito;
    }

    public void setNtEmisNotaCredito(String ntEmisNotaCredito) {
        this.ntEmisNotaCredito = ntEmisNotaCredito;
    }

    @Column(name = "NT_FATTURA")
    public String getNtFattura() {
        return ntFattura;
    }

    public void setNtFattura(String ntFattura) {
        this.ntFattura = ntFattura;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
        return nmEnte;
    }

    public void setNmEnte(String nmEnte) {
        this.nmEnte = nmEnte;
    }

    @Column(name = "NM_STRUT")
    public String getNmStrut() {
        return nmStrut;
    }

    public void setNmStrut(String nmStrut) {
        this.nmStrut = nmStrut;
    }

}
