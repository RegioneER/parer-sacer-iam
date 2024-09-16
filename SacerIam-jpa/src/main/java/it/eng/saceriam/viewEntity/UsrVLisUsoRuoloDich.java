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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_LIS_USO_RUOLO_DICH database table.
 * 
 */
@Entity
@Table(name = "USR_V_LIS_USO_RUOLO_DICH")
public class UsrVLisUsoRuoloDich implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dlCompositoOrganiz;
    private String dsRuolo;
    private BigDecimal idDichAbilOrganiz;
    private BigDecimal idOrganizIamRuolo;
    private BigDecimal idRuolo;
    private BigDecimal idUsoRuoloDich;
    private String nmRuolo;
    private String tiScopoRuolo;

    public UsrVLisUsoRuoloDich() {
        // document why this constructor is empty
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "DS_RUOLO")
    public String getDsRuolo() {
        return this.dsRuolo;
    }

    public void setDsRuolo(String dsRuolo) {
        this.dsRuolo = dsRuolo;
    }

    @Column(name = "ID_DICH_ABIL_ORGANIZ")
    public BigDecimal getIdDichAbilOrganiz() {
        return this.idDichAbilOrganiz;
    }

    public void setIdDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        this.idDichAbilOrganiz = idDichAbilOrganiz;
    }

    @Column(name = "ID_ORGANIZ_IAM_RUOLO")
    public BigDecimal getIdOrganizIamRuolo() {
        return this.idOrganizIamRuolo;
    }

    public void setIdOrganizIamRuolo(BigDecimal idOrganizIamRuolo) {
        this.idOrganizIamRuolo = idOrganizIamRuolo;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
        return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        this.idRuolo = idRuolo;
    }

    @Id
    @Column(name = "ID_USO_RUOLO_DICH")
    public BigDecimal getIdUsoRuoloDich() {
        return this.idUsoRuoloDich;
    }

    public void setIdUsoRuoloDich(BigDecimal idUsoRuoloDich) {
        this.idUsoRuoloDich = idUsoRuoloDich;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
        return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
        this.nmRuolo = nmRuolo;
    }

    @Column(name = "TI_SCOPO_RUOLO")
    public String getTiScopoRuolo() {
        return this.tiScopoRuolo;
    }

    public void setTiScopoRuolo(String tiScopoRuolo) {
        this.tiScopoRuolo = tiScopoRuolo;
    }

}
