package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_LIS_ENTE_BY_ABIL_ORG database table.
 */
@Entity
@Table(name = "USR_V_LIS_ENTE_BY_ABIL_ORG")
@NamedQuery(name = "UsrVLisEnteByAbilOrg.findAll", query = "SELECT u FROM UsrVLisEnteByAbilOrg u")
public class UsrVLisEnteByAbilOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    public UsrVLisEnteByAbilOrg() {
    }

    private UsrVLisEnteByAbilOrgId usrVLisEnteByAbilOrgId;

    @EmbeddedId()
    public UsrVLisEnteByAbilOrgId getUsrVLisEnteByAbilOrgId() {
        return usrVLisEnteByAbilOrgId;
    }

    public void setUsrVLisEnteByAbilOrgId(UsrVLisEnteByAbilOrgId usrVLisEnteByAbilOrgId) {
        this.usrVLisEnteByAbilOrgId = usrVLisEnteByAbilOrgId;
    }
}
