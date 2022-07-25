package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNAORG_BY_AMMIN database table.
 */
@Entity
@Table(name = "USR_V_UNAORG_BY_AMMIN")
@NamedQuery(name = "UsrVUnaorgByAmmin.findAll", query = "SELECT u FROM UsrVUnaorgByAmmin u")
public class UsrVUnaorgByAmmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private String nmApplic;

    public UsrVUnaorgByAmmin() {
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVUnaorgByAmminId usrVUnaorgByAmminId;

    @EmbeddedId()
    public UsrVUnaorgByAmminId getUsrVUnaorgByAmminId() {
        return usrVUnaorgByAmminId;
    }

    public void setUsrVUnaorgByAmminId(UsrVUnaorgByAmminId usrVUnaorgByAmminId) {
        this.usrVUnaorgByAmminId = usrVUnaorgByAmminId;
    }
}
