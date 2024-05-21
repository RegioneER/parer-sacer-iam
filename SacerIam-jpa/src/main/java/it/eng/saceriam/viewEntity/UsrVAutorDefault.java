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
 * The persistent class for the USR_V_AUTOR_DEFAULT database table.
 */
@Entity
@Table(name = "USR_V_AUTOR_DEFAULT")
public class UsrVAutorDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAutor;

    private BigDecimal idUsoRuoloUserDefault;

    private BigDecimal idUsoUserApplic;

    private String nmApplic;

    private String nmAutor;

    private String nmRuolo;

    private String nmUserid;

    public UsrVAutorDefault() {
        // document why this constructor is empty
    }

    @Column(name = "DS_AUTOR")
    public String getDsAutor() {
        return this.dsAutor;
    }

    public void setDsAutor(String dsAutor) {
        this.dsAutor = dsAutor;
    }

    @Column(name = "ID_USO_RUOLO_USER_DEFAULT")
    public BigDecimal getIdUsoRuoloUserDefault() {
        return this.idUsoRuoloUserDefault;
    }

    public void setIdUsoRuoloUserDefault(BigDecimal idUsoRuoloUserDefault) {
        this.idUsoRuoloUserDefault = idUsoRuoloUserDefault;
    }

    @Column(name = "ID_USO_USER_APPLIC")
    public BigDecimal getIdUsoUserApplic() {
        return this.idUsoUserApplic;
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
        this.idUsoUserApplic = idUsoUserApplic;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_AUTOR")
    public String getNmAutor() {
        return this.nmAutor;
    }

    public void setNmAutor(String nmAutor) {
        this.nmAutor = nmAutor;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
        return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
        this.nmRuolo = nmRuolo;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    private UsrVAutorDefaultId usrVAutorDefaultId;

    @EmbeddedId()
    public UsrVAutorDefaultId getUsrVAutorDefaultId() {
        return usrVAutorDefaultId;
    }

    public void setUsrVAutorDefaultId(UsrVAutorDefaultId usrVAutorDefaultId) {
        this.usrVAutorDefaultId = usrVAutorDefaultId;
    }
}
