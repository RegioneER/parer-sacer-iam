/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.helper;

import org.junit.Test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;
import static it.eng.ArquillianUtils.*;
import static org.junit.Assert.assertTrue;
import javax.ejb.EJB;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class GestioneJobHelperTest {

    @EJB
    private GestioneJobHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        return getSacerIamArchive(GestioneJobHelper.class).addPackages(true, "it.eng.parer.sacerlog.entity",
                "it.eng.parer.sacerlog.viewEntity");
    }

    @Test
    public void getLogVVisLastSched_queryIsOk() {
        String nmJob = aString();
        helper.getLogVVisLastSched(nmJob);
        assertTrue(true);
    }

}
