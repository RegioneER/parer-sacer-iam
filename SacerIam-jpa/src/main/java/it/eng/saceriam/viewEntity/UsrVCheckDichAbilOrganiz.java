package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHECK_DICH_ABIL_ORGANIZ database table.
 */
@Entity
@Table(name = "USR_V_CHECK_DICH_ABIL_ORGANIZ")
public class UsrVCheckDichAbilOrganiz implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    public UsrVCheckDichAbilOrganiz() {
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    private UsrVCheckDichAbilOrganizId usrVCheckDichAbilOrganizId;

    @EmbeddedId()
    public UsrVCheckDichAbilOrganizId getUsrVCheckDichAbilOrganizId() {
        return usrVCheckDichAbilOrganizId;
    }

    public void setUsrVCheckDichAbilOrganizId(UsrVCheckDichAbilOrganizId usrVCheckDichAbilOrganizId) {
        this.usrVCheckDichAbilOrganizId = usrVCheckDichAbilOrganizId;
    }
}
