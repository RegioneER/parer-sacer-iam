package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_TIPO_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_TIPO_ACCORDO")
@NamedQuery(name = "OrgTipoAccordo.findAll", query = "SELECT o FROM OrgTipoAccordo o")
public class OrgTipoAccordo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoAccordo;
    private String cdAlgoTariffario;
    private String cdTipoAccordo;
    private String dsTipoAccordo;
    private Date dtIstituz;
    private Date dtSoppres;
    private String flPagamento;
    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();
    private List<OrgTariffario> orgTariffarios = new ArrayList<>();

    public OrgTipoAccordo() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_TIPO_ACCORDO") // @SequenceGenerator(name =
                                                                       // "ORG_TIPO_ACCORDO_IDTIPOACCORDO_GENERATOR",
                                                                       // sequenceName = "SORG_TIPO_ACCORDO",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_TIPO_ACCORDO_IDTIPOACCORDO_GENERATOR")
    @Column(name = "ID_TIPO_ACCORDO")
    public Long getIdTipoAccordo() {
        return this.idTipoAccordo;
    }

    public void setIdTipoAccordo(Long idTipoAccordo) {
        this.idTipoAccordo = idTipoAccordo;
    }

    @Column(name = "CD_ALGO_TARIFFARIO")
    public String getCdAlgoTariffario() {
        return this.cdAlgoTariffario;
    }

    public void setCdAlgoTariffario(String cdAlgoTariffario) {
        this.cdAlgoTariffario = cdAlgoTariffario;
    }

    @Column(name = "CD_TIPO_ACCORDO")
    public String getCdTipoAccordo() {
        return this.cdTipoAccordo;
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
        this.cdTipoAccordo = cdTipoAccordo;
    }

    @Column(name = "DS_TIPO_ACCORDO")
    public String getDsTipoAccordo() {
        return this.dsTipoAccordo;
    }

    public void setDsTipoAccordo(String dsTipoAccordo) {
        this.dsTipoAccordo = dsTipoAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ISTITUZ")
    public Date getDtIstituz() {
        return this.dtIstituz;
    }

    public void setDtIstituz(Date dtIstituz) {
        this.dtIstituz = dtIstituz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SOPPRES")
    public Date getDtSoppres() {
        return this.dtSoppres;
    }

    public void setDtSoppres(Date dtSoppres) {
        this.dtSoppres = dtSoppres;
    }

    @Column(name = "FL_PAGAMENTO", columnDefinition = "char(1)")
    public String getFlPagamento() {
        return this.flPagamento;
    }

    public void setFlPagamento(String flPagamento) {
        this.flPagamento = flPagamento;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgTipoAccordo")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().add(orgAccordoEnte);
        orgAccordoEnte.setOrgTipoAccordo(this);

        return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().remove(orgAccordoEnte);
        orgAccordoEnte.setOrgTipoAccordo(null);

        return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTariffario
    @OneToMany(mappedBy = "orgTipoAccordo")
    public List<OrgTariffario> getOrgTariffarios() {
        return this.orgTariffarios;
    }

    public void setOrgTariffarios(List<OrgTariffario> orgTariffarios) {
        this.orgTariffarios = orgTariffarios;
    }

    public OrgTariffario addOrgTariffario(OrgTariffario orgTariffario) {
        getOrgTariffarios().add(orgTariffario);
        orgTariffario.setOrgTipoAccordo(this);

        return orgTariffario;
    }

    public OrgTariffario removeOrgTariffario(OrgTariffario orgTariffario) {
        getOrgTariffarios().remove(orgTariffario);
        orgTariffario.setOrgTipoAccordo(null);

        return orgTariffario;
    }

}
