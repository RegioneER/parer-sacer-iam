/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.helper;

import it.eng.spagoLite.security.exception.AuthWSException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.util.Date;

import static it.eng.ArquillianUtils.*;
import static org.junit.Assert.assertTrue;

/**
 * @author Manuel) return Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class RecuperoHelpHelperTest {
    @EJB
    private RecuperoHelpHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = getSacerIamArchive(RecuperoHelpHelper.class).addClass(RecuperoHelpHelperTest.class)
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void recuperoHelp_queryIsOk() {
        String nmApplic = aString();
        String tiHelpOnLine = aString();
        String nmPaginaWeb = aString();
        String nmEntryMenu = aString();
        Date dtRiferimento = todayTs();

        helper.recuperoHelp(nmApplic, tiHelpOnLine, nmPaginaWeb, nmEntryMenu, dtRiferimento);
        assertTrue(true);
    }

    @Test
    public void findVistaById_queryIsOk() {
        BigDecimal idHelp = aBigDecimal();

        helper.findVistaById(idHelp);
        assertTrue(true);
    }

    @Test
    public void findById_queryIsOk() {
        BigDecimal idHelp = aBigDecimal();

        helper.findById(idHelp);
        assertTrue(true);
    }

    @Test
    public void getHelpBetweenDate_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        String tiHelpOnLine = aString();
        Date dtRiferimento = todayTs();
        BigDecimal idPaginaWeb = aBigDecimal();
        BigDecimal idEntryMenu = aBigDecimal();
        helper.getHelpBetweenDate(idApplic, tiHelpOnLine, dtRiferimento, idPaginaWeb, idEntryMenu);
        assertTrue(true);
    }

    @Test
    public void appExists_queryIsOk() throws AuthWSException {
        String nomeApplicazione = aString();
        helper.appExists(nomeApplicazione);
        assertTrue(true);
    }

    @Test
    public void modificaHelp_queryIsOk() {
        BigDecimal idHelp = aBigDecimal();
        String fileName = aString();
        Date dataIni = todayTs();
        Date datafine = tomorrowTs();
        String blHelpOnLine = aString();
        byte[] blSorgenteHelpOnLine = new byte[] {};

        helper.modificaHelp(idHelp, fileName, dataIni, datafine, blHelpOnLine, blSorgenteHelpOnLine);
        assertTrue(true);
    }

    @Test
    @Ignore("operazione da evitare in automatico")
    public void cancellaHelp_queryIsOk() {
        BigDecimal idHelp = aBigDecimal();

        helper.cancellaHelp(idHelp);
    }

    @Test
    public void findMostRecentDtFin_queryIsOk() {
        long idApplic = aLong();
        String tiHelpOnLine = aString();
        long idPaginaWeb = aLong();
        long idEntryMenu = aLong();

        helper.findMostRecentDtFin(idApplic, tiHelpOnLine, idPaginaWeb, idEntryMenu);
        assertTrue(true);
    }

    @Test
    public void intersectsExistingHelp_queryIsOk() {
        long idHelpOnLine = aLong();
        long idApplic = aLong();
        String tiHelpOnLine = aString();
        long idPaginaWeb = aLong();
        long idEntryMenu = aLong();
        Date dataInizio = todayTs();
        Date dataFine = tomorrowTs();

        helper.intersectsExistingHelp(idHelpOnLine, idApplic, tiHelpOnLine, idPaginaWeb, idEntryMenu, dataInizio,
                dataFine);
        assertTrue(true);
    }
}
