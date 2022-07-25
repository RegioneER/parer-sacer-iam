package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_ACCORDO_VIGIL database table.
 *
 */
@Entity
@Table(name = "ORG_ACCORDO_VIGIL")
@NamedQuery(name = "OrgAccordoVigil.findAll", query = "SELECT o FROM OrgAccordoVigil o")
public class OrgAccordoVigil implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAccordoVigil;
    private BigDecimal aaRepertorio;
    private String cdKeyRepertorio;
    private String cdRegistroRepertorio;
    private String dsFirmatarioEnte;
    private String dsNoteEnteAccordo;
    private Date dtFinVal;
    private Date dtIniVal;
    private Date dtRegAccordo;
    private String nmEnte;
    private String nmStrut;
    private OrgEnteSiam orgEnteSiamByIdEnteConserv;
    private OrgEnteSiam orgEnteSiamByIdEnteOrganoVigil;
    private List<OrgVigilEnteProdut> orgVigilEnteProduts = new ArrayList<>();

    public OrgAccordoVigil() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_ACCORDO_VIGIL") // @SequenceGenerator(name =
                                                                        // "ORG_ACCORDO_VIGIL_IDACCORDOVIGIL_GENERATOR",
                                                                        // sequenceName = "SORG_ACCORDO_VIGIL",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_ACCORDO_VIGIL_IDACCORDOVIGIL_GENERATOR")
    @Column(name = "ID_ACCORDO_VIGIL")
    public Long getIdAccordoVigil() {
        return this.idAccordoVigil;
    }

    public void setIdAccordoVigil(Long idAccordoVigil) {
        this.idAccordoVigil = idAccordoVigil;
    }

    @Column(name = "AA_REPERTORIO")
    public BigDecimal getAaRepertorio() {
        return this.aaRepertorio;
    }

    public void setAaRepertorio(BigDecimal aaRepertorio) {
        this.aaRepertorio = aaRepertorio;
    }

    @Column(name = "CD_KEY_REPERTORIO")
    public String getCdKeyRepertorio() {
        return this.cdKeyRepertorio;
    }

    public void setCdKeyRepertorio(String cdKeyRepertorio) {
        this.cdKeyRepertorio = cdKeyRepertorio;
    }

    @Column(name = "CD_REGISTRO_REPERTORIO")
    public String getCdRegistroRepertorio() {
        return this.cdRegistroRepertorio;
    }

    public void setCdRegistroRepertorio(String cdRegistroRepertorio) {
        this.cdRegistroRepertorio = cdRegistroRepertorio;
    }

    @Column(name = "DS_FIRMATARIO_ENTE")
    public String getDsFirmatarioEnte() {
        return this.dsFirmatarioEnte;
    }

    public void setDsFirmatarioEnte(String dsFirmatarioEnte) {
        this.dsFirmatarioEnte = dsFirmatarioEnte;
    }

    @Column(name = "DS_NOTE_ENTE_ACCORDO")
    public String getDsNoteEnteAccordo() {
        return this.dsNoteEnteAccordo;
    }

    public void setDsNoteEnteAccordo(String dsNoteEnteAccordo) {
        this.dsNoteEnteAccordo = dsNoteEnteAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL")
    public Date getDtFinVal() {
        return this.dtFinVal;
    }

    public void setDtFinVal(Date dtFinVal) {
        this.dtFinVal = dtFinVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_ACCORDO")
    public Date getDtRegAccordo() {
        return this.dtRegAccordo;
    }

    public void setDtRegAccordo(Date dtRegAccordo) {
        this.dtRegAccordo = dtRegAccordo;
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

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONSERV")
    public OrgEnteSiam getOrgEnteSiamByIdEnteConserv() {
        return this.orgEnteSiamByIdEnteConserv;
    }

    public void setOrgEnteSiamByIdEnteConserv(OrgEnteSiam orgEnteSiamByIdEnteConserv) {
        this.orgEnteSiamByIdEnteConserv = orgEnteSiamByIdEnteConserv;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_ORGANO_VIGIL")
    public OrgEnteSiam getOrgEnteSiamByIdEnteOrganoVigil() {
        return this.orgEnteSiamByIdEnteOrganoVigil;
    }

    public void setOrgEnteSiamByIdEnteOrganoVigil(OrgEnteSiam orgEnteSiamByIdEnteOrganoVigil) {
        this.orgEnteSiamByIdEnteOrganoVigil = orgEnteSiamByIdEnteOrganoVigil;
    }

    // bi-directional many-to-one association to OrgVigilEnteProdut
    @OneToMany(mappedBy = "orgAccordoVigil")
    public List<OrgVigilEnteProdut> getOrgVigilEnteProduts() {
        return this.orgVigilEnteProduts;
    }

    public void setOrgVigilEnteProduts(List<OrgVigilEnteProdut> orgVigilEnteProduts) {
        this.orgVigilEnteProduts = orgVigilEnteProduts;
    }

    public OrgVigilEnteProdut addOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
        getOrgVigilEnteProduts().add(orgVigilEnteProdut);
        orgVigilEnteProdut.setOrgAccordoVigil(this);

        return orgVigilEnteProdut;
    }

    public OrgVigilEnteProdut removeOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
        getOrgVigilEnteProduts().remove(orgVigilEnteProdut);
        orgVigilEnteProdut.setOrgAccordoVigil(null);

        return orgVigilEnteProdut;
    }
}
