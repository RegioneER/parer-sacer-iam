package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ALLORGCHILD_BY_GESTORE database table.
 */
@Entity
@Table(name = "USR_V_ALLORGCHILD_BY_GESTORE")
@NamedQuery(name = "UsrVAllorgchildByGestore.findAll", query = "SELECT u FROM UsrVAllorgchildByGestore u")
public class UsrVAllorgchildByGestore implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteGestore;

    private String nmApplic;

    public UsrVAllorgchildByGestore() {
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

    @Column(name = "ID_ENTE_GESTORE")
    public BigDecimal getIdEnteGestore() {
        return this.idEnteGestore;
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
        this.idEnteGestore = idEnteGestore;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVAllorgchildByGestoreId usrVAllorgchildByGestoreId;

    @EmbeddedId()
    public UsrVAllorgchildByGestoreId getUsrVAllorgchildByGestoreId() {
        return usrVAllorgchildByGestoreId;
    }

    public void setUsrVAllorgchildByGestoreId(UsrVAllorgchildByGestoreId usrVAllorgchildByGestoreId) {
        this.usrVAllorgchildByGestoreId = usrVAllorgchildByGestoreId;
    }
}
