package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ORG_ENTE_CONVENZ_ORG database table.
 *
 */
@Entity
@Table(name = "ORG_ENTE_CONVENZ_ORG")
@NamedQuery(name = "OrgEnteConvenzOrg.findAll", query = "SELECT o FROM OrgEnteConvenzOrg o")
public class OrgEnteConvenzOrg implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEnteConvenzOrg;
    private Date dtFineVal;
    private Date dtIniVal;
    private OrgEnteSiam orgEnteSiam;
    private UsrOrganizIam usrOrganizIam;

    public OrgEnteConvenzOrg() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_ENTE_CONVENZ_ORG") // @SequenceGenerator(name =
                                                                           // "ORG_ENTE_CONVENZ_ORG_IDENTECONVENZORG_GENERATOR",
                                                                           // sequenceName = "SORG_ENTE_CONVENZ_ORG",
                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_ENTE_CONVENZ_ORG_IDENTECONVENZORG_GENERATOR")
    @Column(name = "ID_ENTE_CONVENZ_ORG")
    public Long getIdEnteConvenzOrg() {
        return this.idEnteConvenzOrg;
    }

    public void setIdEnteConvenzOrg(Long idEnteConvenzOrg) {
        this.idEnteConvenzOrg = idEnteConvenzOrg;
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

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
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

}
