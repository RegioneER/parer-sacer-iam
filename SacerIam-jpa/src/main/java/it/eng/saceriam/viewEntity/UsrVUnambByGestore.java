package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNAMB_BY_GESTORE database table.
 */
@Entity
@Table(name = "USR_V_UNAMB_BY_GESTORE")
@NamedQuery(name = "UsrVUnambByGestore.findAll", query = "SELECT u FROM UsrVUnambByGestore u")
public class UsrVUnambByGestore implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleDich;

    private BigDecimal idEnteGestore;

    private String nmAmbienteEnteConvenz;

    public UsrVUnambByGestore() {
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

    @Column(name = "ID_ENTE_GESTORE")
    public BigDecimal getIdEnteGestore() {
        return this.idEnteGestore;
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
        this.idEnteGestore = idEnteGestore;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    private UsrVUnambByGestoreId usrVUnambByGestoreId;

    @EmbeddedId()
    public UsrVUnambByGestoreId getUsrVUnambByGestoreId() {
        return usrVUnambByGestoreId;
    }

    public void setUsrVUnambByGestoreId(UsrVUnambByGestoreId usrVUnambByGestoreId) {
        this.usrVUnambByGestoreId = usrVUnambByGestoreId;
    }
}
