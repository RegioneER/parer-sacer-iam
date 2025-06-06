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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORG_STO_ENTE_AMBIENTE_CONVENZ database table.
 *
 */
@Entity
@Table(name = "ORG_STO_ENTE_AMBIENTE_CONVENZ")
public class OrgStoEnteAmbienteConvenz implements Serializable {
    private static final long serialVersionUID = 1L;
    private long idStoEnteAmbienteConvenz;
    private Date dtFinVal;
    private Date dtIniVal;
    private OrgAmbienteEnteConvenz orgAmbienteEnteConvenz;
    private OrgEnteSiam orgEnteConvenz;

    public OrgStoEnteAmbienteConvenz() {
    }

    @Id
    @SequenceGenerator(name = "ORG_STO_ENTE_AMBIENTE_CONVENZ_IDSTOENTEAMBIENTECONVENZ_GENERATOR", sequenceName = "SORG_STO_ENTE_AMBIENTE_CONVENZ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_STO_ENTE_AMBIENTE_CONVENZ_IDSTOENTEAMBIENTECONVENZ_GENERATOR")
    @Column(name = "ID_STO_ENTE_AMBIENTE_CONVENZ")
    public long getIdStoEnteAmbienteConvenz() {
	return this.idStoEnteAmbienteConvenz;
    }

    public void setIdStoEnteAmbienteConvenz(long idStoEnteAmbienteConvenz) {
	this.idStoEnteAmbienteConvenz = idStoEnteAmbienteConvenz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL")
    public Date getDtFinVal() {
	return this.dtFinVal;
    }

    public void setDtFinVal(Date dtFinVal) {
	this.dtFinVal = dtFinVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
	return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
	this.dtIniVal = dtIniVal;
    }

    // bi-directional many-to-one association to OrgAmbienteEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public OrgAmbienteEnteConvenz getOrgAmbienteEnteConvenz() {
	return this.orgAmbienteEnteConvenz;
    }

    public void setOrgAmbienteEnteConvenz(OrgAmbienteEnteConvenz orgAmbienteEnteConvenz) {
	this.orgAmbienteEnteConvenz = orgAmbienteEnteConvenz;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteConvenz() {
	return this.orgEnteConvenz;
    }

    public void setOrgEnteConvenz(OrgEnteSiam orgEnteConvenz) {
	this.orgEnteConvenz = orgEnteConvenz;
    }

}
