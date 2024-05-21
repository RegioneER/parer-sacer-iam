/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVAbilDatiVigilToAddId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoDatoIam;

    @Column(name = "ID_TIPO_DATO_IAM")
    public BigDecimal getIdTipoDatoIam() {
        return idTipoDatoIam;
    }

    public void setIdTipoDatoIam(BigDecimal idTipoDatoIam) {
        this.idTipoDatoIam = idTipoDatoIam;
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
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.idTipoDatoIam);
        hash = 37 * hash + Objects.hashCode(this.idUserIamCor);
        hash = 37 * hash + Objects.hashCode(this.idUserIamGestito);
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
        final UsrVAbilDatiVigilToAddId other = (UsrVAbilDatiVigilToAddId) obj;
        if (!Objects.equals(this.idTipoDatoIam, other.idTipoDatoIam)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCor, other.idUserIamCor)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamGestito, other.idUserIamGestito)) {
            return false;
        }
        return true;
    }

}
