package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_V_LIS_SERV_FATTURA database table.
 *
 */
@Entity
@Table(name = "ORG_V_LIS_SERV_FATTURA")
@NamedQuery(name = "OrgVLisServFattura.findAll", query = "SELECT o FROM OrgVLisServFattura o")
public class OrgVLisServFattura implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal aaServizioFattura;
    private String cdDdt;
    private String cdIva;
    private String cdOda;
    private String cdTipoServizio;
    private Date dtErog;
    private Date dtErogServErog;
    private String flPagamento;
    private String flSuperamentoScaglione;
    private String ggFatturazione;
    private BigDecimal idFatturaEnte;
    private BigDecimal idServizioFattura;
    private BigDecimal imIva;
    private BigDecimal imNetto;
    private BigDecimal imValoreTariffa;
    private BigDecimal mmFattura;
    private BigDecimal niAbitanti;
    private BigDecimal niByteVers;
    private BigDecimal niUnitaDocVers;
    private String nmServizioErogato;
    private String nmServizioFattura;
    private String nmSistemaVersante;
    private String nmTariffa;
    private String tipoFatturazione;

    public OrgVLisServFattura() {
    }

    @Column(name = "AA_SERVIZIO_FATTURA")
    public BigDecimal getAaServizioFattura() {
        return this.aaServizioFattura;
    }

    public void setAaServizioFattura(BigDecimal aaServizioFattura) {
        this.aaServizioFattura = aaServizioFattura;
    }

    @Column(name = "CD_DDT")
    public String getCdDdt() {
        return this.cdDdt;
    }

    public void setCdDdt(String cdDdt) {
        this.cdDdt = cdDdt;
    }

    @Column(name = "CD_IVA")
    public String getCdIva() {
        return this.cdIva;
    }

    public void setCdIva(String cdIva) {
        this.cdIva = cdIva;
    }

    @Column(name = "CD_ODA")
    public String getCdOda() {
        return this.cdOda;
    }

    public void setCdOda(String cdOda) {
        this.cdOda = cdOda;
    }

    @Column(name = "CD_TIPO_SERVIZIO")
    public String getCdTipoServizio() {
        return this.cdTipoServizio;
    }

    public void setCdTipoServizio(String cdTipoServizio) {
        this.cdTipoServizio = cdTipoServizio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EROG")
    public Date getDtErog() {
        return this.dtErog;
    }

    public void setDtErog(Date dtErog) {
        this.dtErog = dtErog;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EROG_SERV_EROG")
    public Date getDtErogServErog() {
        return this.dtErogServErog;
    }

    public void setDtErogServErog(Date dtErogServErog) {
        this.dtErogServErog = dtErogServErog;
    }

    @Column(name = "FL_PAGAMENTO", columnDefinition = "char(1)")
    public String getFlPagamento() {
        return this.flPagamento;
    }

    public void setFlPagamento(String flPagamento) {
        this.flPagamento = flPagamento;
    }

    @Column(name = "FL_SUPERAMENTO_SCAGLIONE", columnDefinition = "char(1)")
    public String getFlSuperamentoScaglione() {
        return this.flSuperamentoScaglione;
    }

    public void setFlSuperamentoScaglione(String flSuperamentoScaglione) {
        this.flSuperamentoScaglione = flSuperamentoScaglione;
    }

    @Column(name = "GG_FATTURAZIONE", columnDefinition = "char")
    public String getGgFatturazione() {
        return this.ggFatturazione;
    }

    public void setGgFatturazione(String ggFatturazione) {
        this.ggFatturazione = ggFatturazione;
    }

    @Column(name = "ID_FATTURA_ENTE")
    public BigDecimal getIdFatturaEnte() {
        return this.idFatturaEnte;
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        this.idFatturaEnte = idFatturaEnte;
    }

    @Id
    @Column(name = "ID_SERVIZIO_FATTURA")
    public BigDecimal getIdServizioFattura() {
        return this.idServizioFattura;
    }

    public void setIdServizioFattura(BigDecimal idServizioFattura) {
        this.idServizioFattura = idServizioFattura;
    }

    @Column(name = "IM_IVA")
    public BigDecimal getImIva() {
        return this.imIva;
    }

    public void setImIva(BigDecimal imIva) {
        this.imIva = imIva;
    }

    @Column(name = "IM_NETTO")
    public BigDecimal getImNetto() {
        return this.imNetto;
    }

    public void setImNetto(BigDecimal imNetto) {
        this.imNetto = imNetto;
    }

    @Column(name = "IM_VALORE_TARIFFA")
    public BigDecimal getImValoreTariffa() {
        return this.imValoreTariffa;
    }

    public void setImValoreTariffa(BigDecimal imValoreTariffa) {
        this.imValoreTariffa = imValoreTariffa;
    }

    @Column(name = "MM_FATTURA")
    public BigDecimal getMmFattura() {
        return this.mmFattura;
    }

    public void setMmFattura(BigDecimal mmFattura) {
        this.mmFattura = mmFattura;
    }

    @Column(name = "NI_ABITANTI")
    public BigDecimal getNiAbitanti() {
        return this.niAbitanti;
    }

    public void setNiAbitanti(BigDecimal niAbitanti) {
        this.niAbitanti = niAbitanti;
    }

    @Column(name = "NI_BYTE_VERS")
    public BigDecimal getNiByteVers() {
        return this.niByteVers;
    }

    public void setNiByteVers(BigDecimal niByteVers) {
        this.niByteVers = niByteVers;
    }

    @Column(name = "NI_UNITÀ_DOC_VERS")
    public BigDecimal getNiUnitaDocVers() {
        return this.niUnitaDocVers;
    }

    public void setNiUnitaDocVers(BigDecimal niUnitaDocVers) {
        this.niUnitaDocVers = niUnitaDocVers;
    }

    @Column(name = "NM_SERVIZIO_EROGATO")
    public String getNmServizioErogato() {
        return this.nmServizioErogato;
    }

    public void setNmServizioErogato(String nmServizioErogato) {
        this.nmServizioErogato = nmServizioErogato;
    }

    @Column(name = "NM_SERVIZIO_FATTURA")
    public String getNmServizioFattura() {
        return this.nmServizioFattura;
    }

    public void setNmServizioFattura(String nmServizioFattura) {
        this.nmServizioFattura = nmServizioFattura;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
        return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
        this.nmSistemaVersante = nmSistemaVersante;
    }

    @Column(name = "NM_TARIFFA")
    public String getNmTariffa() {
        return this.nmTariffa;
    }

    public void setNmTariffa(String nmTariffa) {
        this.nmTariffa = nmTariffa;
    }

    @Column(name = "TIPO_FATTURAZIONE")
    public String getTipoFatturazione() {
        return this.tipoFatturazione;
    }

    public void setTipoFatturazione(String tipoFatturazione) {
        this.tipoFatturazione = tipoFatturazione;
    }

}
