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

package it.eng.saceriam.slite.gen.viewbean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.eng.saceriam.viewEntity.OrgVRicEnteConvenz;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella ORG_V_RIC_ENTE_CONVENZ
 *
 */
public class OrgVRicEnteConvenzRowBean extends BaseRow
	implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Monday, 15 October 2018 11:00" )
     */

    public static final OrgVRicEnteConvenzTableDescriptor TABLE_DESCRIPTOR = new OrgVRicEnteConvenzTableDescriptor();

    public OrgVRicEnteConvenzRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdEnteConvenz() {
	return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
	setObject("id_ente_convenz", idEnteConvenz);
    }

    public String getNmEnteConvenz() {
	return getString("nm_ente_convenz");
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
	setObject("nm_ente_convenz", nmEnteConvenz);
    }

    public BigDecimal getIdCategEnte() {
	return getBigDecimal("id_categ_ente");
    }

    public void setIdCategEnte(BigDecimal idCategEnte) {
	setObject("id_categ_ente", idCategEnte);
    }

    public BigDecimal getIdAmbitoTerrit() {
	return getBigDecimal("id_ambito_territ");
    }

    public void setIdAmbitoTerrit(BigDecimal idAmbitoTerrit) {
	setObject("id_ambito_territ", idAmbitoTerrit);
    }

    public Timestamp getDtIniVal() {
	return getTimestamp("dt_ini_val");
    }

    public void setDtIniVal(Timestamp dtIniVal) {
	setObject("dt_ini_val", dtIniVal);
    }

    public Timestamp getDtCessazione() {
	return getTimestamp("dt_cessazione");
    }

    public void setDtCessazione(Timestamp dtCessazione) {
	setObject("dt_cessazione", dtCessazione);
    }

    public BigDecimal getIdTipoAccordo() {
	return getBigDecimal("id_tipo_accordo");
    }

    public void setIdTipoAccordo(BigDecimal idTipoAccordo) {
	setObject("id_tipo_accordo", idTipoAccordo);
    }

    public String getCdTipoAccordo() {
	return getString("cd_tipo_accordo");
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
	setObject("cd_tipo_accordo", cdTipoAccordo);
    }

    public Timestamp getDtRichModuloInfo() {
	return getTimestamp("dt_rich_modulo_info");
    }

    public void setDtRichModuloInfo(Timestamp dtRichModuloInfo) {
	setObject("dt_rich_modulo_info", dtRichModuloInfo);
    }

    public String getFlEsistonoModuli() {
	return getString("fl_esistono_moduli");
    }

    public void setFlEsistonoModuli(String flEsistonoModuli) {
	setObject("fl_esistono_moduli", flEsistonoModuli);
    }

    public String getFlRecesso() {
	return getString("fl_recesso");
    }

    public void setFlRecesso(String flRecesso) {
	setObject("fl_recesso", flRecesso);
    }

    public String getFlChiuso() {
	return getString("fl_chiuso");
    }

    public void setFlChiuso(String flChiuso) {
	setObject("fl_chiuso", flChiuso);
    }

    public BigDecimal getEnteAttivo() {
	return getBigDecimal("ente_attivo");
    }

    public void setEnteAttivo(BigDecimal enteAttivo) {
	setObject("ente_attivo", enteAttivo);
    }

    public String getArchivista() {
	return getString("archivista");
    }

    public void setArchivista(String archivista) {
	setObject("archivista", archivista);
    }

    public BigDecimal getIdUserIamArk() {
	return getBigDecimal("id_user_iam_ark");
    }

    public void setIdUserIamArk(BigDecimal idUserIamArk) {
	setObject("id_user_iam_ark", idUserIamArk);
    }

    public BigDecimal getIdUserIamCor() {
	return getBigDecimal("id_user_iam_cor");
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
	setObject("id_user_iam_cor", idUserIamCor);
    }

    public BigDecimal getIdAmbienteEnteConvenz() {
	return getBigDecimal("id_ambiente_ente_convenz");
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
	setObject("id_ambiente_ente_convenz", idAmbienteEnteConvenz);
    }

    public BigDecimal getIdEnteConserv() {
	return getBigDecimal("id_ente_conserv");
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
	setObject("id_ente_conserv", idEnteConserv);
    }

    public BigDecimal getIdEnteGestore() {
	return getBigDecimal("id_ente_gestore");
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
	setObject("id_ente_gestore", idEnteGestore);
    }

    public Timestamp getDtDecAccordo() {
	return getTimestamp("dt_dec_accordo");
    }

    public void setDtDecAccordo(Timestamp dtDecAccordo) {
	setObject("dt_dec_accordo", dtDecAccordo);
    }

    public Timestamp getDtFineValidAccordo() {
	return getTimestamp("dt_fine_valid_accordo");
    }

    public void setDtFineValidAccordo(Timestamp dtFineValidAccordo) {
	setObject("dt_fine_valid_accordo", dtFineValidAccordo);
    }

    public Timestamp getDtScadAccordo() {
	return getTimestamp("dt_scad_accordo");
    }

    public void setDtScadAccordo(Timestamp dtScadAccordo) {
	setObject("dt_scad_accordo", dtScadAccordo);
    }

    public String getFlNonConvenz() {
	return getString("fl_non_convenz");
    }

    public void setFlNonConvenz(String flNonConvenz) {
	setObject("fl_non_convenz", flNonConvenz);
    }

    public String getFlEsistonoGestAcc() {
	return getString("fl_esistono_gest_acc");
    }

    public void setFlEsistonoGestAcc(String flEsistonoGestAcc) {
	setObject("fl_esistono_gest_acc", flEsistonoGestAcc);
    }

    public String getFlEsistonoPrpVar() {
	return getString("fl_esistono_prp_var");
    }

    public void setFlEsistonoPrpVar(String flEsistonoPrpVar) {
	setObject("fl_esistono_prp_var", flEsistonoPrpVar);
    }

    public String getTiEnteConvenz() {
	return getString("ti_ente_convenz");
    }

    public void setTiEnteConvenz(String tiEnteConvenz) {
	setObject("ti_ente_convenz", tiEnteConvenz);
    }

    public String getTipoGestioneAccordo() {
	return getString("tipo_gestione_accordo");
    }

    public void setTipoGestioneAccordo(String tipoGestioneAccordo) {
	setObject("tipo_gestione_accordo", tipoGestioneAccordo);
    }

    public String getFlGestAccNoRisp() {
	return getString("fl_gest_acc_no_risp");
    }

    public void setFlGestAccNoRisp(String flGestAccNoRisp) {
	setObject("fl_gest_acc_no_risp", flGestAccNoRisp);
    }

    public String getTiStatoAccordo() {
	return getString("ti_stato_accordo");
    }

    public void setTiStatoAccordo(String tiStatoAccordo) {
	setObject("ti_stato_accordo", tiStatoAccordo);
    }

    public String getCdEnteConvenz() {
	return getString("cd_ente_convenz");
    }

    public void setCdEnteConvenz(String cdEnteConvenz) {
	setObject("cd_ente_convenz", cdEnteConvenz);
    }

    @Override
    public void entityToRowBean(Object obj) {
	OrgVRicEnteConvenz entity = (OrgVRicEnteConvenz) obj;
	this.setIdEnteConvenz(entity.getIdEnteConvenz());
	this.setNmEnteConvenz(entity.getNmEnteConvenz());
	this.setIdCategEnte(entity.getIdCategEnte());
	this.setIdAmbitoTerrit(entity.getIdAmbitoTerrit());
	if (entity.getDtIniVal() != null) {
	    this.setDtIniVal(new Timestamp(entity.getDtIniVal().getTime()));
	}
	if (entity.getDtCessazione() != null) {
	    this.setDtCessazione(new Timestamp(entity.getDtCessazione().getTime()));
	}
	this.setIdTipoAccordo(entity.getIdTipoAccordo());
	this.setCdTipoAccordo(entity.getCdTipoAccordo());
	if (entity.getDtRichModuloInfo() != null) {
	    this.setDtRichModuloInfo(new Timestamp(entity.getDtRichModuloInfo().getTime()));
	}
	this.setFlEsistonoModuli(entity.getFlEsistonoModuli());
	this.setFlRecesso(entity.getFlRecesso());
	this.setFlChiuso(entity.getFlChiuso());
	this.setEnteAttivo(entity.getEnteAttivo());
	this.setArchivista(entity.getArchivista());
	this.setIdUserIamArk(entity.getIdUserIamArk());
	this.setIdUserIamCor(entity.getIdUserIamCor());
	this.setIdAmbienteEnteConvenz(entity.getIdAmbienteEnteConvenz());
	if (entity.getDtDecAccordo() != null) {
	    this.setDtDecAccordo(new Timestamp(entity.getDtDecAccordo().getTime()));
	}
	if (entity.getDtFineValidAccordo() != null) {
	    this.setDtFineValidAccordo(new Timestamp(entity.getDtFineValidAccordo().getTime()));
	}
	if (entity.getDtScadAccordo() != null) {
	    this.setDtScadAccordo(new Timestamp(entity.getDtScadAccordo().getTime()));
	}
	this.setFlNonConvenz(entity.getFlNonConvenz());
	this.setFlEsistonoGestAcc(entity.getFlEsistonoGestAcc());
	this.setTipoGestioneAccordo(entity.getTipoGestioneAccordo());
	this.setFlGestAccNoRisp(entity.getFlGestAccNoRisp());
	this.setTiStatoAccordo(entity.getTiStatoAccordo());
	this.setCdEnteConvenz(entity.getCdEnteConvenz());
    }

    @Override
    public OrgVRicEnteConvenz rowBeanToEntity() {
	OrgVRicEnteConvenz entity = new OrgVRicEnteConvenz();
	entity.setIdEnteConvenz(this.getIdEnteConvenz());
	entity.setNmEnteConvenz(this.getNmEnteConvenz());
	entity.setIdCategEnte(this.getIdCategEnte());
	entity.setIdAmbitoTerrit(this.getIdAmbitoTerrit());
	entity.setDtIniVal(this.getDtIniVal());
	entity.setDtCessazione(this.getDtCessazione());
	entity.setIdTipoAccordo(this.getIdTipoAccordo());
	entity.setCdTipoAccordo(this.getCdTipoAccordo());
	entity.setDtRichModuloInfo(this.getDtRichModuloInfo());
	entity.setFlEsistonoModuli(this.getFlEsistonoModuli());
	entity.setFlRecesso(this.getFlRecesso());
	entity.setFlChiuso(this.getFlChiuso());
	entity.setEnteAttivo(this.getEnteAttivo());
	entity.setArchivista(this.getArchivista());
	entity.setIdUserIamArk(this.getIdUserIamArk());
	entity.setIdUserIamCor(this.getIdUserIamCor());
	entity.setIdAmbienteEnteConvenz(this.getIdAmbienteEnteConvenz());
	entity.setDtDecAccordo(this.getDtDecAccordo());
	entity.setDtFineValidAccordo(this.getDtFineValidAccordo());
	entity.setDtScadAccordo(this.getDtScadAccordo());
	entity.setFlNonConvenz(this.getFlNonConvenz());
	entity.setFlEsistonoGestAcc(this.getFlEsistonoGestAcc());
	entity.setTipoGestioneAccordo(this.getTipoGestioneAccordo());
	entity.setFlGestAccNoRisp(this.getFlGestAccNoRisp());
	entity.setTiStatoAccordo(this.getTiStatoAccordo());
	entity.setCdEnteConvenz(this.getCdEnteConvenz());
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
