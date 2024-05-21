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

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORG_ENTE database table.
 *
 */
@Entity
@Cacheable(true)
@Table(schema = "SACER", name = "ORG_ENTE")
public class OrgEnte implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEnte;
    private String dsEnte;
    private String nmEnte;
    private String cdEnteNormaliz;
    private String tipoDefTemplateEnte;
    private Date dtFinValAppartAmbiente;
    private Date dtIniValAppartAmbiente;
    private Date dtFineVal;
    private Date dtIniVal;
    private String flCessato;

    public OrgEnte() {
    }

    @Id
    @Column(name = "ID_ENTE")
    public Long getIdEnte() {
        return this.idEnte;
    }

    public void setIdEnte(Long idEnte) {
        this.idEnte = idEnte;
    }

    @Column(name = "DS_ENTE")
    public String getDsEnte() {
        return this.dsEnte;
    }

    public void setDsEnte(String dsEnte) {
        this.dsEnte = dsEnte;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
        return this.nmEnte;
    }

    public void setNmEnte(String nmEnte) {
        this.nmEnte = nmEnte;
    }

    @Column(name = "CD_ENTE_NORMALIZ")
    public String getCdEnteNormaliz() {
        return this.cdEnteNormaliz;
    }

    public void setCdEnteNormaliz(String cdEnteNormaliz) {
        this.cdEnteNormaliz = cdEnteNormaliz;
    }

    @Column(name = "TIPO_DEF_TEMPLATE_ENTE")
    public String getTipoDefTemplateEnte() {
        return this.tipoDefTemplateEnte;
    }

    public void setTipoDefTemplateEnte(String tipoDefTemplateEnte) {
        this.tipoDefTemplateEnte = tipoDefTemplateEnte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL_APPART_AMBIENTE")
    public Date getDtFinValAppartAmbiente() {
        return this.dtFinValAppartAmbiente;
    }

    public void setDtFinValAppartAmbiente(Date dtFinValAppartAmbiente) {
        this.dtFinValAppartAmbiente = dtFinValAppartAmbiente;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL_APPART_AMBIENTE")
    public Date getDtIniValAppartAmbiente() {
        return this.dtIniValAppartAmbiente;
    }

    public void setDtIniValAppartAmbiente(Date dtIniValAppartAmbiente) {
        this.dtIniValAppartAmbiente = dtIniValAppartAmbiente;
    }

    // // bi-directional many-to-one association to OrgStoricoEnteAmbiente
    // @OneToMany(mappedBy = "orgEnte")
    // public List<OrgStoricoEnteAmbiente> getOrgStoricoEnteAmbientes() {
    // return this.orgStoricoEnteAmbientes;
    // }
    //
    // public void setOrgStoricoEnteAmbientes(List<OrgStoricoEnteAmbiente> orgStoricoEnteAmbientes) {
    // this.orgStoricoEnteAmbientes = orgStoricoEnteAmbientes;
    // }
    //
    // public OrgStoricoEnteAmbiente addOrgStoricoEnteAmbiente(OrgStoricoEnteAmbiente orgStoricoEnteAmbiente) {
    // getOrgStoricoEnteAmbientes().add(orgStoricoEnteAmbiente);
    // orgStoricoEnteAmbiente.setOrgEnte(this);
    //
    // return orgStoricoEnteAmbiente;
    // }
    //
    // public OrgStoricoEnteAmbiente removeOrgStoricoEnteAmbiente(OrgStoricoEnteAmbiente orgStoricoEnteAmbiente) {
    // getOrgStoricoEnteAmbientes().remove(orgStoricoEnteAmbiente);
    // orgStoricoEnteAmbiente.setOrgEnte(null);
    //
    // return orgStoricoEnteAmbiente;
    // }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "FL_CESSATO", columnDefinition = "char")
    public String getFlCessato() {
        return this.flCessato;
    }

    public void setFlCessato(String flCessato) {
        this.flCessato = flCessato;
    }

    @PrePersist
    void preInsert() {
        if (this.flCessato == null) {
            this.flCessato = "0";
        }
    }
}
