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

package it.eng.saceriam.web.ejb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csvreader.CsvReader;

import it.eng.parer.idpjaas.logutils.LogDto;
import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.ejb.common.AppServerInstance;
import it.eng.parer.sacerlog.ejb.util.ObjectsToLogBefore;
import it.eng.parer.sacerlog.ejb.util.PremisEnums;
import it.eng.parer.sacerlog.entity.LogEventoLoginUser;
import it.eng.parer.sacerlog.entity.constraint.ConstLogEventoLoginUser;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.amministrazioneEntiConvenzionati.helper.EntiConvenzionatiHelper;
import it.eng.saceriam.amministrazioneEntiNonConvenzionati.ejb.EntiNonConvenzionatiEjb;
import it.eng.saceriam.common.Constants.TiOperReplic;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplClasseTipoDato;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.LogAgente;
import it.eng.saceriam.entity.OrgAccordoEnte;
import it.eng.saceriam.entity.OrgAmbienteEnteConvenz;
import it.eng.saceriam.entity.OrgAppartCollegEnti;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgServizioErog;
import it.eng.saceriam.entity.OrgSuptEsternoEnteConvenz;
import it.eng.saceriam.entity.OrgVigilEnteProdut;
import it.eng.saceriam.entity.PrfDichAutor;
import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.PrfRuoloCategoria;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.saceriam.entity.UsrAbilDati;
import it.eng.saceriam.entity.UsrAbilOrganiz;
import it.eng.saceriam.entity.UsrAppartUserRich;
import it.eng.saceriam.entity.UsrDichAbilDati;
import it.eng.saceriam.entity.UsrDichAbilEnteConvenz;
import it.eng.saceriam.entity.UsrDichAbilOrganiz;
import it.eng.saceriam.entity.UsrIndIpUser;
import it.eng.saceriam.entity.UsrOldPsw;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrRichGestUser;
import it.eng.saceriam.entity.UsrStatoUser;
import it.eng.saceriam.entity.UsrTipoDatoIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUserExt;
import it.eng.saceriam.entity.UsrUsoRuoloDich;
import it.eng.saceriam.entity.UsrUsoRuoloUserDefault;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.entity.constraint.ConstPrfRuolo;
import it.eng.saceriam.entity.constraint.ConstUsrAppartUserRich;
import it.eng.saceriam.entity.constraint.ConstUsrRichGestUser;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.entity.constraint.ConstUtente;
import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.exception.ParerWarningException;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloCategoriaRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloCategoriaTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilOrganizRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilOrganizTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrIndIpUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrIndIpUserTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoRuoloUserDefaultRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoRuoloUserDefaultTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoUserApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoUserApplicTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorRowBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisRuoloRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUsoRuoloDichRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUsoRuoloDichTableBean;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.viewEntity.AplVTreeEntryMenu;
import it.eng.saceriam.viewEntity.OrgVEnteConvByDelabilorg;
import it.eng.saceriam.viewEntity.PrfVLisDichAutor;
import it.eng.saceriam.viewEntity.UsrVCheckDichAbilDati;
import it.eng.saceriam.viewEntity.UsrVCheckDichAbilEnte;
import it.eng.saceriam.viewEntity.UsrVCheckDichAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVCheckRuoloDefault;
import it.eng.saceriam.viewEntity.UsrVCheckRuoloDich;
import it.eng.saceriam.viewEntity.UsrVCreaAbilDati;
import it.eng.saceriam.viewEntity.UsrVLisEnteByAbilOrg;
import it.eng.saceriam.web.dto.PairAuth;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.helper.SistemiVersantiHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ActionEnums;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.dto.FileBinario;
import it.eng.saceriam.ws.ejb.WsIdpLogger;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.security.auth.PwdUtil;

/**
 *
 * @author Gilioli_P feat. 40_Emme
 */
@Stateless
@LocalBean
public class AuthEjb {

    private static final Logger log = LoggerFactory.getLogger(AuthEjb.class);

    public enum TipoEventoLogEventoLoginUser {
	BAD_USER_ASSOCIATION
    }

    public static final String DS_EVENTO_BAD_USER_ASSOCIATION = "Comunicazione di utente Parer non associato al codice fiscale dell'utente attualmente loggato.";
    private static final String I_CAMPI_OBBLIGATORI_PER_LE_AZIONI_NON_SON = "I campi obbligatori per le azioni non sono stati definiti nel csv fornito";

    @EJB
    private UserHelper userHelper;
    @EJB
    private SistemiVersantiHelper sistemiVersantiHelper;
    @EJB
    private EntiConvenzionatiEjb entiConvEjb;
    @EJB
    private EntiNonConvenzionatiEjb entiNonConvEjb;
    @EJB
    private EntiConvenzionatiHelper entiConvHelper;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;
    @EJB
    private AmministrazioneUtentiEjb amministrazioneUtentiEjb;
    @EJB
    private AmministrazioneUtentiHelper amministrazioneUtentiHelper;
    @EJB
    private WsIdpLogger idpLogger;
    @EJB
    private AppServerInstance asi;

    @Resource
    private SessionContext ctx;
    /*
     * Nei controlli di coerenza, numero massimo di record da visualizzare in caso di messaggio di
     * errore
     */
    private static final int MAX_NUM_ERROR_RECORD = 3;

    public void associaUtenteConCodiceFiscale(LogParam param, String username, String password,
	    String codiceFiscale, String cognome, String nome)
	    throws ParerUserError, ParerWarningException {
	UsrUser user;
	if (codiceFiscale == null) {
	    throw new ParerUserError("Codice fiscale nullo!");
	} else {
	    try {
		user = userHelper.findUser(username, password);
		// MEV#26484 - Modificare la gestione dell'accesso SPID quando l'utente non è attivo
		// su SIAM
	    } catch (Exception ex) {
		throw new ParerUserError("Username o password errati");
	    }
	    if (user != null) {
		Date dataAttuale = new Date();
		// Controllo che l'utente non sia scaduto o disattivo
		if (user.getFlAttivo() != null && user.getFlAttivo().equals("1")
			&& (user.getDtScadPsw().after(dataAttuale)
				|| user.getDtScadPsw().equals(dataAttuale))) {
		    /*
		     * Controlla che l'utente loggato con spid non abia fatto accesso con le
		     * credenziali di un utente con diverso codice fiscale. Il codice fiscale su DB
		     * deve essere nullo.
		     */
		    if (user.getCdFisc() != null
			    && !user.getCdFisc().toUpperCase().equalsIgnoreCase(codiceFiscale)) {
			throw new ParerUserError(
				"Il codice fiscale dell'utente PARER non coincide con quello dell'utente attualmente loggato");
		    }
		    // MEV#27568 - Inserimento controllo nella associazione utente SPID con
		    // anagrafica utenti
		    if (!(cognome.trim().equalsIgnoreCase(user.getNmCognomeUser().trim())
			    && nome.trim().toUpperCase()
				    .equals(user.getNmNomeUser().trim().toUpperCase()))) {
			throw new ParerWarningException(
				"Il cognome e il nome dell'utente con cui è avvenuta l'autenticazione SPID non corrispondono a quelli associati all'utenza Parer.");
		    }
		    // ---
		    user.setCdFisc(codiceFiscale.toUpperCase());
		    userHelper.getEntityManager().flush();
		    sacerLogEjb.log(param.getTransactionLogContext(),
			    paramHelper.getParamApplicApplicationName(), param.getNomeUtente(),
			    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE,
			    new BigDecimal(user.getIdUserIam()), param.getNomePagina());
		} else {
		    // MEV#26484 - Modificare la gestione dell'accesso SPID quando l'utente non è
		    // attivo su SIAM
		    throw new ParerWarningException("L'utente selezionato è disattivato");
		}
	    }
	}
    }

    public void scriviLogEventoLoginUserAssociazioneFallita(String nmUserId) {
	LogEventoLoginUser l = new LogEventoLoginUser();
	l.setNmUserid(nmUserId);
	l.setDtEvento(new Date());
	l.setDsEvento(DS_EVENTO_BAD_USER_ASSOCIATION);
	l.setTipoEvento(TipoEventoLogEventoLoginUser.BAD_USER_ASSOCIATION.name());
	// setCdIndIpClient non valorizzato come per il cambio Password
	l.setCdIndServer(asi.getName());
	amministrazioneUtentiHelper.getEntityManager().persist(l);
    }

    public void changePwd(LogParam param, String username, String newPassword, String oldPassword)
	    throws ParerUserError {
	byte[] binarySalt = PwdUtil.generateSalt();

	UsrUser user = this.getUserOrDie(username);

	String numOldPsw = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NUM_OLD_PSW.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);

	/*
	 * Se l'utente è di tipo AUTOMA, aggiorno al 31/12/2444, altrimenti (PERSONA FISICA) a 90
	 * giorni da oggi
	 */
	Calendar cal = Calendar.getInstance();
	if (user.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
	    cal.set(Calendar.YEAR, 2444);
	    cal.set(Calendar.MONTH, Calendar.DECEMBER);
	    cal.set(Calendar.DATE, 31);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	} else {
	    /* Altrimenti setto la scadenza a data corrente più 90 giorni */
	    Date dataOdierna = new Date();
	    cal.setTime(dataOdierna);
	    cal.add(Calendar.DATE, 90);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	}

	userHelper.updateUserPwd(username, oldPassword, newPassword, new Integer(numOldPsw),
		binarySalt, cal.getTime());

	// Registro la password dell'utente nella tabella USR_OLD_PSW
	saveOldPsw(username);

