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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ORG_V_VIS_STATO_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_V_VIS_STATO_ACCORDO")
public class OrgVVisStatoAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idEnteConvenz;
    private BigDecimal idAccordoEnte;
    private String nmEnteConvenz;
    private String tiStatoAccordo;

    public OrgVVisStatoAccordo() {
	// document why this constructor is empty
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
	return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
	this.idEnteConvenz = idEnteConvenz;
    }

    @Id
    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
	return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
	this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
	return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
	this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "TI_STATO_ACCORDO")
    public String getTiStatoAccordo() {
	return this.tiStatoAccordo;
    }

    public void setTiStatoAccordo(String tiStatoAccordo) {
	this.tiStatoAccordo = tiStatoAccordo;
    }

}
