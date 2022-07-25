package it.eng.saceriam.ws.client;

import it.eng.integriam.client.ws.IAMSoapClients;
import it.eng.saceriam.ws.client.allineaRuolo.AllineaRuolo;
import it.eng.saceriam.ws.client.allineaRuolo.AllineaRuolo_Service;
import it.eng.spagoLite.security.auth.AuthenticationHandlerConstants;
import it.eng.spagoLite.security.auth.SOAPClientLoginHandlerResolver;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bonora_L
 */
public class SoapClients {

    private static final Logger logger = LoggerFactory.getLogger(SoapClients.class);

    private static AllineaRuolo_Service allineaRuoloService;

    public static AllineaRuolo allineaRuoloClient(String username, String password, String serviceURL,
            Integer timeout) {
        try {
            logger.info("Connessione al WS: " + serviceURL);
            if (allineaRuoloService == null) {
                synchronized (IAMSoapClients.class) {
                    if (allineaRuoloService == null) {
                        URL wsdlURL = new URL(serviceURL + "?wsdl");
                        allineaRuoloService = new AllineaRuolo_Service(wsdlURL);
                        allineaRuoloService.setHandlerResolver(new SOAPClientLoginHandlerResolver());
                        logger.info("Creato il client service per il WS: " + serviceURL);
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
