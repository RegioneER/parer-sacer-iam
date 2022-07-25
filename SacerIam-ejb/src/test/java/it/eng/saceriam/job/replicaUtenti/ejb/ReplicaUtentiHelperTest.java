/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.job.replicaUtenti.ejb;

import static it.eng.ArquillianUtils.*;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class ReplicaUtentiHelperTest {

    @EJB
    private ReplicaUtentiHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = getSacerIamArchive(ReplicaUtentiHelper.class)
                .addClasses(it.eng.saceriam.common.Constants.EsitoServizio.class,
                        it.eng.saceriam.common.Constants.class, ReplicaUtentiHelperTest.class)
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void getAplApplicWithURL_queryIsOk() {
        helper.getAplApplicWithURL();
        assertTrue(true);
    }

    @Test
    public void getUsersDaReplicList_queryIsOk() {
        helper.getUsersDaReplicList();
        assertTrue(true);
    }

    @Test
    public void getFreeAsyncJobs_queryIsOk() {
        String asyncJob = aString();
        String flJobAttivo = aString();
        helper.getFreeAsyncJobs(asyncJob, flJobAttivo);
        assertTrue(true);
    }

    @Test
    public void getLogUserDaReplicList_queryIsOk() {
        Collection<BigDecimal> users = aSetOfBigDecimal(2);
        Long idLogJob = aLong();
        helper.getLogUserDaReplicList(users, idLogJob);
        assertTrue(true);
    }

    @Test
    public void writeAtomicLogUserDaReplic_queryIsOk() {
        Long idLogUserDaReplic = aLong();

        String cdErr = aString();
        String dsErr = aString();
        Set<String> applicErroreSet = aSetOfString(2);
        for (Constants.EsitoServizio esitoServizio : Constants.EsitoServizio.values()) {
            try {
                helper.writeAtomicLogUserDaReplic(idLogUserDaReplic, esitoServizio, cdErr, dsErr, applicErroreSet);
                assertTrue(true);
            } catch (Exception e) {
                assertNoResultException(e);
            }
        }
    }

    @Test
    public void getUsrVAllAutorServiziWeb_queryIsOk() {
        Long idUserIam = aLong();
        Long idApplic = aLong();
        helper.getUsrVAllAutorServiziWeb(idUserIam, idApplic);
        assertTrue(true);
    }

    @Test
    public void getUsrVAbilOrganizList_queryIsOk() {
        Long idUserIam = aLong();
        Long idApplic = aLong();
        helper.getUsrVAbilOrganizList(idUserIam, idApplic);
        assertTrue(true);
    }

    @Test
    public void getUsrVAbilDatiList_queryIsOk() {
        Long idUserIam = aLong();
        Long idApplic = aLong();
        helper.getUsrVAbilDatiList(idUserIam, idApplic);
        assertTrue(true);
    }

    @Test
    public void getUsrIndIpUserList_queryIsOk() {
        Long idUserIam = aLong();
        helper.getUsrIndIpUserList(idUserIam);
        assertTrue(true);
    }

    @Test
    public void getTiScopoDichAbilOrganiz_queryIsOk() {
        BigDecimal idUsoUserApplic = aBigDecimal();
        BigDecimal idOrganizIam = aBigDecimal();
        helper.getTiScopoDichAbilOrganiz(idUsoUserApplic, idOrganizIam);
        assertTrue(true);
    }

    @Test
    public void getIdUsoUserApplic_queryIsOk() {
        Long idUserIam = aLong();
        Long idApplic = aLong();
        try {
            helper.getIdUsoUserApplic(idUserIam, idApplic);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }

    @Test
    public void getUsersDaDisattivareList_queryIsOk() {
        helper.getUsersDaDisattivareList();
        assertTrue(true);
    }

    @Test
    public void getUsersDaCessareList_queryIsOk() {
        int numMesi = aInt();
        helper.getUsersDaCessareList(numMesi);
        assertTrue(true);
    }

    @Test
    public void getUsrUsoUserApplic_queryIsOk() {
        Long idUserIam = aLong();
        helper.getUsrUsoUserApplic(idUserIam);
        assertTrue(true);
    }

    @Test
    public void writeLogUserDaReplic_queryIsOk() {
        UsrUsoUserApplic uso = new UsrUsoUserApplic();
        uso.setIdUsoUserApplic(aLong());
        Long idUserIam = aLong();
        try {
            helper.writeLogUserDaReplic(uso, idUserIam);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }
}
