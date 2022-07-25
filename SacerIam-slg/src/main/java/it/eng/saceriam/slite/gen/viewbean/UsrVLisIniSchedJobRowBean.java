package it.eng.saceriam.slite.gen.viewbean;

import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import it.eng.saceriam.viewEntity.UsrVLisIniSchedJob;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import javax.annotation.Generated;

/**
 * RowBean per la tabella Usr_V_Lis_Ini_Sched_Job
 *
 */
public class UsrVLisIniSchedJobRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 9 June 2016 11:48" )
     */

    public static UsrVLisIniSchedJobTableDescriptor TABLE_DESCRIPTOR = new UsrVLisIniSchedJobTableDescriptor();

    public UsrVLisIniSchedJobRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    public BigDecimal getIdLogJob() {
        return getBigDecimal("id_log_job");
    }

    public void setIdLogJob(BigDecimal idLogJob) {
        setObject("id_log_job", idLogJob);
    }

    public String getNmJob() {
        return getString("nm_job");
    }

    public void setNmJob(String nmJob) {
        setObject("nm_job", nmJob);
    }

    public Timestamp getDtRegLogJobIni() {
        return getTimestamp("dt_reg_log_job_ini");
    }

    public void setDtRegLogJobIni(Timestamp dtRegLogJobIni) {
        setObject("dt_reg_log_job_ini", dtRegLogJobIni);
    }

    public Timestamp getDtRegLogJobFineSuc() {
        return getTimestamp("dt_reg_log_job_fine_suc");
    }

    public void setDtRegLogJobFineSuc(Timestamp dtRegLogJobFineSuc) {
        setObject("dt_reg_log_job_fine_suc", dtRegLogJobFineSuc);
    }

    public Timestamp getDtRegLogJobIniSuc() {
        return getTimestamp("dt_reg_log_job_ini_suc");
    }

    public void setDtRegLogJobIniSuc(Timestamp dtRegLogJobIniSuc) {
        setObject("dt_reg_log_job_ini_suc", dtRegLogJobIniSuc);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrVLisIniSchedJob entity = (UsrVLisIniSchedJob) obj;
        this.setIdLogJob(entity.getIdLogJob());
        this.setNmJob(entity.getNmJob());
        if (entity.getDtRegLogJobIni() != null) {
            this.setDtRegLogJobIni(new Timestamp(entity.getDtRegLogJobIni().getTime()));
        }
        if (entity.getDtRegLogJobFineSuc() != null) {
            this.setDtRegLogJobFineSuc(new Timestamp(entity.getDtRegLogJobFineSuc().getTime()));
        }
        if (entity.getDtRegLogJobIniSuc() != null) {
            this.setDtRegLogJobIniSuc(new Timestamp(entity.getDtRegLogJobIniSuc().getTime()));
        }
    }

    @Override
    public UsrVLisIniSchedJob rowBeanToEntity() {
        UsrVLisIniSchedJob entity = new UsrVLisIniSchedJob();
        entity.setIdLogJob(this.getIdLogJob());
        entity.setNmJob(this.getNmJob());
        entity.setDtRegLogJobIni(this.getDtRegLogJobIni());
        entity.setDtRegLogJobFineSuc(this.getDtRegLogJobFineSuc());
        entity.setDtRegLogJobIniSuc(this.getDtRegLogJobIniSuc());
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
