package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the USR_V_LIS_SCHED database table.
 *
 */
@Entity
@Table(name = "USR_V_LIS_SCHED")
public class UsrVLisSched implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dlMsgErr;
    private Date dtRegLogJobFine;
    private Date dtRegLogJobIni;
    private BigDecimal durataGg;
    private BigDecimal durataMin;
    private BigDecimal durataOre;
    private BigDecimal durataSec;
    private BigDecimal idLogJob;
    private String nmJob;
    private String tiBlocco;

    public UsrVLisSched() {
    }

    @Column(name = "DL_MSG_ERR")
    public String getDlMsgErr() {
        return this.dlMsgErr;
    }

    public void setDlMsgErr(String dlMsgErr) {
        this.dlMsgErr = dlMsgErr;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_LOG_JOB_FINE")
    public Date getDtRegLogJobFine() {
        return this.dtRegLogJobFine;
    }

    public void setDtRegLogJobFine(Date dtRegLogJobFine) {
        this.dtRegLogJobFine = dtRegLogJobFine;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_LOG_JOB_INI")
    public Date getDtRegLogJobIni() {
        return this.dtRegLogJobIni;
    }

    public void setDtRegLogJobIni(Date dtRegLogJobIni) {
        this.dtRegLogJobIni = dtRegLogJobIni;
    }

    @Column(name = "DURATA_GG")
    public BigDecimal getDurataGg() {
        return this.durataGg;
    }

    public void setDurataGg(BigDecimal durataGg) {
        this.durataGg = durataGg;
    }

    @Column(name = "DURATA_MIN")
    public BigDecimal getDurataMin() {
        return this.durataMin;
    }

    public void setDurataMin(BigDecimal durataMin) {
        this.durataMin = durataMin;
    }

    @Column(name = "DURATA_ORE")
    public BigDecimal getDurataOre() {
        return this.durataOre;
    }

    public void setDurataOre(BigDecimal durataOre) {
        this.durataOre = durataOre;
    }

    @Column(name = "DURATA_SEC")
    public BigDecimal getDurataSec() {
        return this.durataSec;
    }

    public void setDurataSec(BigDecimal durataSec) {
        this.durataSec = durataSec;
    }

    @Id
    @Column(name = "ID_LOG_JOB")
    public BigDecimal getIdLogJob() {
        return this.idLogJob;
    }

    public void setIdLogJob(BigDecimal idLogJob) {
        this.idLogJob = idLogJob;
    }

    @Column(name = "NM_JOB")
    public String getNmJob() {
        return this.nmJob;
    }

    public void setNmJob(String nmJob) {
        this.nmJob = nmJob;
    }

    @Column(name = "TI_BLOCCO")
    public String getTiBlocco() {
        return tiBlocco;
    }

    public void setTiBlocco(String tiBlocco) {
        this.tiBlocco = tiBlocco;
    }

}