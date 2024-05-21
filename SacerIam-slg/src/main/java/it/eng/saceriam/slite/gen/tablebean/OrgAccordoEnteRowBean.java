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

package it.eng.saceriam.slite.gen.tablebean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.eng.saceriam.entity.OrgAccordoEnte;
import it.eng.saceriam.entity.OrgCdIva;
import it.eng.saceriam.entity.OrgClasseEnteConvenz;
import it.eng.saceriam.entity.OrgClusterAccordo;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgFasciaStorageAccordo;
import it.eng.saceriam.entity.OrgTariffario;
import it.eng.saceriam.entity.OrgTipoAccordo;
import it.eng.saceriam.entity.constraint.ConstOrgAccordoEnte.TiScopoAccordo;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Org_Accordo_Ente
 *
 */
public class OrgAccordoEnteRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 7 November 2017 10:25" )
     */

    public static OrgAccordoEnteTableDescriptor TABLE_DESCRIPTOR = new OrgAccordoEnteTableDescriptor();

    public OrgAccordoEnteRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdAccordoEnte() {
        return getBigDecimal("id_accordo_ente");
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        setObject("id_accordo_ente", idAccordoEnte);
    }

    public BigDecimal getIdEnteConvenz() {
        return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        setObject("id_ente_convenz", idEnteConvenz);
    }

    public Timestamp getDtRegAccordo() {
        return getTimestamp("dt_reg_accordo");
    }

    public void setDtRegAccordo(Timestamp dtRegAccordo) {
        setObject("dt_reg_accordo", dtRegAccordo);
    }

    public Timestamp getDtDetermina() {
        return getTimestamp("dt_determina");
    }

    public void setDtDetermina(Timestamp dt_determina) {
        setObject("dt_determina", dt_determina);
    }

    public BigDecimal getIdTipoAccordo() {
        return getBigDecimal("id_tipo_accordo");
    }

    public void setIdTipoAccordo(BigDecimal idTipoAccordo) {
        setObject("id_tipo_accordo", idTipoAccordo);
    }

    public BigDecimal getIdTariffario() {
        return getBigDecimal("id_tariffario");
    }

    public void setIdTariffario(BigDecimal idTariffario) {
        setObject("id_tariffario", idTariffario);
    }

    public BigDecimal getIdClasseEnteConvenz() {
        return getBigDecimal("id_classe_ente_convenz");
    }

    public void setIdClasseEnteConvenz(BigDecimal idClasseEnteConvenz) {
        setObject("id_classe_ente_convenz", idClasseEnteConvenz);
    }

    public BigDecimal getNiAbitanti() {
        return getBigDecimal("ni_abitanti");
    }

    public void setNiAbitanti(BigDecimal niAbitanti) {
        setObject("ni_abitanti", niAbitanti);
    }

    public Timestamp getDtScadAccordo() {
        return getTimestamp("dt_scad_accordo");
    }

    public void setDtScadAccordo(Timestamp dtScadAccordo) {
        setObject("dt_scad_accordo", dtScadAccordo);
    }

    public Timestamp getDtFineValidAccordo() {
        return getTimestamp("dt_fine_valid_accordo");
    }

    public void setDtFineValidAccordo(Timestamp dtFineValidAccordo) {
        setObject("dt_fine_valid_accordo", dtFineValidAccordo);
    }

    public String getDsFirmatarioEnte() {
        return getString("ds_firmatario_ente");
    }

    public void setDsFirmatarioEnte(String dsFirmatarioEnte) {
        setObject("ds_firmatario_ente", dsFirmatarioEnte);
    }

    public Timestamp getDtRichModuloInfo() {
        return getTimestamp("dt_rich_modulo_info");
    }

    public void setDtRichModuloInfo(Timestamp dtRichModuloInfo) {
        setObject("dt_rich_modulo_info", dtRichModuloInfo);
    }

    public String getFlPagamento() {
        return getString("fl_pagamento");
    }

    public void setFlPagamento(String flPagamento) {
        setObject("fl_pagamento", flPagamento);
    }

    public String getCdRegistroDetermina() {
        return getString("cd_registro_determina");
    }

    public void setCdRegistroDetermina(String cdRegistroDetermina) {
        setObject("cd_registro_determina", cdRegistroDetermina);
    }

    public BigDecimal getAaDetermina() {
        return getBigDecimal("aa_determina");
    }

    public void setAaDetermina(BigDecimal aaDetermina) {
        setObject("aa_determina", aaDetermina);
    }

    public BigDecimal getAaRepertorio() {
        return getBigDecimal("aa_repertorio");
    }

    public void setAaRepertorio(BigDecimal aaRepertorio) {
        setObject("aa_repertorio", aaRepertorio);
    }

    public String getCdRegistroRepertorio() {
        return getString("cd_registro_repertorio");
    }

    public void setCdRegistroRepertorio(String cd_registro_repertorio) {
        setObject("cd_registro_repertorio", cd_registro_repertorio);
    }

    public String getCdKeyDetermina() {
        return getString("cd_key_determina");
    }

    public void setCdKeyDetermina(String cd_key_determina) {
        setObject("cd_key_determina", cd_key_determina);
    }

    public String getCdKeyRepertorio() {
        return getString("cd_key_repertorio");
    }

    public void setCdKeyRepertorio(String cdKeyRepertorio) {
        setObject("cd_key_repertorio", cdKeyRepertorio);
    }

    // getDsNoteAccordo
    public String getDsNoteAccordo() {
        return getString("ds_note_accordo");
    }

    public void setDsNoteAccordo(String dsNoteAccordo) {
        setObject("ds_note_accordo", dsNoteAccordo);
    }

    public String getCdUfe() {
        return getString("cd_ufe");
    }

    public void setCdUfe(String cdUfe) {
        setObject("cd_ufe", cdUfe);
    }

    public String getDsUfe() {
        return getString("ds_ufe");
    }

    public void setDsUfe(String dsUfe) {
        setObject("ds_ufe", dsUfe);
    }

    public String getCdCig() {
        return getString("cd_cig");
    }

    public void setCdCig(String cdCig) {
        setObject("cd_cig", cdCig);
    }

    public String getCdCup() {
        return getString("cd_cup");
    }

    public void setCdCup(String cdCup) {
        setObject("cd_cup", cdCup);
    }

    public String getCdCapitolo() {
        return getString("cd_capitolo");
    }

    public void setCdCapitolo(String cdCapitolo) {
        setObject("cd_capitolo", cdCapitolo);
    }

    public String getCdCoge() {
        return getString("cd_coge");
    }

    public void setCdCoge(String cdCoge) {
        setObject("cd_coge", cdCoge);
    }

    public BigDecimal getIdCdIva() {
        return getBigDecimal("id_cd_iva");
    }

    public void setIdCdIva(BigDecimal idCdIva) {
        setObject("id_cd_iva", idCdIva);
    }

    public String getCdRifContab() {
        return getString("cd_rif_contab");
    }

    public void setCdRifContab(String cdRifContab) {
        setObject("cd_rif_contab", cdRifContab);
    }

    public String getCdDdt() {
        return getString("cd_ddt");
    }

    public void setCdDdt(String cdDdt) {
        setObject("cd_ddt", cdDdt);
    }

    public String getCdOda() {
        return getString("cd_oda");
    }

    public void setCdOda(String cdOda) {
        setObject("cd_oda", cdOda);
    }

    public Timestamp getDtDecAccordo() {
        return getTimestamp("dt_dec_accordo");
    }

    public void setDtDecAccordo(Timestamp dtDecAccordo) {
        setObject("dt_dec_accordo", dtDecAccordo);
    }

    public Timestamp getDtDecAccordoInfo() {
        return getTimestamp("dt_dec_accordo_info");
    }

    public void setDtDecAccordoInfo(Timestamp dt_dec_accordo_info) {
        setObject("dt_dec_accordo_info", dt_dec_accordo_info);
    }

    public String getFlRecesso() {
        return getString("fl_recesso");
    }

    public void setFlRecesso(String flRecesso) {
        setObject("fl_recesso", flRecesso);
    }

    public String getFlAccordoChiuso() {
        return getString("fl_accordo_chiuso");
    }

    public void setFlAccordoChiuso(String flAccordoChiuso) {
        setObject("fl_accordo_chiuso", flAccordoChiuso);
    }

    public String getDsAttoAccordo() {
        return getString("ds_atto_accordo");
    }

    public void setDsAttoAccordo(String dsAttoAccordo) {
        setObject("ds_atto_accordo", dsAttoAccordo);
    }

    public Timestamp getDtAttoAccordo() {
        return getTimestamp("dt_atto_accordo");
    }

    public void setDtAttoAccordo(Timestamp dtAttoAccordo) {
        setObject("dt_atto_accordo", dtAttoAccordo);
    }

    public String getTiScopoAccordo() {
        return getString("ti_scopo_accordo");
    }

    public void setTiScopoAccordo(String tiScopoAccordo) {
        setObject("ti_scopo_accordo", tiScopoAccordo);
    }

    public String getDsNotaRecesso() {
        return getString("ds_nota_recesso");
    }

    public void setDsNotaRecesso(String dsNotaRecesso) {
        setObject("ds_nota_recesso", dsNotaRecesso);
    }

    public String getDsNotaFatturazione() {
        return getString("ds_nota_fatturazione");
    }

    public void setDsNotaFatturazione(String dsNotaFatturazione) {
        setObject("ds_nota_fatturazione", dsNotaFatturazione);
    }

    public String getCdClienteFatturazione() {
        return getString("cd_cliente_fatturazione");
    }

    public void setCdClienteFatturazione(String cdClienteFatturazione) {
        setObject("cd_cliente_fatturazione", cdClienteFatturazione);
    }

    public BigDecimal getIdEnteConvenzAmministratore() {
        return getBigDecimal("id_ente_convenz_amministratore");
    }

    public void setIdEnteConvenzAmministratore(BigDecimal idEnteConvenzAmministratore) {
        setObject("id_ente_convenz_amministratore", idEnteConvenzAmministratore);
    }

    public BigDecimal getIdEnteConvenzConserv() {
        return getBigDecimal("id_ente_convenz_conserv");
    }

    public void setIdEnteConvenzConserv(BigDecimal idEnteConvenzConserv) {
        setObject("id_ente_convenz_conserv", idEnteConvenzConserv);
    }

    public BigDecimal getIdEnteConvenzGestore() {
        return getBigDecimal("id_ente_convenz_gestore");
    }

    public void setIdEnteConvenzGestore(BigDecimal idEnteConvenzGestore) {
        setObject("id_ente_convenz_gestore", idEnteConvenzGestore);
    }

    public BigDecimal getIdClusterAccordo() {
        return getBigDecimal("id_cluster_accordo");
    }

    public void setIdClusterAccordo(BigDecimal id_cluster_accordo) {
        setObject("id_cluster_accordo", id_cluster_accordo);
    }

    public BigDecimal getIdFasciaStorageStandardAccordo() {
        return getBigDecimal("id_fascia_storage_standard_accordo");
    }

    public void setIdFasciaStorageStandardAccordo(BigDecimal id_fascia_storage_standard_accordo) {
        setObject("id_fascia_storage_standard_accordo", id_fascia_storage_standard_accordo);
    }

    public BigDecimal getIdFasciaStorageManualeAccordo() {
        return getBigDecimal("id_fascia_storage_manuale_accordo");
    }

    public void setIdFasciaStorageManualeAccordo(BigDecimal id_fascia_storage_manuale_accordo) {
        setObject("id_fascia_storage_manuale_accordo", id_fascia_storage_manuale_accordo);
    }

    public BigDecimal getNiTipoUdStandard() {
        return getBigDecimal("ni_tipo_ud_standard");
    }

    public void setNiTipoUdStandard(BigDecimal ni_tipo_ud_standard) {
        setObject("ni_tipo_ud_standard", ni_tipo_ud_standard);
    }

    public BigDecimal getNiTipoUdManuale() {
        return getBigDecimal("ni_tipo_ud_manuale");
    }

    public void setNiTipoUdManuale(BigDecimal ni_tipo_ud_manuale) {
        setObject("ni_tipo_ud_manuale", ni_tipo_ud_manuale);
    }

    public BigDecimal getNiRefertiStandard() {
        return getBigDecimal("ni_referti_standard");
    }

    public void setNiRefertiStandard(BigDecimal ni_referti_standard) {
        setObject("ni_referti_standard", ni_referti_standard);
    }

    public BigDecimal getNiRefertiManuale() {
        return getBigDecimal("ni_referti_manuale");
    }

    public void setNiRefertiManuale(BigDecimal ni_referti_manuale) {
        setObject("ni_referti_manuale", ni_referti_manuale);
    }

    public BigDecimal getNiStudioDicom() {
        return getBigDecimal("ni_studio_dicom");
    }

    public void setNiStudioDicom(BigDecimal ni_studio_dicom) {
        setObject("ni_studio_dicom", ni_studio_dicom);
    }

    public String getDsNotaAttivazione() {
        return getString("ds_nota_attivazione");
    }

    public void setDsNotaAttivazione(String ds_nota_attivazione) {
        setObject("ds_nota_attivazione", ds_nota_attivazione);
    }

    public String getDsNoteEnteAccordo() {
        return getString("ds_note_ente_accordo");
    }

    public void setDsNoteEnteAccordo(String ds_note_ente_accordo) {
        setObject("ds_note_ente_accordo", ds_note_ente_accordo);
    }

    public String getNmEnte() {
        return getString("nm_ente");
    }

    public void setNmEnte(String nm_ente) {
        setObject("nm_ente", nm_ente);
    }

    public String getNmStrut() {
        return getString("nm_strut");
    }

    public void setNmStrut(String nm_strut) {
        setObject("nm_strut", nm_strut);
    }

    public BigDecimal getImAttivDocAmm() {
        return getBigDecimal("im_attiv_doc_amm");
    }

    public void setImAttivDocAmm(BigDecimal im_attiv_doc_amm) {
        setObject("im_attiv_doc_amm", im_attiv_doc_amm);
    }

    public BigDecimal getImAttivDocSani() {
        return getBigDecimal("im_attiv_doc_sani");
    }

    public void setImAttivDocSani(BigDecimal im_attiv_doc_sani) {
        setObject("im_attiv_doc_sani", im_attiv_doc_sani);
    }

    public BigDecimal getNiStudioDicomPrev() {
        return getBigDecimal("ni_studio_dicom_prev");
    }

    public void setNiStudioDicomPrev(BigDecimal ni_studio_dicom_prev) {
        setObject("ni_studio_dicom_prev", ni_studio_dicom_prev);
    }

    @Override
    public void entityToRowBean(Object obj) {
        // OrgAccordoEnte
        OrgAccordoEnte entity = (OrgAccordoEnte) obj;
        this.setIdAccordoEnte(new BigDecimal(entity.getIdAccordoEnte()));
        if (entity.getOrgEnteSiam() != null) {
            this.setIdEnteConvenz(new BigDecimal(entity.getOrgEnteSiam().getIdEnteSiam()));
        }
        if (entity.getDtRegAccordo() != null) {
            this.setDtRegAccordo(new Timestamp(entity.getDtRegAccordo().getTime()));
        }
        if (entity.getOrgTipoAccordo() != null) {
            this.setIdTipoAccordo(new BigDecimal(entity.getOrgTipoAccordo().getIdTipoAccordo()));

        }
        // setIdTariffario
        if (entity.getOrgTariffario() != null) {
            this.setIdTariffario(new BigDecimal(entity.getOrgTariffario().getIdTariffario()));

        }
        if (entity.getOrgClasseEnteConvenz() != null) {
            this.setIdClasseEnteConvenz(new BigDecimal(entity.getOrgClasseEnteConvenz().getIdClasseEnteConvenz()));

        }
        this.setNiAbitanti(entity.getNiAbitanti());
        if (entity.getDtScadAccordo() != null) {
            this.setDtScadAccordo(new Timestamp(entity.getDtScadAccordo().getTime()));
        }
        if (entity.getDtFineValidAccordo() != null) {
            this.setDtFineValidAccordo(new Timestamp(entity.getDtFineValidAccordo().getTime()));
        }
        this.setDsFirmatarioEnte(entity.getDsFirmatarioEnte());
        if (entity.getDtRichModuloInfo() != null) {
            this.setDtRichModuloInfo(new Timestamp(entity.getDtRichModuloInfo().getTime()));
        }
        this.setFlPagamento(entity.getFlPagamento());
        this.setCdRegistroRepertorio(entity.getCdRegistroRepertorio());
        this.setAaRepertorio(entity.getAaRepertorio());
        this.setCdKeyRepertorio(entity.getCdKeyRepertorio());
        this.setDsNoteAccordo(entity.getDsNoteAccordo());
        this.setCdUfe(entity.getCdUfe());
        this.setDsUfe(entity.getDsUfe());
        this.setCdCig(entity.getCdCig());
        this.setCdCup(entity.getCdCup());
        this.setCdCapitolo(entity.getCdCapitolo());
        this.setCdCoge(entity.getCdCoge());
        if (entity.getOrgCdIva() != null) {
            this.setIdCdIva(new BigDecimal(entity.getOrgCdIva().getIdCdIva()));

        }
        this.setCdRifContab(entity.getCdRifContab());
        this.setCdDdt(entity.getCdDdt());
        this.setCdOda(entity.getCdOda());
        if (entity.getDtDecAccordo() != null) {
            this.setDtDecAccordo(new Timestamp(entity.getDtDecAccordo().getTime()));
        }
        if (entity.getDtDecAccordoInfo() != null) {
            this.setDtDecAccordoInfo(new Timestamp(entity.getDtDecAccordoInfo().getTime()));
        }

        this.setFlRecesso(entity.getFlRecesso());
        this.setFlAccordoChiuso(entity.getFlAccordoChiuso());
        this.setDsAttoAccordo(entity.getDsAttoAccordo());
        if (entity.getDtAttoAccordo() != null) {
            this.setDtAttoAccordo(new Timestamp(entity.getDtAttoAccordo().getTime()));
        }
        this.setTiScopoAccordo(entity.getTiScopoAccordo().name());
        this.setDsNotaRecesso(entity.getDsNotaRecesso());
        this.setDsNotaFatturazione(entity.getDsNotaFatturazione());
        this.setCdClienteFatturazione(entity.getCdClienteFatturazione());
        if (entity.getOrgEnteSiamByIdEnteConvenzAmministratore() != null) {
            this.setIdEnteConvenzAmministratore(
                    new BigDecimal(entity.getOrgEnteSiamByIdEnteConvenzAmministratore().getIdEnteSiam()));

        }
        if (entity.getOrgEnteSiamByIdEnteConvenzConserv() != null) {
            this.setIdEnteConvenzConserv(new BigDecimal(entity.getOrgEnteSiamByIdEnteConvenzConserv().getIdEnteSiam()));

        }
        if (entity.getOrgEnteSiamByIdEnteConvenzGestore() != null) {
            this.setIdEnteConvenzGestore(new BigDecimal(entity.getOrgEnteSiamByIdEnteConvenzGestore().getIdEnteSiam()));

        }

        if (entity.getOrgClusterAccordo() != null) {
            this.setIdClusterAccordo(new BigDecimal(entity.getOrgClusterAccordo().getIdClusterAccordo()));
        }

        if (entity.getOrgFasciaStorageStandardAccordo() != null) {
            this.setIdFasciaStorageStandardAccordo(
                    new BigDecimal(entity.getOrgFasciaStorageStandardAccordo().getIdFasciaStorageAccordo()));
        }

        if (entity.getOrgFasciaStorageManualeAccordo() != null) {
            this.setIdFasciaStorageManualeAccordo(
                    new BigDecimal(entity.getOrgFasciaStorageManualeAccordo().getIdFasciaStorageAccordo()));
        }

        this.setNiTipoUdStandard(entity.getNiTipoUdStandard());
        this.setNiTipoUdManuale(entity.getNiTipoUdManuale());
        this.setNiRefertiStandard(entity.getNiRefertiStandard());
        this.setNiRefertiManuale(entity.getNiRefertiManuale());
        this.setNiStudioDicom(entity.getNiStudioDicom());
        this.setDsNotaAttivazione(entity.getDsNotaAttivazione());
        this.setDsNoteEnteAccordo(entity.getDsNoteEnteAccordo());
        this.setNmEnte(entity.getNmEnte());
        this.setNmStrut(entity.getNmStrut());
        this.setImAttivDocAmm(entity.getImAttivDocAmm());
        this.setImAttivDocSani(entity.getImAttivDocSani());
        this.setNiStudioDicomPrev(entity.getNiStudioDicomPrev());
        this.setCdRegistroDetermina(entity.getCdRegistroDetermina());
        this.setAaDetermina(entity.getAaDetermina());
        this.setCdKeyDetermina(entity.getCdKeyDetermina());
        if (entity.getDtDetermina() != null) {
            this.setDtDetermina(new Timestamp(entity.getDtDetermina().getTime()));
        }
    }

    @Override
    public OrgAccordoEnte rowBeanToEntity() {
        OrgAccordoEnte entity = new OrgAccordoEnte();
        if (this.getIdAccordoEnte() != null) {
            entity.setIdAccordoEnte(this.getIdAccordoEnte().longValue());
        }
        if (this.getIdEnteConvenz() != null) {
            if (entity.getOrgEnteSiam() == null) {
                entity.setOrgEnteSiam(new OrgEnteSiam());
            }
            entity.getOrgEnteSiam().setIdEnteSiam(this.getIdEnteConvenz().longValue());
        }
        entity.setDtRegAccordo(this.getDtRegAccordo());
        if (this.getIdTipoAccordo() != null) {
            if (entity.getOrgTipoAccordo() == null) {
                entity.setOrgTipoAccordo(new OrgTipoAccordo());
            }
            entity.getOrgTipoAccordo().setIdTipoAccordo(this.getIdTipoAccordo().longValue());
        }
        if (this.getIdTariffario() != null) {
            if (entity.getOrgTariffario() == null) {
                entity.setOrgTariffario(new OrgTariffario());
            }
            entity.getOrgTariffario().setIdTariffario(this.getIdTariffario().longValue());
        }
        if (this.getIdClasseEnteConvenz() != null) {
            if (entity.getOrgClasseEnteConvenz() == null) {
                entity.setOrgClasseEnteConvenz(new OrgClasseEnteConvenz());
            }
            entity.getOrgClasseEnteConvenz().setIdClasseEnteConvenz(this.getIdClasseEnteConvenz().longValue());
        }
        entity.setNiAbitanti(this.getNiAbitanti());
        entity.setDtScadAccordo(this.getDtScadAccordo());
        entity.setDtFineValidAccordo(this.getDtFineValidAccordo());
        entity.setDsFirmatarioEnte(this.getDsFirmatarioEnte());
        entity.setDtRichModuloInfo(this.getDtRichModuloInfo());
        entity.setFlPagamento(this.getFlPagamento());
        entity.setCdRegistroRepertorio(this.getCdRegistroRepertorio());
        entity.setAaRepertorio(this.getAaRepertorio());
        entity.setCdKeyRepertorio(this.getCdKeyRepertorio());
        entity.setDsNoteAccordo(this.getDsNoteAccordo());
        entity.setCdUfe(this.getCdUfe());
        entity.setDsUfe(this.getDsUfe());
        entity.setCdCig(this.getCdCig());
        entity.setCdCup(this.getCdCup());
        entity.setCdCapitolo(this.getCdCapitolo());
        entity.setCdCoge(this.getCdCoge());
        if (this.getIdCdIva() != null) {
            if (entity.getOrgCdIva() == null) {
                entity.setOrgCdIva(new OrgCdIva());
            }
            entity.getOrgCdIva().setIdCdIva(this.getIdCdIva().longValue());
        }
        entity.setCdRifContab(this.getCdRifContab());
        entity.setCdDdt(this.getCdDdt());
        entity.setCdOda(this.getCdOda());
        entity.setDtDecAccordo(this.getDtDecAccordo());
        entity.setDtDecAccordoInfo(this.getDtDecAccordoInfo());
        entity.setFlRecesso(this.getFlRecesso());
        entity.setFlAccordoChiuso(this.getFlAccordoChiuso());
        entity.setDsAttoAccordo(this.getDsAttoAccordo());
        entity.setDtAttoAccordo(this.getDtAttoAccordo());
        entity.setTiScopoAccordo(TiScopoAccordo.valueOf(this.getTiScopoAccordo()));
        entity.setDsNotaRecesso(this.getDsNotaRecesso());
        entity.setDsNotaFatturazione(this.getDsNotaFatturazione());
        entity.setCdClienteFatturazione(this.getCdClienteFatturazione());
        if (this.getIdEnteConvenzAmministratore() != null) {
            if (entity.getOrgEnteSiamByIdEnteConvenzAmministratore() == null) {
                entity.setOrgEnteSiamByIdEnteConvenzAmministratore(new OrgEnteSiam());
            }
            entity.getOrgEnteSiamByIdEnteConvenzAmministratore()
                    .setIdEnteSiam(this.getIdEnteConvenzAmministratore().longValue());
        }
        if (this.getIdEnteConvenzConserv() != null) {
            if (entity.getOrgEnteSiamByIdEnteConvenzConserv() == null) {
                entity.setOrgEnteSiamByIdEnteConvenzConserv(new OrgEnteSiam());
            }
            entity.getOrgEnteSiamByIdEnteConvenzConserv().setIdEnteSiam(this.getIdEnteConvenzConserv().longValue());
        }
        if (this.getIdEnteConvenzGestore() != null) {
            if (entity.getOrgEnteSiamByIdEnteConvenzGestore() == null) {
                entity.setOrgEnteSiamByIdEnteConvenzGestore(new OrgEnteSiam());
            }
            entity.getOrgEnteSiamByIdEnteConvenzGestore().setIdEnteSiam(this.getIdEnteConvenzGestore().longValue());
        }
        if (this.getIdClusterAccordo() != null) {
            if (entity.getOrgClusterAccordo() == null) {
                entity.setOrgClusterAccordo(new OrgClusterAccordo());
            }
            entity.getOrgClusterAccordo().setIdClusterAccordo(this.getIdClusterAccordo().longValue());
        }
        if (this.getIdFasciaStorageStandardAccordo() != null) {
            if (entity.getOrgFasciaStorageStandardAccordo() == null) {
                entity.setOrgFasciaStorageStandardAccordo(new OrgFasciaStorageAccordo());
            }
            entity.getOrgFasciaStorageStandardAccordo()
                    .setIdFasciaStorageAccordo(this.getIdFasciaStorageStandardAccordo().longValue());
        }
        if (this.getIdFasciaStorageManualeAccordo() != null) {
            if (entity.getOrgFasciaStorageManualeAccordo() == null) {
                entity.setOrgFasciaStorageManualeAccordo(new OrgFasciaStorageAccordo());
            }
            entity.getOrgFasciaStorageManualeAccordo()
                    .setIdFasciaStorageAccordo(this.getIdFasciaStorageManualeAccordo().longValue());
        }
        entity.setNiTipoUdStandard(this.getNiTipoUdStandard());
        entity.setNiTipoUdManuale(this.getNiTipoUdManuale());
        entity.setNiRefertiStandard(this.getNiRefertiStandard());
        entity.setNiRefertiManuale(this.getNiRefertiManuale());
        entity.setNiStudioDicom(this.getNiStudioDicom());
        entity.setDsNotaAttivazione(this.getDsNotaAttivazione());
        entity.setDsNoteEnteAccordo(this.getDsNoteEnteAccordo());
        entity.setNmEnte(this.getNmEnte());
        entity.setNmStrut(this.getNmStrut());
        entity.setImAttivDocAmm(this.getImAttivDocAmm());
        entity.setImAttivDocSani(this.getImAttivDocSani());
        entity.setNiStudioDicomPrev(this.getNiStudioDicomPrev());
        entity.setCdRegistroDetermina(this.getCdRegistroDetermina());
        entity.setAaDetermina(this.getAaDetermina());
        entity.setCdKeyDetermina(this.getCdKeyDetermina());
        entity.setDtDetermina(this.getDtDetermina());
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
