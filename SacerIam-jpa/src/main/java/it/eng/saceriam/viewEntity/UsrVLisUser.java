/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USR_V_LIS_USER database table.
 */
@Entity
@Table(name = "USR_V_LIS_USER")
public class UsrVLisUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal aaRichGestUser;

    private String cdFisc;

    private String cdKeyRichGestUser;

    private String cdLastRichGestUser;

    private String cdRegistroRichGestUser;

    private String cdRichGestUser;

    private String dsEmail;

    private String dsEmailSecondaria;

    private String dsListaAzioni;

    private Date dtLastRichGestUser;

    private Date dtRichGestUser;

    private String flAttivo;

    private String flAzioniEvase;

    private String flErrReplic;

    private String flRespEnteConvenz;

    private BigDecimal idAmbEnteConvenzAppart;

    private BigDecimal idApplic;

    private BigDecimal idEnteConvenzAbil;

    private BigDecimal idEnteRich;

    private BigDecimal idEnteSiamAppart;

    private BigDecimal idLastRichGestUser;

    private BigDecimal idOrganizIam;

    private BigDecimal idOrganizIamRich;

    private BigDecimal idSistemaVersante;

    private BigDecimal idUserIam;

    private BigDecimal idUserIamCorr;

    private String nmCognomeUser;

    private String nmEnteSiamAppart;

    private String nmNomeUser;

    private String nmRuoloDefault;

    private String nmRuoloDich;

    private String nmSistemaVersante;

    private String nmUserid;

    private String tiStatoUser;

    private String tipoUser;

    public UsrVLisUser() {
    }

    public UsrVLisUser(BigDecimal idUserIam, String nmCognomeUser, String nmNomeUser,
	    String nmUserid, String cdFisc, String nmRuoloDefault, String flAttivo,
	    String flErrReplic, String nmSistemaVersante, String tipoUser, String nmEnteSiamAppart,
	    BigDecimal idLastRichGestUser, String cdLastRichGestUser, String dsListaAzioni,
	    Date dtLastRichGestUser, String flAzioniEvase, String dsEmail,
	    String dsEmailSecondaria) {
	this.idUserIam = idUserIam;
	this.nmCognomeUser = nmCognomeUser;
	this.nmNomeUser = nmNomeUser;
	this.nmUserid = nmUserid;
	this.cdFisc = cdFisc;
	this.nmRuoloDefault = nmRuoloDefault;
	this.flAttivo = flAttivo;
	this.flErrReplic = flErrReplic;
	this.nmSistemaVersante = nmSistemaVersante;
	this.tipoUser = tipoUser;
	this.nmEnteSiamAppart = nmEnteSiamAppart;
	this.idLastRichGestUser = idLastRichGestUser;
	this.cdLastRichGestUser = cdLastRichGestUser;
	this.dsListaAzioni = dsListaAzioni;
	this.dtLastRichGestUser = dtLastRichGestUser;
	this.flAzioniEvase = flAzioniEvase;
	this.dsEmail = dsEmail;
	this.dsEmailSecondaria = dsEmailSecondaria;
    }

    @Column(name = "AA_RICH_GEST_USER")
    public BigDecimal getAaRichGestUser() {
	return this.aaRichGestUser;
    }

    public void setAaRichGestUser(BigDecimal aaRichGestUser) {
	this.aaRichGestUser = aaRichGestUser;
    }

    @Column(name = "CD_FISC")
    public String getCdFisc() {
	return this.cdFisc;
    }

    public void setCdFisc(String cdFisc) {
	this.cdFisc = cdFisc;
    }

    @Column(name = "CD_KEY_RICH_GEST_USER")
    public String getCdKeyRichGestUser() {
	return this.cdKeyRichGestUser;
    }

    public void setCdKeyRichGestUser(String cdKeyRichGestUser) {
	this.cdKeyRichGestUser = cdKeyRichGestUser;
    }

    @Column(name = "CD_LAST_RICH_GEST_USER")
    public String getCdLastRichGestUser() {
	return this.cdLastRichGestUser;
    }

    public void setCdLastRichGestUser(String cdLastRichGestUser) {
	this.cdLastRichGestUser = cdLastRichGestUser;
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

    @Column(name = "DS_EMAIL")
    public String getDsEmail() {
	return this.dsEmail;
    }

    public void setDsEmail(String dsEmail) {
	this.dsEmail = dsEmail;
    }

    @Column(name = "DS_EMAIL_SECONDARIA")
    public String getDsEmailSecondaria() {
	return this.dsEmailSecondaria;
    }

    public void setDsEmailSecondaria(String dsEmailSecondaria) {
	this.dsEmailSecondaria = dsEmailSecondaria;
    }

    @Column(name = "DS_LISTA_AZIONI")
    public String getDsListaAzioni() {
	return this.dsListaAzioni;
    }

    public void setDsListaAzioni(String dsListaAzioni) {
	this.dsListaAzioni = dsListaAzioni;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_LAST_RICH_GEST_USER")
    public Date getDtLastRichGestUser() {
	return this.dtLastRichGestUser;
    }

    public void setDtLastRichGestUser(Date dtLastRichGestUser) {
	this.dtLastRichGestUser = dtLastRichGestUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_RICH_GEST_USER")
    public Date getDtRichGestUser() {
	return this.dtRichGestUser;
    }

    public void setDtRichGestUser(Date dtRichGestUser) {
	this.dtRichGestUser = dtRichGestUser;
    }

    @Column(name = "FL_ATTIVO", columnDefinition = "char(1)")
    public String getFlAttivo() {
	return this.flAttivo;
    }

    public void setFlAttivo(String flAttivo) {
	this.flAttivo = flAttivo;
    }

    @Column(name = "FL_AZIONI_EVASE", columnDefinition = "char(1)")
    public String getFlAzioniEvase() {
	return this.flAzioniEvase;
    }

    public void setFlAzioniEvase(String flAzioniEvase) {
	this.flAzioniEvase = flAzioniEvase;
    }

    @Column(name = "FL_ERR_REPLIC", columnDefinition = "char(1)")
    public String getFlErrReplic() {
	return this.flErrReplic;
    }

    public void setFlErrReplic(String flErrReplic) {
	this.flErrReplic = flErrReplic;
    }

    @Column(name = "FL_RESP_ENTE_CONVENZ", columnDefinition = "char(1)")
    public String getFlRespEnteConvenz() {
	return this.flRespEnteConvenz;
    }

    public void setFlRespEnteConvenz(String flRespEnteConvenz) {
	this.flRespEnteConvenz = flRespEnteConvenz;
    }

    @Column(name = "ID_AMB_ENTE_CONVENZ_APPART")
    public BigDecimal getIdAmbEnteConvenzAppart() {
	return this.idAmbEnteConvenzAppart;
    }

    public void setIdAmbEnteConvenzAppart(BigDecimal idAmbEnteConvenzAppart) {
	this.idAmbEnteConvenzAppart = idAmbEnteConvenzAppart;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
	return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
	this.idApplic = idApplic;
    }

    @Column(name = "ID_ENTE_CONVENZ_ABIL")
    public BigDecimal getIdEnteConvenzAbil() {
	return this.idEnteConvenzAbil;
    }

    public void setIdEnteConvenzAbil(BigDecimal idEnteConvenzAbil) {
	this.idEnteConvenzAbil = idEnteConvenzAbil;
    }

    @Column(name = "ID_ENTE_RICH")
    public BigDecimal getIdEnteRich() {
	return this.idEnteRich;
    }

    public void setIdEnteRich(BigDecimal idEnteRich) {
	this.idEnteRich = idEnteRich;
    }

    @Column(name = "ID_ENTE_SIAM_APPART")
    public BigDecimal getIdEnteSiamAppart() {
	return this.idEnteSiamAppart;
    }

    public void setIdEnteSiamAppart(BigDecimal idEnteSiamAppart) {
	this.idEnteSiamAppart = idEnteSiamAppart;
    }

    @Column(name = "ID_LAST_RICH_GEST_USER")
    public BigDecimal getIdLastRichGestUser() {
	return this.idLastRichGestUser;
    }

    public void setIdLastRichGestUser(BigDecimal idLastRichGestUser) {
	this.idLastRichGestUser = idLastRichGestUser;
    }

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
	return this.idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
	this.idOrganizIam = idOrganizIam;
    }

    @Column(name = "ID_ORGANIZ_IAM_RICH")
    public BigDecimal getIdOrganizIamRich() {
	return this.idOrganizIamRich;
    }

    public void setIdOrganizIamRich(BigDecimal idOrganizIamRich) {
	this.idOrganizIamRich = idOrganizIamRich;
    }

    @Column(name = "ID_SISTEMA_VERSANTE")
    public BigDecimal getIdSistemaVersante() {
	return this.idSistemaVersante;
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
	this.idSistemaVersante = idSistemaVersante;
    }

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
	return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
	this.idUserIam = idUserIam;
    }

    @Column(name = "ID_USER_IAM_CORR")
    public BigDecimal getIdUserIamCorr() {
	return this.idUserIamCorr;
    }

    public void setIdUserIamCorr(BigDecimal idUserIamCorr) {
	this.idUserIamCorr = idUserIamCorr;
    }

    @Column(name = "NM_COGNOME_USER")
    public String getNmCognomeUser() {
	return this.nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
	this.nmCognomeUser = nmCognomeUser;
    }

    @Column(name = "NM_ENTE_SIAM_APPART")
    public String getNmEnteSiamAppart() {
	return this.nmEnteSiamAppart;
    }

    public void setNmEnteSiamAppart(String nmEnteSiamAppart) {
	this.nmEnteSiamAppart = nmEnteSiamAppart;
    }

    @Column(name = "NM_NOME_USER")
    public String getNmNomeUser() {
	return this.nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
	this.nmNomeUser = nmNomeUser;
    }

    @Column(name = "NM_RUOLO_DEFAULT")
    public String getNmRuoloDefault() {
	return this.nmRuoloDefault;
    }

    public void setNmRuoloDefault(String nmRuoloDefault) {
	this.nmRuoloDefault = nmRuoloDefault;
    }

    @Column(name = "NM_RUOLO_DICH")
    public String getNmRuoloDich() {
	return this.nmRuoloDich;
    }

    public void setNmRuoloDich(String nmRuoloDich) {
	this.nmRuoloDich = nmRuoloDich;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
	return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
	this.nmSistemaVersante = nmSistemaVersante;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
	return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
	this.nmUserid = nmUserid;
    }

    @Column(name = "TI_STATO_USER")
    public String getTiStatoUser() {
	return this.tiStatoUser;
    }

    public void setTiStatoUser(String tiStatoUser) {
	this.tiStatoUser = tiStatoUser;
    }

    @Column(name = "TIPO_USER")
    public String getTipoUser() {
	return this.tipoUser;
    }

    public void setTipoUser(String tipoUser) {
	this.tipoUser = tipoUser;
    }

    private UsrVLisUserId usrVLisUserId;

    @EmbeddedId()
    public UsrVLisUserId getUsrVLisUserId() {
	return usrVLisUserId;
    }

    public void setUsrVLisUserId(UsrVLisUserId usrVLisUserId) {
	this.usrVLisUserId = usrVLisUserId;
    }
}
