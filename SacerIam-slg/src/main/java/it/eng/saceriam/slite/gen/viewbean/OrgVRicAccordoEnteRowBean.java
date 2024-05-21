/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.slite.gen.viewbean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.eng.saceriam.viewEntity.OrgVRicAccordoEnte;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_V_Ric_Accordo_Ente
 *
 */
public class OrgVRicAccordoEnteRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 16 December 2021 12:32" )
     */

    public static OrgVRicAccordoEnteTableDescriptor TABLE_DESCRIPTOR = new OrgVRicAccordoEnteTableDescriptor();

    public OrgVRicAccordoEnteRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdEnteConvenz() {
        return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal id_ente_convenz) {
        setObject("id_ente_convenz", id_ente_convenz);
    }

    public String getNmEnteConvenz() {
        return getString("nm_ente_convenz");
    }

    public void setNmEnteConvenz(String nm_ente_convenz) {
        setObject("nm_ente_convenz", nm_ente_convenz);
    }

    public BigDecimal getIdAmbienteEnteConvenz() {
        return getBigDecimal("id_ambiente_ente_convenz");
    }

    public void setIdAmbienteEnteConvenz(BigDecimal id_ambiente_ente_convenz) {
        setObject("id_ambiente_ente_convenz", id_ambiente_ente_convenz);
    }

    public String getNmAmbienteEnteConvenz() {
        return getString("nm_ambiente_ente_convenz");
    }

    public void setNmAmbienteEnteConvenz(String nm_ambiente_ente_convenz) {
        setObject("nm_ambiente_ente_convenz", nm_ambiente_ente_convenz);
    }

    public BigDecimal getIdUserIamCor() {
        return getBigDecimal("id_user_iam_cor");
    }

    public void setIdUserIamCor(BigDecimal id_user_iam_cor) {
        setObject("id_user_iam_cor", id_user_iam_cor);
    }

    public BigDecimal getIdTipoAccordo() {
        return getBigDecimal("id_tipo_accordo");
    }

    public void setIdTipoAccordo(BigDecimal id_tipo_accordo) {
        setObject("id_tipo_accordo", id_tipo_accordo);
    }

    public String getCdTipoAccordo() {
        return getString("cd_tipo_accordo");
    }

    public void setCdTipoAccordo(String cd_tipo_accordo) {
        setObject("cd_tipo_accordo", cd_tipo_accordo);
    }

    public String getIdTipoGestioneAccordo() {
        return getString("id_tipo_gestione_accordo");
    }

    public void setIdTipoGestioneAccordo(String id_tipo_gestione_accordo) {
        setObject("id_tipo_gestione_accordo", id_tipo_gestione_accordo);
    }

    public String getCdTipoGestioneAccordo() {
        return getString("cd_tipo_gestione_accordo");
    }

    public void setCdTipoGestioneAccordo(String cd_tipo_gestione_accordo) {
        setObject("cd_tipo_gestione_accordo", cd_tipo_gestione_accordo);
    }

    public BigDecimal getIdAccordoEnte() {
        return getBigDecimal("id_accordo_ente");
    }

    public void setIdAccordoEnte(BigDecimal id_accordo_ente) {
        setObject("id_accordo_ente", id_accordo_ente);
    }

    public String getCdRegistroRepertorio() {
        return getString("cd_registro_repertorio");
    }

    public void setCdRegistroRepertorio(String cd_registro_repertorio) {
        setObject("cd_registro_repertorio", cd_registro_repertorio);
    }

    public BigDecimal getAaRepertorio() {
        return getBigDecimal("aa_repertorio");
    }

    public void setAaRepertorio(BigDecimal aa_repertorio) {
        setObject("aa_repertorio", aa_repertorio);
    }

    public String getCdKeyRepertorio() {
        return getString("cd_key_repertorio");
    }

    public void setCdKeyRepertorio(String cd_key_repertorio) {
        setObject("cd_key_repertorio", cd_key_repertorio);
    }

    public String getFlRecesso() {
        return getString("fl_recesso");
    }

    public void setFlRecesso(String fl_recesso) {
        setObject("fl_recesso", fl_recesso);
    }

    public Timestamp getDtDecAccordoInfo() {
        return getTimestamp("dt_dec_accordo_info");
    }

    public void setDtDecAccordoInfo(Timestamp dt_dec_accordo_info) {
        setObject("dt_dec_accordo_info", dt_dec_accordo_info);
    }

    public Timestamp getDtScadAccordo() {
        return getTimestamp("dt_scad_accordo");
    }

    public void setDtScadAccordo(Timestamp dt_scad_accordo) {
        setObject("dt_scad_accordo", dt_scad_accordo);
    }

    public Timestamp getDtDecAccordo() {
        return getTimestamp("dt_dec_accordo");
    }

    public void setDtDecAccordo(Timestamp dt_dec_accordo) {
        setObject("dt_dec_accordo", dt_dec_accordo);
    }

    public Timestamp getDtFineValidAccordo() {
        return getTimestamp("dt_fine_valid_accordo");
    }

    public void setDtFineValidAccordo(Timestamp dt_fine_valid_accordo) {
        setObject("dt_fine_valid_accordo", dt_fine_valid_accordo);
    }

    public String getDsNotaFatturazione() {
        return getString("ds_nota_fatturazione");
    }

    public void setDsNotaFatturazione(String ds_nota_fatturazione) {
        setObject("ds_nota_fatturazione", ds_nota_fatturazione);
    }

    public String getFlEsistonoGestAcc() {
        return getString("fl_esistono_gest_acc");
    }

    public void setFlEsistonoGestAcc(String fl_esistono_gest_acc) {
        setObject("fl_esistono_gest_acc", fl_esistono_gest_acc);
    }

    public String getFlEsisteNotaFatturazione() {
        return getString("fl_esiste_nota_fatturazione");
    }

    public void setFlEsisteNotaFatturazione(String fl_esiste_nota_fatturazione) {
        setObject("fl_esiste_nota_fatturazione", fl_esiste_nota_fatturazione);
    }

    public String getFlEsistonoSae() {
        return getString("fl_esistono_sae");
    }

    public void setFlEsistonoSae(String fl_esistono_sae) {
        setObject("fl_esistono_sae", fl_esistono_sae);
    }

    public String getFlEsistonoSue() {
        return getString("fl_esistono_sue");
    }

    public void setFlEsistonoSue(String fl_esistono_sue) {
        setObject("fl_esistono_sue", fl_esistono_sue);
    }

    public String getFlFasciaManuale() {
        return getString("fl_fascia_manuale");
    }

    public void setFlFasciaManuale(String fl_fascia_manuale) {
        setObject("fl_fascia_manuale", fl_fascia_manuale);
    }

    public String getTiFasciaStandard() {
        return getString("ti_fascia_standard");
    }

    public void setTiFasciaStandard(String ti_fascia_standard) {
        setObject("ti_fascia_standard", ti_fascia_standard);
    }

    public String getTiFasciaManuale() {
        return getString("ti_fascia_manuale");
    }

    public void setTiFasciaManuale(String ti_fascia_manuale) {
        setObject("ti_fascia_manuale", ti_fascia_manuale);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgVRicAccordoEnte entity = (OrgVRicAccordoEnte) obj;
        this.setIdEnteConvenz(entity.getIdEnteConvenz());
        this.setNmEnteConvenz(entity.getNmEnteConvenz());
        this.setIdAmbienteEnteConvenz(entity.getIdAmbienteEnteConvenz());
        this.setNmAmbienteEnteConvenz(entity.getNmAmbienteEnteConvenz());
        this.setIdUserIamCor(entity.getIdUserIamCor());
        this.setIdTipoAccordo(entity.getIdTipoAccordo());
        this.setCdTipoAccordo(entity.getCdTipoAccordo());
        this.setIdTipoGestioneAccordo(entity.getIdTipoGestioneAccordo());
        this.setCdTipoGestioneAccordo(entity.getCdTipoGestioneAccordo());
        this.setIdAccordoEnte(entity.getIdAccordoEnte());
        this.setCdRegistroRepertorio(entity.getCdRegistroRepertorio());
        this.setAaRepertorio(entity.getAaRepertorio());
        this.setCdKeyRepertorio(entity.getCdKeyRepertorio());
        this.setFlRecesso(entity.getFlRecesso());
        if (entity.getDtDecAccordoInfo() != null) {
            this.setDtDecAccordoInfo(new Timestamp(entity.getDtDecAccordoInfo().getTime()));
        }
        if (entity.getDtScadAccordo() != null) {
            this.setDtScadAccordo(new Timestamp(entity.getDtScadAccordo().getTime()));
        }
        if (entity.getDtDecAccordo() != null) {
            this.setDtDecAccordo(new Timestamp(entity.getDtDecAccordo().getTime()));
        }
        if (entity.getDtFineValidAccordo() != null) {
            this.setDtFineValidAccordo(new Timestamp(entity.getDtFineValidAccordo().getTime()));
        }
        this.setDsNotaFatturazione(entity.getDsNotaFatturazione());
        this.setFlEsistonoGestAcc(entity.getFlEsistonoGestAcc());
        this.setFlEsisteNotaFatturazione(entity.getFlEsisteNotaFatturazione());
        this.setFlEsistonoSae(entity.getFlEsistonoSae());
        this.setFlEsistonoSue(entity.getFlEsistonoSue());
        this.setFlFasciaManuale(entity.getFlFasciaManuale());
        this.setTiFasciaStandard(entity.getTiFasciaStandard());
        this.setTiFasciaManuale(entity.getTiFasciaManuale());
        // PERSONALIZZATO
        String registro = entity.getCdRegistroRepertorio();
        BigDecimal anno = entity.getAaRepertorio();
        String numero = entity.getCdKeyRepertorio();
        if (registro != null) {
            this.setString("reg_anno_num", registro + " - " + anno + " - " + numero);
        }
    }

    @Override
    public OrgVRicAccordoEnte rowBeanToEntity() {
        OrgVRicAccordoEnte entity = new OrgVRicAccordoEnte();
        entity.setIdEnteConvenz(this.getIdEnteConvenz());
        entity.setNmEnteConvenz(this.getNmEnteConvenz());
        entity.setIdAmbienteEnteConvenz(this.getIdAmbienteEnteConvenz());
        entity.setNmAmbienteEnteConvenz(this.getNmAmbienteEnteConvenz());
        entity.setIdUserIamCor(this.getIdUserIamCor());
        entity.setIdTipoAccordo(this.getIdTipoAccordo());
        entity.setCdTipoAccordo(this.getCdTipoAccordo());
        entity.setIdTipoGestioneAccordo(this.getIdTipoGestioneAccordo());
        entity.setCdTipoGestioneAccordo(this.getCdTipoGestioneAccordo());
        entity.setIdAccordoEnte(this.getIdAccordoEnte());
        entity.setCdRegistroRepertorio(this.getCdRegistroRepertorio());
        entity.setAaRepertorio(this.getAaRepertorio());
        entity.setCdKeyRepertorio(this.getCdKeyRepertorio());
        entity.setFlRecesso(this.getFlRecesso());
        entity.setDtDecAccordoInfo(this.getDtDecAccordoInfo());
        entity.setDtScadAccordo(this.getDtScadAccordo());
        entity.setDtDecAccordo(this.getDtDecAccordo());
        entity.setDtFineValidAccordo(this.getDtFineValidAccordo());
        entity.setDsNotaFatturazione(this.getDsNotaFatturazione());
        entity.setFlEsistonoGestAcc(this.getFlEsistonoGestAcc());
        entity.setFlEsisteNotaFatturazione(this.getFlEsisteNotaFatturazione());
        entity.setFlFasciaManuale(this.getFlFasciaManuale());
        entity.setFlEsistonoSae(this.getFlEsistonoSae());
        entity.setFlEsistonoSue(this.getFlEsistonoSue());
        entity.setTiFasciaStandard(this.getTiFasciaStandard());
        entity.setTiFasciaManuale(this.getTiFasciaManuale());
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
