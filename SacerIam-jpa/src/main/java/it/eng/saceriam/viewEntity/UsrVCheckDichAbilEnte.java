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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_CHECK_DICH_ABIL_ENTE database table.
 */
@Entity
@Table(name = "USR_V_CHECK_DICH_ABIL_ENTE")
@NamedQuery(name = "UsrVCheckDichAbilEnte.findAll", query = "SELECT u FROM UsrVCheckDichAbilEnte u")
public class UsrVCheckDichAbilEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nmEnteConvenz;

    public UsrVCheckDichAbilEnte() {
        // document why this constructor is empty
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    private UsrVCheckDichAbilEnteId usrVCheckDichAbilEnteId;

    @EmbeddedId()
    public UsrVCheckDichAbilEnteId getUsrVCheckDichAbilEnteId() {
        return usrVCheckDichAbilEnteId;
    }

    public void setUsrVCheckDichAbilEnteId(UsrVCheckDichAbilEnteId usrVCheckDichAbilEnteId) {
        this.usrVCheckDichAbilEnteId = usrVCheckDichAbilEnteId;
    }
}
