package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_APPLIC database table.
 */
@Entity
@Table(name = "USR_V_ABIL_APPLIC")
@NamedQuery(name = "UsrVAbilApplic.findAll", query = "SELECT u FROM UsrVAbilApplic u")
public class UsrVAbilApplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nmApplic;

    private String nmUserid;

    public UsrVAbilApplic() {
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    private UsrVAbilApplicId usrVAbilApplicId;

    @EmbeddedId()
    public UsrVAbilApplicId getUsrVAbilApplicId() {
        return usrVAbilApplicId;
    }

    public void setUsrVAbilApplicId(UsrVAbilApplicId usrVAbilApplicId) {
        this.usrVAbilApplicId = usrVAbilApplicId;
    }
}
