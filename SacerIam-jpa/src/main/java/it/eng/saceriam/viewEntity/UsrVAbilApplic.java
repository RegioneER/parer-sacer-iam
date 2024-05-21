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
 * The persistent class for the USR_V_ABIL_APPLIC database table.
 */
@Entity
@Table(name = "USR_V_ABIL_APPLIC")
@NamedQuery(name = "UsrVAbilApplic.findAll", query = "SELECT u FROM UsrVAbilApplic u")
public class UsrVAbilApplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nmApplic;

    private String nmUserid;

    public UsrVAbilApplic() {
        // document why this constructor is empty
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    private UsrVAbilApplicId usrVAbilApplicId;

    @EmbeddedId()
    public UsrVAbilApplicId getUsrVAbilApplicId() {
        return usrVAbilApplicId;
    }

    public void setUsrVAbilApplicId(UsrVAbilApplicId usrVAbilApplicId) {
        this.usrVAbilApplicId = usrVAbilApplicId;
    }
}
