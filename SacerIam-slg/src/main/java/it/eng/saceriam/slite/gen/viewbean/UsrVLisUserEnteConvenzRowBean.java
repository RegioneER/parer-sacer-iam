package it.eng.saceriam.slite.gen.viewbean;

import it.eng.saceriam.viewEntity.UsrVLisUserEnteConvenz;
import it.eng.saceriam.viewEntity.UsrVLisUserEnteConvenzId;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * RowBean per la tabella Usr_V_Lis_User_Ente_Convenz
 *
 */
public class UsrVLisUserEnteConvenzRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Friday, 20 October 2017 16:58" )
     */

    public static UsrVLisUserEnteConvenzTableDescriptor TABLE_DESCRIPTOR = new UsrVLisUserEnteConvenzTableDescriptor();

    public UsrVLisUserEnteConvenzRowBean() {
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

    public BigDecimal getIdApplic() {
        return getBigDecimal("id_applic");
    }

    public void setIdApplic(BigDecimal idApplic) {
        setObject("id_applic", idApplic);
    }

    public BigDecimal getIdUserIam() {
        return getBigDecimal("id_user_iam");
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        setObject("id_user_iam", idUserIam);
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

    public String getNmUserid() {
        return getString("nm_userid");
    }

    public void setNmUserid(String nmUserid) {
        setObject("nm_userid", nmUserid);
    }

    public String getNmApplic() {
        return getString("nm_applic");
    }

    public void setNmApplic(String nmApplic) {
        setObject("nm_applic", nmApplic);
    }

    public String getNmRuoloDefault() {
        return getString("nm_ruolo_default");
    }

    public void setNmRuoloDefault(String nmRuoloDefault) {
        setObject("nm_ruolo_default", nmRuoloDefault);
    }

    public String getDlCompositoOrganiz() {
        return getString("dl_composito_organiz");
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        setObject("dl_composito_organiz", dlCompositoOrganiz);
    }

    public String getFlUserAdmin() {
        return getString("fl_user_admin");
    }

    public void setFlUserAdmin(String flUserAdmin) {
        setObject("fl_user_admin", flUserAdmin);
    }

    public String getTipoUser() {
        return getString("tipo_user");
    }

    public void setTipoUser(String tipoUser) {
        setObject("tipo_user", tipoUser);
    }

    public BigDecimal getIdSistemaVersante() {
        return getBigDecimal("id_sistema_versante");
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
        setObject("id_sistema_versante", idSistemaVersante);
    }

    public String getTiStatoUser() {
        return getString("ti_stato_user");
    }

    public void setTiStatoUser(String tiStatoUser) {
        setObject("ti_stato_user", tiStatoUser);
    }

    public BigDecimal getIdRichGestUser() {
        return getBigDecimal("id_rich_gest_user");
    }

    public void setIdRichGestUser(BigDecimal idRichGestUser) {
        setObject("id_rich_gest_user", idRichGestUser);
    }

    public Timestamp getDtRichGestUser() {
        return getTimestamp("dt_rich_gest_user");
    }

    public void setDtRichGestUser(Timestamp dtRichGestUser) {
        setObject("dt_rich_gest_user", dtRichGestUser);
    }

    public String getKeyRichGestUser() {
        return getString("key_rich_gest_user");
    }

    public void setKeyRichGestUser(String keyRichGestUser) {
        setObject("key_rich_gest_user", keyRichGestUser);
    }

    public String getFlAzioniEvase() {
        return getString("fl_azioni_evase");
    }

    public void setFlAzioniEvase(String flAzioniEvase) {
        setObject("fl_azioni_evase", flAzioniEvase);
    }

    public String getDsListaAzioni() {
        return getString("ds_lista_azioni");
    }

    public void setDsListaAzioni(String dsListaAzioni) {
        setObject("ds_lista_azioni", dsListaAzioni);
    }

    public String getTiEnteConvenzUser() {
        return getString("ti_ente_convenz_user");
    }

    public void setTiEnteConvenzUser(String tiEnteConvenzUser) {
        setObject("ti_ente_convenz_user", tiEnteConvenzUser);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrVLisUserEnteConvenz entity = (UsrVLisUserEnteConvenz) obj;
        this.setIdEnteConvenz(entity.getUsrVLisUserEnteConvenzId() == null ? null
                : entity.getUsrVLisUserEnteConvenzId().getIdEnteConvenz());
        this.setIdUserIam(entity.getUsrVLisUserEnteConvenzId() == null ? null
                : entity.getUsrVLisUserEnteConvenzId().getIdUserIam());
        this.setNmCognomeUser(entity.getNmCognomeUser());
        this.setNmNomeUser(entity.getNmNomeUser());
        this.setNmUserid(entity.getNmUserid());
        this.setNmApplic(entity.getNmApplic());
        this.setNmRuoloDefault(entity.getNmRuoloDefault());
        this.setDlCompositoOrganiz(entity.getDlCompositoOrganiz());
        this.setTipoUser(entity.getTipoUser());
        this.setIdSistemaVersante(entity.getIdSistemaVersante());
        this.setTiStatoUser(entity.getTiStatoUser());
        this.setIdRichGestUser(entity.getIdRichGestUser());
        this.setTiEnteConvenzUser(entity.getTiEnteConvenzUser());
        if (entity.getDtRichGestUser() != null) {
            this.setDtRichGestUser(new Timestamp(entity.getDtRichGestUser().getTime()));
        }
        this.setKeyRichGestUser(entity.getKeyRichGestUser());
        this.setFlAzioniEvase(entity.getFlAzioniEvase());
        this.setDsListaAzioni(entity.getDsListaAzioni());
    }

    @Override
    public UsrVLisUserEnteConvenz rowBeanToEntity() {
        UsrVLisUserEnteConvenz entity = new UsrVLisUserEnteConvenz();
        entity.setUsrVLisUserEnteConvenzId(new UsrVLisUserEnteConvenzId());
        entity.getUsrVLisUserEnteConvenzId().setIdEnteConvenz(this.getIdEnteConvenz());
        entity.getUsrVLisUserEnteConvenzId().setIdUserIam(this.getIdUserIam());
        entity.setNmCognomeUser(this.getNmCognomeUser());
        entity.setNmNomeUser(this.getNmNomeUser());
        entity.setNmUserid(this.getNmUserid());
        entity.setNmApplic(this.getNmApplic());
        entity.setNmRuoloDefault(this.getNmRuoloDefault());
        entity.setDlCompositoOrganiz(this.getDlCompositoOrganiz());
        entity.setTipoUser(this.getTipoUser());
        entity.setIdSistemaVersante(this.getIdSistemaVersante());
        entity.setTiStatoUser(this.getTiStatoUser());
        entity.setIdRichGestUser(this.getIdRichGestUser());
        entity.setDtRichGestUser(this.getDtRichGestUser());
        entity.setKeyRichGestUser(this.getKeyRichGestUser());
        entity.setFlAzioniEvase(this.getFlAzioniEvase());
        entity.setDsListaAzioni(this.getDsListaAzioni());
        entity.setTiEnteConvenzUser(this.getTiEnteConvenzUser());
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
