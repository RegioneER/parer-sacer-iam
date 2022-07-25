package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the ORG_SCAGLIONE_TARIFFA database table.
 *
 */
@Entity
@Table(name = "ORG_SCAGLIONE_TARIFFA")
@NamedQuery(name = "OrgScaglioneTariffa.findAll", query = "SELECT o FROM OrgScaglioneTariffa o")
public class OrgScaglioneTariffa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idScaglioneTariffa;
    private BigDecimal imScaglione;
    private BigDecimal niFineScaglione;
    private BigDecimal niIniScaglione;
    private OrgTariffa orgTariffa;

    public OrgScaglioneTariffa() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_SCAGLIONE_TARIFFA") // @SequenceGenerator(name =
                                                                            // "ORG_SCAGLIONE_TARIFFA_IDSCAGLIONETARIFFA_GENERATOR",
                                                                            // sequenceName = "SORG_SCAGLIONE_TARIFFA",
                                                                            // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_SCAGLIONE_TARIFFA_IDSCAGLIONETARIFFA_GENERATOR")
    @Column(name = "ID_SCAGLIONE_TARIFFA")
    public Long getIdScaglioneTariffa() {
        return this.idScaglioneTariffa;
    }

    public void setIdScaglioneTariffa(Long idScaglioneTariffa) {
        this.idScaglioneTariffa = idScaglioneTariffa;
    }

    @Column(name = "IM_SCAGLIONE")
    public BigDecimal getImScaglione() {
        return this.imScaglione;
    }

    public void setImScaglione(BigDecimal imScaglione) {
        this.imScaglione = imScaglione;
    }

    @Column(name = "NI_FINE_SCAGLIONE")
    public BigDecimal getNiFineScaglione() {
        return this.niFineScaglione;
    }

    public void setNiFineScaglione(BigDecimal niFineScaglione) {
        this.niFineScaglione = niFineScaglione;
    }

    @Column(name = "NI_INI_SCAGLIONE")
    public BigDecimal getNiIniScaglione() {
        return this.niIniScaglione;
    }

    public void setNiIniScaglione(BigDecimal niIniScaglione) {
        this.niIniScaglione = niIniScaglione;
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

}
