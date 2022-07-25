package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_LIS_ABIL_ORGANIZ database table.
 */
@Entity
@Table(name = "USR_V_LIS_ABIL_ORGANIZ")
@NamedQuery(name = "UsrVLisAbilOrganiz.findAll", query = "SELECT u FROM UsrVLisAbilOrganiz u")
public class UsrVLisAbilOrganiz implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private String dsCausaleAbil;

    private String flAbilAutomatica;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idApplic;

    private BigDecimal idEnteUtente;

    private BigDecimal idSuptEstEnteConvenz;

    private BigDecimal idVigilEnteProdut;

    private String nmApplic;

    private String nmEnteCausale;

    private String tiScopoAbil;

    public UsrVLisAbilOrganiz() {
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
        return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
        this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "FL_ABIL_AUTOMATICA", columnDefinition = "char(1)")
    public String getFlAbilAutomatica() {
        return this.flAbilAutomatica;
    }

    public void setFlAbilAutomatica(String flAbilAutomatica) {
        this.flAbilAutomatica = flAbilAutomatica;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_ENTE_UTENTE")
    public BigDecimal getIdEnteUtente() {
        return this.idEnteUtente;
    }

    public void setIdEnteUtente(BigDecimal idEnteUtente) {
        this.idEnteUtente = idEnteUtente;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
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

    @Column(name = "NM_ENTE_CAUSALE")
    public String getNmEnteCausale() {
        return this.nmEnteCausale;
    }

    public void setNmEnteCausale(String nmEnteCausale) {
        this.nmEnteCausale = nmEnteCausale;
    }

    @Column(name = "TI_SCOPO_ABIL")
    public String getTiScopoAbil() {
        return this.tiScopoAbil;
    }

    public void setTiScopoAbil(String tiScopoAbil) {
        this.tiScopoAbil = tiScopoAbil;
    }

    private UsrVLisAbilOrganizId usrVLisAbilOrganizId;

    @EmbeddedId()
    public UsrVLisAbilOrganizId getUsrVLisAbilOrganizId() {
        return usrVLisAbilOrganizId;
    }

    public void setUsrVLisAbilOrganizId(UsrVLisAbilOrganizId usrVLisAbilOrganizId) {
        this.usrVLisAbilOrganizId = usrVLisAbilOrganizId;
    }
}
