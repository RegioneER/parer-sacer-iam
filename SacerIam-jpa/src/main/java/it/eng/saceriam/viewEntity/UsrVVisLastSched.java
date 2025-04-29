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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USR_V_VIS_LAST_SCHED database table.
 *
 */
@Entity
@Table(name = "USR_V_VIS_LAST_SCHED")
public class UsrVVisLastSched implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date dtRegLogJobIni;
    private String flErr;
    private String flJobAttivo;
    private BigDecimal idLogJob;
    private String nmJob;

    public UsrVVisLastSched() {
	// document why this constructor is empty
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_LOG_JOB_INI")
    public Date getDtRegLogJobIni() {
	return this.dtRegLogJobIni;
    }

    public void setDtRegLogJobIni(Date dtRegLogJobIni) {
	this.dtRegLogJobIni = dtRegLogJobIni;
    }

    @Column(name = "FL_ERR", columnDefinition = "char(1)")
    public String getFlErr() {
	return this.flErr;
    }

    public void setFlErr(String flErr) {
	this.flErr = flErr;
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
