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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the ORG_TIPO_ACCORDO database table.
 */
@Entity
@Table(name = "ORG_TIPO_ACCORDO")
@NamedQuery(name = "OrgTipoAccordo.findAll", query = "SELECT o FROM OrgTipoAccordo o")
public class OrgTipoAccordo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoAccordo;

    private String cdAlgoTariffario;

    private String cdTipoAccordo;

    private String dsTipoAccordo;

    private Date dtIstituz;

    private Date dtSoppres;

    private String flPagamento;

    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();

    private List<OrgTariffario> orgTariffarios = new ArrayList<>();

    public OrgTipoAccordo() {
    }

    @Id
    @Column(name = "ID_TIPO_ACCORDO")
    @GenericGenerator(name = "SORG_TIPO_ACCORDO_ID_TIPO_ACCORDO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_TIPO_ACCORDO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_TIPO_ACCORDO_ID_TIPO_ACCORDO_GENERATOR")
    public Long getIdTipoAccordo() {
        return this.idTipoAccordo;
    }

    public void setIdTipoAccordo(Long idTipoAccordo) {
        this.idTipoAccordo = idTipoAccordo;
    }

    @Column(name = "CD_ALGO_TARIFFARIO")
    public String getCdAlgoTariffario() {
        return this.cdAlgoTariffario;
    }

    public void setCdAlgoTariffario(String cdAlgoTariffario) {
        this.cdAlgoTariffario = cdAlgoTariffario;
    }

    @Column(name = "CD_TIPO_ACCORDO")
    public String getCdTipoAccordo() {
        return this.cdTipoAccordo;
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
        this.cdTipoAccordo = cdTipoAccordo;
    }

    @Column(name = "DS_TIPO_ACCORDO")
    public String getDsTipoAccordo() {
        return this.dsTipoAccordo;
    }

    public void setDsTipoAccordo(String dsTipoAccordo) {
        this.dsTipoAccordo = dsTipoAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ISTITUZ")
    public Date getDtIstituz() {
        return this.dtIstituz;
    }

    public void setDtIstituz(Date dtIstituz) {
        this.dtIstituz = dtIstituz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SOPPRES")
    public Date getDtSoppres() {
        return this.dtSoppres;
    }

    public void setDtSoppres(Date dtSoppres) {
        this.dtSoppres = dtSoppres;
    }

    @Column(name = "FL_PAGAMENTO", columnDefinition = "char(1)")
    public String getFlPagamento() {
        return this.flPagamento;
    }

    public void setFlPagamento(String flPagamento) {
        this.flPagamento = flPagamento;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgTipoAccordo")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().add(orgAccordoEnte);
        orgAccordoEnte.setOrgTipoAccordo(this);
        return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        getOrgAccordoEntes().remove(orgAccordoEnte);
        orgAccordoEnte.setOrgTipoAccordo(null);
        return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTariffario
    @OneToMany(mappedBy = "orgTipoAccordo")
    public List<OrgTariffario> getOrgTariffarios() {
        return this.orgTariffarios;
    }

    public void setOrgTariffarios(List<OrgTariffario> orgTariffarios) {
        this.orgTariffarios = orgTariffarios;
    }

    public OrgTariffario addOrgTariffario(OrgTariffario orgTariffario) {
        getOrgTariffarios().add(orgTariffario);
        orgTariffario.setOrgTipoAccordo(this);
        return orgTariffario;
    }

    public OrgTariffario removeOrgTariffario(OrgTariffario orgTariffario) {
        getOrgTariffarios().remove(orgTariffario);
        orgTariffario.setOrgTipoAccordo(null);
        return orgTariffario;
    }
}
