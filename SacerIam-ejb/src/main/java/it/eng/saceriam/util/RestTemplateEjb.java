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

package it.eng.saceriam.util;

import it.eng.saceriam.helper.ParamHelper;
import java.net.URI;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author S257421
 */
@Singleton
@Startup
public class RestTemplateEjb {

    private RestTemplate restTemplate = null;

    private static final Logger log = LoggerFactory.getLogger(RestTemplateEjb.class);
    private static final String VERSATORE_CESSATO = "/rest/verificheSuPing/versatoreCessato.json";
    private static final String EXISTS_VERSAMENTI_PER_UTENTE = "/rest/verificheSuPing/existsVersamentiPerUtente.json";
    private static final String ERROR_PARAMETRO_URL = "Il parametro URL_PING non è stato configurato sul sistema!";
    private static final String ERROR_PARAMETRO_USER = "Il parametro USER_PING non è stato configurato sul sistema!";
    private static final String ERROR_PARAMETRO_PASSWORD = "Il parametro PASWORD non è stato configurato sul sistema!";

    @EJB
    private ParamHelper paramHelper;

    @PostConstruct
    public void init() {
	try {
	    SimpleClientHttpRequestFactory fac = new org.springframework.http.client.SimpleClientHttpRequestFactory();
	    fac.setConnectTimeout(10000);
	    fac.setReadTimeout(10000);
	    restTemplate = new RestTemplate(fac);
	} catch (Exception ex) {
	    log.error("ERRORE nell'inizializzazione del RestTemplateEjb", ex);
	}
    }

    /*
     * Verifica in PING se un versatore è cessato.
     */
    public boolean chiamaVersatoreCessato(Long idOrganizApplic) {
	boolean risposta = false;
	MultiValueMap<String, String> valori = new LinkedMultiValueMap<>();
	valori.add("idOrganizApplic", Long.toString(idOrganizApplic));
	try {
	    log.debug("Invoco il servizio {}" + VERSATORE_CESSATO);
	    URI targetUrl = UriComponentsBuilder
		    .fromHttpUrl(paramHelper.getUrlPing() + VERSATORE_CESSATO).queryParams(valori)
		    .build().toUri();
	    ResponseEntity<Boolean> resp = restTemplate.postForEntity(targetUrl, getRequest(),
		    Boolean.class);
	    if (resp != null) {
		risposta = resp.getBody();
		log.debug("OK");
	    } else {
		log.info("RISPOSTA WS nulla!");
		throw new RestClientException(
			"Il servizio " + VERSATORE_CESSATO + " ha tornato un risultato nullo!");
	    }
	} catch (RestClientException ex) {
	    throw new RestClientException(
		    "Errore durante l'invocazione del WS Rest " + VERSATORE_CESSATO, ex);
	}
	return risposta;
    }

    /*
     * Verifica su PING se esistono versamenti per l'utente passato come parametro
     */
    public boolean chiamaExistsVersamentiPerUtente(Long idUserIam) {
	MultiValueMap<String, String> valori = new LinkedMultiValueMap<>();
	valori.add("idUserIam", Long.toString(idUserIam));
	boolean risposta = false;
	try {
	    log.debug("Invoco il servizio {}" + EXISTS_VERSAMENTI_PER_UTENTE);
	    URI targetUrl = UriComponentsBuilder
		    .fromHttpUrl(paramHelper.getUrlPing() + EXISTS_VERSAMENTI_PER_UTENTE)
		    .queryParams(valori).build().toUri();
	    ResponseEntity<Boolean> resp = restTemplate.postForEntity(targetUrl, getRequest(),
		    Boolean.class);
	    if (resp != null) {
		risposta = resp.getBody();
		log.debug("OK");
	    } else {
		log.info("RISPOSTA WS nulla!");
		throw new RestClientException("Il servizio " + EXISTS_VERSAMENTI_PER_UTENTE
			+ " ha tornato un risultato nullo!");
	    }
	} catch (RestClientException ex) {
	    throw new RestClientException(
		    "Errore durante l'invocazione del WS Rest " + EXISTS_VERSAMENTI_PER_UTENTE, ex);
	}
	return risposta;
    }

    /*
     * Crea la Request con utente e password già impostati
     */
    private HttpEntity<String> getRequest() {
	HttpEntity<String> request = null;
	String parametroUrlPing = paramHelper.getUrlPing();
	if (parametroUrlPing == null || parametroUrlPing.trim().equals("")) {
	    log.warn(ERROR_PARAMETRO_URL);
	    throw new RuntimeException(ERROR_PARAMETRO_URL);
	}
	String nomeUtente = paramHelper.getUserPing();
	if (nomeUtente == null || nomeUtente.trim().equals("")) {
	    log.warn(ERROR_PARAMETRO_USER);
	    throw new RuntimeException(ERROR_PARAMETRO_USER);
	}
	String passwordUtente = paramHelper.getPasswordPing();
	if (passwordUtente == null || passwordUtente.trim().equals("")) {
	    log.warn(ERROR_PARAMETRO_PASSWORD);
	    throw new RuntimeException(ERROR_PARAMETRO_PASSWORD);
	}
	String plainCreds = nomeUtente + ":" + passwordUtente;
	byte[] plainCredsBytes = plainCreds.getBytes();
	byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
	String base64Creds = new String(base64CredsBytes);
	HttpHeaders headers = new HttpHeaders();
	headers.add("Authorization", "Basic " + base64Creds);
	request = new HttpEntity<>(headers);
	return request;
    }
}
