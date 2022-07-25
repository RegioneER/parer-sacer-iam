package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_V_ENTE_CONVENZ_BY_ORGANIZ database table.
 *
 */
@Entity
@Table(name = "ORG_V_ENTE_CONVENZ_BY_ORGANIZ")
@NamedQuery(name = "OrgVEnteConvenzByOrganiz.findAll", query = "SELECT o FROM OrgVEnteConvenzByOrganiz o")
public class OrgVEnteConvenzByOrganiz implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date dtFineVal;
    private Date dtIniVal;
    private BigDecimal idApplic;
    private BigDecimal idEnteConvenz;
    private BigDecimal idEnteConvenzOrg;
    private BigDecimal idOrganizApplic;
    private BigDecimal idOrganizIam;
    private BigDecimal idTipoOrganiz;
    private String nmApplic;
    private String nmEnteConvenz;
    private String nmOrganiz;
    private String nmTipoOrganiz;

    public OrgVEnteConvenzByOrganiz() {
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    @Id
    @Column(name = "ID_ENTE_CONVENZ_ORG")
    public BigDecimal getIdEnteConvenzOrg() {
        return this.idEnteConvenzOrg;
    }

    public void setIdEnteConvenzOrg(BigDecimal idEnteConvenzOrg) {
        this.idEnteConvenzOrg = idEnteConvenzOrg;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
        return this.idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
        return this.idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
    }

    @Column(name = "ID_TIPO_ORGANIZ")
    public BigDecimal getIdTipoOrganiz() {
        return this.idTipoOrganiz;
    }

    public void setIdTipoOrganiz(BigDecimal idTipoOrganiz) {
        this.idTipoOrganiz = idTipoOrganiz;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "NM_ORGANIZ")
    public String getNmOrganiz() {
        return this.nmOrganiz;
    }

    public void setNmOrganiz(String nmOrganiz) {
        this.nmOrganiz = nmOrganiz;
    }

    @Column(name = "NM_TIPO_ORGANIZ")
    public String getNmTipoOrganiz() {
        return this.nmTipoOrganiz;
    }

    public void setNmTipoOrganiz(String nmTipoOrganiz) {
        this.nmTipoOrganiz = nmTipoOrganiz;
    }

}
