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
 * The persistent class for the USR_V_ALL_AUTOR database table.
 */
@Entity
@Table(name = "USR_V_ALL_AUTOR")
public class UsrVAllAutor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAutor;

    private BigDecimal idOrganizApplic;

    private BigDecimal idOrganizIam;

    private BigDecimal idUsoUserApplic;

    private String nmApplic;

    private String nmAutor;

    private String nmOrganizApplic;

    private String nmPaginaWeb;

    private String nmRuolo;

    private String nmTipoOrganizApplic;

    private String nmUserid;

    private String flHelpPresente;

    public UsrVAllAutor() {
    }

    public UsrVAllAutor(String tiDichAutor, String nmPaginaWeb, String nmAutor, String dsAutor,
            BigDecimal idOrganizApplic, String nmTipoOrganizApplic, String tiUsoRuo) {
        this.usrVAllAutorId = new UsrVAllAutorId();
        this.usrVAllAutorId.setTiUsoRuo(tiUsoRuo);
        this.usrVAllAutorId.setTiDichAutor(tiDichAutor);
        this.nmPaginaWeb = nmPaginaWeb;
        this.nmAutor = nmAutor;
        this.dsAutor = dsAutor;
        this.idOrganizApplic = idOrganizApplic;
        this.nmTipoOrganizApplic = nmTipoOrganizApplic;
    }

    @Column(name = "DS_AUTOR")
    public String getDsAutor() {
        return this.dsAutor;
    }

    public void setDsAutor(String dsAutor) {
        this.dsAutor = dsAutor;
    }

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
        return this.idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
    }

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
        return this.idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
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

    @Column(name = "NM_ORGANIZ_APPLIC")
    public String getNmOrganizApplic() {
        return this.nmOrganizApplic;
    }

    public void setNmOrganizApplic(String nmOrganizApplic) {
        this.nmOrganizApplic = nmOrganizApplic;
    }

    @Column(name = "NM_PAGINA_WEB")
    public String getNmPaginaWeb() {
        return this.nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
        this.nmPaginaWeb = nmPaginaWeb;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
        return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
        this.nmRuolo = nmRuolo;
    }

    @Column(name = "NM_TIPO_ORGANIZ_APPLIC")
    public String getNmTipoOrganizApplic() {
        return this.nmTipoOrganizApplic;
    }

    public void setNmTipoOrganizApplic(String nmTipoOrganizApplic) {
        this.nmTipoOrganizApplic = nmTipoOrganizApplic;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "FL_HELP_PRESENTE", columnDefinition = "char(1)")
    public String getFlHelpPresente() {
        return flHelpPresente;
    }

    public void setFlHelpPresente(String flHelpPresente) {
        this.flHelpPresente = flHelpPresente;
    }

    private UsrVAllAutorId usrVAllAutorId;

    @EmbeddedId()
    public UsrVAllAutorId getUsrVAllAutorId() {
        return usrVAllAutorId;
    }

    public void setUsrVAllAutorId(UsrVAllAutorId usrVAllAutorId) {
        this.usrVAllAutorId = usrVAllAutorId;
    }
}
