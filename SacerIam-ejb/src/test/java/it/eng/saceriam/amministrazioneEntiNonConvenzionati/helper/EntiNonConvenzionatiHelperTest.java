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
package it.eng.saceriam.amministrazioneEntiNonConvenzionati.helper;

import static it.eng.ArquillianUtils.aBigDecimal;
import static it.eng.ArquillianUtils.aListOfBigDecimal;
import static it.eng.ArquillianUtils.aString;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static it.eng.ArquillianUtils.todayTs;
import static it.eng.ArquillianUtils.tomorrowTs;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@ArquillianTest
public class EntiNonConvenzionatiHelperTest {

    @EJB
    private EntiNonConvenzionatiHelper helper;

    @Deployment
    public static JavaArchive createTestArchive() {
	return getSacerIamArchive(EntiNonConvenzionatiHelper.class)
		.addClass(EntiNonConvenzionatiHelperTest.class).addPackages(true,
			"it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
    }

    @Test
    void retrieveOrgAmbitoTerritQueryIsOk() {
	String tiAmbitoTerrit = aString();
	helper.retrieveOrgAmbitoTerrit(tiAmbitoTerrit);
	assertTrue(true);
    }

    @Test
    void getOrgAmbitoTerritByCodeQueryIsOk() {
	String cdAmbitoTerritoriale = aString();
	helper.getOrgAmbitoTerritByCode(cdAmbitoTerritoriale);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAmbitoTerritChildListQueryIsOk() {
	List<BigDecimal> idAmbitoTerritoriale = aListOfBigDecimal(2);
	helper.retrieveOrgAmbitoTerritChildList(idAmbitoTerritoriale);
	assertTrue(true);
    }

    @Test
    void retrieveIdAmbitoTerritChildListQueryIsOk() {
	BigDecimal idAmbitoTerrit = aBigDecimal();
	helper.retrieveIdAmbitoTerritChildList(idAmbitoTerrit);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAmbitoTerritParentQueryIsOk() {
	BigDecimal idAmbitoTerrit = aBigDecimal();
	helper.retrieveOrgAmbitoTerritParent(idAmbitoTerrit);
	assertTrue(true);
    }

    @Test
    void retrieveOrgCategEnteListQueryIsOk() {

	helper.retrieveOrgCategEnteList();
	assertTrue(true);
    }

    @Test
    void getOrgEnteSiamQueryIsOk() {
	String nmEnteSiam = aString();

	helper.getOrgEnteSiam(nmEnteSiam);
	assertTrue(true);
    }

    @Test
    void getOrgEnteConvenzByTiEnteConvenzQueryIsOk() {
	for (ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz : ConstOrgEnteSiam.TiEnteConvenz
		.values()) {
	    helper.getOrgEnteConvenzByTiEnteConvenz(tiEnteConvenz);
	    assertTrue(true);
	}
    }

    @Test
    @Disabled("Su SVIL ci sono record di ORG_ENTE_SIAM con  TI_ENTE_NON_CONVENZ = 'SOGGETTO_ATTUATORE'  che da errore, è un work in progress")
    void getOrgEnteNonConvenzListQueryIsOk() {
	BigDecimal idEnteNonConvenzDaEscludere = aBigDecimal();
	helper.getOrgEnteNonConvenzList(idEnteNonConvenzDaEscludere);
	assertTrue(true);
    }

    @Test
    void getOrgSuptEsternoEnteConvenzListQueryIsOk() {
	BigDecimal idEnteFornitEst = aBigDecimal();

	helper.getOrgSuptEsternoEnteConvenzList(idEnteFornitEst);
	assertTrue(true);
    }

    @Test
    void getOrgEnteConvenzOrgListQueryIsOk() {
	BigDecimal idEnteFornitEst = aBigDecimal();

	helper.getOrgEnteConvenzOrgList(idEnteFornitEst);
	assertTrue(true);
    }

    @Test
    void retrieveOrgEnteArkRifQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgEnteArkRif(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgEnteUserRifQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgEnteUserRif(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgEnteNonConvenzListQueryIsOk() {
	String nmEnteSiam = aString();
	BigDecimal idUserIamCor = aBigDecimal();
	String flEnteCessato = aString();
	List<BigDecimal> idArchivista = aListOfBigDecimal(2);
	String noArchivista = aString();
	Date dataOdierna = todayTs();
	String tiEnteNonConvenz = aString();

	helper.retrieveOrgEnteNonConvenzList(nmEnteSiam, idUserIamCor, flEnteCessato, idArchivista,
		noArchivista, dataOdierna, tiEnteNonConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAccordoVigilQueryIsOk() {
	BigDecimal idEnteOrganoVigil = aBigDecimal();

	helper.retrieveOrgAccordoVigil(idEnteOrganoVigil);
	assertTrue(true);
    }

    @Test
    void existsEnteConvenzOrgEnteCorrispQueryIsOk() {
	BigDecimal idOrganizIam = aBigDecimal();
	BigDecimal idEnteSiamProdutCorrisp = aBigDecimal();

	helper.existsEnteConvenzOrgEnteCorrisp(idOrganizIam, idEnteSiamProdutCorrisp);
	assertTrue(true);
    }

    @Test
    void retrieveOrgVigilEnteProdutQueryIsOk() {
	BigDecimal idAccordoVigil = aBigDecimal();

	helper.retrieveOrgVigilEnteProdut(idAccordoVigil);
	assertTrue(true);
    }

    @Test
    void getUsrOrganizIamQueryIsOk() {
	String nmApplic = aString();
	String nmTipoOrganiz = aString();
	BigDecimal idOrganizApplic = aBigDecimal();

	helper.getUsrOrganizIam(nmApplic, nmTipoOrganiz, idOrganizApplic);
	assertTrue(true);
    }

    @Test
    void isUtenteArchivistaInEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();

	helper.isUtenteArchivistaInEnteConvenz(idEnteConvenz, idUserIam);
	assertTrue(true);
    }

    @Test
    void isReferenteEnteInEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();

	helper.isReferenteEnteInEnteConvenz(idEnteConvenz, idUserIam);
	assertTrue(true);
    }

    @Test
    void isReferenteEnteCessatoQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.isReferenteEnteCessato(idUserIam);
	assertTrue(true);
    }

    @Test
    void retrieveUtentiArchivistiQueryIsOk() {

	helper.retrieveUtentiArchivisti();
	assertTrue(true);
    }

    @Test
    void retrieveAmbientiEntiConvenzAbilitatiQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.retrieveAmbientiEntiConvenzAbilitati(idUserIam);
	assertTrue(true);
    }

    @Test
    void retrieveAmbientiEntiXenteAbilitatiQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.retrieveAmbientiEntiXenteAbilitati(idUserIam);
	assertTrue(true);
    }

    @Test
    void retrieveAbilAmbEnteXEnteValidAmbientiQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.retrieveAbilAmbEnteXEnteValidAmbienti(idUserIam);
	assertTrue(true);
    }

    @Test
    void retrieveEntiConvenzAbilitatiAccordoValidoQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.retrieveEntiConvenzAbilitatiAccordoValido(idUserIamCor, idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void existsEnteSuptPerEnteFornitQueryIsOk() {
	BigDecimal idEnteFornitEst = aBigDecimal();
	BigDecimal idEnteProdut = aBigDecimal();
	Date dtIniValSupt = todayTs();
	Date dtFinValSupt = tomorrowTs();
	BigDecimal idSuptEstEnteConvenzDaEscludere = aBigDecimal();

	helper.existsEnteSuptPerEnteFornit(idEnteFornitEst, idEnteProdut, dtIniValSupt,
		dtFinValSupt, idSuptEstEnteConvenzDaEscludere);
	assertTrue(true);
    }

    @Test
    void countEnteSuptPerEnteFornitQueryIsOk() {
	BigDecimal idEnteFornitEst = aBigDecimal();
	BigDecimal idEnteProdut = aBigDecimal();

	helper.countEnteSuptPerEnteFornit(idEnteFornitEst, idEnteProdut);
	assertTrue(true);
    }

    @Test
    void getAplSistemaVersanteByIdQueryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();

	helper.getAplSistemaVersanteById(idEnteSiam);
	assertTrue(true);
    }

    @Test
    void getStrutUnitaDocAccordoOrganizMapQueryIsOk() {
	BigDecimal idOrganizApplic = BigDecimal.ZERO;
	try {
	    helper.getStrutUnitaDocAccordoOrganizMap(idOrganizApplic);
	} catch (Exception e) {
	    assertTrue(e.getMessage().contains("IndexOutOfBoundsException"));
	}
    }
}
