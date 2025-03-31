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

package it.eng.saceriam.job.util;

//import it.eng.tpi.bean.EliminaCartellaArchiviataRisposta;
//import it.eng.tpi.bean.RegistraCartellaRiArkRisposta;
//import it.eng.tpi.bean.RetrieveFileUnitaDocRisposta;
//import it.eng.tpi.bean.StatoArchiviazioneCartellaRisposta;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import it.eng.parer.ws.ejb.XmlContextCache;
import it.eng.saceriam.job.dto.EsitoConnessione;
import it.eng.saceriam.job.dto.RichiestaSacer.TipoRichiesta;
import it.eng.saceriam.job.dto.RichiestaSacerInput;

public class RichiestaWSSacer {

    private static final Logger log = LoggerFactory.getLogger(RichiestaWSSacer.class);

    // private static XmlContextCache xmlContextCache = null;

    public static EsitoConnessione callWs(RichiestaSacerInput input) {
        return callWs(input.getTipoRichiesta(), input.getUrlRichiesta(), input.getParams(),
                input.getTimeout().intValue());
    }

    public static EsitoConnessione callWs(TipoRichiesta tipoRichiesta, String url, List<NameValuePair> inputParams,
            int timeout) {
        EsitoConnessione esitoConnessione = new EsitoConnessione(tipoRichiesta);

        try {
            boolean useHttps = true;

            // crea una nuova istanza di HttpClient, predisponendo la chiamata del metodo POST

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout)
                    .build();

            try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
                if (useHttps) {
                    // se devo usare HTTPS...
                    // creo un array di TrustManager per considerare tutti i certificati server come validi.
                    // questo andrebbe rimpiazzato con uno che validi il certificato con un certstore...
                    X509TrustManager tm = new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    };

                    try {
                        // Creo il contesto SSL utilizzando i trust manager creati
                        SSLContext ctx = SSLContext.getInstance("TLS");
                        ctx.init(null, new TrustManager[] { tm }, null);

                        // Creo la connessione https
                        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                        ClientConnectionManager ccm = httpclient.getConnectionManager();
                        SchemeRegistry sr = ccm.getSchemeRegistry();
                        sr.register(new Scheme("https", 443, ssf));
                    } catch (NoSuchAlgorithmException | KeyManagementException e) {
                        log.error("Errore interno nella preparazione della chiamata HTTPS " + e.getMessage());
                    }
                }
                // URI uri = new URI(url);
                // UriBuilder builder = UriBuilder.fromUri(uri);
                // for (NameValuePair pair : inputParams) {
                // builder.queryParam(pair.getName(), pair.getValue());
                // }
                log.debug("Chiamata del servizio all'url " + url);
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(inputParams));
                HttpResponse response = null;
                boolean timeoutException = false;
                int statusCode = 0;
                try {
                    response = httpclient.execute(httpPost);
                    statusCode = response.getStatusLine().getStatusCode();
                } catch (Exception ex) {
                    timeoutException = true;
                    log.debug("catch timeoutException " + ex);
                }
                if (statusCode == 404 || timeoutException) {
                    esitoConnessione.setErroreConnessione(true);
                    if (statusCode == 404) {
                        esitoConnessione.setDescrErrConnessione("Errore 404");
                    } else {
                        esitoConnessione.setDescrErrConnessione("Errore timeout");
                    }
                } else {
                    // recupera la risposta
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        String responseString;
                        if (resEntity != null) {
                            responseString = EntityUtils.toString(resEntity);
                            log.debug("Risposta :" + responseString);
                            Unmarshaller um;

                            esitoConnessione.setXmlResponse(responseString);
                            esitoConnessione.setErroreConnessione(false);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            log.error("Impossibile decodificare il messaggio di risposta", ex);
            esitoConnessione.setResponse(null);
            esitoConnessione.setXmlResponse(null);
            esitoConnessione.setErroreConnessione(false);
            esitoConnessione.setDescrErrConnessione(null);
            esitoConnessione.setCodiceEsito(EsitoConnessione.Esito.KO.name());
            esitoConnessione.setCodiceErrore(null);
            esitoConnessione.setMessaggioErrore("Impossibile decodificare il messaggio di risposta");
        }
        return esitoConnessione;
    }

    private static <T> T unmarshallResponse(Unmarshaller um, String responseString, Class<T> classType)
            throws JAXBException {
        JAXBElement<T> jaxbObject = um.unmarshal(new StreamSource(new StringReader(responseString)), classType);
        T obj = jaxbObject.getValue();
        return obj;
    }
}
