package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_CLASSE_ENTE_CONVENZ database table.
 *
 */
@Entity
@Table(name = "ORG_CLASSE_ENTE_CONVENZ")
@NamedQuery(name = "OrgClasseEnteConvenz.findAll", query = "SELECT o FROM OrgClasseEnteConvenz o")
public class OrgClasseEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idClasseEnteConvenz;
    private String cdClasseEnteConvenz;
    private String dsClasseEnteConvenz;
    private BigDecimal niMaxAbit;
    private BigDecimal niMinAbit;
    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();
    private List<OrgTariffa> orgTariffas = new ArrayList<>();

    public OrgClasseEnteConvenz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_CLASSE_ENTE_CONVENZ") // @SequenceGenerator(name =
                                                                              // "ORG_CLASSE_ENTE_CONVENZ_IDCLASSEENTECONVENZ_GENERATOR",
                                                                              // sequenceName =
                                                                              // "SORG_CLASSE_ENTE_CONVENZ",
                                                                              // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_CLASSE_ENTE_CONVENZ_IDCLASSEENTECONVENZ_GENERATOR")
    @Column(name = "ID_CLASSE_ENTE_CONVENZ")
    public Long getIdClasseEnteConvenz() {
        return this.idClasseEnteConvenz;
    }

    public void setIdClasseEnteConvenz(Long idClasseEnteConvenz) {
        this.idClasseEnteConvenz = idClasseEnteConvenz;
    }

    @Column(name = "CD_CLASSE_ENTE_CONVENZ")
    public String getCdClasseEnteConvenz() {
        return this.cdClasseEnteConvenz;
    }

    public void setCdClasseEnteConvenz(String cdClasseEnteConvenz) {
        this.cdClasseEnteConvenz = cdClasseEnteConvenz;
    }

    @Column(name = "DS_CLASSE_ENTE_CONVENZ")
    public String getDsClasseEnteConvenz() {
        return this.dsClasseEnteConvenz;
    }

    public void setDsClasseEnteConvenz(String dsClasseEnteConvenz) {
        this.dsClasseEnteConvenz = dsClasseEnteConvenz;
    }

    @Column(name = "NI_MAX_ABIT")
    public BigDecimal getNiMaxAbit() {
        return this.niMaxAbit;
    }

    public void setNiMaxAbit(BigDecimal niMaxAbit) {
        this.niMaxAbit = niMaxAbit;
    }

    @Column(name = "NI_MIN_ABIT")
    public BigDecimal getNiMinAbit() {
        return this.niMinAbit;
    }

    public void setNiMinAbit(BigDecimal niMinAbit) {
        this.niMinAbit = niMinAbit;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgClasseEnteConvenz")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().add(orgAccordoEnte);
        orgAccordoEnte.setOrgClasseEnteConvenz(this);

        return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().remove(orgAccordoEnte);
        orgAccordoEnte.setOrgClasseEnteConvenz(null);

        return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTariffa
    @OneToMany(mappedBy = "orgClasseEnteConvenz")
    public List<OrgTariffa> getOrgTariffas() {
        return this.orgTariffas;
    }

    public void setOrgTariffas(List<OrgTariffa> orgTariffas) {
        this.orgTariffas = orgTariffas;
    }

    public OrgTariffa addOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().add(orgTariffa);
        orgTariffa.setOrgClasseEnteConvenz(this);

        return orgTariffa;
    }

    public OrgTariffa removeOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().remove(orgTariffa);
        orgTariffa.setOrgClasseEnteConvenz(null);

        return orgTariffa;
    }

}
