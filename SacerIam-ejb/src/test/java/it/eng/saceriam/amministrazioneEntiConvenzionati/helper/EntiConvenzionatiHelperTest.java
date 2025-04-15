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
package it.eng.saceriam.amministrazioneEntiConvenzionati.helper;

import static it.eng.ArquillianUtils.aBigDecimal;
import static it.eng.ArquillianUtils.aFlag;
import static it.eng.ArquillianUtils.aListOfBigDecimal;
import static it.eng.ArquillianUtils.aListOfString;
import static it.eng.ArquillianUtils.aLong;
import static it.eng.ArquillianUtils.aSetOfBigDecimal;
import static it.eng.ArquillianUtils.aSetOfString;
import static it.eng.ArquillianUtils.aString;
import static it.eng.ArquillianUtils.assertNoResultException;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static it.eng.ArquillianUtils.todayTs;
import static it.eng.ArquillianUtils.tomorrowTs;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@ArquillianTest
public class EntiConvenzionatiHelperTest {

    @EJB
    private EntiConvenzionatiHelper helper;

    @Deployment
    public static Archive<JavaArchive> createTestArchive() {
	return getSacerIamArchive(EntiConvenzionatiHelper.class)
		.addPackages(true, "it.eng.parer.sacerlog.entity",
			"it.eng.parer.sacerlog.viewEntity")
		.addClasses(
			it.eng.saceriam.amministrazioneEntiConvenzionati.dto.ServizioFatturatoBean.class,
			it.eng.saceriam.grantedViewEntity.DecVLisTiUniDocAms.class,
			EntiConvenzionatiHelperTest.class);
    }

