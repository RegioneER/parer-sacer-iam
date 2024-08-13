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

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_SUPT_ESTERNO_ENTE_CONVENZ database table.
 */
@Entity
@Table(name = "ORG_SUPT_ESTERNO_ENTE_CONVENZ")
public class OrgSuptEsternoEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idSuptEstEnteConvenz;

    private Date dtFinVal;

    private Date dtIniVal;

    private OrgEnteSiam orgEnteSiamByIdEnteFornitEst;

    private OrgEnteSiam orgEnteSiamByIdEnteProdut;

    public OrgSuptEsternoEnteConvenz() {
    }

    @Id
    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    @GenericGenerator(name = "SORG_SUPT_ESTERNO_ENTE_CONVENZ_ID_SUPT_EST_ENTE_CONVENZ_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_SUPT_ESTERNO_ENTE_CONVENZ"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_SUPT_ESTERNO_ENTE_CONVENZ_ID_SUPT_EST_ENTE_CONVENZ_GENERATOR")
    public Long getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(Long idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL")
    public Date getDtFinVal() {
        return this.dtFinVal;
    }

    public void setDtFinVal(Date dtFinVal) {
        this.dtFinVal = dtFinVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_FORNIT_EST")
    public OrgEnteSiam getOrgEnteSiamByIdEnteFornitEst() {
        return this.orgEnteSiamByIdEnteFornitEst;
    }

    public void setOrgEnteSiamByIdEnteFornitEst(OrgEnteSiam orgEnteSiamByIdEnteFornitEst) {
        this.orgEnteSiamByIdEnteFornitEst = orgEnteSiamByIdEnteFornitEst;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_PRODUT")
    public OrgEnteSiam getOrgEnteSiamByIdEnteProdut() {
        return this.orgEnteSiamByIdEnteProdut;
    }

    public void setOrgEnteSiamByIdEnteProdut(OrgEnteSiam orgEnteSiamByIdEnteProdut) {
        this.orgEnteSiamByIdEnteProdut = orgEnteSiamByIdEnteProdut;
    }
}
