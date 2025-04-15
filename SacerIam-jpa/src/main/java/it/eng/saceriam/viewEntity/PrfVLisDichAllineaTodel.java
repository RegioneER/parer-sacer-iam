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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the PRF_V_LIS_DICH_ALLINEA_TODEL database table.
 *
 */
@Entity
@Table(name = "PRF_V_LIS_DICH_ALLINEA_TODEL")
public class PrfVLisDichAllineaTodel implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idDichAutor;
    private BigDecimal idRuolo;

    public PrfVLisDichAllineaTodel() {
	// document why this constructor is empty
    }

    @Id
    @Column(name = "ID_DICH_AUTOR")
    public BigDecimal getIdDichAutor() {
	return this.idDichAutor;
    }

    public void setIdDichAutor(BigDecimal idDichAutor) {
	this.idDichAutor = idDichAutor;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
	return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
	this.idRuolo = idRuolo;
    }

}
