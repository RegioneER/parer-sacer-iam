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
 * The persistent class for the USR_V_ABIL_DATI database table.
 */
@Entity
@Table(name = "USR_V_ABIL_DATI")
public class UsrVAbilDati implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private String dsOrganiz;

    private String dsTipoDato;

    private BigDecimal idApplic;

    private BigDecimal idClasseTipoDato;

    private BigDecimal idDichAbilDati;

    private BigDecimal idOrganizApplic;

    private BigDecimal idOrganizIam;

    private BigDecimal idOrganizIamPadre;

    private BigDecimal idTipoDatoApplic;

    private BigDecimal idTipoOrganiz;

    private BigDecimal idUsoUserApplic;

    private String nmApplic;

    private String nmClasseTipoDato;

    private String nmOrganiz;

    private String nmTipoDato;

    public UsrVAbilDati() {
    }

    public UsrVAbilDati(BigDecimal idOrganizIam, BigDecimal idTipoDatoApplic, String nmClasseTipoDato) {
        this.idOrganizIam = idOrganizIam;
        this.idTipoDatoApplic = idTipoDatoApplic;
        this.nmClasseTipoDato = nmClasseTipoDato;
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "DS_ORGANIZ")
    public String getDsOrganiz() {
        return this.dsOrganiz;
    }

    public void setDsOrganiz(String dsOrganiz) {
        this.dsOrganiz = dsOrganiz;
    }

    @Column(name = "DS_TIPO_DATO")
    public String getDsTipoDato() {
        return this.dsTipoDato;
    }

    public void setDsTipoDato(String dsTipoDato) {
        this.dsTipoDato = dsTipoDato;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_CLASSE_TIPO_DATO")
    public BigDecimal getIdClasseTipoDato() {
        return this.idClasseTipoDato;
    }

    public void setIdClasseTipoDato(BigDecimal idClasseTipoDato) {
        this.idClasseTipoDato = idClasseTipoDato;
    }

    @Column(name = "ID_DICH_ABIL_DATI")
    public BigDecimal getIdDichAbilDati() {
        return this.idDichAbilDati;
    }

    public void setIdDichAbilDati(BigDecimal idDichAbilDati) {
        this.idDichAbilDati = idDichAbilDati;
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

    @Column(name = "ID_ORGANIZ_IAM_PADRE")
    public BigDecimal getIdOrganizIamPadre() {
        return this.idOrganizIamPadre;
    }

    public void setIdOrganizIamPadre(BigDecimal idOrganizIamPadre) {
        this.idOrganizIamPadre = idOrganizIamPadre;
    }

    @Column(name = "ID_TIPO_DATO_APPLIC")
    public BigDecimal getIdTipoDatoApplic() {
        return this.idTipoDatoApplic;
    }

    public void setIdTipoDatoApplic(BigDecimal idTipoDatoApplic) {
        this.idTipoDatoApplic = idTipoDatoApplic;
    }

    @Column(name = "ID_TIPO_ORGANIZ")
    public BigDecimal getIdTipoOrganiz() {
        return this.idTipoOrganiz;
    }

    public void setIdTipoOrganiz(BigDecimal idTipoOrganiz) {
        this.idTipoOrganiz = idTipoOrganiz;
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

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
        return this.nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
        this.nmClasseTipoDato = nmClasseTipoDato;
    }

    @Column(name = "NM_ORGANIZ")
    public String getNmOrganiz() {
        return this.nmOrganiz;
    }

    public void setNmOrganiz(String nmOrganiz) {
        this.nmOrganiz = nmOrganiz;
    }

    @Column(name = "NM_TIPO_DATO")
    public String getNmTipoDato() {
        return this.nmTipoDato;
    }

    public void setNmTipoDato(String nmTipoDato) {
        this.nmTipoDato = nmTipoDato;
    }

    private UsrVAbilDatiId usrVAbilDatiId;

    @EmbeddedId()
    public UsrVAbilDatiId getUsrVAbilDatiId() {
        return usrVAbilDatiId;
    }

    public void setUsrVAbilDatiId(UsrVAbilDatiId usrVAbilDatiId) {
        this.usrVAbilDatiId = usrVAbilDatiId;
    }
}
