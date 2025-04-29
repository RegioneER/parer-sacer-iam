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
public class OrgVCreaServErogByAccId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idAccordoEnte;

    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
	return idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
	this.idAccordoEnte = idAccordoEnte;
    }

    private String nmServizioErogato;

    @Column(name = "NM_SERVIZIO_EROGATO")
    public String getNmServizioErogato() {
	return nmServizioErogato;
    }

    public void setNmServizioErogato(String nmServizioErogato) {
	this.nmServizioErogato = nmServizioErogato;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	OrgVCreaServErogByAccId that = (OrgVCreaServErogByAccId) o;
	return idAccordoEnte.equals(that.idAccordoEnte)
		&& nmServizioErogato.equals(that.nmServizioErogato);
    }

    @Override
    public int hashCode() {
	return Objects.hash(idAccordoEnte, nmServizioErogato);
    }
}
