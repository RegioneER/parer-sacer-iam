/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.entity;

import it.eng.parer.jboss.timer.common.JobTable;
import it.eng.parer.jboss.timer.common.entity.JobDefinition;
import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.math.BigDecimal;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the PIG_DEC_JOB database table.
 *
 */
/* PIG_DEC_JOB */
@Entity
@Table(name = "DEC_JOB")
public class DecJob extends JobDefinition implements JobTable {

    private static final long serialVersionUID = 1L;
    private Long idJob;
    private String nmJob;
    private String tiSchedJob;
    private String tiScopoJob;
    private String dsJob;
    private String nmAmbito;
    private BigDecimal niOrdExec;

    public DecJob() {
        // hibernate
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SDEC_JOB") // @SequenceGenerator(name = "DEC_JOB_IDJOB_GENERATOR",
    // sequenceName = "SDEC_JOB", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEC_JOB_IDJOB_GENERATOR")
    @Column(name = "ID_JOB")
    @Override
    public Long getIdJob() {
        return idJob;
    }

    @Override
    public void setIdJob(Long idJob) {
        this.idJob = idJob;
    }

    @Column(name = "NM_JOB")
    @Override
    public String getNmJob() {
        return nmJob;
    }

    @Override
    public void setNmJob(String nmJob) {
        this.nmJob = nmJob;
    }

    @Column(name = "DS_JOB")
    public String getDsJob() {
        return this.dsJob;
    }

    public void setDsJob(String dsJob) {
        this.dsJob = dsJob;
    }

    @Column(name = "NM_AMBITO")
    public String getNmAmbito() {
        return this.nmAmbito;
    }

    public void setNmAmbito(String nmAmbito) {
        this.nmAmbito = nmAmbito;
    }

    @Column(name = "NI_ORD_EXEC")
    public BigDecimal getNiOrdExec() {
        return this.niOrdExec;
    }

    public void setNiOrdExec(BigDecimal niOrdExec) {
        this.niOrdExec = niOrdExec;
    }

    @Column(name = "TI_SCHED_JOB")
    @Override
    public String getTiSchedJob() {
        return tiSchedJob;
    }

    @Override
    public void setTiSchedJob(String tiSchedJob) {
        this.tiSchedJob = tiSchedJob;
    }

    @Column(name = "TI_SCOPO_JOB")
    @Override
    public String getTiScopoJob() {
        return tiScopoJob;
    }

    @Override
    public void setTiScopoJob(String tiScopoJob) {
        this.tiScopoJob = tiScopoJob;
    }

}
