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
 * The persistent class for the USR_V_CREA_ABIL_DATI database table.
 */
@Entity
@Table(name = "USR_V_CREA_ABIL_DATI")
public class UsrVCreaAbilDati implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idSuptEstEnteConvenz;

    private BigDecimal idVigilEnteProdut;

    private String nmApplic;

    private String nmClasseTipoDato;

    private String nmUserid;

    public UsrVCreaAbilDati() {
	// document why this constructor is empty
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
	return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
	this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
	return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
	this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
	return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
	this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
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

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
	return this.nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
	this.nmClasseTipoDato = nmClasseTipoDato;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
	return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
	this.nmUserid = nmUserid;
    }

    private UsrVCreaAbilDatiId usrVCreaAbilDatiId;

    @EmbeddedId()
    public UsrVCreaAbilDatiId getUsrVCreaAbilDatiId() {
	return usrVCreaAbilDatiId;
    }

    public void setUsrVCreaAbilDatiId(UsrVCreaAbilDatiId usrVCreaAbilDatiId) {
	this.usrVCreaAbilDatiId = usrVCreaAbilDatiId;
    }
}
