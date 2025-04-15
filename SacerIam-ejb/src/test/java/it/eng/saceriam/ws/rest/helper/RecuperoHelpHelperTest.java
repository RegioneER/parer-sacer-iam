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

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.helper;

import static it.eng.ArquillianUtils.aBigDecimal;
import static it.eng.ArquillianUtils.aLong;
import static it.eng.ArquillianUtils.aString;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static it.eng.ArquillianUtils.todayTs;
import static it.eng.ArquillianUtils.tomorrowTs;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import it.eng.spagoLite.security.exception.AuthWSException;

/**
 * @author Manuel) return Bertuzzi <manuel.bertuzzi@eng.it>
 */
@ArquillianTest
public class RecuperoHelpHelperTest {
    @EJB
    private RecuperoHelpHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
	final JavaArchive archive = getSacerIamArchive(RecuperoHelpHelper.class)
		.addClass(RecuperoHelpHelperTest.class).addPackages(true,
			"it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
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

	helper.modificaHelp(idHelp, fileName, dataIni, datafine, blHelpOnLine,
		blSorgenteHelpOnLine);
	assertTrue(true);
    }

    @Test
    @Disabled("operazione da evitare in automatico")
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

	helper.intersectsExistingHelp(idHelpOnLine, idApplic, tiHelpOnLine, idPaginaWeb,
		idEntryMenu, dataInizio, dataFine);
	assertTrue(true);
    }
}
