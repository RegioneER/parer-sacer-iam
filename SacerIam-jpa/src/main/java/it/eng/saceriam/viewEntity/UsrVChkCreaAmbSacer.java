package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHK_CREA_AMB_SACER database table.
 */
@Entity
@Table(name = "USR_V_CHK_CREA_AMB_SACER")
@NamedQuery(name = "UsrVChkCreaAmbSacer.findAll", query = "SELECT u FROM UsrVChkCreaAmbSacer u")
public class UsrVChkCreaAmbSacer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String flCreaAmbiente;

    private String nmApplic;

    public UsrVChkCreaAmbSacer() {
    }

    @Column(name = "FL_CREA_AMBIENTE", columnDefinition = "char(1)")
    public String getFlCreaAmbiente() {
        return this.flCreaAmbiente;
    }

    public void setFlCreaAmbiente(String flCreaAmbiente) {
        this.flCreaAmbiente = flCreaAmbiente;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVChkCreaAmbSacerId usrVChkCreaAmbSacerId;

    @EmbeddedId()
    public UsrVChkCreaAmbSacerId getUsrVChkCreaAmbSacerId() {
        return usrVChkCreaAmbSacerId;
    }

    public void setUsrVChkCreaAmbSacerId(UsrVChkCreaAmbSacerId usrVChkCreaAmbSacerId) {
        this.usrVChkCreaAmbSacerId = usrVChkCreaAmbSacerId;
    }
}
