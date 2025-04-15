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
 * The persistent class for the APL_COMP_SW database table.
 */
@Entity
@Table(name = "APL_COMP_SW")
public class AplCompSw implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCompSw;

    private String nmCompSw;

    private List<AplAzioneCompSw> aplAzioneCompSws = new ArrayList<>();

    private AplApplic aplApplic;

    private LogAgente logAgente;

    public AplCompSw() {
    }

    @Id
    @Column(name = "ID_COMP_SW")
    @GenericGenerator(name = "SAPL_COMP_SW_ID_COMP_SW_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_COMP_SW"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_COMP_SW_ID_COMP_SW_GENERATOR")
    public Long getIdCompSw() {
	return this.idCompSw;
    }

    public void setIdCompSw(Long idCompSw) {
	this.idCompSw = idCompSw;
    }

    @Column(name = "NM_COMP_SW")
    public String getNmCompSw() {
	return this.nmCompSw;
    }

    public void setNmCompSw(String nmCompSw) {
	this.nmCompSw = nmCompSw;
    }

    // bi-directional many-to-one association to AplAzioneCompSw
    @OneToMany(mappedBy = "aplCompSw")
    public List<AplAzioneCompSw> getAplAzioneCompSws() {
	return this.aplAzioneCompSws;
    }

    public void setAplAzioneCompSws(List<AplAzioneCompSw> aplAzioneCompSws) {
	this.aplAzioneCompSws = aplAzioneCompSws;
    }

    public AplAzioneCompSw addAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
	getAplAzioneCompSws().add(aplAzioneCompSw);
	aplAzioneCompSw.setAplCompSw(this);
	return aplAzioneCompSw;
    }

    public AplAzioneCompSw removeAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
	getAplAzioneCompSws().remove(aplAzioneCompSw);
	aplAzioneCompSw.setAplCompSw(null);
	return aplAzioneCompSw;
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

    // bi-directional many-to-one association to LogAgente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AGENTE")
    public LogAgente getLogAgente() {
	return this.logAgente;
    }

    public void setLogAgente(LogAgente logAgente) {
	this.logAgente = logAgente;
    }
}
