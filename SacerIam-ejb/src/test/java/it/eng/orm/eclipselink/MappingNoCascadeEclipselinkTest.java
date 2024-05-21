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
package it.eng.orm.eclipselink;

import it.eng.ArquillianUtils;
import it.eng.ExpectedException;
import it.eng.orm.cascade.nessuno.LogAgenteForTest;
import it.eng.orm.cascade.nessuno.UsrUserForTest;
import it.eng.orm.CascadeTestEjb;
import it.eng.parer.sacerlog.ejb.util.PremisEnums;
import it.eng.spagoLite.security.auth.PwdUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.Date;

import static it.eng.ArquillianUtils.getSacerIamArchive;
import static it.eng.ArquillianUtils.throwExpectedIfMessageIs;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class MappingNoCascadeEclipselinkTest {

    @EJB
    private CascadeTestEjb ejb;

    @Deployment
    public static Archive<?> createTestArchive() {
        String warName = MappingNoCascadeEclipselinkTest.class.getSimpleName() + "Tests.war";
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, warName);
        webArchive.addAsResource("META-INF/xmldbNativeQueries/xmlQueries.xml")
                .addAsResource(ArquillianUtils.class.getClassLoader().getResource("persistence-eclipselink.xml"),
                        "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        webArchive.addClasses(ArquillianUtils.class, it.eng.RollbackException.class, ExpectedException.class,
                UsrUserForTest.class, LogAgenteForTest.class, CascadeTestEjb.class,
                it.eng.spagoLite.security.auth.PwdUtil.class);

        webArchive.addPackage("it.eng.saceriam.entity.constraint").addPackages(true, "org.apache.commons.codec",
                "it.eng.parer.sacerlog.ejb.util");

        return webArchive;
    }

    @Test(expected = ExpectedException.class)
    public void persistChild_failMissingParent() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            UsrUserForTest user = buildUsrUserForTest();
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            user.setLogAgente(logAgente);
            try {
                ejb.persist(user);
                idUsr = user.getIdUserIam();
                idLogAgente = logAgente.getIdAgente();
            } catch (Exception e) {
                throwExpectedIfMessageIs(e, "java.lang.IllegalStateException");
            }
        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
    }

    @Test
    // COMPORTAMENTO ANOMALO DI ECLIPSELINK, SE NON CI SONO CASCADE ESPLICITATI
    // NON DOVREBBE PERSISTERE GLI OGGETTI COLLEGATI COME LOG_AGENTE
    public void mergeInsertChild_persistParent() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            UsrUserForTest user = buildUsrUserForTest();
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            user.setLogAgente(logAgente);
            UsrUserForTest merged = ejb.merge(user);
            idUsr = merged.getIdUserIam();
            idLogAgente = merged.getLogAgente().getIdAgente();
            Assert.assertNotNull(idUsr);
            Assert.assertNotNull(idLogAgente);
        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
    }

    @Test
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void mergeChild_noUpdatesParent() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            // metto il riferimento a un LogAgente non persistito
            ejb.persist(logAgente);
            idLogAgente = logAgente.getIdAgente();
            UsrUserForTest user = buildUsrUserForTest();
            user.setLogAgente(logAgente);
            ejb.persist(user);
            idUsr = user.getIdUserIam();
            UsrUserForTest reloaded = ejb.findById(idUsr, UsrUserForTest.class, u2 -> u2.getLogAgente().getNmAgente());
            final String nmNomeUser = "MERGED";
            reloaded.setNmNomeUser(nmNomeUser);
            final String nmAgenteBefore = reloaded.getLogAgente().getNmAgente();
            final String nmAgenteAfter = "MERGED AGAIN";
            reloaded.getLogAgente().setNmAgente(nmAgenteAfter);
            ejb.merge(reloaded);
            Assert.assertEquals(nmNomeUser,
                    ejb.findById(idUsr, UsrUserForTest.class, u1 -> u1.getLogAgente().getNmAgente()).getNmNomeUser());
            Assert.assertEquals(nmAgenteBefore,
                    ejb.findById(idUsr, UsrUserForTest.class, u -> u.getLogAgente().getNmAgente()).getLogAgente()
                            .getNmAgente());
        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
    }

    @Test
    public void removeChild_noRemoveParent() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            // metto il riferimento a un LogAgente non persistito
            ejb.persist(logAgente);
            idLogAgente = logAgente.getIdAgente();
            UsrUserForTest user = buildUsrUserForTest();
            user.setLogAgente(logAgente);
            ejb.persist(user);
            idUsr = user.getIdUserIam();
            ejb.removeById(UsrUserForTest.class, idUsr);
            Assert.assertNull(ejb.findById(idUsr, UsrUserForTest.class, u -> u.getLogAgente().getNmAgente()));

            Assert.assertNotNull(ejb.findById(idLogAgente, LogAgenteForTest.class,
                    l -> l.getUsers().stream().forEach(u -> u.getNmNomeUser())));
        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
    }

    @Test(expected = ExpectedException.class)
    public void removeParent_fail() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            // metto il riferimento a un LogAgente non persistito
            ejb.persist(logAgente);
            idLogAgente = logAgente.getIdAgente();
            UsrUserForTest user = buildUsrUserForTest();
            user.setLogAgente(logAgente);
            ejb.persist(user);
            logAgente.setUsers(new ArrayList<>());
            logAgente.getUsers().add(user);
            idUsr = user.getIdUserIam();
            try {
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
            } catch (Exception e) {
                throwExpectedIfMessageIs(e, "SQLIntegrityConstraintViolationException");
            }
        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
    }

    @Test
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void mergeParent_noUpdatesChild() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            // metto il riferimento a un LogAgente non persistito
            ejb.persist(logAgente);
            idLogAgente = logAgente.getIdAgente();
            UsrUserForTest user = buildUsrUserForTest();
            user.setLogAgente(logAgente);
            ejb.persist(user);
            idUsr = user.getIdUserIam();

            LogAgenteForTest reloaded = ejb.findById(idLogAgente, LogAgenteForTest.class,
                    l2 -> l2.getUsers().stream().forEach(u2 -> u2.getNmNomeUser()));
            final String nmAgente = "MERGED AGAIN";
            reloaded.setNmAgente(nmAgente);
            final String nmNomeUserBefore = reloaded.getUsers().get(0).getNmNomeUser();
            final String nmNomeUserAfter = "MERGED";
            reloaded.getUsers().get(0).setNmNomeUser(nmNomeUserAfter);
            ejb.merge(reloaded);

            Assert.assertEquals(nmAgente, ejb.findById(idLogAgente, LogAgenteForTest.class,
                    l1 -> l1.getUsers().stream().forEach(u1 -> u1.getNmNomeUser())).getNmAgente());

            Assert.assertEquals(nmNomeUserBefore,
                    ejb.findById(idLogAgente, LogAgenteForTest.class,
                            l -> l.getUsers().stream().forEach(u -> u.getNmNomeUser())).getUsers().get(0)
                            .getNmNomeUser());
        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
    }

    @Test(expected = ExpectedException.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void mergeInsertParent_tryInsertChildButFail() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            UsrUserForTest user = buildUsrUserForTest();
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            logAgente.getUsers().add(user);
            try {
                LogAgenteForTest merged = ejb.merge(logAgente);
                idUsr = merged.getUsers().get(0).getIdUserIam();
                idLogAgente = merged.getIdAgente();
                Assert.assertNull(idUsr);
                Assert.assertNotNull(idLogAgente);
            } catch (Exception e) {
                // eclipselink fa l'insert della usruser (ERRORE) ma va in errore perché non ha l'id dell log agente
                // (campo obbligatorio)
                throwExpectedIfMessageIs(e, "SQLIntegrityConstraintViolationException");
            }

        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
    }

    @Test(expected = ExpectedException.class)
    public void persistParent_fail() {
        Long idUsr = null;
        Long idLogAgente = null;
        try {
            UsrUserForTest user = buildUsrUserForTest();
            LogAgenteForTest logAgente = buildLogAgenteForTest();
            logAgente.getUsers().add(user);
            try {
                ejb.persist(logAgente);
                idLogAgente = logAgente.getIdAgente();
                Assert.assertNotNull(idLogAgente);
                idUsr = logAgente.getUsers().get(0).getIdUserIam();
                Assert.assertNull(idUsr);
            } catch (Exception e) {
                throwExpectedIfMessageIs(e, "java.lang.IllegalStateException");
            }
        } finally {
            if (idUsr != null)
                ejb.removeById(UsrUserForTest.class, idUsr);
            if (idLogAgente != null)
                ejb.removeById(LogAgenteForTest.class, idLogAgente);
        }
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
