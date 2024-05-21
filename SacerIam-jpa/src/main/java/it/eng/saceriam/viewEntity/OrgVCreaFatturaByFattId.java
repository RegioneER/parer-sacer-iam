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
public class OrgVCreaFatturaByFattId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal aaFattura;

    @Column(name = "AA_FATTURA")
    public BigDecimal getAaFattura() {
        return aaFattura;
    }

    public void setAaFattura(BigDecimal aaFattura) {
        this.aaFattura = aaFattura;
    }

    private BigDecimal idFatturaEnteDaRicalc;

    @Column(name = "ID_FATTURA_ENTE_DA_RICALC")
    public BigDecimal getIdFatturaEnteDaRicalc() {
        return idFatturaEnteDaRicalc;
    }

    public void setIdFatturaEnteDaRicalc(BigDecimal idFatturaEnteDaRicalc) {
        this.idFatturaEnteDaRicalc = idFatturaEnteDaRicalc;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.aaFattura);
        hash = 53 * hash + Objects.hashCode(this.idFatturaEnteDaRicalc);
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
        final OrgVCreaFatturaByFattId other = (OrgVCreaFatturaByFattId) obj;
        if (!Objects.equals(this.aaFattura, other.aaFattura)) {
            return false;
        }
        if (!Objects.equals(this.idFatturaEnteDaRicalc, other.idFatturaEnteDaRicalc)) {
            return false;
        }
        return true;
    }

}
