package it.eng.saceriam.exception;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParerWarningException extends ParerAbstractError implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String USER_WARNING_ELEMENT = "USER_WARNING";
    public static final String USER_ERROR_CODE = "CODE";
    private static final String[] stringArray = new String[0];
    private String _code = null;
    private static final Logger logger = LoggerFactory.getLogger(ParerWarningException.class);

    private ResourceBundle _bundle = ResourceBundle.getBundle("it.eng.saceriam.exception.errors", Locale.ITALIAN);

    public ParerWarningException(String severity, String code, List params) {
        super();
        init(severity, code, params);
    }

    public ParerWarningException(String message) {
        super();
        init(ParerErrorSeverity.WARNING, message);
    }

    public ParerWarningException() {
        super();
        init(ParerErrorSeverity.WARNING, null, null);
    }

    /**
     * Questo metodo ha il compito di inizializzare lo stato dell'oggetto, viene invocato da tutti i costruttori di
     * <code>ParerWarningException</code>.
     */
    private void init(String severity, String code, List params) {
        logger.debug("ParerWarningException::init: invocato");
        setSeverity(severity);
        logger.debug("ParerWarningException::init: severity [" + getSeverity() + "]");
        _code = code;
        logger.debug("ParerWarningException::init: code [" + code + "]");
        String text = getText(code, params);
        setDescription(text);
        logger.debug("ParerWarningException::init: description [" + getDescription() + "]");
    }

    private void init(String severity, String message) {
        logger.debug("ParerWarningException::init: invocato");
        setSeverity(severity);
        logger.debug("ParerWarningException::init: severity [" + getSeverity() + "]");
        _code = null;
        logger.debug("ParerWarningException::init: code [" + _code + "]");
        setDescription(message);
        logger.debug("ParerWarningException::init: description [" + getDescription() + "]");
    }

    private String getText(String code, List params) {
        if (code == null)
            return "";

        String text;
        try {
            text = _bundle.getString(code);
        } catch (MissingResourceException e) {
            text = "?? key " + code + " not found ??";
        }

        if (params != null) {
            Object[] strParams = (Object[]) params.toArray(stringArray);
            MessageFormat mf = new MessageFormat(text);
            text = mf.format(strParams, new StringBuffer(), null).toString();
        }
        return text;
    }

    /**
     * Ritorna il codice dell'errore.
     * 
     * @return <em>String</em> codice dell'errore.
     */
    public String getErrorCode() {
        return _code;
    }

    public String getCategory() {
        return ParerErrorCategory.USER_ERROR;
    }

}
