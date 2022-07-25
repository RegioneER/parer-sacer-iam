package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_RIC_RICHIESTE database table.
 */
@Entity
@Table(name = "USR_V_RIC_RICHIESTE")
@NamedQuery(name = "UsrVRicRichieste.findAll", query = "SELECT u FROM UsrVRicRichieste u")
public class UsrVRicRichieste implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal aaRichGestUser;

    private String cdKeyRichGestUser;

    private String cdRegistroRichGestUser;

    private String cdRichGestUser;

    private String dlCompositoOrganiz;

    private Date dtRichGestUser;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idEnteSiam;

    private BigDecimal idOrganizIam;

    private BigDecimal idUserIamAppRich;

    private String nmAmbienteEnteConvenz;

    private String nmCognomeUserAppRich;

    private String nmCognomeUserRich;

    private String nmEnteSiam;

    private String nmNomeUserAppRich;

    private String nmNomeUserRich;

    private String nmUserRich;

    private String nmUseridAppRich;

    private String nmUseridCor;

    private String nmUseridRich;

    private String tiStatoRichGestUser;

    private String tiUserRich;

    public UsrVRicRichieste() {
    }

    public UsrVRicRichieste(BigDecimal idRichGestUser, String nmAmbienteEnteConvenz, String nmEnteSiam,
            String dlCompositoOrganiz, String cdRegistroRichGestUser, BigDecimal aaRichGestUser,
            String cdKeyRichGestUser, String cdRichGestUser, String nmCognomeUserRich, String nmNomeUserRich,
            String nmUserRich, Date dtRichGestUser, String tiStatoRichGestUser) {
        this.usrVRicRichiesteId = new UsrVRicRichiesteId();
        this.usrVRicRichiesteId.setIdRichGestUser(idRichGestUser);
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
        this.nmEnteSiam = nmEnteSiam;
        this.dlCompositoOrganiz = dlCompositoOrganiz;
        this.cdRegistroRichGestUser = cdRegistroRichGestUser;
        this.aaRichGestUser = aaRichGestUser;
        this.cdKeyRichGestUser = cdKeyRichGestUser;
        this.cdRichGestUser = cdRichGestUser;
        this.nmCognomeUserRich = nmCognomeUserRich;
        this.nmNomeUserRich = nmNomeUserRich;
        this.nmUserRich = nmUserRich;
        this.dtRichGestUser = dtRichGestUser;
        this.tiStatoRichGestUser = tiStatoRichGestUser;
    }

    @Column(name = "AA_RICH_GEST_USER")
    public BigDecimal getAaRichGestUser() {
        return this.aaRichGestUser;
    }

    public void setAaRichGestUser(BigDecimal aaRichGestUser) {
        this.aaRichGestUser = aaRichGestUser;
    }

    @Column(name = "CD_KEY_RICH_GEST_USER")
    public String getCdKeyRichGestUser() {
        return this.cdKeyRichGestUser;
    }

    public void setCdKeyRichGestUser(String cdKeyRichGestUser) {
        this.cdKeyRichGestUser = cdKeyRichGestUser;
    }

    @Column(name = "CD_REGISTRO_RICH_GEST_USER")
    public String getCdRegistroRichGestUser() {
        return this.cdRegistroRichGestUser;
    }

    public void setCdRegistroRichGestUser(String cdRegistroRichGestUser) {
        this.cdRegistroRichGestUser = cdRegistroRichGestUser;
    }

    @Column(name = "CD_RICH_GEST_USER")
    public String getCdRichGestUser() {
        return this.cdRichGestUser;
    }

    public void setCdRichGestUser(String cdRichGestUser) {
        this.cdRichGestUser = cdRichGestUser;
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_RICH_GEST_USER")
    public Date getDtRichGestUser() {
        return this.dtRichGestUser;
    }

    public void setDtRichGestUser(Date dtRichGestUser) {
        this.dtRichGestUser = dtRichGestUser;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_ENTE_SIAM")
    public BigDecimal getIdEnteSiam() {
        return this.idEnteSiam;
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
        this.idEnteSiam = idEnteSiam;
    }

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
        return this.idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
    }

    @Column(name = "ID_USER_IAM_APP_RICH")
    public BigDecimal getIdUserIamAppRich() {
        return this.idUserIamAppRich;
    }

    public void setIdUserIamAppRich(BigDecimal idUserIamAppRich) {
        this.idUserIamAppRich = idUserIamAppRich;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_COGNOME_USER_APP_RICH")
    public String getNmCognomeUserAppRich() {
        return this.nmCognomeUserAppRich;
    }

    public void setNmCognomeUserAppRich(String nmCognomeUserAppRich) {
        this.nmCognomeUserAppRich = nmCognomeUserAppRich;
    }

    @Column(name = "NM_COGNOME_USER_RICH")
    public String getNmCognomeUserRich() {
        return this.nmCognomeUserRich;
    }

    public void setNmCognomeUserRich(String nmCognomeUserRich) {
        this.nmCognomeUserRich = nmCognomeUserRich;
    }

    @Column(name = "NM_ENTE_SIAM")
    public String getNmEnteSiam() {
        return this.nmEnteSiam;
    }

    public void setNmEnteSiam(String nmEnteSiam) {
        this.nmEnteSiam = nmEnteSiam;
    }

    @Column(name = "NM_NOME_USER_APP_RICH")
    public String getNmNomeUserAppRich() {
        return this.nmNomeUserAppRich;
    }

    public void setNmNomeUserAppRich(String nmNomeUserAppRich) {
        this.nmNomeUserAppRich = nmNomeUserAppRich;
    }

    @Column(name = "NM_NOME_USER_RICH")
    public String getNmNomeUserRich() {
        return this.nmNomeUserRich;
    }

    public void setNmNomeUserRich(String nmNomeUserRich) {
        this.nmNomeUserRich = nmNomeUserRich;
    }

    @Column(name = "NM_USER_RICH")
    public String getNmUserRich() {
        return this.nmUserRich;
    }

    public void setNmUserRich(String nmUserRich) {
        this.nmUserRich = nmUserRich;
    }

    @Column(name = "NM_USERID_APP_RICH")
    public String getNmUseridAppRich() {
        return this.nmUseridAppRich;
    }

    public void setNmUseridAppRich(String nmUseridAppRich) {
        this.nmUseridAppRich = nmUseridAppRich;
    }

    @Column(name = "NM_USERID_COR")
    public String getNmUseridCor() {
        return this.nmUseridCor;
    }

    public void setNmUseridCor(String nmUseridCor) {
        this.nmUseridCor = nmUseridCor;
    }

    @Column(name = "NM_USERID_RICH")
    public String getNmUseridRich() {
        return this.nmUseridRich;
    }

    public void setNmUseridRich(String nmUseridRich) {
        this.nmUseridRich = nmUseridRich;
    }

    @Column(name = "TI_STATO_RICH_GEST_USER")
    public String getTiStatoRichGestUser() {
        return this.tiStatoRichGestUser;
    }

    public void setTiStatoRichGestUser(String tiStatoRichGestUser) {
        this.tiStatoRichGestUser = tiStatoRichGestUser;
    }

    @Column(name = "TI_USER_RICH")
    public String getTiUserRich() {
        return this.tiUserRich;
    }

    public void setTiUserRich(String tiUserRich) {
        this.tiUserRich = tiUserRich;
    }

    private UsrVRicRichiesteId usrVRicRichiesteId;

    @EmbeddedId()
    public UsrVRicRichiesteId getUsrVRicRichiesteId() {
        return usrVRicRichiesteId;
    }

    public void setUsrVRicRichiesteId(UsrVRicRichiesteId usrVRicRichiesteId) {
        this.usrVRicRichiesteId = usrVRicRichiesteId;
    }
}
