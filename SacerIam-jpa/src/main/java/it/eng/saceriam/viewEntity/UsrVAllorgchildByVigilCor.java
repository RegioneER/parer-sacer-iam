package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ALLORGCHILD_BY_VIGIL_COR database table.
 */
@Entity
@Table(name = "USR_V_ALLORGCHILD_BY_VIGIL_COR")
@NamedQuery(name = "UsrVAllorgchildByVigilCor.findAll", query = "SELECT u FROM UsrVAllorgchildByVigilCor u")
public class UsrVAllorgchildByVigilCor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteOrganoVigil;

    private BigDecimal idEnteProdutCorrisp;

    private String nmApplic;

    public UsrVAllorgchildByVigilCor() {
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

    @Column(name = "ID_ENTE_ORGANO_VIGIL")
    public BigDecimal getIdEnteOrganoVigil() {
        return this.idEnteOrganoVigil;
    }

    public void setIdEnteOrganoVigil(BigDecimal idEnteOrganoVigil) {
        this.idEnteOrganoVigil = idEnteOrganoVigil;
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

    private UsrVAllorgchildByVigilCorId usrVAllorgchildByVigilCorId;

    @EmbeddedId()
    public UsrVAllorgchildByVigilCorId getUsrVAllorgchildByVigilCorId() {
        return usrVAllorgchildByVigilCorId;
    }

    public void setUsrVAllorgchildByVigilCorId(UsrVAllorgchildByVigilCorId usrVAllorgchildByVigilCorId) {
        this.usrVAllorgchildByVigilCorId = usrVAllorgchildByVigilCorId;
    }
}
