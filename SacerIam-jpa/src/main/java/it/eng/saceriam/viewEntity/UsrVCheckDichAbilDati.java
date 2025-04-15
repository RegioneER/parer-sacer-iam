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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_CHECK_DICH_ABIL_DATI database table.
 */
@Entity
@Table(name = "USR_V_CHECK_DICH_ABIL_DATI")
public class UsrVCheckDichAbilDati implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private String nmClasseTipoDato;

    private String nmTipoDato;

    public UsrVCheckDichAbilDati() {
	// document why this constructor is empty
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
	return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
	this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
	return this.nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
	this.nmClasseTipoDato = nmClasseTipoDato;
    }

    @Column(name = "NM_TIPO_DATO")
    public String getNmTipoDato() {
	return this.nmTipoDato;
    }

    public void setNmTipoDato(String nmTipoDato) {
	this.nmTipoDato = nmTipoDato;
    }

    private UsrVCheckDichAbilDatiId usrVCheckDichAbilDatiId;

    @EmbeddedId()
    public UsrVCheckDichAbilDatiId getUsrVCheckDichAbilDatiId() {
	return usrVCheckDichAbilDatiId;
    }

    public void setUsrVCheckDichAbilDatiId(UsrVCheckDichAbilDatiId usrVCheckDichAbilDatiId) {
	this.usrVCheckDichAbilDatiId = usrVCheckDichAbilDatiId;
    }
}
