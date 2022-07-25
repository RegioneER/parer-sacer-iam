package it.eng.saceriam.ws.dto;

//import it.eng.parer.ws.utils.AvanzamentoWs;
import java.io.Serializable;

/**
 *
 * @author Fioravanti_F
 */
public interface IRispostaWS extends Serializable {

    public enum SeverityEnum {

        OK, WARNING, ERROR
    }

    public enum ErrorTypeEnum {

        NOERROR, WS_DATA, WS_SIGNATURE, DB_FATAL
    }

    String getErrorCode();

    String getErrorMessage();

    ErrorTypeEnum getErrorType();

    SeverityEnum getSeverity();

    void setErrorCode(String errorCode);

    void setErrorMessage(String errorMessage);

    void setErrorType(ErrorTypeEnum errorType);

    void setSeverity(SeverityEnum severity);

}
