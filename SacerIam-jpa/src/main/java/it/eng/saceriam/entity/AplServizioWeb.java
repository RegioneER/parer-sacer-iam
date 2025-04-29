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

import javax.persistence.Cacheable;
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
 * The persistent class for the APL_SERVIZIO_WEB database table.
 */
@Entity
@Cacheable(true)
@Table(name = "APL_SERVIZIO_WEB")
public class AplServizioWeb implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idServizioWeb;

    private String dsServizioWeb;

    private String nmServizioWeb;

    private AplApplic aplApplic;

    private List<PrfAutor> prfAutors = new ArrayList<>();

    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();

    public AplServizioWeb() {
    }

    @Id
    @Column(name = "ID_SERVIZIO_WEB")
    @GenericGenerator(name = "SAPL_SERVIZIO_WEB_ID_SERVIZIO_WEB_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_SERVIZIO_WEB"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_SERVIZIO_WEB_ID_SERVIZIO_WEB_GENERATOR")
    public Long getIdServizioWeb() {
	return this.idServizioWeb;
    }

    public void setIdServizioWeb(Long idServizioWeb) {
	this.idServizioWeb = idServizioWeb;
    }

    @Column(name = "DS_SERVIZIO_WEB")
    public String getDsServizioWeb() {
	return this.dsServizioWeb;
    }

    public void setDsServizioWeb(String dsServizioWeb) {
	this.dsServizioWeb = dsServizioWeb;
    }

    @Column(name = "NM_SERVIZIO_WEB")
    public String getNmServizioWeb() {
	return this.nmServizioWeb;
    }

    public void setNmServizioWeb(String nmServizioWeb) {
	this.nmServizioWeb = nmServizioWeb;
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

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "aplServizioWeb")
    public List<PrfAutor> getPrfAutors() {
	return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
	this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplServizioWeb")
    public List<PrfDichAutor> getPrfDichAutors() {
	return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
	this.prfDichAutors = prfDichAutors;
    }
}
