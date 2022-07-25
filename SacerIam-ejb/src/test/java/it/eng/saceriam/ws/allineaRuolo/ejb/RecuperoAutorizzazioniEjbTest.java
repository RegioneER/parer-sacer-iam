package it.eng.saceriam.ws.allineaRuolo.ejb;

import it.eng.ArquillianUtils;
import it.eng.parer.idpjaas.logutils.IdpLogger;
import it.eng.parer.idpjaas.logutils.LogDto;
import it.eng.saceriam.ws.ejb.RecuperoAutorizzazioniEjb;
import javax.ejb.EJB;
import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RecuperoAutorizzazioniEjbTest extends TestCase {

    @EJB
    private RecuperoAutorizzazioniEjb ejb;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive iamArchive = ArquillianUtils.getSacerIamArchive(RecuperoAutorizzazioniEjb.class)
                .addPackages(true, "org.apache.commons.lang3", "it.eng.saceriam", "it.eng.parer.jboss.timers",
                        "it.eng.integriam.client.ws.reputente")
                .addClasses(RecuperoAutorizzazioniEjbTest.class, LogDto.class, IdpLogger.EsitiLog.class);
        return ArquillianUtils.createEnterpriseArchive(RecuperoAutorizzazioniEjbTest.class.getSimpleName(), iamArchive,
                ArquillianUtils.createPaginatorJavaArchive(), ArquillianUtils.createJbossTimerArchive(),
                ArquillianUtils.createSacerLogJavaArchive());
    }

    @Test
    public void retrievePagineAzioniSoloIdOrganiz_queryIsOk() {
        Integer idUserIam = 1;
        String nmApplic = "SACER";
        Integer idOrganiz = 1;
        assertNotNull(ejb.recuperoAutorizzazioniPerId(idUserIam, nmApplic, idOrganiz));
    }

    @Test
    public void retrievePagineAzioniNoOrganiz_queryIsOk() {
        String nmUserIAM = "IMTEST";
        String nmApplic = "SACER";
        Integer idOrganiz = null;
        assertNotNull(ejb.recuperoAutorizzazioniPerNome(nmUserIAM, nmApplic, idOrganiz));
    }

    @Test
    public void retrievePagineAzioniWSRecuperoAutorizzazioniPerNome_queryIsOk() {
        String nmUserIAM = "admin_generale";
        String nmApplic = "SACER";
        Integer idOrganiz = 2;
        assertNotNull(ejb.recuperoAutorizzazioniPerNome(nmUserIAM, nmApplic, idOrganiz));
    }

    @Test
    public void retrievePagineAzioniWSRecuperoAutorizzazioniPerId_queryIsOk() {
        Integer idUserIam = 5000;
        String nmApplic = "SACER";
        Integer idOrganiz = 2;
        assertNotNull(ejb.recuperoAutorizzazioniPerId(idUserIam, nmApplic, idOrganiz));
    }

}