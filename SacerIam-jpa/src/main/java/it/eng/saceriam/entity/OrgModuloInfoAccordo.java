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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_MODULO_INFO_ACCORDO database table.
 */
@Entity
@Table(name = "ORG_MODULO_INFO_ACCORDO")
public class OrgModuloInfoAccordo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idModuloInfoAccordo;

    private BigDecimal aaModuloInfo;

    private byte[] blModuloInfo;

    private String cdKeyModuloInfo;

    private String cdModuloInfo;

    private String cdRegistroModuloInfo;

    private String dsModuloInfo;

    private String nmFileModuloInfo;

    private Date dtRicev;

    private OrgAccordoEnte orgAccordoEnte;

    private OrgEnteSiam orgEnteConvenz;

    private String nmEnte;

    private String nmStrut;

    public OrgModuloInfoAccordo() {
    }

    @Id
    @Column(name = "ID_MODULO_INFO_ACCORDO")
    @GenericGenerator(name = "SORG_MODULO_INFO_ACCORDO_ID_MODULO_INFO_ACCORDO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_MODULO_INFO_ACCORDO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_MODULO_INFO_ACCORDO_ID_MODULO_INFO_ACCORDO_GENERATOR")
    public Long getIdModuloInfoAccordo() {
        return this.idModuloInfoAccordo;
    }

    public void setIdModuloInfoAccordo(Long idModuloInfoAccordo) {
        this.idModuloInfoAccordo = idModuloInfoAccordo;
    }

    @Column(name = "AA_MODULO_INFO")
    public BigDecimal getAaModuloInfo() {
        return this.aaModuloInfo;
    }

    public void setAaModuloInfo(BigDecimal aaModuloInfo) {
        this.aaModuloInfo = aaModuloInfo;
    }

    @Lob
    @Column(name = "BL_MODULO_INFO")
    public byte[] getBlModuloInfo() {
        return this.blModuloInfo;
    }

    public void setBlModuloInfo(byte[] blModuloInfo) {
        this.blModuloInfo = blModuloInfo;
    }

    @Column(name = "CD_KEY_MODULO_INFO")
    public String getCdKeyModuloInfo() {
        return this.cdKeyModuloInfo;
    }

    public void setCdKeyModuloInfo(String cdKeyModuloInfo) {
        this.cdKeyModuloInfo = cdKeyModuloInfo;
    }

    @Column(name = "CD_MODULO_INFO")
    public String getCdModuloInfo() {
        return this.cdModuloInfo;
    }

    public void setCdModuloInfo(String cdModuloInfo) {
        this.cdModuloInfo = cdModuloInfo;
    }

    @Column(name = "CD_REGISTRO_MODULO_INFO")
    public String getCdRegistroModuloInfo() {
        return this.cdRegistroModuloInfo;
    }

    public void setCdRegistroModuloInfo(String cdRegistroModuloInfo) {
        this.cdRegistroModuloInfo = cdRegistroModuloInfo;
    }

    @Column(name = "DS_MODULO_INFO")
    public String getDsModuloInfo() {
        return this.dsModuloInfo;
    }

    public void setDsModuloInfo(String dsModuloInfo) {
        this.dsModuloInfo = dsModuloInfo;
    }

    @Column(name = "NM_FILE_MODULO_INFO")
    public String getNmFileModuloInfo() {
        return this.nmFileModuloInfo;
    }

    public void setNmFileModuloInfo(String nmFileModuloInfo) {
        this.nmFileModuloInfo = nmFileModuloInfo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_RICEV")
    public Date getDtRicev() {
        return this.dtRicev;
    }

    public void setDtRicev(Date dtRicev) {
        this.dtRicev = dtRicev;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCORDO_ENTE")
    public OrgAccordoEnte getOrgAccordoEnte() {
        return this.orgAccordoEnte;
    }

    public void setOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        this.orgAccordoEnte = orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteConvenz() {
        return this.orgEnteConvenz;
    }

    public void setOrgEnteConvenz(OrgEnteSiam orgEnteConvenz) {
        this.orgEnteConvenz = orgEnteConvenz;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
        return nmEnte;
    }

    public void setNmEnte(String nmEnte) {
        this.nmEnte = nmEnte;
    }

    @Column(name = "NM_STRUT")
    public String getNmStrut() {
        return nmStrut;
    }

    public void setNmStrut(String nmStrut) {
        this.nmStrut = nmStrut;
    }
}
