package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the USR_V_VIS_DICH_ABIL database table.
 *
 */
@Entity
@Table(name = "USR_V_VIS_DICH_ABIL")
public class UsrVVisDichAbil implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dlCompositoOrganiz;
    private BigDecimal idDichAbilOrganiz;
    private String nmApplic;
    private String nmCognomeUser;
    private String nmNomeUser;
    private String nmUserid;
    private String tiScopoDichAbilOrganiz;

    public UsrVVisDichAbil() {
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Id
    @Column(name = "ID_DICH_ABIL_ORGANIZ")
    public BigDecimal getIdDichAbilOrganiz() {
        return this.idDichAbilOrganiz;
    }

    public void setIdDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        this.idDichAbilOrganiz = idDichAbilOrganiz;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_COGNOME_USER")
    public String getNmCognomeUser() {
        return this.nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
        this.nmCognomeUser = nmCognomeUser;
    }

    @Column(name = "NM_NOME_USER")
    public String getNmNomeUser() {
        return this.nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
        this.nmNomeUser = nmNomeUser;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "TI_SCOPO_DICH_ABIL_ORGANIZ")
    public String getTiScopoDichAbilOrganiz() {
        return this.tiScopoDichAbilOrganiz;
    }

    public void setTiScopoDichAbilOrganiz(String tiScopoDichAbilOrganiz) {
        this.tiScopoDichAbilOrganiz = tiScopoDichAbilOrganiz;
    }
}