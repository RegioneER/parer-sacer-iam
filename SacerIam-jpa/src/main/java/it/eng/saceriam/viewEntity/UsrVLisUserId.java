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
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVLisUserId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idRuoloDefault;

    @Column(name = "ID_RUOLO_DEFAULT")
    public BigDecimal getIdRuoloDefault() {
	return idRuoloDefault;
    }

    public void setIdRuoloDefault(BigDecimal idRuoloDefault) {
	this.idRuoloDefault = idRuoloDefault;
    }

    private BigDecimal idRuoloDich;

    @Column(name = "ID_RUOLO_DICH")
    public BigDecimal getIdRuoloDich() {
	return idRuoloDich;
    }

    public void setIdRuoloDich(BigDecimal idRuoloDich) {
	this.idRuoloDich = idRuoloDich;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 29 * hash + Objects.hashCode(this.idRuoloDefault);
	hash = 29 * hash + Objects.hashCode(this.idRuoloDich);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final UsrVLisUserId other = (UsrVLisUserId) obj;
	if (!Objects.equals(this.idRuoloDefault, other.idRuoloDefault)) {
	    return false;
	}
	if (!Objects.equals(this.idRuoloDich, other.idRuoloDich)) {
	    return false;
	}
	return true;
    }
}
