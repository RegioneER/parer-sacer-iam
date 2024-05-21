/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.helper;

import it.eng.saceriam.common.Constants;

import java.math.BigDecimal;
import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static it.eng.ArquillianUtils.*;
import static org.junit.Assert.*;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class ParamHelperTest {

    @EJB
    private ParamHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = getSacerIamArchive(ParamHelper.class)
                .addClasses(ParamHelperTest.class, it.eng.saceriam.common.Constants.TipoIamVGetValAppart.class,
                        it.eng.saceriam.common.Constants.class, it.eng.saceriam.web.helper.dto.IamVGetValParamDto.class)
                .addPackages(true, "org.apache.commons.lang3")
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void getParamApplicApplicationName_queryIsOk() {

        helper.getParamApplicApplicationName();
        assertTrue(true);
    }

    @Test
    public void getValoreParamApplic_queryIsOk() {
        String nmParamApplic = aString();
        BigDecimal idAmbienteEnteConvenz = aBigDecimal();
        BigDecimal idEnteSiam = aBigDecimal();
        for (Constants.TipoIamVGetValAppart tipoIamVGetValAppart : Constants.TipoIamVGetValAppart.values()) {
            try {
                helper.getValoreParamApplic(nmParamApplic, idAmbienteEnteConvenz, idEnteSiam, tipoIamVGetValAppart);
                assertTrue(true);
            } catch (Exception e) {
                assertExceptionMessage(e, "ParamApplicNotFoundException");
            }
        }
    }
}
