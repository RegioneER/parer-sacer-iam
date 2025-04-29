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
public class UsrVCheckRuoloDefaultId implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idAutorAggiunta;

    @Column(name = "ID_AUTOR_AGGIUNTA")
    public BigDecimal getIdAutorAggiunta() {
	return idAutorAggiunta;
    }

    public void setIdAutorAggiunta(BigDecimal idAutorAggiunta) {
	this.idAutorAggiunta = idAutorAggiunta;
    }

    private BigDecimal idRuoloAggiunto;

    @Column(name = "ID_RUOLO_AGGIUNTO")
    public BigDecimal getIdRuoloAggiunto() {
	return idRuoloAggiunto;
    }

    public void setIdRuoloAggiunto(BigDecimal idRuoloAggiunto) {
	this.idRuoloAggiunto = idRuoloAggiunto;
    }

    private BigDecimal idUserCorrente;

    @Column(name = "ID_USER_CORRENTE")
    public BigDecimal getIdUserCorrente() {
	return idUserCorrente;
    }

    public void setIdUserCorrente(BigDecimal idUserCorrente) {
	this.idUserCorrente = idUserCorrente;
    }

    private String tiDichAutor;

    @Column(name = "TI_DICH_AUTOR")
    public String getTiDichAutor() {
	return tiDichAutor;
    }

    public void setTiDichAutor(String tiDichAutor) {
	this.tiDichAutor = tiDichAutor;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 17 * hash + Objects.hashCode(this.idAutorAggiunta);
	hash = 17 * hash + Objects.hashCode(this.idRuoloAggiunto);
	hash = 17 * hash + Objects.hashCode(this.idUserCorrente);
	hash = 17 * hash + Objects.hashCode(this.tiDichAutor);
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
	final UsrVCheckRuoloDefaultId other = (UsrVCheckRuoloDefaultId) obj;
	if (!Objects.equals(this.tiDichAutor, other.tiDichAutor)) {
	    return false;
	}
	if (!Objects.equals(this.idAutorAggiunta, other.idAutorAggiunta)) {
	    return false;
	}
	if (!Objects.equals(this.idRuoloAggiunto, other.idRuoloAggiunto)) {
	    return false;
	}
	if (!Objects.equals(this.idUserCorrente, other.idUserCorrente)) {
	    return false;
	}
	return true;
    }
}
