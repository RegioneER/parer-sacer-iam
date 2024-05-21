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
 * The persistent class for the ORG_V_CHK_REF_ENTE database table.
 */
@Entity
@Table(name = "ORG_V_CHK_REF_ENTE")
@NamedQuery(name = "OrgVChkRefEnte.findAll", query = "SELECT u FROM OrgVChkRefEnte u")
public class OrgVChkRefEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private String flRefOk;

    public OrgVChkRefEnte() {
        // document why this constructor is empty
    }

    @Column(name = "FL_REF_OK", columnDefinition = "char(1)")
    public String getFlRefOk() {
        return this.flRefOk;
    }

    public void setFlRefOk(String flRefOk) {
        this.flRefOk = flRefOk;
    }

    private OrgVChkRefEnteId orgVChkRefEnteId;

    @EmbeddedId()
    public OrgVChkRefEnteId getOrgVChkRefEnteId() {
        return orgVChkRefEnteId;
    }

    public void setOrgVChkRefEnteId(OrgVChkRefEnteId orgVChkRefEnteId) {
        this.orgVChkRefEnteId = orgVChkRefEnteId;
    }
}
