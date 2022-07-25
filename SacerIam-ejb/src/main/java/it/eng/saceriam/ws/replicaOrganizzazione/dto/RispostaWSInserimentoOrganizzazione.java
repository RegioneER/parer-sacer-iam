package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IRispostaWS;

/**
 *
 * @author Gilioli_P
 */
public class RispostaWSInserimentoOrganizzazione implements IRispostaWS {

    private SeverityEnum severity = SeverityEnum.OK;
    private ErrorTypeEnum errorType = ErrorTypeEnum.NOERROR;
    private String errorMessage;
    private String errorCode;
    private InserimentoOrganizzazioneRisposta inserimentoOrganizzazioneRisposta;

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

    public InserimentoOrganizzazioneRisposta getInserimentoOrganizzazioneRisposta() {
        return inserimentoOrganizzazioneRisposta;
    }

    public void setInserimentoOrganizzazioneRisposta(
            InserimentoOrganizzazioneRisposta inserimentoOrganizzazioneRisposta) {
        this.inserimentoOrganizzazioneRisposta = inserimentoOrganizzazioneRisposta;
    }
}