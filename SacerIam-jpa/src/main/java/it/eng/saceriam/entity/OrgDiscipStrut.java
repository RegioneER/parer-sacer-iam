package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the ORG_DISCIP_STRUT database table.
 *
 */
@Entity
@Table(name = "ORG_DISCIP_STRUT")
@NamedQuery(name = "OrgDiscipStrut.findAll", query = "SELECT o FROM OrgDiscipStrut o")
public class OrgDiscipStrut implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idDiscipStrut;
    private BigDecimal aaDiscipStrut;
    private byte[] blDiscipStrut;
    private String cdKeyDiscipStrut;
    private String cdRegistroDiscipStrut;
    private String dsDiscipStrut;
    private String dsNoteDiscipStrut;
    private Date dtDiscipStrut;
    private String flInseritoManualmente;
    private OrgAccordoEnte orgAccordoEnte;
    private OrgEnteSiam orgEnteConvenz;
    private UsrOrganizIam usrOrganizIam;
    private String enteDiscipStrut;
    private String strutturaDiscipStrut;

    public OrgDiscipStrut() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_DISCIP_STRUT") // @SequenceGenerator(name =
                                                                       // "ORG_DISCIP_STRUT_IDDISCIPSTRUT_GENERATOR",
                                                                       // sequenceName = "SORG_DISCIP_STRUT",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_DISCIP_STRUT_IDDISCIPSTRUT_GENERATOR")
    @Column(name = "ID_DISCIP_STRUT")
    public Long getIdDiscipStrut() {
        return this.idDiscipStrut;
    }

    public void setIdDiscipStrut(Long idDiscipStrut) {
        this.idDiscipStrut = idDiscipStrut;
    }

    @Column(name = "AA_DISCIP_STRUT")
    public BigDecimal getAaDiscipStrut() {
        return this.aaDiscipStrut;
    }

    public void setAaDiscipStrut(BigDecimal aaDiscipStrut) {
        this.aaDiscipStrut = aaDiscipStrut;
    }

    @Lob
    @Column(name = "BL_DISCIP_STRUT")
    public byte[] getBlDiscipStrut() {
        return this.blDiscipStrut;
    }

    public void setBlDiscipStrut(byte[] blDiscipStrut) {
        this.blDiscipStrut = blDiscipStrut;
    }

    @Column(name = "CD_KEY_DISCIP_STRUT")
    public String getCdKeyDiscipStrut() {
        return this.cdKeyDiscipStrut;
    }

    public void setCdKeyDiscipStrut(String cdKeyDiscipStrut) {
        this.cdKeyDiscipStrut = cdKeyDiscipStrut;
    }

    @Column(name = "CD_REGISTRO_DISCIP_STRUT")
    public String getCdRegistroDiscipStrut() {
        return this.cdRegistroDiscipStrut;
    }

    public void setCdRegistroDiscipStrut(String cdRegistroDiscipStrut) {
        this.cdRegistroDiscipStrut = cdRegistroDiscipStrut;
    }

    @Column(name = "DS_DISCIP_STRUT")
    public String getDsDiscipStrut() {
        return this.dsDiscipStrut;
    }

    public void setDsDiscipStrut(String dsDiscipStrut) {
        this.dsDiscipStrut = dsDiscipStrut;
    }

    @Column(name = "DS_NOTE_DISCIP_STRUT")
    public String getDsNoteDiscipStrut() {
        return this.dsNoteDiscipStrut;
    }

    public void setDsNoteDiscipStrut(String dsNoteDiscipStrut) {
        this.dsNoteDiscipStrut = dsNoteDiscipStrut;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DISCIP_STRUT")
    public Date getDtDiscipStrut() {
        return this.dtDiscipStrut;
    }

    public void setDtDiscipStrut(Date dtDiscipStrut) {
        this.dtDiscipStrut = dtDiscipStrut;
    }

    @Column(name = "FL_INSERITO_MANUALMENTE", columnDefinition = "char(1)")
    public String getFlInseritoManualmente() {
        return this.flInseritoManualmente;
    }

    public void setFlInseritoManualmente(String flInseritoManualmente) {
        this.flInseritoManualmente = flInseritoManualmente;
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

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteConvenz() {
        return this.orgEnteConvenz;
    }

    public void setOrgEnteConvenz(OrgEnteSiam orgEnteConvenz) {
        this.orgEnteConvenz = orgEnteConvenz;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZ_IAM")
    public UsrOrganizIam getUsrOrganizIam() {
        return this.usrOrganizIam;
    }

    public void setUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
        this.usrOrganizIam = usrOrganizIam;
    }

    @Column(name = "ENTE_DISCIP_STRUT")
    public String getEnteDiscipStrut() {
        return enteDiscipStrut;
    }

    public void setEnteDiscipStrut(String enteDiscipStrut) {
        this.enteDiscipStrut = enteDiscipStrut;
    }

    @Column(name = "STRUTTURA_DISCIP_STRUT")
    public String getStrutturaDiscipStrut() {
        return strutturaDiscipStrut;
    }

    public void setStrutturaDiscipStrut(String strutturaDiscipStrut) {
        this.strutturaDiscipStrut = strutturaDiscipStrut;
    }

}
