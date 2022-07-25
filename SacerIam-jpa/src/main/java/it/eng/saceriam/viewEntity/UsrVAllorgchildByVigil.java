package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ALLORGCHILD_BY_VIGIL database table.
 */
@Entity
@Table(name = "USR_V_ALLORGCHILD_BY_VIGIL")
@NamedQuery(name = "UsrVAllorgchildByVigil.findAll", query = "SELECT u FROM UsrVAllorgchildByVigil u")
public class UsrVAllorgchildByVigil implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteOrganoVigil;

    private BigDecimal idEnteProdutCorrisp;

    private BigDecimal idEnteProdutVigil;

    private BigDecimal idRichGestUser;

    private BigDecimal idVigilEnteProdut;

    private String nmApplic;

    public UsrVAllorgchildByVigil() {
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

    @Column(name = "ID_ENTE_PRODUT_VIGIL")
    public BigDecimal getIdEnteProdutVigil() {
        return this.idEnteProdutVigil;
    }

    public void setIdEnteProdutVigil(BigDecimal idEnteProdutVigil) {
        this.idEnteProdutVigil = idEnteProdutVigil;
    }

    @Column(name = "ID_RICH_GEST_USER")
    public BigDecimal getIdRichGestUser() {
        return this.idRichGestUser;
    }

    public void setIdRichGestUser(BigDecimal idRichGestUser) {
        this.idRichGestUser = idRichGestUser;
    }

    @Column(name = "ID_VIGIL_ENTE_PRODUT")
    public BigDecimal getIdVigilEnteProdut() {
        return this.idVigilEnteProdut;
    }

    public void setIdVigilEnteProdut(BigDecimal idVigilEnteProdut) {
        this.idVigilEnteProdut = idVigilEnteProdut;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVAllorgchildByVigilId usrVAllorgchildByVigilId;

    @EmbeddedId()
    public UsrVAllorgchildByVigilId getUsrVAllorgchildByVigilId() {
        return usrVAllorgchildByVigilId;
    }

    public void setUsrVAllorgchildByVigilId(UsrVAllorgchildByVigilId usrVAllorgchildByVigilId) {
        this.usrVAllorgchildByVigilId = usrVAllorgchildByVigilId;
    }
}
