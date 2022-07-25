package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the USR_ORGANIZ_IAM database table.
 *
 */
@Entity
@Table(name = "USR_ORGANIZ_IAM")
@NamedQuery(name = "UsrOrganizIam.findAll", query = "SELECT u FROM UsrOrganizIam u")
public class UsrOrganizIam implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idOrganizIam;
    private String dsOrganiz;
    private BigDecimal idOrganizApplic;
    private String nmOrganiz;
    private List<OrgEnteConvenzOrg> orgEnteConvenzOrgs = new ArrayList<>();
    private List<UsrAbilOrganiz> usrAbilOrganizs = new ArrayList<>();
    private List<UsrDichAbilDati> usrDichAbilDatis = new ArrayList<>();
    private List<UsrDichAbilOrganiz> usrDichAbilOrganizs = new ArrayList<>();
    private AplApplic aplApplic;
    private AplTipoOrganiz aplTipoOrganiz;
    private OrgEnteSiam orgEnteSiamConserv;
    private OrgEnteSiam orgEnteSiamGestore;
    private UsrOrganizIam usrOrganizIam;
    private List<UsrOrganizIam> usrOrganizIams = new ArrayList<>();
    private List<UsrRichGestUser> usrRichGestUsers = new ArrayList<>();
    private List<UsrTipoDatoIam> usrTipoDatoIams = new ArrayList<>();
    private List<UsrUsoRuoloDich> usrUsoRuoloDiches = new ArrayList<>();

    public UsrOrganizIam() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_ORGANIZ_IAM") // @SequenceGenerator(name =
                                                                      // "USR_ORGANIZ_IAM_IDORGANIZIAM_GENERATOR",
                                                                      // sequenceName = "SUSR_ORGANIZ_IAM",
                                                                      // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_ORGANIZ_IAM_IDORGANIZIAM_GENERATOR")
    @Column(name = "ID_ORGANIZ_IAM")
    public Long getIdOrganizIam() {
        return this.idOrganizIam;
    }

    public void setIdOrganizIam(Long idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
    }

    @Column(name = "DS_ORGANIZ")
    public String getDsOrganiz() {
        return this.dsOrganiz;
    }

    public void setDsOrganiz(String dsOrganiz) {
        this.dsOrganiz = dsOrganiz;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
        return this.idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    @Column(name = "NM_ORGANIZ")
    public String getNmOrganiz() {
        return this.nmOrganiz;
    }

    public void setNmOrganiz(String nmOrganiz) {
        this.nmOrganiz = nmOrganiz;
    }

    // bi-directional many-to-one association to OrgEnteConvenzOrg
    @OneToMany(mappedBy = "usrOrganizIam", cascade = CascadeType.REMOVE)
    public List<OrgEnteConvenzOrg> getOrgEnteConvenzOrgs() {
        return this.orgEnteConvenzOrgs;
    }

    public void setOrgEnteConvenzOrgs(List<OrgEnteConvenzOrg> orgEnteConvenzOrgs) {
        this.orgEnteConvenzOrgs = orgEnteConvenzOrgs;
    }

    public OrgEnteConvenzOrg addOrgEnteConvenzOrg(OrgEnteConvenzOrg orgEnteConvenzOrg) {
        getOrgEnteConvenzOrgs().add(orgEnteConvenzOrg);
        orgEnteConvenzOrg.setUsrOrganizIam(this);

        return orgEnteConvenzOrg;
    }

    public OrgEnteConvenzOrg removeOrgEnteConvenzOrg(OrgEnteConvenzOrg orgEnteConvenzOrg) {
        getOrgEnteConvenzOrgs().remove(orgEnteConvenzOrg);
        orgEnteConvenzOrg.setUsrOrganizIam(null);

        return orgEnteConvenzOrg;
    }

    // bi-directional many-to-one association to UsrAbilOrganiz
    @OneToMany(mappedBy = "usrOrganizIam")
    public List<UsrAbilOrganiz> getUsrAbilOrganizs() {
        return this.usrAbilOrganizs;
    }

    public void setUsrAbilOrganizs(List<UsrAbilOrganiz> usrAbilOrganizs) {
        this.usrAbilOrganizs = usrAbilOrganizs;
    }

    public UsrAbilOrganiz addUsrAbilOrganiz(UsrAbilOrganiz usrAbilOrganiz) {
        getUsrAbilOrganizs().add(usrAbilOrganiz);
        usrAbilOrganiz.setUsrOrganizIam(this);

        return usrAbilOrganiz;
    }

    public UsrAbilOrganiz removeUsrAbilOrganiz(UsrAbilOrganiz usrAbilOrganiz) {
        getUsrAbilOrganizs().remove(usrAbilOrganiz);
        usrAbilOrganiz.setUsrOrganizIam(null);

        return usrAbilOrganiz;
    }

    // bi-directional many-to-one association to UsrDichAbilDati
    @OneToMany(mappedBy = "usrOrganizIam", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrDichAbilDati> getUsrDichAbilDatis() {
        return this.usrDichAbilDatis;
    }

    public void setUsrDichAbilDatis(List<UsrDichAbilDati> usrDichAbilDatis) {
        this.usrDichAbilDatis = usrDichAbilDatis;
    }

    public UsrDichAbilDati addUsrDichAbilDati(UsrDichAbilDati usrDichAbilDati) {
        getUsrDichAbilDatis().add(usrDichAbilDati);
        usrDichAbilDati.setUsrOrganizIam(this);

        return usrDichAbilDati;
    }

    public UsrDichAbilDati removeUsrDichAbilDati(UsrDichAbilDati usrDichAbilDati) {
        getUsrDichAbilDatis().remove(usrDichAbilDati);
        usrDichAbilDati.setUsrOrganizIam(null);

        return usrDichAbilDati;
    }

    // bi-directional many-to-one association to UsrDichAbilOrganiz
    @OneToMany(mappedBy = "usrOrganizIam", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrDichAbilOrganiz> getUsrDichAbilOrganizs() {
        return this.usrDichAbilOrganizs;
    }

    public void setUsrDichAbilOrganizs(List<UsrDichAbilOrganiz> usrDichAbilOrganizs) {
        this.usrDichAbilOrganizs = usrDichAbilOrganizs;
    }

    public UsrDichAbilOrganiz addUsrDichAbilOrganiz(UsrDichAbilOrganiz usrDichAbilOrganiz) {
        getUsrDichAbilOrganizs().add(usrDichAbilOrganiz);
        usrDichAbilOrganiz.setUsrOrganizIam(this);

        return usrDichAbilOrganiz;
    }

    public UsrDichAbilOrganiz removeUsrDichAbilOrganiz(UsrDichAbilOrganiz usrDichAbilOrganiz) {
        getUsrDichAbilOrganizs().remove(usrDichAbilOrganiz);
        usrDichAbilOrganiz.setUsrOrganizIam(null);

        return usrDichAbilOrganiz;
    }

    // bi-directional many-to-one association to AplApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPLIC")
    public AplApplic getAplApplic() {
        return this.aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
        this.aplApplic = aplApplic;
    }

    // bi-directional many-to-one association to AplTipoOrganiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_ORGANIZ")
    public AplTipoOrganiz getAplTipoOrganiz() {
        return this.aplTipoOrganiz;
    }

    public void setAplTipoOrganiz(AplTipoOrganiz aplTipoOrganiz) {
        this.aplTipoOrganiz = aplTipoOrganiz;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_GESTORE")
    public OrgEnteSiam getOrgEnteSiamGestore() {
        return this.orgEnteSiamGestore;
    }

    public void setOrgEnteSiamGestore(OrgEnteSiam orgEnteSiamGestore) {
        this.orgEnteSiamGestore = orgEnteSiamGestore;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONSERV")
    public OrgEnteSiam getOrgEnteSiamConserv() {
        return this.orgEnteSiamConserv;
    }

    public void setOrgEnteSiamConserv(OrgEnteSiam orgEnteSiamConserv) {
        this.orgEnteSiamConserv = orgEnteSiamConserv;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZ_IAM_PADRE")
    public UsrOrganizIam getUsrOrganizIam() {
        return this.usrOrganizIam;
    }

    public void setUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
        this.usrOrganizIam = usrOrganizIam;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @OneToMany(mappedBy = "usrOrganizIam")
    public List<UsrOrganizIam> getUsrOrganizIams() {
        return this.usrOrganizIams;
    }

    public void setUsrOrganizIams(List<UsrOrganizIam> usrOrganizIams) {
        this.usrOrganizIams = usrOrganizIams;
    }

    public UsrOrganizIam addUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
        getUsrOrganizIams().add(usrOrganizIam);
        usrOrganizIam.setUsrOrganizIam(this);

        return usrOrganizIam;
    }

    public UsrOrganizIam removeUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
        getUsrOrganizIams().remove(usrOrganizIam);
        usrOrganizIam.setUsrOrganizIam(null);

        return usrOrganizIam;
    }

    // bi-directional many-to-one association to UsrRichGestUser
    @OneToMany(mappedBy = "usrOrganizIam")
    public List<UsrRichGestUser> getUsrRichGestUsers() {
        return this.usrRichGestUsers;
    }

    public void setUsrRichGestUsers(List<UsrRichGestUser> usrRichGestUsers) {
        this.usrRichGestUsers = usrRichGestUsers;
    }

    public UsrRichGestUser addUsrRichGestUser(UsrRichGestUser usrRichGestUser) {
        getUsrRichGestUsers().add(usrRichGestUser);
        usrRichGestUser.setUsrOrganizIam(this);

        return usrRichGestUser;
    }

    public UsrRichGestUser removeUsrRichGestUser(UsrRichGestUser usrRichGestUser) {
        getUsrRichGestUsers().remove(usrRichGestUser);
        usrRichGestUser.setUsrOrganizIam(null);

        return usrRichGestUser;
    }

    // bi-directional many-to-one association to UsrTipoDatoIam
    @OneToMany(mappedBy = "usrOrganizIam", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrTipoDatoIam> getUsrTipoDatoIams() {
        return this.usrTipoDatoIams;
    }

    public void setUsrTipoDatoIams(List<UsrTipoDatoIam> usrTipoDatoIams) {
        this.usrTipoDatoIams = usrTipoDatoIams;
    }

    public UsrTipoDatoIam addUsrTipoDatoIam(UsrTipoDatoIam usrTipoDatoIam) {
        getUsrTipoDatoIams().add(usrTipoDatoIam);
        usrTipoDatoIam.setUsrOrganizIam(this);

        return usrTipoDatoIam;
    }

    public UsrTipoDatoIam removeUsrTipoDatoIam(UsrTipoDatoIam usrTipoDatoIam) {
        getUsrTipoDatoIams().remove(usrTipoDatoIam);
        usrTipoDatoIam.setUsrOrganizIam(null);

        return usrTipoDatoIam;
    }

    // bi-directional many-to-one association to UsrUsoRuoloDich
    @OneToMany(mappedBy = "usrOrganizIam", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrUsoRuoloDich> getUsrUsoRuoloDiches() {
        return this.usrUsoRuoloDiches;
    }

    public void setUsrUsoRuoloDiches(List<UsrUsoRuoloDich> usrUsoRuoloDiches) {
        this.usrUsoRuoloDiches = usrUsoRuoloDiches;
    }

    public UsrUsoRuoloDich addUsrUsoRuoloDich(UsrUsoRuoloDich usrUsoRuoloDich) {
        getUsrUsoRuoloDiches().add(usrUsoRuoloDich);
        usrUsoRuoloDich.setUsrOrganizIam(this);

        return usrUsoRuoloDich;
    }

    public UsrUsoRuoloDich removeUsrUsoRuoloDich(UsrUsoRuoloDich usrUsoRuoloDich) {
        getUsrUsoRuoloDiches().remove(usrUsoRuoloDich);
        usrUsoRuoloDich.setUsrOrganizIam(null);

        return usrUsoRuoloDich;
    }

}
