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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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
import it.eng.saceriam.web.action.VerificaRuoliAction;
import it.eng.saceriam.web.security.SacerIamAuthenticator;
import it.eng.saceriam.web.util.ApplicationBasePropertiesSeviceImpl;
import it.eng.spagoLite.actions.RedirectAction;
import it.eng.spagoLite.actions.security.LoginAction;
import it.eng.spagoLite.actions.security.LogoutAction;

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
                .setCachePeriod(3600); // Cache for 3600 seconds for better performance
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
    public PaginatorImpl paginatorImpl() {
        return new PaginatorImpl();
    }

    /*
     * Template da inserire nelle applicazioni che usano SpagoLite e che utilizzano l' Help On line.
     * Deve implementare l'interfaccia IApplicationBasePropertiesSevice
     *
     */
    @Bean
    public ApplicationBasePropertiesSeviceImpl applicationBasePropertiesSeviceImpl() {
        return new ApplicationBasePropertiesSeviceImpl();
    }

    /*
     * MEV#27457 - Rendere indipendente SIAM da PING Template per chiamate rest con impostazione del
     * timeout
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);
        factory.setConnectTimeout(15000);
        factory.setBufferRequestBody(false); // Added for better performance with large requests
        return new RestTemplate(factory);
    }

    /*
     * Classe che va a caricare le autorizzazioni da IAM
     */
    @Bean(name = "sacerIamAuthenticator")
    public SacerIamAuthenticator sacerIamAuthenticator() {
        return new SacerIamAuthenticator();
    }

    /*
     * Serve per parametrizzare l'applicazione specifica per esempio per caricare le variabili di
     * sistema che hanno come suffisso ad esempio "saceriam".
     */
    @Bean
    public String nomeApplicazione() {
        return "saceriam";
    }

    /*
     * CONFIGURAZIONE DEI BEAN DELLE ACTION che prima erano nell'xml di springweb Configurazione
     * delle action ereditate dal framework
     */
    @Bean(name = "/View.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public RedirectAction redirectAction() {
        return new RedirectAction();
    }

    @Bean(name = "/Login.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public LoginAction loginAction() {
        return new LoginAction();
    }

    @Bean(name = "/Logout.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public LogoutAction logoutAction() {
        return new LogoutAction();
    }

    /* Configurazione delle action specifiche del modulo web */
    @Bean(name = "/Home.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public HomeAction homeAction() {
        return new HomeAction();
    }

    @Bean(name = "/ModificaPsw.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public ModificaPswAction modificaPswAction() {
        return new ModificaPswAction();
    }

    @Bean(name = "/SceltaOrganizzazione.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public SceltaOrganizzazioneAction sceltaOrganizzazioneAction() {
        return new SceltaOrganizzazioneAction();
    }

    @Bean(name = "/AssociazioneUtente.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AssociazioneUtenteAction associazioneUtenteAction() {
        return new AssociazioneUtenteAction();
    }

    @Bean(name = "/AllineaComponenti.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AllineaComponentiAction allineaComponentiAction() {
        return new AllineaComponentiAction();
    }

    @Bean(name = "/GestioneNoteRilascio.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneNoteRilascioAction gestioneNoteRilascioAction() {
        return new GestioneNoteRilascioAction();
    }

    @Bean(name = "/GestioneNews.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneNewsAction gestioneNewsAction() {
        return new GestioneNewsAction();
    }

    @Bean(name = "/GestioneJob.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneJobAction gestioneJobAction() {
        return new GestioneJobAction();
    }

    @Bean(name = "/GestioneHelpOnLine.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneHelpOnLineAction gestioneHelpOnLineAction() {
        return new GestioneHelpOnLineAction();
    }

    @Bean(name = "/DettaglioHelpOnLine.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneHelpOnLineAction gestioneHelpOnLineActionDettaglio() {
        return new GestioneHelpOnLineAction();
    }

    @Bean(name = "/AmministrazioneUtenti.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneUtentiAction amministrazioneUtentiAction() {
        return new AmministrazioneUtentiAction();
    }

    @Bean(name = "/AmministrazioneRuoli.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneRuoliAction amministrazioneRuoliAction() {
        return new AmministrazioneRuoliAction();
    }

    @Bean(name = "/VerificaRuoli.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public VerificaRuoliAction verificaRuoliAction() {
        return new VerificaRuoliAction();
    }

    @Bean(name = "/AmministrazioneSistemiVersanti.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneSistemiVersantiAction amministrazioneSistemiVersantiAction() {
        return new AmministrazioneSistemiVersantiAction();
    }

    @Bean(name = "/AmministrazioneEntiConvenzionati.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneEntiConvenzionatiAction amministrazioneEntiConvenzionatiAction() {
        return new AmministrazioneEntiConvenzionatiAction();
    }

    @Bean(name = "/AmministrazioneEntiNonConvenzionati.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    AmministrazioneEntiNonConvenzionatiAction amministrazioneEntiNonConvenzionatiAction() {
        return new AmministrazioneEntiNonConvenzionatiAction();
    }

    @Bean(name = "/AmministrazioneTipiAccordo.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneTipiAccordoAction amministrazioneTipiAccordoAction() {
        return new AmministrazioneTipiAccordoAction();
    }

    @Bean(name = "/AmministrazioneTariffari.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneTariffariAction amministrazioneTariffariAction() {
        return new AmministrazioneTariffariAction();
    }

    @Bean(name = "/AmministrazioneClasseEnte.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneClasseEnteAction amministrazioneClasseEnteAction() {
        return new AmministrazioneClasseEnteAction();
    }

    @Bean(name = "/AmministrazioneTipiServizio.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public AmministrazioneTipiServizioAction amministrazioneTipiServizioAction() {
        return new AmministrazioneTipiServizioAction();
    }

    @Bean(name = "/GestioneAmbitoTerritoriale.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneAmbitoTerritorialeAction gestioneAmbitoTerritorialeAction() {
        return new GestioneAmbitoTerritorialeAction();
    }

    @Bean(name = "/GestioneFatturazioneServizi.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneFatturazioneServiziAction gestioneFatturazioneServiziAction() {
        return new GestioneFatturazioneServiziAction();
    }

    @Bean(name = "/GestioneAccessiFalliti.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public GestioneAccessiFallitiAction gestioneAccessiFallitiAction() {
        return new GestioneAccessiFallitiAction();
    }

    @Bean(name = "/NoteRilascio.html")
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    public NoteRilascioAction noteRilascioAction() {
        return new NoteRilascioAction();
    }

}
