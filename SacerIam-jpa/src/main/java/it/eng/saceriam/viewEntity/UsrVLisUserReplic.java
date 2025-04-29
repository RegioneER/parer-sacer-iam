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
 * The persistent class for the USR_V_LIS_USER_REPLIC database table.
 *
 */
@Entity
@Table(name = "USR_V_LIS_USER_REPLIC")
public class UsrVLisUserReplic implements Serializable {

    private static final long serialVersionUID = 1L;
    private String cdErr;
    private String dsMsgErr;
    private Date dtErr;
    private Date dtLogUserDaReplic;
    private Date dtChiusuraReplica;
    private BigDecimal idApplic;
    private BigDecimal idLogUserDaReplic;
    private BigDecimal idUserIam;
    private String nmApplic;
    private String nmUserid;
    private String tiOperReplic;
    private String tiStatoReplic;

    public UsrVLisUserReplic() {
	// document why this constructor is empty
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

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
	return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
	this.idApplic = idApplic;
    }

    @Id
    @Column(name = "ID_LOG_USER_DA_REPLIC")
    public BigDecimal getIdLogUserDaReplic() {
	return this.idLogUserDaReplic;
    }

    public void setIdLogUserDaReplic(BigDecimal idLogUserDaReplic) {
	this.idLogUserDaReplic = idLogUserDaReplic;
    }

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
	return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
	this.idUserIam = idUserIam;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
	return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
	this.nmApplic = nmApplic;
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
}
