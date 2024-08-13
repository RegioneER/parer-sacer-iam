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
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_LIS_USER_VIGIL_BY_STRUT database table.
 */
@Entity
@Table(name = "USR_V_LIS_USER_VIGIL_BY_STRUT")
public class UsrVLisUserVigilByStrut implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idAccordoVigil;

    private BigDecimal idEnteOrganoVigil;

    private BigDecimal idEnteProdut;

    public UsrVLisUserVigilByStrut() {
        // document why this constructor is empty
    }

    @Column(name = "ID_ACCORDO_VIGIL")
    public BigDecimal getIdAccordoVigil() {
        return this.idAccordoVigil;
    }

    public void setIdAccordoVigil(BigDecimal idAccordoVigil) {
        this.idAccordoVigil = idAccordoVigil;
    }

    @Column(name = "ID_ENTE_ORGANO_VIGIL")
    public BigDecimal getIdEnteOrganoVigil() {
        return this.idEnteOrganoVigil;
    }

    public void setIdEnteOrganoVigil(BigDecimal idEnteOrganoVigil) {
        this.idEnteOrganoVigil = idEnteOrganoVigil;
    }

    @Column(name = "ID_ENTE_PRODUT")
    public BigDecimal getIdEnteProdut() {
        return this.idEnteProdut;
    }

    public void setIdEnteProdut(BigDecimal idEnteProdut) {
        this.idEnteProdut = idEnteProdut;
    }

    private UsrVLisUserVigilByStrutId usrVLisUserVigilByStrutId;

    @EmbeddedId()
    public UsrVLisUserVigilByStrutId getUsrVLisUserVigilByStrutId() {
        return usrVLisUserVigilByStrutId;
    }

    public void setUsrVLisUserVigilByStrutId(UsrVLisUserVigilByStrutId usrVLisUserVigilByStrutId) {
        this.usrVLisUserVigilByStrutId = usrVLisUserVigilByStrutId;
    }
}
