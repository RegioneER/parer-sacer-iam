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
public class UsrVCheckDichAbilDatiId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idDichAbilDatiAggiunta;

    @Column(name = "ID_DICH_ABIL_DATI_AGGIUNTA")
    public BigDecimal getIdDichAbilDatiAggiunta() {
	return idDichAbilDatiAggiunta;
    }

    public void setIdDichAbilDatiAggiunta(BigDecimal idDichAbilDatiAggiunta) {
	this.idDichAbilDatiAggiunta = idDichAbilDatiAggiunta;
    }

    private BigDecimal idTipoDatoIam;

    @Column(name = "ID_TIPO_DATO_IAM")
    public BigDecimal getIdTipoDatoIam() {
	return idTipoDatoIam;
    }

    public void setIdTipoDatoIam(BigDecimal idTipoDatoIam) {
	this.idTipoDatoIam = idTipoDatoIam;
    }

    private BigDecimal idUserIamCorrente;

    @Column(name = "ID_USER_IAM_CORRENTE")
    public BigDecimal getIdUserIamCorrente() {
	return idUserIamCorrente;
    }

    public void setIdUserIamCorrente(BigDecimal idUserIamCorrente) {
	this.idUserIamCorrente = idUserIamCorrente;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 67 * hash + Objects.hashCode(this.idDichAbilDatiAggiunta);
	hash = 67 * hash + Objects.hashCode(this.idTipoDatoIam);
	hash = 67 * hash + Objects.hashCode(this.idUserIamCorrente);
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
	final UsrVCheckDichAbilDatiId other = (UsrVCheckDichAbilDatiId) obj;
	if (!Objects.equals(this.idDichAbilDatiAggiunta, other.idDichAbilDatiAggiunta)) {
	    return false;
	}
	if (!Objects.equals(this.idTipoDatoIam, other.idTipoDatoIam)) {
	    return false;
	}
	if (!Objects.equals(this.idUserIamCorrente, other.idUserIamCorrente)) {
	    return false;
	}
	return true;
    }
}
