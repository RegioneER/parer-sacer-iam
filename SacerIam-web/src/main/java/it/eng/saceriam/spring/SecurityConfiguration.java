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

package it.eng.saceriam.spring;

import it.eng.spagoLite.spring.CustomSaml2AuthenticationSuccessHandler;
import it.eng.spagoLite.spring.ParerSecurityConfiguration;
import static it.eng.spagoLite.spring.ParerSecurityConfiguration.nomeApplicazione;
import it.eng.spagoLite.spring.RefreshableRelyingPartyRegistrationRepository;
import java.util.List;
import javax.servlet.Filter;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.RelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Marco Iacolucci
 */
@EnableWebSecurity
public class SecurityConfiguration extends ParerSecurityConfiguration {

    @Autowired
    private CustomSaml2AuthenticationSuccessHandler successHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    public SecurityConfiguration() {
	nomeApplicazione = "saceriam";
    }

    /*
     * Impostazione del filtro di sicurezza Spring ed esposizione del metadata di Saceriam
     */
    @Bean
    SecurityFilterChain app(HttpSecurity http,
	    RefreshableRelyingPartyRegistrationRepository relyingPartyRegistrationRepository)
	    throws Exception {

	// ----- Questo per abilitare l'esposizione del metadata del service provider
	RelyingPartyRegistrationResolver reg = new DefaultRelyingPartyRegistrationResolver(
		relyingPartyRegistrationRepository);

	// Metadata dell'app reperibile in locle al seguente URL standard:
	// http://localhost:8080/saceriam/saml2/service-provider-metadata/saceriam

	Saml2MetadataFilter filter = new Saml2MetadataFilter(reg, new OpenSamlMetadataResolver());

	http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/logout").permitAll()
		.requestMatchers("/Logout.html").permitAll()
		.requestMatchers("/AssociazioneUtente.html").permitAll()
		.requestMatchers("/ModificaPsw.html").permitAll()
		.requestMatchers("/saml/SingleLogout/alias/saceriam").permitAll()
		.requestMatchers("/rest/**").permitAll().requestMatchers("/saml/**").permitAll()
		.requestMatchers("/saml2/**").permitAll().requestMatchers("/*.html").authenticated()
		.requestMatchers("/*.json").authenticated().anyRequest().permitAll())
		.addFilterBefore(filter, HeaderWriterFilter.class)
		// Il CSRF è abilitato di default !!
		.csrf(c -> c.requireCsrfProtectionMatcher((HttpServletRequest request) -> {
		    boolean metodiOk = "POST".equals(request.getMethod())
			    || "PUT".equals(request.getMethod())
			    || "DELETE".equals(request.getMethod());
		    boolean matchHtml = AntPathRequestMatcher.antMatcher("*.html").matches(request);
		    boolean matchJson = AntPathRequestMatcher.antMatcher("*.json").matches(request);
		    return metodiOk && matchHtml && matchJson;
		})).saml2Login(saml2 -> {
		    saml2.successHandler(successHandler);
		    saml2.loginPage("/discovery");
		    saml2.loginProcessingUrl("/saml/SSO/alias/{registrationId}");
		}).logout(logout -> logout.logoutSuccessUrl("/Logout.html?operation=success"))
		.saml2Logout(saml2 -> {
		    saml2.logoutResponse().logoutUrl("/saml/SingleLogout/alias/{registrationId}");
		    saml2.logoutUrl("/logout");
		});

	http.headers().contentSecurityPolicy(System.getProperty(
		"http.sec.header.content-security-policy",
		"'default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' 'report-sample' *; style-src 'self' 'report-sample' 'unsafe-inline' *; img-src 'self' data: *;"));
	http.headers().permissionsPolicy(
		pol -> pol.policy(System.getProperty("http.sec.header.permissions-policy",
			"'cross-origin-isolated=*, vertical-scroll=*'")));

	SecurityFilterChain catena = http.build();
	List<Filter> filtri = catena.getFilters();
	for (javax.servlet.Filter filter1 : filtri) {
	    LOGGER.info("FILTRO configurato->>{}", filter1);
	}

	return catena;
    }

}
