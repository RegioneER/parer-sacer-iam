package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_AMB_ENTE_CONVENZ database table.
 */
@Entity
@Table(name = "USR_V_ABIL_AMB_ENTE_CONVENZ")
@NamedQuery(name = "UsrVAbilAmbEnteConvenz.findAll", query = "SELECT u FROM UsrVAbilAmbEnteConvenz u")
public class UsrVAbilAmbEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String nmAmbienteEnteConvenz;

    private String nmUserid;

    public UsrVAbilAmbEnteConvenz() {
    }

    public UsrVAbilAmbEnteConvenz(BigDecimal idAmbienteEnteConvenz, String nmAmbienteEnteConvenz,
            String dsAmbienteEnteConvenz) {
        this.usrVAbilAmbEnteConvenzId = new UsrVAbilAmbEnteConvenzId();
        this.usrVAbilAmbEnteConvenzId.setIdAmbienteEnteConvenz(idAmbienteEnteConvenz);
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    private UsrVAbilAmbEnteConvenzId usrVAbilAmbEnteConvenzId;

    @EmbeddedId()
    public UsrVAbilAmbEnteConvenzId getUsrVAbilAmbEnteConvenzId() {
        return usrVAbilAmbEnteConvenzId;
    }

    public void setUsrVAbilAmbEnteConvenzId(UsrVAbilAmbEnteConvenzId usrVAbilAmbEnteConvenzId) {
        this.usrVAbilAmbEnteConvenzId = usrVAbilAmbEnteConvenzId;
    }
}
