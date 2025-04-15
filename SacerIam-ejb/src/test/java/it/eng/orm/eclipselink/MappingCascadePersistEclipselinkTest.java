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
package it.eng.orm.eclipselink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static it.eng.ArquillianUtils.throwExpectedIfMessageIs;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.eng.ArquillianUtils;
import it.eng.ExpectedException;
import it.eng.orm.CascadeTestEjb;
import it.eng.orm.cascade.persist.LogAgenteForTest;
import it.eng.orm.cascade.persist.UsrUserForTest;
import it.eng.parer.sacerlog.ejb.util.PremisEnums;
import it.eng.spagoLite.security.auth.PwdUtil;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@ArquillianTest
public class MappingCascadePersistEclipselinkTest {

    @EJB
    private CascadeTestEjb ejb;
    private Long idUser;
    private Long idLogAgente;

    @BeforeAll
    void init() {
	idUser = null;
	idLogAgente = null;
    }

    @AfterAll
    void cleanRecords() {
	if (idUser != null)
	    ejb.removeById(UsrUserForTest.class, idUser);
	if (idLogAgente != null)
	    ejb.removeById(LogAgenteForTest.class, idLogAgente);
    }

    /** PARENT **/
    @Test
    void persistParent_failBecauseTryInsertChildFirst() {
	Exception exception = assertThrows(Exception.class, () -> {
	    LogAgenteForTest logAgente = buildLogAgenteForTest();
	    UsrUserForTest user = buildUsrUserForTest();
	    logAgente.getUsers().add(user);
	    ejb.persist(logAgente);
	});
	throwExpectedIfMessageIs(exception, "SQLIntegrityConstraintViolationException");
    }

    @Test
    void mergeInsertParent_failBecauseFirstInsertChild() {
	Exception exception = assertThrows(Exception.class, () -> {
	    LogAgenteForTest logAgente = buildLogAgenteForTest();
	    UsrUserForTest user = buildUsrUserForTest();
	    logAgente.getUsers().add(user);
	    ejb.merge(logAgente);
	});
	throwExpectedIfMessageIs(exception, "SQLIntegrityConstraintViolationException");
    }

    @Test
    void mergeUpdateParent_ignoreChild() {
	LogAgenteForTest logAgente = buildLogAgenteForTest();
	UsrUserForTest user = buildUsrUserForTest();
	user.setLogAgente(logAgente);
	ejb.persist(user);
	idUser = user.getIdUserIam();
	idLogAgente = user.getLogAgente().getIdAgente();

	logAgente = ejb.findById(idLogAgente, LogAgenteForTest.class,
		l -> l.getUsers().stream().forEach(u -> u.getNmNomeUser()));
	final String nmAgente = "MERGED";
	logAgente.setNmAgente(nmAgente);
	final String nmNomeUser = logAgente.getUsers().get(0).getNmNomeUser();
	logAgente.getUsers().get(0).setNmNomeUser("AFTER MERGE");
	ejb.merge(logAgente);
	assertEquals(nmAgente, ejb.findById(idLogAgente, LogAgenteForTest.class).getNmAgente());
	assertEquals(nmNomeUser, ejb.findById(idUser, UsrUserForTest.class).getNmNomeUser());
    }

    @Test
    void removeParent_failReferenceFromChild() {
	Exception exception = assertThrows(Exception.class, () -> {
	    LogAgenteForTest logAgente = buildLogAgenteForTest();
	    UsrUserForTest user = buildUsrUserForTest();
	    user.setLogAgente(logAgente);
	    ejb.persist(user);
	    idUser = user.getIdUserIam();
	    idLogAgente = user.getLogAgente().getIdAgente();
	    ejb.removeById(LogAgenteForTest.class, idLogAgente);
	});
	throwExpectedIfMessageIs(exception, "SQLIntegrityConstraintViolationException");
    }

    /** CHILD **/
    @Test
    void persistChild_persistParent() {
	UsrUserForTest user = buildUsrUserForTest();
	LogAgenteForTest logAgenteForTest = buildLogAgenteForTest();
	user.setLogAgente(logAgenteForTest);
	ejb.persist(user);
	idUser = user.getIdUserIam();
	idLogAgente = user.getLogAgente().getIdAgente();
	assertNotNull(idUser);
	assertNotNull(idLogAgente);
    }

    @Test
    void mergeInsertChild_insertAlsoParent() {
	UsrUserForTest user = buildUsrUserForTest();
	LogAgenteForTest logAgenteForTest = buildLogAgenteForTest();
	user.setLogAgente(logAgenteForTest);
	user = ejb.merge(user);
	idUser = user.getIdUserIam();
	idLogAgente = user.getLogAgente().getIdAgente();
	assertNotNull(idLogAgente);
	assertNotNull(idUser);
    }

    @Test
    void mergeUpdateChild_ignoreParent() {
	LogAgenteForTest logAgente = buildLogAgenteForTest();
	UsrUserForTest user = buildUsrUserForTest();
	user.setLogAgente(logAgente);
	ejb.persist(user);
	idUser = user.getIdUserIam();
	idLogAgente = user.getLogAgente().getIdAgente();

	user = ejb.findById(idUser, UsrUserForTest.class, u -> u.getLogAgente().getNmAgente());
	final String nmNomeUser = "AFTER MERGE";
	user.setNmNomeUser(nmNomeUser);
	final String nmAgente = user.getLogAgente().getNmAgente();
	user.getLogAgente().setNmAgente("AFTER MERGE");
	ejb.merge(user);
	assertEquals(nmNomeUser, ejb.findById(idUser, UsrUserForTest.class).getNmNomeUser());
	assertEquals(nmAgente, ejb.findById(idLogAgente, LogAgenteForTest.class).getNmAgente());
    }

    @Test
    void removeChild_ignoreParent() {
	LogAgenteForTest logAgente = buildLogAgenteForTest();
	System.out.println("NM AGENTE " + logAgente.getNmAgente());

	UsrUserForTest user = buildUsrUserForTest();
	user.setLogAgente(logAgente);
	ejb.persist(user);
	idUser = user.getIdUserIam();
	idLogAgente = user.getLogAgente().getIdAgente();

	ejb.removeById(UsrUserForTest.class, idUser);

	assertNotNull(ejb.findById(idLogAgente, LogAgenteForTest.class));
	assertNull(ejb.findById(idUser, UsrUserForTest.class));
    }

    @Deployment
    public static Archive<?> createTestArchive() {
	String warName = MappingCascadeRemoveEclipselinkTest.class.getSimpleName() + "Tests.war";
	WebArchive webArchive = ShrinkWrap.create(WebArchive.class, warName);
	webArchive.addAsResource("META-INF/xmldbNativeQueries/xmlQueries.xml")
		.addAsResource(ArquillianUtils.class.getClassLoader()
			.getResource("persistence-eclipselink.xml"), "META-INF/persistence.xml")
		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

	webArchive.addClasses(ArquillianUtils.class, it.eng.RollbackException.class,
		ExpectedException.class, UsrUserForTest.class, LogAgenteForTest.class,
		CascadeTestEjb.class, PwdUtil.class);

	webArchive.addPackage("it.eng.saceriam.entity.constraint").addPackages(true,
		"org.apache.commons.codec", "it.eng.parer.sacerlog.ejb.util");

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
