package it.eng.saceriam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_TARIFFA_AA_ACCORDO database table.
 * 
 */
@Entity
@Table(name = "ORG_TARIFFA_AA_ACCORDO")
@NamedQuery(name = "OrgTariffaAaAccordo.findAll", query = "SELECT o FROM OrgTariffaAaAccordo o")
public class OrgTariffaAaAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idTariffaAaAccordo;
    private BigDecimal imTariffaAaAccordo;
    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();
    private OrgAaAccordo orgAaAccordo;
    private OrgTipoServizio orgTipoServizio;

    public OrgTariffaAaAccordo() {
    }

    @Id
    @SequenceGenerator(name = "ORG_TARIFFA_AA_ACCORDO_IDTARIFFAAAACCORDO_GENERATOR", sequenceName = "SORG_TARIFFA_AA_ACCORDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_TARIFFA_AA_ACCORDO_IDTARIFFAAAACCORDO_GENERATOR")
    @Column(name = "ID_TARIFFA_AA_ACCORDO")
    public Long getIdTariffaAaAccordo() {
        return this.idTariffaAaAccordo;
    }

    public void setIdTariffaAaAccordo(Long idTariffaAaAccordo) {
        this.idTariffaAaAccordo = idTariffaAaAccordo;
    }

    @Column(name = "IM_TARIFFA_AA_ACCORDO")
    public BigDecimal getImTariffaAaAccordo() {
        return this.imTariffaAaAccordo;
    }

    public void setImTariffaAaAccordo(BigDecimal imTariffaAaAccordo) {
        this.imTariffaAaAccordo = imTariffaAaAccordo;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "orgTariffaAaAccordo", cascade = CascadeType.REMOVE)
    public List<OrgServizioErog> getOrgServizioErogs() {
        return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
        this.orgServizioErogs = orgServizioErogs;
    }

    // bi-directional many-to-one association to OrgAaAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AA_ACCORDO")
    public OrgAaAccordo getOrgAaAccordo() {
        return this.orgAaAccordo;
    }

    public void setOrgAaAccordo(OrgAaAccordo orgAaAccordo) {
        this.orgAaAccordo = orgAaAccordo;
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