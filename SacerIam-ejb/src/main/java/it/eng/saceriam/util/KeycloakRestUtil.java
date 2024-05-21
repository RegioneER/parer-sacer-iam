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

package it.eng.saceriam.util;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class KeycloakRestUtil {

    private String urlKeycloak = null;
    private String accessToken = null;
    private String refreshToken = null;
    private String tokenType = null;
    private String scope = null;
    private String clientId = null;
    private String clientSecret = null;
    private int expiresIn;
    private int timeout;

    private static final Logger log = LoggerFactory.getLogger(KeycloakRestUtil.class);

    public KeycloakRestUtil(String urlKeycloak, int timeout, String clientId, String clientSecret) {
        this.urlKeycloak = urlKeycloak;
        this.timeout = timeout;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /*
     * Ottiene il token di autenticazione da keycloak per chiamare le api successivamente con lo stesso token
     */
    public boolean getToken() {
        boolean ret = false;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        String urlLocale = urlKeycloak + "/auth/realms/Parer/protocol/openid-connect/token";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, Object> mapForm = new LinkedMultiValueMap<>();
            mapForm.add("grant_type", "client_credentials");
            mapForm.add("client_id", clientId);
            mapForm.add("client_secret", clientSecret);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(mapForm, headers);
            ResponseEntity<Object> response = restTemplate.exchange(urlLocale, HttpMethod.POST, request, Object.class);
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
            if (map != null) {
                accessToken = (String) map.get("access_token");
                tokenType = (String) map.get("token_type");
                refreshToken = (String) map.get("refresh_token");
                expiresIn = (int) map.get("expires_in");
                scope = (String) map.get("scope");
                log.debug("Ottenuto token dall'URL {}", urlLocale);
                ret = true;
            } else {
                log.info("Nessun dato tornato dall'url {}", urlLocale);
            }
        } catch (RestClientException ex) {
            log.error("Errore nel contattare keycloak!", ex);
        } finally {
            //
        }
        return ret;
    }

    /*
     * Invia tramite Keycloak la mail all'utente per l'update della password, in sostanza la mail di attivazione utente
     */
    public boolean sendUserActivation(String idKeycloakUser) {
        boolean ret = false;
        String urlLocal = urlKeycloak + "/auth/admin/realms/Parer/users/" + idKeycloakUser + "/execute-actions-email";
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);
            String parametri = "[\"attivazione-utente-required-action\"]";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlLocal);
            HttpEntity<String> request = new HttpEntity<>(parametri, headers);
            restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, request, String.class);
            log.info("Inviata la mail all'utente per l'update {} della password", idKeycloakUser);
            ret = true;
        } catch (RestClientException ex) {
            log.error("Errore nel contattare keycloak!", ex);
        } finally {
            //
        }
        return ret;

    }

}
