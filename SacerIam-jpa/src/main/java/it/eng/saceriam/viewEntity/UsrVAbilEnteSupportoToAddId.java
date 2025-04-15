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
public class UsrVAbilEnteSupportoToAddId implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idEnteConvenz;

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
	return idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
	this.idEnteConvenz = idEnteConvenz;
    }

    private BigDecimal idUserIamCor;

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
	return idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
	this.idUserIamCor = idUserIamCor;
    }

    private BigDecimal idUserIamGestito;

    @Column(name = "ID_USER_IAM_GESTITO")
    public BigDecimal getIdUserIamGestito() {
	return idUserIamGestito;
    }

    public void setIdUserIamGestito(BigDecimal idUserIamGestito) {
	this.idUserIamGestito = idUserIamGestito;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	UsrVAbilEnteSupportoToAddId that = (UsrVAbilEnteSupportoToAddId) o;
	return idEnteConvenz.equals(that.idEnteConvenz) && idUserIamCor.equals(that.idUserIamCor)
		&& idUserIamGestito.equals(that.idUserIamGestito);
    }

    @Override
    public int hashCode() {
	return Objects.hash(idEnteConvenz, idUserIamCor, idUserIamGestito);
    }
}
