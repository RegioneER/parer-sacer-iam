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

package it.eng.saceriam.slite.gen.tablebean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.LogAgente;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUserExt;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Usr_User
 *
 */
public class UsrUserRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Thursday, 14 September 2017 15:03"
     * )
     */
    public static UsrUserTableDescriptor TABLE_DESCRIPTOR = new UsrUserTableDescriptor();

    public UsrUserRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdUserIam() {
	return getBigDecimal("id_user_iam");
    }

    public void setIdUserIam(BigDecimal idUserIam) {
	setObject("id_user_iam", idUserIam);
    }

    public String getNmUserid() {
	return getString("nm_userid");
    }

    public void setNmUserid(String nmUserid) {
	setObject("nm_userid", nmUserid);
    }

    public String getCdPsw() {
	return getString("cd_psw");
    }

    public void setCdPsw(String cdPsw) {
	setObject("cd_psw", cdPsw);
    }

    public String getNmCognomeUser() {
	return getString("nm_cognome_user");
    }

    public void setNmCognomeUser(String nmCognomeUser) {
	setObject("nm_cognome_user", nmCognomeUser);
    }

    public String getNmNomeUser() {
	return getString("nm_nome_user");
    }

    public void setNmNomeUser(String nmNomeUser) {
	setObject("nm_nome_user", nmNomeUser);
    }

    public String getFlAttivo() {
	return getString("fl_attivo");
    }

    public void setFlAttivo(String flAttivo) {
	setObject("fl_attivo", flAttivo);
    }

    public Timestamp getDtRegPsw() {
	return getTimestamp("dt_reg_psw");
    }

    public void setDtRegPsw(Timestamp dtRegPsw) {
	setObject("dt_reg_psw", dtRegPsw);
    }

    public Timestamp getDtScadPsw() {
	return getTimestamp("dt_scad_psw");
    }

    public void setDtScadPsw(Timestamp dtScadPsw) {
	setObject("dt_scad_psw", dtScadPsw);
    }

    public String getCdSalt() {
	return getString("cd_salt");
    }

    public void setCdSalt(String cdSalt) {
	setObject("cd_salt", cdSalt);
    }

    public String getCdFisc() {
	return getString("cd_fisc");
    }

    public void setCdFisc(String cdFisc) {
	setObject("cd_fisc", cdFisc);
    }

    public String getDsEmail() {
	return getString("ds_email");
    }

    public void setDsEmail(String dsEmail) {
	setObject("ds_email", dsEmail);
    }

    public String getDsEmailSecondaria() {
	return getString("ds_email_secondaria");
    }

    public void setDsEmailSecondaria(String dsEmailSecondaria) {
	setObject("ds_email_secondaria", dsEmailSecondaria);
    }

    public String getFlContrIp() {
	return getString("fl_contr_ip");
    }

    public void setFlContrIp(String flContrIp) {
	setObject("fl_contr_ip", flContrIp);
    }

    public String getTipoUser() {
	return getString("tipo_user");
    }

    public void setTipoUser(String tipoUser) {
	setObject("tipo_user", tipoUser);
    }

    public BigDecimal getIdAgente() {
	return getBigDecimal("id_agente");
    }

    public void setIdAgente(BigDecimal idAgente) {
	setObject("id_agente", idAgente);
    }

    public BigDecimal getIdSistemaVersante() {
	return getBigDecimal("id_sistema_versante");
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
	setObject("id_sistema_versante", idSistemaVersante);
    }

    public BigDecimal getIdEnteSiam() {
	return getBigDecimal("id_ente_siam");
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
	setObject("id_ente_siam", idEnteSiam);
    }

    public BigDecimal getIdStatoUserCor() {
	return getBigDecimal("id_stato_user_cor");
    }

    public void setIdStatoUserCor(BigDecimal idStatoUserCor) {
	setObject("id_stato_user_cor", idStatoUserCor);
    }

    public String getFlRespEnteConvenz() {
	return getString("fl_resp_ente_convenz");
    }

    public void setFlRespEnteConvenz(String flRespEnteConvenz) {
	setObject("fl_resp_ente_convenz", flRespEnteConvenz);
    }

    public String getCdPswDaNotif() {
	return getString("cd_psw_da_notif");
    }

    public void setCdPswDaNotif(String cdPswDaNotif) {
	setObject("cd_psw_da_notif", cdPswDaNotif);
    }

    public String getFlAbilEntiCollegAutom() {
	return getString("fl_abil_enti_colleg_autom");
    }

    public void setFlAbilEntiCollegAutom(String flAbilEntiCollegAutom) {
	setObject("fl_abil_enti_colleg_autom", flAbilEntiCollegAutom);
    }

    public String getFlAbilOrganizAutom() {
	return getString("fl_abil_organiz_autom");
    }

    public void setFlAbilOrganizAutom(String flAbilOrganizAutom) {
	setObject("fl_abil_organiz_autom", flAbilOrganizAutom);
    }

    public String getTipoAuth() {
	return getString("tipo_auth");
    }

    public void setTipoAuth(String tipo_auth) {
	setObject("tipo_auth", tipo_auth);
    }

    public String getDlCertClient() {
	return getString("dl_cert_client");
    }

    public void setDlCertClient(String dl_cert_client) {
	setObject("dl_cert_client", dl_cert_client);
    }

    public Timestamp getDtIniCert() {
	return getTimestamp("dt_ini_cert");
    }

    public void setDtIniCert(Timestamp dt_ini_cert) {
	setObject("dt_ini_cert", dt_ini_cert);
    }

    public Timestamp getDtFinCert() {
	return getTimestamp("dt_fin_cert");
    }

    public void setDtFinCert(Timestamp dt_fin_cert) {
	setObject("dt_fin_cert", dt_fin_cert);
    }

    public String getFlAbilFornitAutom() {
	return getString("fl_abil_fornit_autom");
    }

    public void setFlAbilFornitAutom(String flAbilFornitAutom) {
	setObject("fl_abil_fornit_autom", flAbilFornitAutom);
    }

    @Override
    public void entityToRowBean(Object obj) {
	UsrUser entity = (UsrUser) obj;
	this.setIdUserIam(new BigDecimal(entity.getIdUserIam()));
	this.setNmUserid(entity.getNmUserid());
	this.setCdPsw(entity.getCdPsw());
	this.setNmCognomeUser(entity.getNmCognomeUser());
	this.setNmNomeUser(entity.getNmNomeUser());
	this.setFlAttivo(entity.getFlAttivo());
	if (entity.getDtRegPsw() != null) {
	    this.setDtRegPsw(new Timestamp(entity.getDtRegPsw().getTime()));
	}
	if (entity.getDtScadPsw() != null) {
	    this.setDtScadPsw(new Timestamp(entity.getDtScadPsw().getTime()));
	}
	this.setCdSalt(entity.getCdSalt());
	this.setCdFisc(entity.getCdFisc());
	this.setDsEmail(entity.getDsEmail());
	this.setDsEmailSecondaria(entity.getDsEmailSecondaria());
	this.setFlContrIp(entity.getFlContrIp());
	this.setTipoUser(entity.getTipoUser());
	if (entity.getLogAgente() != null) {
	    this.setIdAgente(new BigDecimal(entity.getLogAgente().getIdAgente()));

	}
	if (entity.getAplSistemaVersante() != null) {
	    this.setIdSistemaVersante(
		    new BigDecimal(entity.getAplSistemaVersante().getIdSistemaVersante()));

	}
	if (entity.getOrgEnteSiam() != null) {
	    this.setIdEnteSiam(new BigDecimal(entity.getOrgEnteSiam().getIdEnteSiam()));

	}
	this.setIdStatoUserCor(entity.getIdStatoUserCor());
	this.setFlRespEnteConvenz(entity.getFlRespEnteConvenz());
	this.setFlAbilEntiCollegAutom(entity.getFlAbilEntiCollegAutom());
	this.setFlAbilOrganizAutom(entity.getFlAbilOrganizAutom());

	// MAC#26322 - correzione errore dettaglio enti convenzionati
	UsrUserExt usrUserExt = entity.getUsrUserExt();
	if (usrUserExt != null) {
	    this.setDlCertClient(usrUserExt.getDlCertClient());
	}

	this.setTipoAuth(entity.getTipoAuth());
	if (entity.getDtIniCert() != null) {
	    this.setDtIniCert(new Timestamp(entity.getDtIniCert().getTime()));
	}
	if (entity.getDtFinCert() != null) {
	    this.setDtFinCert(new Timestamp(entity.getDtFinCert().getTime()));
	}
	this.setFlAbilFornitAutom(entity.getFlAbilFornitAutom());
    }

    @Override
    public UsrUser rowBeanToEntity() {
	UsrUser entity = new UsrUser();
	if (this.getIdUserIam() != null) {
	    entity.setIdUserIam(this.getIdUserIam().longValue());
	}
	entity.setNmUserid(this.getNmUserid());
	entity.setCdPsw(this.getCdPsw());
	entity.setNmCognomeUser(this.getNmCognomeUser());
	entity.setNmNomeUser(this.getNmNomeUser());
	entity.setFlAttivo(this.getFlAttivo());
	entity.setDtRegPsw(this.getDtRegPsw());
	entity.setDtScadPsw(this.getDtScadPsw());
	entity.setCdSalt(this.getCdSalt());
	entity.setCdFisc(this.getCdFisc());
	entity.setDsEmail(this.getDsEmail());
	entity.setDsEmailSecondaria(this.getDsEmailSecondaria());
	entity.setFlContrIp(this.getFlContrIp());
	entity.setTipoUser(this.getTipoUser());
	if (this.getIdAgente() != null) {
	    if (entity.getLogAgente() == null) {
		entity.setLogAgente(new LogAgente());
	    }
	    entity.getLogAgente().setIdAgente(this.getIdAgente().longValue());
	}
	if (this.getIdSistemaVersante() != null) {
	    if (entity.getAplSistemaVersante() == null) {
		entity.setAplSistemaVersante(new AplSistemaVersante());
	    }
	    entity.getAplSistemaVersante()
		    .setIdSistemaVersante(this.getIdSistemaVersante().longValue());
	}
	if (this.getIdEnteSiam() != null) {
	    if (entity.getOrgEnteSiam() == null) {
		entity.setOrgEnteSiam(new OrgEnteSiam());
	    }
	    entity.getOrgEnteSiam().setIdEnteSiam(this.getIdEnteSiam().longValue());
	}
	entity.setIdStatoUserCor(this.getIdStatoUserCor());
	entity.setFlRespEnteConvenz(this.getFlRespEnteConvenz());
	entity.setFlAbilEntiCollegAutom(this.getFlAbilEntiCollegAutom());
	entity.setFlAbilOrganizAutom(this.getFlAbilOrganizAutom());
	entity.setTipoAuth(this.getTipoAuth());

	// MAC#26322 - correzione errore dettaglio enti convenzionati
	UsrUserExt usrUserExt = new UsrUserExt();
	usrUserExt.setDlCertClient(this.getDlCertClient());
	entity.setUsrUserExt(usrUserExt);

	entity.setDtIniCert(this.getDtIniCert());
	entity.setDtFinCert(this.getDtFinCert());
	entity.setFlAbilFornitAutom(this.getFlAbilFornitAutom());
	return entity;
    }

    // gestione della paginazione
    public void setRownum(Integer rownum) {
	setObject("rownum", rownum);
    }

    public Integer getRownum() {
	return Integer.parseInt(getObject("rownum").toString());
    }

    public void setRnum(Integer rnum) {
	setObject("rnum", rnum);
    }

    public Integer getRnum() {
	return Integer.parseInt(getObject("rnum").toString());
    }

    public void setNumrecords(Integer numRecords) {
	setObject("numrecords", numRecords);
    }

    public Integer getNumrecords() {
	return Integer.parseInt(getObject("numrecords").toString());
    }

}
