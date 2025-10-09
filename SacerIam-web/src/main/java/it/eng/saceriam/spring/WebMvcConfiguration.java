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

import it.eng.paginator.ejb.PaginatorImpl;
import it.eng.parer.sacerlog.web.spring.SpagoliteWebMvcConfiguration;
import it.eng.saceriam.web.action.AllineaComponentiAction;
import it.eng.saceriam.web.action.AmministrazioneClasseEnteAction;
import it.eng.saceriam.web.action.AmministrazioneEntiConvenzionatiAction;
import it.eng.saceriam.web.action.AmministrazioneEntiNonConvenzionatiAction;
import it.eng.saceriam.web.action.AmministrazioneRuoliAction;
import it.eng.saceriam.web.action.AmministrazioneSistemiVersantiAction;
import it.eng.saceriam.web.action.AmministrazioneTariffariAction;
import it.eng.saceriam.web.action.AmministrazioneTipiAccordoAction;
import it.eng.saceriam.web.action.AmministrazioneTipiServizioAction;
import it.eng.saceriam.web.action.AmministrazioneUtentiAction;
import it.eng.saceriam.web.action.AssociazioneUtenteAction;
import it.eng.saceriam.web.action.GestioneAccessiFallitiAction;
import it.eng.saceriam.web.action.GestioneAmbitoTerritorialeAction;
import it.eng.saceriam.web.action.GestioneFatturazioneServiziAction;
import it.eng.saceriam.web.action.GestioneHelpOnLineAction;
import it.eng.saceriam.web.action.GestioneJobAction;
import it.eng.saceriam.web.action.GestioneNewsAction;
import it.eng.saceriam.web.action.GestioneNoteRilascioAction;
import it.eng.saceriam.web.action.HomeAction;
import it.eng.saceriam.web.action.ModificaPswAction;
import it.eng.saceriam.web.action.NoteRilascioAction;
import it.eng.saceriam.web.action.SceltaOrganizzazioneAction;
import it.eng.saceriam.web.security.SacerIamAuthenticator;
import it.eng.saceriam.web.util.ApplicationBasePropertiesSeviceImpl;
import it.eng.spagoLite.actions.RedirectAction;
import it.eng.spagoLite.actions.security.LoginAction;
import it.eng.spagoLite.actions.security.LogoutAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 *
 * @author Marco Iacolucci
 */
@EnableWebMvc
@ComponentScan(basePackages = {
	"it.eng.saceriam.web.action", "it.eng.saceriam.spring",
	"it.eng.saceriam.web.rest.controller", "it.eng.saceriam.slite.gen.action",
	"it.eng.spagoLite.actions", "it.eng.spagoCore", "it.eng.parer.sacerlog.web.action" })
