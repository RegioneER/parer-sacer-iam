package it.eng.saceriam.ws.restituzioneNewsApplicazione.dto;

import it.eng.saceriam.ws.dto.IRispostaWS;

/**
 *
 * @author Gilioli_P
 */
public class RispostaWSRestituzioneNewsApplicazione implements IRispostaWS {

    private SeverityEnum severity = SeverityEnum.OK;
    private ErrorTypeEnum errorType = ErrorTypeEnum.NOERROR;
    private String errorMessage;
    private String errorCode;
    private RestituzioneNewsApplicazioneRisposta restituzioneNewsApplicazioneRisposta;

    @Override
    public SeverityEnum getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(SeverityEnum severity) {
        this.severity = severity;
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return errorType;
    }

    @Override
    public void setErrorType(ErrorTypeEnum errorType) {
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

    public RestituzioneNewsApplicazioneRisposta getRestituzioneNewsApplicazioneRisposta() {
        return restituzioneNewsApplicazioneRisposta;
    }

    public void setRestituzioneNewsApplicazioneRisposta(
            RestituzioneNewsApplicazioneRisposta restituzioneNewsApplicazioneRisposta) {
        this.restituzioneNewsApplicazioneRisposta = restituzioneNewsApplicazioneRisposta;
    }
}