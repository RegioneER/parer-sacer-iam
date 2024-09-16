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

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the ORG_V_ENTE_CONV_BY_DELABILORG database table.
 */
@Entity
@Table(name = "ORG_V_ENTE_CONV_BY_DELABILORG")
public class OrgVEnteConvByDelabilorg implements Serializable {

    private static final long serialVersionUID = 1L;

    public OrgVEnteConvByDelabilorg() {
    }

    private OrgVEnteConvByDelabilorgId orgVEnteConvByDelabilorgId;

    @EmbeddedId()
    public OrgVEnteConvByDelabilorgId getOrgVEnteConvByDelabilorgId() {
        return orgVEnteConvByDelabilorgId;
    }

    public void setOrgVEnteConvByDelabilorgId(OrgVEnteConvByDelabilorgId orgVEnteConvByDelabilorgId) {
        this.orgVEnteConvByDelabilorgId = orgVEnteConvByDelabilorgId;
    }
}
