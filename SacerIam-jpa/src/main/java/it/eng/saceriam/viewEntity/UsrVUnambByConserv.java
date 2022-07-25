package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNAMB_BY_CONSERV database table.
 */
@Entity
@Table(name = "USR_V_UNAMB_BY_CONSERV")
@NamedQuery(name = "UsrVUnambByConserv.findAll", query = "SELECT u FROM UsrVUnambByConserv u")
public class UsrVUnambByConserv implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleDich;

    private BigDecimal idEnteConserv;

    private String nmAmbienteEnteConvenz;

    public UsrVUnambByConserv() {
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

    @Column(name = "ID_ENTE_CONSERV")
    public BigDecimal getIdEnteConserv() {
        return this.idEnteConserv;
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
        this.idEnteConserv = idEnteConserv;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    private UsrVUnambByConservId usrVUnambByConservId;

    @EmbeddedId()
    public UsrVUnambByConservId getUsrVUnambByConservId() {
        return usrVUnambByConservId;
    }

    public void setUsrVUnambByConservId(UsrVUnambByConservId usrVUnambByConservId) {
        this.usrVUnambByConservId = usrVUnambByConservId;
    }
}
