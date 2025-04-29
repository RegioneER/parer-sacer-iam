/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the LOG_JOB database table.
 */
@Entity
@Table(name = "LOG_JOB")
public class LogJob implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idLogJob;

    private String cdIndIpServer;

    private String dlMsgErr;

    private Date dtRegLogJob;

    private String dtVirtualReg;

    private String nmJob;

    private String tiRegLogJob;

    private String cdIndServer;

    private List<LogUserDaReplic> logUserDaReplics = new ArrayList<>();

    public LogJob() {
    }

    @Id
    @Column(name = "ID_LOG_JOB")
    @GenericGenerator(name = "SLOG_JOB_ID_LOG_JOB_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SLOG_JOB"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SLOG_JOB_ID_LOG_JOB_GENERATOR")
    public Long getIdLogJob() {
	return this.idLogJob;
    }

    public void setIdLogJob(Long idLogJob) {
	this.idLogJob = idLogJob;
    }

    @Column(name = "CD_IND_IP_SERVER")
    public String getCdIndIpServer() {
	return this.cdIndIpServer;
    }

    public void setCdIndIpServer(String cdIndIpServer) {
	this.cdIndIpServer = cdIndIpServer;
    }

    @Column(name = "DL_MSG_ERR")
    public String getDlMsgErr() {
	return this.dlMsgErr;
    }

    public void setDlMsgErr(String dlMsgErr) {
	this.dlMsgErr = dlMsgErr;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_LOG_JOB")
    public Date getDtRegLogJob() {
	return this.dtRegLogJob;
    }

    public void setDtRegLogJob(Date dtRegLogJob) {
	this.dtRegLogJob = dtRegLogJob;
    }

    @Column(name = "DT_VIRTUAL_REG", insertable = false)
    public String getDtVirtualReg() {
	return this.dtVirtualReg;
    }

    public void setDtVirtualReg(String dtVirtualReg) {
	this.dtVirtualReg = dtVirtualReg;
    }

    @Column(name = "NM_JOB")
    public String getNmJob() {
	return this.nmJob;
    }

    public void setNmJob(String nmJob) {
	this.nmJob = nmJob;
    }

    @Column(name = "TI_REG_LOG_JOB")
    public String getTiRegLogJob() {
	return this.tiRegLogJob;
    }

    public void setTiRegLogJob(String tiRegLogJob) {
	this.tiRegLogJob = tiRegLogJob;
    }

    @Column(name = "CD_IND_SERVER")
    public String getCdIndServer() {
	return cdIndServer;
    }

    public void setCdIndServer(String cdIndServer) {
	this.cdIndServer = cdIndServer;
    }

    // bi-directional many-to-one association to LogUserDaReplic
    @OneToMany(mappedBy = "logJob")
    public List<LogUserDaReplic> getLogUserDaReplics() {
	return this.logUserDaReplics;
    }

    public void setLogUserDaReplics(List<LogUserDaReplic> logUserDaReplics) {
	this.logUserDaReplics = logUserDaReplics;
    }

    public LogUserDaReplic addLogUserDaReplic(LogUserDaReplic logUserDaReplic) {
	getLogUserDaReplics().add(logUserDaReplic);
	logUserDaReplic.setLogJob(this);
	return logUserDaReplic;
    }

    public LogUserDaReplic removeLogUserDaReplic(LogUserDaReplic logUserDaReplic) {
	getLogUserDaReplics().remove(logUserDaReplic);
	logUserDaReplic.setLogJob(null);
	return logUserDaReplic;
    }
}
