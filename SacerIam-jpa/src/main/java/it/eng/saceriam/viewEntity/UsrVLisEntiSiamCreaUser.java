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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_LIS_ENTI_SIAM_CREA_USER database table.
 */
@Entity
@Table(name = "USR_V_LIS_ENTI_SIAM_CREA_USER")
public class UsrVLisEntiSiamCreaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idRichGestUser;

    private String nmAmbienteEnteConvenz;

    private String nmEnteSiam;

    public UsrVLisEntiSiamCreaUser() {
	// document why this constructor is empty
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
	return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
	this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
	return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
	this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_RICH_GEST_USER")
    public BigDecimal getIdRichGestUser() {
	return this.idRichGestUser;
    }

    public void setIdRichGestUser(BigDecimal idRichGestUser) {
	this.idRichGestUser = idRichGestUser;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
	return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
	this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_ENTE_SIAM")
    public String getNmEnteSiam() {
	return this.nmEnteSiam;
    }

    public void setNmEnteSiam(String nmEnteSiam) {
	this.nmEnteSiam = nmEnteSiam;
    }

    private UsrVLisEntiSiamCreaUserId usrVLisEntiSiamCreaUserId;

    @EmbeddedId()
    public UsrVLisEntiSiamCreaUserId getUsrVLisEntiSiamCreaUserId() {
	return usrVLisEntiSiamCreaUserId;
    }

    public void setUsrVLisEntiSiamCreaUserId(UsrVLisEntiSiamCreaUserId usrVLisEntiSiamCreaUserId) {
	this.usrVLisEntiSiamCreaUserId = usrVLisEntiSiamCreaUserId;
    }
}
