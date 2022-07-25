package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNAMB_BY_AMMIN database table.
 */
@Entity
@Table(name = "USR_V_UNAMB_BY_AMMIN")
@NamedQuery(name = "UsrVUnambByAmmin.findAll", query = "SELECT u FROM UsrVUnambByAmmin u")
public class UsrVUnambByAmmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleDich;

    private String nmAmbienteEnteConvenz;

    public UsrVUnambByAmmin() {
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    private UsrVUnambByAmminId usrVUnambByAmminId;

    @EmbeddedId()
    public UsrVUnambByAmminId getUsrVUnambByAmminId() {
        return usrVUnambByAmminId;
    }

    public void setUsrVUnambByAmminId(UsrVUnambByAmminId usrVUnambByAmminId) {
        this.usrVUnambByAmminId = usrVUnambByAmminId;
    }
}
