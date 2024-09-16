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
import javax.persistence.Table;

/**
 * The persistent class for the APL_V_LIS_SIST_VERS_PER_AUTOMA database table.
 * 
 */
@Entity
@Table(name = "APL_V_LIS_SIST_VERS_PER_AUTOMA")
public class AplVLisSistVersPerAutoma implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cdVersione;
    private String dsSistemaVersante;
    private BigDecimal idEnteUtente;
    private BigDecimal idSistemaVersante;
    private String nmSistemaVersante;

    public AplVLisSistVersPerAutoma() {
        // document why this constructor is empty
    }

    @Column(name = "CD_VERSIONE")
    public String getCdVersione() {
        return this.cdVersione;
    }

    public void setCdVersione(String cdVersione) {
        this.cdVersione = cdVersione;
    }

    @Column(name = "DS_SISTEMA_VERSANTE")
    public String getDsSistemaVersante() {
        return this.dsSistemaVersante;
    }

    public void setDsSistemaVersante(String dsSistemaVersante) {
        this.dsSistemaVersante = dsSistemaVersante;
    }

    @Column(name = "ID_ENTE_UTENTE")
    public BigDecimal getIdEnteUtente() {
        return this.idEnteUtente;
    }

    public void setIdEnteUtente(BigDecimal idEnteUtente) {
        this.idEnteUtente = idEnteUtente;
    }

    @Id
    @Column(name = "ID_SISTEMA_VERSANTE")
    public BigDecimal getIdSistemaVersante() {
        return this.idSistemaVersante;
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
        this.idSistemaVersante = idSistemaVersante;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
        return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
        this.nmSistemaVersante = nmSistemaVersante;
    }

}
