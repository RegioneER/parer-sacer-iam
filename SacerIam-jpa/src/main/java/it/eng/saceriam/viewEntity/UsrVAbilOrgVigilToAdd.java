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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the USR_V_ABIL_ORG_VIGIL_TO_ADD database table.
 */
@Entity
@Table(name = "USR_V_ABIL_ORG_VIGIL_TO_ADD")
@NamedQuery(name = "UsrVAbilOrgVigilToAdd.findAll", query = "SELECT u FROM UsrVAbilOrgVigilToAdd u")
public class UsrVAbilOrgVigilToAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idApplic;

    private BigDecimal idEnteProdutVigil;

    private BigDecimal idUsoUserApplicGestito;

    private BigDecimal idVigilEnteProdut;

    private String nmApplic;

    public UsrVAbilOrgVigilToAdd() {
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

    @Column(name = "ID_ENTE_PRODUT_VIGIL")
    public BigDecimal getIdEnteProdutVigil() {
        return this.idEnteProdutVigil;
    }

    public void setIdEnteProdutVigil(BigDecimal idEnteProdutVigil) {
        this.idEnteProdutVigil = idEnteProdutVigil;
    }

    @Column(name = "ID_USO_USER_APPLIC_GESTITO")
    public BigDecimal getIdUsoUserApplicGestito() {
        return this.idUsoUserApplicGestito;
    }

    public void setIdUsoUserApplicGestito(BigDecimal idUsoUserApplicGestito) {
        this.idUsoUserApplicGestito = idUsoUserApplicGestito;
    }

    @Column(name = "ID_VIGIL_ENTE_PRODUT")
    public BigDecimal getIdVigilEnteProdut() {
        return this.idVigilEnteProdut;
    }

    public void setIdVigilEnteProdut(BigDecimal idVigilEnteProdut) {
        this.idVigilEnteProdut = idVigilEnteProdut;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    private UsrVAbilOrgVigilToAddId usrVAbilOrgVigilToAddId;

    @EmbeddedId()
    public UsrVAbilOrgVigilToAddId getUsrVAbilOrgVigilToAddId() {
        return usrVAbilOrgVigilToAddId;
    }

    public void setUsrVAbilOrgVigilToAddId(UsrVAbilOrgVigilToAddId usrVAbilOrgVigilToAddId) {
        this.usrVAbilOrgVigilToAddId = usrVAbilOrgVigilToAddId;
    }
}
