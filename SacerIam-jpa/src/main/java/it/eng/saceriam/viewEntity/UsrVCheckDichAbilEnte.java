package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHECK_DICH_ABIL_ENTE database table.
 */
@Entity
@Table(name = "USR_V_CHECK_DICH_ABIL_ENTE")
@NamedQuery(name = "UsrVCheckDichAbilEnte.findAll", query = "SELECT u FROM UsrVCheckDichAbilEnte u")
public class UsrVCheckDichAbilEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nmEnteConvenz;

    public UsrVCheckDichAbilEnte() {
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    private UsrVCheckDichAbilEnteId usrVCheckDichAbilEnteId;

    @EmbeddedId()
    public UsrVCheckDichAbilEnteId getUsrVCheckDichAbilEnteId() {
        return usrVCheckDichAbilEnteId;
    }

    public void setUsrVCheckDichAbilEnteId(UsrVCheckDichAbilEnteId usrVCheckDichAbilEnteId) {
        this.usrVCheckDichAbilEnteId = usrVCheckDichAbilEnteId;
    }
}
