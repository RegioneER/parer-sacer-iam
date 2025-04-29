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

package it.eng.saceriam.ws.allineamentoEnteConvenzionato.dto;

import java.io.Serializable;

/**
 *
 * @author Gilioli_P
 */
public class RispostaWSAllineamentoEnteConvenzionato implements Serializable {

    public enum EsitoEnum {
	OK, WARNING, ERROR
    }

    private EsitoEnum esito = EsitoEnum.OK;
    private String errorMessage;
    private String errorCode;

    public EsitoEnum getEsito() {
	return esito;
    }

    public void setEsito(EsitoEnum esito) {
	this.esito = esito;
    }

    public String getErrorMessage() {
	return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

}
