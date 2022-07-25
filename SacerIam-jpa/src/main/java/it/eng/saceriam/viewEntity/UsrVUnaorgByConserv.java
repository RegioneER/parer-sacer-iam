package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNAORG_BY_CONSERV database table.
 */
@Entity
@Table(name = "USR_V_UNAORG_BY_CONSERV")
@NamedQuery(name = "UsrVUnaorgByConserv.findAll", query = "SELECT u FROM UsrVUnaorgByConserv u")
public class UsrVUnaorgByConserv implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteConserv;

    private String nmApplic;

    public UsrVUnaorgByConserv() {
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

    @Column(name = "ID_ENTE_CONSERV")
    public BigDecimal getIdEnteConserv() {
        return this.idEnteConserv;
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
        this.idEnteConserv = idEnteConserv;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVUnaorgByConservId usrVUnaorgByConservId;

    @EmbeddedId()
    public UsrVUnaorgByConservId getUsrVUnaorgByConservId() {
        return usrVUnaorgByConservId;
    }

    public void setUsrVUnaorgByConservId(UsrVUnaorgByConservId usrVUnaorgByConservId) {
        this.usrVUnaorgByConservId = usrVUnaorgByConservId;
    }
}
