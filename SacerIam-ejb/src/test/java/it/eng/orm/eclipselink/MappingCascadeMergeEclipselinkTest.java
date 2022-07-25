/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.orm.eclipselink;

import it.eng.ArquillianUtils;
import it.eng.ExpectedException;
import it.eng.orm.CascadeTestEjb;
import it.eng.orm.cascade.merge.LogAgenteForTest;
import it.eng.orm.cascade.merge.UsrUserForTest;
import it.eng.parer.sacerlog.ejb.util.PremisEnums;
import it.eng.spagoLite.security.auth.PwdUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class MappingCascadeMergeEclipselinkTest {

    @EJB
    private CascadeTestEjb ejb;
    private Long idUser;
    private Long idLogAgente;

    @Before
    public void init() {
        idUser = null;
        idLogAgente = null;
    }

    @After
    public void cleanRecords() {
        if (idUser != null)
            ejb.removeById(UsrUserForTest.class, idUser);
        if (idLogAgente != null)
            ejb.removeById(LogAgenteForTest.class, idLogAgente);
    }

    /** PARENT **/
    @Test(expected = ExpectedException.class)
    public void persistParent_failBecauseMissingCascadePersist() {
        LogAgenteForTest logAgente = buildLogAgenteForTest();
        UsrUserForTest user = buildUsrUserForTest();
        logAgente.getUsers().add(user);
        try {
            ejb.persist(logAgente);
            idLogAgente = logAgente.getIdAgente();
            idUser = logAgente.getUsers().get(0).getIdUserIam();
            Assert.assertNotNull(idLogAgente);
            Assert.assertNull(idUser);
        } catch (Exception e) {
            ArquillianUtils.throwExpectedIfMessageIs(e, "java.lang.IllegalStateException");
        }
    }

    @Test(expected = ExpectedException.class)
    public void mergeInsertParent_failBecauseTryInsertFirstChild() {
        LogAgenteForTest logAgente = buildLogAgenteForTest();
        UsrUserForTest user = buildUsrUserForTest();
        logAgente.getUsers().add(user);
        try {
            logAgente = ejb.merge(logAgente);
            idLogAgente = logAgente.getIdAgente();
            idUser = logAgente.getUsers().get(0).getIdUserIam();
            Assert.assertNotNull(idLogAgente);
            Assert.assertNotNull(idUser);
        } catch (Exception e) {
            ArquillianUtils.throwExpectedIfMessageIs(e, "SQLIntegrityConstraintViolationException");
        }
    }

    @Test
    public void mergeUpdateParent_updateChild() {

        LogAgenteForTest logAgente = buildLogAgenteForTest();
        ejb.persist(logAgente);
        idLogAgente = logAgente.getIdAgente();

        UsrUserForTest user = buildUsrUserForTest();
        user.setLogAgente(logAgente);
        ejb.persist(user);
        idUser = user.getIdUserIam();

        logAgente = ejb.findById(idLogAgente, LogAgenteForTest.class,
                l -> l.getUsers().stream().forEach(u -> u.getNmNomeUser()));
        final String nmAgente = "MERGED";
        logAgente.setNmAgente(nmAgente);
        final String nmNomeUser = "AFTER MERGE";
        logAgente.getUsers().get(0).setNmNomeUser(nmNomeUser);
        ejb.merge(logAgente);
        assertEquals(nmAgente, ejb.findById(idLogAgente, LogAgenteForTest.class).getNmAgente());
        assertEquals(nmNomeUser, ejb.findById(idUser, UsrUserForTest.class).getNmNomeUser());
    }

    @Test(expected = ExpectedException.class)
    public void removeParent_failReferenceFromChild() {
        LogAgenteForTest logAgente = buildLogAgenteForTest();
        ejb.persist(logAgente);
        idLogAgente = logAgente.getIdAgente();

        UsrUserForTest user = buildUsrUserForTest();
        user.setLogAgente(logAgente);
        ejb.persist(user);
        idUser = user.getIdUserIam();
        try {
            ejb.removeById(LogAgenteForTest.class, idLogAgente);
        } catch (Exception e) {
            ArquillianUtils.throwExpectedIfMessageIs(e, "SQLIntegrityConstraintViolationException");
        }
    }

    /** CHILD **/
    @Test(expected = ExpectedException.class)
    public void persistChild_failReferenceToParent() {
        UsrUserForTest user = buildUsrUserForTest();
        LogAgenteForTest logAgenteForTest = buildLogAgenteForTest();
        user.setLogAgente(logAgenteForTest);
        try {
            ejb.persist(user);
            idUser = user.getIdUserIam();
            idLogAgente = user.getLogAgente().getIdAgente();
        } catch (Exception e) {
            ArquillianUtils.throwExpectedIfMessageIs(e, "java.lang.IllegalStateException");
        }
    }

    @Test
    public void mergeInsertChild_insertParent() {
        UsrUserForTest user = buildUsrUserForTest();
        LogAgenteForTest logAgenteForTest = buildLogAgenteForTest();
        user.setLogAgente(logAgenteForTest);
        user = ejb.merge(user);
        idUser = user.getIdUserIam();
        idLogAgente = user.getLogAgente().getIdAgente();
        assertNotNull(idUser);
        assertNotNull(idLogAgente);
    }

    @Test
    public void mergeUpdateChild_updateParent() {
        LogAgenteForTest logAgente = buildLogAgenteForTest();
        ejb.persist(logAgente);
        idLogAgente = logAgente.getIdAgente();

        UsrUserForTest user = buildUsrUserForTest();
        user.setLogAgente(logAgente);
        ejb.persist(user);
        idUser = user.getIdUserIam();

        user = ejb.findById(idUser, UsrUserForTest.class, u -> u.getLogAgente().getNmAgente());
        final String nmNomeUser = "AFTER MERGE";
        user.setNmNomeUser(nmNomeUser);
        final String nmAgente = "AFTER MERGE";
        user.getLogAgente().setNmAgente(nmAgente);
        ejb.merge(user);
        assertEquals(nmNomeUser, ejb.findById(idUser, UsrUserForTest.class).getNmNomeUser());
        assertEquals(nmAgente, ejb.findById(idLogAgente, LogAgenteForTest.class).getNmAgente());
    }

    @Test
    public void removeChild_ignoreParent() {
        LogAgenteForTest logAgente = buildLogAgenteForTest();
        ejb.persist(logAgente);
        idLogAgente = logAgente.getIdAgente();

        UsrUserForTest user = buildUsrUserForTest();
        user.setLogAgente(logAgente);
        ejb.persist(user);
        idUser = user.getIdUserIam();
        ejb.removeById(UsrUserForTest.class, idUser);

        Assert.assertNotNull(ejb.findById(idLogAgente, LogAgenteForTest.class));
        Assert.assertNull(ejb.findById(idUser, UsrUserForTest.class));
    }

    @Deployment
    public static Archive<?> createTestArchive() {
        String warName = MappingCascadeRemoveEclipselinkTest.class.getSimpleName() + "Tests.war";
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, warName);
        webArchive.addAsResource("META-INF/xmldbNativeQueries/xmlQueries.xml")
                .addAsResource(ArquillianUtils.class.getClassLoader().getResource("persistence-eclipselink.xml"),
                        "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        webArchive.addClasses(ArquillianUtils.class, it.eng.RollbackException.class, ExpectedException.class,
                it.eng.orm.cascade.merge.UsrUserForTest.class, it.eng.orm.cascade.merge.LogAgenteForTest.class,
                CascadeTestEjb.class, PwdUtil.class);

        webArchive.addPackage("it.eng.saceriam.entity.constraint").addPackages(true, "org.apache.commons.codec",
                "it.eng.parer.sacerlog.ejb.util");

        return webArchive;
    }

    private LogAgenteForTest buildLogAgenteForTest() {
        LogAgenteForTest logAgente = new LogAgenteForTest();
        logAgente.setNmAgente("JUNIT AGENTE TEST");
        logAgente.setTipoAgentePremis(PremisEnums.TipoAgente.PERSON);
        // Determina il tipo origine utente
        logAgente.setTipoOrigineAgente(PremisEnums.TipoOrigineAgente.UTENTE.name());
        logAgente.setUsers(new ArrayList<>());
        return logAgente;
    }

    private UsrUserForTest buildUsrUserForTest() {
        UsrUserForTest userEntity = new UsrUserForTest();
        byte[] binarySalt = PwdUtil.generateSalt();
        userEntity.setDtRegPsw(new Date());
        userEntity.setCdSalt(PwdUtil.encodeUFT8Base64String(binarySalt));
        userEntity.setCdPsw(PwdUtil.encodePBKDF2Password(binarySalt, "PWD"));
        userEntity.setNmCognomeUser("JUNIT");
        userEntity.setNmNomeUser("TEST");
        userEntity.setNmUserid("JUNIT-TEST");
        userEntity.setFlAttivo("1");
        userEntity.setCdFisc("JNTTST85E11A944M");
        userEntity.setDsEmail("test@junit.org");
        userEntity.setDsEmailSecondaria(null);
        userEntity.setFlContrIp("0");
        userEntity.setTipoUser("PERSONA_FISICA");
        userEntity.setDtScadPsw(new Date());
        userEntity.setFlRespEnteConvenz("0");
        userEntity.setFlAbilEntiCollegAutom("0");
        userEntity.setFlAbilOrganizAutom("0");
        userEntity.setFlAbilFornitAutom("0");
        userEntity.setFlUserAdmin("0");
        userEntity.setFlUtenteDitta("0");
        userEntity.setIdEnteSiam(432L);
        return userEntity;
    }

}
