/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.helper;

import static org.junit.Assert.*;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static it.eng.ArquillianUtils.*;

import javax.ejb.EJB;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class ComboHelperTest {

    @EJB
    private ComboHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        return getSacerIamArchive(ComboHelper.class).addPackages(true, "it.eng.parer.sacerlog.entity",
                "it.eng.parer.sacerlog.viewEntity");
    }

    @Test
    public void getAplApplicAbilitateList_long_queryIsOk() {
        long idUserIamCorrente = aLong();

        helper.getAplApplicAbilitateList(idUserIamCorrente);
        assertTrue(true);
    }

    @Test
    public void getAplApplicAbilitateRicercaReplicheList_queryIsOk() {
        long idUserIamCorrente = aLong();

        helper.getAplApplicAbilitateRicercaReplicheList(idUserIamCorrente);
        assertTrue(true);
    }

    @Test
    public void getFederationMetadata_queryIsOk() {
        helper.getFederationMetadata();
        assertTrue(true);
    }

    @Test
    public void getAplApplicAbilitateUtenteList_3args_queryIsOk() {
        long idUserIamCorrente = aLong();
        boolean isAppartEnteConvenzAdmin = false;
        boolean isEnteOrganoVigilanza = false;

        helper.getAplApplicAbilitateUtenteList(idUserIamCorrente, isAppartEnteConvenzAdmin, isEnteOrganoVigilanza);
        assertTrue(true);
    }
}
