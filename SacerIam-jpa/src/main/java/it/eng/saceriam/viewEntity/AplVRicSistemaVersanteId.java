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
public class AplVRicSistemaVersanteId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idOrganizIam;

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
	return idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
	this.idOrganizIam = idOrganizIam;
    }

    private BigDecimal idSistemaVersante;

    @Column(name = "ID_SISTEMA_VERSANTE")
    public BigDecimal getIdSistemaVersante() {
	return idSistemaVersante;
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
	this.idSistemaVersante = idSistemaVersante;
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
	int hash = 7;
	hash = 89 * hash + Objects.hashCode(this.idOrganizIam);
	hash = 89 * hash + Objects.hashCode(this.idSistemaVersante);
	hash = 89 * hash + Objects.hashCode(this.idUserIam);
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
	final AplVRicSistemaVersanteId other = (AplVRicSistemaVersanteId) obj;
	if (!Objects.equals(this.idOrganizIam, other.idOrganizIam)) {
	    return false;
	}
	if (!Objects.equals(this.idSistemaVersante, other.idSistemaVersante)) {
	    return false;
	}
	if (!Objects.equals(this.idUserIam, other.idUserIam)) {
	    return false;
	}
	return true;
    }
}
