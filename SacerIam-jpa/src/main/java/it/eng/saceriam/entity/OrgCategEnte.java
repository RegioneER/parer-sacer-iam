/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ORG_CATEG_ENTE database table.
 *
 */
/* ORG_CATEG_ENTE */
@Entity
@Table(name = "ORG_CATEG_ENTE", schema = "SACER")
public class OrgCategEnte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idCategEnte;
    private String cdCategEnte;
    private String dsCategEnte;
    // private List<OrgEnte> orgEntes = new ArrayList<>();

    public OrgCategEnte() {
    }

    @Id
    @Column(name = "ID_CATEG_ENTE")
    public Long getIdCategEnte() {
	return this.idCategEnte;
    }

    public void setIdCategEnte(Long idCategEnte) {
	this.idCategEnte = idCategEnte;
    }

    @Column(name = "CD_CATEG_ENTE")
    public String getCdCategEnte() {
	return this.cdCategEnte;
    }

    public void setCdCategEnte(String cdCategEnte) {
	this.cdCategEnte = cdCategEnte;
    }

    @Column(name = "DS_CATEG_ENTE")
    public String getDsCategEnte() {
	return this.dsCategEnte;
    }

    public void setDsCategEnte(String dsCategEnte) {
	this.dsCategEnte = dsCategEnte;
    }

    // //bi-directional many-to-one association to OrgEnte
    // @OneToMany(mappedBy="orgCategEnte")
    // public List<OrgEnte> getOrgEntes() {
    // return this.orgEntes;
    // }
    //
    // public void setOrgEntes(List<OrgEnte> orgEntes) {
    // this.orgEntes = orgEntes;
    // }

}
