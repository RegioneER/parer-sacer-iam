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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_NEWS_APPLIC database table.
 */
@Entity
@Table(name = "APL_NEWS_APPLIC")
public class AplNewsApplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idNewsApplic;

    private AplApplic aplApplic;

    private AplNews aplNew;

    public AplNewsApplic() {
    }

    @Id
    @Column(name = "ID_NEWS_APPLIC")
    @GenericGenerator(name = "SAPL_NEWS_APPLIC_ID_NEWS_APPLIC_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_NEWS_APPLIC"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_NEWS_APPLIC_ID_NEWS_APPLIC_GENERATOR")
    public Long getIdNewsApplic() {
	return this.idNewsApplic;
    }

    public void setIdNewsApplic(Long idNewsApplic) {
	this.idNewsApplic = idNewsApplic;
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

    // bi-directional many-to-one association to AplNews
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ID_NEWS", nullable = false)
    public AplNews getAplNew() {
	return this.aplNew;
    }

    public void setAplNew(AplNews aplNew) {
	this.aplNew = aplNew;
    }
}
