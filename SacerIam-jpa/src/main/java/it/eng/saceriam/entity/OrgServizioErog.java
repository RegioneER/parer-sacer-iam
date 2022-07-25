package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_SERVIZIO_EROG database table.
 *
 */
@Entity
@Table(name = "ORG_SERVIZIO_EROG")
@NamedQuery(name = "OrgServizioErog.findAll", query = "SELECT o FROM OrgServizioErog o")
public class OrgServizioErog implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idServizioErogato;
    private Date dtErog;
    private String flPagamento;
    private BigDecimal imValoreTariffa;
    private String nmServizioErogato;
    private String ntServizioErog;
    private AplSistemaVersante aplSistemaVersante;
    private OrgAccordoEnte orgAccordoEnte;
    private OrgTariffa orgTariffa;
    private OrgTipoServizio orgTipoServizio;
    private List<OrgServizioFattura> orgServizioFatturas = new ArrayList<>();
    private OrgTariffaAccordo orgTariffaAccordo;
    private OrgTariffaAaAccordo orgTariffaAaAccordo;

    public OrgServizioErog() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_SERVIZIO_EROG") // @SequenceGenerator(name =
                                                                        // "ORG_SERVIZIO_EROG_IDSERVIZIOEROGATO_GENERATOR",
                                                                        // sequenceName = "SORG_SERVIZIO_EROG",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_SERVIZIO_EROG_IDSERVIZIOEROGATO_GENERATOR")
    @Column(name = "ID_SERVIZIO_EROGATO")
    public Long getIdServizioErogato() {
        return this.idServizioErogato;
    }

    public void setIdServizioErogato(Long idServizioErogato) {
        this.idServizioErogato = idServizioErogato;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EROG")
    public Date getDtErog() {
        return this.dtErog;
    }

    public void setDtErog(Date dtErog) {
        this.dtErog = dtErog;
    }

    @Column(name = "FL_PAGAMENTO", columnDefinition = "char(1)")
    public String getFlPagamento() {
        return this.flPagamento;
    }

    public void setFlPagamento(String flPagamento) {
        this.flPagamento = flPagamento;
    }

    @Column(name = "IM_VALORE_TARIFFA")
    public BigDecimal getImValoreTariffa() {
        return this.imValoreTariffa;
    }

    public void setImValoreTariffa(BigDecimal imValoreTariffa) {
        this.imValoreTariffa = imValoreTariffa;
    }

    @Column(name = "NM_SERVIZIO_EROGATO")
    public String getNmServizioErogato() {
        return this.nmServizioErogato;
    }

    public void setNmServizioErogato(String nmServizioErogato) {
        this.nmServizioErogato = nmServizioErogato;
    }

    @Column(name = "NT_SERVIZIO_EROG")
    public String getNtServizioErog() {
        return this.ntServizioErog;
    }

    public void setNtServizioErog(String ntServizioErog) {
        this.ntServizioErog = ntServizioErog;
    }

    // bi-directional many-to-one association to AplSistemaVersante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SISTEMA_VERSANTE")
    public AplSistemaVersante getAplSistemaVersante() {
        return this.aplSistemaVersante;
    }

    public void setAplSistemaVersante(AplSistemaVersante aplSistemaVersante) {
        this.aplSistemaVersante = aplSistemaVersante;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCORDO_ENTE")
    public OrgAccordoEnte getOrgAccordoEnte() {
        return this.orgAccordoEnte;
    }

    public void setOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        this.orgAccordoEnte = orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTariffa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TARIFFA")
    public OrgTariffa getOrgTariffa() {
        return this.orgTariffa;
    }

    public void setOrgTariffa(OrgTariffa orgTariffa) {
        this.orgTariffa = orgTariffa;
    }

    // bi-directional many-to-one association to OrgTipoServizio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_SERVIZIO")
    public OrgTipoServizio getOrgTipoServizio() {
        return this.orgTipoServizio;
    }

    public void setOrgTipoServizio(OrgTipoServizio orgTipoServizio) {
        this.orgTipoServizio = orgTipoServizio;
    }

    // bi-directional many-to-one association to OrgServizioFattura
    @OneToMany(mappedBy = "orgServizioErog")
    public List<OrgServizioFattura> getOrgServizioFatturas() {
        return this.orgServizioFatturas;
    }

    public void setOrgServizioFatturas(List<OrgServizioFattura> orgServizioFatturas) {
        this.orgServizioFatturas = orgServizioFatturas;
    }

    public OrgServizioFattura addOrgServizioFattura(OrgServizioFattura orgServizioFattura) {
        getOrgServizioFatturas().add(orgServizioFattura);
        orgServizioFattura.setOrgServizioErog(this);

        return orgServizioFattura;
    }

    public OrgServizioFattura removeOrgServizioFattura(OrgServizioFattura orgServizioFattura) {
        getOrgServizioFatturas().remove(orgServizioFattura);
        orgServizioFattura.setOrgServizioErog(null);

        return orgServizioFattura;
    }

    // bi-directional many-to-one association to OrgTariffaAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TARIFFA_ACCORDO")
    public OrgTariffaAccordo getOrgTariffaAccordo() {
        return this.orgTariffaAccordo;
    }

    public void setOrgTariffaAccordo(OrgTariffaAccordo orgTariffaAccordo) {
        this.orgTariffaAccordo = orgTariffaAccordo;
    }

    // bi-directional many-to-one association to OrgTariffaAaAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TARIFFA_AA_ACCORDO")
    public OrgTariffaAaAccordo getOrgTariffaAaAccordo() {
        return this.orgTariffaAaAccordo;
    }

    public void setOrgTariffaAaAccordo(OrgTariffaAaAccordo orgTariffaAaAccordo) {
        this.orgTariffaAaAccordo = orgTariffaAaAccordo;
    }

}
