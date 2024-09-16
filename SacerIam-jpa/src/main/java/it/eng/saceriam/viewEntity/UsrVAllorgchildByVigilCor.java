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
 * The persistent class for the USR_V_ALLORGCHILD_BY_VIGIL_COR database table.
 */
@Entity
@Table(name = "USR_V_ALLORGCHILD_BY_VIGIL_COR")
public class UsrVAllorgchildByVigilCor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteOrganoVigil;

    private BigDecimal idEnteProdutCorrisp;

    private String nmApplic;

    public UsrVAllorgchildByVigilCor() {
        // document why this constructor is empty
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_ENTE_ORGANO_VIGIL")
    public BigDecimal getIdEnteOrganoVigil() {
        return this.idEnteOrganoVigil;
    }

    public void setIdEnteOrganoVigil(BigDecimal idEnteOrganoVigil) {
        this.idEnteOrganoVigil = idEnteOrganoVigil;
    }

    @Column(name = "ID_ENTE_PRODUT_CORRISP")
    public BigDecimal getIdEnteProdutCorrisp() {
        return this.idEnteProdutCorrisp;
    }

    public void setIdEnteProdutCorrisp(BigDecimal idEnteProdutCorrisp) {
        this.idEnteProdutCorrisp = idEnteProdutCorrisp;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVAllorgchildByVigilCorId usrVAllorgchildByVigilCorId;

    @EmbeddedId()
    public UsrVAllorgchildByVigilCorId getUsrVAllorgchildByVigilCorId() {
        return usrVAllorgchildByVigilCorId;
    }

    public void setUsrVAllorgchildByVigilCorId(UsrVAllorgchildByVigilCorId usrVAllorgchildByVigilCorId) {
        this.usrVAllorgchildByVigilCorId = usrVAllorgchildByVigilCorId;
    }
}
