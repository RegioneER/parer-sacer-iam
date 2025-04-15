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

package it.eng.saceriam.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * The persistent class for the USR_USER_EXT database table.
 *
 */
@Entity
@Table(name = "USR_USER_EXT")
public class UsrUserExt implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUserIam;
    private String dlCertClient;

    public UsrUserExt() {
	// Costruttore vuoto
    }

    @Id
    @Column(name = "ID_USER_IAM")
    public Long getIdUserIam() {
	return this.idUserIam;
    }

    public void setIdUserIam(Long idUserIam) {
	this.idUserIam = idUserIam;
    }

    @Lob
    @Column(name = "DL_CERT_CLIENT")
    public String getDlCertClient() {
	return dlCertClient;
    }

    public void setDlCertClient(String dlCertClient) {
	this.dlCertClient = dlCertClient;
    }

}
