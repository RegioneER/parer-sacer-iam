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

package it.eng.saceriam.exception;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParerUserError extends ParerAbstractError implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String USER_ERROR_ELEMENT = "USER_ERROR";
    public static final String USER_ERROR_CODE = "CODE";
    private static final String[] stringArray = new String[0];
    private String _code = null;
    private static final Logger logger = LoggerFactory.getLogger(ParerUserError.class);

    private ResourceBundle _bundle = ResourceBundle.getBundle("it.eng.saceriam.exception.errors", Locale.ITALIAN);

    public ParerUserError(String severity, String code, List params) {
        super();
        init(severity, code, params);
    }

    public ParerUserError(String message) {
        super();
        init(ParerErrorSeverity.ERROR, message);
    }

    public ParerUserError() {
        super();
        init(ParerErrorSeverity.ERROR, null, null);
    }

    /**
     * Questo metodo ha il compito di inizializzare lo stato dell'oggetto, viene invocato da tutti i costruttori di
     * <code>ParerUserError</code>.
     */
    private void init(String severity, String code, List params) {
        logger.debug("ParerUserError::init: invocato");
        setSeverity(severity);
        logger.debug("ParerUserError::init: severity [" + getSeverity() + "]");
        _code = code;
        logger.debug("ParerUserError::init: code [" + code + "]");
        String text = getText(code, params);
        setDescription(text);
        logger.debug("ParerUserError::init: description [" + getDescription() + "]");
    }

    private void init(String severity, String message) {
        logger.debug("ParerUserError::init: invocato");
        setSeverity(severity);
        logger.debug("ParerUserError::init: severity [" + getSeverity() + "]");
        _code = null;
        logger.debug("ParerUserError::init: code [" + _code + "]");
        setDescription(message);
        logger.debug("ParerUserError::init: description [" + getDescription() + "]");
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
