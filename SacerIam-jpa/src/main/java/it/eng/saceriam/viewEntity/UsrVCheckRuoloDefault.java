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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_CHECK_RUOLO_DEFAULT database table.
 */
@Entity
@Table(name = "USR_V_CHECK_RUOLO_DEFAULT")
public class UsrVCheckRuoloDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAutorAggiunta;

    private BigDecimal idApplicScelta;

    private String nmAutorAggiunta;

    public UsrVCheckRuoloDefault() {
        // document why this constructor is empty
    }

    @Column(name = "DS_AUTOR_AGGIUNTA")
    public String getDsAutorAggiunta() {
        return this.dsAutorAggiunta;
    }

    public void setDsAutorAggiunta(String dsAutorAggiunta) {
        this.dsAutorAggiunta = dsAutorAggiunta;
    }

    @Column(name = "ID_APPLIC_SCELTA")
    public BigDecimal getIdApplicScelta() {
        return this.idApplicScelta;
    }

    public void setIdApplicScelta(BigDecimal idApplicScelta) {
        this.idApplicScelta = idApplicScelta;
    }

    @Column(name = "NM_AUTOR_AGGIUNTA")
    public String getNmAutorAggiunta() {
        return this.nmAutorAggiunta;
    }

    public void setNmAutorAggiunta(String nmAutorAggiunta) {
        this.nmAutorAggiunta = nmAutorAggiunta;
    }

    private UsrVCheckRuoloDefaultId usrVCheckRuoloDefaultId;

    @EmbeddedId()
    public UsrVCheckRuoloDefaultId getUsrVCheckRuoloDefaultId() {
        return usrVCheckRuoloDefaultId;
    }

    public void setUsrVCheckRuoloDefaultId(UsrVCheckRuoloDefaultId usrVCheckRuoloDefaultId) {
        this.usrVCheckRuoloDefaultId = usrVCheckRuoloDefaultId;
    }
}