    @Test
    void retrieveOrgAmbitoTerritQueryIsOk() {
	String tiAmbitoTerrit = aString();

	helper.retrieveOrgAmbitoTerrit(tiAmbitoTerrit);
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
    void getOrgEnteConvenzStringQueryIsOk() {
	String nmEnteConvenz = aString();

	helper.getOrgEnteConvenz(nmEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgEnteConvenz2StringQueryIsOk() {
	String cdEnteConvenz = aString();
	String tiCdEnteConvenz = aString();

	helper.getOrgEnteConvenz(cdEnteConvenz, tiCdEnteConvenz);
	assertTrue(true);
    }

    @Test
    @Disabled("Su SVIL ci sono record di ORG_ENTE_SIAM con  TI_ENTE_NON_CONVENZ = 'SOGGETTO_ATTUATORE'  che da errore, è un work in progress")
    void getOrgEnteConvenzListBigDecimalQueryIsOk() {
	BigDecimal idEnteConvenzDaEscludere = aBigDecimal();

	helper.getOrgEnteConvenzList(idEnteConvenzDaEscludere);
	assertTrue(true);
    }

    @Test
    void getOrgEnteConvenzListConstOrgEnteSiamTiEnteConvenzQueryIsOk() {
	for (ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz : ConstOrgEnteSiam.TiEnteConvenz
		.values()) {
	    helper.getOrgEnteConvenzList(tiEnteConvenz);
	    assertTrue(true);
	}
    }

    @Test
    void getOrgAmbienteEnteConvenzListQueryIsOk() {
	for (ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz : ConstOrgEnteSiam.TiEnteConvenz
		.values()) {
	    helper.getOrgAmbienteEnteConvenzList(tiEnteConvenz);
	    assertTrue(true);
	}
    }

    @Test
    void getOrgEnteConvenzUserAbilListQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	ConstOrgEnteSiam.TiEnteConvenz[] tiEnteConvenz = ConstOrgEnteSiam.TiEnteConvenz.values();

	helper.getOrgEnteConvenzUserAbilList(idUserIamCor, tiEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgAmbienteEnteConvenzUserAbilListQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	ConstOrgEnteSiam.TiEnteConvenz[] tiEnteConvenz = ConstOrgEnteSiam.TiEnteConvenz.values();

	helper.getOrgAmbienteEnteConvenzUserAbilList(idUserIamCor, tiEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgEnteConvenzCollegUserAbilListQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getOrgEnteConvenzCollegUserAbilList(idUserIamCor, idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgEnteConvenzCessatiListQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getOrgEnteConvenzCessatiList(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgEnteConvenzNonCessatiListQueryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();
	BigDecimal idEnteConvenzExcluded = aBigDecimal();

	helper.getOrgEnteConvenzNonCessatiList(idAmbienteEnteConvenz, idEnteConvenzExcluded);
	assertTrue(true);
    }

    @Test
    void getUsrUserCollegListQueryIsOk() {
	BigDecimal idCollegEntiConvenz = aBigDecimal();
	BigDecimal idEnteConvenzExcluded = aBigDecimal();

	helper.getUsrUserCollegList(idCollegEntiConvenz, idEnteConvenzExcluded);
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
    void retrieveOrgCollegEntiConvenzBigDecimalQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgCollegEntiConvenz(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgCollegEntiConvenz5argsQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	String nmColleg = aString();
	String dsColleg = aString();
	Date dtValidDa = todayTs();
	Date dtValidA = tomorrowTs();

	helper.retrieveOrgCollegEntiConvenz(idUserIamCor, nmColleg, dsColleg, dtValidDa, dtValidA);
	assertTrue(true);
    }

    @Test
    void retrieveOrgCollegEntiConvenzValidQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();

	helper.retrieveOrgCollegEntiConvenzValid(idUserIamCor);
	assertTrue(true);
    }

    @Test
    void retrieveOrgSuptEsternoEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	for (ConstOrgEnteSiam.TiEnteNonConvenz tiEnteNonConvenz : ConstOrgEnteSiam.TiEnteNonConvenz
		.values()) {
	    helper.retrieveOrgSuptEsternoEnteConvenz(idEnteConvenz, tiEnteNonConvenz.name());
	}
	assertTrue(true);
    }

    @Test
    void retrieveOrgVigilEnteProdutQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgVigilEnteProdut(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgEnteConvenzListQueryIsOk() {
	String nmEnteConvenz = aString();
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();
	String flEnteAttivo = aFlag();
	String flEnteCessato = aFlag();
	BigDecimal idCategEnte = aBigDecimal();
	List<BigDecimal> idAmbitoTerritRegione = aListOfBigDecimal(2);
	List<BigDecimal> idAmbitoTerritProv = aListOfBigDecimal(2);
	List<BigDecimal> idAmbitoTerritFormAssoc = aListOfBigDecimal(2);
	List<BigDecimal> idTipoAccordo = aListOfBigDecimal(2);
	Date dtFineValidAccordoDa = todayTs();
	Date dtFineValidAccordoA = tomorrowTs();
	Date dtScadAccordoDa = todayTs();
	Date dtScadAccordoA = tomorrowTs();
	Date dtDecAccordoDa = todayTs();
	Date dtDecAccordoA = tomorrowTs();
	Date dtDecAccordoInfoDa = todayTs();
	Date dtDecAccordoInfoA = tomorrowTs();
	Date dtIniValDa = todayTs();
	Date dtIniValA = tomorrowTs();
	Date dtCessazioneDa = todayTs();
	Date dtCessazioneA = tomorrowTs();

	List<BigDecimal> idArchivista = aListOfBigDecimal(2);
	String noArchivista = aString();
	String flRicev = aFlag();
	String flRichModuloInfo = aFlag();
	String flNonConvenz = aFlag();
	String flRecesso = aFlag();
	String flChiuso = aFlag();
	String flEsistonoGestAcc = aFlag();
	List<BigDecimal> idTipoGestioneAccordo = aListOfBigDecimal(2);
	String flGestAccNoRisp = aFlag();
	String tiStatoAccordo = "Accordo valido";
	String cdFisc = aString();
	helper.retrieveOrgEnteConvenzList(nmEnteConvenz, idUserIamCor, idAmbienteEnteConvenz,
		flEnteAttivo, flEnteCessato, idCategEnte, idAmbitoTerritRegione, idAmbitoTerritProv,
		idAmbitoTerritFormAssoc, idTipoAccordo, dtFineValidAccordoDa, dtFineValidAccordoA,
		dtScadAccordoDa, dtScadAccordoA, idArchivista, noArchivista, flRicev,
		flRichModuloInfo, flNonConvenz, flRecesso, flChiuso, flEsistonoGestAcc,
		idTipoGestioneAccordo, flGestAccNoRisp, tiStatoAccordo, cdFisc, dtDecAccordoDa,
		dtDecAccordoA, dtDecAccordoInfoDa, dtDecAccordoInfoA, dtIniValDa, dtIniValA,
		dtCessazioneDa, dtCessazioneA);
	tiStatoAccordo = "Accordo non valido";
	helper.retrieveOrgEnteConvenzList(nmEnteConvenz, idUserIamCor, idAmbienteEnteConvenz,
		flEnteAttivo, flEnteCessato, idCategEnte, idAmbitoTerritRegione, idAmbitoTerritProv,
		idAmbitoTerritFormAssoc, idTipoAccordo, dtFineValidAccordoDa, dtFineValidAccordoA,
		dtScadAccordoDa, dtScadAccordoA, idArchivista, noArchivista, flRicev,
		flRichModuloInfo, flNonConvenz, flRecesso, flChiuso, flEsistonoGestAcc,
		idTipoGestioneAccordo, flGestAccNoRisp, tiStatoAccordo, cdFisc, dtDecAccordoDa,
		dtDecAccordoA, dtDecAccordoInfoDa, dtDecAccordoInfoA, dtIniValDa, dtIniValA,
		dtCessazioneDa, dtCessazioneA);
	assertTrue(true);
    }

    @Test
    void esisteGestionePerChiaveQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();
	String cdRegistroGestAccordo = aString();
	BigDecimal aaGestAccordo = aBigDecimal();
	String cdKeyGestAccordo = aString();

	helper.esisteGestionePerChiave(idAccordoEnte, cdRegistroGestAccordo, aaGestAccordo,
		cdKeyGestAccordo);
	assertTrue(true);
    }

    @Test
    void esisteModelloInfoPerDataIdentificativoQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();
	String cdModuloInfo = aString();
	String cdRegistroModuloInfo = aString();
	BigDecimal aaModuloInfo = aBigDecimal();
	String cdKeyModuloInfo = aString();

	helper.esisteModelloInfoPerDataIdentificativo(idAccordoEnte, cdModuloInfo,
		cdRegistroModuloInfo, aaModuloInfo, cdKeyModuloInfo);
	assertTrue(true);
    }

    @Test
    void retrieveOrgStoEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgStoEnteConvenz(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAccordoEnteGestoreQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idEnteConserv = aBigDecimal();
	boolean isValid = false;
	ConstOrgEnteSiam.TiEnteConvenz[] tiEnteConvenz = ConstOrgEnteSiam.TiEnteConvenz.values();

	helper.retrieveOrgAccordoEnteGestore(idUserIamCor, idEnteConserv, isValid, tiEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgVRicEnteConvenzListQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	String tiEnteConvenz = aString();

	helper.getOrgVRicEnteConvenzList(idUserIamCor, tiEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getEnteConvenzConservListQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idEnteSiamGestore = aBigDecimal();

	helper.getEnteConvenzConservList(idUserIamCor, idEnteSiamGestore);
	assertTrue(true);
    }

    @Test
    void getEntiValidiAmbienteQueryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getEntiValidiAmbiente(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getUtentiAttiviAbilitatiAdAmbienteQueryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getUtentiAttiviAbilitatiAdAmbiente(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getUtentiAttiviAbilitatiAdAmbiente2QueryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getUtentiAttiviAbilitatiAdAmbiente2(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getUtentiAttiviAbilitatiAdEnteQueryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();

	helper.getUtentiAttiviAbilitatiAdEnte(idEnteSiam);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAccordoEnteQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgAccordoEnte(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAccordoEnteValidByTipoQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	for (ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz : ConstOrgEnteSiam.TiEnteConvenz
		.values()) {
	    helper.retrieveOrgAccordoEnteValidByTipo(idEnteConvenz, tiEnteConvenz.name());
	    assertTrue(true);
	}
    }

    @Test
    void retrieveOrgAccordoEntePiuRecenteQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgAccordoEntePiuRecente(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAccordoEnteValidoQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgAccordoEnteValido(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgEnteConvenzOrgBigDecimalQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgEnteConvenzOrg(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgVEnteConvenzByOrganizQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	boolean filterValid = false;

	helper.retrieveOrgVEnteConvenzByOrganiz(idEnteConvenz, filterValid);
	assertTrue(true);
    }

    @Test
    void retrieveOrgEnteConvenzOrg2BigDecimalQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	BigDecimal idOrganizIam = aBigDecimal();

	helper.retrieveOrgEnteConvenzOrg(idEnteConvenz, idOrganizIam);
	assertTrue(true);
    }

    @Test
    void retrieveOrgEnteConvenzOrgForOrganizQueryIsOk() {
	BigDecimal idOrganizIam = aBigDecimal();

	helper.retrieveOrgEnteConvenzOrgForOrganiz(idOrganizIam);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaServiziInFatturaPerEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaServiziInFatturaPerEnteConvenz(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaServiziInFatturaPerAccordoQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();

	helper.checkEsistenzaServiziInFatturaPerAccordo(idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaServiziInFatturaQueryIsOk() {
	BigDecimal idServizioErogato = aBigDecimal();

	helper.checkEsistenzaServiziInFattura(idServizioErogato);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAssociazioneEnteConvenzStrutVersQueryIsOk() {
	BigDecimal idOrganizIam = aBigDecimal();
	Date dtIniVal = todayTs();
	Date dtFineVal = tomorrowTs();
	BigDecimal idEnteConvenzOrgDaEscludere = aBigDecimal();

	helper.checkEsistenzaAssociazioneEnteConvenzStrutVers(idOrganizIam, dtIniVal, dtFineVal,
		idEnteConvenzOrgDaEscludere);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaUtenteEnteAppartQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaUtenteEnteAppart(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkAppartenenzaUtenteEnteQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkAppartenenzaUtenteEnte(idUserIam, idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaEnteAbilUnEnteQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaEnteAbilUnEnte(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaRichiesteUtenteQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaRichiesteUtente(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAssociazioneStruttureEnteQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	helper.checkEsistenzaAssociazioneStruttureEnte(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTipoAccordoQueryIsOk() {
	helper.retrieveOrgTipoAccordo();
	assertTrue(true);
    }

    @Test
    void retrieveOrgTipiGestioneAccordoQueryIsOk() {

	helper.retrieveOrgTipiGestioneAccordo();
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffarioBigDecimalQueryIsOk() {
	BigDecimal idTipoAccordo = aBigDecimal();

	helper.retrieveOrgTariffario(idTipoAccordo);
	assertTrue(true);
    }

    @Test
    void retrieveOrgServizioErogQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();

	helper.retrieveOrgServizioErog(idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void retrieveOrgModuloInfoAccordoQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();

	helper.retrieveOrgModuloInfoAccordo(idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void retrieveOrgGestioneAccordoQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();

	helper.retrieveOrgGestioneAccordo(idAccordoEnte);
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
    void checkEsistenzaAltriAccordiDtDecDtFineValidQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	Date dtDecAccordo = todayTs();
	Date dtFineValidAccordo = tomorrowTs();
	BigDecimal idAccordoDaEscludere = aBigDecimal();

	helper.checkEsistenzaAltriAccordiDtDecDtFineValid(idEnteConvenz, dtDecAccordo,
		dtFineValidAccordo, idAccordoDaEscludere);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAccordoDtDecDtScadNulliQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaAccordoDtDecDtScadNulli(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void isDtDecDtFineValIndefiniteQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.isDtDecDtFineValIndefinite(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void isDtDecAccordoEmptyQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.isDtDecAccordoEmpty(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAccordoSuDataRegistrazioneQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	Date dtRegAccordo = todayTs();
	BigDecimal idAccordoDaEscludere = aBigDecimal();

	helper.checkEsistenzaAccordoSuDataRegistrazione(idEnteConvenz, dtRegAccordo,
		idAccordoDaEscludere);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAccordiAttiviQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaAccordiAttivi(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void isDataCessazioneEnteInferioreUgualeDataAccordoQueryIsOk() {
	long idEnteSiam = aLong();
	Date dtCessazione = todayTs();

	helper.isDataCessazioneEnteInferioreUgualeDataAccordo(idEnteSiam, dtCessazione);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaUtenteAbilitatoXEnteQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaUtenteAbilitatoXEnte(idUserIam, idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAmbienteValidoDtDecDtFineValidQueryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();
	Date dtDecAccordo = todayTs();
	Date dtFineValidAccordo = tomorrowTs();
	BigDecimal idAmbienteDaEscludere = aBigDecimal();

	helper.checkEsistenzaAmbienteValidoDtDecDtFineValid(idAmbienteEnteConvenz, dtDecAccordo,
		dtFineValidAccordo, idAmbienteDaEscludere);
	assertTrue(true);
    }

    @Test
    void getUsrTipoDatoIamQueryIsOk() {
	String nmApplic = aString();
	String nmTipoOrganiz = aString();
	BigDecimal idOrganizApplic = aBigDecimal();
	String nmClasseTipoDato = aString();

	helper.getUsrTipoDatoIam(nmApplic, nmTipoOrganiz, idOrganizApplic, nmClasseTipoDato);
	assertTrue(true);
    }

    @Test
    void retrieveOrgClasseEnteConvenz0argsQueryIsOk() {

	helper.retrieveOrgClasseEnteConvenz();
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffa3argsQueryIsOk() {
	BigDecimal idTipoServizio = aBigDecimal();
	BigDecimal idTariffario = aBigDecimal();
	BigDecimal idClasseEnteConvenz = aBigDecimal();

	helper.retrieveOrgTariffa(idTipoServizio, idTariffario, idClasseEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgServizioFatturaQueryIsOk() {
	long idServizioErogato = aLong();

	helper.retrieveOrgServizioFattura(idServizioErogato);
	assertTrue(true);
    }

    @Test
    void getUltimoStatoFatturaEnteQueryIsOk() {
	long idFatturaEnte = aLong();

	helper.getUltimoStatoFatturaEnte(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getLastAccordoEnteQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getLastAccordoEnte(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getImValoreTariffaDaScaglioneQueryIsOk() {
	long idTariffa = aLong();

	helper.getImValoreTariffaDaScaglione(idTariffa);
	assertTrue(true);
    }

    @Test
    void getIdSistemiVersantiListQueryIsOk() {
	long idEnteConvenz = aLong();

	helper.getIdSistemiVersantiList(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTipoServizio3argsQueryIsOk() {
	long idClasseEnteConvenz = aLong();
	long idTariffario = aLong();
	List<BigDecimal> idTipoServizioEsclusi = aListOfBigDecimal(2);

	helper.retrieveOrgTipoServizio(idClasseEnteConvenz, idTariffario, idTipoServizioEsclusi);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaServiziErogatiPerAccordoQueryIsOk() {
	BigDecimal idServizioErogato = aBigDecimal();
	BigDecimal idAccordoEnte = aBigDecimal();
	BigDecimal idTipoServizio = aBigDecimal();
	BigDecimal idSistemaVersante = aBigDecimal();

	helper.checkEsistenzaServiziErogatiPerAccordo(idServizioErogato, idAccordoEnte,
		idTipoServizio, idSistemaVersante);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTipoServizio0argsQueryIsOk() {

	helper.retrieveOrgTipoServizio();
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffa2argsQueryIsOk() {
	long idTariffario = aLong();
	String nmTariffa = aString();

	helper.retrieveOrgTariffa(idTariffario, nmTariffa);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaServiziErogatiPerEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.checkEsistenzaServiziErogatiPerEnteConvenz(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTipoAccordoListQueryIsOk() {
	String cdTipoAccordo = aString();
	String dsTipoAccordo = aString();
	String flPagamento = aString();
	String isAttivo = aString();

	helper.retrieveOrgTipoAccordoList(cdTipoAccordo, dsTipoAccordo, flPagamento, isAttivo);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAccordiPerTipoAccordoQueryIsOk() {
	BigDecimal idTipoAccordo = aBigDecimal();

	helper.checkEsistenzaAccordiPerTipoAccordo(idTipoAccordo);
	assertTrue(true);
    }

    @Test
    void getOrgTipoAccordoQueryIsOk() {
	String cdTipoAccordo = aString();

	helper.getOrgTipoAccordo(cdTipoAccordo);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffario3argsQueryIsOk() {
	String isValido = aString();
	String cdTipoAccordo = aString();
	String nomeTariffario = aString();

	helper.retrieveOrgTariffario(isValido, cdTipoAccordo, nomeTariffario);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTipoAccordoAttiviQueryIsOk() {

	helper.retrieveOrgTipoAccordoAttivi();
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffaBigDecimalQueryIsOk() {
	BigDecimal idTariffario = aBigDecimal();

	helper.retrieveOrgTariffa(idTariffario);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffaByTipoServizioQueryIsOk() {
	BigDecimal idTipoServizio = aBigDecimal();

	helper.retrieveOrgTariffaByTipoServizio(idTipoServizio);
	assertTrue(true);
    }

    @Test
    void retrieveOrgScaglioneTariffaQueryIsOk() {
	BigDecimal idTariffa = aBigDecimal();

	helper.retrieveOrgScaglioneTariffa(idTariffa);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAccordiPerTariffarioQueryIsOk() {
	BigDecimal idTariffario = aBigDecimal();

	helper.checkEsistenzaAccordiPerTariffario(idTariffario);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaTariffarioQueryIsOk() {
	String nmTariffario = aString();
	BigDecimal idTipoAccordo = aBigDecimal();
	BigDecimal idTariffarioDaEscludere = aBigDecimal();

	helper.checkEsistenzaTariffario(nmTariffario, idTipoAccordo, idTariffarioDaEscludere);
	assertTrue(true);
    }

    @Test
    void retrieveOrgClasseEnteConvenz2StringQueryIsOk() {
	String cdClasseEnteConvenz = aString();
	String dsClasseEnteConvenz = aString();

	helper.retrieveOrgClasseEnteConvenz(cdClasseEnteConvenz, dsClasseEnteConvenz);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaServiziErogatiPerTipoServizioQueryIsOk() {
	BigDecimal idTipoServizio = aBigDecimal();

	helper.checkEsistenzaServiziErogatiPerTipoServizio(idTipoServizio);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaServiziErogatiPerTariffaQueryIsOk() {
	BigDecimal idTariffa = aBigDecimal();

	helper.checkEsistenzaServiziErogatiPerTariffa(idTariffa);
	assertTrue(true);
    }

    @Test
    void getOrgTipoServizioQueryIsOk() {
	String cdTipoServizio = aString();

	helper.getOrgTipoServizio(cdTipoServizio);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTipoServizioListQueryIsOk() {
	String cdTipoServizio = aString();
	String tiClasseTipoServizio = aString();
	String tipoFatturazione = aString();

	helper.retrieveOrgTipoServizioList(cdTipoServizio, tiClasseTipoServizio, tipoFatturazione);
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
    void getOrgTariffaBigDecimalStringQueryIsOk() {
	BigDecimal idTariffario = aBigDecimal();
	String nmTariffa = aString();

	helper.getOrgTariffa(idTariffario, nmTariffa);
	assertTrue(true);
    }

    @Test
    void getOrgTariffa3argsQueryIsOk() {
	BigDecimal idTariffario = aBigDecimal();
	BigDecimal idTipoServizio = aBigDecimal();
	BigDecimal idClasseEnteConvenz = aBigDecimal();
	BigDecimal idTariffa = aBigDecimal();

	helper.getOrgTariffa(idTariffario, idTipoServizio, idClasseEnteConvenz, idTariffa);
	assertTrue(true);
    }

    @Test
    void retrieveOrgCdIvaListQueryIsOk() {

	helper.retrieveOrgCdIvaList();
	assertTrue(true);
    }

    @Test
    void retrieveOrgVRicFattureListQueryIsOk() {
	String nmEnteConvenz = aString();
	BigDecimal idTipoAccordo = aBigDecimal();
	BigDecimal idTipoServizio = aBigDecimal();
	BigDecimal aaFatturaDa = aBigDecimal();
	BigDecimal aaFatturaA = aBigDecimal();
	BigDecimal pgFatturaEnteDa = aBigDecimal();
	BigDecimal pgFatturaEnteA = aBigDecimal();
	String cdFattura = aString();
	String cdRegistroEmisFattura = aString();
	BigDecimal aaEmissFattura = aBigDecimal();
	String cdEmisFattura = aString();
	BigDecimal pgFatturaEmis = aBigDecimal();
	Date dtEmisFatturaDa = todayTs();
	Date dtEmisFatturaA = tomorrowTs();
	Set<String> tiStatoFatturaEnte = aSetOfString(2);

	helper.retrieveOrgVRicFattureList(nmEnteConvenz, idTipoAccordo, idTipoServizio, aaFatturaDa,
		aaFatturaA, pgFatturaEnteDa, pgFatturaEnteA, cdFattura, cdRegistroEmisFattura,
		aaEmissFattura, cdEmisFattura, pgFatturaEmis, dtEmisFatturaDa, dtEmisFatturaA,
		tiStatoFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getOrgStatoFatturaEnteQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();
	String tiStatoFatturaEnte = aString();

	helper.getOrgStatoFatturaEnte(idFatturaEnte, tiStatoFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getServiziFatturatiQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.getServiziFatturati(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getOrgServizioFatturaQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();
	BigDecimal idServizioErogato = aBigDecimal();
	BigDecimal aaServizioFattura = aBigDecimal();

	helper.getOrgServizioFattura(idFatturaEnte, idServizioErogato, aaServizioFattura);
	assertTrue(true);
    }

    @Test
    void retrieveUtentiArchivistiQueryIsOk() {

	helper.retrieveUtentiArchivisti();
	assertTrue(true);
    }

    @Test
    void retrieveUtentiArchivistiDaVistaQueryIsOk() {

	helper.retrieveUtentiArchivistiDaVista();
	assertTrue(true);
    }

    @Test
    void getOrgStoEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	Date dtIniVal = todayTs();

	helper.getOrgStoEnteConvenz(idEnteConvenz, dtIniVal);
	assertTrue(true);
    }

    @Test
    void getServiziErogatiSuAccordoQueryIsOk() {
	long idAccordoEnte = aLong();

	helper.getServiziErogatiSuAccordo(idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void retrieveEntiConvenzAbilitatiBigDecimalQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();

	helper.retrieveEntiConvenzAbilitati(idUserIamCor);
	assertTrue(true);
    }

    @Test
    void retrieveEntiConvenzAbilitatiAmbienteQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	List<BigDecimal> idAmbienteEnteConvenz = aListOfBigDecimal(2);
	for (ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz : ConstOrgEnteSiam.TiEnteConvenz
		.values()) {
	    helper.retrieveEntiConvenzAbilitatiAmbiente(idUserIamCor, idAmbienteEnteConvenz,
		    tiEnteConvenz);
	    assertTrue(true);
	}
    }

    @Test
    void retrieveEntiConvenzCollegAbilitatiAmbienteQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	List<BigDecimal> idAmbienteEnteConvenz = aListOfBigDecimal(2);
	for (ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz : ConstOrgEnteSiam.TiEnteConvenz
		.values()) {
	    helper.retrieveEntiConvenzCollegAbilitatiAmbiente(idUserIamCor, idAmbienteEnteConvenz,
		    tiEnteConvenz);
	    assertTrue(true);
	}
    }

    @Test
    void retrieveEntiConvenzAbilitatiAccordoValidoQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.retrieveEntiConvenzAbilitatiAccordoValido(idUserIamCor, idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveEntiNonConvenzAbilitatiQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	String tiEnteNonConvenz = aString();

	helper.retrieveEntiNonConvenzAbilitati(idUserIam, tiEnteNonConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveEntiConvenzAbilitatiXEnteCapofila3argsQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idEnteConvenz = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.retrieveEntiConvenzAbilitatiXEnteCapofila(idUserIamCor, idEnteConvenz,
		idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveEntiConvenzAbilitatiXEnteCapofila2BigDecimalQueryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.retrieveEntiConvenzAbilitatiXEnteCapofila(idUserIamCor, idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveAbilAmbEntiConvenzValidByIdEnteConservQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idEnteConserv = aBigDecimal();

	helper.retrieveAbilAmbEntiConvenzValidByIdEnteConserv(idUserIam, idEnteConserv);
	assertTrue(true);
    }

    @Test
    void retrieveAbilAmbEnteXEnteValidAmbientiQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idEnteConserv = aBigDecimal();
	BigDecimal idEnteGestore = aBigDecimal();

	helper.retrieveAbilAmbEnteXEnteValidAmbienti(idUserIam, idEnteConserv, idEnteGestore);
	assertTrue(true);
    }

    @Test
    void retrieveAmbientiEntiConvenzAbilitatiBigDecimalQueryIsOk() {
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
    void retrieveAmbientiEntiXenteAbilitatiValidQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.retrieveAmbientiEntiXenteAbilitatiValid(idUserIam);
	assertTrue(true);
    }

    @Test
    void retrieveEntiConvenzAbilitatiBigDecimalDateQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	Date data = todayTs();

	helper.retrieveEntiConvenzAbilitati(idUserIam, data);
	assertTrue(true);
    }

    @Test
    void retrieveEntiConvenzAbilitatiByUtenteAmbienteQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();
	Date data = todayTs();

	helper.retrieveEntiConvenzAbilitatiByUtenteAmbiente(idUserIam, idAmbienteEnteConvenz, data);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAmbienteEnteConvenzByEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveAmbientiEntiConvenzAbilitati3argsQueryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	String nmAmbienteEnteConvenz = aString();
	String dsAmbienteEnteConvenz = aString();

	helper.retrieveAmbientiEntiConvenzAbilitati(idUserIam, nmAmbienteEnteConvenz,
		dsAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgAmbienteEnteConvenzQueryIsOk() {
	String nmAmbienteEnteConvenz = aString();

	helper.getOrgAmbienteEnteConvenz(nmAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgCollegEntiConvenzQueryIsOk() {
	String nmColleg = aString();

	helper.getOrgCollegEntiConvenz(nmColleg);
	assertTrue(true);
    }

    @Test
    void retrieveEnteCapofilaQueryIsOk() {
	BigDecimal idEnteConvenzCapofila = aBigDecimal();

	helper.retrieveEnteCapofila(idEnteConvenzCapofila);
	assertTrue(true);
    }

    @Test
    void retrieveOrgVRicEnteConvenzByAmbienteQueryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.retrieveOrgVRicEnteConvenzByAmbiente(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgVLisServFatturaListByIdFatturaQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.retrieveOrgVLisServFatturaListByIdFattura(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void retrieveOrgVLisServFatturaListByIdServizioErogQueryIsOk() {
	BigDecimal idServizioErogato = aBigDecimal();

	helper.retrieveOrgVLisServFatturaListByIdServizioErog(idServizioErogato);
	assertTrue(true);
    }

    @Test
    void retrieveOrgPagamFatturaEnteListQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.retrieveOrgPagamFatturaEnteList(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void retrieveFattureRiemesseListQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.retrieveFattureRiemesseList(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getOrgSollecitoFatturaEnteListQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.getOrgSollecitoFatturaEnteList(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getLastProgressivoIncassoQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.getLastProgressivoIncasso(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void retrieveOrgModifFatturaEnteListQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.retrieveOrgModifFatturaEnteList(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void isUnitaDocVersataInSacerQueryIsOk() {
	BigDecimal idStrut = aBigDecimal();
	String cdRegistroKeyUnitaDoc = aString();
	BigDecimal aaKeyUnitaDoc = aBigDecimal();
	String cdKeyUnitaDoc = aString();

	helper.isUnitaDocVersataInSacer(idStrut, cdRegistroKeyUnitaDoc, aaKeyUnitaDoc,
		cdKeyUnitaDoc);
	assertTrue(true);
    }

    @Test
    void retrieveRegistriConnessiListQueryIsOk() {
	BigDecimal idTipoUnitaDoc = aBigDecimal();

	helper.retrieveRegistriConnessiList(idTipoUnitaDoc);
	assertTrue(true);
    }

    @Test
    void existOtherAnagrafichePerIntervalloQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	Date dtIniVal = todayTs();
	Date dtFineVal = tomorrowTs();
	BigDecimal idStoEnteConvenzDaEscludere = aBigDecimal();

	helper.existOtherAnagrafichePerIntervallo(idEnteConvenz, dtIniVal, dtFineVal,
		idStoEnteConvenzDaEscludere);
	assertTrue(true);
    }

    @Test
    void checkModificheIntervenuteFatturaQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();

	helper.checkModificheIntervenuteFattura(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    // @Ignore("da correggere il mappaggio o la vista su DB ")
    void getOrgVCreaFatturaByAnnoQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	BigDecimal aaFattura = aBigDecimal();
	BigDecimal aaServizioFattura = aBigDecimal();

	helper.getOrgVCreaFatturaByAnno(idEnteConvenz, aaFattura, aaServizioFattura);
	assertTrue(true);
    }

    @Test
    void getOrgVCreaServFattAnnualeCustomQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();
	BigDecimal idAccordoEnte = aBigDecimal();
	BigDecimal annoFattServizi = aBigDecimal();

	helper.getOrgVCreaServFattAnnualeCustom(idFatturaEnte, idAccordoEnte, annoFattServizi);
	assertTrue(true);
    }

    @Test
    void getOrgVCreaServFattUnatantumCustomQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();
	BigDecimal idAccordoEnte = aBigDecimal();
	BigDecimal annoFattServizi = aBigDecimal();

	helper.getOrgVCreaServFattUnatantumCustom(idFatturaEnte, idAccordoEnte, annoFattServizi);
	assertTrue(true);
    }

    @Test
    void getOrgVCreaServFattUnaPrecCustomQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();
	BigDecimal idAccordoEnte = aBigDecimal();
	BigDecimal annoFattServizi = aBigDecimal();

	helper.getOrgVCreaServFattUnaPrecCustom(idFatturaEnte, idAccordoEnte, annoFattServizi);
	assertTrue(true);
    }

    @Test
    void getOrgVCreaFatturaByFattQueryIsOk() {
	long idFatturaStornataDaRiemettere = aLong();
	BigDecimal aaFattura = aBigDecimal();

	helper.getOrgVCreaFatturaByFatt(idFatturaStornataDaRiemettere, aaFattura);
	assertTrue(true);
    }

    @Test
    void getIdFattureStornateDaRiemettereListQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getIdFattureStornateDaRiemettereList(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getAnnoServiziDaFatturareQueryIsOk() {
	long idFatturaEnte = aLong();

	helper.getAnnoServiziDaFatturare(idFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getOrgSollecitoFatturaEnteQueryIsOk() {
	BigDecimal idFatturaEnte = aBigDecimal();
	String cdRegistroSollecito = aString();
	BigDecimal aaVarSollecito = aBigDecimal();
	String cdKeyVarSollecito = aString();

	helper.getOrgSollecitoFatturaEnte(idFatturaEnte, cdRegistroSollecito, aaVarSollecito,
		cdKeyVarSollecito);
	assertTrue(true);
    }

    @Test
    void getOrgFatturaEnteByStatoListQueryIsOk() {
	List<String> tiStatoFatturaEnte = aListOfString(2);

	helper.getOrgFatturaEnteByStatoList(tiStatoFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getFattureEnteConvenzionatoQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	String tiStatoFatturaEnte = aString();

	helper.getFattureEnteConvenzionato(idEnteConvenz, tiStatoFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getEntiConvByAbilEliminateQueryIsOk() {
	Set<BigDecimal> dichOrganizDeleteList = aSetOfBigDecimal(2);

	helper.getEntiConvByAbilEliminate(dichOrganizDeleteList);
	assertTrue(true);
    }

    @Test
    void existsUtenteArchivistaPerEnteSiamQueryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();
	String flReferente = aString();
	BigDecimal idUserIamExcluded = aBigDecimal();

	helper.existsUtenteArchivistaPerEnteSiam(idEnteSiam, idUserIam, flReferente,
		idUserIamExcluded);
	assertTrue(true);
    }

    @Test
    void existsReferenteEntePerEnteSiamQueryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();
	String qualificaUser = aString();

	helper.existsReferenteEntePerEnteSiam(idEnteSiam, idUserIam, qualificaUser);
	assertTrue(true);
    }

    @Test
    void existsCollegamentoEntePerEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	String nmColleg = aString();

	helper.existsCollegamentoEntePerEnteConvenz(idEnteConvenz, nmColleg);
	assertTrue(true);
    }

    @Test
    void existsEnteCollegatoPerCollegamentoQueryIsOk() {
	BigDecimal idCollegEntiConvenz = aBigDecimal();
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.existsEnteCollegatoPerCollegamento(idCollegEntiConvenz, idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getStrutUnitaDocAccordoOrganizMapQueryIsOk() {
	BigDecimal idOrganizApplic = aBigDecimal();
	helper.getStrutUnitaDocAccordoOrganizMap(idOrganizApplic);
	assertTrue(true);
    }

    @Test
    void getDataDecorrenzaMinimaQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getDataDecorrenzaMinima(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getDataScadenzaMinimaQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getDataScadenzaMinima(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getDataFineValiditaMassimaQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getDataFineValiditaMassima(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void existsUDInSacerQueryIsOk() {
	BigDecimal idOrganizApplic = aBigDecimal();
	String registro = aString();
	BigDecimal anno = aBigDecimal();
	String numero = aString();

	helper.existsUDInSacer(idOrganizApplic, registro, anno, numero);
	assertTrue(true);
    }

    @Test
    void getOrgDiscipStrutListQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();

	helper.getOrgDiscipStrutList(idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void getOrgAppartCollegEntiListQueryIsOk() {
	BigDecimal idCollegEntiConvenz = aBigDecimal();

	helper.getOrgAppartCollegEntiList(idCollegEntiConvenz);
	assertTrue(true);
    }

    @Test
    void existsDisciplinareQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();
	BigDecimal idOrganizIam = aBigDecimal();
	Date dtDiscipStrut = todayTs();

	helper.existsDisciplinare(idAccordoEnte, idOrganizIam, dtDiscipStrut);
	assertTrue(true);
    }

    @Test
    void getNumMaxGestAccordoEnteQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();

	helper.getNumMaxGestAccordoEnte(idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void getTiParamApplicQueryIsOk() {

	helper.getTiParamApplic();
	assertTrue(true);
    }

    @Test
    void getTiGestioneParamQueryIsOk() {
	helper.getTiGestioneParam();
	assertTrue(true);
    }

    @Test
    void getIamParamApplicListQueryIsOk() {
	String tiParamApplic = aString();
	String tiGestioneParam = aString();
	String flAppartApplic = aString();
	String flAppartAmbiente = aString();
	String flApparteEnte = aString();

	helper.getIamParamApplicList(tiParamApplic, tiGestioneParam, flAppartApplic,
		flAppartAmbiente, flApparteEnte);
	assertTrue(true);
    }

    @Test
    void existsIamValoreParamApplicQueryIsOk() {
	long idParamApplic = aLong();
	String tiAppart = aString();

	helper.existsIamValoreParamApplic(idParamApplic, tiAppart);
	assertTrue(true);
    }

    @Test
    void getIamValoreParamApplicLongStringQueryIsOk() {
	long idParamApplic = aLong();
	String tiAppart = aString();

	helper.getIamValoreParamApplic(idParamApplic, tiAppart);
	assertTrue(true);
    }

    @Test
    void existsIamParamApplicQueryIsOk() {
	String nmParamApplic = aString();
	BigDecimal idParamApplic = aBigDecimal();

	helper.existsIamParamApplic(nmParamApplic, idParamApplic);
	assertTrue(true);
    }

    @Test
    void getIamParamApplicListEnteQueryIsOk() {

	helper.getIamParamApplicListEnte();
	assertTrue(true);
    }

    @Test
    void getIamParamApplicListAmbienteQueryIsOk() {

	helper.getIamParamApplicListAmbiente();
	assertTrue(true);
    }

    @Test
    void getIamValoreParamApplic4argsQueryIsOk() {
	BigDecimal idParamApplic = aBigDecimal();
	String tiAppart = aString();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getIamValoreParamApplic(idParamApplic, tiAppart, idAmbienteEnteConvenz,
		idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getAmbientiAbilitatiUnAmbienteListQueryIsOk() {
	long idUserIamCor = aLong();
	BigDecimal idEnte = aBigDecimal();

	helper.getAmbientiAbilitatiUnAmbienteList(idUserIamCor, "AMMINISTRATORE", idEnte);
	assertTrue(true);
	helper.getAmbientiAbilitatiUnAmbienteList(idUserIamCor, "CONSERVATORE", idEnte);
	assertTrue(true);
	helper.getAmbientiAbilitatiUnAmbienteList(idUserIamCor, "GESTORE", idEnte);
	assertTrue(true);
    }

    @Test
    void getEntiAbilitatiUnEnteListQueryIsOk() {
	long idUserIamCor = aLong();
	BigDecimal idEnte = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();
	helper.getEntiAbilitatiUnEnteList(idUserIamCor, "AMMINISTRATORE", idEnte,
		idAmbienteEnteConvenz);
	assertTrue(true);
	helper.getEntiAbilitatiUnEnteList(idUserIamCor, "CONSERVATORE", idEnte,
		idAmbienteEnteConvenz);
	assertTrue(true);
	helper.getEntiAbilitatiUnEnteList(idUserIamCor, "GESTORE", idEnte, idAmbienteEnteConvenz);
	assertTrue(true);
	helper.getEntiAbilitatiUnEnteList(idUserIamCor, "PRODUTTORE", idEnte,
		idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getAmbientiEntiAbilitatiUnEnteListQueryIsOk() {
	long idUserIamCor = aLong();
	BigDecimal idEnte = aBigDecimal();
	helper.getAmbientiEntiAbilitatiUnEnteList(idUserIamCor, "AMMINISTRATORE", idEnte);
	assertTrue(true);
	helper.getAmbientiEntiAbilitatiUnEnteList(idUserIamCor, "CONSERVATORE", idEnte);
	assertTrue(true);
	helper.getAmbientiEntiAbilitatiUnEnteList(idUserIamCor, "GESTORE", idEnte);
	assertTrue(true);
	helper.getAmbientiEntiAbilitatiUnEnteList(idUserIamCor, "PRODUTTORE", idEnte);
	assertTrue(true);
    }

    @Test
    void getAmbietiDaUsrVLisEntiSiamCreaUserListQueryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	long idUserIamCor = aLong();

	helper.getAmbietiDaUsrVLisEntiSiamCreaUserList(idRichGestUser, idUserIamCor);
	assertTrue(true);
    }

    @Test
    void getAmbietiDaUsrVLisEntiSiamAppEnteListQueryIsOk() {
	long idUserIamCor = aLong();

	helper.getAmbietiDaUsrVLisEntiSiamAppEnteList(idUserIamCor);
	assertTrue(true);
    }

    @Test
    void getAmbietiDaUsrVLisEntiSiamPerAzioListQueryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	long idUserIamCor = aLong();

	helper.getAmbietiDaUsrVLisEntiSiamPerAzioList(idRichGestUser, idUserIamCor);
	assertTrue(true);
    }

    @Test
    void getEntiDaUsrVLisEntiSiamCreaUserListQueryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	long idUserIamCor = aLong();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getEntiDaUsrVLisEntiSiamCreaUserList(idRichGestUser, idUserIamCor,
		idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getEntiDaUsrVLisEntiSiamPerAzioListQueryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	long idUserIamCor = aLong();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getEntiDaUsrVLisEntiSiamPerAzioList(idRichGestUser, idUserIamCor,
		idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getEntiDaUsrVLisEntiSiamAppEnteListQueryIsOk() {
	long idUserIamCor = aLong();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getEntiDaUsrVLisEntiSiamAppEnteList(idUserIamCor, idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getAmbientiDaUsrVLisEntiModifAppEnteListQueryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	long idUserIamCor = aLong();
	BigDecimal idUserIamModif = aBigDecimal();

	helper.getAmbientiDaUsrVLisEntiModifAppEnteList(idRichGestUser, idUserIamCor,
		idUserIamModif);
	assertTrue(true);
    }

    @Test
    void getEntiDaUsrVLisEntiModifAppEnteListQueryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	long idUserIamCor = aLong();
	BigDecimal idUserIamModif = aBigDecimal();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getEntiDaUsrVLisEntiModifAppEnteList(idRichGestUser, idUserIamCor, idUserIamModif,
		idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAppartCollegEntiQueryIsOk() {
	BigDecimal idCollegEntiConvenz = aBigDecimal();

	helper.retrieveOrgAppartCollegEnti(idCollegEntiConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgAppartCollegEntiByIdEnteConvenzQueryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.retrieveOrgAppartCollegEntiByIdEnteConvenz(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getIdTipoUnitaDocQueryIsOk() {
	BigDecimal idStrut = aBigDecimal();
	String nmTipoUnitaDoc = aString();

	helper.getIdTipoUnitaDoc(idStrut, nmTipoUnitaDoc);
	assertTrue(true);
    }

    @Test
    void getTiStatoAccordoByIdQueryIsOk() {
	BigDecimal idAccordoEnte = aBigDecimal();
	try {
	    helper.getTiStatoAccordoById(idAccordoEnte);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void retrieveOrgTipoAccordoNoClasseEnteQueryIsOk() {
	helper.retrieveOrgTipoAccordoNoClasseEnte();
	assertTrue(true);
    }

    @Test
    void getTipiServizioDelTariffarioAnnualitaDelTipoAccordoQueryIsOk() {
	helper.getTipiServizioDelTariffarioAnnualitaDelTipoAccordo(BigDecimal.ZERO,
		BigDecimal.ZERO);
	assertTrue(true);
    }

    @Test
    void getOrgAaAccordoListQueryIsOk() {
	helper.getOrgAaAccordoList(BigDecimal.ZERO);
	assertTrue(true);
    }

    @Test
    void checkEsistenzaAnnualitaPerAccordoQueryIsOk() {
	helper.checkEsistenzaAnnualitaPerAccordo(BigDecimal.ZERO, BigDecimal.ZERO);
	assertTrue(true);
    }

    @Test
    void getOrgTariffaAccordoQueryIsOk() {
	helper.getOrgTariffaAccordo(BigDecimal.ZERO);
	assertTrue(true);
    }

    @Test
    void getOrgTariffaAaAccordo2QueryIsOk() {
	helper.getOrgTariffaAaAccordo(BigDecimal.ZERO, BigDecimal.ZERO);
	assertTrue(true);
    }

    @Test
    void getOrgTariffaAaAccordo1QueryIsOk() {
	helper.getOrgTariffaAaAccordo(BigDecimal.ZERO);
	assertTrue(true);
    }

    @Test
    void getTipiServizioAssociatiTariffarioPerAccordoDaCreareQueryIsOk() {
	final BigDecimal idTariffario = BigDecimal.ZERO;
	helper.getTipiServizioAssociatiTariffarioPerAccordoDaCreare(idTariffario);
	assertTrue(true);
    }

    @Test
    void getTipiServizioAssociatiTariffarioPerAnnualitaDaCreareQueryIsOk() {
	final BigDecimal idTariffario = BigDecimal.ZERO;
	helper.getTipiServizioAssociatiTariffarioPerAnnualitaDaCreare(idTariffario);
	assertTrue(true);
    }

    @Test
    void getTipiServizioDelTariffarioDelTipoAccordoGiaCreatoQueryIsOk() {
	final BigDecimal idTariffario = BigDecimal.ZERO;
	final BigDecimal idAccordoEnte = BigDecimal.ZERO;
	helper.getTipiServizioDelTariffarioDelTipoAccordoGiaCreato(idTariffario, idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void getTipiServizioDelTariffarioDelTipoAccordoAnnualitaGiaCreatoQueryIsOk() {
	final BigDecimal idTariffario = BigDecimal.ZERO;
	final BigDecimal idAccordoEnte = BigDecimal.ZERO;
	helper.getTipiServizioDelTariffarioDelTipoAccordoAnnualitaGiaCreato(idTariffario,
		idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffaAccordoByTipoServizioQueryIsOk() {
	final BigDecimal idTipoServizio = BigDecimal.ZERO;
	helper.retrieveOrgTariffaAccordoByTipoServizio(idTipoServizio);
	assertTrue(true);
    }

    @Test
    void retrieveOrgTariffaAnnualitaByTipoServizioQueryIsOk() {
	final BigDecimal idTipoServizio = BigDecimal.ZERO;
	helper.retrieveOrgTariffaAnnualitaByTipoServizio(idTipoServizio);
	assertTrue(true);
    }

    @Test
    void retrieveOrgVRicFattureList2QueryIsOk() {
	String nmEnteConvenz = "TEST_STRING";
	BigDecimal idTipoAccordo = BigDecimal.ZERO;
	BigDecimal idTipoServizio = BigDecimal.ZERO;
	BigDecimal aaFatturaDa = BigDecimal.ONE;
	BigDecimal aaFatturaA = BigDecimal.TEN;
	BigDecimal pgFatturaEnteDa = BigDecimal.ONE;
	BigDecimal pgFatturaEnteA = BigDecimal.TEN;
	String cdFattura = "TEST_STRING";
	String cdRegistroEmisFattura = "TEST_STRING";
	BigDecimal aaEmissFattura = BigDecimal.ZERO;
	String cdEmisFattura = "TEST_STRING";
	BigDecimal pgFatturaEmis = BigDecimal.ZERO;
	Date dtEmisFatturaDa = todayTs();
	Date dtEmisFatturaA = tomorrowTs();
	Set<String> tiStatoFatturaEnte = aSetOfString(2);

	helper.retrieveOrgVRicFattureList2(nmEnteConvenz, idTipoAccordo, idTipoServizio,
		aaFatturaDa, aaFatturaA, pgFatturaEnteDa, pgFatturaEnteA, cdFattura,
		cdRegistroEmisFattura, aaEmissFattura, cdEmisFattura, pgFatturaEmis,
		dtEmisFatturaDa, dtEmisFatturaA, tiStatoFatturaEnte);
	assertTrue(true);
    }

    @Test
    void getPgFatturaQueryIsOk() {
	helper.getPgFattura(BigDecimal.ZERO, BigDecimal.ONE);
	assertTrue(true);
    }

    @Test
    void getPrimoOrgVVisFatturaEnteQueryIsOk() {
	BigDecimal idFatturaEnte = BigDecimal.ZERO;
	try {
	    helper.getPrimoOrgVVisFatturaEnte(idFatturaEnte);
	    fail();
	} catch (Exception e) {
	    assertTrue(e.getMessage().contains("IllegalStateException"));
	}
    }

    @Test
    void getOrgEnteArkRifListQueryIsOk() {
	BigDecimal idEnteConvenz = BigDecimal.ZERO;
	helper.getOrgEnteArkRifList(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveOrgVRicFattureAccordiQueryIsOk() {
	BigDecimal idAccordoEnte = BigDecimal.ZERO;
	helper.retrieveOrgVRicFattureAccordi(idAccordoEnte);
	assertTrue(true);
    }

    @Test
    void getMinDateAnagraficaQueryIsOk() {
	BigDecimal idEnteConvenz = BigDecimal.ZERO;
	helper.getMinDateAnagrafica(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getMaxDateAnagraficaQueryIsOk() {
	BigDecimal idEnteConvenz = BigDecimal.ZERO;
	helper.getMaxDateAnagrafica(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getOrgStoEnteConvenzExcludeQueryIsOk() {
	BigDecimal idEnteConvenz = BigDecimal.ZERO;
	BigDecimal idStoEnteConvenzToExclude = BigDecimal.ZERO;
	helper.getOrgStoEnteConvenz(idEnteConvenz, idStoEnteConvenzToExclude);
	assertTrue(true);
    }
}
