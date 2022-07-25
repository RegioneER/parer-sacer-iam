package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the USR_DICH_ABIL_ORGANIZ database table.
 *
 */
@Entity
@Table(name = "USR_DICH_ABIL_ORGANIZ")
@NamedQuery(name = "UsrDichAbilOrganiz.findAll", query = "SELECT u FROM UsrDichAbilOrganiz u")
public class UsrDichAbilOrganiz implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idDichAbilOrganiz;
    private String dsCausaleDich;
    private String tiScopoDichAbilOrganiz;
    private List<UsrAbilOrganiz> usrAbilOrganizs = new ArrayList<>();
    private OrgAppartCollegEnti orgAppartCollegEnti;
    private OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz;
    private OrgVigilEnteProdut orgVigilEnteProdut;
    private UsrOrganizIam usrOrganizIam;
    private UsrUsoUserApplic usrUsoUserApplic;
    private List<UsrUsoRuoloDich> usrUsoRuoloDiches = new ArrayList<>();

    public UsrDichAbilOrganiz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_DICH_ABIL_ORGANIZ") // @SequenceGenerator(name =
                                                                            // "USR_DICH_ABIL_ORGANIZ_IDDICHABILORGANIZ_GENERATOR",
                                                                            // sequenceName = "SUSR_DICH_ABIL_ORGANIZ",
                                                                            // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "USR_DICH_ABIL_ORGANIZ_IDDICHABILORGANIZ_GENERATOR")
    @Column(name = "ID_DICH_ABIL_ORGANIZ")
    public Long getIdDichAbilOrganiz() {
        return this.idDichAbilOrganiz;
    }

    public void setIdDichAbilOrganiz(Long idDichAbilOrganiz) {
        this.idDichAbilOrganiz = idDichAbilOrganiz;
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "TI_SCOPO_DICH_ABIL_ORGANIZ")
    public String getTiScopoDichAbilOrganiz() {
        return this.tiScopoDichAbilOrganiz;
    }

    public void setTiScopoDichAbilOrganiz(String tiScopoDichAbilOrganiz) {
        this.tiScopoDichAbilOrganiz = tiScopoDichAbilOrganiz;
    }

    // bi-directional many-to-one association to UsrAbilOrganiz
    @OneToMany(mappedBy = "usrDichAbilOrganiz")
    public List<UsrAbilOrganiz> getUsrAbilOrganizs() {
        return this.usrAbilOrganizs;
    }

    public void setUsrAbilOrganizs(List<UsrAbilOrganiz> usrAbilOrganizs) {
        this.usrAbilOrganizs = usrAbilOrganizs;
    }

    public UsrAbilOrganiz addUsrAbilOrganiz(UsrAbilOrganiz usrAbilOrganiz) {
        getUsrAbilOrganizs().add(usrAbilOrganiz);
        usrAbilOrganiz.setUsrDichAbilOrganiz(this);

        return usrAbilOrganiz;
    }

    public UsrAbilOrganiz removeUsrAbilOrganiz(UsrAbilOrganiz usrAbilOrganiz) {
        getUsrAbilOrganizs().remove(usrAbilOrganiz);
        usrAbilOrganiz.setUsrDichAbilOrganiz(null);

        return usrAbilOrganiz;
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

    // bi-directional many-to-one association to UsrUsoRuoloDich
    @OneToMany(cascade = { CascadeType.PERSIST }, mappedBy = "usrDichAbilOrganiz")
    public List<UsrUsoRuoloDich> getUsrUsoRuoloDiches() {
        return this.usrUsoRuoloDiches;
    }

    public void setUsrUsoRuoloDiches(List<UsrUsoRuoloDich> usrUsoRuoloDiches) {
        this.usrUsoRuoloDiches = usrUsoRuoloDiches;
    }

    public UsrUsoRuoloDich addUsrUsoRuoloDich(UsrUsoRuoloDich usrUsoRuoloDich) {
        getUsrUsoRuoloDiches().add(usrUsoRuoloDich);
        usrUsoRuoloDich.setUsrDichAbilOrganiz(this);

        return usrUsoRuoloDich;
    }

    public UsrUsoRuoloDich removeUsrUsoRuoloDich(UsrUsoRuoloDich usrUsoRuoloDich) {
        getUsrUsoRuoloDiches().remove(usrUsoRuoloDich);
        usrUsoRuoloDich.setUsrDichAbilOrganiz(null);

        return usrUsoRuoloDich;
    }

}