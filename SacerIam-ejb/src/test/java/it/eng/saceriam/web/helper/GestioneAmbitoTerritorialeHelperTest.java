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
package it.eng.saceriam.web.helper;

import static it.eng.ArquillianUtils.aBigDecimal;
import static it.eng.ArquillianUtils.aString;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.jupiter.api.Test;

import it.eng.ArquillianUtils;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@ArquillianTest
public class GestioneAmbitoTerritorialeHelperTest {

    @EJB
    private GestioneAmbitoTerritorialeHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
	return ArquillianUtils.getSacerIamArchive(GestioneAmbitoTerritorialeHelper.class)
		.addPackages(true, "it.eng.parer.sacerlog.entity",
			"it.eng.parer.sacerlog.viewEntity");
    }

    @Test
    void getOrgAmbitoTerritList_queryIsOk() {
	String tipo = aString();

	helper.getOrgAmbitoTerritList(tipo);
	assertTrue(true);
    }

    @Test
    void getOrgAmbitoTerritByCode_queryIsOk() {
	String cdAmbitoTerritoriale = aString();

	helper.getOrgAmbitoTerritByCode(cdAmbitoTerritoriale);
	assertTrue(true);
    }

    @Test
    void getOrgAmbitoTerritChildList_BigDecimal_queryIsOk() {
	BigDecimal idAmbitoTerritoriale = aBigDecimal();

	helper.getOrgAmbitoTerritChildList(idAmbitoTerritoriale);
	assertTrue(true);
    }

    @Test
    void countOrgEnteConvenzByAmbitoTerrit_queryIsOk() {
	BigDecimal idAmbitoTerrit = aBigDecimal();

	helper.countOrgEnteConvenzByAmbitoTerrit(idAmbitoTerrit);
	assertTrue(true);
    }

    @Test
    void countOrgStoEnteConvenzByAmbitoTerrit_queryIsOk() {
	BigDecimal idAmbitoTerrit = aBigDecimal();

	helper.countOrgStoEnteConvenzByAmbitoTerrit(idAmbitoTerrit);
	assertTrue(true);
    }
}
