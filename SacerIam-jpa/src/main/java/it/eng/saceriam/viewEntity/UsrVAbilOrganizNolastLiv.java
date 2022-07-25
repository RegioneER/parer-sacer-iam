package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_ORGANIZ_NOLAST_LIV database table.
 */
@Entity
@Table(name = "USR_V_ABIL_ORGANIZ_NOLAST_LIV")
@NamedQuery(name = "UsrVAbilOrganizNolastLiv.findAll", query = "SELECT u FROM UsrVAbilOrganizNolastLiv u")
public class UsrVAbilOrganizNolastLiv implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private String dlPathIdOrganizIam;

    private String dsOrganiz;

    private BigDecimal idApplic;

    private BigDecimal idDichAbilOrganiz;

    private BigDecimal idOrganizApplic;

    private BigDecimal idOrganizIamPadre;

    private BigDecimal idTipoOrganiz;

    private BigDecimal idUsoUserApplic;

    private String nmApplic;

    private String nmOrganiz;

    private String nmTipoOrganiz;

    public UsrVAbilOrganizNolastLiv() {
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "DL_PATH_ID_ORGANIZ_IAM")
    public String getDlPathIdOrganizIam() {
        return this.dlPathIdOrganizIam;
    }

    public void setDlPathIdOrganizIam(String dlPathIdOrganizIam) {
        this.dlPathIdOrganizIam = dlPathIdOrganizIam;
    }

    @Column(name = "DS_ORGANIZ")
    public String getDsOrganiz() {
        return this.dsOrganiz;
    }

    public void setDsOrganiz(String dsOrganiz) {
        this.dsOrganiz = dsOrganiz;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_DICH_ABIL_ORGANIZ")
    public BigDecimal getIdDichAbilOrganiz() {
        return this.idDichAbilOrganiz;
    }

    public void setIdDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        this.idDichAbilOrganiz = idDichAbilOrganiz;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
        return this.idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    @Column(name = "ID_ORGANIZ_IAM_PADRE")
    public BigDecimal getIdOrganizIamPadre() {
        return this.idOrganizIamPadre;
    }

    public void setIdOrganizIamPadre(BigDecimal idOrganizIamPadre) {
        this.idOrganizIamPadre = idOrganizIamPadre;
    }

    @Column(name = "ID_TIPO_ORGANIZ")
    public BigDecimal getIdTipoOrganiz() {
        return this.idTipoOrganiz;
    }

    public void setIdTipoOrganiz(BigDecimal idTipoOrganiz) {
        this.idTipoOrganiz = idTipoOrganiz;
    }

    @Column(name = "ID_USO_USER_APPLIC")
    public BigDecimal getIdUsoUserApplic() {
        return this.idUsoUserApplic;
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
        this.idUsoUserApplic = idUsoUserApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
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

    private UsrVAbilOrganizNolastLivId usrVAbilOrganizNolastLivId;

    @EmbeddedId()
    public UsrVAbilOrganizNolastLivId getUsrVAbilOrganizNolastLivId() {
        return usrVAbilOrganizNolastLivId;
    }

    public void setUsrVAbilOrganizNolastLivId(UsrVAbilOrganizNolastLivId usrVAbilOrganizNolastLivId) {
        this.usrVAbilOrganizNolastLivId = usrVAbilOrganizNolastLivId;
    }
}
