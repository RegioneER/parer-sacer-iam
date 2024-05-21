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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_ABIL_ENTE_SUPPORTO_TO_DEL database table.
 * 
 */
@Entity
@Table(name = "USR_V_ABIL_ENTE_SUPPORTO_TO_DEL")
@NamedQuery(name = "UsrVAbilEnteSupportoToDel.findAll", query = "SELECT u FROM UsrVAbilEnteSupportoToDel u")
public class UsrVAbilEnteSupportoToDel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dsCausaleAbil;
    private String flAbilAutomatica;
    private BigDecimal idAbilEnteSiam;
    private BigDecimal idAccordoVigil;
    private BigDecimal idAppartCollegEnti;
    private BigDecimal idDichAbilEnteConvenz;
    private BigDecimal idEnteSiam;
    private BigDecimal idSuptEstEnteConvenz;
    private BigDecimal idUserIam;

    public UsrVAbilEnteSupportoToDel() {
        // document why this constructor is empty
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
        return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
        this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "FL_ABIL_AUTOMATICA", columnDefinition = "CHAR")
    public String getFlAbilAutomatica() {
        return this.flAbilAutomatica;
    }

    public void setFlAbilAutomatica(String flAbilAutomatica) {
        this.flAbilAutomatica = flAbilAutomatica;
    }

    @Id
    @Column(name = "ID_ABIL_ENTE_SIAM")
    public BigDecimal getIdAbilEnteSiam() {
        return this.idAbilEnteSiam;
    }

    public void setIdAbilEnteSiam(BigDecimal idAbilEnteSiam) {
        this.idAbilEnteSiam = idAbilEnteSiam;
    }

    @Column(name = "ID_ACCORDO_VIGIL")
    public BigDecimal getIdAccordoVigil() {
        return this.idAccordoVigil;
    }

    public void setIdAccordoVigil(BigDecimal idAccordoVigil) {
        this.idAccordoVigil = idAccordoVigil;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_DICH_ABIL_ENTE_CONVENZ")
    public BigDecimal getIdDichAbilEnteConvenz() {
        return this.idDichAbilEnteConvenz;
    }

    public void setIdDichAbilEnteConvenz(BigDecimal idDichAbilEnteConvenz) {
        this.idDichAbilEnteConvenz = idDichAbilEnteConvenz;
    }

    @Column(name = "ID_ENTE_SIAM")
    public BigDecimal getIdEnteSiam() {
        return this.idEnteSiam;
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
        this.idEnteSiam = idEnteSiam;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
    }

}
