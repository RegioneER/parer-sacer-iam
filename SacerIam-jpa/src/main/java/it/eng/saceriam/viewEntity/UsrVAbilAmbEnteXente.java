package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the USR_V_ABIL_AMB_ENTE_XENTE database table.
 */
@Entity
@Table(name = "USR_V_ABIL_AMB_ENTE_XENTE")
@NamedQuery(name = "UsrVAbilAmbEnteXente.findAll", query = "SELECT u FROM UsrVAbilAmbEnteXente u")
public class UsrVAbilAmbEnteXente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idUserIam;

    private String nmAmbienteEnteConvenz;

    public UsrVAbilAmbEnteXente() {
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

    private UsrVAbilAmbEnteXenteId usrVAbilAmbEnteXenteId;

    @EmbeddedId()
    public UsrVAbilAmbEnteXenteId getUsrVAbilAmbEnteXenteId() {
        return usrVAbilAmbEnteXenteId;
    }

    public void setUsrVAbilAmbEnteXenteId(UsrVAbilAmbEnteXenteId usrVAbilAmbEnteXenteId) {
        this.usrVAbilAmbEnteXenteId = usrVAbilAmbEnteXenteId;
    }
}
