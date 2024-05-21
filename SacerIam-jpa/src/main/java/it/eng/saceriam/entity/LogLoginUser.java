/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the LOG_LOGIN_USER database table.
 */
/*
 * @Entity
 * 
 * @Table(name = "LOG_LOGIN_USER")
 * 
 * @NamedQuery(name = "LogLoginUser.findAll", query = "SELECT l FROM LogLoginUser l")
 */
public class LogLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idLoginUser;

    private String cdIndIpClient;

    private String cdIndIpServer;

    private Date dtLogin;

    private String dtVirtualLogin;

    private String nmUserid;

    public LogLoginUser() {
    }

    @Id
    @Column(name = "ID_LOGIN_USER")
    @GenericGenerator(name = "SLOG_LOGIN_USER_ID_LOGIN_USER_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SLOG_LOGIN_USER"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SLOG_LOGIN_USER_ID_LOGIN_USER_GENERATOR")
    public Long getIdLoginUser() {
        return this.idLoginUser;
    }

    public void setIdLoginUser(Long idLoginUser) {
        this.idLoginUser = idLoginUser;
    }

    @Column(name = "CD_IND_IP_CLIENT")
    public String getCdIndIpClient() {
        return this.cdIndIpClient;
    }

    public void setCdIndIpClient(String cdIndIpClient) {
        this.cdIndIpClient = cdIndIpClient;
    }

    @Column(name = "CD_IND_IP_SERVER")
    public String getCdIndIpServer() {
        return this.cdIndIpServer;
    }

    public void setCdIndIpServer(String cdIndIpServer) {
        this.cdIndIpServer = cdIndIpServer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_LOGIN")
    public Date getDtLogin() {
        return this.dtLogin;
    }

    public void setDtLogin(Date dtLogin) {
        this.dtLogin = dtLogin;
    }

    @Column(name = "DT_VIRTUAL_LOGIN")
    public String getDtVirtualLogin() {
        return this.dtVirtualLogin;
    }

    public void setDtVirtualLogin(String dtVirtualLogin) {
        this.dtVirtualLogin = dtVirtualLogin;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }
}
