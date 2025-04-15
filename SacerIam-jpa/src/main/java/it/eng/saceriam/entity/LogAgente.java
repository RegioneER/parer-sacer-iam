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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the LOG_AGENTE database table.
 */
@Entity
@Table(name = "LOG_AGENTE")
@NamedQuery(name = "LogAgente.findAll", query = "SELECT l FROM LogAgente l")
public class LogAgente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAgente;

    private String nmAgente;

    private String tipoAgentePremis;

    private String tipoOrigineAgente;

    public LogAgente() {
    }

    @Id
    @Column(name = "ID_AGENTE")
    @GenericGenerator(name = "SLOG_AGENTE_ID_AGENTE_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SLOG_AGENTE"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SLOG_AGENTE_ID_AGENTE_GENERATOR")
    public Long getIdAgente() {
	return this.idAgente;
    }

    public void setIdAgente(Long idAgente) {
	this.idAgente = idAgente;
    }

    @Column(name = "NM_AGENTE")
    public String getNmAgente() {
	return this.nmAgente;
    }

    public void setNmAgente(String nmAgente) {
	this.nmAgente = nmAgente;
    }

    @Column(name = "TIPO_AGENTE_PREMIS")
    public String getTipoAgentePremis() {
	return this.tipoAgentePremis;
    }

    public void setTipoAgentePremis(String tipoAgentePremis) {
	this.tipoAgentePremis = tipoAgentePremis;
    }

    @Column(name = "TIPO_ORIGINE_AGENTE")
    public String getTipoOrigineAgente() {
	return this.tipoOrigineAgente;
    }

    public void setTipoOrigineAgente(String tipoOrigineAgente) {
	this.tipoOrigineAgente = tipoOrigineAgente;
    }
}
