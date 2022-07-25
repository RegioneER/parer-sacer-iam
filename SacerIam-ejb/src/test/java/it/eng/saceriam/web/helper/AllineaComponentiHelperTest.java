/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.helper;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static it.eng.ArquillianUtils.*;
import static org.junit.Assert.assertTrue;

/**
 * @author manuel.bertuzzi@eng.it
 */
@RunWith(Arquillian.class)
public class AllineaComponentiHelperTest {
    @EJB
    private AllineaComponentiHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = getSacerIamArchive(AllineaComponentiHelper.class)
                .addClass(AllineaComponentiHelperTest.class).addPackages(true, "org.springframework.cglib")
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void getAplApplicTableBean_queryIsOk() {
        helper.getAplApplicTableBean();
        assertTrue(true);
    }

    @Test
    public void getAplApplicRowBean_queryIsOk() {
        String nmApplic = aString();
        helper.getAplApplicRowBean(nmApplic);
        assertTrue(true);
    }
}
