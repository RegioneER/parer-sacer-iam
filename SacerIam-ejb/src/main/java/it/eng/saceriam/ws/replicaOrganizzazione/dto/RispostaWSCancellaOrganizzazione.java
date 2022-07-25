package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IRispostaWS;

/**
 *
 * @author Gilioli_P
 */
public class RispostaWSCancellaOrganizzazione implements IRispostaWS {

    private IRispostaWS.SeverityEnum severity = IRispostaWS.SeverityEnum.OK;
    private IRispostaWS.ErrorTypeEnum errorType = IRispostaWS.ErrorTypeEnum.NOERROR;
    private String errorMessage;
    private String errorCode;
    private CancellaOrganizzazioneRisposta cancellaOrganizzazioneRisposta;

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

    public CancellaOrganizzazioneRisposta getCancellaOrganizzazioneRisposta() {
        return cancellaOrganizzazioneRisposta;
    }

    public void setCancellaOrganizzazioneRisposta(CancellaOrganizzazioneRisposta cancellaOrganizzazioneRisposta) {
        this.cancellaOrganizzazioneRisposta = cancellaOrganizzazioneRisposta;
    }
}