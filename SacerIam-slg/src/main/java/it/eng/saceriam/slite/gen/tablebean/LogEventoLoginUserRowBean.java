package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.grantedEntity.LogEventoLoginUser;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * RowBean per la tabella Log_Evento_Login_User
 *
 */
public class LogEventoLoginUserRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 22 November 2017 16:00" )
     */
    public static LogEventoLoginUserTableDescriptor TABLE_DESCRIPTOR = new LogEventoLoginUserTableDescriptor();

    public LogEventoLoginUserRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdEventoLoginUser() {
        return getBigDecimal("id_evento_login_user");
    }

    public void setIdEventoLoginUser(BigDecimal idEventoLoginUser) {
        setObject("id_evento_login_user", idEventoLoginUser);
    }

    public String getNmUserid() {
        return getString("nm_userid");
    }

    public void setNmUserid(String nmUserid) {
        setObject("nm_userid", nmUserid);
    }

    public String getCdIndIpClient() {
        return getString("cd_ind_ip_client");
    }

    public void setCdIndIpClient(String cdIndIpClient) {
        setObject("cd_ind_ip_client", cdIndIpClient);
    }

    public String getCdIndServer() {
        return getString("cd_ind_server");
    }

    public void setCdIndServer(String cdIndServer) {
        setObject("cd_ind_server", cdIndServer);
    }

    public Timestamp getDtEvento() {
        return getTimestamp("dt_evento");
    }

    public void setDtEvento(Timestamp dtEvento) {
        setObject("dt_evento", dtEvento);
    }

    public String getTipoEvento() {
        return getString("tipo_evento");
    }

    public void setTipoEvento(String tipoEvento) {
        setObject("tipo_evento", tipoEvento);
    }

    public String getDsEvento() {
        return getString("ds_evento");
    }

    public void setDsEvento(String dsEvento) {
        setObject("ds_evento", dsEvento);
    }

    @Override
    public void entityToRowBean(Object obj) {
        LogEventoLoginUser entity = (LogEventoLoginUser) obj;
        this.setIdEventoLoginUser(new BigDecimal(entity.getIdEventoLoginUser()));
        this.setNmUserid(entity.getNmUserid());
        this.setCdIndIpClient(entity.getCdIndIpClient());
        this.setCdIndServer(entity.getCdIndServer());
        if (entity.getDtEvento() != null) {
            this.setDtEvento(new Timestamp(entity.getDtEvento().getTime()));
        }
        this.setTipoEvento(entity.getTipoEvento());
        this.setDsEvento(entity.getDsEvento());
    }

    @Override
    public LogEventoLoginUser rowBeanToEntity() {
        LogEventoLoginUser entity = new LogEventoLoginUser();
        if (this.getIdEventoLoginUser() != null) {
            entity.setIdEventoLoginUser(this.getIdEventoLoginUser().longValue());
        }
        entity.setNmUserid(this.getNmUserid());
        entity.setCdIndIpClient(this.getCdIndIpClient());
        entity.setCdIndServer(this.getCdIndServer());
        entity.setDtEvento(this.getDtEvento());
        entity.setTipoEvento(this.getTipoEvento());
        entity.setDsEvento(this.getDsEvento());
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
