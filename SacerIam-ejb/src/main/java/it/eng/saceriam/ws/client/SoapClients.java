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

package it.eng.saceriam.ws.client;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.integriam.client.ws.IAMSoapClients;
import it.eng.saceriam.ws.client.allineaRuolo.AllineaRuolo;
import it.eng.saceriam.ws.client.allineaRuolo.AllineaRuolo_Service;
import it.eng.spagoLite.security.auth.AuthenticationHandlerConstants;
import it.eng.spagoLite.security.auth.SOAPClientLoginHandlerResolver;

/**
 *
 * @author Bonora_L
 */
public class SoapClients {

    private static final Logger logger = LoggerFactory.getLogger(SoapClients.class);

    private static AllineaRuolo_Service allineaRuoloService;

    private SoapClients() {
	//
    }

    public static AllineaRuolo allineaRuoloClient(String username, String password,
	    String serviceURL, Integer timeout) {
	try {
	    logger.info("Connessione al WS: {}", serviceURL);
	    if (allineaRuoloService == null) {
		synchronized (IAMSoapClients.class) {
		    if (allineaRuoloService == null) {
			URL wsdlURL = new URL(serviceURL + "?wsdl");
			allineaRuoloService = new AllineaRuolo_Service(wsdlURL);
			allineaRuoloService
				.setHandlerResolver(new SOAPClientLoginHandlerResolver());
			logger.info("Creato il client service per il WS: {}", serviceURL);
		    }
		}
	    }
	    AllineaRuolo client = allineaRuoloService.getAllineaRuoloPort();
	    Map<String, Object> requestContext = ((BindingProvider) client).getRequestContext();
	    // Timeout in millis
	    requestContext.put("com.sun.xml.internal.ws.connect.timeout", timeout);
	    // Timeout in millis
	    requestContext.put("com.sun.xml.internal.ws.request.timeout", timeout);
	    // Timeout in millis
	    requestContext.put("com.sun.xml.ws.request.timeout", timeout);
	    // Timeout in millis
	    requestContext.put("com.sun.xml.ws.connect.timeout", timeout);
	    // Timeout in millis
	    requestContext.put("javax.xml.ws.client.connectionTimeout", timeout);
	    // Timeout in millis
	    requestContext.put("javax.xml.ws.client.receiveTimeout", timeout);
	    // Endpoint URL
	    requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
	    requestContext.put(AuthenticationHandlerConstants.USER, username);
	    requestContext.put(AuthenticationHandlerConstants.PWD, password);

	    return client;
	} catch (Exception e) {
	    logger.error("Errore durante l'inizializzazione del bean. WS Sever offline?", e);
	    return null;
	}
    }

}
