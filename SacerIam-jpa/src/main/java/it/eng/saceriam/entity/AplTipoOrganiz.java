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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_TIPO_ORGANIZ database table.
 */
@Entity
@Table(name = "APL_TIPO_ORGANIZ")
public class AplTipoOrganiz implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoOrganiz;

    private String flLastLivello;

    private String nmTipoOrganiz;

    private AplApplic aplApplic;

    private List<UsrOrganizIam> usrOrganizIams = new ArrayList<>();

    public AplTipoOrganiz() {
    }

    @Id
    @Column(name = "ID_TIPO_ORGANIZ")
    @GenericGenerator(name = "SAPL_TIPO_ORGANIZ_ID_TIPO_ORGANIZ_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_TIPO_ORGANIZ"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_TIPO_ORGANIZ_ID_TIPO_ORGANIZ_GENERATOR")
    public Long getIdTipoOrganiz() {
	return this.idTipoOrganiz;
    }

    public void setIdTipoOrganiz(Long idTipoOrganiz) {
	this.idTipoOrganiz = idTipoOrganiz;
    }

    @Column(name = "FL_LAST_LIVELLO", columnDefinition = "char(1)")
    public String getFlLastLivello() {
	return this.flLastLivello;
    }

    public void setFlLastLivello(String flLastLivello) {
	this.flLastLivello = flLastLivello;
    }

    @Column(name = "NM_TIPO_ORGANIZ")
    public String getNmTipoOrganiz() {
	return this.nmTipoOrganiz;
    }

    public void setNmTipoOrganiz(String nmTipoOrganiz) {
	this.nmTipoOrganiz = nmTipoOrganiz;
    }

    // bi-directional many-to-one association to AplApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPLIC")
    public AplApplic getAplApplic() {
	return this.aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
	this.aplApplic = aplApplic;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @OneToMany(mappedBy = "aplTipoOrganiz")
    public List<UsrOrganizIam> getUsrOrganizIams() {
	return this.usrOrganizIams;
    }

    public void setUsrOrganizIams(List<UsrOrganizIam> usrOrganizIams) {
	this.usrOrganizIams = usrOrganizIams;
    }
}
