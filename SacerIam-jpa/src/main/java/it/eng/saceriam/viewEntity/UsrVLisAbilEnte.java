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
 * The persistent class for the USR_V_LIS_ABIL_ENTE database table.
 */
@Entity
@Table(name = "USR_V_LIS_ABIL_ENTE")
public class UsrVLisAbilEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleAbil;

    private String flAbilAutomatica;

    private BigDecimal idAccordoVigil;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idEnteUtente;

    private BigDecimal idSuptEstEnteConvenz;

    private String nmCompositoEnte;

    private String tiScopoAbil;

    public UsrVLisAbilEnte() {
        // document why this constructor is empty
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
        return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
        this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "FL_ABIL_AUTOMATICA", columnDefinition = "char(1)")
    public String getFlAbilAutomatica() {
        return this.flAbilAutomatica;
    }

    public void setFlAbilAutomatica(String flAbilAutomatica) {
        this.flAbilAutomatica = flAbilAutomatica;
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

    @Column(name = "ID_ENTE_UTENTE")
    public BigDecimal getIdEnteUtente() {
        return this.idEnteUtente;
    }

    public void setIdEnteUtente(BigDecimal idEnteUtente) {
        this.idEnteUtente = idEnteUtente;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "NM_COMPOSITO_ENTE")
    public String getNmCompositoEnte() {
        return this.nmCompositoEnte;
    }

    public void setNmCompositoEnte(String nmCompositoEnte) {
        this.nmCompositoEnte = nmCompositoEnte;
    }

    @Column(name = "TI_SCOPO_ABIL")
    public String getTiScopoAbil() {
        return this.tiScopoAbil;
    }

    public void setTiScopoAbil(String tiScopoAbil) {
        this.tiScopoAbil = tiScopoAbil;
    }

    private UsrVLisAbilEnteId usrVLisAbilEnteId;

    @EmbeddedId()
    public UsrVLisAbilEnteId getUsrVLisAbilEnteId() {
        return usrVLisAbilEnteId;
    }

    public void setUsrVLisAbilEnteId(UsrVLisAbilEnteId usrVLisAbilEnteId) {
        this.usrVLisAbilEnteId = usrVLisAbilEnteId;
    }
}
