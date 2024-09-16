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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USR_V_ABIL_AMB_CONVENZ_XENTE database table.
 */
@Entity
@Table(name = "USR_V_ABIL_AMB_CONVENZ_XENTE")
public class UsrVAbilAmbConvenzXente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String nmAmbienteEnteConvenz;

    private Date dtFineVal;

    private Date dtIniVal;

    private BigDecimal idEnteConserv;

    private BigDecimal idEnteGestore;

    private String nmEnteConserv;

    private String nmEnteGestore;

    public UsrVAbilAmbConvenzXente() {
        // document why this constructor is empty
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "ID_ENTE_CONSERV")
    public BigDecimal getIdEnteConserv() {
        return this.idEnteConserv;
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
        this.idEnteConserv = idEnteConserv;
    }

    @Column(name = "ID_ENTE_GESTORE")
    public BigDecimal getIdEnteGestore() {
        return this.idEnteGestore;
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
        this.idEnteGestore = idEnteGestore;
    }

    @Column(name = "NM_ENTE_CONSERV")
    public String getNmEnteConserv() {
        return this.nmEnteConserv;
    }

    public void setNmEnteConserv(String nmEnteConserv) {
        this.nmEnteConserv = nmEnteConserv;
    }

    @Column(name = "NM_ENTE_GESTORE")
    public String getNmEnteGestore() {
        return this.nmEnteGestore;
    }

    public void setNmEnteGestore(String nmEnteGestore) {
        this.nmEnteGestore = nmEnteGestore;
    }

    private UsrVAbilAmbConvenzXenteId usrVAbilAmbConvenzXenteId;

    @EmbeddedId()
    public UsrVAbilAmbConvenzXenteId getUsrVAbilAmbConvenzXenteId() {
        return usrVAbilAmbConvenzXenteId;
    }

    public void setUsrVAbilAmbConvenzXenteId(UsrVAbilAmbConvenzXenteId usrVAbilAmbConvenzXenteId) {
        this.usrVAbilAmbConvenzXenteId = usrVAbilAmbConvenzXenteId;
    }
}
