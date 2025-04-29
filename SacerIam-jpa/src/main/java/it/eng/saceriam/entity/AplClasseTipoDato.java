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
 * The persistent class for the APL_CLASSE_TIPO_DATO database table.
 */
@Entity
@Table(name = "APL_CLASSE_TIPO_DATO")
public class AplClasseTipoDato implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idClasseTipoDato;

    private String nmClasseTipoDato;

    private AplApplic aplApplic;

    private List<UsrDichAbilDati> usrDichAbilDatis = new ArrayList<>();

    private List<UsrTipoDatoIam> usrTipoDatoIams = new ArrayList<>();

    public AplClasseTipoDato() {
    }

    @Id
    @Column(name = "ID_CLASSE_TIPO_DATO")
    @GenericGenerator(name = "SAPL_CLASSE_TIPO_DATO_ID_CLASSE_TIPO_DATO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_CLASSE_TIPO_DATO"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_CLASSE_TIPO_DATO_ID_CLASSE_TIPO_DATO_GENERATOR")
    public Long getIdClasseTipoDato() {
	return this.idClasseTipoDato;
    }

    public void setIdClasseTipoDato(Long idClasseTipoDato) {
	this.idClasseTipoDato = idClasseTipoDato;
    }

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
	return this.nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
	this.nmClasseTipoDato = nmClasseTipoDato;
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

    // bi-directional many-to-one association to UsrDichAbilDati
    @OneToMany(mappedBy = "aplClasseTipoDato")
    public List<UsrDichAbilDati> getUsrDichAbilDatis() {
	return this.usrDichAbilDatis;
    }

    public void setUsrDichAbilDatis(List<UsrDichAbilDati> usrDichAbilDatis) {
	this.usrDichAbilDatis = usrDichAbilDatis;
    }

    // bi-directional many-to-one association to UsrTipoDatoIam
    @OneToMany(mappedBy = "aplClasseTipoDato")
    public List<UsrTipoDatoIam> getUsrTipoDatoIams() {
	return this.usrTipoDatoIams;
    }

    public void setUsrTipoDatoIams(List<UsrTipoDatoIam> usrTipoDatoIams) {
	this.usrTipoDatoIams = usrTipoDatoIams;
    }
}
