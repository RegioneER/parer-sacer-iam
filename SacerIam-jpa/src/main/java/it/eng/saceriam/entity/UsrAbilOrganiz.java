package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_ABIL_ORGANIZ database table.
 * 
 */
@Entity
@Table(name = "USR_ABIL_ORGANIZ")
@NamedQuery(name = "UsrAbilOrganiz.findAll", query = "SELECT u FROM UsrAbilOrganiz u")
public class UsrAbilOrganiz implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idAbilOrganiz;
    private String dsCausaleAbil;
    private String flAbilAutomatica;
    private OrgAppartCollegEnti orgAppartCollegEnti;
    private OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz;
    private OrgVigilEnteProdut orgVigilEnteProdut;
    private UsrDichAbilOrganiz usrDichAbilOrganiz;
    private UsrOrganizIam usrOrganizIam;
    private UsrUsoUserApplic usrUsoUserApplic;

    public UsrAbilOrganiz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_ABIL_ORGANIZ") // @SequenceGenerator(name =
                                                                       // "USR_ABIL_ORGANIZ_IDABILORGANIZ_GENERATOR",
                                                                       // sequenceName = "SUSR_ABIL_ORGANIZ",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_ABIL_ORGANIZ_IDABILORGANIZ_GENERATOR")
    @Column(name = "ID_ABIL_ORGANIZ")
    public Long getIdAbilOrganiz() {
        return this.idAbilOrganiz;
    }

    public void setIdAbilOrganiz(Long idAbilOrganiz) {
        this.idAbilOrganiz = idAbilOrganiz;
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
        return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
        this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "FL_ABIL_AUTOMATICA", columnDefinition = "char(1)")
    public String getFlAbilAutomatica() {
        return this.flAbilAutomatica;
    }

    public void setFlAbilAutomatica(String flAbilAutomatica) {
        this.flAbilAutomatica = flAbilAutomatica;
    }

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPART_COLLEG_ENTI")
    public OrgAppartCollegEnti getOrgAppartCollegEnti() {
        return this.orgAppartCollegEnti;
    }

    public void setOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
        this.orgAppartCollegEnti = orgAppartCollegEnti;
    }

    // bi-directional many-to-one association to OrgSuptEsternoEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public OrgSuptEsternoEnteConvenz getOrgSuptEsternoEnteConvenz() {
        return this.orgSuptEsternoEnteConvenz;
    }

    public void setOrgSuptEsternoEnteConvenz(OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz) {
        this.orgSuptEsternoEnteConvenz = orgSuptEsternoEnteConvenz;
    }

    // bi-directional many-to-one association to OrgVigilEnteProdut
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VIGIL_ENTE_PRODUT")
    public OrgVigilEnteProdut getOrgVigilEnteProdut() {
        return this.orgVigilEnteProdut;
    }

    public void setOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
        this.orgVigilEnteProdut = orgVigilEnteProdut;
    }

    // bi-directional many-to-one association to UsrDichAbilOrganiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DICH_ABIL_ORGANIZ")
    public UsrDichAbilOrganiz getUsrDichAbilOrganiz() {
        return this.usrDichAbilOrganiz;
    }

    public void setUsrDichAbilOrganiz(UsrDichAbilOrganiz usrDichAbilOrganiz) {
        this.usrDichAbilOrganiz = usrDichAbilOrganiz;
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

    // bi-directional many-to-one association to UsrUsoUserApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USO_USER_APPLIC")
    public UsrUsoUserApplic getUsrUsoUserApplic() {
        return this.usrUsoUserApplic;
    }

    public void setUsrUsoUserApplic(UsrUsoUserApplic usrUsoUserApplic) {
        this.usrUsoUserApplic = usrUsoUserApplic;
    }

}