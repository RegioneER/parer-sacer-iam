package it.eng.saceriam.slite.gen.viewbean;

import it.eng.saceriam.viewEntity.UsrVCreaAbilDati;
import it.eng.saceriam.viewEntity.UsrVCreaAbilDatiId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;

/**
 * RowBean per la tabella Usr_V_Crea_Abil_Dati
 *
 */
public class UsrVCreaAbilDatiRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Tuesday, 16 April 2019 14:11" )
     */

    public static UsrVCreaAbilDatiTableDescriptor TABLE_DESCRIPTOR = new UsrVCreaAbilDatiTableDescriptor();

    public UsrVCreaAbilDatiRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public String getNmUserid() {
        return getString("nm_userid");
    }

    public void setNmUserid(String nmUserid) {
        setObject("nm_userid", nmUserid);
    }

    public BigDecimal getIdUsoUserApplic() {
        return getBigDecimal("id_uso_user_applic");
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
        setObject("id_uso_user_applic", idUsoUserApplic);
    }

    public String getNmApplic() {
        return getString("nm_applic");
    }

    public void setNmApplic(String nmApplic) {
        setObject("nm_applic", nmApplic);
    }

    public BigDecimal getIdClasseTipoDato() {
        return getBigDecimal("id_classe_tipo_dato");
    }

    public void setIdClasseTipoDato(BigDecimal idClasseTipoDato) {
        setObject("id_classe_tipo_dato", idClasseTipoDato);
    }

    public String getNmClasseTipoDato() {
        return getString("nm_classe_tipo_dato");
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
        setObject("nm_classe_tipo_dato", nmClasseTipoDato);
    }

    public String getTiScopoDichAbilDati() {
        return getString("ti_scopo_dich_abil_dati");
    }

    public void setTiScopoDichAbilDati(String tiScopoDichAbilDati) {
        setObject("ti_scopo_dich_abil_dati", tiScopoDichAbilDati);
    }

    public BigDecimal getIdOrganizIam() {
        return getBigDecimal("id_organiz_iam");
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        setObject("id_organiz_iam", idOrganizIam);
    }

    public BigDecimal getIdAppartCollegEnti() {
        return getBigDecimal("id_appart_colleg_enti");
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        setObject("id_appart_colleg_enti", idAppartCollegEnti);
    }

    public BigDecimal getIdSuptEstEnteConvenz() {
        return getBigDecimal("id_supt_est_ente_convenz");
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        setObject("id_supt_est_ente_convenz", idSuptEstEnteConvenz);
    }

    public BigDecimal getIdVigilEnteProdut() {
        return getBigDecimal("id_vigil_ente_produt");
    }

    public void setIdVigilEnteProdut(BigDecimal idVigilEnteProdut) {
        setObject("id_vigil_ente_produt", idVigilEnteProdut);
    }

    public String getDsCausaleDich() {
        return getString("ds_causale_dich");
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        setObject("ds_causale_dich", dsCausaleDich);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrVCreaAbilDati entity = (UsrVCreaAbilDati) obj;
        this.setNmUserid(entity.getNmUserid());
        this.setIdUsoUserApplic(
                entity.getUsrVCreaAbilDatiId() == null ? null : entity.getUsrVCreaAbilDatiId().getIdUsoUserApplic());
        this.setNmApplic(entity.getNmApplic());
        this.setIdClasseTipoDato(
                entity.getUsrVCreaAbilDatiId() == null ? null : entity.getUsrVCreaAbilDatiId().getIdClasseTipoDato());
        this.setNmClasseTipoDato(entity.getNmClasseTipoDato());
        this.setTiScopoDichAbilDati(entity.getUsrVCreaAbilDatiId() == null ? null
                : entity.getUsrVCreaAbilDatiId().getTiScopoDichAbilDati());
        this.setIdOrganizIam(
                entity.getUsrVCreaAbilDatiId() == null ? null : entity.getUsrVCreaAbilDatiId().getIdOrganizIam());
        this.setIdAppartCollegEnti(entity.getIdAppartCollegEnti());
        this.setIdSuptEstEnteConvenz(entity.getIdSuptEstEnteConvenz());
        this.setIdVigilEnteProdut(entity.getIdVigilEnteProdut());
        this.setDsCausaleDich(entity.getDsCausaleDich());
    }

    @Override
    public UsrVCreaAbilDati rowBeanToEntity() {
        UsrVCreaAbilDati entity = new UsrVCreaAbilDati();
        entity.setUsrVCreaAbilDatiId(new UsrVCreaAbilDatiId());
        entity.setNmUserid(this.getNmUserid());
        entity.getUsrVCreaAbilDatiId().setIdUsoUserApplic(this.getIdUsoUserApplic());
        entity.setNmApplic(this.getNmApplic());
        entity.getUsrVCreaAbilDatiId().setIdClasseTipoDato(this.getIdClasseTipoDato());
        entity.setNmClasseTipoDato(this.getNmClasseTipoDato());
        entity.getUsrVCreaAbilDatiId().setTiScopoDichAbilDati(this.getTiScopoDichAbilDati());
        entity.getUsrVCreaAbilDatiId().setIdOrganizIam(this.getIdOrganizIam());
        entity.setIdAppartCollegEnti(this.getIdAppartCollegEnti());
        entity.setIdSuptEstEnteConvenz(this.getIdSuptEstEnteConvenz());
        entity.setIdVigilEnteProdut(this.getIdVigilEnteProdut());
        entity.setDsCausaleDich(this.getDsCausaleDich());
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
