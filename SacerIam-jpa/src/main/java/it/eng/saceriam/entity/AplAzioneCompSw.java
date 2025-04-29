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
 * The persistent class for the APL_AZIONE_COMP_SW database table.
 */
@Entity
@Table(name = "APL_AZIONE_COMP_SW")
public class AplAzioneCompSw implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAzioneCompSw;

    private String dsAzioneCompSw;

    private String nmAzioneCompSw;

    private AplCompSw aplCompSw;

    private AplTipoEvento aplTipoEvento;

    public AplAzioneCompSw() {
    }

    @Id
    @Column(name = "ID_AZIONE_COMP_SW")
    @GenericGenerator(name = "SAPL_AZIONE_COMP_SW_ID_AZIONE_COMP_SW_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_AZIONE_COMP_SW"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_AZIONE_COMP_SW_ID_AZIONE_COMP_SW_GENERATOR")
    public Long getIdAzioneCompSw() {
	return this.idAzioneCompSw;
    }

    public void setIdAzioneCompSw(Long idAzioneCompSw) {
	this.idAzioneCompSw = idAzioneCompSw;
    }

    @Column(name = "DS_AZIONE_COMP_SW")
    public String getDsAzioneCompSw() {
	return this.dsAzioneCompSw;
    }

    public void setDsAzioneCompSw(String dsAzioneCompSw) {
	this.dsAzioneCompSw = dsAzioneCompSw;
    }

    @Column(name = "NM_AZIONE_COMP_SW")
    public String getNmAzioneCompSw() {
	return this.nmAzioneCompSw;
    }

    public void setNmAzioneCompSw(String nmAzioneCompSw) {
	this.nmAzioneCompSw = nmAzioneCompSw;
    }

    // bi-directional many-to-one association to AplCompSw
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COMP_SW")
    public AplCompSw getAplCompSw() {
	return this.aplCompSw;
    }

    public void setAplCompSw(AplCompSw aplCompSw) {
	this.aplCompSw = aplCompSw;
    }

    // bi-directional many-to-one association to AplTipoEvento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO")
    public AplTipoEvento getAplTipoEvento() {
	return this.aplTipoEvento;
    }

    public void setAplTipoEvento(AplTipoEvento aplTipoEvento) {
	this.aplTipoEvento = aplTipoEvento;
    }
}
