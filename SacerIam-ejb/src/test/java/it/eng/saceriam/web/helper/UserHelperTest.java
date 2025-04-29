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
import static it.eng.ArquillianUtils.aFlag;
import static it.eng.ArquillianUtils.aListOfString;
import static it.eng.ArquillianUtils.aLong;
import static it.eng.ArquillianUtils.aSetOfBigDecimal;
import static it.eng.ArquillianUtils.aString;
import static it.eng.ArquillianUtils.assertNoResultException;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static it.eng.ArquillianUtils.todayTs;
import static it.eng.ArquillianUtils.tomorrowTs;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import it.eng.parer.sacerlog.ejb.util.PremisEnums;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.AplTipoEvento;
import it.eng.saceriam.entity.AplTipoOrganiz;
import it.eng.saceriam.entity.OrgAppartCollegEnti;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.PrfDichAutor;
import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.saceriam.entity.UsrDichAbilOrganiz;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.entity.constraint.ConstPrfRuolo;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiTableBean;
import it.eng.saceriam.viewEntity.UsrVCreaAbilDati;
import it.eng.saceriam.viewEntity.UsrVCreaAbilDatiId;
import it.eng.saceriam.web.util.ApplEnum;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@ArquillianTest
public class UserHelperTest {
    @EJB
    private UserHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
	final JavaArchive archive = getSacerIamArchive(UserHelper.class)
		.addClasses(it.eng.saceriam.ws.ejb.WsIdpLogger.class,
			it.eng.saceriam.web.util.ApplEnum.TiOperReplic.class,
			it.eng.saceriam.helper.ParamHelper.class,
			it.eng.saceriam.common.Constants.TipoIamVGetValAppart.class,
			it.eng.parer.sacerlog.ejb.common.AppServerInstance.class,
			it.eng.parer.sacerlog.ejb.common.helper.ParamApplicHelper.class,
			it.eng.parer.idpjaas.logutils.LogDto.class,
			it.eng.parer.idpjaas.logutils.IdpLogger.EsitiLog.class,
			it.eng.saceriam.web.util.ApplEnum.class,
			it.eng.saceriam.common.Constants.class,
			it.eng.parer.sacerlog.ejb.helper.SacerLogHelper.class,
			it.eng.saceriam.web.helper.dto.IamVGetValParamDto.class,
			it.eng.parer.sacerlog.ejb.util.PremisEnums.TipoClasseEvento.class,
			UserHelperTest.class)
		.addPackages(true, "org.apache.commons.lang3.mutable",
			"it.eng.parer.sacerlog.common")
		.addPackages(true, "it.eng.parer.sacerlog.entity",
			"it.eng.parer.sacerlog.viewEntity");
	return archive;
    }

    @Test
    void findUserById_queryIsOk() {
	long idUtente = aLong();
	try {
	    helper.findUserById(idUtente);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void findUserByName_queryIsOk() {
	String username = aString();
	try {
	    helper.findUserByName(username);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void findByCodiceFiscale_queryIsOk() {
	String codiceFiscale = aString();

	helper.findByCodiceFiscale(codiceFiscale);
	assertTrue(true);
    }

    @Test
    void getUsrUserByNmUserid_queryIsOk() {
	String nmUserid = aString();

	helper.getUsrUserByNmUserid(nmUserid);
	assertTrue(true);
    }

    @Test
    void resetPwd_4args_queryIsOk() {
	long idUtente = aLong();
	String randomPwd = aString();
	String salt = aString();
	Date scad = todayTs();
	try {
	    helper.resetPwd(idUtente, randomPwd, salt, scad);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    @Disabled("operazione da evitare in automatico")
    void resetPwd_3args_queryIsOk() {
	long idUtente = aLong();
	String randomPwd = aString();
	String salt = aString();
	try {
	    helper.resetPwd(idUtente, randomPwd, salt);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    private UsrUser mockUser() {
	UsrUser user = new UsrUser();
	user.setIdUserIam(aLong());
	return user;
    }

    @Test
    void getUserMenu_queryIsOk() {
	List<PrfRuolo> ruoli = new ArrayList<>(1);
	ruoli.add(mockPrfRuolo());

	helper.getUserMenu(ruoli);
	assertTrue(true);
    }

    private PrfRuolo mockPrfRuolo() {
	final PrfRuolo prfRuolo = new PrfRuolo();
	prfRuolo.setDsEsitoRichAllineaRuoli_1(aString());
	prfRuolo.setDsEsitoRichAllineaRuoli_2(aString());
	prfRuolo.setDsMsgAllineamentoParz(aString());
	prfRuolo.setDsRuolo(aString());
	prfRuolo.setFlAllineamentoInCorso(aFlag());
	prfRuolo.setIdRuolo(aLong());
	prfRuolo.setNmRuolo(aString());
	prfRuolo.setPrfAllineaRuolos(new ArrayList<>(0));
	prfRuolo.setPrfRuoloCategorias(new ArrayList<>(0));
	prfRuolo.setPrfUsoRuoloApplics(new ArrayList<>(0));
	prfRuolo.setTiRuolo(ConstPrfRuolo.TiRuolo.PERSONA_FISICA.name());
	prfRuolo.setTiStatoRichAllineaRuoli_1(
		ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO.name());
	prfRuolo.setTiStatoRichAllineaRuoli_2(
		ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_PARZIALE.name());
	prfRuolo.setUsrUsoRuoloDiches(new ArrayList<>(0));
	prfRuolo.setUsrUsoRuoloUserDefaults(new ArrayList<>(0));
	return prfRuolo;
    }

    @Test
    void getUserPages_queryIsOk() {
	List<PrfRuolo> ruoli = new ArrayList<>(1);
	ruoli.add(mockPrfRuolo());
	helper.getUserPages(ruoli);
	assertTrue(true);
    }

    @Test
    void getUserMenuE_queryIsOk() {
	List<PrfRuolo> ruoli = new ArrayList<>(1);
	ruoli.add(mockPrfRuolo());

	helper.getUserMenuE(ruoli);
	assertTrue(true);
    }

    @Test
    void getUserActions_queryIsOk() {
	List<PrfRuolo> ruoli = new ArrayList<>(1);
	ruoli.add(mockPrfRuolo());
	long idPagina = aLong();

	helper.getUserActions(ruoli, idPagina);
	assertTrue(true);
    }

    @Test
    void getAplApplic_String_queryIsOk() {
	String name = aString();
	try {
	    helper.getAplApplic(name);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void getAplApplic_BigDecimal_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();

	helper.getAplApplic(idApplic);
	assertTrue(true);
    }

    @Test
    void getUsrUsoUserApplicFiltrate_queryIsOk() {
	List<UsrUsoUserApplic> usrUsoUserApplicList = new ArrayList<>();
	usrUsoUserApplicList.add(mockUsrUsoUserApplic());
	helper.getUsrUsoUserApplicFiltrate(usrUsoUserApplicList);
	assertTrue(true);
    }

    @Test
    void getAplApplicFiltrate_queryIsOk() {
	Set<BigDecimal> idApplicSet = aSetOfBigDecimal(2);

	helper.getAplApplicFiltrate(idApplicSet);
	assertTrue(true);
    }

    @Test
    void getAplApplicConOrganizzazioni_queryIsOk() {
	Set<BigDecimal> idApplicSet = aSetOfBigDecimal(2);

	helper.getAplApplicConOrganizzazioni(idApplicSet);
	assertTrue(true);
    }

    @Test
    void isApplicConsentita_queryIsOk() {
	String nmApplic = aString();

	helper.isApplicConsentita(nmApplic);
	assertTrue(true);
    }

    @Test
    void getAplEntryMenu_3args_queryIsOk() throws Exception {
	String menuName = aString();
	String menuDesc = aString();
	String applName = aString();

	helper.getAplEntryMenu(menuName, menuDesc, applName);
	assertTrue(true);
    }

    @Test
    void getAplEntryMenu_String_String_queryIsOk() throws Exception {
	String menuName = aString();
	String applName = aString();

	helper.getAplEntryMenu(menuName, applName);
	assertTrue(true);
    }

    @Test
    void getAplEntryMenu_BigDecimal_queryIsOk() {
	BigDecimal idEntryMenu = aBigDecimal();

	helper.getAplEntryMenu(idEntryMenu);
	assertTrue(true);
    }

    @Test
    void getAplAzionePagina_AplPaginaWeb_queryIsOk() {
	AplPaginaWeb aplPaginaWeb = mockAplPaginaWeb();

	helper.getAplAzionePagina(aplPaginaWeb);
	assertTrue(true);
    }

    private AplPaginaWeb mockAplPaginaWeb() {
	AplPaginaWeb aplPaginaWeb = new AplPaginaWeb();
	aplPaginaWeb.setAplApplic(mockAplApplic());
	aplPaginaWeb.setAplAzionePaginas(new ArrayList<>(0));
	aplPaginaWeb.setDsPaginaWeb(aString());
	aplPaginaWeb.setIdPaginaWeb(aLong());
	aplPaginaWeb.setNmPaginaWeb(aString());
	aplPaginaWeb.setPrfAutors(new ArrayList<>());
	aplPaginaWeb.setPrfDichAutors(new ArrayList<>());
	aplPaginaWeb.setTiHelpOnLine(Constants.TiHelpOnLine.HELP_PAGINA.name());
	return aplPaginaWeb;
    }

    @Test
    void getAplPaginaWeb_BigDecimal_queryIsOk() {
	BigDecimal idPaginaWeb = aBigDecimal();

	helper.getAplPaginaWeb(idPaginaWeb);
	assertTrue(true);
    }

    @Test
    void getAplPaginaWeb_String_String_queryIsOk() throws Exception {
	String nomePagina = aString();
	String nomeApplicazione = aString();

	helper.getAplPaginaWeb(nomePagina, nomeApplicazione);
	assertTrue(true);
    }

    private AplEntryMenu mockAplEntryMenu() {
	AplEntryMenu entryMenu = new AplEntryMenu();
	entryMenu.setAplApplic(mockAplApplic());
	entryMenu.setAplEntryMenus(new ArrayList<>(0));
	entryMenu.setAplHelpOnLines(new ArrayList<>(0));
	entryMenu.setAplPaginaWebs(new ArrayList<>(0));
	entryMenu.setDlLinkEntryMenu(aString());
	entryMenu.setDsEntryMenu(aString());
	entryMenu.setEntryMenuPadre(null);
	entryMenu.setIdEntryMenu(aLong());
	entryMenu.setNiLivelloEntryMenu(aBigDecimal());
	entryMenu.setNiOrdEntryMenu(aBigDecimal());
	entryMenu.setNmEntryMenu(aString());
	entryMenu.setPrfAutors(new ArrayList<>());
	entryMenu.setPrfDichAutorsFoglia(new ArrayList<>());
	entryMenu.setPrfDichAutorsPadre(new ArrayList<>());
	return entryMenu;
    }

    @Test
    void getAplAzionePagina_3args_queryIsOk() throws Exception {
	String nomeAzione = aString();
	String nomePagina = aString();
	String nomeApplicazione = aString();

	helper.getAplAzionePagina(nomeAzione, nomePagina, nomeApplicazione);
	assertTrue(true);
    }

    @Test
    void getAplAzionePagina_BigDecimal_queryIsOk() {
	BigDecimal idAzionePagina = aBigDecimal();

	helper.getAplAzionePagina(idAzionePagina);
	assertTrue(true);
    }

    private AplAzionePagina mockAplAzionePagina() {
	AplAzionePagina action = new AplAzionePagina();
	action.setAplPaginaWeb(mockAplPaginaWeb());
	action.setAplTipoEvento(mockAplTipoEvento());
	action.setDsAzionePagina(aString());
	action.setIdAzionePagina(aLong());
	action.setNmAzionePagina(aString());
	action.setPrfAutors(new ArrayList<>());
	action.setPrfDichAutors(new ArrayList<>());
	return action;
    }

    private AplTipoEvento mockAplTipoEvento() {
	final AplTipoEvento aplTipoEvento = new AplTipoEvento();
	aplTipoEvento.setAplApplic(mockAplApplic());
	aplTipoEvento.setAplAzioneCompSws(new ArrayList<>());
	aplTipoEvento.setAplAzionePaginas(new ArrayList<>());
	aplTipoEvento.setAplTipoEventoOggettoTrigs(new ArrayList<>());
	aplTipoEvento.setIdTipoEvento(aLong());
	aplTipoEvento.setNmTipoEvento(aString());
	aplTipoEvento.setTipoClasseEvento(PremisEnums.TipoClasseEvento.INSERIMENTO.name());
	aplTipoEvento
		.setTipoClassePremisEvento(PremisEnums.TipoClasseEvento.VISUALIZZAZIONE.name());
	return aplTipoEvento;
    }

    @Test
    void getActionsNotInCsv_queryIsOk() {
	String nomeAppl = aString();
	String nomePag = aString();
	List<String> pageActions = aListOfString(2);

	helper.getActionsNotInCsv(nomeAppl, nomePag, pageActions);
	assertTrue(true);
    }

    @Test
    void getPagesNotInCsv_queryIsOk() {
	String nomeAppl = aString();
	List<String> dati = aListOfString(2);

	helper.getPagesNotInCsv(nomeAppl, dati);
	assertTrue(true);
    }

    @Test
    void getAplServizioWeb_String_String_queryIsOk() throws Exception {
	String nomeServizio = aString();
	String nomeApplicazione = aString();

	helper.getAplServizioWeb(nomeServizio, nomeApplicazione);
	assertTrue(true);
    }

    @Test
    void getAplServizioWeb_BigDecimal_queryIsOk() {
	BigDecimal idServizioWeb = aBigDecimal();

	helper.getAplServizioWeb(idServizioWeb);
	assertTrue(true);
    }

    private AplServizioWeb mockAplServizioWeb() {
	AplServizioWeb service = new AplServizioWeb();
	service.setAplApplic(mockAplApplic());
	service.setDsServizioWeb(aString());
	service.setIdServizioWeb(aLong());
	service.setNmServizioWeb(aString());
	service.setPrfAutors(new ArrayList<>(0));
	service.setPrfDichAutors(new ArrayList<>(0));
	return service;
    }

    @Test
    void getListAplEntryMenu_queryIsOk() {
	String applName = aString();

	helper.getListAplEntryMenu(applName);
	assertTrue(true);
    }

    @Test
    void getListAplEntryMenuUltimoLivello_String_queryIsOk() {
	String applName = aString();

	helper.getListAplEntryMenuUltimoLivello(applName);
	assertTrue(true);
    }

    @Test
    void getListAplEntryMenuUltimoLivello_BigDecimal_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();

	helper.getListAplEntryMenuUltimoLivello(idApplic);
	assertTrue(true);
    }

    @Test
    void getListAplPaginaWeb_String_queryIsOk() {
	String applName = aString();

	helper.getListAplPaginaWeb(applName);
	assertTrue(true);
    }

    @Test
    void getListAplPaginaWeb_String_String_queryIsOk() {
	String applName = aString();
	String tiHelpOnLine = aString();

	helper.getListAplPaginaWeb(applName, tiHelpOnLine);
	assertTrue(true);
    }

    @Test
    void getListAplPaginaWeb_BigDecimal_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();

	helper.getListAplPaginaWeb(idApplic);
	assertTrue(true);
    }

    @Test
    void getListAplPaginaWeb_BigDecimal_String_queryIsOk() {
	BigDecimal idApplic = aBigDecimal();
	String tiHelpOnLine = aString();

	helper.getListAplPaginaWeb(idApplic, tiHelpOnLine);
	assertTrue(true);
    }

    @Test
    void getListAplServizioWeb_queryIsOk() {
	String applName = aString();

	helper.getListAplServizioWeb(applName);
	assertTrue(true);
    }

    @Test
    void getListAplAzionePagina_queryIsOk() {
	String applName = aString();

	helper.getListAplAzionePagina(applName);
	assertTrue(true);
    }

    @Test
    void getPrfUsoRuoloApplic_queryIsOk() {
	String applName = aString();

	helper.getPrfUsoRuoloApplic(applName);
	assertTrue(true);
    }

    private PrfUsoRuoloApplic mockPrfUsoRuoloApplic() {
	PrfUsoRuoloApplic ruolo = new PrfUsoRuoloApplic();
	ruolo.setAplApplic(mockAplApplic());
	ruolo.setIdUsoRuoloApplic(aLong());
	ruolo.setPrfAutors(new ArrayList<>(0));
	ruolo.setPrfDichAutors(new ArrayList<>(0));
	ruolo.setPrfRuolo(mockPrfRuolo());
	return ruolo;
    }

    @Test
    void getDichAutor_queryIsOk() {
	PrfUsoRuoloApplic ruolo = mockPrfUsoRuoloApplic();
	String tiDichAutor = aString();
	String tiScopoDichAutor = aString();

	helper.getDichAutor(ruolo, tiDichAutor, tiScopoDichAutor);
	assertTrue(true);
    }

    private PrfDichAutor mockPrfDichAutor() {
	final PrfDichAutor prfDichAutor = new PrfDichAutor();
	prfDichAutor.setAplAzionePagina(mockAplAzionePagina());
	prfDichAutor.setAplEntryMenuFoglia(mockAplEntryMenu());
	prfDichAutor.setAplEntryMenuPadre(mockAplEntryMenu());
	prfDichAutor.setAplPaginaWeb(mockAplPaginaWeb());
	prfDichAutor.setAplServizioWeb(mockAplServizioWeb());
	prfDichAutor.setIdDichAutor(aLong());
	prfDichAutor.setPrfUsoRuoloApplic(mockPrfUsoRuoloApplic());
	prfDichAutor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
	prfDichAutor
		.setTiScopoDichAutor(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
	return prfDichAutor;
    }

    @Test
    void isAuthorizedAplPaginaWeb_queryIsOk() {
	PrfUsoRuoloApplic ruolo = mockPrfUsoRuoloApplic();
	String dichAutor = aString();
	AplPaginaWeb pagina = mockAplPaginaWeb();

	helper.isAuthorizedAplPaginaWeb(ruolo, dichAutor, pagina);
	assertTrue(true);
    }

    @Test
    void isWsEnabled_queryIsOk() {
	List<PrfRuolo> ruoli = new ArrayList<>(1);
	ruoli.add(mockPrfRuolo());
	String servizioWeb = aString();

	helper.isWsEnabled(ruoli, servizioWeb);
	assertTrue(true);
    }

    @Test
    void getRuoloById_queryIsOk() {
	BigDecimal idRuolo = aBigDecimal();

	helper.getRuoloById(idRuolo);
	assertTrue(true);
    }

    @Test
    void getDichAutorById_queryIsOk() {
	BigDecimal idDichAutor = aBigDecimal();

	helper.getDichAutorById(idDichAutor);
	assertTrue(true);
    }

    @Test
    void getPrfUsoRuoloApplicById_queryIsOk() {
	BigDecimal idUsoRuoloApplic = aBigDecimal();

	helper.getPrfUsoRuoloApplicById(idUsoRuoloApplic);
	assertTrue(true);
    }

    @Test
    void getUsrDichAbilOrganiz_queryIsOk() {
	BigDecimal idDichAbilOrganiz = aBigDecimal();

	helper.getUsrDichAbilOrganiz(idDichAbilOrganiz);
	assertTrue(true);
    }

    @Test
    void getUsrDichAbilDati_queryIsOk() {
	BigDecimal idDichAbilDati = aBigDecimal();

	helper.getUsrDichAbilDati(idDichAbilDati);
	assertTrue(true);
    }

    @Test
    void getLogAgenteByNmAgente_queryIsOk() {
	String nmAgente = aString();

	helper.getLogAgenteByNmAgente(nmAgente);
	assertTrue(true);
    }

    @Test
    void getIncoherenceOrganiz_queryIsOk() {
	long idUserIam = aLong();

	helper.getIncoherenceOrganiz(idUserIam);
	assertTrue(true);
    }

    @Test
    void getUsrIndIpUserById_queryIsOk() {
	BigDecimal idIndIpUser = aBigDecimal();

	helper.getUsrIndIpUserById(idIndIpUser);
	assertTrue(true);
    }

    @Test
    void getUsrUsoUserApplicById_queryIsOk() {
	BigDecimal idUsoUsrApplic = aBigDecimal();

	helper.getUsrUsoUserApplicById(idUsoUsrApplic);
	assertTrue(true);
    }

    @Test
    void getUsrUsoUserApplic_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.getUsrUsoUserApplic(idUserIam);
	assertTrue(true);
    }

    @Test
    void getAllUsrUsoUserApplics_queryIsOk() {

	helper.getAllUsrUsoUserApplics();
	assertTrue(true);
    }

    @Test
    void checkAplApplicURL_queryIsOk() {
	String nmApplic = aString();
	boolean hasBeenModified = false;
	try {
	    helper.checkAplApplicURL(nmApplic, hasBeenModified);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    @Disabled("Servono parametri reali")
    void registraLogUserDaReplic_3args_1_queryIsOk() {
	UsrUser user = mockUser();
	AplApplic applic = mockAplApplic();
	for (ApplEnum.TiOperReplic oper : ApplEnum.TiOperReplic.values()) {
	    helper.registraLogUserDaReplic(user, applic, oper);
	    assertTrue(true);
	}
    }

    @Test
    @Disabled("Servono parametri reali")
    void registraLogUserDaReplic_3args_2_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();
	BigDecimal idApplic = aBigDecimal();
	for (ApplEnum.TiOperReplic oper : ApplEnum.TiOperReplic.values()) {
	    helper.registraLogUserDaReplic(idUserIam, idApplic, oper);
	    assertTrue(true);
	}
    }

    @Test
    void registraLogUserDaReplic_BigDecimal_AplApplic_queryIsOk() {
	BigDecimal idRuolo = aBigDecimal();
	AplApplic applic = mockAplApplic();
	helper.registraLogUserDaReplic(idRuolo, applic);
	assertTrue(true);
    }

    @Test
    void getUsrOrganizIamById_queryIsOk() {
	BigDecimal idOrganizIam = aBigDecimal();

	helper.getUsrOrganizIamById(idOrganizIam);
	assertTrue(true);
    }

    @Test
    void getAplClasseTipoDatoById_queryIsOk() {
	BigDecimal idClasseTipoDato = aBigDecimal();

	helper.getAplClasseTipoDatoById(idClasseTipoDato);
	assertTrue(true);
    }

    @Test
    void getUsrTipoDatoIamById_queryIsOk() {
	BigDecimal idUserTipoDato = aBigDecimal();

	helper.getUsrTipoDatoIamById(idUserTipoDato);
	assertTrue(true);
    }

    @Test
    void getUsrUsoRuoloUserDefault_queryIsOk() {
	BigDecimal idUsoRuoloUserDefaul = aBigDecimal();

	helper.getUsrUsoRuoloUserDefault(idUsoRuoloUserDefaul);
	assertTrue(true);
    }

    @Test
    void getUsrVCheckRuoloDefaultList_queryIsOk() {
	long idUserCorrente = aLong();
	long idRuolo = aLong();
	long idApplic = aLong();
	String dichAutor = aString();

	helper.getUsrVCheckRuoloDefaultList(idUserCorrente, idRuolo, idApplic, dichAutor);
	assertTrue(true);
    }

    @Test
    void getUsrVCheckDichAbilOrganizList_queryIsOk() {
	long idUserIamCorrente = aLong();
	long idDichAbilOrganizAggiunta = aLong();

	helper.getUsrVCheckDichAbilOrganizList(idUserIamCorrente, idDichAbilOrganizAggiunta);
	assertTrue(true);
    }

    @Test
    void getUsrVCheckDichAbilDatiList_queryIsOk() {
	long idUserIamCorrente = aLong();
	long idDichAbilDatiAggiunta = aLong();

	helper.getUsrVCheckDichAbilDatiList(idUserIamCorrente, idDichAbilDatiAggiunta);
	assertTrue(true);
    }

    @Test
    void getUsrVCheckDichAbilEnteList_queryIsOk() {
	long idUserIamCorrente = aLong();
	long idDichAbilEnteAggiunta = aLong();

	helper.getUsrVCheckDichAbilEnteList(idUserIamCorrente, idDichAbilEnteAggiunta);
	assertTrue(true);
    }

    @Test
    void getUsrUsoRuoloDich_queryIsOk() {
	UsrDichAbilOrganiz dich = mockUsrDichAbilOrganiz();
	PrfRuolo prfRuolo = mockPrfRuolo();
	UsrOrganizIam organizIamRuolo = mockUsrOrganizIam();
	String tiScopoRuolo = aString();

	helper.getUsrUsoRuoloDich(dich, prfRuolo, organizIamRuolo, tiScopoRuolo);
	assertTrue(true);
    }

    private UsrOrganizIam mockUsrOrganizIam() {
	UsrOrganizIam organizIamRuolo = new UsrOrganizIam();
	organizIamRuolo.setAplApplic(mockAplApplic());
	organizIamRuolo.setAplTipoOrganiz(mockAplTipoOrganiz());
	organizIamRuolo.setDsOrganiz(aString());
	organizIamRuolo.setIdOrganizApplic(aBigDecimal());
	organizIamRuolo.setIdOrganizIam(aLong());
	organizIamRuolo.setNmOrganiz(aString());
	organizIamRuolo.setOrgEnteConvenzOrgs(new ArrayList<>(0));
	organizIamRuolo.setOrgEnteSiamConserv(mockOrgEnteSiam());
	organizIamRuolo.setOrgEnteSiamGestore(null);
	organizIamRuolo.setUsrAbilOrganizs(new ArrayList<>(0));
	organizIamRuolo.setUsrDichAbilDatis(new ArrayList<>(0));
	organizIamRuolo.setUsrDichAbilOrganizs(new ArrayList<>(0));
	final UsrOrganizIam usrOrganizIam = new UsrOrganizIam();
	usrOrganizIam.setIdOrganizIam(aLong());
	organizIamRuolo.setUsrOrganizIam(usrOrganizIam);
	organizIamRuolo.setUsrOrganizIams(new ArrayList<>(0));
	organizIamRuolo.setUsrRichGestUsers(new ArrayList<>(0));
	organizIamRuolo.setUsrTipoDatoIams(new ArrayList<>(0));
	organizIamRuolo.setUsrUsoRuoloDiches(new ArrayList<>(0));
	return organizIamRuolo;
    }

    private AplTipoOrganiz mockAplTipoOrganiz() {
	final AplTipoOrganiz aplTipoOrganiz = new AplTipoOrganiz();
	aplTipoOrganiz.setAplApplic(mockAplApplic());
	aplTipoOrganiz.setFlLastLivello(aFlag());
	aplTipoOrganiz.setIdTipoOrganiz(aLong());
	aplTipoOrganiz.setNmTipoOrganiz(aString());
	aplTipoOrganiz.setUsrOrganizIams(new ArrayList<>(0));
	return aplTipoOrganiz;
    }

    private UsrDichAbilOrganiz mockUsrDichAbilOrganiz() {
	UsrDichAbilOrganiz dich = new UsrDichAbilOrganiz();
	dich.setDsCausaleDich(aString());
	dich.setIdDichAbilOrganiz(aLong());
	final OrgAppartCollegEnti orgAppartCollegEnti = new OrgAppartCollegEnti();
	orgAppartCollegEnti.setDtFinVal(tomorrowTs());
	orgAppartCollegEnti.setDtIniVal(todayTs());
	orgAppartCollegEnti.setIdAppartCollegEnti(aLong());
	orgAppartCollegEnti.setOrgCollegEntiConvenz(null);
	orgAppartCollegEnti.setOrgEnteSiam(mockOrgEnteSiam());
	dich.setOrgAppartCollegEnti(orgAppartCollegEnti);
	return dich;
    }

    private OrgEnteSiam mockOrgEnteSiam() {
	final OrgEnteSiam orgEnteSiam = new OrgEnteSiam();
	orgEnteSiam.setIdEnteSiam(aLong());
	return orgEnteSiam;
    }

    @Test
    void getPrfVLisDichAutorList_queryIsOk() {
	BigDecimal idRuolo = aBigDecimal();
	String tiDichAutor = aString();

	helper.getPrfVLisDichAutorList(idRuolo, tiDichAutor);
	assertTrue(true);
    }

    @Test
    void getUsrVCheckRuoloDichList_queryIsOk() {
	long idUserCorrente = aLong();
	BigDecimal idRuolo = aBigDecimal();
	BigDecimal idApplic = aBigDecimal();
	BigDecimal idOrganizIamRuolo = aBigDecimal();
	String dichAutor = aString();

	helper.getUsrVCheckRuoloDichList(idUserCorrente, idRuolo, idApplic, idOrganizIamRuolo,
		dichAutor);
	assertTrue(true);
    }

    @Test
    @Disabled("servono dei parametri reali")
    void registraLogUserDaReplic_String_ApplEnumTiOperReplic_queryIsOk() {
	String username = aString();
	for (ApplEnum.TiOperReplic oper : ApplEnum.TiOperReplic.values()) {
	    helper.registraLogUserDaReplic(username, oper);
	    assertTrue(true);
	}
    }

    @Test
    void getUsrVCreaAbilDati_queryIsOk() {
	String nmUserid = aString();
	String nmApplic = aString();
	String nmClasseTipoDato = aString();

	helper.getUsrVCreaAbilDati(nmUserid, nmApplic, nmClasseTipoDato);
	assertTrue(true);
    }

    @Test
    @Disabled("servono dei dati coerenti")
    void aggiungiDichAbilTipoDato_queryIsOk() {
	UsrUser user = mockUser();
	UsrUsoUserApplic usrUsoUserApplic = mockUsrUsoUserApplic();
	UsrDichAbilDatiTableBean tipiDato = new UsrDichAbilDatiTableBean();
	UsrVCreaAbilDati creaAbilDato = mockUsrVCreaAbilDati();
	for (ApplEnum.TiScopoDichAbilDati tiScopoDichAbilDati : ApplEnum.TiScopoDichAbilDati
		.values()) {
	    creaAbilDato.getUsrVCreaAbilDatiId().setTiScopoDichAbilDati(tiScopoDichAbilDati.name());
	    helper.aggiungiDichAbilTipoDato(creaAbilDato, user, usrUsoUserApplic, tipiDato);
	    assertTrue(true);
	}
    }

    private UsrUsoUserApplic mockUsrUsoUserApplic() {
	UsrUsoUserApplic usrUsoUserApplic = new UsrUsoUserApplic();
	usrUsoUserApplic.setIdUsoUserApplic(aLong());
	usrUsoUserApplic.setAplApplic(mockAplApplic());
	usrUsoUserApplic.setUsrAbilDatis(new ArrayList<>(0));
	usrUsoUserApplic.setUsrAbilOrganizs(new ArrayList<>(0));
	usrUsoUserApplic.setUsrDichAbilDatis(new ArrayList<>(0));
	usrUsoUserApplic.setUsrDichAbilOrganizs(new ArrayList<>(0));
	usrUsoUserApplic.setUsrUser(mockUser());
	usrUsoUserApplic.setUsrUsoRuoloUserDefaults(new ArrayList<>(0));
	return usrUsoUserApplic;
    }

    private AplApplic mockAplApplic() {
	final AplApplic aplApplic = new AplApplic();
	aplApplic.setIdApplic(aLong());
	aplApplic.setNmApplic(aString());
	return aplApplic;
    }

    private UsrVCreaAbilDati mockUsrVCreaAbilDati() {
	UsrVCreaAbilDati creaAbilDato = new UsrVCreaAbilDati();
	creaAbilDato.setUsrVCreaAbilDatiId(new UsrVCreaAbilDatiId());
	creaAbilDato.setDsCausaleDich(aString());
	creaAbilDato.setIdAppartCollegEnti(aBigDecimal());
	creaAbilDato.getUsrVCreaAbilDatiId().setIdClasseTipoDato(aBigDecimal());
	creaAbilDato.getUsrVCreaAbilDatiId().setIdOrganizIam(aBigDecimal());
	creaAbilDato.setIdSuptEstEnteConvenz(aBigDecimal());
	creaAbilDato.getUsrVCreaAbilDatiId().setIdUsoUserApplic(aBigDecimal());
	creaAbilDato.setIdVigilEnteProdut(aBigDecimal());
	creaAbilDato.setNmApplic(aString());
	creaAbilDato.setNmClasseTipoDato(aString());
	creaAbilDato.setNmUserid(aString());
	creaAbilDato.getUsrVCreaAbilDatiId()
		.setTiScopoDichAbilDati(ApplEnum.TiScopoDichAbilDati.ALL_ORG.name());
	return creaAbilDato;
    }

    @Test
    void aggiungiAbilOrganizToAdd_queryIsOk() {
	long idUserIam = aLong();
	long idApplic = aLong();

	helper.aggiungiAbilOrganizToAdd(idUserIam, idApplic);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilDatiToAdd_queryIsOk() {
	long idUserIam = aLong();
	long idApplic = aLong();
	Long idClasseTipoDato = aLong();

	helper.aggiungiAbilDatiToAdd(idUserIam, idApplic, idClasseTipoDato);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilEnteToAdd_queryIsOk() {
	long idUserIam = aLong();

	helper.aggiungiAbilEnteToAdd(idUserIam);
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
    void getTipologiaUtenteAdmin_queryIsOk() {
	Long idUserAdmin = aLong();
	try {
	    helper.getTipologiaUtenteAdmin(idUserAdmin);
	    assertTrue(true);
	} catch (Exception e) {
	    assertNoResultException(e);
	}
    }

    @Test
    void retrieveUsrVLisEnteByAbilOrg_queryIsOk() {
	BigDecimal idUserIam = aBigDecimal();

	helper.retrieveUsrVLisEnteByAbilOrg(idUserIam);
	assertTrue(true);
    }

    @Test
    void getLastAccordoEnte_queryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();
	Date dataOdierna = todayTs();

	helper.getLastAccordoEnte(idEnteConvenz, dataOdierna);
	assertTrue(true);
    }

    @Test
    void getUtentiAbilitatiAmbienteEnteConvenz_queryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getUtentiAbilitatiAmbienteEnteConvenz(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getUtentiAbilitatiEnteConvenz_queryIsOk() {
	BigDecimal idEnteConvenz = aBigDecimal();

	helper.getUtentiAbilitatiEnteConvenz(idEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getUtentiAbilitatiAmbEnteXnteConvenz_queryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getUtentiAbilitatiAmbEnteXnteConvenz(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getLastPgOldPsw_queryIsOk() {
	long idUserIam = aLong();

	helper.getLastPgOldPsw(idUserIam);
	assertTrue(true);
    }

    @Test
    void isInOldLastPasswords_queryIsOk() {
	String newPassword = aString();
	long idUserIam = aLong();
	int numOldPsw = 2;

	helper.isInOldLastPasswords(newPassword, idUserIam, numOldPsw);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilOrganizVigil_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilOrganizVigil(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilOrganizFornit_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilOrganizFornit(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilDatiVigil_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilDatiVigil(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilDatiFornit_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilDatiFornit(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilEnteColleg_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilEnteColleg(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilEnteFornit_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilEnteFornit(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilEnteVigil_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilEnteVigil(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilEnteNoconv_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilEnteNoconv(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilEnteCorrisp_queryIsOk() {
	long idUserIamCor = aLong();
	long idUserIamGestito = aLong();

	helper.aggiungiAbilEnteCorrisp(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    @Disabled("operazione da evitare in automatico")
    void eliminaAbilOrganizVigil_queryIsOk() {
	long idUserIam = aLong();

	helper.eliminaAbilOrganizVigil(idUserIam);
	assertTrue(true);
    }

    @Test
    @Disabled("operazione da evitare in automatico")
    void eliminaAbilOrganizSupest_queryIsOk() {
	long idUserIam = aLong();

	helper.eliminaAbilOrganizSupest(idUserIam);
	assertTrue(true);
    }

    @Test
    @Disabled("operazione da evitare in automatico")
    void eliminaAbilTipiDatoVigil_queryIsOk() {
	long idUserIam = aLong();

	helper.eliminaAbilTipiDatoVigil(idUserIam);
	assertTrue(true);
    }

    @Test
    @Disabled("operazione da evitare in automatico")
    void eliminaAbilTipiDatoSupest_queryIsOk() {
	long idUserIam = aLong();

	helper.eliminaAbilTipiDatoSupest(idUserIam);
	assertTrue(true);
    }

    @Test
    @Disabled("operazione da evitare in automatico")
    void eliminaAbilEntiConv_long_queryIsOk() {
	long idUserIam = aLong();

	helper.eliminaAbilEntiConv(idUserIam);
	assertTrue(true);
    }

    @Test
    void isAbilEntiConv_queryIsOk() {
	long idUserIam = aLong();
	long idEnteSiam = aLong();

	helper.isAbilEntiConv(idUserIam, idEnteSiam);
	assertTrue(true);
    }

    @Test
    void isAbilEntiConvOrg_queryIsOk() {
	long idUsoUserApplic = aLong();
	long idOrganizIam = aLong();

	helper.isAbilEntiConvOrg(idUsoUserApplic, idOrganizIam);
	assertTrue(true);
    }

    @Test
    void isAbilEntiConvDati_queryIsOk() {
	long idUsoUserApplic = aLong();
	long idTipoDatoIam = aLong();

	helper.isAbilEntiConvDati(idUsoUserApplic, idTipoDatoIam);
	assertTrue(true);
    }

    @Test
    @Disabled("operazione da evitare in automatico")
    void eliminaAbilEntiConv_long_long_queryIsOk() {
	long idUserIam = aLong();
	long idEnteSiam = aLong();

	helper.eliminaAbilEntiConv(idUserIam, idEnteSiam);
	assertTrue(true);
    }

    @Test
    void getUsrAbilOrganizList_queryIsOk() {
	long idUsoUserApplic = aLong();

	helper.getUsrAbilOrganizList(idUsoUserApplic);
	assertTrue(true);
    }

    @Test
    void getUsrAbilDatiList_queryIsOk() {
	long idUsoUserApplic = aLong();

	helper.getUsrAbilDatiList(idUsoUserApplic);
	assertTrue(true);
    }

    @Test
    void getUsrVLisAbilEnteAmbienteList_queryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getUsrVLisAbilEnteAmbienteList(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void getUtentiDichAbilUnAmbiente_queryIsOk() {
	BigDecimal idAmbienteEnteConvenz = aBigDecimal();

	helper.getUtentiDichAbilUnAmbiente(idAmbienteEnteConvenz);
	assertTrue(true);
    }

    @Test
    void aggiungiAbilEntiSuptFornitQueryIsOk() {
	long idUserIamCor = -1L;
	long idUserIamGestito = -1L;
	long idEnteFornitEst = -1L;
	long idEnteProdut = -1L;
	helper.aggiungiAbilEntiSuptFornit(idUserIamCor, idUserIamGestito, idEnteFornitEst,
		idEnteProdut);
	assertTrue(true);
    }

    @Test
    void getAbilOrganizToAddQueryIsOK() {
	long idUserIam = 0L;
	long idApplic = 0L;
	helper.getAbilOrganizToAdd(idUserIam, idApplic);
	assertTrue(true);
    }

    @Test
    void getAbilDatiToAddQueryIsOK() {
	long idUserIam = 0L;
	long idApplic = 0L;
	helper.getAbilDatiToAdd(idUserIam, idApplic);
	assertTrue(true);
    }

    @Test
    void getAbilOrganizToVigilQueryIsOK() {
	long idUserIamCor = 0L;
	long idUserIamGestito = 0L;
	helper.getAbilOrganizToVigil(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

    @Test
    void getAbilDatiVigil() {
	long idUserIamCor = 0L;
	long idUserIamGestito = 0L;
	helper.getAbilDatiVigil(idUserIamCor, idUserIamGestito);
	assertTrue(true);
    }

}
