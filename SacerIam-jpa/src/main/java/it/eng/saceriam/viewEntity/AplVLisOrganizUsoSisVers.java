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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the APL_V_LIS_ORGANIZ_USO_SIS_VERS database table.
 */
@Entity
@Table(name = "APL_V_LIS_ORGANIZ_USO_SIS_VERS")
@NamedQuery(name = "AplVLisOrganizUsoSisVers.findAll", query = "SELECT a FROM AplVLisOrganizUsoSisVers a")
public class AplVLisOrganizUsoSisVers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private BigDecimal idOrganizApplic;

    private String nmApplic;

    private String nmSistemaVersante;

    public AplVLisOrganizUsoSisVers() {
        // document why this constructor is empty
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
        return this.idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
        return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
        this.nmSistemaVersante = nmSistemaVersante;
    }

    private AplVLisOrganizUsoSisVersId aplVLisOrganizUsoSisVersId;

    @EmbeddedId()
    public AplVLisOrganizUsoSisVersId getAplVLisOrganizUsoSisVersId() {
        return aplVLisOrganizUsoSisVersId;
    }

    public void setAplVLisOrganizUsoSisVersId(AplVLisOrganizUsoSisVersId aplVLisOrganizUsoSisVersId) {
        this.aplVLisOrganizUsoSisVersId = aplVLisOrganizUsoSisVersId;
    }
}
