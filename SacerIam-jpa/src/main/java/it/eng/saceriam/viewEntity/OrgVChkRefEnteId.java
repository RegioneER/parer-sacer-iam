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
public class OrgVChkRefEnteId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idEnteRef;

    @Column(name = "ID_ENTE_REF")
    public BigDecimal getIdEnteRef() {
	return idEnteRef;
    }

    public void setIdEnteRef(BigDecimal idEnteRef) {
	this.idEnteRef = idEnteRef;
    }

    private BigDecimal idUserIam;

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
	return idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
	this.idUserIam = idUserIam;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 31 * hash + Objects.hashCode(this.idEnteRef);
	hash = 31 * hash + Objects.hashCode(this.idUserIam);
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
	final OrgVChkRefEnteId other = (OrgVChkRefEnteId) obj;
	if (!Objects.equals(this.idEnteRef, other.idEnteRef)) {
	    return false;
	}
	if (!Objects.equals(this.idUserIam, other.idUserIam)) {
	    return false;
	}
	return true;
    }

}
