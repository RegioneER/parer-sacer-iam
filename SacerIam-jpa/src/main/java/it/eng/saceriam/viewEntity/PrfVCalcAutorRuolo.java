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
 * The persistent class for the PRF_V_CALC_AUTOR_RUOLO database table.
 */
@Entity
@Table(name = "PRF_V_CALC_AUTOR_RUOLO")
@NamedQuery(name = "PrfVCalcAutorRuolo.findAll", query = "SELECT p FROM PrfVCalcAutorRuolo p")
public class PrfVCalcAutorRuolo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAutor;

    private BigDecimal idUsoRuoloApplic;

    private String nmApplic;

    private String nmRuolo;

    public PrfVCalcAutorRuolo() {
    }

    @Column(name = "DS_AUTOR")
    public String getDsAutor() {
        return this.dsAutor;
    }

    public void setDsAutor(String dsAutor) {
        this.dsAutor = dsAutor;
    }

    @Column(name = "ID_USO_RUOLO_APPLIC")
    public BigDecimal getIdUsoRuoloApplic() {
        return this.idUsoRuoloApplic;
    }

    public void setIdUsoRuoloApplic(BigDecimal idUsoRuoloApplic) {
        this.idUsoRuoloApplic = idUsoRuoloApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
        return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
        this.nmRuolo = nmRuolo;
    }

    private PrfVCalcAutorRuoloId prfVCalcAutorRuoloId;

    @EmbeddedId()
    public PrfVCalcAutorRuoloId getPrfVCalcAutorRuoloId() {
        return prfVCalcAutorRuoloId;
    }

    public void setPrfVCalcAutorRuoloId(PrfVCalcAutorRuoloId prfVCalcAutorRuoloId) {
        this.prfVCalcAutorRuoloId = prfVCalcAutorRuoloId;
    }
}
