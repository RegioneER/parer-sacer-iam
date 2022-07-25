/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.helper;

import static it.eng.ArquillianUtils.getSacerIamArchive;

import it.eng.ArquillianUtils;

import java.math.BigDecimal;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static it.eng.ArquillianUtils.*;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class GestioneAmbitoTerritorialeHelperTest {

    @EJB
    private GestioneAmbitoTerritorialeHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        return ArquillianUtils.getSacerIamArchive(GestioneAmbitoTerritorialeHelper.class).addPackages(true,
                "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
    }

    @Test
    public void getOrgAmbitoTerritList_queryIsOk() {
        String tipo = aString();

        helper.getOrgAmbitoTerritList(tipo);
        assertTrue(true);
    }

    @Test
    public void getOrgAmbitoTerritByCode_queryIsOk() {
        String cdAmbitoTerritoriale = aString();

        helper.getOrgAmbitoTerritByCode(cdAmbitoTerritoriale);
        assertTrue(true);
    }

    @Test
    public void getOrgAmbitoTerritChildList_BigDecimal_queryIsOk() {
        BigDecimal idAmbitoTerritoriale = aBigDecimal();

        helper.getOrgAmbitoTerritChildList(idAmbitoTerritoriale);
        assertTrue(true);
    }

    @Test
    public void countOrgEnteConvenzByAmbitoTerrit_queryIsOk() {
        BigDecimal idAmbitoTerrit = aBigDecimal();

        helper.countOrgEnteConvenzByAmbitoTerrit(idAmbitoTerrit);
        assertTrue(true);
    }

    @Test
    public void countOrgStoEnteConvenzByAmbitoTerrit_queryIsOk() {
        BigDecimal idAmbitoTerrit = aBigDecimal();

        helper.countOrgStoEnteConvenzByAmbitoTerrit(idAmbitoTerrit);
        assertTrue(true);
    }
}
