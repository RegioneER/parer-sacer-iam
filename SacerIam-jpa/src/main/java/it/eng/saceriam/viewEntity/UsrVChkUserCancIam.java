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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_CHK_USER_CANC_IAM database table.
 *
 */
@Entity
@Table(name = "USR_V_CHK_USER_CANC_IAM")
public class UsrVChkUserCancIam implements Serializable {

    private static final long serialVersionUID = 1L;
    private String flCancOk;
    private BigDecimal idUserIam;

    public UsrVChkUserCancIam() {
	// document why this constructor is empty
    }

    @Column(name = "FL_CANC_OK", columnDefinition = "char(1)")
    public String getFlCancOk() {
	return this.flCancOk;
    }

    public void setFlCancOk(String flCancOk) {
	this.flCancOk = flCancOk;
    }

    @Id
    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
	return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
	this.idUserIam = idUserIam;
    }

}
