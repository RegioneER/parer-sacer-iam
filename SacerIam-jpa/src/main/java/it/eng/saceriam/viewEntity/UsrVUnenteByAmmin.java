package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNENTE_BY_AMMIN database table.
 */
@Entity
@Table(name = "USR_V_UNENTE_BY_AMMIN")
@NamedQuery(name = "UsrVUnenteByAmmin.findAll", query = "SELECT u FROM UsrVUnenteByAmmin u")
public class UsrVUnenteByAmmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleDich;

    private BigDecimal idAmbienteEnteConvenz;

    private String nmAmbienteEnteConvenz;

    private String nmEnteConvenz;

    public UsrVUnenteByAmmin() {
    }

    public UsrVUnenteByAmmin(BigDecimal idAmbienteEnteConvenz, String nmAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
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

    private UsrVUnenteByAmminId usrVUnenteByAmminId;

    @EmbeddedId()
    public UsrVUnenteByAmminId getUsrVUnenteByAmminId() {
        return usrVUnenteByAmminId;
    }

    public void setUsrVUnenteByAmminId(UsrVUnenteByAmminId usrVUnenteByAmminId) {
        this.usrVUnenteByAmminId = usrVUnenteByAmminId;
    }
}
