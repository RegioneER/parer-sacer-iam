package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNAORG_BY_FORNIT database table.
 */
@Entity
@Table(name = "USR_V_UNAORG_BY_FORNIT")
@NamedQuery(name = "UsrVUnaorgByFornit.findAll", query = "SELECT u FROM UsrVUnaorgByFornit u")
public class UsrVUnaorgByFornit implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteFornitEst;

    private BigDecimal idEnteProdutCorrisp;

    private BigDecimal idEnteProdutSupt;

    private BigDecimal idRichGestUser;

    private BigDecimal idSuptEstEnteConvenz;

    private String nmApplic;

    public UsrVUnaorgByFornit() {
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

    @Column(name = "ID_ENTE_FORNIT_EST")
    public BigDecimal getIdEnteFornitEst() {
        return this.idEnteFornitEst;
    }

    public void setIdEnteFornitEst(BigDecimal idEnteFornitEst) {
        this.idEnteFornitEst = idEnteFornitEst;
    }

    @Column(name = "ID_ENTE_PRODUT_CORRISP")
    public BigDecimal getIdEnteProdutCorrisp() {
        return this.idEnteProdutCorrisp;
    }

    public void setIdEnteProdutCorrisp(BigDecimal idEnteProdutCorrisp) {
        this.idEnteProdutCorrisp = idEnteProdutCorrisp;
    }

    @Column(name = "ID_ENTE_PRODUT_SUPT")
    public BigDecimal getIdEnteProdutSupt() {
        return this.idEnteProdutSupt;
    }

    public void setIdEnteProdutSupt(BigDecimal idEnteProdutSupt) {
        this.idEnteProdutSupt = idEnteProdutSupt;
    }

    @Column(name = "ID_RICH_GEST_USER")
    public BigDecimal getIdRichGestUser() {
        return this.idRichGestUser;
    }

    public void setIdRichGestUser(BigDecimal idRichGestUser) {
        this.idRichGestUser = idRichGestUser;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVUnaorgByFornitId usrVUnaorgByFornitId;

    @EmbeddedId()
    public UsrVUnaorgByFornitId getUsrVUnaorgByFornitId() {
        return usrVUnaorgByFornitId;
    }

    public void setUsrVUnaorgByFornitId(UsrVUnaorgByFornitId usrVUnaorgByFornitId) {
        this.usrVUnaorgByFornitId = usrVUnaorgByFornitId;
    }
}
