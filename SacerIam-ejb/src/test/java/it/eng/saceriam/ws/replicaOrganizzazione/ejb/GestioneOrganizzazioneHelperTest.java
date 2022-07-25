/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.replicaOrganizzazione.ejb;

import static it.eng.ArquillianUtils.getSacerIamArchive;

import it.eng.ArquillianUtils;
import it.eng.saceriam.exception.TransactionException;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.*;

import java.math.BigDecimal;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import static org.junit.Assert.*;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static it.eng.ArquillianUtils.*;

import javax.ejb.EJB;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class GestioneOrganizzazioneHelperTest {

    @EJB
    private GestioneOrganizzazioneHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = ArquillianUtils.getSacerIamArchive(GestioneOrganizzazioneHelper.class);
        archive.addClasses(it.eng.saceriam.web.helper.UserHelper.class, it.eng.parer.sacerlog.ejb.SacerLogEjb.class,
                it.eng.saceriam.ws.dto.IRispostaWS.class, it.eng.saceriam.web.util.ApplEnum.TiOperReplic.class,
                it.eng.saceriam.entity.AplApplic.class, it.eng.saceriam.entity.AplClasseTipoDato.class,
                it.eng.saceriam.entity.AplTipoOrganiz.class, it.eng.saceriam.entity.OrgEnteConvenzOrg.class,
                it.eng.saceriam.entity.UsrOrganizIam.class, it.eng.saceriam.entity.UsrTipoDatoIam.class,
                it.eng.saceriam.exception.TransactionException.class, it.eng.saceriam.web.util.ApplEnum.class,
                it.eng.saceriam.ws.dto.IRispostaWS.class, it.eng.saceriam.ws.dto.IWSDesc.class,
                it.eng.parer.sacerlog.ejb.helper.SacerLogHelper.class, it.eng.saceriam.ws.ejb.WsIdpLogger.class,
                it.eng.saceriam.helper.ParamHelper.class, it.eng.saceriam.common.Constants.TipoIamVGetValAppart.class,
                it.eng.parer.sacerlog.ejb.common.AppServerInstance.class,
                it.eng.parer.sacerlog.ejb.common.helper.ParamApplicHelper.class,
                it.eng.parer.sacerlog.util.TransactionLogContext.class, it.eng.saceriam.common.Constants.class,
                it.eng.parer.sacerlog.ejb.util.ObjectsToLogBefore.class,
                it.eng.parer.sacerlog.common.Constants.NmParamApplic.class, org.apache.commons.lang3.ClassUtils.class,
                it.eng.saceriam.ws.utils.MessaggiWSBundle.class, GestioneOrganizzazioneHelperTest.class);
        archive.addPackages(false, "it.eng.saceriam.ws.replicaOrganizzazione.dto", "org.apache.commons.lang3.mutable",
                "org.apache.commons.lang3.exception", "it.eng.parer.idpjaas.logutils", "it.eng.saceriam.web.helper.dto")
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void getUsrOrganizIam_queryIsOk() {
        Long idApplic = aLong();
        Integer idOrganizApplic = aInt();
        String nmTipoOrganiz = aString();

        helper.getUsrOrganizIam(idApplic, idOrganizApplic, nmTipoOrganiz);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgEnteConvenzOrg_queryIsOk() {
        long idOrganizIam = aLong();

        helper.retrieveOrgEnteConvenzOrg(idOrganizIam);
        assertTrue(true);
    }

    @Test
    public void isLastLevel_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        String nmTipoOrganiz = aString();
        try {
            helper.isLastLevel(idApplic, nmTipoOrganiz);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }

    @Test
    public void getUsersOnDich_queryIsOk() {
        Long idOrganizIam = aLong();
        for (ApplEnum.TiOperReplic tiOperReplic : ApplEnum.TiOperReplic.values()) {
            helper.getUsersOnDich(idOrganizIam, tiOperReplic);
            assertTrue(true);
        }
    }

    @Test
    public void getUsersWithOrganizDadDich_queryIsOk() {
        Long idOrganizIam = aLong();

        helper.getUsersWithOrganizDadDich(idOrganizIam);
        assertTrue(true);
    }

    @Test
    public void getAplClasseTipoDato_queryIsOk() {
        Long idApplic = aLong();
        String nmClasseTipoDato = aString();
        try {
            helper.getAplClasseTipoDato(idApplic, nmClasseTipoDato);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }

    @Test
    public void getAplTipoOrganiz_queryIsOk() {
        Long idApplic = aLong();
        String nmTipoOrganiz = aString();
        try {
            helper.getAplTipoOrganiz(idApplic, nmTipoOrganiz);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }

    @Test
    public void getAplApplic_queryIsOk() {
        Long idApplic = aLong();

        helper.getAplApplic(idApplic);
        assertTrue(true);
    }

    @Test
    @Ignore("operazione da evitare in automatico")
    public void cancellaOrganizzazione_queryIsOk() throws TransactionException {
        Long idApplic = -99L;
        CancellaOrganizzazioneInput parametriInput = new CancellaOrganizzazioneInput(aString(), aInt(), aString());
        RispostaWSCancellaOrganizzazione rispostaWs = new RispostaWSCancellaOrganizzazione();

        helper.cancellaOrganizzazione(idApplic, parametriInput, rispostaWs);
        assertTrue(true);
    }

    @Test
    public void getUsrTipoDatoIam_queryIsOk() {
        Long idOrganizIam = aLong();
        String nmTipoDato = aString();
        try {
            helper.getUsrTipoDatoIam(idOrganizIam, nmTipoDato);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }

    @Test
    public void getUsersSacer_queryIsOk() {
        Long idOrganizIam = aLong();

        helper.getUsersSacer(idOrganizIam);
        assertTrue(true);
    }
}
