package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ALLORGCHILD_BY_AMMIN database table.
 */
@Entity
@Table(name = "USR_V_ALLORGCHILD_BY_AMMIN")
@NamedQuery(name = "UsrVAllorgchildByAmmin.findAll", query = "SELECT u FROM UsrVAllorgchildByAmmin u")
public class UsrVAllorgchildByAmmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private String nmApplic;

    public UsrVAllorgchildByAmmin() {
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

    private UsrVAllorgchildByAmminId usrVAllorgchildByAmminId;

    @EmbeddedId()
    public UsrVAllorgchildByAmminId getUsrVAllorgchildByAmminId() {
        return usrVAllorgchildByAmminId;
    }

    public void setUsrVAllorgchildByAmminId(UsrVAllorgchildByAmminId usrVAllorgchildByAmminId) {
        this.usrVAllorgchildByAmminId = usrVAllorgchildByAmminId;
    }
}
