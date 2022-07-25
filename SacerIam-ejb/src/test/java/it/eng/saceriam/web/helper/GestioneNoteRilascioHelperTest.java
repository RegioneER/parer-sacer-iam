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
import java.math.BigDecimal;
import java.util.Date;

import static it.eng.ArquillianUtils.*;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class GestioneNoteRilascioHelperTest {

    @EJB
    private GestioneNoteRilascioHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {

        final JavaArchive archive = getSacerIamArchive(GestioneNoteRilascioHelper.class)
                .addClass(GestioneNoteRilascioHelperTest.class).addPackages(true, "org.apache.commons.collections")
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void getAplNoteRilascioList_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        String cdVersione = aString();
        Date dataVersioneDa = null;
        Date dataVersioneA = null;
        String blNota = aString();
        String dsEvidenza = aString();

        helper.getAplNoteRilascioList(idApplic, cdVersione, dataVersioneDa, dataVersioneA, blNota, dsEvidenza);
        assertTrue(true);
    }

    @Test
    public void getAplNotaRilascioById_queryIsOk() {
        BigDecimal idNotaRilascio = aBigDecimal();

        helper.getAplNotaRilascioById(idNotaRilascio);
        assertTrue(true);
    }

    @Test
    public void getAplApplicById_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();

        helper.getAplApplicById(idApplic);
        assertTrue(true);
    }

    @Test
    public void getAplApplic_queryIsOk() {
        long idApplic = aLong();
        helper.getAplApplic(idApplic);
        assertTrue(true);
    }

    @Test
    public void getAplNoteRilascioPrecList_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        BigDecimal idNotaRilascio = aBigDecimal();
        Date dtVersione = todayTs();
        helper.getAplNoteRilascioPrecList(idApplic, idNotaRilascio, dtVersione);
        assertTrue(true);
    }
}
