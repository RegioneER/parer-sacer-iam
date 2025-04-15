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
 * The persistent class for the USR_V_ABIL_ORGANIZ_TO_ADD database table.
 */
@Entity
@Table(name = "USR_V_ABIL_ORGANIZ_TO_ADD")
public class UsrVAbilOrganizToAdd implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dsCausaleAbil;
    private String flAttivo;
    private BigDecimal idAppartCollegEnti;
    private BigDecimal idApplic;
    private BigDecimal idDichAbilOrganiz;
    private BigDecimal idSuptEstEnteConvenz;
    private BigDecimal idUsoUserApplic;
    private BigDecimal idVigilEnteProdut;
    private String nmApplic;

    public UsrVAbilOrganizToAdd() {
	// document why this constructor is empty
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
	return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
	this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "FL_ATTIVO", columnDefinition = "char(1)")
    public String getFlAttivo() {
	return this.flAttivo;
    }

    public void setFlAttivo(String flAttivo) {
	this.flAttivo = flAttivo;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
	return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
	this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
	return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
	this.idApplic = idApplic;
    }

    @Column(name = "ID_DICH_ABIL_ORGANIZ")
    public BigDecimal getIdDichAbilOrganiz() {
	return this.idDichAbilOrganiz;
    }

    public void setIdDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
	this.idDichAbilOrganiz = idDichAbilOrganiz;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
	return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
	this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "ID_USO_USER_APPLIC")
    public BigDecimal getIdUsoUserApplic() {
	return this.idUsoUserApplic;
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
	this.idUsoUserApplic = idUsoUserApplic;
    }

    @Column(name = "ID_VIGIL_ENTE_PRODUT")
    public BigDecimal getIdVigilEnteProdut() {
	return this.idVigilEnteProdut;
    }

    public void setIdVigilEnteProdut(BigDecimal idVigilEnteProdut) {
	this.idVigilEnteProdut = idVigilEnteProdut;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
	return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
	this.nmApplic = nmApplic;
    }

    private UsrVAbilOrganizToAddId usrVAbilOrganizToAddId;

    @EmbeddedId()
    public UsrVAbilOrganizToAddId getUsrVAbilOrganizToAddId() {
	return usrVAbilOrganizToAddId;
    }

    public void setUsrVAbilOrganizToAddId(UsrVAbilOrganizToAddId usrVAbilOrganizToAddId) {
	this.usrVAbilOrganizToAddId = usrVAbilOrganizToAddId;
    }
}
