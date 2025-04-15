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

import static it.eng.ArquillianUtils.aString;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;

/**
 * @author manuel.bertuzzi@eng.it
 */
@ArquillianTest
public class AllineaComponentiHelperTest {
    @EJB
    private AllineaComponentiHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
	final JavaArchive archive = getSacerIamArchive(AllineaComponentiHelper.class)
		.addClass(AllineaComponentiHelperTest.class)
		.addPackages(true, "org.springframework.cglib").addPackages(true,
			"it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
	return archive;
    }

    @Test
    void getAplApplicTableBean_queryIsOk() {
	helper.getAplApplicTableBean();
	assertTrue(true);
    }

    @Test
    void getAplApplicRowBean_queryIsOk() {
	String nmApplic = aString();
	helper.getAplApplicRowBean(nmApplic);
	assertTrue(true);
    }
}
