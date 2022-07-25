package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the LOG_V_VIS_LAST_SCHED database table.
 *
 */
@Entity
@Table(name = "LOG_V_VIS_LAST_SCHED")
public class LogVVisLastSched implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date dtRegLogJobIni;
    private String flJobAttivo;
    private BigDecimal idLogJob;
    private String nmJob;

    public LogVVisLastSched() {
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_LOG_JOB_INI")
    public Date getDtRegLogJobIni() {
        return this.dtRegLogJobIni;
    }

    public void setDtRegLogJobIni(Date dtRegLogJobIni) {
        this.dtRegLogJobIni = dtRegLogJobIni;
    }

    @Column(name = "FL_JOB_ATTIVO", columnDefinition = "char(1)")
    public String getFlJobAttivo() {
        return this.flJobAttivo;
    }

    public void setFlJobAttivo(String flJobAttivo) {
        this.flJobAttivo = flJobAttivo;
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

}
