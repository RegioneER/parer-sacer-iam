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

package it.eng;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class ArquillianUtils {

    public static JavaArchive getSacerIamArchive() {
	return getSacerIamArchive(null);
    }

    public static void saveArchiveTo(Archive<WebArchive> testArchive, String path) {
	testArchive.as(ZipExporter.class).exportTo(new File(path), true);
    }

    public static JavaArchive createSacerLogJavaArchive() {
	final JavaArchive paginatorArchive = ShrinkWrap.create(JavaArchive.class, "sacerlog.jar")
		.addPackages(true, "it.eng.parer.sacerlog").addAsResource(
			ArquillianUtils.class.getClassLoader().getResource("ejb-jar-sacerlog.xml"),
			"META-INF/ejb-jar.xml");
	return paginatorArchive;
    }

    public static JavaArchive createPaginatorJavaArchive() {
	final JavaArchive paginatorArchive = ShrinkWrap.create(JavaArchive.class, "paginator.jar")
		.addPackages(true, "it.eng.paginator").addAsResource(
			ArquillianUtils.class.getClassLoader().getResource("ejb-jar-paginator.xml"),
			"META-INF/ejb-jar.xml");
	return paginatorArchive;
    }

    public static EnterpriseArchive createEnterpriseArchive(String archiveName,
	    JavaArchive... modules) {
	EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, archiveName + ".ear")
		.addAsResource(EmptyAsset.INSTANCE, "beans.xml");
	for (JavaArchive m : modules) {
	    ear.addAsModule(m);
	}
	return ear;
    }

    public static JavaArchive createJbossTimerArchive() {
	final JavaArchive timersArchive = ShrinkWrap.create(JavaArchive.class, "timers.jar")
		.addPackages(true, "it.eng.parer.jboss.timer")
		.addPackages(true, "it.eng.parer.jboss.timers").addAsResource(
			ArquillianUtils.class.getClassLoader().getResource("ejb-jar-timers.xml"),
			"META-INF/ejb-jar.xml");
	return timersArchive;
    }

    public static JavaArchive getSacerIamArchive(Class clazz) {
	String warName = clazz == null ? "SacerIamTests.jar" : clazz.getSimpleName() + "Tests.jar";
	JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, warName);
	javaArchive
		.addAsResource(ArquillianUtils.class.getClassLoader().getResource("ejb-jar.xml"),
			"META-INF/ejb-jar.xml")
		.addAsResource(
			ArquillianUtils.class.getClassLoader().getResource("persistence.xml"),
			"META-INF/persistence.xml")
		.addAsResource(
			ArquillianUtils.class.getClassLoader()
				.getResource("org.hibernate.integrator.spi.Integrator"),
			"META-INF/services/org.hibernate.integrator.spi.Integrator")
		.addAsResource("META-INF/xmldbNativeQueries/xmlQueries.xml")
		.addPackage("it.eng.sequences.hibernate");
	javaArchive.addClasses(it.eng.paginator.hibernate.OracleSqlInterceptor.class,
		ArquillianUtils.class, it.eng.saceriam.web.util.Transform.class,
		it.eng.saceriam.helper.GenericHelper.class,
		it.eng.saceriam.helper.HelperInterface.class,
		org.apache.commons.lang3.StringUtils.class, it.eng.spagoCore.error.EMFError.class,
		it.eng.RollbackException.class, it.eng.ExpectedException.class,
		it.eng.saceriam.helper.ParamHelper.class);
	javaArchive.addPackages(true, "it.eng.spagoLite", "it.eng.saceriam.slite.gen",
		"it.eng.saceriam.entity", "it.eng.saceriam.viewEntity",
		"it.eng.saceriam.grantedEntity", "it.eng.paginator", "it.eng.saceriam.exception",
		"it.eng.saceriam.common");
	if (clazz != null) {
	    javaArchive.addClass(clazz);
	}
	return javaArchive;
    }

    public static Date[] aDateArray(int n) {
	List list = new ArrayList();
	Calendar calendar = Calendar.getInstance();
	for (int i = 0; i < n; i++) {
	    Date date = calendar.getTime();
	    list.add(date);
	    calendar.add(Calendar.DATE, 1);
	}
	Date[] array = new Date[list.size()];
	list.toArray(array);
	return array;
    }

    public static Timestamp todayTs() {
	return Timestamp.valueOf(LocalDateTime.now());
    }

    public static Timestamp tomorrowTs() {
	return Timestamp.valueOf(LocalDateTime.now().plusDays(1L));
    }

    public static Set emptySet() {
	return new HashSet(0);
    }

    public static int aInt() {
	return new Random().ints(-100, -1).findFirst().getAsInt();
    }

    public static long aLong() {
	return 0L;
    }

    public static BigDecimal aBigDecimal() {
	return BigDecimal.ZERO;
    }

    public static Set<BigDecimal> aSetOfBigDecimal(int size) {
	Set set = new HashSet(size);
	IntStream.range(0, size).forEach(n -> set.add(aBigDecimal()));
	return set;
    }

    public static Set<Long> aSetOfLong(int size) {
	Set set = new HashSet(size);
	IntStream.range(0, size).forEach(n -> set.add(aLong()));
	return set;
    }

    public static Set<String> aSetOfString(int size) {
	Set set = new HashSet(size);
	IntStream.range(0, size).forEach(n -> set.add(aRandomString()));
	return set;
    }

    public static List<BigDecimal> aListOfBigDecimal(int size) {
	return aSetOfBigDecimal(size).stream().collect(Collectors.toList());
    }

    public static List<Long> aListOfLong(int size) {
	List<Long> list = new ArrayList(size);
	IntStream.range(0, size).forEach(n -> list.add(aLong()));
	return list;
    }

    public static List<Integer> aListOfInt(int size) {
	List<Integer> list = new ArrayList(size);
	IntStream.range(0, size).forEach(n -> list.add(aInt()));
	return list;
    }

    public static List<String> aListOfString(int size) {
	List list = new ArrayList(size);
	IntStream.range(0, size).forEach(n -> list.add(aRandomString()));
	return list;
    }

    public static String aRandomString() {
	final int zero = 48;
	final int zed = 122;
	Random random = new Random();

	return random.ints(zero, zed + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		.limit(10)
		.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		.toString();
    }

    public static String aString() {
	return "TEST_STRING";
    }

    public static void assertNoResultException(Exception e) {
	assertExceptionMessage(e, "No entity found", "NoResultException", "ParerNoResultException",
		"it.eng.parer.exception.errors", "java.lang.NullPointerException",
		"java.lang.IndexOutOfBoundsException", "ParerUserError");
    }

    public static void assertNoAutogeneratedSequence(Exception e) {
	assertExceptionMessage(e, "IdentifierGenerationException");
    }

    public static void assertMergeNullEntity(Exception e) {
	assertExceptionMessage(e, "attempt to create merge event with null entity");
    }

    public static void assertExceptionMessage(Exception e, String... messages) {
	boolean ok = false;
	for (String m : messages) {
	    final String message = e.getMessage() != null ? e.getMessage()
		    : e.getClass().getSimpleName();
	    if (message.contains(m)) {
		ok = true;
		break;
	    }
	}
	assertTrue(ok);
    }

    public static String[] aStringArray(int size) {
	String[] array = new String[size];
	List list = new ArrayList(size);
	IntStream.range(0, size).forEach(n -> list.add(aRandomString()));
	list.toArray(array);
	return array;
    }

    public static String aFlag() {
	return "1";
    }

    public static Boolean aBoolean() {
	return Boolean.TRUE;
    }

    public static void throwExpectedIfMessageIs(Exception e, String errorMessage) {
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));
	if (errors.toString().contains(errorMessage)) {
	    throw new ExpectedException();
	}
    }

}