	sacerLogEjb.log(param.getTransactionLogContext(),
		paramHelper.getParamApplicApplicationName(), param.getNomeUtente(),
		param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE,
		new BigDecimal(user.getIdUserIam()), param.getNomePagina());
    }

    /* Determina se l'utente deve ancora cambiare la password per la prima volta */
    public boolean userHasToChangePasswordFirstTime(String username) throws ParerUserError {
	boolean ret = false;
	UsrUser u = this.getUserOrDie(username);
	if (u != null && u.getDtScadPsw().before(u.getDtRegPsw())) {
	    ret = true;
	}
	return ret;
    }

    private UsrUser getUserOrDie(String username) throws ParerUserError {
	return getUserOrDie(username, ConstLogEventoLoginUser.DS_EVENTO_UNKNOWN_USR_EDIT_PSW);
    }

    private UsrUser getUserOrDie(String username, String dsEvento) throws ParerUserError {
	List<UsrUser> userList = userHelper.getUsrUserByNmUserid(username);

	if (userList.isEmpty()) {
	    LogDto logDto = new LogDto();
	    logDto.setNmAttore(ConstLogEventoLoginUser.NM_ATTORE_ONLINE);
	    logDto.setNmUser(username);
	    log.error("Utente non presente");
	    logDto.setTipoEvento(LogDto.TipiEvento.BAD_USER);
	    logDto.setDsEvento(dsEvento);
	    logDto.setTsEvento(new Date());
	    idpLogger.scriviLog(logDto);
	    // Non devo dare possibilità all'utente di risalire a quale sia la causa dell'errore
	    // (user o password)
	    throw new ParerUserError("Le vecchie credenziali non sono corrette");
	}
	return userList.get(0);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String resetPwd(LogParam param, BigDecimal idUtente) {
	UsrUser ut = userHelper.findUserById(idUtente.longValueExact());
	String randomPwd = RandomStringUtils.randomAlphabetic(8);
	byte[] binarySalt = PwdUtil.generateSalt();
	userHelper.resetPwd(idUtente.longValue(),
		PwdUtil.encodePBKDF2Password(binarySalt, randomPwd),
		PwdUtil.encodeUFT8Base64String(binarySalt));

	// Registro la password dell'utente nella tabella USR_OLD_PSW
	saveOldPsw(ut.getNmUserid());

	/*
	 * Codice aggiuntivo per il logging...
	 */
	sacerLogEjb.log(param.getTransactionLogContext(),
		paramHelper.getParamApplicApplicationName(), param.getNomeUtente(),
		param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE, idUtente,
		param.getNomePagina());

	return randomPwd;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void resetPwd(LogParam param, BigDecimal idUtente, String newPassword)
	    throws ParerUserError {
	UsrUser user = userHelper.findById(UsrUser.class, idUtente);
	Calendar cal = Calendar.getInstance();
	if (user.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
	    cal.set(Calendar.YEAR, 2444);
	    cal.set(Calendar.MONTH, Calendar.DECEMBER);
	    cal.set(Calendar.DATE, 31);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	} else {
	    /* Altrimenti setto la scadenza a data corrente più 90 giorni */
	    Date dataOdierna = new Date();
	    cal.setTime(dataOdierna);
	    cal.add(Calendar.DATE, 90);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	}
	byte[] binarySalt = PwdUtil.generateSalt();
	String saltedPassword = PwdUtil.encodePBKDF2Password(binarySalt, newPassword);
	String binarySaltEncoded = PwdUtil.encodeUFT8Base64String(binarySalt);

	String numOldPsw = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.NUM_OLD_PSW.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);

	// Controlli su ultime password
	if (userHelper.isInOldLastPasswords(newPassword, idUtente.longValue(),
		new Integer(numOldPsw))) {
	    log.error(
		    "La password inserita coincide con una delle ultime {} password inserite in precedenza",
		    numOldPsw);
	    throw new ParerUserError("La password inserita coincide con una delle ultime {} "
		    + numOldPsw + " password inserite in precedenza");
	}

	// Inserisco la nuova password
	userHelper.resetPwd(idUtente.longValue(), saltedPassword, binarySaltEncoded, cal.getTime());

	// La salvo nella tabella delle vecchie psw
	saveOldPsw(idUtente.longValue(), saltedPassword, binarySaltEncoded);

	/*
	 * Codice aggiuntivo per il logging...
	 */
	userHelper.findUserById(idUtente.longValueExact());
	sacerLogEjb.log(param.getTransactionLogContext(),
		paramHelper.getParamApplicApplicationName(), param.getNomeUtente(),
		param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE, idUtente,
		param.getNomePagina());
    }

    public void saveOldPsw(String nmUserid) {
	UsrUser ut = userHelper.findUserByName(nmUserid);
	BigDecimal lastPgOldPsw = userHelper.getLastPgOldPsw(ut.getIdUserIam());

	UsrOldPsw oldPsw = new UsrOldPsw();
	oldPsw.setCdPsw(ut.getCdPsw());
	oldPsw.setCdSalt(ut.getCdSalt());
	oldPsw.setUsrUser(ut);
	if (ut.getUsrOldPsws() == null) {
	    ut.setUsrOldPsws(new ArrayList<>());
	}
	ut.getUsrOldPsws().add(oldPsw);
	lastPgOldPsw = lastPgOldPsw.add(BigDecimal.ONE);
	oldPsw.setPgOldPsw(lastPgOldPsw);
	userHelper.getEntityManager().persist(oldPsw);
    }

    public void saveOldPsw(long idUserIam, String cdPsw, String cdSalt) {
	UsrUser ut = userHelper.findById(UsrUser.class, idUserIam);
	BigDecimal lastPgOldPsw = userHelper.getLastPgOldPsw(idUserIam);

	UsrOldPsw oldPsw = new UsrOldPsw();
	oldPsw.setCdPsw(cdPsw);
	oldPsw.setCdSalt(cdSalt);
	oldPsw.setUsrUser(ut);
	if (ut.getUsrOldPsws() == null) {
	    ut.setUsrOldPsws(new ArrayList<>());
	}
	ut.getUsrOldPsws().add(oldPsw);
	lastPgOldPsw = lastPgOldPsw.add(BigDecimal.ONE);
	oldPsw.setPgOldPsw(lastPgOldPsw);
	userHelper.getEntityManager().persist(oldPsw);
    }

    /**
     * Legge riga per riga i 4 file CSV e salva le informazioni nelle relative tabelle
     *
     * @param listaFiles       i 4 file binari relativi ai CSV
     * @param charset          il charset utilizzato
     * @param nomeApplicazione il nome dell'applicazione trattata
     *
     * @return true se sono stati modificati dei servizi
     *
     * @throws Exception errore generico
     */
    public boolean readCsvFiles(List<FileBinario> listaFiles, String charset,
	    String nomeApplicazione) throws Exception {
	boolean serviziModificati = false;
	try {
	    for (FileBinario file : listaFiles) {
		byte[] fileBytes;
		if (file.isInMemoria()) {
		    fileBytes = file.getDati();
		} else {
		    fileBytes = Files.readAllBytes(file.getFileSuDisco().toPath());
		}
		/* Recupero il CSVReader relativo al file che sto trattando */
		CsvReader csvReader = new CsvReader(new ByteArrayInputStream(fileBytes), ';',
			Charset.forName(charset));
		csvReader.setSkipEmptyRecords(true);
		csvReader.readHeaders();
		/* Comincio a scorrere il file */
		if (file.getId().equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
		    ///////////////////////
		    /* FILE ENTRATE MENU */
		    ///////////////////////
		    List<PairAuth> csvMenu = new ArrayList<>();
		    while (csvReader.readRecord()) {
			/* Ottengo i campi del record */
			String nomeApplicazioneDaFile = csvReader
				.get(ActionEnums.MenuEntryFields.APPLIC.name());
			String nomeEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.NM_ENTRY_MENU.name());
			String descEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.DS_ENTRY_MENU.name());
			String numLivelloEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.NI_LIVELLO_ENTRY_MENU.name());
			String numOrdineEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.NI_ORD_ENTRY_MENU.name());
			String nomeEntryMenuPadre = csvReader
				.get(ActionEnums.MenuEntryFields.NM_ENTRY_MENU_PADRE.name());
			String linkEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.DL_LINK_ENTRY_MENU.name());

			/*
			 * Verifico che non ci siano incongruenze tra applicazione scelta e
			 * applicazione descritta su file
			 */
			if (StringUtils.isNotBlank(nomeApplicazione)
				&& StringUtils.isNotBlank(nomeApplicazioneDaFile)
				&& nomeApplicazione.equals(nomeApplicazioneDaFile)) {
			    /* Verifico i campi obbligatori */
			    if (StringUtils.isNotBlank(nomeApplicazione)
				    && StringUtils.isNotBlank(nomeEntryMenu)
				    && StringUtils.isNotBlank(descEntryMenu)
				    && StringUtils.isNotBlank(numLivelloEntryMenu)
				    && StringUtils.isNotBlank(numOrdineEntryMenu)) {
				try {
				    /* Creo la riga e la salvo nel db */
				    addMenuEntry(nomeApplicazione, nomeEntryMenu, descEntryMenu,
					    numLivelloEntryMenu, numOrdineEntryMenu,
					    nomeEntryMenuPadre, linkEntryMenu);
				    /*
				     * Mi mantengo l'entrata a menu in una lista, per poter
				     * eliminare i menu non presenti nel file (e dunque da
				     * cancellare da DB)
				     */
				    csvMenu.add(new PairAuth(nomeApplicazione, nomeEntryMenu));
				} catch (Exception ex) {
				    csvReader.close();
				    log.error(
					    "L'inserimento dell'entry menu ha ritornato un errore :"
						    + ex.getMessage(),
					    ex.getCause());
				    throw new EMFError(EMFError.ERROR,
					    "Errore nell'inserimento delle entry menu", ex);
				}
			    } else {
				csvReader.close();
				log.error(
					"I campi obbligatori per le entry menu non sono stati definiti nel csv fornito");
				throw new EMFError(EMFError.ERROR,
					"I campi obbligatori per le entry menu non sono stati definiti nel csv fornito");
			    }
			} else {
			    csvReader.close();
			    log.error(
				    "Incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			    throw new EMFError(EMFError.ERROR,
				    "Sono state rilevate incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			}
		    }
		    /* Elimino i menu non presenti nel csv */
		    deleteDataNotInCsv(csvMenu, file.getId());
		} else if (file.getId().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
		    /////////////////
		    /* FILE PAGINE */
		    /////////////////
		    List<PairAuth> csvPages;
		    csvPages = new ArrayList<>();
		    while (csvReader.readRecord()) {
			/* Ottengo i campi del record */
			String nomeApplicazioneDaFile = csvReader
				.get(ActionEnums.WebPagesFields.APPLIC.name());
			String nomePagina = csvReader
				.get(ActionEnums.WebPagesFields.NM_PAGINA_WEB.name());
			String descPagina = csvReader
				.get(ActionEnums.WebPagesFields.DS_PAGINA_WEB.name());
			String tiHelpOnline = csvReader
				.get(ActionEnums.WebPagesFields.TI_HELP_ON_LINE.name());
			String entryMenu = csvReader
				.get(ActionEnums.WebPagesFields.ENTRY_MENU.name());

			/*
			 * Verifico che non ci siano incongruenze tra applicazione scelta e
			 * applicazione descritta su file
			 */
			if (StringUtils.isNotBlank(nomeApplicazione)
				&& StringUtils.isNotBlank(nomeApplicazioneDaFile)
				&& nomeApplicazione.equals(nomeApplicazioneDaFile)) {
			    /* Verifico i campi obbligatori */
			    if (StringUtils.isNotBlank(nomeApplicazione)
				    && StringUtils.isNotBlank(nomePagina)
				    && StringUtils.isNotBlank(descPagina)
				    && StringUtils.isNotBlank(tiHelpOnline)
				    && StringUtils.isNotBlank(entryMenu)) {
				try {
				    /* Creo la riga e la salvo nel db */
				    addWebPage(nomeApplicazione, nomePagina, descPagina,
					    tiHelpOnline, entryMenu);
				    /*
				     * Mi mantengo le pagine in una lista, per poter eliminare i
				     * menu non presenti nel file (e dunque da cancellare da DB)
				     */
				    csvPages.add(new PairAuth(nomeApplicazione, nomePagina));
				} catch (Exception ex) {
				    csvReader.close();
				    log.error("L'inserimento della pagina ha ritornato un errore :"
					    + ex.getMessage(), ex.getCause());
				    throw new EMFError(EMFError.ERROR,
					    "Errore nell'inserimento delle pagine", ex);
				}
			    } else {
				csvReader.close();
				log.error(
					"I campi obbligatori per le pagine web non sono stati definiti nel csv fornito");
				throw new EMFError(EMFError.ERROR,
					"I campi obbligatori per le pagine web non sono stati definiti nel csv fornito");
			    }
			} else {
			    csvReader.close();
			    log.error(
				    "Incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			    throw new EMFError(EMFError.ERROR,
				    "Sono state rilevate incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			}
		    }
		    /* Elimino le pagine non presenti nel csv */
		    deleteDataNotInCsv(csvPages, file.getId());
		} else if (file.getId().equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
		    /////////////////
		    /* FILE AZIONI */
		    /////////////////
		    List<PairAuth> csvActions = new ArrayList<>();
		    while (csvReader.readRecord()) {
			/* Ottengo i campi del record */
			String nomeApplicazioneDaFile = csvReader
				.get(ActionEnums.ActionsFields.APPLIC.name());
			String nomePagina = csvReader
				.get(ActionEnums.ActionsFields.NM_PAGINA_WEB.name());
			String nomeAzione = csvReader
				.get(ActionEnums.ActionsFields.NM_AZIONE_PAGINA.name());
			String descAzione = csvReader
				.get(ActionEnums.ActionsFields.DS_AZIONE_PAGINA.name());

			/*
			 * Verifico che non ci siano incongruenze tra applicazione scelta e
			 * applicazione descritta su file
			 */
			if (StringUtils.isNotBlank(nomeApplicazione)
				&& StringUtils.isNotBlank(nomeApplicazioneDaFile)
				&& nomeApplicazione.equals(nomeApplicazioneDaFile)) {
			    /* Verifico i campi obbligatori */
			    if (StringUtils.isNotBlank(nomeApplicazione)
				    && StringUtils.isNotBlank(nomePagina)
				    && StringUtils.isNotBlank(nomeAzione)
				    && StringUtils.isNotBlank(descAzione)) {
				try {
				    /* Creo la riga e la salvo nel db */
				    addAction(nomeApplicazione, nomePagina, nomeAzione, descAzione);
				    /*
				     * Mi mantengo le azioni in una lista, per poter eliminare i
				     * dati non presenti nel file
				     */
				    csvActions.add(new PairAuth(nomeApplicazione,
					    new PairAuth(nomePagina, nomeAzione)));
				} catch (Exception ex) {
				    csvReader.close();
				    log.error("L'inserimento dell'azione ha ritornato un errore :"
					    + ex.getMessage(), ex.getCause());
				    throw new EMFError(EMFError.ERROR,
					    "Errore nell'inserimento delle azioni", ex);
				}
			    } else {
				csvReader.close();
				log.error(I_CAMPI_OBBLIGATORI_PER_LE_AZIONI_NON_SON);
				throw new EMFError(EMFError.ERROR,
					I_CAMPI_OBBLIGATORI_PER_LE_AZIONI_NON_SON);
			    }
			} else {
			    csvReader.close();
			    log.error(
				    "Incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			    throw new EMFError(EMFError.ERROR,
				    "Sono state rilevate incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			}
		    }
		    /* Elimino le azioni non presenti nel csv */
		    deleteDataNotInCsv(csvActions, file.getId());
		} else if (file.getId().equals(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name())) {
		    //////////////////////
		    /* FILE SERVIZI WEB */
		    //////////////////////
		    List<PairAuth> csvWebServices = new ArrayList<>();
		    int numAggiunti = 0;
		    while (csvReader.readRecord()) {
			/* Ottengo i campi del record */
			String nomeApplicazioneDaFile = csvReader
				.get(ActionEnums.WebServicesFields.APPLIC.name());
			String nomeServizio = csvReader
				.get(ActionEnums.WebServicesFields.NM_SERVIZIO_WEB.name());
			String descServizio = csvReader
				.get(ActionEnums.WebServicesFields.DS_SERVIZIO_WEB.name());

			/*
			 * Verifico che non ci siano incongruenze tra applicazione scelta e
			 * applicazione descritta su file
			 */
			if (StringUtils.isNotBlank(nomeApplicazione)
				&& StringUtils.isNotBlank(nomeApplicazioneDaFile)
				&& nomeApplicazione.equals(nomeApplicazioneDaFile)) {
			    /* Verifico i campi obbligatori */
			    if (StringUtils.isNotBlank(nomeApplicazione)
				    && StringUtils.isNotBlank(nomeServizio)
				    && StringUtils.isNotBlank(descServizio)) {
				try {
				    /* Creo la riga e la salvo nel db */
				    numAggiunti = addService(nomeApplicazione, nomeServizio,
					    descServizio, numAggiunti);
				    /*
				     * Mi mantengo i servizi in una lista, per poter eliminare i
				     * dati non presenti nel file
				     */
				    csvWebServices
					    .add(new PairAuth(nomeApplicazione, nomeServizio));
				} catch (Exception ex) {
				    csvReader.close();
				    log.error(
					    "L'inserimento del servizio web ha ritornato un errore :"
						    + ex.getMessage(),
					    ex.getCause());
				    throw new EMFError(EMFError.ERROR,
					    "Errore nell'inserimento dei servizi web", ex);
				}
			    } else {
				csvReader.close();
				log.error(
					"I campi obbligatori per i servizi web non sono stati definiti nel csv fornito");
				throw new EMFError(EMFError.ERROR,
					"I campi obbligatori per i servizi web non sono stati definiti nel csv fornito");
			    }
			} else {
			    csvReader.close();
			    log.error(
				    "Incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			    throw new EMFError(EMFError.ERROR,
				    "Sono state rilevate incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			}
		    }
		    int numEliminati = 0;
		    /* Elimino i servizi web non presenti nel csv */
		    numEliminati = deleteDataNotInCsv(csvWebServices, file.getId());
		    if (numAggiunti > 0 || numEliminati > 0) {
			serviziModificati = true;
		    }
		}
		/* Chiudo il CSVReader */
		csvReader.close();
	    }
	} catch (IOException e) {
	    ctx.setRollbackOnly();
	    log.error(e.getMessage(), e);
	    throw new EMFError(EMFError.BLOCKING, e.getMessage());
	} catch (EMFError ex) {
	    ctx.setRollbackOnly();
	    throw ex;
	}
	return serviziModificati;
    }

    /**
     * Legge riga per riga i CSV di pagine e azioni e recupera i dati per i controlli
     *
     * @param listaFiles       i 4 file binari relativi ai CSV
     * @param charset          il charset utilizzato
     * @param nomeApplicazione il nome dell'applicazione trattata
     *
     * @return true se sono stati modificati dei servizi
     *
     * @throws Exception errore generico
     */
    public String checkAzioniPagineDaCancellare(List<FileBinario> listaFiles, String charset,
	    String nomeApplicazione) throws Exception {
	try {
	    List<AplPaginaWeb> pagineWebList = new ArrayList<>();
	    List<AplAzionePagina> azioniPaginaList = new ArrayList<>();
	    List<AplEntryMenu> entryMenuListHelperOnLine = new ArrayList<>();
	    List<AplPaginaWeb> pagineWebListHelperOnLine = new ArrayList<>();
	    for (FileBinario file : listaFiles) {
		byte[] fileBytes;
		if (file.isInMemoria()) {
		    fileBytes = file.getDati();
		} else {
		    fileBytes = Files.readAllBytes(file.getFileSuDisco().toPath());
		}
		/* Recupero il CSVReader relativo al file che sto trattando */
		CsvReader csvReader = new CsvReader(new ByteArrayInputStream(fileBytes), ';',
			Charset.forName(charset));
		csvReader.setSkipEmptyRecords(true);
		csvReader.readHeaders();
		if (file.getId().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
		    /////////////////
		    /* FILE PAGINE */
		    /////////////////
		    List<PairAuth> csvPages = new ArrayList<>();
		    List<PairAuth> csvPagesAiutoInLinea = new ArrayList<>();
		    while (csvReader.readRecord()) {
			/* Ottengo i campi del record */
			String nomeApplicazioneDaFile = csvReader
				.get(ActionEnums.WebPagesFields.APPLIC.name());
			String nomePagina = csvReader
				.get(ActionEnums.WebPagesFields.NM_PAGINA_WEB.name());
			String descPagina = csvReader
				.get(ActionEnums.WebPagesFields.DS_PAGINA_WEB.name());
			String tiHelpOnline = csvReader
				.get(ActionEnums.WebPagesFields.TI_HELP_ON_LINE.name());
			String entryMenu = csvReader
				.get(ActionEnums.WebPagesFields.ENTRY_MENU.name());

			/*
			 * Verifico che non ci siano incongruenze tra applicazione scelta e
			 * applicazione descritta su file
			 */
			if (StringUtils.isNotBlank(nomeApplicazione)
				&& StringUtils.isNotBlank(nomeApplicazioneDaFile)
				&& nomeApplicazione.equals(nomeApplicazioneDaFile)) {
			    /* Verifico i campi obbligatori */
			    if (StringUtils.isNotBlank(nomeApplicazione)
				    && StringUtils.isNotBlank(nomePagina)
				    && StringUtils.isNotBlank(descPagina)
				    && StringUtils.isNotBlank(tiHelpOnline)
				    && StringUtils.isNotBlank(entryMenu)) {
				try {
				    /* Mi mantengo le pagine del CSV in una lista */
				    csvPages.add(new PairAuth(nomeApplicazione, nomePagina));
				    /*
				     * Mi mantengo le pagine del CSV in una lista per help on line
				     */
				    csvPagesAiutoInLinea
					    .add(new PairAuth(nomeApplicazione, nomePagina));
				} catch (Exception ex) {
				    csvReader.close();
				    log.error("Il recupero della pagina ha ritornato un errore :"
					    + ex.getMessage(), ex.getCause());
				    throw new EMFError(EMFError.ERROR,
					    "Errore nel recupero delle azioni", ex);
				}
			    } else {
				csvReader.close();
				log.error(
					"I campi obbligatori per le pagine web non sono stati definiti nel csv fornito");
				throw new EMFError(EMFError.ERROR,
					"I campi obbligatori per le pagine web non sono stati definiti nel csv fornito");
			    }
			} else {
			    csvReader.close();
			    log.error(
				    "Incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			    throw new EMFError(EMFError.ERROR,
				    "Sono state rilevate incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			}
		    }
		    /* Recupero le pagine non presenti nel csv */
		    pagineWebList = selectPagineNotInCsv(csvPages);
		    /* Recupero le pagine non presenti nel csv con aiuto in linea */
		    pagineWebListHelperOnLine = selectPagineNotInCsvAiutoInLinea(
			    csvPagesAiutoInLinea);
		} else if (file.getId().equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
		    /////////////////
		    /* FILE AZIONI */
		    /////////////////
		    List<PairAuth> csvActions = new ArrayList<>();
		    while (csvReader.readRecord()) {
			/* Ottengo i campi del record */
			String nomeApplicazioneDaFile = csvReader
				.get(ActionEnums.ActionsFields.APPLIC.name());
			String nomePagina = csvReader
				.get(ActionEnums.ActionsFields.NM_PAGINA_WEB.name());
			String nomeAzione = csvReader
				.get(ActionEnums.ActionsFields.NM_AZIONE_PAGINA.name());
			String descAzione = csvReader
				.get(ActionEnums.ActionsFields.DS_AZIONE_PAGINA.name());

			/*
			 * Verifico che non ci siano incongruenze tra applicazione scelta e
			 * applicazione descritta su file
			 */
			if (StringUtils.isNotBlank(nomeApplicazione)
				&& StringUtils.isNotBlank(nomeApplicazioneDaFile)
				&& nomeApplicazione.equals(nomeApplicazioneDaFile)) {
			    /* Verifico i campi obbligatori */
			    if (StringUtils.isNotBlank(nomeApplicazione)
				    && StringUtils.isNotBlank(nomePagina)
				    && StringUtils.isNotBlank(nomeAzione)
				    && StringUtils.isNotBlank(descAzione)) {
				try {
				    /*
				     * Mi mantengo le azioni in una lista, per poter eliminare i
				     * dati non presenti nel file
				     */
				    csvActions.add(new PairAuth(nomeApplicazione,
					    new PairAuth(nomePagina, nomeAzione)));
				} catch (Exception ex) {
				    csvReader.close();
				    log.error("Il recupero dell'azione ha ritornato un errore :"
					    + ex.getMessage(), ex.getCause());
				    throw new EMFError(EMFError.ERROR,
					    "Errore nel recupero delle azioni", ex);
				}
			    } else {
				csvReader.close();
				log.error(I_CAMPI_OBBLIGATORI_PER_LE_AZIONI_NON_SON);
				throw new EMFError(EMFError.ERROR,
					I_CAMPI_OBBLIGATORI_PER_LE_AZIONI_NON_SON);
			    }
			} else {
			    csvReader.close();
			    log.error(
				    "Incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			    throw new EMFError(EMFError.ERROR,
				    "Sono state rilevate incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			}
		    }
		    /* Recupero le azioni non presenti nel csv */
		    azioniPaginaList = selectAzioniNotInCsv(csvActions);
		} else /* Comincio a scorrere il file */ if (file.getId()
			.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
		    ///////////////////////
		    /* FILE ENTRATE MENU */
		    ///////////////////////
		    List<PairAuth> csvMenu = new ArrayList<>();
		    while (csvReader.readRecord()) {
			/* Ottengo i campi del record */
			String nomeApplicazioneDaFile = csvReader
				.get(ActionEnums.MenuEntryFields.APPLIC.name());
			String nomeEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.NM_ENTRY_MENU.name());
			String descEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.DS_ENTRY_MENU.name());
			String numLivelloEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.NI_LIVELLO_ENTRY_MENU.name());
			String numOrdineEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.NI_ORD_ENTRY_MENU.name());
			String nomeEntryMenuPadre = csvReader
				.get(ActionEnums.MenuEntryFields.NM_ENTRY_MENU_PADRE.name());
			String linkEntryMenu = csvReader
				.get(ActionEnums.MenuEntryFields.DL_LINK_ENTRY_MENU.name());

			/*
			 * Verifico che non ci siano incongruenze tra applicazione scelta e
			 * applicazione descritta su file
			 */
			if (StringUtils.isNotBlank(nomeApplicazione)
				&& StringUtils.isNotBlank(nomeApplicazioneDaFile)
				&& nomeApplicazione.equals(nomeApplicazioneDaFile)) {
			    /* Verifico i campi obbligatori */
			    if (StringUtils.isNotBlank(nomeApplicazione)
				    && StringUtils.isNotBlank(nomeEntryMenu)
				    && StringUtils.isNotBlank(descEntryMenu)
				    && StringUtils.isNotBlank(numLivelloEntryMenu)
				    && StringUtils.isNotBlank(numOrdineEntryMenu)) {
				try {
				    /* Creo la riga e la salvo nel db */
				    addMenuEntry(nomeApplicazione, nomeEntryMenu, descEntryMenu,
					    numLivelloEntryMenu, numOrdineEntryMenu,
					    nomeEntryMenuPadre, linkEntryMenu);
				    /*
				     * Mi mantengo l'entrata a menu in una lista, per poter
				     * eliminare i menu non presenti nel file (e dunque da
				     * cancellare da DB)
				     */
				    csvMenu.add(new PairAuth(nomeApplicazione, nomeEntryMenu));
				} catch (Exception ex) {
				    csvReader.close();
				    log.error(
					    "L'inserimento dell'entry menu ha ritornato un errore :"
						    + ex.getMessage(),
					    ex.getCause());
				    throw new EMFError(EMFError.ERROR,
					    "Errore nell'inserimento delle entry menu", ex);
				}
			    } else {
				csvReader.close();
				log.error(
					"I campi obbligatori per le entry menu non sono stati definiti nel csv fornito");
				throw new EMFError(EMFError.ERROR,
					"I campi obbligatori per le entry menu non sono stati definiti nel csv fornito");
			    }
			} else {
			    csvReader.close();
			    log.error(
				    "Incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			    throw new EMFError(EMFError.ERROR,
				    "Sono state rilevate incongruenze tra il nome applicazione selezionato e il nome applicazione definito nel csv fornito: uno dei due nomi applicazione è assente oppure i due nomi applicazioni sono diversi");
			}
		    }
		    /* Recupero i menu non presenti nel csv con aiuto in linea */
		    entryMenuListHelperOnLine = selectMenuNotInCsvHelpOnLine(csvMenu);
		}

		/* Chiudo il CSVReader */
		csvReader.close();
	    }

	    /* Devo aggiungere le azioni relative alle pagine eventualmente cancellate */
	    for (AplPaginaWeb paginaWeb : pagineWebList) {
		for (AplAzionePagina azionePagina : paginaWeb.getAplAzionePaginas()) {
		    if (azionePagina.getAplTipoEvento() != null) {
			azioniPaginaList.add(azionePagina);
		    }
		}
	    }

	    StringBuilder azioniPagineMessage = new StringBuilder();
	    if (!pagineWebList.isEmpty()) {
		azioniPagineMessage.append("Pagine: ").append(",");
		for (AplPaginaWeb paginaWeb : pagineWebList) {
		    azioniPagineMessage.append(paginaWeb.getNmPaginaWeb()).append(",");
		}
		azioniPagineMessage.append(" ").append(",");
	    }
	    if (!azioniPaginaList.isEmpty()) {
		azioniPagineMessage.append("Azioni: ").append(",");
		for (AplAzionePagina azionePagina : azioniPaginaList) {
		    azioniPagineMessage.append(azionePagina.getAplPaginaWeb().getNmPaginaWeb())
			    .append(" - ").append(azionePagina.getNmAzionePagina()).append(",");
		}
	    }

	    // Rollback in caso di gestione HELP ON LINE
	    StringBuilder messaggioAiutoInLinea = new StringBuilder(
		    "Impossibile procedere con l'allineamento componenti, l'help on line è presente per i/le seguenti: <br>");
	    if (!entryMenuListHelperOnLine.isEmpty()) {
		messaggioAiutoInLinea.append("<br> Menù: <br>");
		int i = 0;
		for (AplEntryMenu entryMenu : entryMenuListHelperOnLine) {
		    messaggioAiutoInLinea.append(entryMenu.getNmEntryMenu());
		    i++;
		    if (i < pagineWebListHelperOnLine.size()) {
			messaggioAiutoInLinea.append(",");
		    }
		}
	    }

	    if (!pagineWebListHelperOnLine.isEmpty()) {
		messaggioAiutoInLinea.append("<br> Pagine: <br>");
		int i = 0;
		for (AplPaginaWeb paginaWeb : pagineWebListHelperOnLine) {
		    messaggioAiutoInLinea.append(paginaWeb.getNmPaginaWeb());
		    i++;
		    if (i < pagineWebListHelperOnLine.size()) {
			messaggioAiutoInLinea.append(",<br>");
		    }
		}
	    }

	    if (!entryMenuListHelperOnLine.isEmpty() || !pagineWebListHelperOnLine.isEmpty()) {
		messaggioAiutoInLinea.append(
			"<br><br> E' necessario scaricare gli help on line degli elementi elencati, quindi cancellare gli stessi help on line "
				+ "da appositiva sezione. Infine riprovare con l'allineamento componenti");
		ctx.setRollbackOnly();
		log.error(messaggioAiutoInLinea.toString());
		throw new ParerInternalError(messaggioAiutoInLinea.toString());
	    }

	    return azioniPagineMessage.length() > 0 ? azioniPagineMessage.toString() : null;

	} catch (IOException e) {
	    ctx.setRollbackOnly();
	    log.error(e.getMessage(), e);
	    throw new EMFError(EMFError.BLOCKING, e.getMessage());
	} catch (EMFError ex) {
	    ctx.setRollbackOnly();
	    throw ex;
	}
    }

    /**
     * Metodo che inserisce/aggiorna le entrate a menù
     *
     * @param nomeApplicazione    nome applicazione
     * @param nomeEntryMenu       nome entry menu
     * @param descEntryMenu       descrizione entry menu
     * @param numLivelloEntryMenu numero livello
     * @param numOrdineEntryMenu  numero ordine
     * @param nomeEntryMenuPadre  nome entry menu padre
     * @param linkEntryMenu       link
     *
     * @throws Exception errore generico
     */
    public void addMenuEntry(String nomeApplicazione, String nomeEntryMenu, String descEntryMenu,
	    String numLivelloEntryMenu, String numOrdineEntryMenu, String nomeEntryMenuPadre,
	    String linkEntryMenu) throws Exception {
	AplEntryMenu menu;
	if ((menu = userHelper.getAplEntryMenu(nomeEntryMenu, nomeApplicazione)) != null) {
	    userHelper.updateAplEntryMenu(menu, nomeApplicazione, descEntryMenu,
		    numLivelloEntryMenu, numOrdineEntryMenu, nomeEntryMenuPadre, linkEntryMenu);
	} else {
	    userHelper.insertAplEntryMenu(nomeApplicazione, nomeEntryMenu, descEntryMenu,
		    numLivelloEntryMenu, numOrdineEntryMenu, nomeEntryMenuPadre, linkEntryMenu);
	}
    }

    public void addWebPage(String nomeApplicazione, String nomePagina, String descPagina,
	    String tiHelpOnline, String entryMenu) throws Exception {
	AplPaginaWeb page;
	AplVTreeEntryMenu menuTree = userHelper.getAplEntryMenu(null, entryMenu, nomeApplicazione);
	if (menuTree != null) {
	    AplEntryMenu menu = userHelper.getAplEntryMenu(menuTree.getIdEntryMenu());
	    if ((page = userHelper.getAplPaginaWeb(nomePagina, nomeApplicazione)) != null) {
		userHelper.updateAplPaginaWeb(page, descPagina, tiHelpOnline, menu);
	    } else {
		userHelper.insertAplPaginaWeb(nomeApplicazione, nomePagina, descPagina,
			tiHelpOnline, menu);
	    }
	} else {
	    throw new EMFError(EMFError.BLOCKING, "Pagina legata a una entrata a menu inesistente");
	}
    }

    public void addAction(String nomeApplicazione, String nomePagina, String nomeAzione,
	    String descAzione) throws Exception {
	AplAzionePagina action;
	if ((action = userHelper.getAplAzionePagina(nomeAzione, nomePagina,
		nomeApplicazione)) != null) {
	    userHelper.updateAplAzionePagina(action, nomeApplicazione, nomePagina, descAzione);
	} else {
	    userHelper.insertAplAzionePagina(nomeApplicazione, nomePagina, nomeAzione, descAzione);
	}
    }

    public int addService(String nomeApplicazione, String nomeServizio, String descServizio,
	    int numAggiunti) throws Exception {
	AplServizioWeb service;
	if ((service = userHelper.getAplServizioWeb(nomeServizio, nomeApplicazione)) != null) {
	    userHelper.updateAplServizioWeb(service, descServizio);
	} else {
	    userHelper.insertAplServizioWeb(nomeApplicazione, nomeServizio, descServizio);
	    numAggiunti++;
	}
	return numAggiunti;
    }

    /**
     * Metodo che elimina i dati non presenti nella lista, in base al tipo: - ENTRY_MENU - PAGINA -
     * AZIONE - SERVIZIO_WEB
     *
     * @param csvMenu la lista di dati presenti
     * @param tipo    il tipo di dato tra quelli elencati
     *
     * @return il numero di record eliminati
     */
    public int deleteDataNotInCsv(List<PairAuth> csvMenu, String tipo) {
	int numEliminati = 0;
	while (!csvMenu.isEmpty()) {
	    String nomeAppl = (String) csvMenu.get(0).getAppl();
	    List<String> dati = null;
	    List<PairAuth> pairActions = null;
	    if (!tipo.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
		dati = parsePairAuthList(csvMenu, nomeAppl);
	    } else {
		pairActions = parsePairAuthList(csvMenu, nomeAppl);
	    }
	    if (tipo.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
		userHelper.deleteMenuEntries(nomeAppl, dati);
	    } else if (tipo.equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
		userHelper.deletePages(nomeAppl, dati);
	    } else if (tipo.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
		if (pairActions != null) {
		    while (!pairActions.isEmpty()) {
			List<String> azioni = null;
			String pagina = (String) pairActions.get(0).getAppl();
			azioni = parsePairAuthList(pairActions, pagina);
			userHelper.deleteActions(nomeAppl, pagina, azioni);
		    }
		}
	    } else if (tipo.equals(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name())) {
		numEliminati = userHelper.deleteWebServices(nomeAppl, dati);
	    }
	}
	return numEliminati;
    }

    /**
     * Metodo che recupera la lista di azioni, con tipo evento annesso, non più presenti nel CSV ma
     * ancora su DB
     *
     * @param csvAzioni la lista delle azioni presenti nel CSV
     *
     * @return le azioni non più presenti nel CSV, ma ancora su DB, con collegati tipi evento
     */
    public List<AplAzionePagina> selectAzioniNotInCsv(List<PairAuth> csvAzioni) {
	List<AplAzionePagina> azioniPaginaList = new ArrayList<>();
	while (!csvAzioni.isEmpty()) {
	    String nomeAppl = (String) csvAzioni.get(0).getAppl();
	    List<PairAuth> pairActions = parsePairAuthList(csvAzioni, nomeAppl);
	    while (!pairActions.isEmpty()) {
		List<String> azioni = null;
		String pagina = (String) pairActions.get(0).getAppl();
		azioni = parsePairAuthList(pairActions, pagina);
		azioniPaginaList.addAll(userHelper.getActionsNotInCsv(nomeAppl, pagina, azioni));
	    }
	}
	return azioniPaginaList;
    }

    /**
     * Metodo che recupera la lista di pagine, con azioni con tipo evento annesso, non più presenti
     * nel CSV ma ancora su DB
     *
     * @param csvPagine la lista delle pagine presenti nel CSV
     *
     * @return le pagine non più presenti nel CSV, ma ancora su DB, con azioni con collegati tipi
     *         evento
     */
    public List<AplPaginaWeb> selectPagineNotInCsv(List<PairAuth> csvPagine) {
	List<AplPaginaWeb> pagineWebList = new ArrayList<>();
	while (!csvPagine.isEmpty()) {
	    String nomeAppl = (String) csvPagine.get(0).getAppl();
	    List<String> dati = parsePairAuthList(csvPagine, nomeAppl);
	    pagineWebList = userHelper.getPagesNotInCsv(nomeAppl, dati);
	}
	return pagineWebList;
    }

    /**
     * Metodo che recupera la lista di menu, non più presenti nel CSV ma ancora su DB, che stanno
     * per essere cancellati ma hanno associato un help on line
     *
     * @param csvMenu la lista delle voci di menù presenti nel CSV
     *
     * @return le voci di menu non più presenti nel CSV, ma ancora su DB
     */
    public List<AplEntryMenu> selectMenuNotInCsvHelpOnLine(List<PairAuth> csvMenu) {
	List<AplEntryMenu> entryMenuList = new ArrayList<>();
	while (!csvMenu.isEmpty()) {
	    String nomeAppl = (String) csvMenu.get(0).getAppl();
	    List<String> dati = parsePairAuthList(csvMenu, nomeAppl);
	    entryMenuList = userHelper.getMenusNotInCsvPerHelpOnLine(nomeAppl, dati);
	}
	return entryMenuList;
    }

    /**
     * Metodo che recupera la lista di pagine non più presenti nel CSV ma ancora su DB che
     * presentano associato un help on line
     *
     * @param csvPagine la lista delle pagine presenti nel CSV
     *
     * @return le pagine non più presenti nel CSV, ma ancora su DB, con help on line
     */
    public List<AplPaginaWeb> selectPagineNotInCsvAiutoInLinea(List<PairAuth> csvPagine) {
	List<AplPaginaWeb> pagineWebList = new ArrayList<>();
	while (!csvPagine.isEmpty()) {
	    String nomeAppl = (String) csvPagine.get(0).getAppl();
	    List<String> dati = parsePairAuthList(csvPagine, nomeAppl);
	    pagineWebList = userHelper.getPagesNotInCsvAiutoInLinea(nomeAppl, dati);
	}
	return pagineWebList;
    }

    public int deleteDataNotInCsv(List<PairAuth> csvMenu, List<Long> idAzioni) {
	int numEliminati = 0;
	while (!csvMenu.isEmpty()) {
	    userHelper.deleteActions(idAzioni);
	}
	return numEliminati;
    }

    private List parsePairAuthList(List<PairAuth> csvMenu, String nome) {
	int counter = 0;
	String nomeAppl = null;
	Iterator it = csvMenu.listIterator();
	List<String> dati = new ArrayList<>();
	List<PairAuth> azioni = new ArrayList<>();
	while (it.hasNext()) {
	    PairAuth auth = (PairAuth) it.next();
	    if (counter == 0) {
		nomeAppl = (String) auth.getAppl();
		if (nome.equals(nomeAppl)) {
		    if (auth.getAuth() instanceof String) {
			dati.add((String) auth.getAuth());
		    } else if (auth.getAuth() instanceof PairAuth) {
			azioni.add((PairAuth) auth.getAuth());
		    }
		    it.remove();
		}
	    } else {
		String appl = (String) auth.getAppl();
		String dato = null;
		PairAuth azione = null;
		if (auth.getAuth() instanceof String) {
		    dato = (String) auth.getAuth();
		} else if (auth.getAuth() instanceof PairAuth) {
		    azione = (PairAuth) auth.getAuth();
		}
		if (appl.equals(nome)) {
		    if (dato != null) {
			dati.add(dato);
		    } else if (azione != null) {
			azioni.add(azione);
		    }
		    it.remove();
		}
	    }
	    counter++;
	}
	if (!dati.isEmpty()) {
	    return dati;
	} else {
	    return azioni;
	}
    }

    /**
     * Allinea le autorizzazioni dei ruoli relative alle entrate a menù
     *
     * @param applicazione      nome applicazione
     * @param cdVersioneNew     versione
     * @param serviziModificati boolean true/false
     *
     * @throws SQLException                      errore esecuzione query
     * @throws EJBTransactionRolledbackException errore generico
     */
    public void authorizeApplication(String applicazione, String cdVersioneNew,
	    boolean serviziModificati) throws SQLException, EJBTransactionRolledbackException {
	/* Seleziona i ruoli dell'applicazione */
	List<PrfUsoRuoloApplic> ruoli = userHelper.getPrfUsoRuoloApplic(applicazione);
	AplApplic aplApplic = userHelper.getAplApplic(applicazione);
	aplApplic.setCdVersioneComp(cdVersioneNew);
	/* Gestisce le autorizzazioni per ogni ruolo dell'applicazione */
	handleAutors(ruoli);
	/* Gestisce gli URL dei servizi di replica utenti */
	replayUsers(applicazione, serviziModificati);
    }

    /**
     * Gestisce le autorizzazioni per ogni ruolo dell'applicazione per i tipi: - ENTRY_MENU - PAGINA
     * - AZIONE - SERVIZIO_WEB
     *
     * @param ruoli lista ruoli di tipo {@link PrfUsoRuoloApplic}
     */
    private void handleAutors(List<PrfUsoRuoloApplic> ruoli) {
	for (PrfUsoRuoloApplic ruolo : ruoli) {
	    authorizeEntryMenus(ruolo);
	    int counterPages = authorizePages(ruolo);
	    authorizeActions(ruolo, counterPages);
	    authorizeWebServices(ruolo);
	}
    }

    /**
     * Gestisce le autorizzazioni delle entryMenu sul ruolo - Elimina le autorizzazioni di tipo
     * ENTRY_MENU - Aggiorna le dichiarazioni di autorizzazioni e elimina le dichiarazioni
     * ridondanti
     *
     * @param ruolo entity {@link PrfUsoRuoloApplic}
     */
    private void authorizeEntryMenus(PrfUsoRuoloApplic ruolo) {
	/* Elimino le autorizzazioni di tipo Entry Menu presenti */
	userHelper.deleteAuthsFromPrfAutor(ruolo, ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	userHelper.updateDichAutor(ruolo);
	int counter = 1;
	/* Inserisco le autorizzazioni di tipo All_Abilitazioni */
	if (userHelper.getDichAutor(ruolo, ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(),
		ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name()).size() == 1) {
	    List<AplEntryMenu> listAplEntryMenu = userHelper
		    .getListAplEntryMenu(ruolo.getAplApplic().getNmApplic());
	    counter = userHelper.insertAuthAllAbilitazioni(ruolo, listAplEntryMenu, null, null,
		    null, counter, null);
	}
	List<PrfDichAutor> padri = userHelper.getDichAutor(ruolo,
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(),
		ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name());
	/* Inserisco le autorizzazioni di tipo All_Abilitazioni_Child */
	if (!padri.isEmpty()) {
	    counter = userHelper.insertAuthAllAbilitazioniChild(ruolo, padri,
		    ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(), counter, null);
	}
	List<PrfDichAutor> foglie = userHelper.getDichAutor(ruolo,
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(),
		ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
	/* Inserisco le autorizzazioni di tipo Una_Abilitazione */
	if (!foglie.isEmpty()) {
	    userHelper.insertAuthUnaAbilitazione(ruolo, foglie,
		    ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name(), counter, null);
	}
    }

    /**
     * Gestisce le autorizzazioni delle pagine web sul ruolo - Elimina le autorizzazioni di tipo
     * PAGINA - Aggiorna le dichiarazioni di autorizzazioni
     *
     * @param ruolo entity {@link PrfUsoRuoloApplic}
     */
    private int authorizePages(PrfUsoRuoloApplic ruolo) {
	userHelper.deleteAuthsFromPrfAutor(ruolo, ConstPrfDichAutor.TiDichAutor.PAGINA.name());
	int counter = 1;
	/* Inserisco le autorizzazioni di tipo All_Abilitazioni */
	List<AplPaginaWeb> listAplPaginaWeb = userHelper
		.getListAplPaginaWeb(ruolo.getAplApplic().getNmApplic());
	if (userHelper.getDichAutor(ruolo, ConstPrfDichAutor.TiDichAutor.PAGINA.name(),
		ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name()).size() == 1) {
	    counter = userHelper.insertAuthAllAbilitazioni(ruolo, null, listAplPaginaWeb, null,
		    null, counter, null);
	}
	/* Inserisco le autorizzazioni di tipo Una_Abilitazione */
	List<PrfDichAutor> dichiarazioni = userHelper.getDichAutor(ruolo,
		ConstPrfDichAutor.TiDichAutor.PAGINA.name(),
		ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
	if (!dichiarazioni.isEmpty()) {
	    counter = userHelper.insertAuthUnaAbilitazione(ruolo, dichiarazioni,
		    ConstPrfDichAutor.TiDichAutor.PAGINA.name(), counter, null);
	}
	return counter;
    }

    /**
     * Gestisce le autorizzazioni delle azioni sul ruolo - Elimina le autorizzazioni di tipo AZIONE
     * - Aggiorna le dichiarazioni di autorizzazioni
     *
     * @param ruolo        entity {@link PrfUsoRuoloApplic}
     * @param counterPages contatore pagine
     */
    private void authorizeActions(PrfUsoRuoloApplic ruolo, int counterPages) {
	userHelper.deleteAuthsFromPrfAutor(ruolo, ConstPrfDichAutor.TiDichAutor.AZIONE.name());
	int counter = 1;
	MutableInt pages = new MutableInt(counterPages);
	List<PrfDichAutor> dichiarazioni;
	/* Inserisco le autorizzazioni di tipo All_Abilitazioni */
	if (userHelper.getDichAutor(ruolo, ConstPrfDichAutor.TiDichAutor.AZIONE.name(),
		ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name()).size() == 1) {
	    List<AplAzionePagina> listAplAzionePagina = userHelper
		    .getListAplAzionePagina(ruolo.getAplApplic().getNmApplic());
	    List<AplPaginaWeb> listAplPaginaWeb = userHelper
		    .getListAplPaginaWeb(ruolo.getAplApplic().getNmApplic());
	    counter = userHelper.insertAuthAllAbilitazioni(ruolo, null, listAplPaginaWeb,
		    listAplAzionePagina, null, counter, pages);
	}
	/* Inserisco le autorizzazioni di tipo All_Abilitazioni_Child */
	dichiarazioni = userHelper.getDichAutor(ruolo, ConstPrfDichAutor.TiDichAutor.AZIONE.name(),
		ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name());
	if (!dichiarazioni.isEmpty()) {
	    counter = userHelper.insertAuthAllAbilitazioniChild(ruolo, dichiarazioni,
		    ConstPrfDichAutor.TiDichAutor.AZIONE.name(), counter, pages);
	}
	dichiarazioni = userHelper.getDichAutor(ruolo, ConstPrfDichAutor.TiDichAutor.AZIONE.name(),
		ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
	/* Inserisco le autorizzazioni di tipo Una_Abilitazione */
	if (!dichiarazioni.isEmpty()) {
	    userHelper.insertAuthUnaAbilitazione(ruolo, dichiarazioni,
		    ConstPrfDichAutor.TiDichAutor.AZIONE.name(), counter, pages);
	}
    }

    /**
     * Gestisce le autorizzazioni dei servizi web sul ruolo - Elimina le autorizzazioni di tipo
     * SERVIZIO_WEB - Aggiorna le dichiarazioni di autorizzazioni
     *
     * @param ruolo entity {@link PrfUsoRuoloApplic}
     */
    private void authorizeWebServices(PrfUsoRuoloApplic ruolo) {
	userHelper.deleteAuthsFromPrfAutor(ruolo,
		ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
	int counter = 1;
	/* Inserisco le autorizzazioni di tipo All_Abilitazioni */
	if (userHelper.getDichAutor(ruolo, ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(),
		ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name()).size() == 1) {
	    List<AplServizioWeb> listAplServizioWeb = userHelper
		    .getListAplServizioWeb(ruolo.getAplApplic().getNmApplic());
	    counter = userHelper.insertAuthAllAbilitazioni(ruolo, null, null, null,
		    listAplServizioWeb, counter, null);
	}
	List<PrfDichAutor> dichiarazioni = userHelper.getDichAutor(ruolo,
		ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(),
		ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
	/* Inserisco le autorizzazioni di tipo Una_Abilitazione */
	if (!dichiarazioni.isEmpty()) {
	    userHelper.insertAuthUnaAbilitazione(ruolo, dichiarazioni,
		    ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name(), counter, null);
	}
    }

    public void replayUsers(String nmApplic, boolean hasBeenModified)
	    throws EJBTransactionRolledbackException {
	userHelper.checkAplApplicURL(nmApplic, hasBeenModified);
    }

    /**
     * Salva un ruolo su DB, sia che esso sia un ruolo nuovo, sia che rappresenti la modifica di un
     * ruolo già esistente
     *
     * @param param                   parametri per il logging
     * @param usoRuoloApplicTableBean table bean {@link PrfRuoloCategoriaTableBean}
     * @param ruolo                   row bean {@link PrfVLisRuoloRowBean}
     * @param entryMenuList           table bean {@link PrfVLisDichAutorTableBean}
     * @param pagesList               table bean {@link PrfVLisDichAutorTableBean}
     * @param actionsList             table bean {@link PrfVLisDichAutorTableBean}
     * @param servicesList            table bean {@link PrfVLisDichAutorTableBean}
     * @param applicationsDeleteList  lista id applicazioni da cancellare
     * @param entryMenuDeleteList     lista menu da cancellare
     * @param pagesDeleteList         lista pagine da cancellare
     * @param actionsDeleteList       lista azioni da cancellare
     * @param servicesDeleteList      lista servizi da cenallare
     * @param servicesDeleteListAppId lista id applicazioni servizi da cancellare
     *
     * @return pk
     *
     * @throws ParerUserError errore generico
     */
    private BigDecimal savePrfRuolo(LogParam param,
	    PrfRuoloCategoriaTableBean ruoloCategoriaTableBean,
	    PrfUsoRuoloApplicTableBean usoRuoloApplicTableBean, PrfVLisRuoloRowBean ruolo,
	    PrfVLisDichAutorTableBean entryMenuList, PrfVLisDichAutorTableBean pagesList,
	    PrfVLisDichAutorTableBean actionsList, PrfVLisDichAutorTableBean servicesList,
	    Set<String> categorieDeleteList, Set<BigDecimal> applicationsDeleteList,
	    Set<BigDecimal> entryMenuDeleteList, Set<BigDecimal> pagesDeleteList,
	    Set<BigDecimal> actionsDeleteList, Set<BigDecimal> servicesDeleteList,
	    Set<BigDecimal> servicesDeleteListAppId) throws ParerUserError {
	/* Modifico il ruolo e i suoi componenti */
	PrfRuolo ruoloEntity = new PrfRuolo();
	/*
	 * Recupero il ruolo, se presente, altrimenti creo nuove istanze: un ruolo e tanti
	 * usoRuoloApplic quante sono le applicazioni definite per quel determinato ruolo
	 */
	List<PrfRuoloCategoria> ruoloCategoriaList = new ArrayList<>();
	List<PrfUsoRuoloApplic> usoruoli = new ArrayList<>();
	boolean modifica = ruolo.getIdRuolo() != null;
	if (modifica) {
	    ruoloEntity = userHelper.getRuoloById(ruolo.getIdRuolo());
	}

	ruoloEntity.setNmRuolo(ruolo.getNmRuolo());
	ruoloEntity.setDsRuolo(ruolo.getDsRuolo());
	ruoloEntity.setTiRuolo(ruolo.getTiRuolo());
	ruoloEntity.setFlAllineamentoInCorso("0");

	String destAllinea1 = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.DESTINATARIO_ALLINEA_RUOLI_1.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	if (StringUtils.isNotBlank(destAllinea1) && !destAllinea1.equalsIgnoreCase("nullo")) {
	    ruoloEntity.setTiStatoRichAllineaRuoli_1(
		    ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
	} else {
	    ruoloEntity.setTiStatoRichAllineaRuoli_1(
		    ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO.name());
	}
	String destAllinea2 = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.NmParamApplic.DESTINATARIO_ALLINEA_RUOLI_2.name(), null, null,
		it.eng.saceriam.common.Constants.TipoIamVGetValAppart.APPLIC);
	if (StringUtils.isNotBlank(destAllinea2) && !destAllinea2.equalsIgnoreCase("nullo")) {
	    ruoloEntity.setTiStatoRichAllineaRuoli_2(
		    ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
	} else {
	    ruoloEntity.setTiStatoRichAllineaRuoli_2(
		    ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO.name());
	}

	/* Elimino le categorie da eliminare (cancellando il record in PrfRuoloCategoria) */
	if (!categorieDeleteList.isEmpty()) {
	    Iterator<String> it = categorieDeleteList.iterator();
	    while (it.hasNext()) {
		String tiCategRuolo = it.next();
		userHelper.deletePrfRuoloCategoriaByRuoloAndCateg(ruoloEntity.getIdRuolo(),
			tiCategRuolo);
	    }
	}

	/* Elimino le applicazioni da eliminare (cancellando il record in PrfUsoRuoloApplic) */
	if (!applicationsDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = applicationsDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idApplic = it.next();
		userHelper.deletePrfUsoRuoloApplicByUserAndApplic(ruoloEntity.getIdRuolo(),
			idApplic);
	    }
	}

	/* Elimino le dichiarazioni da eliminare */
	if (!entryMenuDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = entryMenuDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idDichAutor = it.next();
		userHelper.deleteDichAutorById(idDichAutor);
	    }
	}
	if (!pagesDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = pagesDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idDichAutor = it.next();
		userHelper.deleteDichAutorById(idDichAutor);
	    }
	}
	if (!actionsDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = actionsDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idDichAutor = it.next();
		userHelper.deleteDichAutorById(idDichAutor);
	    }
	}
	if (!servicesDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = servicesDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idDichAutor = it.next();
		userHelper.deleteDichAutorById(idDichAutor);
	    }
	}

	entryMenuList.clearSortingRule();
	pagesList.clearSortingRule();
	actionsList.clearSortingRule();
	servicesList.clearSortingRule();
	entryMenuList.sort();
	pagesList.sort();
	actionsList.sort();
	servicesList.sort();

	/*
	 * Inserisco o modifico i campi delle categorie associate al ruolo (agisco su
	 * PrfRuoloCategoria)
	 */
	for (PrfRuoloCategoriaRowBean ruoloCategoriaRowBean : ruoloCategoriaTableBean) {
	    PrfRuoloCategoria ruoloCategoriaTmp;
	    if (ruoloCategoriaRowBean.getIdRuoloCategoria() != null) {
		ruoloCategoriaTmp = userHelper.findById(PrfRuoloCategoria.class,
			ruoloCategoriaRowBean.getIdRuoloCategoria());
	    } else {
		ruoloCategoriaTmp = new PrfRuoloCategoria();
		ruoloCategoriaTmp.setTiCategRuolo(ruoloCategoriaRowBean.getTiCategRuolo());
		ruoloCategoriaTmp.setPrfRuolo(ruoloEntity);
	    }
	    ruoloCategoriaList.add(ruoloCategoriaTmp);
	    ruoloEntity.setPrfRuoloCategorias(ruoloCategoriaList);
	}

	/*
	 * Inserisco o modifico i campi delle applicazioni associate al ruolo (agisco su
	 * PrfUsoRuoloApplic)
	 */
	for (PrfUsoRuoloApplicRowBean usoRuoloApplicRowBean : usoRuoloApplicTableBean) {
	    PrfUsoRuoloApplic usoRuoloTmp;
	    if (usoRuoloApplicRowBean.getIdUsoRuoloApplic() != null) {
		usoRuoloTmp = userHelper
			.getPrfUsoRuoloApplicById(usoRuoloApplicRowBean.getIdUsoRuoloApplic());
	    } else {
		AplApplic applic = userHelper.getAplApplic(usoRuoloApplicRowBean.getIdApplic());
		usoRuoloTmp = new PrfUsoRuoloApplic();
		usoRuoloTmp.setAplApplic(applic);
		usoRuoloTmp.setPrfRuolo(ruoloEntity);
	    }
	    usoruoli.add(usoRuoloTmp);
	    ruoloEntity.setPrfUsoRuoloApplics(usoruoli);
	}

	/* Istanzio gli uso ruolo applic */
	for (PrfUsoRuoloApplic ur : usoruoli) {
	    if (ur.getPrfDichAutors() == null) {
		ur.setPrfDichAutors(new ArrayList<>());
	    }
	}

	/* Inserisco o modifico i campi */
	for (PrfVLisDichAutorRowBean row : entryMenuList) {
	    PrfUsoRuoloApplic usoRuoloTmp = new PrfUsoRuoloApplic();
	    PrfDichAutor dich;
	    if (row.getIdDichAutor() != null) {
		dich = userHelper.getDichAutorById(row.getIdDichAutor());
		usoRuoloTmp = dich.getPrfUsoRuoloApplic();
	    } else {
		dich = new PrfDichAutor();
		/* Se l'autorizzazione è nuova, recupero il PrfUsoRuoloApplic appropriato */
		for (int i = 0; i < usoruoli.size(); i++) {
		    if (usoruoli.get(i).getAplApplic().getNmApplic().equals(row.getNmApplic())) {
			usoRuoloTmp = usoruoli.get(i);
			/* Passaggio per riferimento */
			dich.setPrfUsoRuoloApplic(usoRuoloTmp);
		    }
		}
	    }
	    dich.setTiDichAutor(row.getTiDichAutor());
	    dich.setTiScopoDichAutor(row.getTiScopoDichAutor());
	    if (row.getTiScopoDichAutor()
		    .equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name())) {
		dich.setAplEntryMenuPadre(userHelper.getAplEntryMenu(row.getIdEntryMenu()));
	    } else if (row.getTiScopoDichAutor()
		    .equals(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name())) {
		dich.setAplEntryMenuFoglia(userHelper.getAplEntryMenu(row.getIdEntryMenu()));
	    } else {
		dich.setAplEntryMenuFoglia(null);
		dich.setAplEntryMenuPadre(null);
	    }
	    if (row.getIdDichAutor() != null) {
		userHelper.flushEntityManager();
	    }
	    usoRuoloTmp.getPrfDichAutors().add(dich);
	}

	for (PrfVLisDichAutorRowBean row : pagesList) {
	    PrfUsoRuoloApplic usoRuoloTmp = new PrfUsoRuoloApplic();
	    PrfDichAutor dich;
	    if (row.getIdDichAutor() != null) {
		dich = userHelper.getDichAutorById(row.getIdDichAutor());
		usoRuoloTmp = dich.getPrfUsoRuoloApplic();
	    } else {
		dich = new PrfDichAutor();
		/* Se l'autorizzazione è nuova, recupero il PrfUsoRuoloApplic appropriato */
		for (int i = 0; i < usoruoli.size(); i++) {
		    if (usoruoli.get(i).getAplApplic().getNmApplic().equals(row.getNmApplic())) {
			usoRuoloTmp = usoruoli.get(i);
			/* Passaggio per riferimento */
			dich.setPrfUsoRuoloApplic(usoRuoloTmp);
		    }
		}
	    }
	    dich.setTiDichAutor(row.getTiDichAutor());
	    dich.setTiScopoDichAutor(row.getTiScopoDichAutor());
	    if (row.getIdPaginaWeb() != null) {
		dich.setAplPaginaWeb(userHelper.getAplPaginaWeb(row.getIdPaginaWeb()));
	    } else {
		dich.setAplPaginaWeb(null);
	    }
	    if (row.getIdDichAutor() != null) {
		userHelper.flushEntityManager();
	    }
	    usoRuoloTmp.getPrfDichAutors().add(dich);
	}

	for (PrfVLisDichAutorRowBean row : actionsList) {
	    PrfUsoRuoloApplic usoRuoloTmp = new PrfUsoRuoloApplic();
	    PrfDichAutor dich;
	    if (row.getIdDichAutor() != null) {
		dich = userHelper.getDichAutorById(row.getIdDichAutor());
		usoRuoloTmp = dich.getPrfUsoRuoloApplic();
	    } else {
		dich = new PrfDichAutor();
		/* Se l'autorizzazione è nuova, recupero il PrfUsoRuoloApplic appropriato */
		for (int i = 0; i < usoruoli.size(); i++) {
		    if (usoruoli.get(i).getAplApplic().getNmApplic().equals(row.getNmApplic())) {
			usoRuoloTmp = usoruoli.get(i);
			/* Passaggio per riferimento */
			dich.setPrfUsoRuoloApplic(usoRuoloTmp);
		    }
		}
	    }
	    dich.setTiDichAutor(row.getTiDichAutor());
	    dich.setTiScopoDichAutor(row.getTiScopoDichAutor());
	    if (row.getIdPaginaWebAzio() != null) {
		dich.setAplPaginaWeb(userHelper.getAplPaginaWeb(row.getIdPaginaWebAzio()));
	    } else {
		dich.setAplPaginaWeb(null);
	    }
	    if (row.getIdAzionePagina() != null) {
		dich.setAplAzionePagina(userHelper.getAplAzionePagina(row.getIdAzionePagina()));
	    } else {
		dich.setAplAzionePagina(null);
	    }
	    if (row.getIdDichAutor() != null) {
		userHelper.flushEntityManager();
	    }
	    usoRuoloTmp.getPrfDichAutors().add(dich);
	}

	for (PrfVLisDichAutorRowBean row : servicesList) {
	    PrfUsoRuoloApplic usoRuoloTmp = new PrfUsoRuoloApplic();
	    PrfDichAutor dich;
	    if (row.getIdDichAutor() != null) {
		dich = userHelper.getDichAutorById(row.getIdDichAutor());
		usoRuoloTmp = dich.getPrfUsoRuoloApplic();
	    } else {
		/* E' un nuovo inserimento: tengo traccia dell'applicazione a cui fa riferimento */
		servicesDeleteListAppId.add(row.getIdApplic());
		dich = new PrfDichAutor();
		/* Se l'autorizzazione è nuova, recupero il PrfUsoRuoloApplic appropriato */
		for (int i = 0; i < usoruoli.size(); i++) {
		    if (usoruoli.get(i).getAplApplic().getNmApplic().equals(row.getNmApplic())) {
			usoRuoloTmp = usoruoli.get(i);
			/* Passaggio per riferimento */
			dich.setPrfUsoRuoloApplic(usoRuoloTmp);
		    }
		}
	    }
	    dich.setTiDichAutor(row.getTiDichAutor());
	    dich.setTiScopoDichAutor(row.getTiScopoDichAutor());
	    if (row.getIdServizioWeb() != null) {
		dich.setAplServizioWeb(userHelper.getAplServizioWeb(row.getIdServizioWeb()));
	    } else {
		dich.setAplServizioWeb(null);
	    }
	    if (row.getIdDichAutor() != null) {
		userHelper.flushEntityManager();
	    }
	    usoRuoloTmp.getPrfDichAutors().add(dich);
	}

	/*
	 * Registro in LOG_USER_DA_REPLIC per ogni applicazione aggiunta o tolta che autorizza
	 * servizi web
	 */
	// "Filtro" le applicazioni non considerando Sacer_Iam e Dpi
	List<AplApplic> applicUsateFiltrateList = new ArrayList<>();
	if (!servicesDeleteListAppId.isEmpty()) {
	    applicUsateFiltrateList = userHelper.getAplApplicFiltrate(servicesDeleteListAppId);
	}
	for (AplApplic applicUsataFiltrata : applicUsateFiltrateList) {
	    try {
		userHelper.registraLogUserDaReplic(ruolo.getIdRuolo(), applicUsataFiltrata);
	    } catch (Exception ex) {
		ctx.setRollbackOnly();
		log.error(ex.getMessage(), ex);
		throw new ParerUserError(
			"Errore durante la registrazione di un record nella tabella LOG_USER_DA_REPLIC");
	    }
	}

	userHelper.getEntityManager().flush();
	ruoloEntity = userHelper.salvaPrfRuolo(ruoloEntity, modifica);
	userHelper.getEntityManager().flush();
	BigDecimal idEntita = new BigDecimal(ruoloEntity.getIdRuolo());
	/*
	 * Codice aggiuntivo per il logging...
	 */
	sacerLogEjb.log(param.getTransactionLogContext(),
		paramHelper.getParamApplicApplicationName(), param.getNomeUtente(),
		param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RUOLO, idEntita,
		param.getNomePagina());

	return idEntita;
    }

    /**
     * Elimina un ruolo dato il suo id registrando nel log per ogni applicazione che autorizza
     * servizi web
     *
     * @param param       parametri per il logging
     * @param idRuolo     id ruolo
     * @param idApplicSet lista id distinti applicativo
     *
     * @return risultato operazione
     *
     * @throws ParerUserError errore generico
     */
    public String deletePrfRuolo(LogParam param, BigDecimal idRuolo, Set<BigDecimal> idApplicSet)
	    throws ParerUserError {
	PrfRuolo ruolo = userHelper.getRuoloById(idRuolo);
	String esito = Constants.Esito.OK.name();
	try {
	    if (ruolo.getUsrUsoRuoloUserDefaults().isEmpty()) {
		if (ruolo.getUsrUsoRuoloDiches().isEmpty()) {
		    /*
		     * Registro in LOG_USER_DA_REPLIC per ogni applicazione che autorizza servizi
		     * web
		     */
		    Iterator<BigDecimal> itera = idApplicSet.iterator();
		    while (itera.hasNext()) {
			/* Recupero l'entity AplApplic interessata */
			BigDecimal idApplic = itera.next();
			AplApplic applic = userHelper.getAplApplic(idApplic);
			try {
			    userHelper.registraLogUserDaReplic(idRuolo, applic);
			} catch (Exception ex) {
			    ctx.setRollbackOnly();
			    log.error(ex.getMessage(), ex);
			    throw new ParerUserError(
				    "Errore durante la registrazione di un record nella tabella LOG_USER_DA_REPLIC");
			}
		    }
		    /*
		     * Codice aggiuntivo per il logging...
		     */
		    sacerLogEjb.log(param.getTransactionLogContext(),
			    paramHelper.getParamApplicApplicationName(), param.getNomeUtente(),
			    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RUOLO,
			    new BigDecimal(ruolo.getIdRuolo()), param.getNomePagina());
		    userHelper.deletePrfRuolo(ruolo);
		} else {
		    esito = "Il ruolo non può essere eliminato perchè usato "
			    + "in almeno una dichiarazione di abilitazione ad operare in una organizzazione nell'applicazione "
			    + ruolo.getUsrUsoRuoloDiches().get(0).getUsrDichAbilOrganiz()
				    .getUsrUsoUserApplic().getAplApplic().getNmApplic();
		}
	    } else {
		esito = "Il ruolo non può essere eliminato perchè usato "
			+ "come ruolo di default per almeno un utente che usa l'applicazione "
			+ ruolo.getUsrUsoRuoloUserDefaults().get(0).getUsrUsoUserApplic()
				.getAplApplic().getNmApplic();
	    }
	} catch (Exception ex) {
	    ctx.setRollbackOnly();
	    log.error(ex.getMessage(), ex);
	    throw new ParerUserError("Errore durante la cancellazione del ruolo");
	}
	return esito;
    }

    /**
     * Elimina un ruolo dato il suo id registrando nel log per ogni applicazione che autorizza
     * servizi web
     *
     * @param idRuolo     id ruolo
     * @param idApplicSet lista distinta id applicazione
     *
     * @return risultato operazione
     *
     * @throws ParerUserError errore generico
     */
    public String deletePrfRuolo(BigDecimal idRuolo, Set<BigDecimal> idApplicSet)
	    throws ParerUserError {
	PrfRuolo ruolo = userHelper.getRuoloById(idRuolo);
	String esito = Constants.Esito.OK.name();
	try {
	    if (ruolo.getUsrUsoRuoloUserDefaults().isEmpty()) {
		if (ruolo.getUsrUsoRuoloDiches().isEmpty()) {
		    /*
		     * Registro in LOG_USER_DA_REPLIC per ogni applicazione che autorizza servizi
		     * web
		     */
		    Iterator<BigDecimal> itera = idApplicSet.iterator();
		    while (itera.hasNext()) {
			/* Recupero l'entity AplApplic interessata */
			BigDecimal idApplic = itera.next();
			AplApplic applic = userHelper.getAplApplic(idApplic);
			try {
			    userHelper.registraLogUserDaReplic(idRuolo, applic);
			} catch (Exception ex) {
			    ctx.setRollbackOnly();
			    log.error(ex.getMessage(), ex);
			    throw new ParerUserError(
				    "Errore durante la registrazione di un record nella tabella LOG_USER_DA_REPLIC");
			}
		    }
		    userHelper.deletePrfRuolo(ruolo);
		} else {
		    esito = "Il ruolo non può essere eliminato perchè usato "
			    + "in almeno una dichiarazione di abilitazione ad operare in una organizzazione nell'applicazione "
			    + ruolo.getUsrUsoRuoloDiches().get(0).getUsrDichAbilOrganiz()
				    .getUsrUsoUserApplic().getAplApplic().getNmApplic();
		}
	    } else {
		esito = "Il ruolo non può essere eliminato perchè usato "
			+ "come ruolo di default per almeno un utente che usa l'applicazione "
			+ ruolo.getUsrUsoRuoloUserDefaults().get(0).getUsrUsoUserApplic()
				.getAplApplic().getNmApplic();
	    }
	} catch (Exception ex) {
	    ctx.setRollbackOnly();
	    log.error(ex.getMessage(), ex);
	    throw new ParerUserError("Errore durante la cancellazione del ruolo");
	}
	return esito;
    }

    /**
     * Allinea le autorizzazioni per il ruolo idRuolo
     *
     * @param idRuolo l'id del ruolo
     */
    public void alignAuthsRuoli(BigDecimal idRuolo) {
	PrfRuolo ruolo = userHelper.getRuoloById(idRuolo);
	handleAutors(ruolo.getPrfUsoRuoloApplics());
    }

    public BigDecimal saveAndAlignRole(LogParam param,
	    PrfRuoloCategoriaTableBean ruoloCategoriaTableBean,
	    PrfUsoRuoloApplicTableBean usoRuoloApplicTableBean, PrfVLisRuoloRowBean ruolo,
	    PrfVLisDichAutorTableBean entryMenuList, PrfVLisDichAutorTableBean pagesList,
	    PrfVLisDichAutorTableBean actionsList, PrfVLisDichAutorTableBean servicesList,
	    Set<String> categorieDeleteList, Set<BigDecimal> applicationsDeleteList,
	    Set<BigDecimal> entryMenuDeleteList, Set<BigDecimal> pagesDeleteList,
	    Set<BigDecimal> actionsDeleteList, Set<BigDecimal> servicesDeleteList,
	    Set<BigDecimal> serviceDeleteListAppId) throws ParerUserError {
	BigDecimal idRuolo = null;
	try {
	    /* Prima salvo il ruolo... */
	    idRuolo = savePrfRuolo(param, ruoloCategoriaTableBean, usoRuoloApplicTableBean, ruolo,
		    entryMenuList, pagesList, actionsList, servicesList, categorieDeleteList,
		    applicationsDeleteList, entryMenuDeleteList, pagesDeleteList, actionsDeleteList,
		    servicesDeleteList, serviceDeleteListAppId);
	    /* ... poi allineo le autorizzazioni */
	    alignAuthsRuoli(idRuolo);
	} catch (Exception ex) {
	    ctx.setRollbackOnly();
	    log.error(ex.getMessage(), ex);
	    throw new ParerUserError("Errore durante il salvataggio del ruolo");
	}
	return idRuolo;
    }

    /**
     * Metodo per eseguire il salvataggio (creazione/modifica) di un utente. Le operazioni eseguite
     * in questo metodo sono: - Creazione/Ricerca dell'entity dell'utente dato come parametro -
     * Eliminazione delle dichiarazioni rimosse dalle liste (solo se l'utente viene modificato) -
     * Inserimento delle nuove dichiarazioni di abilitazione - Salvataggio dell'utente
     *
     * @param param                         logParam
     * @param user                          il rowBean contenente i dati dell'utente
     * @param indIpTB
     * @param usrUsoUserApplicTableBean
     * @param ruoliDefault                  la lista di ruoli di default
     * @param orgDich
     * @param tipiDato
     * @param dichAbilEnteConvenz
     * @param indIpDeleteList
     * @param applicationsDeleteList
     * @param defaultRolesDeleteList        la lista di dichiarazioni di ruoli di default da
     *                                      eliminare
     * @param dichOrganizDeleteList
     * @param dichTipiDatoDeleteList
     * @param dichAbilEnteConvenzDeleteList
     * @param idRichGestUser
     * @param idAppartUserRich
     *
     * @return l'id dell'utente
     */
    private UsrUser saveUser(LogParam param, UsrUserRowBean user, UsrIndIpUserTableBean indIpTB,
	    UsrUsoUserApplicTableBean usrUsoUserApplicTableBean,
	    UsrUsoRuoloUserDefaultTableBean ruoliDefault, UsrDichAbilOrganizTableBean orgDich,
	    UsrDichAbilDatiTableBean tipiDato, UsrDichAbilEnteConvenzTableBean dichAbilEnteConvenz,
	    Set<BigDecimal> indIpDeleteList, Set<BigDecimal> applicationsDeleteList,
	    Set<BigDecimal> defaultRolesDeleteList, Set<BigDecimal> dichOrganizDeleteList,
	    Set<BigDecimal> dichTipiDatoDeleteList, Set<BigDecimal> dichAbilEnteConvenzDeleteList,
	    BigDecimal idRichGestUser, BigDecimal idAppartUserRich) {
	boolean modifica = user.getIdUserIam() != null;
	log.info("Salvo i nuovi dati dell'utente");
	boolean isEnteSiamAppartChanged = false;
	UsrUser userEntity = new UsrUser();
	List<UsrUsoUserApplic> usoUtenti = new ArrayList<>();
	List<UsrIndIpUser> usoIndIpUser = new ArrayList<>();
	List<ObjectsToLogBefore> listaOggettiDaLoggare = new ArrayList<>();
	if (modifica) {
	    /* Modifico l'utente e i suoi componenti */
	    userEntity = userHelper.findUserById(user.getIdUserIam().longValue());
	    if (userEntity.getTipoUser().equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())
		    && user.getTipoUser().equals(ApplEnum.TipoUser.PERSONA_FISICA.name())) {
		byte[] binarySalt = PwdUtil.generateSalt();
		userHelper.resetPwd(user.getIdUserIam().longValue(),
			PwdUtil.encodePBKDF2Password(binarySalt, user.getCdPsw()),
			PwdUtil.encodeUFT8Base64String(binarySalt));

		// Registro la password dell'utente nella tabella USR_OLD_PSW
		saveOldPsw(user.getNmUserid());
	    }

	    if (userEntity.getTipoUser().equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())
		    && user.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
		byte[] binarySalt = PwdUtil.generateSalt();
		userEntity.setCdPsw(PwdUtil.encodePBKDF2Password(binarySalt, user.getCdPsw()));
		userEntity.setCdSalt(PwdUtil.encodeUFT8Base64String(binarySalt));
		// Registro la password dell'utente nella tabella USR_OLD_PSW
		saveOldPsw(user.getNmUserid());
	    }

	    // Traccio se ho modificato l'ente di appartenenza
	    if (userEntity.getOrgEnteSiam().getIdEnteSiam() != user.getIdEnteSiam().longValue()) {
		listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
			param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
			SacerLogConstants.TIPO_OGGETTO_UTENTE, user.getIdUserIam(),
			param.getNomePagina());
		isEnteSiamAppartChanged = true;
	    }
	} else {
	    /* Nuovo utente: popolo i campi e le associazioni alle altre entity */
	    byte[] binarySalt = PwdUtil.generateSalt();
	    userEntity.setUsrUsoUserApplics(usoUtenti);
	    userEntity.setUsrIndIpUsers(usoIndIpUser);
	    userEntity.setDtRegPsw(user.getDtRegPsw());
	    userEntity.setCdSalt(PwdUtil.encodeUFT8Base64String(binarySalt));
	    userEntity.setCdPsw(PwdUtil.encodePBKDF2Password(binarySalt, user.getCdPsw()));
	}

	log.debug(
		"MAC 30075 - Sono all'interno del metodo saveUser dove salvo ogni dato dell'utente "
			+ user.getNmUserid());

	userEntity.setNmCognomeUser(user.getNmCognomeUser());
	userEntity.setNmNomeUser(user.getNmNomeUser());
	userEntity.setNmUserid(user.getNmUserid());
	userEntity.setFlAttivo(user.getFlAttivo());
	userEntity.setCdFisc(user.getCdFisc());
	userEntity.setDsEmail(user.getDsEmail());
	userEntity.setDsEmailSecondaria(user.getDsEmailSecondaria());
	userEntity.setFlContrIp(user.getFlContrIp());
	userEntity.setTipoUser(user.getTipoUser());
	userEntity.setTipoAuth(user.getTipoAuth());
	userEntity.setDtIniCert(user.getDtIniCert());
	userEntity.setDtFinCert(user.getDtFinCert());

	userEntity.setDtScadPsw(user.getDtScadPsw());
	userEntity.setFlRespEnteConvenz(
		user.getFlRespEnteConvenz() != null ? user.getFlRespEnteConvenz() : "0");
	userEntity.setFlAbilEntiCollegAutom(user.getFlAbilEntiCollegAutom());
	userEntity.setFlAbilOrganizAutom(user.getFlAbilOrganizAutom());
	userEntity.setFlAbilFornitAutom(user.getFlAbilFornitAutom());

	/* TODO DA TOGLIERE */
	userEntity.setFlUserAdmin("0");
	userEntity.setFlUtenteDitta("0");

	if (user.getIdSistemaVersante() != null) {
	    AplSistemaVersante sistemaVersante = sistemiVersantiHelper
		    .findById(AplSistemaVersante.class, user.getIdSistemaVersante());
	    userEntity.setAplSistemaVersante(sistemaVersante);
	} else {
	    userEntity.setAplSistemaVersante(null);
	}

	if (user.getIdEnteSiam() != null) {
	    OrgEnteSiam enteSiam = userHelper.findById(OrgEnteSiam.class, user.getIdEnteSiam());
	    userEntity.setOrgEnteSiam(enteSiam);
	} else {
	    userEntity.setOrgEnteSiam(null);
	}

	log.info("Elimino gli indirizzi IP da eliminare");
	/* Elimino gli indirizzi IP da eliminare (cancellando il record in UsrIndIpUser) */
	if (!indIpDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = indIpDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idIndIpUser = it.next();
		userHelper.deleteUsrIndIpUser(idIndIpUser);
	    }
	}

	log.info("Elimino le applicazioni da eliminare");
	/* Elimino le applicazioni da eliminare (cancellando il record in UsrUsoUserApplic) */
	if (!applicationsDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = applicationsDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idUserApplic = it.next();
		userHelper.deleteUsrUsoUserApplic(idUserApplic);
	    }
	}

	log.info(
		"Elimino le dichiarazioni da eliminare: ruoli, organizzazioni, tipi dato, enti convenzionati");
	/* Elimino le dichiarazioni da eliminare: ruoli, organizzazioni, tipi dato */
	if (!defaultRolesDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = defaultRolesDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idUsoRuolo = it.next();
		userHelper.deleteUsoRuoloUserDefault(idUsoRuolo);
	    }
	}
	if (!dichOrganizDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = dichOrganizDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idDichAbil = it.next();
		userHelper.deleteUsrDichAbilOrganiz(idDichAbil);
	    }
	}
	if (!dichTipiDatoDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = dichTipiDatoDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idDichAbil = it.next();
		userHelper.deleteUsrDichAbilDati(idDichAbil);
	    }
	}
	if (!dichAbilEnteConvenzDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = dichAbilEnteConvenzDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idDichAbil = it.next();
		userHelper.deleteUsrDichAbilEnteConvenz(idDichAbil);
	    }
	}

	log.info("Inserisco o modifico i campi degli indirizzi IP associati all'utente");
	/*
	 * Inserisco o modifico i campi degli indirizzi IP associati all'utente (agisco su
	 * UsrIndIpUser)
	 */
	for (UsrIndIpUserRowBean indIpRB : indIpTB) {
	    UsrIndIpUser indIp;
	    if (indIpRB.getIdIndIpUser() != null) {
		indIp = userHelper.getUsrIndIpUserById(indIpRB.getIdIndIpUser());
	    } else {
		/* E' nuovo */
		indIp = new UsrIndIpUser();
		indIp.setCdIndIpUser(indIpRB.getCdIndIpUser());
		indIp.setUsrUser(userEntity);
	    }
	    usoIndIpUser.add(indIp);
	    userEntity.setUsrIndIpUsers(usoIndIpUser);
	}

	log.info("Inserisco o modifico i campi della applicazioni associate all'utente");
	/*
	 * Inserisco o modifico i campi della applicazioni associate all'utente (agisco su
	 * UsrUsoUserApplic)
	 */
	for (UsrUsoUserApplicRowBean usrUsoUserApplicRowBean : usrUsoUserApplicTableBean) {
	    UsrUsoUserApplic usoUserTmp = null;
	    if (usrUsoUserApplicRowBean.getIdUsoUserApplic() != null) {
		usoUserTmp = userHelper
			.getUsrUsoUserApplicById(usrUsoUserApplicRowBean.getIdUsoUserApplic());
	    } else {
		AplApplic applic = userHelper.getAplApplic(usrUsoUserApplicRowBean.getIdApplic());
		usoUserTmp = new UsrUsoUserApplic();
		usoUserTmp.setAplApplic(applic);
		usoUserTmp.setUsrUser(userEntity);
	    }
	    usoUtenti.add(usoUserTmp);
	    userEntity.setUsrUsoUserApplics(usoUtenti);
	}

	/*
	 * Istanzio le liste degli UsrUsoUserApplic: - UsrUsoRuoloUserDefaul - UsrDichAbilOrganiz -
	 * UsrDichAbilDati
	 */
	for (UsrUsoUserApplic ua : usoUtenti) {
	    if (ua.getUsrUsoRuoloUserDefaults() == null) {
		ua.setUsrUsoRuoloUserDefaults(new ArrayList<>());
	    }
	    if (ua.getUsrDichAbilOrganizs() == null) {
		ua.setUsrDichAbilOrganizs(new ArrayList<>());
	    }
	    if (ua.getUsrDichAbilDatis() == null) {
		ua.setUsrDichAbilDatis(new ArrayList<>());
	    }
	}

	log.info("Inserisco le dichiarazioni di abilitazione ruoli default");
	/* Inserisco le dichiarazioni di abilitazione ruoli default */
	for (UsrUsoRuoloUserDefaultRowBean row : ruoliDefault) {
	    /*
	     * Inserisco la dichiarazione se non è già presente e quindi non ha un id
	     */
	    UsrUsoUserApplic usoUserTmp = new UsrUsoUserApplic();
	    if (row.getBigDecimal("id_uso_ruolo_user_default") == null) {
		UsrUsoRuoloUserDefault usoRuolo = new UsrUsoRuoloUserDefault();
		PrfRuolo ruolo = userHelper.getRuoloById(row.getIdRuolo());
		usoRuolo.setPrfRuolo(ruolo);

		/* Se la dichiarazione è nuova, recupero il UsrUsoUserApplic appropriato */
		for (int i = 0; i < usoUtenti.size(); i++) {
		    if (usoUtenti.get(i).getAplApplic().getNmApplic()
			    .equals(row.getString("nm_applic"))) {
			usoUserTmp = usoUtenti.get(i);
			/* Passaggio per riferimento */
			usoUserTmp.getUsrUsoRuoloUserDefaults().add(usoRuolo);
		    }
		}
		usoRuolo.setUsrUsoUserApplic(usoUserTmp);
	    }
	}

	log.info("Inserisco le dichiarazioni di abilitazione organizzazione");
	log.debug(
		"MAC 30075 - Inserisco le dichiarazioni di abilitazione organizzazioni scorrendo la lista. A questo punto la 'Lista dich. abil. organiz' contiene i seguenti record: ");
	// MAC 30075
	orgDich.forEach((dichAbilOrganizRowBean) -> log
		.debug("MAC 30075 - Dichiarazione alle organizzazioni presente - applicazione: "
			+ dichAbilOrganizRowBean.getString("nm_applic") + ", scopo: "
			+ dichAbilOrganizRowBean.getTiScopoDichAbilOrganiz() + ", organizzazione: "
			+ dichAbilOrganizRowBean.getString("dl_composito_organiz")));
	log.debug(
		"MAC 30075 - Procedo trattando ognuno dei record di dichiarazione abilitazione alle organizzazioni ");
	for (UsrDichAbilOrganizRowBean row : orgDich) {
	    UsrUsoUserApplic usoUserTmp = new UsrUsoUserApplic();
	    UsrDichAbilOrganiz dich = new UsrDichAbilOrganiz();
	    /* Carico la dichiarazione già presente */
	    if (row.getIdDichAbilOrganiz() != null) {
		dich = userHelper.getUsrDichAbilOrganiz(row.getIdDichAbilOrganiz());
		usoUserTmp = dich.getUsrUsoUserApplic();
		log.debug(
			"MAC 30075 - Dichiarazione di abilitazione alle organizzazioni già presente, non verrà inserita - applicazione: "
				+ row.getString("nm_applic") + ", scopo: "
				+ row.getTiScopoDichAbilOrganiz() + ", organizzazione: "
				+ row.getString("dl_composito_organiz"));
	    } else {
		dich.setTiScopoDichAbilOrganiz(row.getTiScopoDichAbilOrganiz());
		dich.setUsrOrganizIam(userHelper.getUsrOrganizIamById(row.getIdOrganizIam()));
		dich.setDsCausaleDich(row.getDsCausaleDich());
		if (row.getIdAppartCollegEnti() != null) {
		    dich.setOrgAppartCollegEnti(userHelper.findById(OrgAppartCollegEnti.class,
			    row.getIdAppartCollegEnti()));
		}
		if (row.getIdVigilEnteProdut() != null) {
		    dich.setOrgVigilEnteProdut(userHelper.findById(OrgVigilEnteProdut.class,
			    row.getIdVigilEnteProdut()));
		}
		if (row.getIdSuptEstEnteConvenz() != null) {
		    dich.setOrgSuptEsternoEnteConvenz(userHelper.findById(
			    OrgSuptEsternoEnteConvenz.class, row.getIdSuptEstEnteConvenz()));
		}

		/* Se la dichiarazione è nuova, recupero il UsrUsoUserApplic appropriato */
		for (int i = 0; i < usoUtenti.size(); i++) {
		    if (usoUtenti.get(i).getAplApplic().getNmApplic()
			    .equals(row.getString("nm_applic"))) {
			usoUserTmp = usoUtenti.get(i);
			/* Passaggio per riferimento */
			usoUserTmp.getUsrDichAbilOrganizs().add(dich);
		    }
		}

		log.debug(
			"MAC 30075 - Dichiarazione di abilitazione alle organizzazioni NUOVA che verrà inserita - applicazione: "
				+ row.getString("nm_applic") + ", scopo: "
				+ row.getTiScopoDichAbilOrganiz() + ", organizzazione: "
				+ row.getString("dl_composito_organiz"));

	    }
	    dich.setUsrUsoUserApplic(usoUserTmp);
	}

	log.info("Inserisco le dichiarazioni di abilitazione dati");
	for (UsrDichAbilDatiRowBean row : tipiDato) {
	    UsrUsoUserApplic usoUserTmp = new UsrUsoUserApplic();
	    UsrDichAbilDati dich = new UsrDichAbilDati();
	    /* Carico la dichiarazione già presente */
	    if (row.getIdDichAbilDati() != null) {
		dich = userHelper.getUsrDichAbilDati(row.getIdDichAbilDati());
		usoUserTmp = dich.getUsrUsoUserApplic();
	    } else {
		dich.setTiScopoDichAbilDati(row.getTiScopoDichAbilDati());
		dich.setUsrOrganizIam(userHelper.getUsrOrganizIamById(row.getIdOrganizIam()));
		AplClasseTipoDato classeTipoDato = null;
		if (row.getIdClasseTipoDato() != null) {
		    classeTipoDato = userHelper.getAplClasseTipoDatoById(row.getIdClasseTipoDato());
		}
		dich.setAplClasseTipoDato(classeTipoDato);

		UsrTipoDatoIam tipoDato = null;
		if (row.getIdTipoDatoIam() != null) {
		    tipoDato = userHelper.getUsrTipoDatoIamById(row.getIdTipoDatoIam());
		}

		dich.setDsCausaleDich(row.getDsCausaleDich());
		if (row.getIdAppartCollegEnti() != null) {
		    dich.setOrgAppartCollegEnti(userHelper.findById(OrgAppartCollegEnti.class,
			    row.getIdAppartCollegEnti()));
		}
		if (row.getIdVigilEnteProdut() != null) {
		    dich.setOrgVigilEnteProdut(userHelper.findById(OrgVigilEnteProdut.class,
			    row.getIdVigilEnteProdut()));
		}
		if (row.getIdSuptEstEnteConvenz() != null) {
		    dich.setOrgSuptEsternoEnteConvenz(userHelper.findById(
			    OrgSuptEsternoEnteConvenz.class, row.getIdSuptEstEnteConvenz()));
		}

		dich.setUsrTipoDatoIam(tipoDato);

		/* Se la dichiarazione è nuova, recupero il UsrUsoUserApplic appropriato */
		for (int i = 0; i < usoUtenti.size(); i++) {
		    if (usoUtenti.get(i).getAplApplic().getNmApplic()
			    .equals(row.getString("nm_applic"))) {
			usoUserTmp = usoUtenti.get(i);
			/* Passaggio per riferimento */
			usoUserTmp.getUsrDichAbilDatis().add(dich);
		    }
		}
	    }
	    dich.setUsrUsoUserApplic(usoUserTmp);
	}

	log.info("Inserisco le dichiarazioni di abilitazione enti convenzionati");
	for (UsrDichAbilEnteConvenzRowBean row : dichAbilEnteConvenz) {
	    UsrDichAbilEnteConvenz dich = new UsrDichAbilEnteConvenz();
	    /* Carico la dichiarazione già presente */
	    if (row.getIdDichAbilEnteConvenz() != null) {
		dich = userHelper.findById(UsrDichAbilEnteConvenz.class,
			row.getIdDichAbilEnteConvenz());
	    } else {
		dich.setTiScopoDichAbilEnte(row.getTiScopoDichAbilEnte());
		dich.setDsCausaleDich(row.getDsCausaleDich());
		OrgAmbienteEnteConvenz ambienteEnteConvenz = null;
		if (row.getIdAmbienteEnteConvenz() != null) {
		    ambienteEnteConvenz = userHelper.findById(OrgAmbienteEnteConvenz.class,
			    row.getIdAmbienteEnteConvenz());
		}
		dich.setOrgAmbienteEnteConvenz(ambienteEnteConvenz);

		OrgEnteSiam enteSiam = null;
		if (row.getIdEnteConvenz() != null) {
		    enteSiam = userHelper.findById(OrgEnteSiam.class, row.getIdEnteConvenz());
		}
		dich.setOrgEnteSiam(enteSiam);

		OrgAppartCollegEnti appartCollegEnti = null;
		if (row.getIdAppartCollegEnti() != null) {
		    appartCollegEnti = userHelper.findById(OrgAppartCollegEnti.class,
			    row.getIdAppartCollegEnti());
		}
		dich.setOrgAppartCollegEnti(appartCollegEnti);
	    }
	    dich.setUsrUser(userEntity);
	    if (userEntity.getUsrDichAbilEnteConvenzs() == null) {
		userEntity.setUsrDichAbilEnteConvenzs(new ArrayList<>());
	    }
	    userEntity.getUsrDichAbilEnteConvenzs().add(dich);
	}

	log.info("Inserisco l'agente associato all'utente");
	LogAgente logAgente = userHelper.getLogAgenteByNmAgente(userEntity.getNmUserid());
	if (logAgente == null) {
	    logAgente = new LogAgente();
	    logAgente.setNmAgente(userEntity.getNmUserid());
	    // Determina il tipo Agente Premis
	    if (userEntity.getTipoUser()
		    .equalsIgnoreCase(ConstUtente.UsrUser.PERSONA_FISICA.name())) {
		logAgente.setTipoAgentePremis(PremisEnums.TipoAgente.PERSON);
	    } else if (userEntity.getTipoUser()
		    .equalsIgnoreCase(ConstUtente.UsrUser.AUTOMA.name())) {
		logAgente.setTipoAgentePremis(PremisEnums.TipoAgente.SOFTWARE);
	    } else if (userEntity.getTipoUser()
		    .equalsIgnoreCase(ConstUtente.UsrUser.NON_DI_SISTEMA.name())) {
		logAgente.setTipoAgentePremis(PremisEnums.TipoAgente.PERSON);
	    }
	    // Determina il tipo origine utente
	    logAgente.setTipoOrigineAgente(PremisEnums.TipoOrigineAgente.UTENTE.name());
	    userHelper.persistLogAgente(logAgente);
	}
	userEntity.setLogAgente(logAgente);

	log.info("Inserisco il nuovo stato dell'utente");
	UsrStatoUser statoUser = null;
	if (user.getIdUserIam() == null) {
	    // Inserisco un record nella tabella USR_STATO_USER
	    statoUser = new UsrStatoUser();
	    statoUser.setUsrUser(userEntity);
	    statoUser.setTsStato(new Timestamp(new Date().getTime()));
	    statoUser.setTiStatoUser(ConstUsrStatoUser.TiStatotUser.ATTIVO.name());
	    if (userEntity.getUsrStatoUsers() == null) {
		userEntity.setUsrStatoUsers(new ArrayList<>());
	    }
	    userEntity.getUsrStatoUsers().add(statoUser);
	}

	// MAC#26322 - correzione errore dettaglio enti convenzionati
	UsrUserExt usrUserExt = userEntity.getUsrUserExt();
	if (modifica) {
	    userEntity = amministrazioneUtentiHelper.getEntityManager().merge(userEntity);
	} else {
	    amministrazioneUtentiHelper.getEntityManager().persist(userEntity);
	}
	amministrazioneUtentiHelper.getEntityManager().flush(); // Per persistere immediatamente i
								// dati
	if (usrUserExt == null) {
	    usrUserExt = new UsrUserExt();
	}
	usrUserExt.setIdUserIam(userEntity.getIdUserIam());
	usrUserExt.setDlCertClient(user.getDlCertClient());
	amministrazioneUtentiHelper.getEntityManager().persist(usrUserExt);
	userEntity.setUsrUserExt(usrUserExt);

	userEntity = userHelper.salvaUsrUser(userEntity, modifica);
	if (statoUser != null) {
	    statoUser = userEntity.getUsrStatoUsers().get(0);
	    userEntity.setIdStatoUserCor(BigDecimal.valueOf(statoUser.getIdStatoUser()));
	    if (idRichGestUser != null) {
		UsrRichGestUser richiestaDaEvadere = userHelper.findById(UsrRichGestUser.class,
			idRichGestUser);
		statoUser.setUsrRichGestUser(richiestaDaEvadere);
		if (richiestaDaEvadere.getUsrStatoUsers() == null) {
		    richiestaDaEvadere.setUsrStatoUsers(new ArrayList<>());
		}
		richiestaDaEvadere.getUsrStatoUsers().add(statoUser);
	    }
	}

	// Se ho modificato l'ente di appartenenza devo cancellare anche dalle tabelle delle
	// abilitazioni e dai
	// referenti
	if (isEnteSiamAppartChanged) {
	    List<Long> uuuaList = amministrazioneUtentiHelper
		    .getIdUsrUsoUserApplicList(user.getIdUserIam());
	    if (!uuuaList.isEmpty()) {
		userHelper.deleteAbilOrganiz(uuuaList);
		userHelper.deleteAbilDati(uuuaList);
	    }
	    userHelper.deleteAbilEnteSiam(user.getIdUserIam());
	    userHelper.deleteOrgEnteUserRif(user.getIdUserIam());
	    sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(),
		    param.getNomeUtente(), param.getNomeAzione(), listaOggettiDaLoggare,
		    param.getNomePagina());
	}

	/*
	 * Aggiorna l'azione che ha consentito l'inserimento dell'utente specificando che è stata
	 * evasa
	 */
	if (idAppartUserRich != null) {
	    UsrAppartUserRich azione = userHelper.findById(UsrAppartUserRich.class,
		    idAppartUserRich);
	    azione.setFlAzioneRichEvasa("1");
	    azione.setUsrUser(userEntity);
	    userHelper.getEntityManager().flush();
	}

	/*
	 * Il sistema verifica se tutte le azioni della richiesta che ha consentito l’inserimento
	 * dell’utente (se definita) hanno l’indicatore che sono state evase; in tal caso aggiorna
	 * la richiesta assegnando stato = EVASA
	 */
	if (idRichGestUser != null) {
	    UsrRichGestUser richiesta = userHelper.findById(UsrRichGestUser.class, idRichGestUser);
	    boolean richiestaDaPortareEvasa = true;
	    for (UsrAppartUserRich azione : richiesta.getUsrAppartUserRiches()) {
		if (azione.getFlAzioneRichEvasa().equals("0")) {
		    richiestaDaPortareEvasa = false;
		    break;
		}
	    }
	    if (richiestaDaPortareEvasa) {
		richiesta.setTiStatoRichGestUser("EVASA");
	    }
	}

	/* registra l'evento di riattivazione nella tabella LOG_EVENTO_LOGIN_USER */
	LogDto logDto = new LogDto();
	logDto.setNmAttore(ConstLogEventoLoginUser.NM_ATTORE_ONLINE);
	logDto.setNmUser(user.getNmUserid());
	// FORSE INUTILE
	logDto.setTsEvento(new Date());
	logDto.setTipoEvento(LogDto.TipiEvento.SET_PSW);
	logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_INSERT_USER);

	idpLogger.scriviLog(logDto);

	return userEntity;
    }

    /**
     * Cancello l'utente da DB attraverso il suo id
     *
     * @param param     parametri per il logging
     * @param idUserIam id user IAM
     *
     * @return true se la cancellazione è andata a buon fine
     *
     * @throws ParerUserError errore generico
     */
    public boolean deleteUsrUser(LogParam param, BigDecimal idUserIam) throws ParerUserError {
	try {
	    /* Recupero l'utente */
	    UsrUser user = userHelper.findUserById(idUserIam.longValue());
	    /*
	     * Codice aggiuntivo per il logging...
	     */
	    sacerLogEjb.log(param.getTransactionLogContext(),
		    paramHelper.getParamApplicApplicationName(), param.getNomeUtente(),
		    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE,
		    new BigDecimal(user.getIdUserIam()), param.getNomePagina());

	    /* Elimino l'utente */
	    userHelper.deleteUsrUser(user);

	    return true;
	} catch (Exception ex) {
	    ctx.setRollbackOnly();
	    log.error(ex.getMessage(), ex);
	    throw new ParerUserError("Errore durante la cancellazione dell'utente");
	}
    }

    /**
     * Controlli di coerenza: 1) tra abilitazioni tipi dato rispetto alle organizzazioni 2) tra
     * ruoli di default, rispetto alle autorizzazioni dell'amministratore 3) tra organizzazioni,
     * rispetto alle autorizzazioni sulle stesse dell'amministratore 4) tra tipi dato, rispetto alle
     * autorizzazioni sugli stessi dell'amministratore
     *
     * @param userIam           entity {@link UsrUser}
     * @param idUserIamCorrente id user IAM
     * @param ruoliDefault      table bean {@link UsrUsoRuoloUserDefaultTableBean}
     * @param tipiDato          table bean {@link UsrDichAbilDatiTableBean}
     * @param dichAbil          table bean {@link UsrDichAbilOrganizTableBean}
     *
     * @throws IncoherenceException se non vengono rispettate lancio l'eccezione per poi rollbackare
     *                              tutto
     */
    public void checkAbilCoherence(UsrUser userIam, long idUserIamCorrente,
	    UsrUsoRuoloUserDefaultTableBean ruoliDefault, UsrDichAbilOrganizTableBean dichAbil,
	    UsrDichAbilDatiTableBean tipiDato) throws IncoherenceException {
	log.info("INIZIO CONTROLLI DI COERENZA");
	log.info("CONTROLLO 1 - tra abilitazioni tipi dato rispetto alle organizzazioni");
	/* Controllo 1) */
	List<Object[]> incoherenceOrganizList = userHelper
		.getIncoherenceOrganiz(userIam.getIdUserIam());
	if (!incoherenceOrganizList.isEmpty()) {
	    StringBuilder exception = new StringBuilder("Le organizzazioni: <br>");
	    for (int i = 0; i < incoherenceOrganizList.size(); i++) {
		Object[] oA = incoherenceOrganizList.get(i);
		String org = (String) oA[1];
		if (i == 0) {
		    exception.append("- ").append(org).append("<br>");
		} else if (!oA[0].equals(incoherenceOrganizList.get(i - 1)[0])
			&& i < incoherenceOrganizList.size()) {
		    exception.append("per l'applicazione ")
			    .append(incoherenceOrganizList.get(i - 1)[2]).append("<br><br>");
		    exception.append("- ").append(org).append("<br>");
		} else {
		    exception.append("- ").append(org).append("<br>");
		}
	    }
	    exception.append("per l'applicazione ")
		    .append(incoherenceOrganizList.get(incoherenceOrganizList.size() - 1)[2])
		    .append("<br><br>");
	    exception.append(
		    "a cui appartengono tipi di dato abilitati, non sono abilitate per l'utente.");
	    throw new IncoherenceException(exception.toString());

	}

	/* Controllo 2) */
	log.info(
		"CONTROLLO 2 - tra ruoli di default, rispetto alle autorizzazioni dell'amministratore");
	for (UsrUsoUserApplic usoUser : userIam.getUsrUsoUserApplics()) {
	    for (UsrUsoRuoloUserDefault ruolo : usoUser.getUsrUsoRuoloUserDefaults()) {
		long idRuolo = ruolo.getPrfRuolo().getIdRuolo();
		long idApplic = usoUser.getAplApplic().getIdApplic();
		String nmRuolo = ruolo.getPrfRuolo().getNmRuolo();
		List<UsrVCheckRuoloDefault> checkAzioni = userHelper.getUsrVCheckRuoloDefaultList(
			idUserIamCorrente, idRuolo, idApplic,
			ConstPrfDichAutor.TiDichAutor.AZIONE.name());
		List<UsrVCheckRuoloDefault> checkPagine = userHelper.getUsrVCheckRuoloDefaultList(
			idUserIamCorrente, idRuolo, idApplic,
			ConstPrfDichAutor.TiDichAutor.PAGINA.name());
		List<UsrVCheckRuoloDefault> checkMenu = userHelper.getUsrVCheckRuoloDefaultList(
			idUserIamCorrente, idRuolo, idApplic,
			ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
		List<UsrVCheckRuoloDefault> checkServizi = userHelper.getUsrVCheckRuoloDefaultList(
			idUserIamCorrente, idRuolo, idApplic,
			ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
		String errMsg = "";
		if (!checkAzioni.isEmpty()) {
		    errMsg = errMsg + buildErroreControlloRuolo(checkAzioni, nmRuolo, "azioni");
		}

		if (!checkPagine.isEmpty()) {
		    errMsg = errMsg + buildErroreControlloRuolo(checkPagine, nmRuolo, "pagine");
		}

		if (!checkMenu.isEmpty()) {
		    errMsg = errMsg
			    + buildErroreControlloRuolo(checkMenu, nmRuolo, "entrate a menù");
		}

		if (!checkServizi.isEmpty()) {
		    errMsg = errMsg
			    + buildErroreControlloRuolo(checkServizi, nmRuolo, "servizi web");
		}

		if (errMsg.length() > 0) {
		    throw new IncoherenceException(errMsg);
		}
	    }
	}

	/* Controllo 3) */
	log.info(
		"CONTROLLO 3 - tra organizzazioni, rispetto alle autorizzazioni sulle stesse dell'amministratore");
	for (UsrUsoUserApplic usoUser : userIam.getUsrUsoUserApplics()) {
	    for (UsrDichAbilOrganiz organiz : usoUser.getUsrDichAbilOrganizs()) {
		List<UsrVCheckDichAbilOrganiz> checkOrganiz = userHelper
			.getUsrVCheckDichAbilOrganizList(idUserIamCorrente,
				organiz.getIdDichAbilOrganiz());

		String errMsg = "";
		int count = 0;
		if (!checkOrganiz.isEmpty()) {
		    errMsg = "Le seguenti organizzazioni non sono abilitate per l'utente corrente: <br>";
		    for (UsrVCheckDichAbilOrganiz organizNoAbil : checkOrganiz) {
			if (count < MAX_NUM_ERROR_RECORD) {
			    errMsg = errMsg + organizNoAbil.getDlCompositoOrganiz() + "<br>";
			    count++;
			} else {
			    errMsg = errMsg + "...<br>";
			    break;
			}
		    }
		    throw new IncoherenceException(errMsg);
		}
	    }
	}

	/* Controllo 4) */
	log.info(
		"CONTROLLO 4 - tra tipi dato, rispetto alle autorizzazioni sugli stessi dell'amministratore");
	for (UsrUsoUserApplic usoUser : userIam.getUsrUsoUserApplics()) {
	    for (UsrDichAbilDati dati : usoUser.getUsrDichAbilDatis()) {
		List<UsrVCheckDichAbilDati> checkDati = userHelper
			.getUsrVCheckDichAbilDatiList(idUserIamCorrente, dati.getIdDichAbilDati());

		String errMsg = "";
		int count = 0;
		if (!checkDati.isEmpty()) {
		    errMsg = "I seguenti tipi dato non sono abilitati per l'utente corrente: <br>";
		    for (UsrVCheckDichAbilDati tipiDatoNoAbil : checkDati) {
			if (count < MAX_NUM_ERROR_RECORD) {
			    errMsg = errMsg + tipiDatoNoAbil.getNmTipoDato() + "<br>";
			    count++;
			} else {
			    errMsg = errMsg + "...<br>";
			    break;
			}
		    }
		    throw new IncoherenceException(errMsg);
		}
	    }
	}

	/* Controllo 5) */
	log.info(
		"CONTROLLO 5 - tra applicazioni e abilitazioni a ruoli di default, organizzazioni o tipi dato");
	for (UsrUsoUserApplic usoUser : userIam.getUsrUsoUserApplics()) {
	    String nmApplic = usoUser.getAplApplic().getNmApplic();
	    boolean ruoloTrovato = false;
	    /* Controllo dei ruoli di default dell'utente appena inserito */
	    for (UsrUsoRuoloUserDefaultRowBean ruoloDefault : ruoliDefault) {
		if (nmApplic.equals(ruoloDefault.getString("nm_applic"))) {
		    ruoloTrovato = true;
		    break;
		}
	    }
	    if (!ruoloTrovato) {
		throw new IncoherenceException("Per l'applicazione " + nmApplic
			+ " usata dall'utente, " + "non è definito un ruolo di default");
	    }

	    /* Ricavo la lista delle organizzazioni definite dall'applicazione */
	    List<UsrOrganizIam> organizList = usoUser.getAplApplic().getUsrOrganizIams();

	    /* Se per l'applicazione usata dall'utente sono definite organizzazioni */
	    if (!organizList.isEmpty()) {
		/*
		 * Controllo che sia definita almeno una abilitazione dell'utente per l'applicazione
		 */
		List<UsrAbilOrganiz> listaAbilOrganiz = userHelper
			.getUsrAbilOrganizList(usoUser.getIdUsoUserApplic());

		if (listaAbilOrganiz.isEmpty()) {
		    throw new IncoherenceException(
			    "Per l'applicazione " + nmApplic + " usata dall'utente, "
				    + "non è definita una abilitazione sulle organizzazioni");
		}

		List<UsrAbilDati> listaAbilDati = userHelper
			.getUsrAbilDatiList(usoUser.getIdUsoUserApplic());
		if (listaAbilDati.isEmpty()) {
		    throw new IncoherenceException("Per l'applicazione " + nmApplic
			    + " usata dall'utente, "
			    + "non è definita una abilitazione ai tipi dato delle organizzazioni");
		}
	    }
	}

	/* Controllo 6) */
	log.info("CONTROLLO 6 - tra tipo ruolo e utente");
	switch (userIam.getTipoUser()) {
	case "PERSONA_FISICA":
	    for (UsrUsoRuoloUserDefaultRowBean rb : ruoliDefault) {
		if (rb.getString("ti_ruolo").equals(ConstPrfRuolo.TiRuolo.AUTOMA.name())) {
		    throw new IncoherenceException(
			    "Attenzione: all’utente PERSONA_FISICA è stato assegnato almeno un ruolo di tipo AUTOMA");
		}
		if (rb.getString("nm_applic").equals("SACER_VERSO")
			&& userIam.getAplSistemaVersante() == null) {
		    throw new IncoherenceException(
			    "All’utente è assegnato un ruolo che comporta l’utilizzo di VERSO. E’ obbligatorio indicare il sistema versante");
		}
	    }
	    break;
	case "AUTOMA":
	    for (UsrUsoRuoloUserDefaultRowBean rb : ruoliDefault) {
		if (rb.getString("ti_ruolo").equals(ConstPrfRuolo.TiRuolo.PERSONA_FISICA.name())) {
		    throw new IncoherenceException(
			    "Attenzione: all’utente AUTOMA è stato assegnato almeno un ruolo di tipo PERSONA_FISICA");
		}
	    }
	    break;
	}

	/* Controllo 7) */
	log.info(
		"CONTROLLO 7 - tra enti convenzionati, rispetto alle dichiazioni sugli stessi dell'amministratore");
	for (UsrDichAbilEnteConvenz dichAbilEnteConvenz : userIam.getUsrDichAbilEnteConvenzs()) {
	    List<UsrVCheckDichAbilEnte> checkEnte = userHelper.getUsrVCheckDichAbilEnteList(
		    idUserIamCorrente, dichAbilEnteConvenz.getIdDichAbilEnteConvenz());

	    String errMsg = "";
	    int count = 0;
	    if (!checkEnte.isEmpty()) {
		errMsg = "I seguenti enti convenzionati non sono abilitati per l'utente corrente: <br>";
		for (UsrVCheckDichAbilEnte entiConvenzNoAbil : checkEnte) {
		    if (count < MAX_NUM_ERROR_RECORD) {
			errMsg = errMsg + entiConvenzNoAbil.getNmEnteConvenz() + "<br>";
			count++;
		    } else {
			errMsg = errMsg + "...<br>";
			break;
		    }
		}
		throw new IncoherenceException(errMsg);
	    }
	}

	log.info("FINE CONTROLLI DI COERENZA");
    }

    /**
     * Costruisce il messaggio d'errore nel controllo coerenza 2)
     *
     * @param checkList lista verifiche
     * @param nmRuolo   nome ruolo
     * @param dichAbil  abilitazione
     *
     * @return il messaggio d'errore
     */
    public String buildErroreControlloRuolo(List checkList, String nmRuolo, String dichAbil) {
	String errMsg = "- Il ruolo '" + nmRuolo + "' da aggiungere autorizza i/le seguenti "
		+ dichAbil + " che non sono autorizzati/e per l'utente corrente: <br>";
	int count = 0;

	for (Object check : checkList) {
	    if (count < MAX_NUM_ERROR_RECORD) {
		if (check instanceof UsrVCheckRuoloDefault) {
		    errMsg = errMsg + ((UsrVCheckRuoloDefault) check).getDsAutorAggiunta() + "<br>";
		} else if (check instanceof UsrVCheckRuoloDich) {
		    errMsg = errMsg + ((UsrVCheckRuoloDich) check).getDsAutorAggiunta() + "<br>";
		}
		count++;
	    } else {
		errMsg = errMsg + "...<br>";
		break;
	    }
	}
	return errMsg;
    }

    /**
     * Salva su DB l'utente, effettua i controlli di coerenza e scrivi nel log.L'operazione viene
     * eseguita sia che l'utente venga creato ex-novo, sia che venga modificato un utente già
     * esistente.In caso di controlli di coerenza falliti, viene eseguito il rollback
     *
     * @param param                         parametri per logging
     * @param idUserCorrente                id user
     * @param user                          row bean {@link UsrUserRowBean}
     * @param indIp                         table bean {@link UsrIndIpUserTableBean}
     * @param applic                        table bean {@link UsrUsoUserApplicTableBean}
     * @param ruoliDefault                  table bean {@link UsrUsoRuoloUserDefaultTableBean}
     * @param orgDich                       table bean {@link UsrDichAbilOrganizTableBean}
     * @param tipiDato                      table bean {@link UsrDichAbilDatiTableBean}
     * @param dichAbilEnteConvenzTableBean  table bean {@link UsrDichAbilEnteConvenzTableBean}
     * @param indIpDeleteList               lista ip distinti
     * @param applicationsDeleteList        lista distinte applicazioni
     * @param defaultRolesDeleteList        lista ruoli
     * @param dichOrganizDeleteList         lista id organizzazione da cancellare
     * @param tipiDatoDeleteList            tipi dato da cancellare
     * @param dichAbilEnteConvenzDeleteList lista id enti da abilitare
     * @param applicationsEditSet           lista id applicazioni
     * @param mappaOrgApplic                organizzazioni mappa chiave/valore
     * @param mappaTipiDatoApplic           tipi dato mappa chiave/valore
     * @param oper                          tipo operazione {@link TiOperReplic}
     * @param idRichGestUser                id richiesta
     * @param idAppartUserRich              id richiesta utente
     *
     * @return pk
     *
     * @throws ParerUserError       errore generico
     * @throws IncoherenceException errore generico
     */
    public BigDecimal saveAndAlignUser(LogParam param, long idUserCorrente, UsrUserRowBean user,
	    UsrIndIpUserTableBean indIp, UsrUsoUserApplicTableBean applic,
	    UsrUsoRuoloUserDefaultTableBean ruoliDefault, UsrDichAbilOrganizTableBean orgDich,
	    UsrDichAbilDatiTableBean tipiDato,
	    UsrDichAbilEnteConvenzTableBean dichAbilEnteConvenzTableBean,
	    Set<BigDecimal> indIpDeleteList, Set<BigDecimal> applicationsDeleteList,
	    Set<BigDecimal> defaultRolesDeleteList, Set<BigDecimal> dichOrganizDeleteList,
	    Set<BigDecimal> tipiDatoDeleteList, Set<BigDecimal> dichAbilEnteConvenzDeleteList,
	    Set<BigDecimal> applicationsEditSet, Map<String, Set<PairAuth>> mappaOrgApplic,
	    Map<String, Set<PairAuth>> mappaTipiDatoApplic, ApplEnum.TiOperReplic oper,
	    BigDecimal idRichGestUser, BigDecimal idAppartUserRich)
	    throws ParerUserError, IncoherenceException {
	BigDecimal idUser = null;
	String pagina = "/amministrazioneUtenti/dettaglioUtenteWizard";
	try {
	    // CALCOLO INFORMAZIONI PRELIMINARI ALL'INSERIMENTO/MODIFICA
	    /*
	     * Mi ricavo gli idApplic delle applicazioni che andranno cancellate fra poco dalla
	     * lista delle applicazioni
	     */
	    Set<BigDecimal> idApplicCancellateSet = new HashSet<>();
	    for (BigDecimal applicationsDelete : applicationsDeleteList) {
		idApplicCancellateSet.add(new BigDecimal(
			userHelper.getIdApplicByUsrUsoUserApplic(applicationsDelete)));
	    }
	    boolean aggiuntaAbilitazioneStruttura = false;
	    boolean aggiuntoModificatoEliminatoSistemaVersante = false;
	    boolean modificaTipologiaUtente = false;
	    /*
	     * Prima di persistere l'eventuale modifica, controllo se sono state tolte abilitazioni
	     * ad una struttura // e in caso gestisco il ricalcolo servizi erogati
	     */
	    if (oper.equals(ApplEnum.TiOperReplic.MOD)) {
		log.debug(
			"MAC 30075 - Inizio controlli per servizi erogati (non mi interessano...) ");

		UsrUser userDB = userHelper.findById(UsrUser.class, user.getIdUserIam());

		// Se è stata tolta una abilitazione alle strutture
		if (!dichOrganizDeleteList.isEmpty()) {
		    List<OrgVEnteConvByDelabilorg> entiConvAbilTolte = entiConvHelper
			    .getEntiConvByAbilEliminate(dichOrganizDeleteList);
		    for (OrgVEnteConvByDelabilorg enteConvenz : entiConvAbilTolte) {
			entiConvEjb.calcolaServiziErogatiSuUltimoAccordo(
				enteConvenz.getOrgVEnteConvByDelabilorgId().getIdEnteConvenz());
		    }
		}

		/*
		 * Ricavo l'informazione sul fatto che siano state aggiunte abilitazioni ad una
		 * struttura
		 */
		for (UsrDichAbilOrganizRowBean row : orgDich) {
		    if (row.getIdDichAbilOrganiz() == null) {
			aggiuntaAbilitazioneStruttura = true;
			break;
		    }
		}

		/*
		 * Ricavo l'informazione sul fatto che, in caso di modifica, sia stato
		 * aggiunto/modificato/eliminato il sistema versante oppure sia stata cambiata la
		 * tipologia utente
		 */
		if (user.getIdUserIam() != null) {
		    if ((userDB.getAplSistemaVersante() == null
			    && user.getIdSistemaVersante() != null)
			    || (userDB.getAplSistemaVersante() != null
				    && user.getIdSistemaVersante() != null
				    && userDB.getAplSistemaVersante().getIdSistemaVersante() != user
					    .getIdSistemaVersante().longValue())
			    || (userDB.getAplSistemaVersante() != null
				    && user.getIdSistemaVersante() == null)) {
			aggiuntoModificatoEliminatoSistemaVersante = true;
		    }

		    if (!userDB.getTipoUser().equals(user.getTipoUser())) {
			modificaTipologiaUtente = true;
		    }

		}

		log.debug("MAC 30075 - Fine controlli per servizi erogati (non mi interessano) ");
	    }

	    // FINE CALCOLO INFORMAZIONI PRELIMINARI
	    /* Salva l'utente e le nuove dichiarazioni */
	    UsrUser utente = saveUser(param, user, indIp, applic, ruoliDefault, orgDich, tipiDato,
		    dichAbilEnteConvenzTableBean, indIpDeleteList, applicationsDeleteList,
		    defaultRolesDeleteList, dichOrganizDeleteList, tipiDatoDeleteList,
		    dichAbilEnteConvenzDeleteList, idRichGestUser, idAppartUserRich);
	    idUser = new BigDecimal(utente.getIdUserIam());
	    if (idRichGestUser != null) {
		UsrRichGestUser richiestaDaEvadere = userHelper.findById(UsrRichGestUser.class,
			idRichGestUser);
		if (user.getIdUserIam() == null) {
		    // CREAZIONE UTENTE
		    List<UsrAppartUserRich> usrAppartUserRichByUser = amministrazioneUtentiHelper
			    .getUsrAppartUserRichByUser(idRichGestUser, user.getNmUserid(), null,
				    null);
		    for (UsrAppartUserRich usrAppartUserRich : usrAppartUserRichByUser) {
			if (usrAppartUserRich.getTiAzioneRich()
				.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE
					.getDescrizione())) {
			    usrAppartUserRich.setFlAzioneRichEvasa("1");
			    usrAppartUserRich.setUsrUser(utente);
			    amministrazioneUtentiHelper.getEntityManager().flush();
			    if (!amministrazioneUtentiHelper.existAzioni(idRichGestUser.longValue(),
				    "0")) {
				richiestaDaEvadere.setTiStatoRichGestUser(
					ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
			    }
			}
		    }
		} else {
		    // MODIFICA UTENTE
		    if (idAppartUserRich != null) {
			UsrAppartUserRich usrAppartUserRich = amministrazioneUtentiHelper
				.findById(UsrAppartUserRich.class, idAppartUserRich);
			if (usrAppartUserRich.getFlAzioneRichEvasa().equals("0")) {
			    usrAppartUserRich.setFlAzioneRichEvasa("1");
			    amministrazioneUtentiHelper.getEntityManager().flush();
			    if (!amministrazioneUtentiHelper.existAzioni(
				    usrAppartUserRich.getUsrRichGestUser().getIdRichGestUser(),
				    "0")) {
				richiestaDaEvadere.setTiStatoRichGestUser(
					ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
			    }
			}
		    } else {
			List<UsrAppartUserRich> usrAppartUserRichByUser = amministrazioneUtentiHelper
				.getUsrAppartUserRichByUser(idRichGestUser, null, idUser, null);
			for (UsrAppartUserRich usrAppartUserRich : usrAppartUserRichByUser) {
			    if (usrAppartUserRich.getTiAzioneRich().equals(
				    ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI
					    .getDescrizione())
				    && usrAppartUserRich.getFlAzioneRichEvasa().equals("0")) {
				usrAppartUserRich.setFlAzioneRichEvasa("1");
				amministrazioneUtentiHelper.getEntityManager().flush();
				if (!amministrazioneUtentiHelper
					.existAzioni(idRichGestUser.longValue(), "0")) {
				    richiestaDaEvadere.setTiStatoRichGestUser(
					    ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
				}
			    }
			}
		    }
		}
		amministrazioneUtentiHelper.getEntityManager().flush();
		sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(),
			param.getNomeUtente(), param.getNomeAzione(),
			SacerLogConstants.TIPO_OGGETTO_RICHIESTA, idRichGestUser, pagina);
	    }

	    /*
	     * Creo una copia delle abilitazioni inserite nell'online dall'utente alle quali
	     * aggiungerò quelle di default: questo perchè, in caso di errore successivo, non voglio
	     * che il tablebean a livello online tenga traccia delle abilitazioni inserite in
	     * "background"
	     */
	    UsrDichAbilDatiTableBean tipiDatoConDefault = new UsrDichAbilDatiTableBean();
	    for (UsrDichAbilDatiRowBean tipoDato : tipiDato) {
		UsrDichAbilDatiRowBean tipoDatoConDefault = new UsrDichAbilDatiRowBean();
		tipoDatoConDefault.setIdClasseTipoDato(tipoDato.getIdClasseTipoDato());
		tipoDatoConDefault.setIdDichAbilDati(tipoDato.getIdDichAbilDati());
		tipoDatoConDefault.setIdOrganizIam(tipoDato.getIdOrganizIam());
		tipoDatoConDefault.setIdTipoDatoIam(tipoDato.getIdTipoDatoIam());
		tipoDatoConDefault.setIdUsoUserApplic(tipoDato.getIdUsoUserApplic());
		tipoDatoConDefault.setString("nm_applic", tipoDato.getString("nm_applic"));
		tipoDatoConDefault.setString("nm_classe_tipo_dato",
			tipoDato.getString("nm_classe_tipo_dato"));
		tipoDatoConDefault.setString("dl_composito_organiz",
			tipoDato.getString("dl_composito_organiz"));
		tipoDatoConDefault.setString("nm_tipo_dato", tipoDato.getString("nm_tipo_dato"));
		tipoDatoConDefault.setTiScopoDichAbilDati(tipoDato.getTiScopoDichAbilDati());
		tipiDatoConDefault.add(tipoDatoConDefault);
	    }

	    OrgEnteSiam enteAppart = userHelper.findById(OrgEnteSiam.class, user.getIdEnteSiam());

	    // Se sono in modifica DEVE ESEGUIRE DELLE ELIMINAZIONI PRIMA
	    if (oper.equals(ApplEnum.TiOperReplic.MOD)) {
		/*
		 * Se l’utente appartiene ad un ente non convenzionato di tipo organo di vigilanza e
		 * se per l’utente non e’ settato l’indicatore che segnala che le abilitazioni alle
		 * organizzazioni degli enti produttori vigilati sono allineate in automatico
		 */
		if (enteAppart.getTiEnte().name().equals("NON_CONVENZIONATO")
			&& enteAppart.getTiEnteNonConvenz().name().equals("ORGANO_VIGILANZA")) {
		    /*
		     * Il sistema elimina le abilitazioni alle organizzazioni (a cui l’utente
		     * corrente e’ abilitato) appartenenti agli enti vigilati
		     */
		    userHelper.eliminaAbilOrganizVigil(utente.getIdUserIam());
		    /*
		     * Il sistema elimina le abilitazioni ai tipi dato (a cui l’utente corrente e’
		     * abilitato) delle organizzazioni appartenenti agli enti vigilati
		     */
		    userHelper.eliminaAbilTipiDatoVigil(utente.getIdUserIam());
		}

		/*
		 * Se l’utente appartiene ad un ente non convenzionato di tipo fornitore esterno e
		 * se per l’utente non e’ settato l’indicatore che segnala che le abilitazioni alle
		 * organizzazioni degli enti supportati sono allineate in automatico
		 */
		if (enteAppart.getTiEnte().name().equals("NON_CONVENZIONATO")
			&& enteAppart.getTiEnteNonConvenz().name().equals(
				ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())) {
		    /*
		     * Il sistema elimina le abilitazioni alle organizzazioni (a cui l’utente
		     * corrente e’ abilitato) appartenenti agli enti supportati
		     */
		    userHelper.eliminaAbilOrganizSupest(utente.getIdUserIam());
		    /*
		     * Il sistema elimina le abilitazioni ai tipi dato (a cui l’utente corrente e’
		     * abilitato) delle organizzazioni appartenenti agli enti vigilati
		     */
		    userHelper.eliminaAbilTipiDatoSupest(utente.getIdUserIam());
		}

		/*
		 * Se l’utente appartiene ad un ente non convenzionato di tipo forniore esterno e se
		 * per l’utente non e’ settato l’indicatore che segnala che le abilitazioni alle
		 * organizzazioni degli enti supportati sono allineate in automatico
		 */
		if (enteAppart.getTiEnte().name().equals("NON_CONVENZIONATO")
			&& enteAppart.getTiEnteNonConvenz().name().equals("SOGGETTO_ATTUATORE")) {
		    /*
		     * Il sistema elimina le abilitazioni alle organizzazioni (a cui l’utente
		     * corrente e’ abilitato) appartenenti agli enti supportati
		     */
		    userHelper.eliminaAbilOrganizSupest(utente.getIdUserIam());
		    /*
		     * Il sistema elimina le abilitazioni ai tipi dato (a cui l’utente corrente e’
		     * abilitato) delle organizzazioni appartenenti agli enti vigilati
		     */
		    userHelper.eliminaAbilTipiDatoSupest(utente.getIdUserIam());
		}

		/*
		 * Se l’utente appartiene ad un ente convenzionato di tipo produttore e per esso non
		 * è settato l’indicatore che definisce che deve essere abilitato in automatico agli
		 * enti produttori appartenenti ai collegamenti
		 */
		if (enteAppart.getTiEnte().name().equals("CONVENZIONATO")
			&& enteAppart.getTiEnteConvenz().name().equals("PRODUTTORE")) {
		    /*
		     * Il sistema elimina le abilitazioni agli enti convenzionati collegati all’ente
		     * a cui appartiene l’utente
		     */
		    userHelper.eliminaAbilEntiConv(utente.getIdUserIam());
		}
	    }

	    /* Per ogni applicazione usata dall'utente */
	    log.info(
		    "Inserisco le abilitazioni alle organizzazioni ed ai tipi dato derivanti dalle relative dichiarazioni");
	    for (UsrUsoUserApplic applicazione : utente.getUsrUsoUserApplics()) {
		/*
		 * Il sistema registra le abilitazioni alle organizzazioni derivanti dalle relative
		 * dichiarazioni (vedi vista USR_V_ABIL_ORGANIZ_TO_ADD che restituisce le
		 * abilitazioni alle organizzazioni da aggiungere)
		 */
		userHelper.aggiungiAbilOrganizToAdd(utente.getIdUserIam(),
			applicazione.getAplApplic().getIdApplic());
		/*
		 * il sistema registra le abilitazioni ai tipi dato derivanti dalle relative
		 * dichiarazioni (vedi vista USR_V_ABIL_DATI_TO_ADD che restituisce le abilitazioni
		 * ai tipi dato da aggiungere)
		 */
		userHelper.aggiungiAbilDatiToAdd(utente.getIdUserIam(),
			applicazione.getAplApplic().getIdApplic(), null);
	    }

	    /*
	     * il sistema registra le abilitazioni agli enti convenzionati derivanti dalle relative
	     * dichiarazioni (vedi vista USR_V_ABIL_ENTE_TO_ADD che restituisce le abilitazioni agli
	     * enti da aggiungere)
	     */
	    log.info(
		    "Inserisco le abilitazioni agli enti convenzionati derivanti dalle relative dichiarazioni");
	    userHelper.aggiungiAbilEnteToAdd(utente.getIdUserIam());

	    /*
	     * Per ogni applicazione usata dall'utente N.B.: sì Bonnie, ne ho parlato con te in data
	     * 10/02/2017 di questo doppio ciclo sulle applicazioni
	     */
	    log.info(
		    "Per ogni applicazione usata dall'utente e per ogni classe tipo dato prevista dall'applicazione, aggiungo le dichiarazioni e le abilitazioni ai tipi dato");
	    for (UsrUsoUserApplic applicazione : utente.getUsrUsoUserApplics()) {

		for (AplClasseTipoDato classe : applicazione.getAplApplic()
			.getAplClasseTipoDatos()) {

		    /*
		     * Ricavo le dichiarazioni di abilitazione sui tipi dato da aggiungere in
		     * automatico
		     */
		    List<UsrVCreaAbilDati> tipiDatoDaAggiungereFiltratiPerClasseTipoDato = userHelper
			    .getUsrVCreaAbilDati(utente.getNmUserid(),
				    applicazione.getAplApplic().getNmApplic(),
				    classe.getNmClasseTipoDato());

		    for (UsrVCreaAbilDati creaAbilDati : tipiDatoDaAggiungereFiltratiPerClasseTipoDato) {

			/*
			 * Se la dichiarazione di abilitazione è già presente non la aggiunto onde
			 * evitare duplicati
			 */
			if (!isDuplicata(creaAbilDati, mappaOrgApplic, mappaTipiDatoApplic)) {
			    // Registro la DICHIARAZIONE DI ABILITAZIONE AI TIPI DATO
			    long idDichAbilDati = userHelper.aggiungiDichAbilTipoDato(creaAbilDati,
				    utente, applicazione, tipiDatoConDefault);

			    // Registro le ABILITAZIONI AI TIPI DATO derivanti dalla dichiarazione
			    // aggiunta
			    userHelper.aggiungiAbilDatiToAdd(utente.getIdUserIam(),
				    applicazione.getAplApplic().getIdApplic(),
				    classe.getIdClasseTipoDato());

			    // Eseguo i controlli di coerenza sulle abilitazioni appena inserite
			    List<UsrVCheckDichAbilDati> checkDati = userHelper
				    .getUsrVCheckDichAbilDatiList(idUserCorrente, idDichAbilDati);

			    // Se ci sono tipi dato a cui l'utente non è abilitato rispetto
			    // all'abilitazione appena
			    // inserita, quest'ultima la tolgo
			    if (!checkDati.isEmpty()) {
				userHelper.deleteUsrDichAbilDati(new BigDecimal(idDichAbilDati));
			    }
			}

		    }
		}
	    }

	    /* Controlla la coerenza delle abilitazioni create */
	    checkAbilCoherence(utente, idUserCorrente, ruoliDefault, orgDich, tipiDatoConDefault);

	    /*
	     * Se l’utente appartiene ad un ente non convenzionato di tipo organo di vigilanza e se
	     * per l’utente e’ settato l’indicatore che segnala che le abilitazioni alle
	     * organizzazioni degli enti produttori vigilati sono allineate in automatico ed è di
	     * tipo PERSONA_FISICA
	     */
	    if (enteAppart.getTiEnte().name().equals("NON_CONVENZIONATO")
		    && enteAppart.getTiEnteNonConvenz().name().equals("ORGANO_VIGILANZA")
		    && user.getFlAbilOrganizAutom() != null
		    && user.getFlAbilOrganizAutom().equals("1")
		    && user.getTipoUser().equals("PERSONA_FISICA")) {
		/*
		 * Il sistema registra le abilitazioni alle organizzazioni (a cui l’utente corrente
		 * e’ abilitato) appartenenti agli enti vigilati
		 */
		userHelper.aggiungiAbilOrganizVigil(idUserCorrente, utente.getIdUserIam());
		/*
		 * Il sistema registra le abilitazioni ai tipi dato (a cui l’utente corrente e’
		 * abilitato) delle organizzazioni appartenenti agli enti vigilati
		 */
		userHelper.aggiungiAbilDatiVigil(idUserCorrente, utente.getIdUserIam());
	    }

	    /*
	     * Se l’utente appartiene ad un ente non convenzionato di tipo fornitore esterno e se
	     * per l’utente e’ settato l’indicatore che segnala che le abilitazioni alle
	     * organizzazioni e agli enti produttori supportati sono allineate in automatico ed è di
	     * tipo PERSONA FISICA
	     */
	    if (enteAppart.getTiEnte().name().equals("NON_CONVENZIONATO")
		    && enteAppart.getTiEnteNonConvenz().name()
			    .equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
		    && user.getFlAbilFornitAutom() != null
		    && user.getFlAbilFornitAutom().equals("1")
		    && user.getTipoUser().equals("PERSONA_FISICA")) {
		/*
		 * Il sistema registra le abilitazioni alle organizzazioni (a cui l’utente corrente
		 * e’ abilitato) appartenenti agli enti supportati
		 */
		userHelper.aggiungiAbilOrganizFornit(idUserCorrente, utente.getIdUserIam());
		/*
		 * Il sistema registra le abilitazioni ai tipi dato (a cui l’utente corrente e’
		 * abilitato) delle organizzazioni appartenenti agli enti supportati
		 */
		userHelper.aggiungiAbilDatiFornit(idUserCorrente, utente.getIdUserIam());
	    }

	    /*
	     * Se l’utente appartiene ad un ente non convenzionato di tipo soggetto attuatore e se
	     * per l’utente e’ settato l’indicatore che segnala che le abilitazioni alle
	     * organizzazioni degli enti produttori vigilati sono allineate in automatico ed è di
	     * tipo PERSONA FISICA
	     */
	    if (enteAppart.getTiEnte().name().equals("NON_CONVENZIONATO")
		    && enteAppart.getTiEnteNonConvenz().name().equals("SOGGETTO_ATTUATORE")
		    && user.getFlAbilFornitAutom() != null
		    && user.getFlAbilFornitAutom().equals("1")
		    && user.getTipoUser().equals("PERSONA_FISICA")) {
		/*
		 * Il sistema registra le abilitazioni alle organizzazioni (a cui l’utente corrente
		 * e’ abilitato) appartenenti agli enti supportati
		 */
		userHelper.aggiungiAbilOrganizFornit(idUserCorrente, utente.getIdUserIam());
		/*
		 * Il sistema registra le abilitazioni ai tipi dato (a cui l’utente corrente e’
		 * abilitato) delle organizzazioni appartenenti agli enti supportati
		 */
		userHelper.aggiungiAbilDatiFornit(idUserCorrente, utente.getIdUserIam());
	    }

	    /*
	     * Se l’utente appartiene ad un ente convenzionato di tipo produttore e se per esso è
	     * definito che deve essere abilitato in automatico agli enti produttori appartenenti ai
	     * collegamenti
	     */
	    if (enteAppart.getTiEnte().name().equals("CONVENZIONATO")
		    && enteAppart.getTiEnteConvenz().name().equals("PRODUTTORE")
		    && user.getFlAbilEntiCollegAutom() != null
		    && user.getFlAbilEntiCollegAutom().equals("1")) {
		userHelper.aggiungiAbilEnteColleg(idUserCorrente, utente.getIdUserIam());
	    }

	    /* Se l’utente appartiene ad un ente convenzionato di tipo diverso da amministratore */
	    if (enteAppart.getTiEnte().name().equals("CONVENZIONATO")
		    && !enteAppart.getTiEnteConvenz().name().equals("AMMINISTRATORE")
		    && utente.getTipoUser().equals("PERSONA_FISICA")) {
		userHelper.aggiungiAbilEnteFornit(idUserCorrente, utente.getIdUserIam());
	    }

	    /* Se l’utente appartiene ad un ente convenzionato di tipo conservatore */
	    if (enteAppart.getTiEnte().name().equals("CONVENZIONATO")
		    && enteAppart.getTiEnteConvenz().name().equals("CONSERVATORE")
		    && utente.getTipoUser().equals("PERSONA_FISICA")) {
		userHelper.aggiungiAbilEnteVigil(idUserCorrente, utente.getIdUserIam());
	    }

	    /* Se l’utente appartiene ad un ente convenzionato di tipo amministratore */
	    if (enteAppart.getTiEnte().name().equals("CONVENZIONATO")
		    && enteAppart.getTiEnteConvenz().name().equals("AMMINISTRATORE")
		    && utente.getTipoUser().equals("PERSONA_FISICA")) {
		userHelper.aggiungiAbilEnteNoconv(idUserCorrente, utente.getIdUserIam());
	    }

	    /* Se l’utente appartiene ad un ente NON convenzionato */
	    if (enteAppart.getTiEnte().name().equals("NON_CONVENZIONATO")
		    && (utente.getTipoUser().equals("PERSONA_FISICA")
			    || utente.getTipoUser().equals("AUTOMA"))) {
		userHelper.aggiungiAbilEnteCorrisp(idUserCorrente, utente.getIdUserIam());
		if (user.getFlAbilFornitAutom() != null
			&& user.getFlAbilFornitAutom().equals("1")) {
		    OrgSuptEsternoEnteConvenzTableBean suptExtEnteConvenzTableBean = entiNonConvEjb
			    .getOrgEntiSupportatiTableBean(
				    BigDecimal.valueOf(enteAppart.getIdEnteSiam()));
		    for (OrgSuptEsternoEnteConvenzRowBean rb : suptExtEnteConvenzTableBean) {
			userHelper.aggiungiAbilEntiSuptFornit(idUserCorrente, utente.getIdUserIam(),
				enteAppart.getIdEnteSiam(), rb.getIdEnteProdut().longValue());
		    }
		}
		userHelper.aggiungiAbilEnteColleg(idUserCorrente, utente.getIdUserIam());
	    }

	    if (user.getTipoUser().equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
		    // || user.getTipoUser().equals(ApplEnum.TipoUser.TEAM.name())
		    || user.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
		/* Ora scrivo nella tabella dei LOG */
		if (oper.equals(ApplEnum.TiOperReplic.INS)) {
		    /*
		     * INSERIMENTO: per ogni applicazione usata (tranne SacerIam e Dpi), il sistema
		     * registra nel log l'operazione di inserimento Recupero gli idApplic delle
		     * applicazioni presenti a video nella lista
		     */
		    Set<BigDecimal> idApplicSet = new HashSet<>();
		    for (UsrUsoUserApplicRowBean usrUsoUserApplicRowBean : applic) {
			idApplicSet.add(usrUsoUserApplicRowBean.getIdApplic());
		    }
		    // "Filtro" le applicazioni non considerando Sacer_Iam e Dpi
		    List<AplApplic> applicUsateFiltrateList = userHelper
			    .getAplApplicFiltrate(idApplicSet);
		    // Per ogni applicazione scrivo nel log
		    for (AplApplic applicUsataFiltrata : applicUsateFiltrateList) {
			userHelper.registraLogUserDaReplic(utente, applicUsataFiltrata, oper);
		    }
		} else {
		    /* MODIFICA: discerno tra le applicazioni inserite, cancellate e già presenti */
		    // Tratto le applicazioni inserite e già presenti
		    Set<BigDecimal> idApplicInseriteSet = new HashSet<>();
		    for (UsrUsoUserApplicRowBean usrUsoUserApplicRowBean : applic) {
			if (usrUsoUserApplicRowBean.getIdUsoUserApplic() == null) {
			    BigDecimal idApplic = usrUsoUserApplicRowBean.getIdApplic();
			    idApplicInseriteSet.add(idApplic);
			    // Potrei aver aggiunto abilitazioni a una nuova applicazione. In questo
			    // caso non devo
			    // creare una replica applicazione = MOD
			    applicationsEditSet.remove(idApplic);
			}
		    }

		    // "Filtro" le applicazioni non considerando Sacer_Iam e Dpi
		    List<AplApplic> applicCancellateFiltrateList = new ArrayList<>();
		    if (!idApplicCancellateSet.isEmpty()) {
			applicCancellateFiltrateList = userHelper
				.getAplApplicFiltrate(idApplicCancellateSet);
			for (AplApplic applicCancellataFiltrata : applicCancellateFiltrateList) {
			    userHelper.registraLogUserDaReplic(utente, applicCancellataFiltrata,
				    ApplEnum.TiOperReplic.CANC);
			}
		    }
		    if (!idApplicInseriteSet.isEmpty()) {
			List<AplApplic> applicInseriteFiltrateList = userHelper
				.getAplApplicFiltrate(idApplicInseriteSet);
			// Per ogni applicazione scrivo nel log
			for (AplApplic applicInseritaFiltrata : applicInseriteFiltrateList) {
			    userHelper.registraLogUserDaReplic(utente, applicInseritaFiltrata,
				    ApplEnum.TiOperReplic.INS);
			}
		    }
		    if (!applicationsEditSet.isEmpty()) {
			List<AplApplic> applicPresentiFiltrateList = userHelper
				.getAplApplicFiltrate(applicationsEditSet);
			// Sottraggo dalle applicazioni modificate da registrare nel log,
			// quelle che eventualmente sono presenti nella lista di quelle da eliminare
			applicPresentiFiltrateList.removeAll(applicCancellateFiltrateList);
			for (AplApplic applicPresenteFiltrata : applicPresentiFiltrateList) {
			    userHelper.registraLogUserDaReplic(utente, applicPresenteFiltrata,
				    ApplEnum.TiOperReplic.MOD);
			}
		    }
		}
	    }

	    // CASO INSERT: Se è stato indicato il sistema versante, gestisco l'aggiornamento dei
	    // servizi erogati
	    if ((oper.equals(ApplEnum.TiOperReplic.INS) && user.getIdSistemaVersante() != null)
		    && (utente.getTipoUser().equals("PERSONA_FISICA")
			    || utente.getTipoUser().equals("AUTOMA"))) {
		manageUltimoAccordoEntiConvenzionati(idUser);
	    }

	    // CASO UPDATE: Se sono in modifica e
	    // - ho modificato/eliminato il sistema versante
	    // - oppure ho aggiunto nuove abilitazioni alle strutture
	    if (oper.equals(ApplEnum.TiOperReplic.MOD)
		    && (aggiuntaAbilitazioneStruttura || aggiuntoModificatoEliminatoSistemaVersante
			    || modificaTipologiaUtente)
		    && (utente.getTipoUser().equals("PERSONA_FISICA")
			    || utente.getTipoUser().equals("AUTOMA"))) {
		manageUltimoAccordoEntiConvenzionati(idUser);
	    }

	    log.debug(
		    "MAC 30075 - Salvataggio utente terminato, controlli successivi eseguiti, riverifico la lista delle dichiarazioni di abilitazione alle organizzazioni per l'utente "
			    + utente.getNmUserid());
	    utente.getUsrUsoUserApplics().forEach((usoUser) -> {
		usoUser.getUsrDichAbilOrganizs().forEach((dichAbilOrganiz) -> {
		    String organiz = dichAbilOrganiz.getUsrOrganizIam() != null
			    ? dichAbilOrganiz.getUsrOrganizIam().getNmOrganiz()
			    : "";
		    log.debug(
			    "MAC 30075 - Dichiarazione abilitazione all'organizzazione post salvataggio - applicazione: "
				    + dichAbilOrganiz.getUsrUsoUserApplic().getAplApplic()
					    .getNmApplic()
				    + ", scopo: " + dichAbilOrganiz.getTiScopoDichAbilOrganiz()
				    + ", organizzazione: " + organiz);
		});
	    });
	} catch (IncoherenceException ex) {
	    ctx.setRollbackOnly();
	    throw ex;
	}
	user.setIdUserIam(idUser);
	userHelper.getEntityManager().flush();
	sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(),
		param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE,
		idUser, pagina);
	return idUser;
    }

    private boolean isDuplicata(UsrVCreaAbilDati creaAbilDati,
	    Map<String, Set<PairAuth>> mappaOrgApplic,
	    Map<String, Set<PairAuth>> mappaTipiDatoApplic) {

	Iterator<Map.Entry<String, Set<PairAuth>>> iteratore = mappaTipiDatoApplic.entrySet()
		.iterator();
	while (iteratore.hasNext()) {

	    Map.Entry<String, Set<PairAuth>> entry = iteratore.next();
	    String nmApplic = (String) entry.getKey();
	    Set<PairAuth> scopoSet = (Set<PairAuth>) entry.getValue();
	    //
	    String scopo = creaAbilDati.getUsrVCreaAbilDatiId().getTiScopoDichAbilDati();
	    BigDecimal idClasseTipoDato = creaAbilDati.getUsrVCreaAbilDatiId()
		    .getIdClasseTipoDato();
	    BigDecimal idOrganizIam = creaAbilDati.getUsrVCreaAbilDatiId().getIdOrganizIam();
	    Set<PairAuth> scopoOrgSet = mappaOrgApplic.get(nmApplic);

	    if (StringUtils.isNotBlank(scopo)) {
		// Se scopo ALL_ORG
		if (scopo.equals(ActionEnums.ScopoDichAbilDato.ALL_ORG.name())
			&& scopoSet.size() > 0 && amministrazioneUtentiEjb
				.existsDichAnyScopo(scopoSet, idClasseTipoDato)) {
		    return true;
		} // Se scopo ALL_ORG_CHILD
		else if (scopo.equals(ActionEnums.ScopoDichAbilDato.ALL_ORG_CHILD.name())) {
		    if (idOrganizIam.equals(BigDecimal.ZERO)
			    || scopoSet.contains(new PairAuth(idClasseTipoDato,
				    new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG.name(),
					    BigDecimal.ZERO)))
			    || scopoSet.contains(new PairAuth(idClasseTipoDato,
				    new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG_CHILD.name(),
					    idOrganizIam)))
			    || amministrazioneUtentiEjb.checkDichAutorTipiDatoSetOrganizForChilds(
				    idOrganizIam, idClasseTipoDato, scopoOrgSet)
			    || amministrazioneUtentiEjb
				    .checkDichAutorTipiDatoSetOrganizUnaOrgForChilds(idOrganizIam,
					    idClasseTipoDato, scopoOrgSet)) {
			return true;
		    }

		    // Se scopo UNA_ORG
		} else if (scopo.equals(ActionEnums.ScopoDichAbilDato.UNA_ORG.name())) {
		    if (scopoSet
			    .contains(new PairAuth(idClasseTipoDato,
				    new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG.name(),
					    BigDecimal.ZERO)))
			    || scopoSet.contains(new PairAuth(idClasseTipoDato, new PairAuth(
				    ActionEnums.ScopoDichAbilDato.UNA_ORG.name(), idOrganizIam)))) {
			return true;
		    } // Controllo gli scopi ALL_ORG_CHILD presenti nella lista
		    else {
			// Prendo i "padri" (padri e nonni...) dell'organizzazione che sto inserendo
			String[] padri = amministrazioneUtentiEjb.getPadri(idOrganizIam);
			for (int i = 1; i < padri.length; i++) {
			    if (scopoSet.contains(new PairAuth(idClasseTipoDato,
				    new PairAuth(ActionEnums.ScopoDichAbilDato.ALL_ORG_CHILD.name(),
					    new BigDecimal(padri[i]))))) {
				return true;
			    }
			}
		    }
		}

	    }
	}
	return false;
    }

    public void saveRolesForOrganiz(LogParam param, long idUserIamCorrente, BigDecimal idUserIam,
	    String nmApplic, BigDecimal idDichAbilOrganiz,
	    UsrVLisUsoRuoloDichTableBean lisUsoRuoloDichTableBean,
	    Set<BigDecimal> orgRolesDeleteList, BigDecimal idRichGestUser,
	    BigDecimal idAppartUserRich) throws ParerUserError, IncoherenceException {
	// Recupero la dichiarazione di abilitazione alle organizzazioni
	UsrDichAbilOrganiz usrDichAbilOrganiz = userHelper.getUsrDichAbilOrganiz(idDichAbilOrganiz);
	boolean theRoleHasServices = false;

	try {

	    /* Elimino le dichiarazioni sui ruoli che voglio eliminare */
	    deleteUsrUsoRuoloDich(orgRolesDeleteList);

	    BigDecimal idApplic = new BigDecimal(userHelper.getAplApplic(nmApplic).getIdApplic());
	    for (UsrVLisUsoRuoloDichRowBean row : lisUsoRuoloDichTableBean) {
		/*
		 * Prendo ogni ruolo e gli aggiungo le sue UsrUsoRuoloDiches in caso esso sia frutto
		 * di un nuovo inserimento
		 */
		PrfRuolo ruolo = userHelper.getRuoloById(row.getIdRuolo());
		UsrOrganizIam organizIamRuolo = userHelper.findById(UsrOrganizIam.class,
			row.getIdOrganizIamRuolo());
		String tiScopoRuolo = row.getTiScopoRuolo();
		// Controllo se il record UsrUsoRuoloDich era già presente
		UsrUsoRuoloDich usoRuoloDich = userHelper.getUsrUsoRuoloDich(usrDichAbilOrganiz,
			ruolo, organizIamRuolo, tiScopoRuolo);
		// Se il record non c'è già su db
		if (usoRuoloDich == null) {
		    /*
		     * CONTROLLI COERENZA PER OGNI RUOLO NUOVO INSERITO
		     */
		    checkRuoliPerOrganizzazioni(idUserIamCorrente, row.getIdRuolo(),
			    ruolo.getNmRuolo(), idApplic, row.getIdOrganizIamRuolo());

		    UsrUsoRuoloDich dich = new UsrUsoRuoloDich();
		    dich.setUsrDichAbilOrganiz(usrDichAbilOrganiz);
		    dich.setTiScopoRuolo(row.getTiScopoRuolo());
		    dich.setUsrOrganizIam(organizIamRuolo);
		    dich.setPrfRuolo(ruolo);

		    if (ruolo.getUsrUsoRuoloDiches() == null) {
			ruolo.setUsrUsoRuoloDiches(new ArrayList<>());
		    }
		    ruolo.getUsrUsoRuoloDiches().add(dich);
		    if (usrDichAbilOrganiz.getUsrUsoRuoloDiches() == null) {
			usrDichAbilOrganiz.setUsrUsoRuoloDiches(new ArrayList<>());
		    }
		    usrDichAbilOrganiz.getUsrUsoRuoloDiches().add(dich);
		    if (organizIamRuolo.getUsrUsoRuoloDiches() == null) {
			organizIamRuolo.setUsrUsoRuoloDiches(new ArrayList<>());
		    }
		    organizIamRuolo.getUsrUsoRuoloDiches().add(dich);

		    /*
		     * Essendo questo un ruolo nuovo AGGIUNTO, controllo se autorizza dei servizi
		     * web
		     */
		    List<PrfVLisDichAutor> servicesAutorized = userHelper.getPrfVLisDichAutorList(
			    row.getIdRuolo(), ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
		    if (!servicesAutorized.isEmpty()) {
			theRoleHasServices = true;
		    }
		}
	    }

	    /* Controllo se tra i ruoli da cancellare qualcuno autorizza servizi web */
	    for (BigDecimal idRuoloToDelete : orgRolesDeleteList) {
		List<PrfVLisDichAutor> servicesAutorized = userHelper.getPrfVLisDichAutorList(
			idRuoloToDelete, ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
		if (!servicesAutorized.isEmpty()) {
		    theRoleHasServices = true;
		}
	    }

	    /*
	     * Se ho dei servizi autorizzati da almeno un ruolo inserito o eliminato, registro nel
	     * log degli utenti da replicare
	     */
	    if (theRoleHasServices && userHelper.isApplicConsentita(nmApplic)) {
		userHelper.registraLogUserDaReplic(idUserIam, idApplic, ApplEnum.TiOperReplic.MOD);
	    }

	    // Recupero la richiesta...
	    if (idRichGestUser != null) {
		UsrRichGestUser richiestaDaEvadere = userHelper.findById(UsrRichGestUser.class,
			idRichGestUser);
		// MODIFICA UTENTE
		if (idAppartUserRich != null) {
		    UsrAppartUserRich usrAppartUserRich = amministrazioneUtentiHelper
			    .findById(UsrAppartUserRich.class, idAppartUserRich);
		    if (usrAppartUserRich.getFlAzioneRichEvasa().equals("0")) {
			usrAppartUserRich.setFlAzioneRichEvasa("1");
			amministrazioneUtentiHelper.getEntityManager().flush();
			if (!amministrazioneUtentiHelper.existAzioni(
				usrAppartUserRich.getUsrRichGestUser().getIdRichGestUser(), "0")) {
			    richiestaDaEvadere.setTiStatoRichGestUser(
				    ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
			}
		    }
		} else {
		    List<UsrAppartUserRich> usrAppartUserRichByUser = amministrazioneUtentiHelper
			    .getUsrAppartUserRichByUser(idRichGestUser, null, idUserIam, null);
		    for (UsrAppartUserRich usrAppartUserRich : usrAppartUserRichByUser) {
			if (usrAppartUserRich.getTiAzioneRich().equals(
				ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI
					.getDescrizione())
				&& usrAppartUserRich.getFlAzioneRichEvasa().equals("0")) {
			    usrAppartUserRich.setFlAzioneRichEvasa("1");
			    amministrazioneUtentiHelper.getEntityManager().flush();
			    if (!amministrazioneUtentiHelper.existAzioni(idRichGestUser.longValue(),
				    "0")) {
				richiestaDaEvadere.setTiStatoRichGestUser(
					ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
			    }
			}
		    }
		}

		// Registro il logging per la richiesta
		sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(),
			param.getNomeUtente(), param.getNomeAzione(),
			SacerLogConstants.TIPO_OGGETTO_RICHIESTA, idRichGestUser,
			param.getNomePagina());
		// Registro il logging per la modifica utente
		sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(),
			param.getNomeUtente(), param.getNomeAzione(),
			SacerLogConstants.TIPO_OGGETTO_UTENTE, idUserIam, param.getNomePagina());
	    }
	} catch (IncoherenceException ex) {
	    ctx.setRollbackOnly();
	    throw ex;
	} catch (Exception ex) {
	    ctx.setRollbackOnly();
	    log.error(ex.getMessage(), ex);
	    throw new ParerUserError("Errore durante il salvataggio dei ruoli per organizzazione");
	}
    }

    private void deleteUsrUsoRuoloDich(Set<BigDecimal> orgRolesDeleteList) {
	/* Elimino le dichiarazioni di ruolo da eliminare */
	if (!orgRolesDeleteList.isEmpty()) {
	    Iterator<BigDecimal> it = orgRolesDeleteList.iterator();
	    while (it.hasNext()) {
		BigDecimal idUsoRuoloDich = it.next();
		userHelper.deleteUsrUsoRuoloDich(idUsoRuoloDich);
	    }
	}
    }

    /**
     * Per ogni applicazione usata già definita in precedenza, tranne per applicazione SACER_IAM e
     * per le applicazioni DPI_xxx (cioè le applicazioni che non specificano url del servizio
     * “ReplicaUtente”), il sistema registra, nel log degli utenti da replicare, un record relativo
     * all’utente con l’operazione di modifica e stato = DA_REPLICARE
     *
     * @param idUserIam id user IAM
     * @param applicTB  la lista delle applicazioni definite in precedenza
     * @param tipoOper  il tipo di operazione (inserimento, modifica, cancellazione)
     */
    public void registraUtentiDaReplic(BigDecimal idUserIam, UsrUsoUserApplicTableBean applicTB,
	    ApplEnum.TiOperReplic tipoOper) {
	Set<BigDecimal> idApplicUsate = new HashSet<>();
	for (UsrUsoUserApplicRowBean applicRB : applicTB) {
	    idApplicUsate.add(applicRB.getIdApplic());
	}
	List<AplApplic> applicUsateFiltrateList = userHelper.getAplApplicFiltrate(idApplicUsate);
	UsrUser user = userHelper.findUserById(idUserIam.longValue());

	for (AplApplic applicUsataFiltrata : applicUsateFiltrateList) {
	    userHelper.registraLogUserDaReplic(user, applicUsataFiltrata, tipoOper);
	}
    }

    public void checkRuoliPerOrganizzazioni(long idUserIamCorrente, BigDecimal idRuoloAggiunto,
	    String nmRuoloAggiunto, BigDecimal idApplicDich, BigDecimal idOrganizIamRuolo)
	    throws IncoherenceException {
	List<UsrVCheckRuoloDich> checkAzioni = userHelper.getUsrVCheckRuoloDichList(
		idUserIamCorrente, idRuoloAggiunto, idApplicDich, idOrganizIamRuolo,
		ConstPrfDichAutor.TiDichAutor.AZIONE.name());
	List<UsrVCheckRuoloDich> checkPagine = userHelper.getUsrVCheckRuoloDichList(
		idUserIamCorrente, idRuoloAggiunto, idApplicDich, idOrganizIamRuolo,
		ConstPrfDichAutor.TiDichAutor.PAGINA.name());
	List<UsrVCheckRuoloDich> checkMenu = userHelper.getUsrVCheckRuoloDichList(idUserIamCorrente,
		idRuoloAggiunto, idApplicDich, idOrganizIamRuolo,
		ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
	List<UsrVCheckRuoloDich> checkServizi = userHelper.getUsrVCheckRuoloDichList(
		idUserIamCorrente, idRuoloAggiunto, idApplicDich, idOrganizIamRuolo,
		ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
	String errMsg = "";
	if (!checkAzioni.isEmpty()) {
	    errMsg = errMsg + buildErroreControlloRuolo(checkAzioni, nmRuoloAggiunto, "azioni");
	}

	if (!checkPagine.isEmpty()) {
	    errMsg = errMsg + buildErroreControlloRuolo(checkPagine, nmRuoloAggiunto, "pagine");
	}

	if (!checkMenu.isEmpty()) {
	    errMsg = errMsg
		    + buildErroreControlloRuolo(checkMenu, nmRuoloAggiunto, "entrate a menù");
	}

	if (!checkServizi.isEmpty()) {
	    errMsg = errMsg
		    + buildErroreControlloRuolo(checkServizi, nmRuoloAggiunto, "servizi web");
	}

	if (errMsg.length() > 0) {
	    errMsg = errMsg
		    + "a cui appartengono tipi di dato abilitati, non sono abilitate per l'utente.";
	    throw new IncoherenceException(errMsg);
	}
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void manageUltimoAccordoEntiConvenzionati(BigDecimal idUserIam) {
	// Determino gli enti convenzionati associati all’utente tramite le strutture in cui
	// l’utente è stato abilitato
	List<UsrVLisEnteByAbilOrg> lista = userHelper.retrieveUsrVLisEnteByAbilOrg(idUserIam);
	Date dataOdierna = new Date();
	// Per ogni ente convenzionato, ricavo l'ultimo accordo e su di esso calcolo i servizi
	// erogati
	for (UsrVLisEnteByAbilOrg rec : lista) {
	    OrgAccordoEnte accordoEnte = userHelper.getLastAccordoEnte(
		    rec.getUsrVLisEnteByAbilOrgId().getIdEnteConvenz(), dataOdierna);
	    entiConvEjb.calcolaServiziErogatiSuAccordo(accordoEnte);
	}
    }

    public void checkUltimoAccordoEntiConvenzionatiPerSistemaVersante(BigDecimal idUserIam,
	    BigDecimal idSistemaVersantePreUpdate) throws ParerUserError, ParerWarningException {
	// Determino gli enti convenzionati associati all’utente tramite le strutture in cui
	// l’utente è stato abilitato
	List<UsrVLisEnteByAbilOrg> lista = userHelper.retrieveUsrVLisEnteByAbilOrg(idUserIam);
	Date dataOdierna = new Date();
	// Per ogni ente convenzionato, ricavo l'ultimo accordo
	for (UsrVLisEnteByAbilOrg rec : lista) {
	    OrgAccordoEnte accordoEnte = userHelper.getLastAccordoEnte(
		    rec.getUsrVLisEnteByAbilOrgId().getIdEnteConvenz(), dataOdierna);
	    // ... e verifico se sull’ultimo accordo esistono dei servizi erogati sul sistema
	    // versante
	    if (accordoEnte != null && (accordoEnte.getOrgServizioErogs() != null
		    && !accordoEnte.getOrgServizioErogs().isEmpty())) {
		// Se ci sono servizi erogati, verifico se sono stati fatturati
		for (OrgServizioErog servizioErog : accordoEnte.getOrgServizioErogs()) {
		    if ((servizioErog.getAplSistemaVersante() != null && servizioErog
			    .getAplSistemaVersante()
			    .getIdSistemaVersante() == idSistemaVersantePreUpdate.longValue())
			    && entiConvEjb.checkEsistenzaServiziInFattura(
				    BigDecimal.valueOf(servizioErog.getIdServizioErogato()))) {
			throw new ParerUserError(
				"Non è possibile modificare il sistema versante dell’utente in quanto sono presenti dei servizi fatturati");
		    }
		}
		for (OrgServizioErog servizioErog : accordoEnte.getOrgServizioErogs()) {
		    if (servizioErog.getAplSistemaVersante() != null && servizioErog
			    .getAplSistemaVersante()
			    .getIdSistemaVersante() == idSistemaVersantePreUpdate.longValue()
			    && servizioErog.getDtErog() != null) {
			// Altrimenti se ci sono ma non sono fatturati, lancio un warning
			throw new ParerWarningException(
				"Attenzione: sul sistema versante sono presenti dei servizi erogati non fatturati: si intende procedere con la modifica del sistema versante?");
		    }
		}
	    }
	}
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteUserManagingUltimoAccordoEntiConvenzionati(LogParam param,
	    BigDecimal idUserIam, boolean cancOkPing, boolean cancOkSacer, boolean cancOkIam,
	    UsrAppartUserRich richiestaDaEvadere) throws ParerUserError {
	List<UsrVLisEnteByAbilOrg> lista = userHelper.retrieveUsrVLisEnteByAbilOrg(idUserIam);
	amministrazioneUtentiEjb.deleteOrCessaUtente(param, idUserIam, cancOkPing, cancOkSacer,
		cancOkIam, richiestaDaEvadere);
	amministrazioneUtentiHelper.getEntityManager().flush();
	for (UsrVLisEnteByAbilOrg rec : lista) {
	    entiConvEjb.calcolaServiziErogatiSuUltimoAccordo(
		    rec.getUsrVLisEnteByAbilOrgId().getIdEnteConvenz());
	}
    }
}
