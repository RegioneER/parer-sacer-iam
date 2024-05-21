/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IRispostaWS;

/**
 *
 * @author Gilioli_P
 */
public class RispostaWSModificaOrganizzazione implements IRispostaWS {

    private IRispostaWS.SeverityEnum severity = IRispostaWS.SeverityEnum.OK;
    private IRispostaWS.ErrorTypeEnum errorType = IRispostaWS.ErrorTypeEnum.NOERROR;
    private String errorMessage;
    private String errorCode;
    private ModificaOrganizzazioneRisposta modificaOrganizzazioneRisposta;

    @Override
    public IRispostaWS.SeverityEnum getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(IRispostaWS.SeverityEnum severity) {
        this.severity = severity;
    }

    @Override
    public IRispostaWS.ErrorTypeEnum getErrorType() {
        return errorType;
    }

    @Override
    public void setErrorType(IRispostaWS.ErrorTypeEnum errorType) {
        this.errorType = errorType;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ModificaOrganizzazioneRisposta getModificaOrganizzazioneRisposta() {
        return modificaOrganizzazioneRisposta;
    }

    public void setModificaOrganizzazioneRisposta(ModificaOrganizzazioneRisposta modificaOrganizzazioneRisposta) {
        this.modificaOrganizzazioneRisposta = modificaOrganizzazioneRisposta;
    }
}
