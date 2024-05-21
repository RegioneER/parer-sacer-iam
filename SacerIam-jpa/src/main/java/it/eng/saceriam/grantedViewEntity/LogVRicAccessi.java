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

package it.eng.saceriam.grantedViewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the LOG_V_RIC_ACCESSI database table.
 * 
 */
@Entity
@Table(name = "LOG_V_RIC_ACCESSI")
@NamedQuery(name = "LogVRicAccessi.findAll", query = "SELECT l FROM LogVRicAccessi l")
public class LogVRicAccessi implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cdIndIpClient;
    private String cdIndServer;
    private String dsEvento;
    private Date dtEvento;
    private BigDecimal idEvento;
    private String nmUserid;
    private String tipoEvento;
    private String tipoUser;
    private String nmNomeUser;
    private String nmCognomeUser;
    private String cdFiscUser;
    private String dsEmailUser;
    private String cdIdEsterno;

    public LogVRicAccessi() {
    }

    @Column(name = "CD_IND_IP_CLIENT")
    public String getCdIndIpClient() {
        return this.cdIndIpClient;
    }

    public void setCdIndIpClient(String cdIndIpClient) {
        this.cdIndIpClient = cdIndIpClient;
    }

    @Column(name = "CD_IND_SERVER")
    public String getCdIndServer() {
        return this.cdIndServer;
    }

    public void setCdIndServer(String cdIndServer) {
        this.cdIndServer = cdIndServer;
    }

    @Column(name = "DS_EVENTO")
    public String getDsEvento() {
        return this.dsEvento;
    }

    public void setDsEvento(String dsEvento) {
        this.dsEvento = dsEvento;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_EVENTO")
    public Date getDtEvento() {
        return this.dtEvento;
    }

    public void setDtEvento(Date dtEvento) {
        this.dtEvento = dtEvento;
    }

    @Id
    @Column(name = "ID_EVENTO")
    public BigDecimal getIdEvento() {
        return this.idEvento;
    }

    public void setIdEvento(BigDecimal idEvento) {
        this.idEvento = idEvento;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "TIPO_EVENTO")
    public String getTipoEvento() {
        return this.tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @Column(name = "TIPO_USER")
    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    @Column(name = "NM_NOME_USER")
    public String getNmNomeUser() {
        return nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
        this.nmNomeUser = nmNomeUser;
    }

    @Column(name = "NM_COGNOME_USER")
    public String getNmCognomeUser() {
        return nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
        this.nmCognomeUser = nmCognomeUser;
    }

    @Column(name = "CD_FISC_USER")
    public String getCdFiscUser() {
        return cdFiscUser;
    }

    public void setCdFiscUser(String cdFiscUser) {
        this.cdFiscUser = cdFiscUser;
    }

    @Column(name = "DS_EMAIL_USER")
    public String getDsEmailUser() {
        return dsEmailUser;
    }

    public void setDsEmailUser(String dsEmailUser) {
        this.dsEmailUser = dsEmailUser;
    }

    @Column(name = "CD_ID_ESTERNO")
    public String getCdIdEsterno() {
        return cdIdEsterno;
    }

    public void setCdIdEsterno(String cdIdEsterno) {
        this.cdIdEsterno = cdIdEsterno;
    }

}
