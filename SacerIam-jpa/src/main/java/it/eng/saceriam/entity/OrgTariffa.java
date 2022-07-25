package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_TARIFFA database table.
 *
 */
@Entity
@Table(name = "ORG_TARIFFA")
@NamedQuery(name = "OrgTariffa.findAll", query = "SELECT o FROM OrgTariffa o")
public class OrgTariffa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTariffa;
    private String dsTariffa;
    private BigDecimal imValoreFissoTariffa;
    private BigDecimal niQuantitaUnit;
    private String nmTariffa;
    private String tipoTariffa;
    private List<OrgScaglioneTariffa> orgScaglioneTariffas = new ArrayList<>();
    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();
    private OrgClasseEnteConvenz orgClasseEnteConvenz;
    private OrgTariffario orgTariffario;
    private OrgTipoServizio orgTipoServizio;

    public OrgTariffa() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_TARIFFA") // @SequenceGenerator(name =
                                                                  // "ORG_TARIFFA_IDTARIFFA_GENERATOR", sequenceName =
                                                                  // "SORG_TARIFFA", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_TARIFFA_IDTARIFFA_GENERATOR")
    @Column(name = "ID_TARIFFA")
    public Long getIdTariffa() {
        return this.idTariffa;
    }

    public void setIdTariffa(Long idTariffa) {
        this.idTariffa = idTariffa;
    }

    @Column(name = "DS_TARIFFA")
    public String getDsTariffa() {
        return this.dsTariffa;
    }

    public void setDsTariffa(String dsTariffa) {
        this.dsTariffa = dsTariffa;
    }

    @Column(name = "IM_VALORE_FISSO_TARIFFA")
    public BigDecimal getImValoreFissoTariffa() {
        return this.imValoreFissoTariffa;
    }

    public void setImValoreFissoTariffa(BigDecimal imValoreFissoTariffa) {
        this.imValoreFissoTariffa = imValoreFissoTariffa;
    }

    @Column(name = "NI_QUANTITA_UNIT")
    public BigDecimal getNiQuantitaUnit() {
        return this.niQuantitaUnit;
    }

    public void setNiQuantitaUnit(BigDecimal niQuantitaUnit) {
        this.niQuantitaUnit = niQuantitaUnit;
    }

    @Column(name = "NM_TARIFFA")
    public String getNmTariffa() {
        return this.nmTariffa;
    }

    public void setNmTariffa(String nmTariffa) {
        this.nmTariffa = nmTariffa;
    }

    @Column(name = "TIPO_TARIFFA")
    public String getTipoTariffa() {
        return this.tipoTariffa;
    }

    public void setTipoTariffa(String tipoTariffa) {
        this.tipoTariffa = tipoTariffa;
    }

    // bi-directional many-to-one association to OrgScaglioneTariffa
    @OneToMany(mappedBy = "orgTariffa", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgScaglioneTariffa> getOrgScaglioneTariffas() {
        return this.orgScaglioneTariffas;
    }

    public void setOrgScaglioneTariffas(List<OrgScaglioneTariffa> orgScaglioneTariffas) {
        this.orgScaglioneTariffas = orgScaglioneTariffas;
    }

    public OrgScaglioneTariffa addOrgScaglioneTariffa(OrgScaglioneTariffa orgScaglioneTariffa) {
        getOrgScaglioneTariffas().add(orgScaglioneTariffa);
        orgScaglioneTariffa.setOrgTariffa(this);

        return orgScaglioneTariffa;
    }

    public OrgScaglioneTariffa removeOrgScaglioneTariffa(OrgScaglioneTariffa orgScaglioneTariffa) {
        getOrgScaglioneTariffas().remove(orgScaglioneTariffa);
        orgScaglioneTariffa.setOrgTariffa(null);

        return orgScaglioneTariffa;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "orgTariffa")
    public List<OrgServizioErog> getOrgServizioErogs() {
        return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
        this.orgServizioErogs = orgServizioErogs;
    }

    public OrgServizioErog addOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().add(orgServizioErog);
        orgServizioErog.setOrgTariffa(this);

        return orgServizioErog;
    }

    public OrgServizioErog removeOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().remove(orgServizioErog);
        orgServizioErog.setOrgTariffa(null);

        return orgServizioErog;
    }

    // bi-directional many-to-one association to OrgClasseEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSE_ENTE_CONVENZ")
    public OrgClasseEnteConvenz getOrgClasseEnteConvenz() {
        return this.orgClasseEnteConvenz;
    }

    public void setOrgClasseEnteConvenz(OrgClasseEnteConvenz orgClasseEnteConvenz) {
        this.orgClasseEnteConvenz = orgClasseEnteConvenz;
    }

    // bi-directional many-to-one association to OrgTariffario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TARIFFARIO")
    public OrgTariffario getOrgTariffario() {
        return this.orgTariffario;
    }

    public void setOrgTariffario(OrgTariffario orgTariffario) {
        this.orgTariffario = orgTariffario;
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

}
