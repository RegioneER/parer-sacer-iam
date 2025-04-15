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

package it.eng.saceriam.job.replicaUtenti.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Bonora_L
 */
public class UserApplic {

    private Long idApplic;
    private BigDecimal idUserIam;

    public UserApplic(Long idApplic, BigDecimal idUserIam) {
	this.idApplic = idApplic;
	this.idUserIam = idUserIam;
    }

    public Long getIdApplic() {
	return idApplic;
    }

    public void setIdApplic(Long idApplic) {
	this.idApplic = idApplic;
    }

    public BigDecimal getIdUserIam() {
	return idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
	this.idUserIam = idUserIam;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 59 * hash + Objects.hashCode(this.idApplic);
	hash = 59 * hash + Objects.hashCode(this.idUserIam);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final UserApplic other = (UserApplic) obj;
	if (other.idApplic == null || this.idApplic == null
		|| this.idApplic.compareTo(other.idApplic) != 0) {
	    return false;
	}
	if (other.idUserIam == null || this.idUserIam == null
		|| this.idUserIam.compareTo(other.idUserIam) != 0) {
	    return false;
	}

	return true;
    }

}
