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
public class OrgVCreaServFattUnatantumId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal aaServizioFattura;

    @Column(name = "AA_SERVIZIO_FATTURA")
    public BigDecimal getAaServizioFattura() {
	return aaServizioFattura;
    }

    public void setAaServizioFattura(BigDecimal aaServizioFattura) {
	this.aaServizioFattura = aaServizioFattura;
    }

    private BigDecimal idFatturaEnte;

    @Column(name = "ID_FATTURA_ENTE")
    public BigDecimal getIdFatturaEnte() {
	return idFatturaEnte;
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
	this.idFatturaEnte = idFatturaEnte;
    }

    private BigDecimal idServizioErogato;

    @Column(name = "ID_SERVIZIO_EROGATO")
    public BigDecimal getIdServizioErogato() {
	return idServizioErogato;
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
	this.idServizioErogato = idServizioErogato;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 41 * hash + Objects.hashCode(this.aaServizioFattura);
	hash = 41 * hash + Objects.hashCode(this.idFatturaEnte);
	hash = 41 * hash + Objects.hashCode(this.idServizioErogato);
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
	final OrgVCreaServFattUnatantumId other = (OrgVCreaServFattUnatantumId) obj;
	if (!Objects.equals(this.aaServizioFattura, other.aaServizioFattura)) {
	    return false;
	}
	if (!Objects.equals(this.idFatturaEnte, other.idFatturaEnte)) {
	    return false;
	}
	if (!Objects.equals(this.idServizioErogato, other.idServizioErogato)) {
	    return false;
	}
	return true;
    }

}
