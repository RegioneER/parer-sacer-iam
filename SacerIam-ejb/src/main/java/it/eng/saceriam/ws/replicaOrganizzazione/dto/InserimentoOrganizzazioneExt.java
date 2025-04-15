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

package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IWSDesc;

/**
 *
 * @author Gilioli_P
 */
public class InserimentoOrganizzazioneExt {

    private IWSDesc descrizione;
    private Long idApplic;
    private InserimentoOrganizzazioneInput inserimentoOrganizzazioneInput;

    public IWSDesc getDescrizione() {
	return descrizione;
    }

    public void setDescrizione(IWSDesc descrizione) {
	this.descrizione = descrizione;
    }

    public Long getIdApplic() {
	return idApplic;
    }

    public void setIdApplic(Long idApplic) {
	this.idApplic = idApplic;
    }

    public InserimentoOrganizzazioneInput getInserimentoOrganizzazioneInput() {
	return inserimentoOrganizzazioneInput;
    }

    public void setInserimentoOrganizzazioneInput(
	    InserimentoOrganizzazioneInput inserimentoOrganizzazioneInput) {
	this.inserimentoOrganizzazioneInput = inserimentoOrganizzazioneInput;
    }
}