@Configuration
public class WebMvcConfiguration extends SpagoliteWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	/*
	 * qui si dichiarano le risorse statiche
	 */
	registry.addResourceHandler("/css/**", "/images/**", "/img/**", "/js/**", "/webjars/**")
		.addResourceLocations("/css/", "/images/", "/img/", "/js/", "/webjars/")
		.setCachePeriod(0);
	registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public InternalResourceViewResolver resolver() {
	InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	resolver.setViewClass(JstlView.class);
	resolver.setPrefix("/jsp/");
	resolver.setSuffix(".jsp");
	return resolver;
    }

    @Bean(name = "paginator")
    PaginatorImpl paginatorImpl() {
	return new PaginatorImpl();
    }

    /*
     * Template da inserire nelle applicazioni che usano SpagoLite e che utilizzano l' Help On line.
     * Deve implementare l'interfaccia IApplicationBasePropertiesSevice
     *
     */
    @Bean
    ApplicationBasePropertiesSeviceImpl applicationBasePropertiesSeviceImpl() {
	return new ApplicationBasePropertiesSeviceImpl();
    }

    /*
     * MEV#27457 - Rendere indipendente SIAM da PING Template per chiamate rest con impostazione del
     * timeout
     */
    @Bean
    RestTemplate restTemplate() {
	SimpleClientHttpRequestFactory c = new SimpleClientHttpRequestFactory();
	c.setReadTimeout(10000);
	c.setConnectTimeout(10000);
	return new RestTemplate(c);
    }

    /*
     * Classe che va a caricare le autorizzazioni da IAM
     */
    @Bean(name = "sacerIamAuthenticator")
    SacerIamAuthenticator sacerIamAuthenticator() {
	return new SacerIamAuthenticator();
    }

    /*
     * Serve per parametrizzare l'applicazione specifica per esempio per caricare le variabili di
     * sistema che hanno come suffisso ad esempio "saceriam".
     */
    @Bean
    String nomeApplicazione() {
	return "saceriam";
    }

    /*
     * CONFIGURAZIONE DEI BEAN DELLE ACTION che prima erano nell'xml di springweb Configurazione
     * delle action ereditate dal framework
     */
    @Bean(value = "/View.html")
    @Scope("prototype")
    RedirectAction redirectAction() {
	return new RedirectAction();
    }

    @Bean(value = "/Login.html")
    @Scope("prototype")
    LoginAction loginAction() {
	return new LoginAction();
    }

    @Bean(value = "/Logout.html")
    @Scope("prototype")
    LogoutAction logoutAction() {
	return new LogoutAction();
    }

    /* Configurazione delle action specifiche del modulo web */
    @Bean(value = "/Home.html")
    @Scope("prototype")
    HomeAction homeAction() {
	return new HomeAction();
    }

    @Bean(value = "/ModificaPsw.html")
    @Scope("prototype")
    ModificaPswAction modificaPswAction() {
	return new ModificaPswAction();
    }

    @Bean(value = "/SceltaOrganizzazione.html")
    @Scope("prototype")
    SceltaOrganizzazioneAction sceltaOrganizzazioneAction() {
	return new SceltaOrganizzazioneAction();
    }

    @Bean(value = "/AssociazioneUtente.html")
    @Scope("prototype")
    AssociazioneUtenteAction associazioneUtenteAction() {
	return new AssociazioneUtenteAction();
    }

    @Bean(value = "/AllineaComponenti.html")
    @Scope("prototype")
    AllineaComponentiAction allineaComponentiAction() {
	return new AllineaComponentiAction();
    }

    @Bean(value = "/GestioneNoteRilascio.html")
    @Scope("prototype")
    GestioneNoteRilascioAction gestioneNoteRilascioAction() {
	return new GestioneNoteRilascioAction();
    }

    @Bean(value = "/GestioneNews.html")
    @Scope("prototype")
    GestioneNewsAction gestioneNewsAction() {
	return new GestioneNewsAction();
    }

    @Bean(value = "/GestioneJob.html")
    @Scope("prototype")
    GestioneJobAction gestioneJobAction() {
	return new GestioneJobAction();
    }

    @Bean(value = "/GestioneHelpOnLine.html")
    @Scope("prototype")
    GestioneHelpOnLineAction gestioneHelpOnLineAction() {
	return new GestioneHelpOnLineAction();
    }

    @Bean(value = "/DettaglioHelpOnLine.html")
    @Scope("prototype")
    GestioneHelpOnLineAction gestioneHelpOnLineActionDettaglio() {
	return new GestioneHelpOnLineAction();
    }

    @Bean(value = "/AmministrazioneUtenti.html")
    @Scope("prototype")
    AmministrazioneUtentiAction amministrazioneUtentiAction() {
	return new AmministrazioneUtentiAction();
    }

    @Bean(value = "/AmministrazioneRuoli.html")
    @Scope("prototype")
    AmministrazioneRuoliAction amministrazioneRuoliAction() {
	return new AmministrazioneRuoliAction();
    }

    @Bean(value = "/AmministrazioneSistemiVersanti.html")
    @Scope("prototype")
    AmministrazioneSistemiVersantiAction amministrazioneSistemiVersantiAction() {
	return new AmministrazioneSistemiVersantiAction();
    }

    @Bean(value = "/AmministrazioneEntiConvenzionati.html")
    @Scope("prototype")
    AmministrazioneEntiConvenzionatiAction amministrazioneEntiConvenzionatiAction() {
	return new AmministrazioneEntiConvenzionatiAction();
    }

    @Bean(value = "/AmministrazioneEntiNonConvenzionati.html")
    @Scope("prototype")
    AmministrazioneEntiNonConvenzionatiAction amministrazioneEntiNonConvenzionatiAction() {
	return new AmministrazioneEntiNonConvenzionatiAction();
    }

    @Bean(value = "/AmministrazioneTipiAccordo.html")
    @Scope("prototype")
    AmministrazioneTipiAccordoAction amministrazioneTipiAccordoAction() {
	return new AmministrazioneTipiAccordoAction();
    }

    @Bean(value = "/AmministrazioneTariffari.html")
    @Scope("prototype")
    AmministrazioneTariffariAction amministrazioneTariffariAction() {
	return new AmministrazioneTariffariAction();
    }

    @Bean(value = "/AmministrazioneClasseEnte.html")
    @Scope("prototype")
    AmministrazioneClasseEnteAction amministrazioneClasseEnteAction() {
	return new AmministrazioneClasseEnteAction();
    }

    @Bean(value = "/AmministrazioneTipiServizio.html")
    @Scope("prototype")
    AmministrazioneTipiServizioAction amministrazioneTipiServizioAction() {
	return new AmministrazioneTipiServizioAction();
    }

    @Bean(value = "/GestioneAmbitoTerritoriale.html")
    @Scope("prototype")
    GestioneAmbitoTerritorialeAction gestioneAmbitoTerritorialeAction() {
	return new GestioneAmbitoTerritorialeAction();
    }

    @Bean(value = "/GestioneFatturazioneServizi.html")
    @Scope("prototype")
    GestioneFatturazioneServiziAction gestioneFatturazioneServiziAction() {
	return new GestioneFatturazioneServiziAction();
    }

    @Bean(value = "/GestioneAccessiFalliti.html")
    @Scope("prototype")
    GestioneAccessiFallitiAction gestioneAccessiFallitiAction() {
	return new GestioneAccessiFallitiAction();
    }

    @Bean(value = "/NoteRilascio.html")
    @Scope("prototype")
    NoteRilascioAction noteRilascioAction() {
	return new NoteRilascioAction();
    }

}
