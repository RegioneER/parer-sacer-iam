package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_TARIFFARIO database table.
 *
 */
@Entity
@Table(name = "ORG_TARIFFARIO")
@NamedQuery(name = "OrgTariffario.findAll", query = "SELECT o FROM OrgTariffario o")
public class OrgTariffario implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTariffario;
    private Date dtFineVal;
    private Date dtIniVal;
    private String nmTariffario;
    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();
    private List<OrgTariffa> orgTariffas = new ArrayList<>();
    private OrgTipoAccordo orgTipoAccordo;

    public OrgTariffario() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_TARIFFARIO") // @SequenceGenerator(name =
                                                                     // "ORG_TARIFFARIO_IDTARIFFARIO_GENERATOR",
                                                                     // sequenceName = "SORG_TARIFFARIO", allocationSize
                                                                     // = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_TARIFFARIO_IDTARIFFARIO_GENERATOR")
    @Column(name = "ID_TARIFFARIO")
    public Long getIdTariffario() {
        return this.idTariffario;
    }

    public void setIdTariffario(Long idTariffario) {
        this.idTariffario = idTariffario;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "NM_TARIFFARIO")
    public String getNmTariffario() {
        return this.nmTariffario;
    }

    public void setNmTariffario(String nmTariffario) {
        this.nmTariffario = nmTariffario;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgTariffario")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().add(orgAccordoEnte);
        orgAccordoEnte.setOrgTariffario(this);

        return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().remove(orgAccordoEnte);
        orgAccordoEnte.setOrgTariffario(null);

        return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTariffa
    @OneToMany(mappedBy = "orgTariffario")
    public List<OrgTariffa> getOrgTariffas() {
        return this.orgTariffas;
    }

    public void setOrgTariffas(List<OrgTariffa> orgTariffas) {
        this.orgTariffas = orgTariffas;
    }

    public OrgTariffa addOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().add(orgTariffa);
        orgTariffa.setOrgTariffario(this);

        return orgTariffa;
    }

    public OrgTariffa removeOrgTariffa(OrgTariffa orgTariffa) {
        getOrgTariffas().remove(orgTariffa);
        orgTariffa.setOrgTariffario(null);

        return orgTariffa;
    }

    // bi-directional many-to-one association to OrgTipoAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_ACCORDO")
    public OrgTipoAccordo getOrgTipoAccordo() {
        return this.orgTipoAccordo;
    }

    public void setOrgTipoAccordo(OrgTipoAccordo orgTipoAccordo) {
        this.orgTipoAccordo = orgTipoAccordo;
    }

}
