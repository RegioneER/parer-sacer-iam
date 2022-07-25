package it.eng.saceriam.job.dto;

/**
 *
 * @author Agati_D
 */
public class EsitoConnessione extends RichiestaSacer {

    public enum Esito {

        OK, KO
    }

    private boolean erroreConnessione;
    private String descrErr;
    private String xmlResponse;
    private String descrErrConnessione;
    private String codiceEsito;
    private String codiceErrore;
    private String messaggioErrore;
    private Object response;

    public EsitoConnessione(TipoRichiesta tipoRichiesta) {
        super(tipoRichiesta);
    }

    public boolean isErroreConnessione() {
        return erroreConnessione;
    }

    public void setErroreConnessione(boolean erroreConnessione) {
        this.erroreConnessione = erroreConnessione;
    }

    public String getDescrErr() {
        return descrErr;
    }

    public void setDescrErr(String descrErr) {
        this.descrErr = descrErr;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public void setXmlResponse(String xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    public String getDescrErrConnessione() {
        return descrErrConnessione;
    }

    public void setDescrErrConnessione(String descrErrConnessione) {
        this.descrErrConnessione = descrErrConnessione;
    }

    public String getCodiceEsito() {
        return codiceEsito;
    }

    public void setCodiceEsito(String codiceEsito) {
        this.codiceEsito = codiceEsito;
    }

    public String getCodiceErrore() {
        return codiceErrore;
    }

    public void setCodiceErrore(String codiceErrore) {
        this.codiceErrore = codiceErrore;
    }

    public String getMessaggioErrore() {
        return messaggioErrore;
    }

    public void setMessaggioErrore(String messaggioErrore) {
        this.messaggioErrore = messaggioErrore;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
