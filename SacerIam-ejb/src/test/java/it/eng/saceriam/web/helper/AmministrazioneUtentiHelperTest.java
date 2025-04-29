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
package it.eng.saceriam.web.helper;

import static it.eng.ArquillianUtils.aBigDecimal;
import static it.eng.ArquillianUtils.aDateArray;
import static it.eng.ArquillianUtils.aListOfBigDecimal;
import static it.eng.ArquillianUtils.aListOfString;
import static it.eng.ArquillianUtils.aLong;
import static it.eng.ArquillianUtils.aSetOfBigDecimal;
import static it.eng.ArquillianUtils.aSetOfString;
import static it.eng.ArquillianUtils.aString;
import static it.eng.ArquillianUtils.aStringArray;
import static it.eng.ArquillianUtils.assertNoResultException;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static it.eng.ArquillianUtils.todayTs;
import static it.eng.ArquillianUtils.tomorrowTs;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.container.annotation.ArquillianTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;

import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.web.util.ApplEnum;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@ArquillianTest
public class AmministrazioneUtentiHelperTest {

    @EJB
    private AmministrazioneUtentiHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
	final JavaArchive archive = getSacerIamArchive(AmministrazioneUtentiHelper.class)
		.addClasses(AmministrazioneUtentiHelperTest.class,
			it.eng.saceriam.web.util.ApplEnum.class,
			it.eng.saceriam.web.util.ApplEnum.NmJob.class)
		.addPackages(true, "it.eng.parer.sacerlog.entity",
			"it.eng.parer.sacerlog.viewEntity");
	return archive;
    }

    @Test
    void getPrfRuoli_BigDecimal_queryIsOk() {
	BigDecimal idAppl = aBigDecimal();
	helper.getPrfRuoli(idAppl);
	assertTrue(true);
    }

    @Test
    void getPrfRuoliUtente_queryIsOk() {
	BigDecimal idAppl = aBigDecimal();
	String tipoEnte = aString();
	String tipoUtente = aString();

	helper.getPrfRuoliUtente(idAppl, tipoEnte, tipoUtente);
	assertTrue(true);
    }

    @Test
    void getPrfRuoli_String_queryIsOk() {
	String nmApplic = aString();

	helper.getPrfRuoli(nmApplic);
	assertTrue(true);
    }

    @Test
    void getRuoliDefaultUtenteApplic_queryIsOk() {
	String nmApplic = aString();
	Long idUserIam = aLong();

	helper.getRuoliDefaultUtenteApplic(nmApplic, idUserIam);
	assertTrue(true);
    }

    @Test
    void getPrfRuoli_0args_queryIsOk() {

	helper.getPrfRuoli();
	assertTrue(true);
    }

    @Test
    void getPrfRuoloById_queryIsOk() {
	BigDecimal idRuolo = aBigDecimal();

	helper.getPrfRuoloById(idRuolo);
	assertTrue(true);
    }

    @Test
    void getAplApplicList_queryIsOk() {

	helper.getAplApplicList();
	assertTrue(true);
    }

    @Test
    void getPrfUsoRuoloApplicList_queryIsOk() {
	Set<BigDecimal> idUsoRuoloApplic = aSetOfBigDecimal(2);

	helper.getPrfUsoRuoloApplicList(idUsoRuoloApplic);
	assertTrue(true);
    }

    @Test
    void getPrfDichAutorList_queryIsOk() {
	Set<BigDecimal> idUsoRuoloSet = aSetOfBigDecimal(2);
	String tiDichAutor = aString();
	BigDecimal idApplic = aBigDecimal();

	helper.getPrfDichAutorList(idUsoRuoloSet, tiDichAutor, idApplic);
	assertTrue(true);
    }

    @Test
    void hasServicesAuthorization_queryIsOk() {
	BigDecimal idRuolo = aBigDecimal();
	BigDecimal idApplic = aBigDecimal();

	helper.hasServicesAuthorization(idRuolo, idApplic);
	assertTrue(true);
    }

    @Test
    void getActionChilds_queryIsOk() {
	BigDecimal idPaginaWeb = aBigDecimal();

	helper.getActionChilds(idPaginaWeb);
	assertTrue(true);
    }

    @Test
    void getAplAzionePagina_queryIsOk() {
	BigDecimal idAzionePagina = aBigDecimal();

	helper.getAplAzionePagina(idAzionePagina);
	assertTrue(true);
    }

    @Test
    void getPagesList_queryIsOk() {
	BigDecimal idApplicazione = aBigDecimal();

	helper.getPagesList(idApplicazione);
	assertTrue(true);
    }

    @Test
    void getActionsList_queryIsOk() {
	BigDecimal idApplicazione = aBigDecimal();
	BigDecimal idPaginaWeb = aBigDecimal();

	helper.getActionsList(idApplicazione, idPaginaWeb);
	assertTrue(true);
    }

    @Test
    void getServicesList_queryIsOk() {
	BigDecimal idApplicazione = aBigDecimal();

	helper.getServicesList(idApplicazione);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilOrganizList_BigDecimal_BigDecimal_queryIsOk() {
	BigDecimal idUserIamCorrente = aBigDecimal();
	BigDecimal idApplic = aBigDecimal();

	helper.getUsrVAbilOrganizList(idUserIamCorrente, idApplic);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilOrganizList_5args_queryIsOk() {
	BigDecimal idUserIamCorrente = aBigDecimal();
	BigDecimal idApplic = aBigDecimal();
	String nmTipoOrganiz = aString();
	List<BigDecimal> idOrganizIamDaTogliere = aListOfBigDecimal(2);
	boolean excludeEntiConvenzionati = false;

	helper.getUsrVAbilOrganizList(idUserIamCorrente, idApplic, nmTipoOrganiz,
		idOrganizIamDaTogliere, excludeEntiConvenzionati);
	assertTrue(true);
    }

    @Test
    void getRuoliDefaultByApplic_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();

	helper.getRuoliDefaultByApplic(idApplic);
	assertTrue(true);
    }

    @Test
    void getRuoliDichByApplic_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();

	helper.getRuoliDichByApplic(idApplic);
	assertTrue(true);
    }

    @Test
    void getUsrVTreeOrganizIamNoLastLevelList_queryIsOk() {
	long idUserIam = aLong();
	BigDecimal idApplicazione = aBigDecimal();

	helper.getUsrVTreeOrganizIamNoLastLevelList(idUserIam, idApplicazione);
	assertTrue(true);
    }

    @Test
    void getUsrVTreeOrganizIamAllOrgChildList_queryIsOk() {
	long idUserIamCor = aLong();
	BigDecimal idApplic = aBigDecimal();
	BigDecimal idEnte = aBigDecimal();
	String flAbilOrganizAutom = aString();
	String flAbilOrganizEntiAutom = aString();
	BigDecimal idRichiesta = aBigDecimal();
	helper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor, idApplic, "AMMINISTRATORE",
		idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor, idApplic, "CONSERVATORE", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor, idApplic, "GESTORE", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor, idApplic, "PRODUTTORE", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor, idApplic, "ORGANO_VIGILANZA",
		idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor, idApplic, "FORNITORE_ESTERNO",
		idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
    }

    @Test
    void getUsrVTreeOrganizIamUnaOrgList_queryIsOk() {
	long idUserIamCor = aLong();
	BigDecimal idApplic = aBigDecimal();
	BigDecimal idEnte = aBigDecimal();
	String flAbilOrganizAutom = aString();
	String flAbilOrganizEntiAutom = aString();
	BigDecimal idRichiesta = aBigDecimal();
	helper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor, idApplic, "AMMINISTRATORE", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor, idApplic, "CONSERVATORE", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor, idApplic, "GESTORE", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor, idApplic, "PRODUTTORE", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor, idApplic, "ORGANO_VIGILANZA", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
	helper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor, idApplic, "FORNITORE_ESTERNO", idEnte,
		flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichiesta);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilOrganizNolastLivList_queryIsOk() {
	long idUserIam = aLong();
	BigDecimal idApplic = aBigDecimal();
	String dlPathIdOrganizIam = aString();

	helper.getUsrVAbilOrganizNolastLivList(idUserIam, idApplic, dlPathIdOrganizIam);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilOrganizLastLevelList_long_BigDecimal_queryIsOk() {
	long idUserIam = aLong();
	BigDecimal idApplicazione = aBigDecimal();

	helper.getUsrVAbilOrganizLastLevelList(idUserIam, idApplicazione);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilOrganizLastLevelList_3args_queryIsOk() {
	long idUserIam = aLong();
	BigDecimal idApplic = aBigDecimal();
	String dlPathIdOrganizIam = aString();

	helper.getUsrVAbilOrganizLastLevelList(idUserIam, idApplic, dlPathIdOrganizIam);
	assertTrue(true);
    }

    @Test
    void getUsrVLisUserList_queryIsOk() {
	AmministrazioneUtentiHelper.FiltriUtentiPlain filtri = (new AmministrazioneUtentiHelper.FiltriUtentiPlain(
		aSetOfBigDecimal(2), aLong(), aListOfBigDecimal(2), aString(), aString(), aString(),
		aString(), aBigDecimal(), aBigDecimal(), aString(), aString(), aListOfString(2),
		aString(), aString(), aBigDecimal(), aBigDecimal(), aString(), aBigDecimal(),
		aListOfBigDecimal(2), aBigDecimal(), aString(), todayTs(), tomorrowTs(),
		aBigDecimal(), aString(), aBigDecimal(), aString(), aBigDecimal(), aString()));
	helper.getUsrVLisUserList(filtri);
	assertTrue(true);
    }

    @Test
    void retrieveUsrVLisUserEnteConvenz_queryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	List<String> tiStatoUser = aListOfString(2);
	List<String> tipoUser = aListOfString(2);
	Set<BigDecimal> idUserIamEsclusi = aSetOfBigDecimal(2);

	helper.retrieveUsrVLisUserEnteConvenz(idEnteConvenz, tiStatoUser, tipoUser,
		idUserIamEsclusi);
	assertTrue(true);
    }

    @Test
    void retrieveUsrUserEnteSiamAppart_3args_queryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();
	List<String> tiStatoUser = aListOfString(2);
	List<String> tipoUser = aListOfString(2);

	helper.retrieveUsrUserEnteSiamAppart(idEnteSiam, tiStatoUser, tipoUser);
	assertTrue(true);
    }

    @Test
    void retrieveUsrUserEnteSiamAppart_4args_queryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();
	helper.retrieveUsrUserEnteSiamAppart(idEnteSiam,
		Collections.singletonMap("IN",
			Arrays.asList(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE,
				ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE,
				ConstOrgEnteSiam.TiEnteConvenz.GESTORE)),
		Collections.singletonMap("NOT IN",
			Collections.singletonList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
		Collections.singletonMap("IN",
			Collections.singletonList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

	helper.retrieveUsrUserEnteSiamAppart(idEnteSiam, null,
		Collections.singletonMap("NOT IN",
			Collections.singletonList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
		Collections.singletonMap("IN",
			Collections.singletonList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

	helper.retrieveUsrUserEnteSiamAppart(idEnteSiam,
		Collections.singletonMap("NOT IN",
			Collections.singletonList(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE)),
		Collections.singletonMap("NOT IN",
			Collections.singletonList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
		Collections.singletonMap("IN",
			Collections.singletonList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

	helper.retrieveUsrUserEnteSiamAppart(idEnteSiam,
		Collections.singletonMap("NOT IN",
			Collections.singletonList(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE)),
		Collections.singletonMap("NOT IN",
			Collections.singletonList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
		Collections.singletonMap("IN",
			Collections.singletonList(ApplEnum.TipoUser.PERSONA_FISICA.name())));
	assertTrue(true);
    }

    @Test
    void retrieveIdOrganizIamUserEnteSiamAppartList_queryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();
	List<String> tiStatoUser = aListOfString(2);
	List<String> tipoUser = aListOfString(2);

	helper.retrieveIdOrganizIamUserEnteSiamAppartList(idEnteSiam, tiStatoUser, tipoUser);
	assertTrue(true);
    }

    @Test
    void getPrfDichAutor_queryIsOk() {
	BigDecimal idObject = aBigDecimal();
	BigDecimal idObject2 = aBigDecimal();
	BigDecimal idPrfUsoRuoloApplic = aBigDecimal();
	for (ConstPrfDichAutor.TiScopoDichAutor tiScopoDichAutor : ConstPrfDichAutor.TiScopoDichAutor
		.values()) {
	    for (ConstPrfDichAutor.TiDichAutor tiDichAutor : ConstPrfDichAutor.TiDichAutor
		    .values()) {
		helper.getPrfDichAutor(tiScopoDichAutor.name(), tiDichAutor.name(), idObject,
			idObject2, idPrfUsoRuoloApplic);
		assertTrue(true);
	    }
	}
    }

    @Test
    void getAplApplicById_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();

	helper.getAplApplicById(idApplic);
	assertTrue(true);
    }

    @Test
    void getAplApplicByName_queryIsOk() {
	String nmApplic = aString();

	helper.getAplApplicByName(nmApplic);
	assertTrue(true);
    }

    @Test
    void getAplApplic_queryIsOk() {
	String name = aString();
	try {
	    helper.getAplApplicByName(name);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void getUsrUser_queryIsOk() {
	BigDecimal idUser = aBigDecimal();

	helper.getUsrUser(idUser);
	assertTrue(true);
    }

    @Test
    void getUsrUserByNmUserid_queryIsOk() {
	String nmUserid = aString();
	helper.getUsrUserByNmUserid(nmUserid);
	assertTrue(true);
    }

    @Test
    void getUsrIndIpUserList_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrIndIpUserList(idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrUsoUserApplicList_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrUsoUserApplicList(idUserIam);
	assertTrue(true);
    }

    @Test
    void getIdUsrUsoUserApplicList_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getIdUsrUsoUserApplicList(idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrUsoRuoloUserDefaultList_queryIsOk() {
	Set<BigDecimal> uuuaSet = aSetOfBigDecimal(2);

	helper.getUsrUsoRuoloUserDefaultList(uuuaSet);
	assertTrue(true);
    }

    @Test
    void getUsrDichAbilOrganizList_queryIsOk() {
	Set<BigDecimal> uuuaSet = aSetOfBigDecimal(2);

	helper.getUsrDichAbilOrganizList(uuuaSet);
	assertTrue(true);
    }

    @Test
    void getUsrDichAbilDatiList_queryIsOk() {
	Set<BigDecimal> uuuaSet = aSetOfBigDecimal(2);

	helper.getUsrDichAbilDatiList(uuuaSet);
	assertTrue(true);
    }

    @Test
    void checkUserExists_String_queryIsOk() {
	String userName = aString();

	helper.checkUserExists(userName);
	assertTrue(true);
    }

    @Test
    void checkUserExists_6args_queryIsOk() {
	String userName = aString();
	String nmCognomeUser = aString();
	String nmNomeUser = aString();
	String cdFisc = aString();
	String dsEmail = aString();
	String flContrIp = aString();

	helper.checkUserExists(userName, nmCognomeUser, nmNomeUser, cdFisc, dsEmail, flContrIp);
	assertTrue(true);
    }

    @Test
    void getOrganizIamChilds_queryIsOk() {
	BigDecimal idOrganizIam = aBigDecimal();

	helper.getOrganizIamChilds(idOrganizIam);
	assertTrue(true);
    }

    @Test
    void getUsrVTreeOrganizIam_queryIsOk() {
	BigDecimal idOrganizIam = aBigDecimal();
	try {
	    helper.getUsrVTreeOrganizIam(idOrganizIam);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void getAplClasseTipoDatoList_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();

	helper.getAplClasseTipoDatoList(idApplic);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilDatiList_queryIsOk() {
	long idUserIam = aLong();
	String nmClasseTipoDato = aString();
	BigDecimal idApplic = aBigDecimal();
	BigDecimal idOrganizIam = aBigDecimal();

	helper.getUsrVAbilDatiList(idUserIam, nmClasseTipoDato, idApplic, idOrganizIam);
	assertTrue(true);
    }

    @Test
    void getUsrTipoDatoIamList_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();
	String nmClasseTipoDato = aString();
	BigDecimal idOrganizIam = aBigDecimal();

	helper.getUsrTipoDatoIamList(idApplic, nmClasseTipoDato, idOrganizIam);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilDatiListByApplicClasseTipoDatoUser_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();
	String nmClasseTipoDato = aString();
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrVAbilDatiListByApplicClasseTipoDatoUser(idApplic, nmClasseTipoDato, idUserIam);
	assertTrue(true);
    }

    @Test
    void getRuoliAbilOrganizList_queryIsOk() {
	BigDecimal idDichAbilOrganiz = aBigDecimal();

	helper.getRuoliAbilOrganizList(idDichAbilOrganiz);
	assertTrue(true);
    }

    @Test
    void getUsrVLisUsoRuoloDichList_queryIsOk() {
	BigDecimal idDichAbilOrganiz = aBigDecimal();

	helper.getUsrVLisUsoRuoloDichList(idDichAbilOrganiz);
	assertTrue(true);
    }

    @Test
    void getUsrVVisDichAbil_queryIsOk() {
	BigDecimal idDichAbilOrg = aBigDecimal();

	helper.getUsrVVisDichAbil(idDichAbilOrg);
	assertTrue(true);
    }

    @Test
    void getUsrVLisSchedList_queryIsOk() {
	Date[] dateValidate = aDateArray(2);
	boolean soloSbloccoRepliche = false;
	for (ApplEnum.NmJob nmJob : ApplEnum.NmJob.values()) {
	    helper.getUsrVLisSchedList(nmJob, dateValidate, soloSbloccoRepliche);
	    assertTrue(true);
	}
    }

    @Test
    void getUsrVVisLastSched_queryIsOk() {
	for (ApplEnum.NmJob nmJob : ApplEnum.NmJob.values()) {
	    helper.getUsrVVisLastSched(nmJob);
	    assertTrue(true);
	}
    }

    @Test
    void getUsrVLisUserReplicList_queryIsOk() {
	Date dataValidata = todayTs();
	boolean isFromDettaglioUtente = false;
	String userId = aString();
	BigDecimal idApplic = aBigDecimal();
	String tiOperReplic = aString();
	List<String> tiStatoReplic = aListOfString(2);

	helper.getUsrVLisUserReplicList(dataValidata, isFromDettaglioUtente, userId, idApplic,
		tiOperReplic, tiStatoReplic);
	assertTrue(true);
    }

    @Test
    void hasErroriReplica_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.hasErroriReplica(idUserIam);
	assertTrue(true);
    }

    @Test
    void aggiornaReplicheInCorso_queryIsOk() {
	BigDecimal idLogJob = aBigDecimal();

	helper.aggiornaReplicheInCorso(idLogJob);
	assertTrue(true);
    }

    @Test
    void getUsrDichAbilEnteConvenz_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrDichAbilEnteConvenz(idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrDichAbilEnteConvenzUnEnteAttivi_queryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getUsrDichAbilEnteConvenzUnEnteAttivi(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void retrieveLogEventoLoginUserList_queryIsOk() {
	Date dtEventoDa = todayTs();
	Date dtEventoA = tomorrowTs();
	String nmUserid = aString();
	Set<String> tipiEventoSet = aSetOfString(2);
	String inclUtentiAutomi = aString();
	String nome = aString();
	String cognome = aString();
	String cf = aString();
	String email = aString();
	String idEsterno = aString();

	helper.retrieveLogVRicAccessiList(dtEventoDa, dtEventoA, nmUserid, tipiEventoSet,
		inclUtentiAutomi, nome, cognome, cf, email, idEsterno);
	assertTrue(true);
    }

    @Test
    void getUsrVLisStatoUserList_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrVLisStatoUserList(idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrVRicRichiesteList_queryIsOk() {
	BigDecimal idUserIamCor = aBigDecimal();
	String nmCognomeUserAppRich = aString();
	String nmNomeUserAppRich = aString();
	String nmUseridAppRich = aString();
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();
	BigDecimal idEnteSiam = aBigDecimal();
	String nmCognomeUserRich = aString();
	String nmNomeUserRich = aString();
	String nmUseridRich = aString();
	BigDecimal idOrganizIam = aBigDecimal();
	String cdRegistroRichGestUser = aString();
	BigDecimal aaRichGestUser = aBigDecimal();
	String cdKeyRichGestUser = aString();
	String cdRichGestUser = aString();
	String tiStatoRichGestUser = aString();

	helper.getUsrVRicRichiesteList(idUserIamCor, nmCognomeUserAppRich, nmNomeUserAppRich,
		nmUseridAppRich, idAmbienteEnteConvenz, idEnteSiam, nmCognomeUserRich,
		nmNomeUserRich, nmUseridRich, idOrganizIam, cdRegistroRichGestUser, aaRichGestUser,
		cdKeyRichGestUser, cdRichGestUser, tiStatoRichGestUser);
	assertTrue(true);
    }

    @Test
    void getUsrAppartUserRichList_BigDecimal_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();

	helper.getUsrAppartUserRichList(idRichGestUser);
	assertTrue(true);
    }

    @Test
    void getUsrAppartUserRichList_3args_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	String tiAzioneRich = aString();
	String flAzioneRichEvasa = aString();

	helper.getUsrAppartUserRichList(idRichGestUser, tiAzioneRich, flAzioneRichEvasa);
	assertTrue(true);
    }

    @Test
    void getStatoCorrenteUser_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getStatoCorrenteUser(idUserIam);
	assertTrue(true);
    }

    @Test
    void existsAzioneUtente_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	BigDecimal idAppartUserRich = aBigDecimal();
	List<String> tiAzioneRich = aListOfString(2);
	BigDecimal idUserIam = aBigDecimal();
	String nmCognomeUser = aString();
	String nmNomeUser = aString();
	String nmUserid = aString();
	String flAzioneRichEvasa = aString();

	helper.existsAzioneUtente(idRichGestUser, idAppartUserRich, tiAzioneRich, idUserIam,
		nmCognomeUser, nmNomeUser, nmUserid, flAzioneRichEvasa);
	assertTrue(true);
    }

    @Test
    void getAzioneUtente_7args_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	List<String> tiAzioneRich = aListOfString(2);
	BigDecimal idUserIam = aBigDecimal();
	String nmCognomeUser = aString();
	String nmNomeUser = aString();
	String nmUserid = aString();
	String flAzioneRichEvasa = aString();

	helper.getAzioneUtente(idRichGestUser, tiAzioneRich, idUserIam, nmCognomeUser, nmNomeUser,
		nmUserid, flAzioneRichEvasa);
	assertTrue(true);
    }

    @Test
    void getAzioneUtente_3args_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	List<String> tiAzioneRich = aListOfString(2);
	BigDecimal idUserIam = aBigDecimal();

	helper.getAzioneUtente(idRichGestUser, tiAzioneRich, idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrRichGestUserByOrganizzazioneAndUd_queryIsOk() {
	BigDecimal idOrganizIam = aBigDecimal();
	String cdRegistroRichGestUser = aString();
	BigDecimal aaRichGestUser = aBigDecimal();
	String cdKeyRichGestUser = aString();
	BigDecimal idRichGestUserExcluded = aBigDecimal();

	helper.getUsrRichGestUserByOrganizzazioneAndUd(idOrganizIam, cdRegistroRichGestUser,
		aaRichGestUser, cdKeyRichGestUser, idRichGestUserExcluded);
	assertTrue(true);
    }

    @Test
    void getUsrRichGestUserByRichEnte_queryIsOk() {
	String cdRichGestUser = aString();
	BigDecimal idEnteConvenz = aBigDecimal();
	BigDecimal idRichGestUserExcluded = aBigDecimal();

	helper.getUsrRichGestUserByRichEnte(cdRichGestUser, idEnteConvenz, idRichGestUserExcluded);
	assertTrue(true);
    }

    @Test
    void getRichiestaDaEvadere_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	String tiAzioneRich = aString();

	helper.getRichiestaDaEvadere(idUserIam, tiAzioneRich);
	assertTrue(true);
    }

    @Test
    void getRichiestaDaEseguire_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	String tiAzioneRich = aString();

	helper.getRichiestaDaEseguire(idUserIam, tiAzioneRich);
	assertTrue(true);
    }

    @Test
    void existAzioni_queryIsOk() {
	long idRichGestUser = aLong();
	String flAzioneRichEvasa = aString();

	helper.existAzioni(idRichGestUser, flAzioneRichEvasa);
	assertTrue(true);
    }

    @Test
    void getUsrVAbilOrganiz_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idOrganizApplic = aBigDecimal();

	helper.getUsrVAbilOrganiz(idUserIam, idOrganizApplic);
	assertTrue(true);
    }

    @Test
    void getSusrRichGestUserCd_queryIsOk() {

	helper.getSusrRichGestUserCd();
	assertTrue(true);
    }

    @Test
    void getUsrAppartUserRichByUser_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	String nmUserid = aString();
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idAppartUserRichExcluded = aBigDecimal();

	helper.getUsrAppartUserRichByUser(idRichGestUser, nmUserid, idUserIam,
		idAppartUserRichExcluded);
	assertTrue(true);
    }

    @Test
    void getOtherUsrAppartUserRichByUser_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	String nmUserid = aString();
	BigDecimal idUserIam = aBigDecimal();

	helper.getOtherUsrAppartUserRichByUser(idRichGestUser, nmUserid, idUserIam);
	assertTrue(true);
    }

    @Test
    void existsUtenteByUseridAndTipo_queryIsOk() {
	String nmUserid = aString();
	String[] tipoUser = aStringArray(2);

	helper.existsUtenteByUseridAndTipo(nmUserid, tipoUser);
	assertTrue(true);
    }

    @Test
    void getUsrRicGestUserByUser_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	List<String> tiAzioneRich = aListOfString(2);

	helper.getUsrRicGestUserByUser(idUserIam, tiAzioneRich);
	assertTrue(true);
    }

    @Test
    void getUsrVVisLastRichGestUser_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrVVisLastRichGestUser(idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrVLisEntiSiamPerAzioList_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	long idUserIamCor = aLong();

	helper.getUsrVLisEntiSiamPerAzioList(idRichGestUser, idUserIamCor);
	assertTrue(true);
    }

    @Test
    void getUsrVLisEntiSiamAppEnteList_queryIsOk() {
	long idUserIamCor = aLong();

	helper.getUsrVLisEntiSiamAppEnteList(idUserIamCor);
	assertTrue(true);
    }

    @Test
    void getUsrVLisAbilOrganizList_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrVLisAbilOrganizList(idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrVLisAbilEnteList_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrVLisAbilEnteList(idUserIam);
	assertTrue(true);
    }

    @Test
    void getIdOrganizIamByParam_queryIsOk() {
	String nmEnteUnitaDoc = aString();
	String nmStrutUnitaDoc = aString();

	helper.getIdOrganizIamByParam(nmEnteUnitaDoc, nmStrutUnitaDoc);
	assertTrue(true);
    }

    @Test
    void existsRichiestaUtente_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.existsRichiestaUtente(idUserIam);
	assertTrue(true);
    }

    @Test
    void existsRichiestaUtenteAzione_BigDecimal_String_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	String azione = aString();

	helper.existsRichiestaUtenteAzione(idUserIam, azione);
	assertTrue(true);
    }

    @Test
    void existsRichiestaUtenteAzione_3args_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();
	String azione = aString();

	helper.existsRichiestaUtenteAzione(idRichGestUser, idUserIam, azione);
	assertTrue(true);
    }

    @Test
    void getRichiestaUtenteAzione_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();
	String azione = aString();

	helper.getRichiestaUtenteAzione(idRichGestUser, idUserIam, azione);
	assertTrue(true);
    }

    @Test
    void existsRichiestaUtenteDiversaAzione_BigDecimal_String_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	String azione = aString();

	helper.existsRichiestaUtenteDiversaAzione(idUserIam, azione);
	assertTrue(true);
    }

    @Test
    void existsRichiestaUtenteDiversaAzione_3args_queryIsOk() {
	BigDecimal idRichGestUser = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();
	String azione = aString();

	helper.existsRichiestaUtenteDiversaAzione(idRichGestUser, idUserIam, azione);
	assertTrue(true);
    }

    @Test
    void getOrgVChkRefEnte_queryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();
	BigDecimal idUserIam = aBigDecimal();
	try {
	    helper.getOrgVChkRefEnte(idEnteSiam, idUserIam);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void retrieveUsrUserEnteSup_queryIsOk() {
	BigDecimal idEnteSiam = aBigDecimal();

	helper.retrieveUsrUserEnteSup(idEnteSiam,
		Collections.singletonMap("IN",
			Collections.singletonList(
				ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO)),
		Collections.singletonMap("NOT IN",
			Collections.singletonList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
		Collections.singletonMap("IN",
			Collections.singletonList(ApplEnum.TipoUser.PERSONA_FISICA.name())));
	assertTrue(true);
    }

    @Test
    void getEntryMenuList_queryIsOk() {
	helper.getEntryMenuList(BigDecimal.ONE, true);
	assertTrue(true);
	helper.getEntryMenuList(BigDecimal.ONE, false);
	assertTrue(true);
    }

    @Test
    void getEntryMenuChilds_queryIsOk() {
	helper.getEntryMenuChilds(BigDecimal.ONE);
	assertTrue(true);
    }

    @Test
    void getEntryMenuParentsList_queryIsOk() {
	helper.getEntryMenuParentsList(BigDecimal.ONE);
	assertTrue(true);
    }

    @Test
    void getUsrOrganizIamChilds_queryIsOk() {
	helper.getUsrOrganizIamChilds(BigDecimal.ONE);
	assertTrue(true);
    }
}
