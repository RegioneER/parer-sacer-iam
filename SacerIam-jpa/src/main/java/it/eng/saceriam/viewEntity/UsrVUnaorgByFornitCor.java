package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNAORG_BY_FORNIT_COR database table.
 */
@Entity
@Table(name = "USR_V_UNAORG_BY_FORNIT_COR")
@NamedQuery(name = "UsrVUnaorgByFornitCor.findAll", query = "SELECT u FROM UsrVUnaorgByFornitCor u")
public class UsrVUnaorgByFornitCor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteFornitoreEst;

    private BigDecimal idEnteProdutCorrisp;

    private String nmApplic;

    public UsrVUnaorgByFornitCor() {
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

    @Column(name = "ID_ENTE_FORNITORE_EST")
    public BigDecimal getIdEnteFornitoreEst() {
        return this.idEnteFornitoreEst;
    }

    public void setIdEnteFornitoreEst(BigDecimal idEnteFornitoreEst) {
        this.idEnteFornitoreEst = idEnteFornitoreEst;
    }

    @Column(name = "ID_ENTE_PRODUT_CORRISP")
    public BigDecimal getIdEnteProdutCorrisp() {
        return this.idEnteProdutCorrisp;
    }

    public void setIdEnteProdutCorrisp(BigDecimal idEnteProdutCorrisp) {
        this.idEnteProdutCorrisp = idEnteProdutCorrisp;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVUnaorgByFornitCorId usrVUnaorgByFornitCorId;

    @EmbeddedId()
    public UsrVUnaorgByFornitCorId getUsrVUnaorgByFornitCorId() {
        return usrVUnaorgByFornitCorId;
    }

    public void setUsrVUnaorgByFornitCorId(UsrVUnaorgByFornitCorId usrVUnaorgByFornitCorId) {
        this.usrVUnaorgByFornitCorId = usrVUnaorgByFornitCorId;
    }
}
