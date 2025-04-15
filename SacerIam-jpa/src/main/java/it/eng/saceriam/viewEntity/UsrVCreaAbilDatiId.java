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
public class UsrVCreaAbilDatiId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idClasseTipoDato;

    @Column(name = "ID_CLASSE_TIPO_DATO")
    public BigDecimal getIdClasseTipoDato() {
	return idClasseTipoDato;
    }

    public void setIdClasseTipoDato(BigDecimal idClasseTipoDato) {
	this.idClasseTipoDato = idClasseTipoDato;
    }

    private BigDecimal idOrganizIam;

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
	return idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
	this.idOrganizIam = idOrganizIam;
    }

    private BigDecimal idUsoUserApplic;

    @Column(name = "ID_USO_USER_APPLIC")
    public BigDecimal getIdUsoUserApplic() {
	return idUsoUserApplic;
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
	this.idUsoUserApplic = idUsoUserApplic;
    }

    private String tiScopoDichAbilDati;

    @Column(name = "TI_SCOPO_DICH_ABIL_DATI")
    public String getTiScopoDichAbilDati() {
	return tiScopoDichAbilDati;
    }

    public void setTiScopoDichAbilDati(String tiScopoDichAbilDati) {
	this.tiScopoDichAbilDati = tiScopoDichAbilDati;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 79 * hash + Objects.hashCode(this.idClasseTipoDato);
	hash = 79 * hash + Objects.hashCode(this.idOrganizIam);
	hash = 79 * hash + Objects.hashCode(this.idUsoUserApplic);
	hash = 79 * hash + Objects.hashCode(this.tiScopoDichAbilDati);
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
	final UsrVCreaAbilDatiId other = (UsrVCreaAbilDatiId) obj;
	if (!Objects.equals(this.tiScopoDichAbilDati, other.tiScopoDichAbilDati)) {
	    return false;
	}
	if (!Objects.equals(this.idClasseTipoDato, other.idClasseTipoDato)) {
	    return false;
	}
	if (!Objects.equals(this.idOrganizIam, other.idOrganizIam)) {
	    return false;
	}
	if (!Objects.equals(this.idUsoUserApplic, other.idUsoUserApplic)) {
	    return false;
	}
	return true;
    }
}
