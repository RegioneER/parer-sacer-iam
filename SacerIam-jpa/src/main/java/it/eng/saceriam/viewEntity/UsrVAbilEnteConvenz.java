package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_ENTE_CONVENZ database table.
 */
@Entity
@Table(name = "USR_V_ABIL_ENTE_CONVENZ")
@NamedQuery(name = "UsrVAbilEnteConvenz.findAll", query = "SELECT u FROM UsrVAbilEnteConvenz u")
public class UsrVAbilEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idAmbienteEnteConvenz;

    private String nmAmbienteEnteConvenz;

    private String nmEnteConvenz;

    private String nmUserid;

    public UsrVAbilEnteConvenz() {
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    private UsrVAbilEnteConvenzId usrVAbilEnteConvenzId;

    @EmbeddedId()
    public UsrVAbilEnteConvenzId getUsrVAbilEnteConvenzId() {
        return usrVAbilEnteConvenzId;
    }

    public void setUsrVAbilEnteConvenzId(UsrVAbilEnteConvenzId usrVAbilEnteConvenzId) {
        this.usrVAbilEnteConvenzId = usrVAbilEnteConvenzId;
    }
}
