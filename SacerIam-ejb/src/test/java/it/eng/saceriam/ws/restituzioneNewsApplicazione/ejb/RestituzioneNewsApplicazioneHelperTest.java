/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.restituzioneNewsApplicazione.ejb;

import it.eng.ArquillianUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static it.eng.ArquillianUtils.aLong;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class RestituzioneNewsApplicazioneHelperTest {
    @EJB
    private RestituzioneNewsApplicazioneHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = ArquillianUtils.getSacerIamArchive(RestituzioneNewsApplicazioneHelper.class)
                .addClasses(it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.ListaNews.class,
                        RestituzioneNewsApplicazioneHelperTest.class)
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void getListaNews_queryIsOk() {
        Long idApplic = aLong();
        helper.getListaNews(idApplic);
        assertTrue(true);
    }
}
