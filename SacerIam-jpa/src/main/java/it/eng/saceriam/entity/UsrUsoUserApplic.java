package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the USR_USO_USER_APPLIC database table.
 *
 */
@Entity
@Table(name = "USR_USO_USER_APPLIC")
public class UsrUsoUserApplic implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUsoUserApplic;
    private List<UsrDichAbilDati> usrDichAbilDatis = new ArrayList<>();
    private List<UsrDichAbilOrganiz> usrDichAbilOrganizs = new ArrayList<>();
    private List<UsrUsoRuoloUserDefault> usrUsoRuoloUserDefaults = new ArrayList<>();
    private AplApplic aplApplic;
    private UsrUser usrUser;
    private List<UsrAbilDati> usrAbilDatis = new ArrayList<>();
    private List<UsrAbilOrganiz> usrAbilOrganizs = new ArrayList<>();

    public UsrUsoUserApplic() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_USO_USER_APPLIC") // @SequenceGenerator(name =
                                                                          // "USR_USO_USER_APPLIC_IDUSOUSERAPPLIC_GENERATOR",
                                                                          // sequenceName = "SUSR_USO_USER_APPLIC",
                                                                          // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_USO_USER_APPLIC_IDUSOUSERAPPLIC_GENERATOR")
    @Column(name = "ID_USO_USER_APPLIC")
    public Long getIdUsoUserApplic() {
        return this.idUsoUserApplic;
    }

    public void setIdUsoUserApplic(Long idUsoUserApplic) {
        this.idUsoUserApplic = idUsoUserApplic;
    }

    // bi-directional many-to-one association to UsrAbilDati
    @OneToMany(mappedBy = "usrUsoUserApplic")
    public List<UsrAbilDati> getUsrAbilDatis() {
        return this.usrAbilDatis;
    }

    public void setUsrAbilDatis(List<UsrAbilDati> usrAbilDatis) {
        this.usrAbilDatis = usrAbilDatis;
    }

    // bi-directional many-to-one association to UsrAbilOrganiz
    @OneToMany(mappedBy = "usrUsoUserApplic")
    public List<UsrAbilOrganiz> getUsrAbilOrganizs() {
        return this.usrAbilOrganizs;
    }

    public void setUsrAbilOrganizs(List<UsrAbilOrganiz> usrAbilOrganizs) {
        this.usrAbilOrganizs = usrAbilOrganizs;
    }

    // bi-directional many-to-one association to UsrDichAbilDati
    @OneToMany(mappedBy = "usrUsoUserApplic", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrDichAbilDati> getUsrDichAbilDatis() {
        return this.usrDichAbilDatis;
    }

    public void setUsrDichAbilDatis(List<UsrDichAbilDati> usrDichAbilDatis) {
        this.usrDichAbilDatis = usrDichAbilDatis;
    }

    // bi-directional many-to-one association to UsrDichAbilOrganiz
    @OneToMany(mappedBy = "usrUsoUserApplic", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrDichAbilOrganiz> getUsrDichAbilOrganizs() {
        return this.usrDichAbilOrganizs;
    }

    public void setUsrDichAbilOrganizs(List<UsrDichAbilOrganiz> usrDichAbilOrganizs) {
        this.usrDichAbilOrganizs = usrDichAbilOrganizs;
    }

    // bi-directional many-to-one association to UsrUsoRuoloUserDefault
    @OneToMany(mappedBy = "usrUsoUserApplic", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrUsoRuoloUserDefault> getUsrUsoRuoloUserDefaults() {
        return this.usrUsoRuoloUserDefaults;
    }

    public void setUsrUsoRuoloUserDefaults(List<UsrUsoRuoloUserDefault> usrUsoRuoloUserDefaults) {
        this.usrUsoRuoloUserDefaults = usrUsoRuoloUserDefaults;
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

    // bi-directional many-to-one association to UsrUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM")
    public UsrUser getUsrUser() {
        return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
        this.usrUser = usrUser;
    }
}
