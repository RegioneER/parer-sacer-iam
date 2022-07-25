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