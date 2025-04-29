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

package it.eng.orm.cascade.merge;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the LOG_AGENTE database table.
 *
 */
@Entity
@Table(name = "LOG_AGENTE")
@NamedQuery(name = "LogAgente.findAll", query = "SELECT l FROM LogAgenteForTest l")
public class LogAgenteForTest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAgente;
    private String nmAgente;
    private String tipoAgentePremis;
    private String tipoOrigineAgente;
    private List<UsrUserForTest> users;

    public LogAgenteForTest() {
    }

    @Id
    @SequenceGenerator(name = "LOG_AGENTE_IDAGENTE_GENERATOR", sequenceName = "SLOG_AGENTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_AGENTE_IDAGENTE_GENERATOR")
    @Column(name = "ID_AGENTE")
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

    @OneToMany(mappedBy = "logAgente", cascade = CascadeType.MERGE)
    public List<UsrUserForTest> getUsers() {
	return users;
    }

    public void setUsers(List<UsrUserForTest> users) {
	this.users = users;
    }
}
