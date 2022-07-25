package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the LOG_USER_DA_REPLIC database table.
 *
 */
@Entity
@Table(name = "LOG_USER_DA_REPLIC")
public class LogUserDaReplic implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idLogUserDaReplic;
    private String cdErr;
    private String dsMsgErr;
    private Date dtErr;
    private Date dtLogUserDaReplic;
    private Date dtChiusuraReplica;
    private BigDecimal idUserIam;
    private String nmUserid;
    private String tiOperReplic;
    private String tiStatoReplic;
    private AplApplic aplApplic;
    private LogJob logJob;

    public LogUserDaReplic() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SLOG_USER_DA_REPLIC")
    @Column(name = "ID_LOG_USER_DA_REPLIC")
    public Long getIdLogUserDaReplic() {
        return this.idLogUserDaReplic;
    }

    public void setIdLogUserDaReplic(Long idLogUserDaReplic) {
        this.idLogUserDaReplic = idLogUserDaReplic;
    }

    @Column(name = "CD_ERR")
    public String getCdErr() {
        return this.cdErr;
    }

    public void setCdErr(String cdErr) {
        this.cdErr = cdErr;
    }

    @Column(name = "DS_MSG_ERR")
    public String getDsMsgErr() {
        return this.dsMsgErr;
    }

    public void setDsMsgErr(String dsMsgErr) {
        this.dsMsgErr = dsMsgErr;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ERR")
    public Date getDtErr() {
        return this.dtErr;
    }

    public void setDtErr(Date dtErr) {
        this.dtErr = dtErr;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_LOG_USER_DA_REPLIC")
    public Date getDtLogUserDaReplic() {
        return this.dtLogUserDaReplic;
    }

    public void setDtLogUserDaReplic(Date dtLogUserDaReplic) {
        this.dtLogUserDaReplic = dtLogUserDaReplic;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CHIUSURA_REPLICA")
    public Date getDtChiusuraReplica() {
        return this.dtChiusuraReplica;
    }

    public void setDtChiusuraReplica(Date dtChiusuraReplica) {
        this.dtChiusuraReplica = dtChiusuraReplica;
    }

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "TI_OPER_REPLIC")
    public String getTiOperReplic() {
        return this.tiOperReplic;
    }

    public void setTiOperReplic(String tiOperReplic) {
        this.tiOperReplic = tiOperReplic;
    }

    @Column(name = "TI_STATO_REPLIC")
    public String getTiStatoReplic() {
        return this.tiStatoReplic;
    }

    public void setTiStatoReplic(String tiStatoReplic) {
        this.tiStatoReplic = tiStatoReplic;
    }

    // bi-directional many-to-one association to AplApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPLIC")
    public AplApplic getAplApplic() {
        return this.aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
        this.aplApplic = aplApplic;
    }

    // bi-directional many-to-one association to LogJob
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LOG_JOB")
    public LogJob getLogJob() {
        return this.logJob;
    }

    public void setLogJob(LogJob logJob) {
        this.logJob = logJob;
    }

}
