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

package it.eng.saceriam.web.helper;

import static it.eng.paginator.util.HibernateUtils.bigDecimalFrom;
import static it.eng.paginator.util.HibernateUtils.longFrom;
import static it.eng.paginator.util.HibernateUtils.longListFrom;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.idpjaas.logutils.IdpLogger;
import it.eng.parer.idpjaas.logutils.LogDto;
import it.eng.parer.sacerlog.entity.constraint.ConstLogEventoLoginUser;
import it.eng.saceriam.common.Constants.TiOperReplic;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplClasseTipoDato;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.LogAgente;
import it.eng.saceriam.entity.LogUserDaReplic;
import it.eng.saceriam.entity.OrgAccordoEnte;
import it.eng.saceriam.entity.OrgAppartCollegEnti;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgSuptEsternoEnteConvenz;
import it.eng.saceriam.entity.OrgVigilEnteProdut;
import it.eng.saceriam.entity.PrfAutor;
import it.eng.saceriam.entity.PrfDichAutor;
import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.PrfRuoloCategoria;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.saceriam.entity.UsrAbilDati;
import it.eng.saceriam.entity.UsrAbilEnteSiam;
import it.eng.saceriam.entity.UsrAbilOrganiz;
import it.eng.saceriam.entity.UsrDichAbilDati;
import it.eng.saceriam.entity.UsrDichAbilEnteConvenz;
import it.eng.saceriam.entity.UsrDichAbilOrganiz;
import it.eng.saceriam.entity.UsrIndIpUser;
import it.eng.saceriam.entity.UsrOldPsw;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrTipoDatoIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoRuoloDich;
import it.eng.saceriam.entity.UsrUsoRuoloUserDefault;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.exception.AuthorizationException;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiTableBean;
import it.eng.saceriam.viewEntity.AplVTreeEntryMenu;
import it.eng.saceriam.viewEntity.PrfVLisDichAutor;
import it.eng.saceriam.viewEntity.UsrVAbilDatiToAdd;
import it.eng.saceriam.viewEntity.UsrVAbilDatiVigilToAdd;
import it.eng.saceriam.viewEntity.UsrVAbilEnteSupportoToAdd;
import it.eng.saceriam.viewEntity.UsrVAbilOrgVigilToAdd;
import it.eng.saceriam.viewEntity.UsrVAbilOrganizToAdd;
import it.eng.saceriam.viewEntity.UsrVCheckDichAbilDati;
import it.eng.saceriam.viewEntity.UsrVCheckDichAbilEnte;
import it.eng.saceriam.viewEntity.UsrVCheckDichAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVCheckRuoloDefault;
import it.eng.saceriam.viewEntity.UsrVCheckRuoloDich;
import it.eng.saceriam.viewEntity.UsrVCreaAbilDati;
import it.eng.saceriam.viewEntity.UsrVLisEnteByAbilOrg;
import it.eng.saceriam.viewEntity.UsrVTreeOrganizIam;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.web.util.Transform;
import it.eng.saceriam.ws.ejb.WsIdpLogger;
import it.eng.spagoLite.security.auth.PwdUtil;

/**
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class UserHelper extends GenericHelper {

    @EJB
    WsIdpLogger idpLogger;

    private static final Logger log = LoggerFactory.getLogger(UserHelper.class);

    private static final SecureRandom rand = new SecureRandom();
    private static final int MAX = 9999;
    private static final int MIN = 1000;

    public void flushEntityManager() {
        getEntityManager().flush();
    }

    /**
     * @deprecated
     *
     * @param username
     *            username
     * @param password
     *            password
     *
     * @return l'utente {@link UsrUser}
     *
     * @throws Exception
     *             eccezione
     */
    @Deprecated
    public UsrUser findUser(String username, String password) throws Exception {
        Query q = getEntityManager().createQuery("SELECT u FROM UsrUser u WHERE (u.nmUserid = :username)");
        q.setParameter("username", username);
        UsrUser user = null;
        try {
            user = (UsrUser) q.getSingleResult();
        } catch (NoResultException e) {
            throw new Exception();
        }
        if (user != null) {
            if (user.getCdSalt() == null || user.getCdSalt().equals("")) {
                if (!PwdUtil.encodePassword(password).equals(user.getCdPsw())) {
                    throw new Exception("Bad password");
                }
            } else if (!PwdUtil.encodePBKDF2Password(PwdUtil.decodeUFT8Base64String(user.getCdSalt()), password)
                    .equals(user.getCdPsw())) {
                throw new Exception("Bad password");
            }
            return user;
        } else {
            throw new Exception();
        }
    }

    public void updateUserPwd(String username, String oldpassword, String password, int numOldPsw, byte[] salt,
            Date scadenzaPwd) throws ParerUserError {
        UsrUser user = findUserByName(username);
        String saltedPassword = PwdUtil.encodePBKDF2Password(salt, password);

        /* registra l'evento di riattivazione nella tabella LOG_EVENTO_LOGIN_USER */
        LogDto logDto = new LogDto();
        logDto.setNmAttore(ConstLogEventoLoginUser.NM_ATTORE_ONLINE);
        logDto.setNmUser(user.getNmUserid());
        // FORSE INUTILE
        //
        logDto.setTsEvento(new Date());
        logDto.setTipoEvento(LogDto.TipiEvento.SET_PSW);
        logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_EDIT_PSW);

        if (!user.getFlAttivo().equals("1")) {
            log.error("Utente non attivo");
            logDto.setTipoEvento(LogDto.TipiEvento.LOCKED);
            logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_USR_LOCKED_EDIT_PSW);
            idpLogger.scriviLog(logDto);
            throw new ParerUserError("Utente non attivo");
        } else if (user.getCdSalt() == null || user.getCdSalt().equals("")) {
            if (!PwdUtil.encodePassword(oldpassword).equals(user.getCdPsw())) {
                logDto.setTipoEvento(LogDto.TipiEvento.BAD_PASS);
                logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_BAD_PSW_EDIT_PSW);
                if (idpLogger.scriviLog(logDto) == IdpLogger.EsitiLog.UTENTE_DISATTIVATO) {
                    log.error(
                            "L'utente è stato disattivato per troppi tentativi d'inserimento errati della vecchia password");
                    throw new ParerUserError(
                            "L'utente è stato disattivato per troppi tentativi d'inserimento errati della vecchia password");
                } else {
                    log.error("La vecchia password non è corretta");
                    // Non devo dare possibilità all'utente di risalire a quale sia la causa dell'errore (user o
                    // password)
                    throw new ParerUserError("Le vecchie credenziali non sono corrette");
                }
            }
        } else if (!PwdUtil.encodePBKDF2Password(PwdUtil.decodeUFT8Base64String(user.getCdSalt()), oldpassword)
                .equals(user.getCdPsw())) {
            logDto.setTipoEvento(LogDto.TipiEvento.BAD_PASS);
            logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_BAD_PSW_EDIT_PSW);
            if (idpLogger.scriviLog(logDto) == IdpLogger.EsitiLog.UTENTE_DISATTIVATO) {
                log.error(
                        "L'utente è stato disattivato per troppi tentativi d'inserimento errati della vecchia password");
                throw new ParerUserError(
                        "L'utente è stato disattivato per troppi tentativi d'inserimento errati della vecchia password");
            } else {
                log.error("La vecchia password non è corretta");
                // Non devo dare possibilità all'utente di risalire a quale sia la causa dell'errore (user o password)
                throw new ParerUserError("Le vecchie credenziali non sono corrette");
            }
        }

        if (isInOldLastPasswords(password, user.getIdUserIam(), numOldPsw)) {
            log.error("La password inserita coincide con una delle ultime {} password inserite in precedenza",
                    numOldPsw);
            throw new ParerUserError("La password inserita coincide con una delle ultime " + numOldPsw
                    + " password inserite in precedenza");
        }
        user.setDtRegPsw(new Date());
        user.setDtScadPsw(scadenzaPwd);
        user.setCdPsw(saltedPassword);
        user.setCdSalt(PwdUtil.encodeUFT8Base64String(salt));
        // Se è tutto andato a buon fine, loggo il SET_PSW
        idpLogger.scriviLog(logDto);
    }

    public UsrUser findUserById(long idUtente) throws NoResultException {
        Query q = getEntityManager().createQuery("SELECT u FROM UsrUser u WHERE u.idUserIam = :iduser");
        q.setParameter("iduser", idUtente);
        return (UsrUser) q.getSingleResult();
    }

    public UsrUser findUserByName(String username) throws NoResultException {
        Query q = getEntityManager().createQuery("SELECT u FROM UsrUser u WHERE u.nmUserid = :username");
        q.setParameter("username", username);
        return (UsrUser) q.getSingleResult();
    }

    /*
     * Introdotto per l'itegrazione con SPID Puglia dove a fronte del codice fiscale arrivato da SPID andiamo a cercare
     * sulla usruser un utente avente come username il codice fiscale ignorando il case.
     */
    public List<UsrUser> findUtentiPerUsernameCaseInsensitive(String username) {
        Query q = getEntityManager()
                .createQuery("SELECT u FROM UsrUser u WHERE lower(u.nmUserid) = :username  AND u.flAttivo='1'");
        // MAC#29629 - Correzione estrazione nome utente durante l'autenticazione SPID
        q.setParameter("username", username.toLowerCase());
        return q.getResultList();
    }

    /* Introdotta per lo SPID **/
    public List<UsrUser> findByCodiceFiscale(String codiceFiscale) throws NoResultException {
        Query q = getEntityManager().createQuery(
                "SELECT u FROM UsrUser u WHERE (u.cdFisc = :codiceFiscaleL OR u.cdFisc = :codiceFiscaleU) AND u.flAttivo='1'");
        q.setParameter("codiceFiscaleL", codiceFiscale.toLowerCase());
        q.setParameter("codiceFiscaleU", codiceFiscale.toUpperCase());
        return q.getResultList();
    }

    public List<UsrUser> getUsrUserByNmUserid(String nmUserid) {
        Query q = getEntityManager().createQuery("SELECT u FROM UsrUser u WHERE u.nmUserid = :nmUserid");
        q.setParameter("nmUserid", nmUserid);
        return q.getResultList();
    }

    public void resetPwd(long idUtente, String randomPwd, String salt, Date scad) {
        UsrUser user = findUserById(idUtente);
        user.setDtScadPsw(scad);
        user.setDtRegPsw(new Date());
        user.setCdPsw(randomPwd);
        user.setCdSalt(salt);
        getEntityManager().flush();
    }

    public void resetPwd(long idUtente, String randomPwd, String salt) {
        UsrUser user = findUserById(idUtente);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        if (user.getTipoUser().equals(ApplEnum.TipoUser.AUTOMA.name())) {
            cal.set(2444, Calendar.DECEMBER, 31);
        }
        resetPwd(idUtente, randomPwd, salt, cal.getTime());
    }

    public void deleteUsrUser(UsrUser user) {
        if (user != null) {
            getEntityManager().remove(user);
            getEntityManager().flush();
        }
    }

    /**
     * @deprecated
     *
     * @param idUserIam
     *            id user
     * @param newExpirationDate
     *            la prossima data di scadenza della pwd
     */
    @Deprecated
    public void updatePasswordExpiration(BigDecimal idUserIam, Date newExpirationDate) {
        UsrUser u = getEntityManager().find(UsrUser.class, idUserIam.longValue());
        u.setDtScadPsw(newExpirationDate);
    }

    public List<AplEntryMenu> getUserMenu(List<PrfRuolo> ruoli) {
        StringBuilder ruoParam = new StringBuilder();
        for (int i = 0; i < ruoli.size(); i++) {
            ruoParam.append(ruoParam.length() == 0 ? "?" : ", ?");
        }

        String queryStr = "SELECT * FROM APL_ENTRY_MENU t0 WHERE EXISTS "
                + " (SELECT * FROM prf_ruolo ruo, prf_uso_ruolo_applic usoruo, apl_applic appl , prf_autor autor "
                + " WHERE ruo.id_ruolo in (" + ruoParam + ")" + " AND usoruo.id_ruolo = ruo.id_ruolo AND "
                + " usoruo.id_applic = appl.id_applic AND appl.nm_applic = ? AND usoruo.id_uso_ruolo_applic = autor.id_uso_ruolo_applic AND autor.id_entry_menu = t0.id_entry_menu)"
                + " CONNECT BY PRIOR t0.id_entry_menu = t0.id_entry_menu_padre START WITH t0.id_entry_menu_padre IS NULL "
                + " ORDER SIBLINGS BY t0.ni_ord_entry_menu";

        Query q = getEntityManager().createNativeQuery(queryStr, AplEntryMenu.class);
        for (int i = 0; i < ruoli.size(); i++) {
            q.setParameter(i + 1, ruoli.get(i).getIdRuolo());
        }
        q.setParameter(ruoli.size() + 1, Constants.SACERIAM);
        return q.getResultList();

    }

    /**
     * @deprecated
     *
     * @param ruoli
     *            ruoli
     *
     * @return pagine {@link AplPaginaWeb}
     */
    @Deprecated
    public List<AplPaginaWeb> getUserPages(List<PrfRuolo> ruoli) {
        List<Long> idRuoli = new ArrayList<>();

        for (PrfRuolo r : ruoli) {
            idRuoli.add(r.getIdRuolo());
        }
        String queryStr = "SELECT DISTINCT page FROM PrfRuolo ruoli JOIN ruoli.prfUsoRuoloApplics ruoloApp "
                + "JOIN ruoloApp.prfAutors autors JOIN autors.aplPaginaWeb page WHERE ruoloApp.aplApplic.nmApplic = :nomeapp AND ruoli.idRuolo IN (:ruoli)";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruoli", idRuoli);
        q.setParameter("nomeapp", Constants.SACERIAM);
        return q.getResultList();

    }

    /**
     * @deprecated metodo non utilizzato, da recuperare quando la MenuUtil farò a meno della CONNECT BY PRIOR
     *
     * @param ruoli
     *            ruoli
     *
     * @return lista di {@link AplEntryMenu}
     */
    @Deprecated
    public List<AplEntryMenu> getUserMenuE(List<PrfRuolo> ruoli) {
        List<Long> idRuoli = new ArrayList<>();

        for (PrfRuolo r : ruoli) {
            idRuoli.add(r.getIdRuolo());
        }
        String queryStr = "SELECT DISTINCT m FROM PrfRuolo ruoli JOIN ruoli.prfUsoRuoloApplics ruoloApp "
                + "JOIN ruoloApp.prfAutors autors JOIN autors.aplEntryMenu m WHERE ruoloApp.aplApplic.nmApplic = :nomeapp AND ruoli.idRuolo IN (:ruoli)"
                + " ORDER BY m.niLivelloEntryMenu, m.entryMenuPadre.idEntryMenu, m.niOrdEntryMenu";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruoli", idRuoli);
        q.setParameter("nomeapp", Constants.SACERIAM);
        return q.getResultList();

    }

    /**
     * @deprecated
     *
     * @param idUtente
     *            id utente
     * @param idOrgStrut
     *            id organizzazione
     *
     * @return lista di {@link PrfRuolo}
     *
     * @throws AuthorizationException
     *             eccezione
     */
    @Deprecated
    public List<PrfRuolo> getUserRoles(long idUtente, long idOrgStrut) throws AuthorizationException {
        String queryStr = "SELECT r FROM PrfRuolo r JOIN r.risUsoRuoloStruts usoR "
                + "JOIN usoR.risAbilStrut abilS JOIN abilS.orgStrut strut JOIN abilS.usrUsoUserApplic usrUso "
                + " WHERE usrUso.usrUser.idUser = :iduser AND usrUso.aplApplic.nmApplic = :nomeapp AND strut.idStrut = :idstrut";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("iduser", idUtente);
        q.setParameter("nomeapp", Constants.SACERIAM);
        q.setParameter("idstrut", idOrgStrut);
        List<PrfRuolo> resultList = q.getResultList();
        if (resultList.isEmpty()) {
            queryStr = "SELECT r FROM PrfRuolo r JOIN r.usrUsoRuoloUserDefaults usoDef "
                    + "JOIN usoDef.usrUsoUserApplic usrUso WHERE usrUso.usrUser.idUser = :iduser AND usrUso.aplApplic.nmApplic = :nomeapp";
            q = getEntityManager().createQuery(queryStr);
            q.setParameter("iduser", idUtente);
            q.setParameter("nomeapp", Constants.SACERIAM);
            resultList = q.getResultList();
            if (resultList.isEmpty()) {
                throw new AuthorizationException("Per la struttura scelta non sono definiti i ruoli dell'utente");
            }
        }
        return resultList;
    }

    /**
     * @deprecated
     *
     * @param userRoles
     *            ruoli utente
     * @param idPagina
     *            id pagina
     *
     * @return lista di {@link AplAzionePagina}
     */
    @Deprecated
    public List<AplAzionePagina> getUserActions(List<PrfRuolo> userRoles, long idPagina) {
        List<Long> idRuoli = new ArrayList<>();
        for (PrfRuolo r : userRoles) {
            idRuoli.add(r.getIdRuolo());
        }
        String queryStr = "SELECT azio FROM PrfRuolo ruoli JOIN ruoli.prfUsoRuoloApplics ruoloApp "
                + "JOIN ruoloApp.prfAutors autors JOIN autors.aplAzionePagina azio WHERE ruoloApp.aplApplic.nmApplic = :nomeapp AND azio.aplPaginaWeb.idPaginaWeb = :idpagina AND ruoli.idRuolo IN (:ruoli)";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruoli", idRuoli);
        q.setParameter("nomeapp", Constants.SACERIAM);
        q.setParameter("idpagina", idPagina);
        return q.getResultList();
    }

    public AplApplic getAplApplic(String name) {
        String queryStr = "SELECT applic FROM AplApplic applic WHERE applic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", name);
        return (AplApplic) q.getSingleResult();
    }

    public AplApplic getAplApplic(BigDecimal idApplic) {
        return getEntityManager().find(AplApplic.class, idApplic.longValue());
    }

    /**
     * @deprecated
     *
     * @param usrUsoUserApplicList
     *            lista di uso user applic
     *
     * @return lista di {@link UsrUsoUserApplic}
     */
    @Deprecated
    public List<UsrUsoUserApplic> getUsrUsoUserApplicFiltrate(List<UsrUsoUserApplic> usrUsoUserApplicList) {
        String queryStr = "SELECT usoApplic FROM UsrUsoUserApplic usoApplic "
                + "WHERE usoApplic IN (:usrUsoUserApplicList) "
                + "AND usoApplic.aplApplic.dsUrlReplicaUser IS NOT NULL ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("usrUsoUserApplicList", usrUsoUserApplicList);
        return q.getResultList();
    }

    /**
     * Dati in input un insieme di applicazioni (tramite id), restituisce l'insieme "filtrato" delle applicazioni non
     * consentite (SacerIam e Dpi) non avendo valorizzato il campo dsUrlReplicaUser
     *
     * @param idApplicSet
     *            lista distinta di id applicazione
     *
     * @return lista elemnti di tipo {@link AplApplic}
     */
    public List<AplApplic> getAplApplicFiltrate(Set<BigDecimal> idApplicSet) {
        String queryStr = "SELECT aplApplic FROM AplApplic aplApplic " + "WHERE aplApplic.idApplic IN (:idApplicSet) "
                + "AND aplApplic.dsUrlReplicaUser IS NOT NULL ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplicSet", longListFrom(idApplicSet));
        return q.getResultList();
    }

    /**
     * Dati in input un insieme di applicazioni (tramite id), restituisce l'insieme "filtrato" dell'applicazione
     * SacerIam
     *
     * @param idApplicSet
     *            lista distinta di id applicazione
     *
     * @return table bean {@link AplApplicTableBean}
     */
    public AplApplicTableBean getAplApplicConOrganizzazioni(Set<BigDecimal> idApplicSet) {
        String queryStr = "SELECT DISTINCT apl FROM AplTipoOrganiz aplTipoOrganiz JOIN aplTipoOrganiz.aplApplic apl "
                + "WHERE aplTipoOrganiz.aplApplic.idApplic IN (:idApplicSet) " + "ORDER BY apl.nmApplic ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplicSet", longListFrom(idApplicSet));
        List<AplApplic> applicList = q.getResultList();
        AplApplicTableBean atb = new AplApplicTableBean();

        try {
            if (applicList != null && !applicList.isEmpty()) {
                atb = (AplApplicTableBean) Transform.entities2TableBean(applicList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return atb;
    }

    /**
     * Data in ingresso un'applicazione (il nome, che è univoco) restituisce un booleano che indica se l'applicazione è
     * consentita (ovvero non è SacerIam o Dpi, controllando sul campo dsUrlReplicaUser)
     *
     * @param nmApplic
     *            nome applicazione
     *
     * @return true se applicazione consentita
     */
    public boolean isApplicConsentita(String nmApplic) {
        String queryStr = "SELECT aplApplic FROM AplApplic aplApplic " + "WHERE aplApplic.nmApplic = :nmApplic ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nmApplic", nmApplic);
        List<AplApplic> applicList = q.getResultList();
        if (!applicList.isEmpty()) {
            return applicList.get(0).getDsUrlReplicaUser() != null;
        } else {
            return false;
        }
    }

    public AplVTreeEntryMenu getAplEntryMenu(String menuName, String menuDesc, String applName) throws Exception {
        String queryStr = "SELECT menu FROM AplVTreeEntryMenu menu, AplApplic applic WHERE menu.idApplic = applic.idApplic AND applic.nmApplic = :nomeappl";
        if (StringUtils.isNotBlank(menuName)) {
            queryStr += " AND menu.nmEntryMenu = :menuName";
        }
        if (StringUtils.isNotBlank(menuDesc)) {
            queryStr += " AND menu.dlCompositoEntryMenu = :menuDesc";
        }
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        if (StringUtils.isNotBlank(menuName)) {
            q.setParameter("menuName", menuName);
        }
        if (StringUtils.isNotBlank(menuDesc)) {
            q.setParameter("menuDesc", menuDesc);
        }
        List<AplVTreeEntryMenu> menus = q.getResultList();
        if (!menus.isEmpty() && menus.size() == 1) {
            return menus.get(0);
        } else if (menus.size() > 1) {
            throw new Exception("La query ha ritornato più di un risultato");
        } else {
            return null;
        }
    }

    /**
     * Metodo che restituisce, dati i parametri nome applicazione e nome menuEntry, la menuEntry corrispondente
     *
     * @param menuName
     *            nome menu
     * @param applName
     *            nome applicazione
     *
     * @return AplEntryMenu la menuEntry corrispondente
     *
     * @throws Exception
     *             nel caso siano stati trovati più record corrispondenti
     */
    public AplEntryMenu getAplEntryMenu(String menuName, String applName) throws Exception {
        String queryStr = "SELECT menu FROM AplEntryMenu menu WHERE menu.nmEntryMenu = :nomemenu and menu.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomemenu", menuName);
        q.setParameter("nomeappl", applName);
        List<AplEntryMenu> menus = q.getResultList();
        if (!menus.isEmpty() && menus.size() == 1) {
            return menus.get(0);
        } else if (menus.size() > 1) {
            throw new Exception("La query ha ritornato più di un risultato");
        } else {
            return null;
        }
    }

    public AplEntryMenu getAplEntryMenu(BigDecimal idEntryMenu) {
        return getEntityManager().find(AplEntryMenu.class, idEntryMenu.longValue());
    }

    /**
     * Metodo che ritorna la lista di AplAzionePagina data la pagina web
     *
     * @param aplPaginaWeb
     *            entity {@link AplPaginaWeb}
     *
     * @return la lista di azioni
     */
    public List<AplAzionePagina> getAplAzionePagina(AplPaginaWeb aplPaginaWeb) {
        String queryStr = "SELECT azione FROM AplAzionePagina azione WHERE azione.aplPaginaWeb = :pagina";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("pagina", aplPaginaWeb);
        return q.getResultList();
    }

    public AplPaginaWeb getAplPaginaWeb(BigDecimal idPaginaWeb) {
        return getEntityManager().find(AplPaginaWeb.class, idPaginaWeb.longValue());
    }

    /**
     * Metodo che ritorna il record pagina web dati il nome della pagina e nome applicazione
     *
     * @param nomePagina
     *            nome pagina
     * @param nomeApplicazione
     *            nome applicazione
     *
     * @return AplPaginaWeb la pagina corrispondente
     *
     * @throws Exception
     *             nel caso siano stati trovati più record corrispondenti
     */
    public AplPaginaWeb getAplPaginaWeb(String nomePagina, String nomeApplicazione) throws Exception {
        String queryStr = "SELECT page FROM AplPaginaWeb page WHERE page.nmPaginaWeb = :nomepagina and page.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomepagina", nomePagina);
        q.setParameter("nomeappl", nomeApplicazione);
        List<AplPaginaWeb> pages = q.getResultList();
        if (!pages.isEmpty() && pages.size() == 1) {
            return pages.get(0);
        } else if (pages.size() > 1) {
            throw new Exception("La query ha ritornato più di un risultato");
        } else {
            return null;
        }
    }

    /**
     * Aggiorna la pagina web page con i dati passati come parametro
     *
     * @param page
     *            entity {@link AplPaginaWeb}
     * @param descPagina
     *            descrizione pagina
     * @param tiHelpOnline
     *            tipo help
     * @param entryMenu
     *            entity {@link AplEntryMenu}
     */
    public void updateAplPaginaWeb(AplPaginaWeb page, String descPagina, String tiHelpOnline, AplEntryMenu entryMenu) {
        page.setDsPaginaWeb(descPagina);
        page.setTiHelpOnLine(tiHelpOnline);
        page.setAplEntryMenu(entryMenu);
        getEntityManager().flush();
    }

    /**
     * Inserisce una nuova pagina web con i parametri passati.
     *
     * @param nomeApplicazione
     *            nome applicazione
     * @param nomePagina
     *            nome pagina
     * @param descPagina
     *            descrizione pagina
     * @param tiHelpOnline
     *            tipo help
     * @param entryMenu
     *            entity {@link AplEntryMenu}
     */
    public void insertAplPaginaWeb(String nomeApplicazione, String nomePagina, String descPagina, String tiHelpOnline,
            AplEntryMenu entryMenu) {
        AplPaginaWeb page = new AplPaginaWeb();
        page.setAplApplic(getAplApplic(nomeApplicazione));
        page.setNmPaginaWeb(nomePagina);
        page.setDsPaginaWeb(descPagina);
        page.setTiHelpOnLine(tiHelpOnline);
        page.setAplEntryMenu(entryMenu);
        getEntityManager().persist(page);
        getEntityManager().flush();
    }

    /**
     * Metodo che dato nome applicazione e una lista di pagine, elimina le pagine non comprese nella lista per
     * quell'applicazione
     *
     * @param nomeAppl
     *            nome applicazione
     * @param dati
     *            lista dati
     */
    public void deletePages(String nomeAppl, List<String> dati) {
        String selectQry = "SELECT page.id FROM AplPaginaWeb page WHERE page.aplApplic.nmApplic = :nomeappl and page.nmPaginaWeb NOT IN (:pages)";
        StringBuilder deleteQry = new StringBuilder("DELETE FROM AplPaginaWeb a WHERE a.id IN (").append(selectQry)
                .append(")");
        Query q = getEntityManager().createQuery(deleteQry.toString());
        q.setParameter("pages", dati);
        q.setParameter("nomeappl", nomeAppl);
        q.executeUpdate();
        getEntityManager().flush();
    }

    public AplAzionePagina getAplAzionePagina(String nomeAzione, String nomePagina, String nomeApplicazione)
            throws Exception {
        String queryStr = "SELECT action FROM AplAzionePagina action WHERE action.nmAzionePagina = :nomeazione and action.aplPaginaWeb.nmPaginaWeb = :nomepagina and action.aplPaginaWeb.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeazione", nomeAzione);
        q.setParameter("nomepagina", nomePagina);
        q.setParameter("nomeappl", nomeApplicazione);
        List<AplAzionePagina> actions = q.getResultList();
        if (!actions.isEmpty() && actions.size() == 1) {
            return actions.get(0);
        } else if (actions.size() > 1) {
            throw new Exception("La query ha ritornato più di un risultato");
        } else {
            return null;
        }
    }

    public AplAzionePagina getAplAzionePagina(BigDecimal idAzionePagina) {
        return getEntityManager().find(AplAzionePagina.class, idAzionePagina.longValue());
    }

    /**
     * Aggiorna l'azione action con i dati passati come parametro
     *
     * @param action
     *            entity {@link AplAzionePagina}
     * @param nomeApplicazione
     *            nome applicazione
     * @param nomePagina
     *            nome pagina
     * @param descAzione
     *            descrizione azione
     *
     * @throws Exception
     *             errore generico
     */
    public void updateAplAzionePagina(AplAzionePagina action, String nomeApplicazione, String nomePagina,
            String descAzione) throws Exception {
        action.setAplPaginaWeb(getAplPaginaWeb(nomePagina, nomeApplicazione));
        action.setDsAzionePagina(descAzione);
        getEntityManager().merge(action);
        getEntityManager().flush();
    }

    /**
     * Inserisce una nuova azione con i parametri passati.
     *
     * @param nomeApplicazione
     *            nome applicazione
     * @param nomePagina
     *            nome pagina
     * @param nomeAzione
     *            nome azione
     * @param descAzione
     *            descrizione azione
     *
     * @throws Exception
     *             errore generico
     *
     * @return pk
     */
    public Long insertAplAzionePagina(String nomeApplicazione, String nomePagina, String nomeAzione, String descAzione)
            throws Exception {
        AplAzionePagina azione = new AplAzionePagina();
        azione.setAplPaginaWeb(getAplPaginaWeb(nomePagina, nomeApplicazione));
        azione.setNmAzionePagina(nomeAzione);
        azione.setDsAzionePagina(descAzione);
        getEntityManager().persist(azione);
        getEntityManager().flush();
        return azione.getIdAzionePagina();
    }

    /**
     * Metodo che dato nome applicazione e una lista di azioni, elimina le azioni non comprese nella lista per
     * quell'applicazione
     *
     * @param nomeAppl
     *            nome applicazione
     * @param nomePag
     *            nome pagina
     * @param pageActions
     *            lista azioni
     */
    public void deleteActions(String nomeAppl, String nomePag, List<String> pageActions) {
        String selectQry = "SELECT action.id FROM AplAzionePagina action "
                + "WHERE action.aplPaginaWeb.nmPaginaWeb = :nomepag and action.aplPaginaWeb.aplApplic.nmApplic = :nomeappl "
                + "and action.nmAzionePagina NOT IN (:actions)";
        StringBuilder deleteQry = new StringBuilder("DELETE FROM AplAzionePagina a WHERE a.id IN (").append(selectQry)
                .append(")");
        Query q = getEntityManager().createQuery(deleteQry.toString());
        q.setParameter("actions", pageActions);
        q.setParameter("nomeappl", nomeAppl);
        q.setParameter("nomepag", nomePag);
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Metodo che dato in input il nome applicazione, una pagina e una lista di azioni (prese dal file CSV), seleziona
     * le azioni su DB non comprese nella lista con tipo evento configurato
     *
     * @param nomeAppl
     *            nome applicazione
     * @param nomePag
     *            nome pagina
     * @param pageActions
     *            action
     *
     * @return lista elementi di tipo {@link AplAzionePagina}
     */
    public List<AplAzionePagina> getActionsNotInCsv(String nomeAppl, String nomePag, List<String> pageActions) {
        String queryStr = "SELECT azionePagina FROM AplAzionePagina azionePagina "
                + "WHERE azionePagina.aplPaginaWeb.nmPaginaWeb = :nomepag "
                + "AND azionePagina.aplPaginaWeb.aplApplic.nmApplic = :nomeappl "
                + "AND azionePagina.nmAzionePagina NOT IN (:actions) " + "AND azionePagina.aplTipoEvento IS NOT NULL ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("actions", pageActions);
        q.setParameter("nomeappl", nomeAppl);
        q.setParameter("nomepag", nomePag);
        return q.getResultList();
    }

    /**
     * Metodo che dato in input il nome applicazione e una lista di pagine (prese dal file CSV), seleziona le pagine su
     * DB non comprese nella lista per le quali sia definita almeno una azione su cui e’ definito il tipo evento di
     * configurazione del logging
     *
     * @param nomeAppl
     *            nome applicazione
     * @param dati
     *            lista dati
     *
     * @return lista elementi di tipo {@link AplPaginaWeb}
     */
    public List<AplPaginaWeb> getPagesNotInCsv(String nomeAppl, List<String> dati) {
        String queryStr = "SELECT paginaWeb FROM AplPaginaWeb paginaWeb "
                + "WHERE paginaWeb.aplApplic.nmApplic = :nomeappl " + "AND paginaWeb.nmPaginaWeb NOT IN (:pages) "
                + "AND EXISTS (SELECT azionePagina FROM AplAzionePagina azionePagina "
                + "WHERE azionePagina.aplPaginaWeb = paginaWeb " + "AND azionePagina.aplTipoEvento IS NOT NULL) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("pages", dati);
        q.setParameter("nomeappl", nomeAppl);
        return q.getResultList();
    }

    /**
     * Metodo che dato in input il nome applicazione e una lista di menù tra quelli che potrebbero essere tolti,
     * seleziona i menù su DB della lista per i quali è definito L'HELP ON LINE
     *
     * @param nomeAppl
     *            nome applicazione
     * @param dati
     *            lista dati potenzialmente cancellabili
     *
     * @return lista elementi di tipo {@link AplEntryMenu}
     */
    public List<AplEntryMenu> getMenusNotInCsvPerHelpOnLine(String nomeAppl, List<String> dati) {
        String queryStr = "SELECT entryMenu FROM AplEntryMenu entryMenu "
                + "WHERE entryMenu.aplApplic.nmApplic = :nomeappl " + "AND entryMenu.nmEntryMenu NOT IN (:menus) "
                + "AND EXISTS (SELECT aiutoInLinea FROM AplHelpOnLine aiutoInLinea "
                + "WHERE aiutoInLinea.aplEntryMenu = entryMenu) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("menus", dati);
        q.setParameter("nomeappl", nomeAppl);
        return q.getResultList();
    }

    /**
     * Metodo che dato in input il nome applicazione e una lista di pagine potenzialmente cancellabili, seleziona le
     * pagine per le quali è definito L'HELP ON LINE
     *
     * @param nomeAppl
     *            nome applicazione
     * @param dati
     *            lista dati
     *
     * @return lista elementi di tipo {@link AplPaginaWeb}
     */
    public List<AplPaginaWeb> getPagesNotInCsvAiutoInLinea(String nomeAppl, List<String> dati) {
        String queryStr = "SELECT paginaWeb FROM AplPaginaWeb paginaWeb "
                + "WHERE paginaWeb.aplApplic.nmApplic = :nomeappl " + "AND paginaWeb.nmPaginaWeb NOT IN (:pages) "
                + "AND EXISTS (SELECT aiutoInLinea FROM AplHelpOnLine aiutoInLinea "
                + "WHERE aiutoInLinea.aplPaginaWeb.idPaginaWeb = paginaWeb.idPaginaWeb) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("pages", dati);
        q.setParameter("nomeappl", nomeAppl);
        return q.getResultList();
    }

    /**
     * Metodo che dato nome applicazione e una lista di azioni, elimina le azioni non comprese nella lista per
     * quell'applicazione
     *
     * @param idActions
     *            lista id azioni
     */
    public void deleteActions(List<Long> idActions) {
        String queryStr = "DELETE FROM AplAzionePagina action " + "WHERE action.idAzionePagina NOT IN (:idActions) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idActions", idActions);
        q.executeUpdate();
        getEntityManager().flush();
    }

    public AplServizioWeb getAplServizioWeb(String nomeServizio, String nomeApplicazione) throws Exception {
        String queryStr = "SELECT service FROM AplServizioWeb service WHERE service.nmServizioWeb = :nomeservizio and service.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeservizio", nomeServizio);
        q.setParameter("nomeappl", nomeApplicazione);
        List<AplServizioWeb> services = q.getResultList();
        if (!services.isEmpty() && services.size() == 1) {
            return services.get(0);
        } else if (services.size() > 1) {
            throw new Exception("La query ha ritornato più di un risultato");
        } else {
            return null;
        }
    }

    public AplServizioWeb getAplServizioWeb(BigDecimal idServizioWeb) {
        return getEntityManager().find(AplServizioWeb.class, idServizioWeb.longValue());
    }

    /**
     * Aggiorna il servizio service con i dati passati come parametro
     *
     * @param service
     *            entity AplServizioWeb
     * @param descServizio
     *            descrizione
     */
    public void updateAplServizioWeb(AplServizioWeb service, String descServizio) {
        service.setDsServizioWeb(descServizio);
        getEntityManager().merge(service);
        getEntityManager().flush();
    }

    /**
     * Inserisce un nuovo servizio web con i parametri passati.
     *
     * @param nomeApplicazione
     *            nome applicazione
     * @param nomeServizio
     *            nome servizio
     * @param descServizio
     *            descrizione servizio
     */
    public void insertAplServizioWeb(String nomeApplicazione, String nomeServizio, String descServizio) {
        AplServizioWeb servizio = new AplServizioWeb();
        servizio.setAplApplic(getAplApplic(nomeApplicazione));
        servizio.setNmServizioWeb(nomeServizio);
        servizio.setDsServizioWeb(descServizio);
        getEntityManager().persist(servizio);
        getEntityManager().flush();
    }

    /**
     * Metodo che dato nome applicazione e una lista di servizi web, elimina i servizi non compresi nella lista per
     * quell'applicazione
     *
     * @param nomeAppl
     *            nome applicazione
     * @param dati
     *            lista dati
     *
     * @return numero eliminati
     */
    public int deleteWebServices(String nomeAppl, List<String> dati) {
        String selectQry = "SELECT service.id FROM AplServizioWeb service WHERE service.aplApplic.nmApplic = :nomeappl and service.nmServizioWeb NOT IN (:services)";
        StringBuilder deleteQry = new StringBuilder("DELETE FROM AplServizioWeb a WHERE a.id IN (").append(selectQry)
                .append(")");
        int numEliminati = 0;
        Query q = getEntityManager().createQuery(deleteQry.toString());
        q.setParameter("services", dati);
        q.setParameter("nomeappl", nomeAppl);
        numEliminati = q.executeUpdate();
        getEntityManager().flush();
        return numEliminati;
    }

    /**
     * Metodo che restituisce una lista di menuEntry dato il nome applicazione
     *
     * @param applName
     *            nome applicazione
     *
     * @return la lista corrispondente
     */
    public List<AplEntryMenu> getListAplEntryMenu(String applName) {
        String queryStr = "SELECT menu FROM AplEntryMenu menu WHERE menu.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di menuEntry di ultimo livello (livello 3) dato il nome applicazione
     *
     * @param applName
     *            nome applicazione
     *
     * @return la lista corrispondente
     */
    public List<AplEntryMenu> getListAplEntryMenuUltimoLivello(String applName) {
        String queryStr = "SELECT menu FROM AplEntryMenu menu " + "WHERE menu.aplApplic.nmApplic = :nomeappl "
                + "AND menu.niLivelloEntryMenu = :livelloMenu ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        q.setParameter("livelloMenu", BigDecimal.valueOf(3));
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di menuEntry di ultimo livello (livello 3) dato il nome applicazione
     *
     * @param idApplic
     *            id applicazione
     *
     * @return la lista corrispondente
     */
    public List<AplEntryMenu> getListAplEntryMenuUltimoLivello(BigDecimal idApplic) {
        String queryStr = "SELECT menu FROM AplEntryMenu menu " + "WHERE menu.aplApplic.idApplic = :idApplic "
                + "AND menu.niLivelloEntryMenu = :livelloMenu ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplic", longFrom(idApplic));
        q.setParameter("livelloMenu", BigDecimal.valueOf(3));
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di pagine dato il nome applicazione
     *
     * @param applName
     *            nome applicazione
     *
     * @return la lista corrispondente
     */
    public List<AplPaginaWeb> getListAplPaginaWeb(String applName) {
        String queryStr = "SELECT page FROM AplPaginaWeb page WHERE page.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di pagine dato il nome applicazionee il tipo di help associato alla pagina che
     * può essere HELP_PAGINA oppure HELP_RICERCA_DIPS
     *
     * @deprecated
     *
     * @param applName
     *            nome applicazione
     * @param tiHelpOnLine
     *            tipo help
     *
     * @return la lista corrispondente
     */
    @Deprecated
    public List<AplPaginaWeb> getListAplPaginaWeb(String applName, String tiHelpOnLine) {
        String queryStr = "SELECT page FROM AplPaginaWeb page WHERE page.aplApplic.nmApplic = :nomeappl AND page.tiHelpOnLine = :tiHelpOnLine";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        q.setParameter("tiHelpOnLine", tiHelpOnLine);
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di pagine dato il nome applicazione
     *
     * @param idApplic
     *            id applicazione
     *
     * @return la lista corrispondente
     */
    public List<AplPaginaWeb> getListAplPaginaWeb(BigDecimal idApplic) {
        String queryStr = "SELECT page FROM AplPaginaWeb page WHERE page.aplApplic.idApplic = :idApplic";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplic", longFrom(idApplic));
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di pagine dato il nome applicazione e il tipo di help associato alla pagina che
     * può essere HELP_PAGINA oppure HELP_RICERCA_DIPS
     *
     * @param idApplic
     *            id applicazione
     * @param tiHelpOnLine
     *            tipo help
     *
     * @return la lista corrispondente
     */
    public List<AplPaginaWeb> getListAplPaginaWeb(BigDecimal idApplic, String tiHelpOnLine) {
        String queryStr = "SELECT page FROM AplPaginaWeb page WHERE page.aplApplic.idApplic = :idApplic AND page.tiHelpOnLine = :tiHelpOnLine";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplic", longFrom(idApplic));
        q.setParameter("tiHelpOnLine", tiHelpOnLine);
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di servizi web dato il nome applicazione
     *
     * @param applName
     *            nome applicazione
     *
     * @return la lista corrispondente
     */
    public List<AplServizioWeb> getListAplServizioWeb(String applName) {
        String queryStr = "SELECT serv FROM AplServizioWeb serv WHERE serv.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        return q.getResultList();
    }

    /**
     * Metodo che restituisce una lista di azioni dato il nome applicazione
     *
     * @param applName
     *            nome applicazione
     *
     * @return la lista corrispondente
     */
    public List<AplAzionePagina> getListAplAzionePagina(String applName) {
        String queryStr = "SELECT actions FROM AplAzionePagina actions WHERE actions.aplPaginaWeb.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        return q.getResultList();
    }

    /**
     * Inserisce una nuova menuEntry con i parametri passati.
     *
     * @param nomeApplicazione
     *            nome applicazione
     * @param nomeEntryMenu
     *            nome entry menu
     * @param descEntryMenu
     *            descrizione entry menu
     * @param numLivelloEntryMenu
     *            numero livello
     * @param numOrdineEntryMenu
     *            ordine
     * @param nomeEntryMenuPadre
     *            entry padre
     * @param linkEntryMenu
     *            link menu
     *
     * @throws Exception
     *             errore generico
     */
    public void insertAplEntryMenu(String nomeApplicazione, String nomeEntryMenu, String descEntryMenu,
            String numLivelloEntryMenu, String numOrdineEntryMenu, String nomeEntryMenuPadre, String linkEntryMenu)
            throws Exception {
        AplEntryMenu menu = new AplEntryMenu();
        menu.setAplApplic(getAplApplic(nomeApplicazione));
        menu.setNmEntryMenu(nomeEntryMenu);
        menu.setDsEntryMenu(descEntryMenu);
        menu.setNiLivelloEntryMenu(new BigDecimal(numLivelloEntryMenu));
        menu.setNiOrdEntryMenu(new BigDecimal(numOrdineEntryMenu));
        if (nomeEntryMenuPadre != null) {
            menu.setEntryMenuPadre(getAplEntryMenu(nomeEntryMenuPadre, nomeApplicazione));
        }
        if (linkEntryMenu != null) {
            menu.setDlLinkEntryMenu(linkEntryMenu);
        }
        getEntityManager().persist(menu);
        getEntityManager().flush();
    }

    /**
     * Aggiorna la menuEntry menu con i parametri passati
     *
     * @param menu
     *            AplEntryMenu da aggiornare
     * @param nomeApplicazione
     *            nome applicazione
     * @param descEntryMenu
     *            descrizione entry menu
     * @param numLivelloEntryMenu
     *            numero livello
     * @param numOrdineEntryMenu
     *            ordine
     * @param nomeEntryMenuPadre
     *            nome entry menu padre
     * @param linkEntryMenu
     *            link
     *
     * @throws Exception
     *             errore generico
     */
    public void updateAplEntryMenu(AplEntryMenu menu, String nomeApplicazione, String descEntryMenu,
            String numLivelloEntryMenu, String numOrdineEntryMenu, String nomeEntryMenuPadre, String linkEntryMenu)
            throws Exception {
        menu.setDsEntryMenu(descEntryMenu);
        menu.setNiLivelloEntryMenu(new BigDecimal(numLivelloEntryMenu));
        menu.setNiOrdEntryMenu(new BigDecimal(numOrdineEntryMenu));
        menu.setEntryMenuPadre(getAplEntryMenu(nomeEntryMenuPadre, nomeApplicazione));
        menu.setDlLinkEntryMenu(linkEntryMenu);
        mergeEntity(menu);
        getEntityManager().flush();
    }

    /**
     * Metodo che dato nome applicazione e una lista di menuEntries, elimina le entrate a menu non comprese nella lista
     * per quell'applicazione
     *
     * @param applName
     *            nome applicazione
     * @param menuEntries
     *            lista di menuEntries
     */
    public void deleteMenuEntries(String applName, List<String> menuEntries) {
        String selectQry = "SELECT menu.id FROM AplEntryMenu menu WHERE menu.aplApplic.nmApplic = :nomeappl and menu.nmEntryMenu NOT IN (:menuentries)";
        StringBuilder deleteQry = new StringBuilder("DELETE FROM AplEntryMenu a WHERE a.id IN (").append(selectQry)
                .append(")");
        Query q = getEntityManager().createQuery(deleteQry.toString());
        q.setParameter("menuentries", menuEntries);
        q.setParameter("nomeappl", applName);
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Metodo che ritorna la lista di ruoli per una determinata applicazione
     *
     * @param applName
     *            nome applicazione
     *
     * @return lista dei ruoli
     */
    public List<PrfUsoRuoloApplic> getPrfUsoRuoloApplic(String applName) {
        String queryStr = "SELECT ruoli FROM PrfUsoRuoloApplic ruoli WHERE ruoli.aplApplic.nmApplic = :nomeappl";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", applName);
        return q.getResultList();
    }

    /**
     * Elimina autorizzazioni di tipo dichAutor dato un ruolo
     *
     * @param ruolo
     *            il ruolo dell'autorizzazione
     * @param dichAutor
     *            la dichiarazione di autorizzazione
     */
    public void deleteAuthsFromPrfAutor(PrfUsoRuoloApplic ruolo, String dichAutor) {
        String queryStr = "DELETE FROM PrfAutor auths WHERE auths.prfUsoRuoloApplic = :ruolo and auths.tiDichAutor = :dichautor";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruolo", ruolo);
        q.setParameter("dichautor", dichAutor);
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Determina le dichiarazioni di autorizzazioni per l'applicazione corrente di tipo ENTRY_MENU e con scopo
     * UNA_ABILITAZIONE, per le quali l'entrata a menù specificata non sia foglia; ogni dichiarazione determinata viene
     * aggiornata assegnando lo scopo pari a ALL_ABILITAZIONI_CHILD infine elimina le dichiarazioni ridondanti
     *
     * @param ruolo
     *            entity {@link PrfUsoRuoloApplic}
     */
    public void updateDichAutor(PrfUsoRuoloApplic ruolo) {
        // Per ogni ruolo ricerco le dichiarazioni
        String queryStr = "SELECT dich FROM PrfDichAutor dich "
                + "WHERE dich.prfUsoRuoloApplic = :ruolo and dich.tiDichAutor = '"
                + ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name() + "' AND dich.tiScopoDichAutor = '"
                + ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name()
                + "' AND (SELECT count(menu) FROM AplEntryMenu menu WHERE menu.entryMenuPadre = dich.aplEntryMenuFoglia)>0";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruolo", ruolo);
        List<PrfDichAutor> dichauths = q.getResultList();
        for (PrfDichAutor dich : dichauths) {
            dich.setTiScopoDichAutor(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name());
            dich.setAplEntryMenuPadre(dich.getAplEntryMenuFoglia());
            dich.setAplEntryMenuFoglia(null);
            getEntityManager().merge(dich);
            getEntityManager().flush();
        }
    }

    /**
     * Ritorna la lista di dichiarazioni di autorizzazione dati ruolo, dichiarazione e scopo dichiarazione
     *
     * @param ruolo
     *            entity {@link PrfUsoRuoloApplic}
     * @param tiDichAutor
     *            autore
     * @param tiScopoDichAutor
     *            scopo
     *
     * @return Lista di dichiarazioni
     */
    public List<PrfDichAutor> getDichAutor(PrfUsoRuoloApplic ruolo, String tiDichAutor, String tiScopoDichAutor) {
        String queryStr = "SELECT dich FROM PrfDichAutor dich WHERE dich.prfUsoRuoloApplic = :ruolo AND dich.tiDichAutor = :dichautor AND dich.tiScopoDichAutor = :scopodich";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruolo", ruolo);
        q.setParameter("dichautor", tiDichAutor);
        q.setParameter("scopodich", tiScopoDichAutor);
        return q.getResultList();
    }

    /**
     * Inserisce le autorizzazioni con scopo ALL_ABILITAZIONI su EntryMenu, Pagine, Azioni e Servizi
     *
     * @param ruolo
     *            entity {@link PrfUsoRuoloApplic}
     * @param listAplEntryMenu
     *            lista degli entry menu
     * @param listAplPaginaWeb
     *            lista delle pagine
     * @param listAplAzionePagina
     *            lista delle azioni
     * @param counter
     *            contatore delle autorizzazioni inserite
     * @param counterPages
     *            contatore delle pagine create
     * @param listAplServizioWeb
     *            lista servizi pagine web di tipo {@link AplPaginaWeb}
     *
     * @return il contatore counter aggiornato
     */
    public int insertAuthAllAbilitazioni(PrfUsoRuoloApplic ruolo, List<AplEntryMenu> listAplEntryMenu,
            List<AplPaginaWeb> listAplPaginaWeb, List<AplAzionePagina> listAplAzionePagina,
            List<AplServizioWeb> listAplServizioWeb, int counter, MutableInt counterPages) {
        if (listAplEntryMenu != null) {
            for (AplEntryMenu menu : listAplEntryMenu) {
                PrfAutor autor = new PrfAutor();
                autor.setPrfUsoRuoloApplic(ruolo);
                autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
                autor.setAplEntryMenu(menu);
                getEntityManager().persist(autor);
                getEntityManager().flush();
            }
        } else if (listAplAzionePagina != null) {
            for (AplAzionePagina azione : listAplAzionePagina) {
                PrfAutor autor = new PrfAutor();
                autor.setPrfUsoRuoloApplic(ruolo);
                autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.AZIONE.name());
                autor.setAplAzionePagina(azione);
                getEntityManager().persist(autor);
                getEntityManager().flush();
            }
            for (AplPaginaWeb pagina : listAplPaginaWeb) {
                if (!isAuthorizedAplPaginaWeb(ruolo, ConstPrfDichAutor.TiDichAutor.PAGINA.name(), pagina)) {
                    PrfAutor autor = new PrfAutor();
                    autor.setPrfUsoRuoloApplic(ruolo);
                    autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
                    counterPages.increment();
                    autor.setAplPaginaWeb(pagina);
                    getEntityManager().persist(autor);
                    getEntityManager().flush();
                }
            }
        } else if (listAplPaginaWeb != null) {
            for (AplPaginaWeb pagina : listAplPaginaWeb) {
                PrfAutor autor = new PrfAutor();
                autor.setPrfUsoRuoloApplic(ruolo);
                autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
                autor.setAplPaginaWeb(pagina);
                getEntityManager().persist(autor);
                getEntityManager().flush();
            }
        } else if (listAplServizioWeb != null) {
            for (AplServizioWeb servizio : listAplServizioWeb) {
                PrfAutor autor = new PrfAutor();
                autor.setPrfUsoRuoloApplic(ruolo);
                autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
                autor.setAplServizioWeb(servizio);
                getEntityManager().persist(autor);
                getEntityManager().flush();
            }
        }

        return counter;
    }

    /**
     * Inserisce le autorizzazioni con scopo ALL_ABILITAZIONI su EntryMenu, Azioni
     *
     * @param ruolo
     *            entity {@link PrfUsoRuoloApplic}
     * @param dichiarazioni
     *            le dichiarazioni da autorizzare
     * @param dichAutor
     *            il tipo di dichiarazione
     * @param counter
     *            contatore delle autorizzazioni inserite
     * @param counterPages
     *            contatore delle pagine inserite
     *
     * @return il contatore counter aggiornato alle nuove autorizzazioni
     */
    public int insertAuthAllAbilitazioniChild(PrfUsoRuoloApplic ruolo, List<PrfDichAutor> dichiarazioni,
            String dichAutor, int counter, MutableInt counterPages) {
        if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
            for (PrfDichAutor padre : dichiarazioni) {
                // Inserisco le autorizzazioni per i figli
                long idEntryMenu = padre.getAplEntryMenuPadre().getIdEntryMenu();
                String queryStr = "SELECT * FROM APL_ENTRY_MENU menu " + "START WITH menu.id_entry_menu = "
                        + idEntryMenu + " CONNECT BY PRIOR menu.id_entry_menu = menu.id_entry_menu_padre";
                Query q = getEntityManager().createNativeQuery(queryStr, AplEntryMenu.class);
                List<AplEntryMenu> entries = q.getResultList();
                for (AplEntryMenu menu : entries) {
                    PrfAutor autor = new PrfAutor();
                    autor.setPrfUsoRuoloApplic(ruolo);
                    autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
                    autor.setAplEntryMenu(menu);
                    getEntityManager().persist(autor);
                    getEntityManager().flush();
                }
                // Inserisco le autorizzazioni per gli AVI
                queryStr = "SELECT * FROM APL_ENTRY_MENU entry" + " WHERE entry.id_entry_menu in"
                        + " (SELECT CONNECT_BY_ROOT nodo.id_entry_menu" + " FROM APL_ENTRY_MENU nodo"
                        + " WHERE nodo.id_entry_menu = " + idEntryMenu
                        + " CONNECT BY PRIOR nodo.id_entry_menu = nodo.id_entry_menu_padre)" + " AND NOT EXISTS ("
                        + " SELECT * FROM PRF_AUTOR aut" + " WHERE aut.id_uso_ruolo_applic = "
                        + ruolo.getIdUsoRuoloApplic() + " AND aut.ti_dich_autor = '"
                        + ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name() + "'"
                        + " AND aut.id_entry_menu = entry.id_entry_menu)";
                q = getEntityManager().createNativeQuery(queryStr, AplEntryMenu.class);
                entries = q.getResultList();
                for (AplEntryMenu menu : entries) {
                    PrfAutor autor = new PrfAutor();
                    autor.setPrfUsoRuoloApplic(ruolo);
                    autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
                    autor.setAplEntryMenu(menu);
                    getEntityManager().persist(autor);
                    getEntityManager().flush();
                }
            }
        } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
            for (PrfDichAutor dichiarazione : dichiarazioni) {
                List<AplAzionePagina> azioni = getAplAzionePagina(dichiarazione.getAplPaginaWeb());
                for (AplAzionePagina azione : azioni) {
                    PrfAutor autor = new PrfAutor();
                    autor.setPrfUsoRuoloApplic(ruolo);
                    autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.AZIONE.name());
                    autor.setAplAzionePagina(azione);
                    getEntityManager().persist(autor);
                    getEntityManager().flush();
                }
                if (!isAuthorizedAplPaginaWeb(ruolo, ConstPrfDichAutor.TiDichAutor.PAGINA.name(),
                        dichiarazione.getAplPaginaWeb())) {
                    PrfAutor autor = new PrfAutor();
                    autor.setPrfUsoRuoloApplic(ruolo);
                    autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
                    counterPages.increment();
                    autor.setAplPaginaWeb(dichiarazione.getAplPaginaWeb());
                    getEntityManager().persist(autor);
                    getEntityManager().flush();
                }
            }
        }
        return counter;
    }

    /**
     * Inserisce le autorizzazioni con scopo UNA_ABILITAZIONE su EntryMenu,Pagine, Azioni e Servizi Web
     *
     * @param ruolo
     *            entity {@link PrfUsoRuoloApplic}
     * @param dichiarazioni
     *            le dichiarazioni da autorizzare
     * @param dichAutor
     *            il tipo di dichiarazione
     * @param counter
     *            contatore delle autorizzazioni inserite
     * @param counterPages
     *            contatore delle pagine inserite
     *
     * @return counter aggiornato alle nuove autorizzazioni
     */
    public int insertAuthUnaAbilitazione(PrfUsoRuoloApplic ruolo, List<PrfDichAutor> dichiarazioni, String dichAutor,
            int counter, MutableInt counterPages) {
        for (PrfDichAutor dichautor : dichiarazioni) {
            PrfAutor autor = new PrfAutor();
            autor.setPrfUsoRuoloApplic(ruolo);
            autor.setTiDichAutor(dichAutor);
            if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
                autor.setAplEntryMenu(dichautor.getAplEntryMenuFoglia());
            } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
                autor.setAplPaginaWeb(dichautor.getAplPaginaWeb());
            } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
                autor.setAplAzionePagina(dichautor.getAplAzionePagina());
            } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name())) {
                autor.setAplServizioWeb(dichautor.getAplServizioWeb());
            }
            getEntityManager().persist(autor);
            getEntityManager().flush();

            if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
                // Inserisco le autorizzazioni per gli AVI
                String queryStr = "SELECT * FROM APL_ENTRY_MENU entry" + " WHERE entry.id_entry_menu in"
                        + " (SELECT CONNECT_BY_ROOT nodo.id_entry_menu" + " FROM APL_ENTRY_MENU nodo"
                        + " WHERE nodo.id_entry_menu = " + dichautor.getAplEntryMenuFoglia().getIdEntryMenu()
                        + " CONNECT BY PRIOR nodo.id_entry_menu = nodo.id_entry_menu_padre)" + " AND NOT EXISTS ("
                        + " SELECT * FROM PRF_AUTOR aut" + " WHERE aut.id_uso_ruolo_applic = "
                        + ruolo.getIdUsoRuoloApplic() + " AND aut.ti_dich_autor = '"
                        + ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name() + "'"
                        + " AND aut.id_entry_menu = entry.id_entry_menu)";
                Query q = getEntityManager().createNativeQuery(queryStr, AplEntryMenu.class);
                List<AplEntryMenu> entries = q.getResultList();
                for (AplEntryMenu menu : entries) {
                    autor = new PrfAutor();
                    autor.setPrfUsoRuoloApplic(ruolo);
                    autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
                    autor.setAplEntryMenu(menu);
                    getEntityManager().persist(autor);
                    getEntityManager().flush();
                }
            } else if (dichAutor.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name()) && !isAuthorizedAplPaginaWeb(ruolo,
                    ConstPrfDichAutor.TiDichAutor.PAGINA.name(), dichautor.getAplAzionePagina().getAplPaginaWeb())) {
                autor = new PrfAutor();
                autor.setPrfUsoRuoloApplic(ruolo);
                autor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.PAGINA.name());
                counterPages.increment();
                autor.setAplPaginaWeb(dichautor.getAplAzionePagina().getAplPaginaWeb());
                getEntityManager().persist(autor);
                getEntityManager().flush();
            }
        }
        return counter;
    }

    /**
     * Metodo che verifica la presenza di una autorizzazione dati i parametri:
     *
     * @param ruolo
     *            entity {@link PrfUsoRuoloApplic}
     * @param dichAutor
     *            tipo di dichiarazione
     * @param pagina
     *            la pagina web all'interno della dichiarazione
     *
     * @return true se esiste la dichiarazione
     */
    public boolean isAuthorizedAplPaginaWeb(PrfUsoRuoloApplic ruolo, String dichAutor, AplPaginaWeb pagina) {
        String queryStr = "SELECT auth FROM PrfAutor auth WHERE auth.prfUsoRuoloApplic = :ruolo AND auth.tiDichAutor = :dichautor AND auth.aplPaginaWeb = :pagina";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruolo", ruolo);
        q.setParameter("dichautor", dichAutor);
        q.setParameter("pagina", pagina);
        List<PrfAutor> dichiarazioni = q.getResultList();
        return !dichiarazioni.isEmpty();
    }

    /**
     * Metodo che verifica la presenza di autorizzazioni dati i ruoli e il nome del servizio web di cui verificare
     * l'autorizzazione
     *
     * @deprecated
     *
     * @param ruoli
     *            lista elementi di tipo {@link PrfRuolo}
     * @param servizioWeb
     *            nome servizio
     *
     * @return true se il servizioWeb è autorizzato
     */
    @Deprecated
    public boolean isWsEnabled(List<PrfRuolo> ruoli, String servizioWeb) {
        List<Long> idRuoli = new ArrayList<>();
        for (PrfRuolo r : ruoli) {
            idRuoli.add(r.getIdRuolo());
        }
        String queryStr = "SELECT servizioWeb FROM PrfRuolo ruoli" + " JOIN ruoli.prfUsoRuoloApplics ruoloApp"
                + " JOIN ruoloApp.prfAutors autors" + " JOIN autors.aplServizioWeb servizioWeb"
                + " WHERE ruoli.idRuolo IN (:ruoli) AND autors.tiDichAutor = '"
                + ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name() + "' AND servizioWeb.nmServizioWeb = :servizioWeb";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("ruoli", idRuoli);
        q.setParameter("servizioWeb", servizioWeb);
        List<AplServizioWeb> servizi = q.getResultList();
        return !servizi.isEmpty();
    }

    public PrfRuolo getRuoloById(BigDecimal idRuolo) {
        return getEntityManager().find(PrfRuolo.class, idRuolo.longValue());
    }

    public PrfDichAutor getDichAutorById(BigDecimal idDichAutor) {
        return getEntityManager().find(PrfDichAutor.class, idDichAutor.longValue());
    }

    public PrfRuolo salvaPrfRuolo(PrfRuolo ruolo, boolean modifica) {
        if (modifica) {
            return getEntityManager().merge(ruolo);
        } else {
            getEntityManager().persist(ruolo);
            return ruolo;
        }
    }

    public PrfUsoRuoloApplic getPrfUsoRuoloApplicById(BigDecimal idUsoRuoloApplic) {
        return getEntityManager().find(PrfUsoRuoloApplic.class, idUsoRuoloApplic.longValue());
    }

    /**
     * Richiamato per eliminare un record da PrfRuoloCategoria quando elimino una categoria associata ad un determinato
     * ruolo
     *
     * @param idRuolo
     *            id ruolo
     * @param tiCategRuolo
     *            tipo categoria ruolo
     */
    public void deletePrfRuoloCategoriaByRuoloAndCateg(long idRuolo, String tiCategRuolo) {
        String queryStr = "SELECT ruoloCategoria FROM PrfRuoloCategoria ruoloCategoria "
                + "WHERE ruoloCategoria.prfRuolo.idRuolo = :idRuolo "
                + "AND ruoloCategoria.tiCategRuolo = :tiCategRuolo";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idRuolo", idRuolo);
        query.setParameter("tiCategRuolo", tiCategRuolo);
        List<PrfRuoloCategoria> ruoloCategoria = query.getResultList();
        if (ruoloCategoria != null && !ruoloCategoria.isEmpty()) {
            getEntityManager().remove(ruoloCategoria.get(0));
            getEntityManager().flush();
        }
    }

    /**
     * Richiamato per eliminare un record da PrfUsoRuoloApplic quando elimino una applicazione associata ad un
     * determinato ruolo
     *
     * @param idRuolo
     *            id ruolo
     * @param idApplic
     *            id applicativo
     */
    public void deletePrfUsoRuoloApplicByUserAndApplic(long idRuolo, BigDecimal idApplic) {
        String queryStr = "SELECT usoRuolo FROM PrfUsoRuoloApplic usoRuolo "
                + "WHERE usoRuolo.prfRuolo.idRuolo = :idRuolo " + "AND usoRuolo.aplApplic.idApplic = :idApplic";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idRuolo", idRuolo);
        query.setParameter("idApplic", longFrom(idApplic));
        List<PrfUsoRuoloApplic> applic = query.getResultList();
        if (applic != null && !applic.isEmpty()) {
            getEntityManager().remove(applic.get(0));
            getEntityManager().flush();
        }
    }

    public void deleteDichAutorById(BigDecimal idDichAutor) {
        if (idDichAutor != null) {
            PrfDichAutor dich = getEntityManager().find(PrfDichAutor.class, idDichAutor.longValue());
            if (dich != null) {
                getEntityManager().remove(dich);
                getEntityManager().flush();
            }
        }
    }

    public void deletePrfRuolo(PrfRuolo ruolo) {
        if (ruolo != null) {
            getEntityManager().remove(ruolo);
            getEntityManager().flush();
        }
    }

    public UsrDichAbilOrganiz getUsrDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        return getEntityManager().find(UsrDichAbilOrganiz.class, idDichAbilOrganiz.longValue());
    }

    public UsrDichAbilDati getUsrDichAbilDati(BigDecimal idDichAbilDati) {
        return getEntityManager().find(UsrDichAbilDati.class, idDichAbilDati.longValue());
    }

    public UsrUser salvaUsrUser(UsrUser user, boolean modifica) {
        if (modifica) {
            UsrUser u = getEntityManager().merge(user);
            getEntityManager().flush(); // Per persistere immediatamente i dati
            return u;
        } else {
            getEntityManager().persist(user);
            getEntityManager().flush(); // Per persistere immediatamente i dati
            return user;
        }
    }

    public void persistLogAgente(LogAgente logAgente) {
        getEntityManager().persist(logAgente);
        getEntityManager().flush();
    }

    public LogAgente getLogAgenteByNmAgente(String nmAgente) {
        String queryStr = "SELECT l FROM LogAgente l WHERE l.nmAgente = :nmAgente";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nmAgente", nmAgente);
        List<LogAgente> l = q.getResultList();
        if (!l.isEmpty()) {
            return (LogAgente) l.get(0);
        } else {
            return null;
        }
    }

    public void deleteUsoRuoloUserDefault(BigDecimal idUsoRuoloUserDefault) {
        if (idUsoRuoloUserDefault != null) {
            UsrUsoRuoloUserDefault usoRuolo = getEntityManager().find(UsrUsoRuoloUserDefault.class,
                    idUsoRuoloUserDefault.longValue());
            if (usoRuolo != null) {
                getEntityManager().remove(usoRuolo);
                getEntityManager().flush();
            }
        }
    }

    public void deleteUsrDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        if (idDichAbilOrganiz != null) {
            UsrDichAbilOrganiz dich = getEntityManager().find(UsrDichAbilOrganiz.class, idDichAbilOrganiz.longValue());
            if (dich != null) {
                getEntityManager().remove(dich);
                getEntityManager().flush();
            }
        }
    }

    public void deleteUsrDichAbilDati(BigDecimal idDichAbilDati) {
        if (idDichAbilDati != null) {
            UsrDichAbilDati dich = getEntityManager().find(UsrDichAbilDati.class, idDichAbilDati.longValue());
            if (dich != null) {
                getEntityManager().remove(dich);
                getEntityManager().flush();
            }
        }
    }

    public void deleteUsrDichAbilEnteConvenz(BigDecimal idDichAbilEnteConvenz) {
        if (idDichAbilEnteConvenz != null) {
            UsrDichAbilEnteConvenz dich = getEntityManager().find(UsrDichAbilEnteConvenz.class,
                    idDichAbilEnteConvenz.longValue());
            if (dich != null) {
                getEntityManager().remove(dich);
                getEntityManager().flush();
            }
        }
    }

    /**
     * @deprecated
     *
     * @param usrUsoUserApplic
     *            usr uso user applic
     */
    @Deprecated
    public void deleteAbilStrut(UsrUsoUserApplic usrUsoUserApplic) {
        String queryStr = "DELETE FROM RisAbilStrut abils WHERE abils.usrUsoUserApplic = :usouser";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("usouser", usrUsoUserApplic);
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * @deprecated
     *
     * @param usrUsoUserApplic
     *            usr uso user applic
     */
    @Deprecated
    public void deleteDichAbilOrganiz(List<Long> usrUsoUserApplic) {
        String queryStr = "DELETE FROM UsrDichAbilOrganiz abils WHERE abils.usrUsoUserApplic.idUsoUserApplic IN (:usouser)";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("usouser", usrUsoUserApplic);
        q.executeUpdate();
        getEntityManager().flush();
    }

    public void deleteAbilOrganiz(List<Long> usrUsoUserApplic) {
        String queryStr = "DELETE FROM UsrAbilOrganiz abils WHERE abils.usrUsoUserApplic.idUsoUserApplic IN (:usouser)";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("usouser", usrUsoUserApplic);
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * @deprecated
     *
     * @param usrUsoUserApplic
     *            usr uso user applic
     */
    @Deprecated
    public void deleteDichAbilDati(List<Long> usrUsoUserApplic) {
        String queryStr = "DELETE FROM UsrDichAbilDati abils WHERE abils.usrUsoUserApplic.idUsoUserApplic IN (:usouser)";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("usouser", usrUsoUserApplic);
        q.executeUpdate();
        getEntityManager().flush();
    }

    public void deleteAbilDati(List<Long> usrUsoUserApplic) {
        String queryStr = "DELETE FROM UsrAbilDati abils WHERE abils.usrUsoUserApplic.idUsoUserApplic IN (:usouser)";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("usouser", usrUsoUserApplic);
        q.executeUpdate();
        getEntityManager().flush();
    }

    public void deleteDichAbilEnteConvenz(BigDecimal idUserIam) {
        String queryStr = "DELETE FROM UsrDichAbilEnteConvenz abils WHERE abils.usrUser.idUserIam = :idUserIam";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", idUserIam.longValue());
        q.executeUpdate();
        getEntityManager().flush();
    }

    public void deleteAbilEnteSiam(BigDecimal idUserIam) {
        String queryStr = "DELETE FROM UsrAbilEnteSiam abils WHERE abils.usrUser.idUserIam = :idUserIam";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", idUserIam.longValue());
        q.executeUpdate();
        getEntityManager().flush();
    }

    public void deleteOrgEnteUserRif(BigDecimal idUserIam) {
        String queryStr = "DELETE FROM OrgEnteUserRif rif WHERE rif.usrUser.idUserIam = :idUserIam";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", idUserIam.longValue());
        q.executeUpdate();
        getEntityManager().flush();
    }

    public void deleteUsoUserApplic(BigDecimal idUserIam) {
        String queryStr = "DELETE FROM UsrUsoUserApplic usoUserApplic WHERE usoUserApplic.usrUser.idUserIam = :idUserIam";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", longFrom(idUserIam));
        q.executeUpdate();
        getEntityManager().flush();
    }

    public List<Object[]> getIncoherenceOrganiz(long idUserIam) {
        String queryStr = "SELECT DISTINCT abil_dati.idApplic, abil_dati.dlCompositoOrganiz, abil_dati.nmApplic "
                + "FROM UsrVAbilDati abil_dati " + "WHERE abil_dati.id.idUserIam = :idUserIam " + "AND NOT EXISTS "
                + " (SELECT abil_org.id " + " FROM UsrVAbilOrganiz abil_org "
                + " WHERE abil_org.id.idOrganizIam = abil_dati.idOrganizIam"
                + " AND abil_org.id.idUserIam = :idUserIam ) "
                + "ORDER BY abil_dati.nmApplic, abil_dati.dlCompositoOrganiz";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        return query.getResultList();
    }

    public UsrIndIpUser getUsrIndIpUserById(BigDecimal idIndIpUser) {
        return getEntityManager().find(UsrIndIpUser.class, idIndIpUser.longValue());
    }

    public UsrUsoUserApplic getUsrUsoUserApplicById(BigDecimal idUsoUsrApplic) {
        return getEntityManager().find(UsrUsoUserApplic.class, idUsoUsrApplic.longValue());
    }

    /**
     * @deprecated
     *
     * @param idUserIam
     *            id user
     * @param idApplic
     *            id applicazione
     *
     * @return l'oggetto {@link UsrUsoUserApplic}
     */
    @Deprecated
    public UsrUsoUserApplic getUsrUsoUserApplicByUserIamApplic(BigDecimal idUserIam, BigDecimal idApplic) {
        UsrUsoUserApplic uuua = null;
        List<UsrUsoUserApplic> list = new ArrayList<>();
        if (idUserIam != null && idApplic != null) {
            String queryStr = "SELECT usrUsoUserApplic " + "FROM UsrUsoUserApplic usrUsoUserApplic "
                    + "WHERE usrUsoUserApplic.usrUser.idUserIam = :idUserIam "
                    + "AND usrUsoUserApplic.aplApplic.idApplic = :idApplic ";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("idUserIam", idUserIam);
            query.setParameter("idApplic", idApplic);
            list = query.getResultList();
        }
        if (!list.isEmpty()) {
            uuua = list.get(0);
        }
        return uuua;
    }

    /**
     * @deprecated
     *
     * @param idUsoUsrApplic
     *            id uso user applic
     *
     * @return lista di {@link UsrUsoUserApplic}
     */
    @Deprecated
    public List<UsrUsoUserApplic> getUsrUsoUserApplicListById(Set<BigDecimal> idUsoUsrApplic) {
        List<UsrUsoUserApplic> list = new ArrayList<>();
        if (!idUsoUsrApplic.isEmpty()) {
            String queryStr = "SELECT usrUsoUserApplic " + "FROM UsrUsoUserApplic usrUsoUserApplic "
                    + "WHERE usrUsoUserApplic.idUsoUserApplic IN (:idUsoUsrApplic) ";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("idUsoUsrApplic", idUsoUsrApplic);
            list = query.getResultList();
        }
        return list;
    }

    public List<UsrUsoUserApplic> getUsrUsoUserApplic(BigDecimal idUserIam) {
        String queryStr = "SELECT usrUsoUserApplic " + "FROM UsrUsoUserApplic usrUsoUserApplic "
                + "WHERE usrUsoUserApplic.usrUser.idUserIam = :idUserIam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        return query.getResultList();
    }

    /**
     * @deprecated
     *
     * @return lista di {@link UsrUsoUserApplic}
     */
    @Deprecated
    public List<UsrUsoUserApplic> getAllUsrUsoUserApplics() {
        Query q = getEntityManager().createQuery("SELECT u FROM UsrUsoUserApplic u WHERE u.aplApplic.nmApplic = :appl");
        q.setParameter("appl", Constants.SACERIAM);
        return q.getResultList();
    }

    public void checkAplApplicURL(String nmApplic, boolean hasBeenModified) throws EJBTransactionRolledbackException {
        AplApplic applic = getAplApplic(nmApplic);
        if (applic.getDsUrlReplicaUser() != null && hasBeenModified) {

            // Utenti che usano il ruolo di default
            Query q = getEntityManager().createQuery(
                    "SELECT DISTINCT usr FROM UsrUsoUserApplic uso_user_applic JOIN uso_user_applic.usrUser usr "
                            + "JOIN uso_user_applic.usrUsoRuoloUserDefaults ruo_def JOIN ruo_def.prfRuolo ruo "
                            + "JOIN ruo.prfUsoRuoloApplics uso_ruo JOIN uso_ruo.prfAutors aut "
                            + "WHERE uso_user_applic.aplApplic.idApplic = :idApplic "
                            + "AND aut.tiDichAutor = 'SERVIZIO_WEB' ");

            // Utenti che usano il ruolo specificato
            Query q1 = getEntityManager().createQuery(
                    "SELECT DISTINCT usr FROM UsrUsoUserApplic uso_user_applic JOIN uso_user_applic.usrUser usr "
                            + "JOIN uso_user_applic.usrDichAbilOrganizs abil_organiz JOIN abil_organiz.usrUsoRuoloDiches ruo_dich "
                            + "JOIN ruo_dich.prfRuolo ruo JOIN ruo.prfUsoRuoloApplics uso_ruo "
                            + "JOIN uso_ruo.prfAutors aut " + "WHERE uso_user_applic.aplApplic.idApplic = :idApplic "
                            + "AND aut.tiDichAutor = 'SERVIZIO_WEB' ");

            q.setParameter("idApplic", applic.getIdApplic());
            List<UsrUser> listUsrUso = q.getResultList();

            q1.setParameter("idApplic", applic.getIdApplic());
            List<UsrUser> listUsrUso1 = q1.getResultList();

            listUsrUso.addAll(listUsrUso1);

            // Per ogni utente, registro nel log
            for (UsrUser u : listUsrUso) {
                LogUserDaReplic ur = new LogUserDaReplic();
                ur.setAplApplic(applic);
                ur.setIdUserIam(new BigDecimal(u.getIdUserIam()));
                ur.setNmUserid(u.getNmUserid());
                ur.setTiOperReplic(ApplEnum.TiOperReplic.MOD.name());
                ur.setTiStatoReplic(ApplEnum.TiStatoReplic.DA_REPLICARE.name());
                ur.setDtLogUserDaReplic(new Date());
                getEntityManager().persist(ur);
                getEntityManager().flush();
            }
        }
    }

    /**
     * Registra nel log degli utenti da replicare
     *
     * @param user
     *            l'utente da replicare
     * @param applic
     *            l'applicazione inserita/modificata/cancellata
     * @param oper
     *            il tipo di operazione
     */
    public void registraLogUserDaReplic(UsrUser user, AplApplic applic, ApplEnum.TiOperReplic oper) {
        LogUserDaReplic ur = new LogUserDaReplic();
        ur.setAplApplic(applic);
        ur.setIdUserIam(new BigDecimal(user.getIdUserIam()));
        ur.setNmUserid(user.getNmUserid());
        ur.setTiOperReplic(oper.name());
        ur.setTiStatoReplic(ApplEnum.TiStatoReplic.DA_REPLICARE.name());
        ur.setDtLogUserDaReplic(new Date());
        getEntityManager().persist(ur);
        getEntityManager().flush();
    }

    public void registraLogUserDaReplic(BigDecimal idUserIam, BigDecimal idApplic, ApplEnum.TiOperReplic oper) {
        UsrUser user = getEntityManager().find(UsrUser.class, idUserIam.longValue());
        AplApplic applic = getEntityManager().find(AplApplic.class, idApplic.longValue());
        LogUserDaReplic ur = new LogUserDaReplic();
        ur.setAplApplic(applic);
        ur.setIdUserIam(idUserIam);
        ur.setNmUserid(user.getNmUserid());
        ur.setTiOperReplic(oper.name());
        ur.setTiStatoReplic(ApplEnum.TiStatoReplic.DA_REPLICARE.name());
        ur.setDtLogUserDaReplic(new Date());
        getEntityManager().persist(ur);
        getEntityManager().flush();
    }

    public void registraLogUserDaReplic(BigDecimal idRuolo, AplApplic applic) throws EJBTransactionRolledbackException {
        if (idRuolo != null && applic != null) {
            // Utenti che usano il ruolo di default
            Query q = getEntityManager().createQuery(
                    "SELECT DISTINCT usr FROM UsrUsoUserApplic uso_user_applic JOIN uso_user_applic.usrUser usr "
                            + "JOIN uso_user_applic.usrUsoRuoloUserDefaults ruo_def "
                            + "WHERE uso_user_applic.aplApplic.idApplic = :idApplic "
                            + "AND ruo_def.prfRuolo.idRuolo = :idRuolo ");

            // Utenti che usano il ruolo specificato
            Query q1 = getEntityManager().createQuery(
                    "SELECT DISTINCT usr FROM UsrUsoUserApplic uso_user_applic JOIN uso_user_applic.usrUser usr "
                            + "JOIN uso_user_applic.usrDichAbilOrganizs abil_organiz JOIN abil_organiz.usrUsoRuoloDiches ruo_dich "
                            + "WHERE uso_user_applic.aplApplic.idApplic = :idApplic "
                            + "AND ruo_dich.prfRuolo.idRuolo = :idRuolo ");

            q.setParameter("idRuolo", longFrom(idRuolo));
            q.setParameter("idApplic", applic.getIdApplic());
            List<UsrUser> listUsrUso = q.getResultList();

            q1.setParameter("idRuolo", longFrom(idRuolo));
            q1.setParameter("idApplic", applic.getIdApplic());
            List<UsrUser> listUsrUso1 = q1.getResultList();

            listUsrUso.addAll(listUsrUso1);

            // Per ogni utente, registro nel log
            for (UsrUser u : listUsrUso) {
                LogUserDaReplic ur = new LogUserDaReplic();
                ur.setAplApplic(applic);
                ur.setIdUserIam(new BigDecimal(u.getIdUserIam()));
                ur.setNmUserid(u.getNmUserid());
                ur.setTiOperReplic(ApplEnum.TiOperReplic.MOD.name());
                ur.setTiStatoReplic(ApplEnum.TiStatoReplic.DA_REPLICARE.name());
                ur.setDtLogUserDaReplic(new Date());
                getEntityManager().persist(ur);
                getEntityManager().flush();
            }
        }
    }

    /**
     * Elimina un record da UsrIndIpUser quando elimino un indirizzo IP associato ad un determinato utente
     *
     * @param idIndIpUser
     *            id indirizzo ip user
     */
    public void deleteUsrIndIpUser(BigDecimal idIndIpUser) {
        if (idIndIpUser != null) {
            UsrIndIpUser indIpUser = getEntityManager().find(UsrIndIpUser.class, idIndIpUser.longValue());
            if (indIpUser != null) {
                getEntityManager().remove(indIpUser);
                getEntityManager().flush();
            }
        }
    }

    /**
     * Elimina un record da UsrUsoUserApplic quando elimino una applicazione associata ad un determinato utente
     *
     * @param idUsoUserApplic
     *            id applicativo
     */
    public void deleteUsrUsoUserApplic(BigDecimal idUsoUserApplic) {
        if (idUsoUserApplic != null) {
            UsrUsoUserApplic usoUser = getEntityManager().find(UsrUsoUserApplic.class, idUsoUserApplic.longValue());
            if (usoUser != null) {
                getEntityManager().remove(usoUser);
                getEntityManager().flush();
            }
        }
    }

    public UsrOrganizIam getUsrOrganizIamById(BigDecimal idOrganizIam) {
        if (idOrganizIam != null) {
            return getEntityManager().find(UsrOrganizIam.class, idOrganizIam.longValue());
        }
        return null;
    }

    public AplClasseTipoDato getAplClasseTipoDatoById(BigDecimal idClasseTipoDato) {
        return getEntityManager().find(AplClasseTipoDato.class, idClasseTipoDato.longValue());
    }

    public UsrTipoDatoIam getUsrTipoDatoIamById(BigDecimal idUserTipoDato) {
        return getEntityManager().find(UsrTipoDatoIam.class, idUserTipoDato.longValue());
    }

    /**
     * @deprecated
     *
     * @param idUsoRuoloUserDefaul
     *            id ruolo di default
     *
     * @return {@link UsrUsoRuoloUserDefault}
     */
    @Deprecated
    public UsrUsoRuoloUserDefault getUsrUsoRuoloUserDefault(BigDecimal idUsoRuoloUserDefaul) {
        return getEntityManager().find(UsrUsoRuoloUserDefault.class, idUsoRuoloUserDefaul.longValue());
    }

    public List<UsrVCheckRuoloDefault> getUsrVCheckRuoloDefaultList(long idUserCorrente, long idRuolo, long idApplic,
            String dichAutor) {
        String queryStr = "SELECT check FROM UsrVCheckRuoloDefault check "
                + "WHERE check.id.idUserCorrente = :idUserCorrente " + "AND check.id.idRuoloAggiunto = :idRuolo "
                + "AND check.idApplicScelta = :idApplic ";

        if (dichAutor != null) {
            queryStr = queryStr + "AND check.id.tiDichAutor = :dichAutor ";
        }
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserCorrente", bigDecimalFrom(idUserCorrente));
        q.setParameter("idRuolo", bigDecimalFrom(idRuolo));
        q.setParameter("idApplic", bigDecimalFrom(idApplic));
        if (dichAutor != null) {
            q.setParameter("dichAutor", dichAutor);
        }
        return q.getResultList();
    }

    /**
     * Ritorna la lista delle organizzazioni abilitate da una dichiarazione di abilitazione alle organizzazioni appena
     * aggiunta, che però non sono abilitate per l'utente corrente (amministratore)
     *
     * @param idUserIamCorrente
     *            id user IAM
     * @param idDichAbilOrganizAggiunta
     *            id dichiarazione
     *
     * @return la lista delle organizzazioni non abilitate per l'amministratore rispetto alla dichiarazione
     */
    public List<UsrVCheckDichAbilOrganiz> getUsrVCheckDichAbilOrganizList(long idUserIamCorrente,
            long idDichAbilOrganizAggiunta) {
        String queryStr = "SELECT check FROM UsrVCheckDichAbilOrganiz check "
                + "WHERE check.id.idUserIamCorrente = :idUserIamCorrente "
                + "AND check.id.idDichAbilOrganizAggiunta = :idDichAbilOrganizAggiunta ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCorrente", bigDecimalFrom(idUserIamCorrente));
        q.setParameter("idDichAbilOrganizAggiunta", bigDecimalFrom(idDichAbilOrganizAggiunta));
        return q.getResultList();
    }

    public List<UsrVCheckDichAbilDati> getUsrVCheckDichAbilDatiList(long idUserIamCorrente,
            long idDichAbilDatiAggiunta) {
        String queryStr = "SELECT check FROM UsrVCheckDichAbilDati check "
                + "WHERE check.id.idUserIamCorrente = :idUserIamCorrente "
                + "AND check.id.idDichAbilDatiAggiunta = :idDichAbilDatiAggiunta ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCorrente", bigDecimalFrom(idUserIamCorrente));
        q.setParameter("idDichAbilDatiAggiunta", bigDecimalFrom(idDichAbilDatiAggiunta));
        return q.getResultList();
    }

    public List<UsrVCheckDichAbilEnte> getUsrVCheckDichAbilEnteList(long idUserIamCorrente,
            long idDichAbilEnteAggiunta) {
        String queryStr = "SELECT check FROM UsrVCheckDichAbilEnte check "
                + "WHERE check.id.idUserIamCorrente = :idUserIamCorrente "
                + "AND check.id.idDichAbilEnteAggiunta = :idDichAbilEnteAggiunta ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCorrente", bigDecimalFrom(idUserIamCorrente));
        q.setParameter("idDichAbilEnteAggiunta", bigDecimalFrom(idDichAbilEnteAggiunta));
        return q.getResultList();
    }

    public UsrUsoRuoloDich getUsrUsoRuoloDich(UsrDichAbilOrganiz dich, PrfRuolo prfRuolo, UsrOrganizIam organizIamRuolo,
            String tiScopoRuolo) {
        Query q = getEntityManager().createQuery(
                "SELECT r FROM UsrUsoRuoloDich r " + "WHERE r.usrDichAbilOrganiz = :dich " + "AND r.prfRuolo = :ruolo "
                        + "AND r.usrOrganizIam = :organizIamRuolo " + "AND r.tiScopoRuolo = :tiScopoRuolo");
        q.setParameter("dich", dich);
        q.setParameter("ruolo", prfRuolo);
        q.setParameter("organizIamRuolo", organizIamRuolo);
        q.setParameter("tiScopoRuolo", tiScopoRuolo);
        List<UsrUsoRuoloDich> usoruolodich = q.getResultList();
        UsrUsoRuoloDich result = null;
        if (!usoruolodich.isEmpty() && usoruolodich.size() == 1) {
            result = usoruolodich.get(0);
        }
        return result;
    }

    public void deleteUsrUsoRuoloDich(BigDecimal idUsoRuoloDich) {
        UsrUsoRuoloDich usoRuoloDich = getEntityManager().find(UsrUsoRuoloDich.class, idUsoRuoloDich.longValue());
        getEntityManager().remove(usoRuoloDich);
        getEntityManager().flush();
    }

    /**
     * Metodo che ritorna la lista di un determinato tipo di dichiarazione in base al ruolo, per tutte le applicazioni
     * esclusa SACER_IAM
     *
     * @param idRuolo
     *            l'id del ruolo
     * @param tiDichAutor
     *            il tipo di dichiarazione
     *
     * @return la lista di dichiarazioni
     */
    public List<PrfVLisDichAutor> getPrfVLisDichAutorList(BigDecimal idRuolo, String tiDichAutor) {
        String queryStr = "SELECT dichAutor " + "FROM PrfVLisDichAutor dichAutor "
                + "WHERE dichAutor.idRuolo = :idRuolo " + "AND dichAutor.tiDichAutor = :tiDichAutor "
                + "AND dichAutor.nmApplic != :nmApplic ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idRuolo", idRuolo);
        q.setParameter("tiDichAutor", tiDichAutor);
        q.setParameter("nmApplic", "SACER_IAM");
        return q.getResultList();
    }

    public List<UsrVCheckRuoloDich> getUsrVCheckRuoloDichList(long idUserCorrente, BigDecimal idRuolo,
            BigDecimal idApplic, BigDecimal idOrganizIamRuolo, String dichAutor) {

        String queryStr = "SELECT check FROM UsrVCheckRuoloDich check "
                + "WHERE check.id.idUserCorrente = :idUserCorrente " + "AND check.id.idRuoloAggiunto = :idRuolo "
                + "AND check.idApplicDich = :idApplic " + "AND check.idOrganizIamRuolo = :idOrganizIamRuolo ";

        if (dichAutor != null) {
            queryStr = queryStr + "AND check.id.tiDichAutor = :dichAutor ";
        }
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserCorrente", bigDecimalFrom(idUserCorrente));
        q.setParameter("idRuolo", idRuolo);
        q.setParameter("idApplic", idApplic);
        q.setParameter("idOrganizIamRuolo", idOrganizIamRuolo);
        if (dichAutor != null) {
            q.setParameter("dichAutor", dichAutor);
        }
        return q.getResultList();
    }

    /**
     * Per ogni applicazione usata dall'utente passato in ingresso, registra un record nel log degli utenti da replicare
     * con l'operazione passata in input
     *
     * @param username
     *            username
     * @param oper
     *            tipo operazione {@link TiOperReplic}
     */
    public void registraLogUserDaReplic(String username, ApplEnum.TiOperReplic oper) {
        Set<BigDecimal> idApplicSet = new HashSet<>();
        UsrUser u = findUserByName(username);
        List<UsrUsoUserApplic> applicList = getUsrUsoUserApplic(new BigDecimal(u.getIdUserIam()));
        for (UsrUsoUserApplic applic : applicList) {
            idApplicSet.add(new BigDecimal(applic.getAplApplic().getIdApplic()));
        }
        if (!idApplicSet.isEmpty()) {
            // "Filtro" le applicazioni non considerando Sacer_Iam e Dpi
            List<AplApplic> applicUsateFiltrateList = getAplApplicFiltrate(idApplicSet);
            // Per ogni applicazione scrivo nel log
            for (AplApplic applicUsataFiltrata : applicUsateFiltrateList) {
                registraLogUserDaReplic(applicList.get(0).getUsrUser(), applicUsataFiltrata, oper);
            }
        }
    }

    /**
     * Determina se l’utente e’ abilitato a dei servizi web in funzione dei suoi ruoli di default
     *
     * @deprecated
     *
     * @param idUserIam
     *            id user IAM
     *
     * @return true se autorizzato
     */
    @Deprecated
    public boolean isServiceAuthorized(Long idUserIam) {
        Long num;

        String queryStr = "SELECT COUNT(u) " + "FROM UsrVAllAutor u " + "WHERE u.idUserIam = :idUserIam "
                + "AND u.tiUsoRuo = 'DEF' " + "AND u.tiDichAutor = 'SERVIZIO_WEB' ";

        javax.persistence.Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);

        num = (Long) query.getSingleResult();

        return num != null && num > 0;
    }

    public List<UsrVCreaAbilDati> getUsrVCreaAbilDati(String nmUserid, String nmApplic, String nmClasseTipoDato) {
        String queryStr = "SELECT u FROM UsrVCreaAbilDati u " + "WHERE u.nmUserid = :nmUserid "
                + "AND u.nmApplic = :nmApplic " + "AND u.nmClasseTipoDato = :nmClasseTipoDato ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nmUserid", nmUserid);
        q.setParameter("nmApplic", nmApplic);
        q.setParameter("nmClasseTipoDato", nmClasseTipoDato);
        return q.getResultList();
    }

    /**
     * Aggiunge le dichiarazioni di abilitazione ai tipi dato (USR_DICH_ABIL_DATI) automatiche, sia inserendole come
     * record nella tabella, sia inserendole nel tablebean da visualizzare nell'online
     *
     * @param creaAbilDato
     *            il record con i dati con cui creare la dichiarazione di abilitazione
     * @param user
     *            entity {@link UsrUser}
     * @param usrUsoUserApplic
     *            entity {@link UsrUsoUserApplic}
     * @param tipiDato
     *            il tablebean cui aggiungere la dichiarazione per mostrarlo poi nell'online
     *
     * @return pk
     */
    public long aggiungiDichAbilTipoDato(UsrVCreaAbilDati creaAbilDato, UsrUser user, UsrUsoUserApplic usrUsoUserApplic,
            UsrDichAbilDatiTableBean tipiDato) {

        UsrDichAbilDati dich = new UsrDichAbilDati();
        UsrDichAbilDatiRowBean dichRB = new UsrDichAbilDatiRowBean();
        //
        dich.setTiScopoDichAbilDati(creaAbilDato.getUsrVCreaAbilDatiId().getTiScopoDichAbilDati());
        dichRB.setTiScopoDichAbilDati(creaAbilDato.getUsrVCreaAbilDatiId().getTiScopoDichAbilDati());
        //
        UsrOrganizIam organiz = null;
        if (creaAbilDato.getUsrVCreaAbilDatiId().getIdOrganizIam().compareTo(BigDecimal.ZERO) > 0) {
            organiz = this.getUsrOrganizIamById(creaAbilDato.getUsrVCreaAbilDatiId().getIdOrganizIam());
        }
        dich.setUsrOrganizIam(organiz);
        dichRB.setIdOrganizIam(creaAbilDato.getUsrVCreaAbilDatiId().getIdOrganizIam());
        //
        AplClasseTipoDato classeTipoDato = null;
        if (creaAbilDato.getUsrVCreaAbilDatiId().getIdClasseTipoDato() != null) {
            classeTipoDato = (this
                    .getAplClasseTipoDatoById(creaAbilDato.getUsrVCreaAbilDatiId().getIdClasseTipoDato()));
        }
        dich.setAplClasseTipoDato(classeTipoDato);
        dichRB.setIdClasseTipoDato(creaAbilDato.getUsrVCreaAbilDatiId().getIdClasseTipoDato());
        //
        dich.setUsrTipoDatoIam(null);
        //
        dich.setUsrUsoUserApplic(usrUsoUserApplic);
        usrUsoUserApplic.getUsrDichAbilDatis().add(dich);
        dichRB.setIdUsoUserApplic(new BigDecimal(usrUsoUserApplic.getIdUsoUserApplic()));
        //
        if (dichRB.getIdSuptEstEnteConvenz() != null) {
            dich.setOrgSuptEsternoEnteConvenz(
                    getEntityManager().find(OrgSuptEsternoEnteConvenz.class, dichRB.getIdSuptEstEnteConvenz()));
        }
        if (dichRB.getIdVigilEnteProdut() != null) {
            dich.setOrgVigilEnteProdut(
                    getEntityManager().find(OrgVigilEnteProdut.class, dichRB.getIdVigilEnteProdut()));
        }
        if (dichRB.getIdAppartCollegEnti() != null) {
            dich.setOrgAppartCollegEnti(
                    getEntityManager().find(OrgAppartCollegEnti.class, dichRB.getIdAppartCollegEnti()));
        }
        dich.setDsCausaleDich(dichRB.getDsCausaleDich());

        // Aggiungo i campi mancanti
        dichRB.setString("nm_applic", usrUsoUserApplic.getAplApplic().getNmApplic());
        dichRB.setString("nm_classe_tipo_dato", (classeTipoDato != null ? classeTipoDato.getNmClasseTipoDato() : ""));
        if (organiz != null) {
            UsrVTreeOrganizIam tree = this.getUsrVTreeOrganizIam(new BigDecimal(organiz.getIdOrganizIam()));
            dichRB.setString("dl_composito_organiz", tree.getDlCompositoOrganiz());
        }
        tipiDato.add(dichRB);

        getEntityManager().persist(dich);
        getEntityManager().flush();

        return dich.getIdDichAbilDati();
    }

    /**
     * Aggiunge le abilitazioni alle organizzazioni (USR_ABIL_ORGANIZ) ricavandole dalla relativa vista
     *
     * @param idUserIam
     *            id user IAM
     * @param idApplic
     *            di applicazione
     */
    public void aggiungiAbilOrganizToAdd(long idUserIam, long idApplic) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ORGANIZ (id_abil_organiz, id_uso_user_applic, id_organiz_iam, id_dich_abil_organiz, ds_causale_abil, id_appart_colleg_enti, id_supt_est_ente_convenz, id_vigil_ente_produt) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ORGANIZ.nextval)), id_uso_user_applic, id_organiz_iam, id_dich_abil_organiz, ds_causale_abil, id_appart_colleg_enti, id_supt_est_ente_convenz, id_vigil_ente_produt FROM USR_V_ABIL_ORGANIZ_TO_ADD u WHERE u.ID_USER_IAM = ? AND u.id_applic = ? ");

        q.setParameter(1, idUserIam);
        q.setParameter(2, idApplic);

        q.executeUpdate();

    }

    public List<UsrVAbilOrganizToAdd> getAbilOrganizToAdd(long idUserIam, long idApplic) {

        Query q = getEntityManager().createQuery(
                "SELECT u FROM UsrVAbilOrganizToAdd u WHERE u.id.idUserIam = :idUserIam AND u.idApplic = :idApplic ");

        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.setParameter("idApplic", bigDecimalFrom(idApplic));

        return q.getResultList();

    }

    /**
     * Aggiunge le abilitazioni ai tipi dato (USR_ABIL_DATI) ricavandole dalla relativa vista
     *
     * @param idUserIam
     *            id user IAM
     * @param idApplic
     *            id applicazione
     * @param idClasseTipoDato
     *            id classe tipo dato
     *
     * @return pk
     */
    public int aggiungiAbilDatiToAdd(long idUserIam, long idApplic, Long idClasseTipoDato) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_DATI (id_abil_dati, id_uso_user_applic, id_tipo_dato_iam, id_dich_abil_dati, ds_causale_abil, id_appart_colleg_enti, id_supt_est_ente_convenz, id_vigil_ente_produt) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_DATI.nextval)), id_uso_user_applic, id_tipo_dato_iam, id_dich_abil_dati, ds_causale_abil, id_appart_colleg_enti, id_supt_est_ente_convenz, id_vigil_ente_produt FROM USR_V_ABIL_DATI_TO_ADD u WHERE u.ID_USER_IAM = ? AND u.id_applic = ? ");

        q.setParameter(1, idUserIam);
        q.setParameter(2, idApplic);

        return q.executeUpdate();
    }

    public List<UsrVAbilDatiToAdd> getAbilDatiToAdd(long idUserIam, long idApplic) {

        Query q = getEntityManager().createQuery(
                "SELECT u FROM UsrVAbilDatiToAdd u WHERE u.id.idUserIam = :idUserIam AND u.idApplic = :idApplic ");

        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.setParameter("idApplic", bigDecimalFrom(idApplic));

        return q.getResultList();

    }

    /**
     * Aggiunge le abilitazioni ai tipi dato (USR_ABIL_ENTE_SIAM) ricavandole dalla relativa vista
     *
     * @param idUserIam
     *            id user IAM
     */
    public void aggiungiAbilEnteToAdd(long idUserIam) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, id_dich_abil_ente_convenz, ds_causale_abil, id_appart_colleg_enti) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_DATI.nextval)), id_user_iam, id_ente_convenz, id_dich_abil_ente_convenz, ds_causale_abil, id_appart_colleg_enti FROM USR_V_ABIL_ENTE_TO_ADD u WHERE u.ID_USER_IAM = ? ");

        q.setParameter(1, idUserIam);
        q.executeUpdate();

    }

    public UsrVTreeOrganizIam getUsrVTreeOrganizIam(BigDecimal idOrganizIam) {
        if (idOrganizIam != null) {
            Query query = getEntityManager().createQuery(
                    "SELECT user FROM UsrVTreeOrganizIam user " + "WHERE user.idOrganizIam = :idOrganizIam");
            query.setParameter("idOrganizIam", idOrganizIam);
            List<UsrVTreeOrganizIam> utenti = query.getResultList();
            if (utenti.isEmpty()) {
                throw new NoResultException("Nessun UsrVTreeOrganizIam trovato con idOrganizIam " + idOrganizIam);
            }
            return utenti.get(0);
        } else {
            return new UsrVTreeOrganizIam();
        }
    }

    public String getTipologiaUtenteAdmin(Long idUserAdmin) {
        Query query = getEntityManager()
                .createQuery("SELECT user.tipoUser FROM UsrUser user " + "WHERE user.idUserIam = :idUserAdmin");
        query.setParameter("idUserAdmin", idUserAdmin);
        return (String) query.getSingleResult();
    }

    public List<UsrVLisEnteByAbilOrg> retrieveUsrVLisEnteByAbilOrg(BigDecimal idUserIam) {
        Query query = getEntityManager()
                .createQuery("SELECT userEnteByAbilOrg " + "FROM UsrVLisEnteByAbilOrg userEnteByAbilOrg "
                        + "WHERE userEnteByAbilOrg.id.idUserIam = :idUserIam ");
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idUserIam", idUserIam);
        return query.getResultList();
    }

    // PG: Secondo me caro metodo dovresti stare in EntiConvenzionatiHelper e non qui...
    public OrgAccordoEnte getLastAccordoEnte(BigDecimal idEnteConvenz, Date dataOdierna) {
        Query query = getEntityManager().createQuery("SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND accordoEnte.dtDecAccordo <= :dataOdierna "
                + "AND accordoEnte.dtFineValidAccordo >= :dataOdierna ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dataOdierna", dataOdierna);
        List<OrgAccordoEnte> accordoEnteList = query.getResultList();
        if (!accordoEnteList.isEmpty()) {
            return accordoEnteList.get(0);
        }
        return null;
    }

    /**
     * @deprecated
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente ente convenzionato
     *
     * @return lista degli id degli utenti
     */
    @Deprecated
    public List<BigDecimal> getUtentiAbilitatiAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT abilAmbEnteConvenz.id.idUserIam " + "FROM UsrVAbilAmbEnteConvenz abilAmbEnteConvenz "
                        + "WHERE abilAmbEnteConvenz.id.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        return query.getResultList();
    }

    /**
     * @deprecated
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return lista degli id degli utenti
     */
    @Deprecated
    public List<BigDecimal> getUtentiAbilitatiEnteConvenz(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT abilEnteConvenz.id.idUserIam " + "FROM UsrVAbilEnteConvenz abilEnteConvenz "
                        + "WHERE abilEnteConvenz.id.idEnteConvenz = :idEnteConvenz ");
        query.setParameter("idEnteConvenz", idEnteConvenz);
        return query.getResultList();
    }

    public List<BigDecimal> getUtentiAbilitatiAmbEnteXnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT abilAmbEnteXente.id.idUserIam " + "FROM UsrVAbilAmbEnteXente abilAmbEnteXente "
                        + "WHERE abilAmbEnteXente.id.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        return query.getResultList();
    }

    public BigDecimal getLastPgOldPsw(long idUserIam) {
        Query query = getEntityManager().createQuery(
                "SELECT COALESCE(MAX(oldPsw.pgOldPsw),0) FROM UsrOldPsw oldPsw WHERE oldPsw.usrUser.idUserIam = :idUserIam ");
        query.setParameter("idUserIam", idUserIam);
        return (BigDecimal) query.getSingleResult();
    }

    public boolean isInOldLastPasswords(String newPassword, long idUserIam, int numOldPsw) {
        Query query = getEntityManager().createQuery(
                "SELECT oldPsw FROM UsrOldPsw oldPsw WHERE oldPsw.usrUser.idUserIam = :idUserIam ORDER BY oldPsw.pgOldPsw DESC ");
        query.setParameter("idUserIam", idUserIam);
        query.setMaxResults(numOldPsw);
        List<UsrOldPsw> oldPswList = query.getResultList();
        for (UsrOldPsw oldPsw : oldPswList) {
            String saltedPassword = PwdUtil.encodePBKDF2Password(PwdUtil.decodeUFT8Base64String(oldPsw.getCdSalt()),
                    newPassword);
            if (oldPsw.getCdPsw().equals(saltedPassword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @deprecated
     *
     * @param idSistemaVersante
     *            id sistema versante
     *
     * @return true se il sistema versante è abilitato, false altrimenti
     */
    @Deprecated
    public boolean isSistemaVersanteAbilitato(long idSistemaVersante) {
        Query query = getEntityManager().createQuery(
                "SELECT organizUsoSisVers FROM AplVLisOrganizUsoSisVers organizUsoSisVers, OrgEnteConvenzOrg enteConvenzOrg "
                        + "WHERE organizUsoSisVers.nmApplic = 'SACER' "
                        + "AND organizUsoSisVers.idSistemaVersante = :idSistemaVersante "
                        + "AND organizUsoSisVers.idOrganizIam = enteConvenzOrg.usrOrganizIam.idOrganizIam ");
        query.setParameter("idSistemaVersante", idSistemaVersante);
        return !query.getResultList().isEmpty();
    }

    /**
     * Returns a pseudo-random number between MIN and MAX, inclusive. The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @return Integer between min and max, inclusive.
     *
     * @see java.util.Random#nextInt(int)
     */
    private static int randInt() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((MAX - MIN) + 1) + MIN;

    }

    /**
     * @deprecated
     *
     * @param idUserIamCor
     *            id user
     * @param idUserIamGestito
     *            id user gestito
     *
     * @return lista di {@link UsrVAbilOrgVigilToAdd}
     */
    @Deprecated
    public List<UsrVAbilOrgVigilToAdd> getUsrVAbilOrgVigilToAddList(long idUserIamCor, long idUserIamGestito) {
        String queryStr = "SELECT abilOrgVigilToAdd FROM UsrVAbilOrgVigilToAdd abilOrgVigilToAdd "
                + "WHERE abilOrgVigilToAdd.idUserIamCor = :idUserIamCor "
                + "AND abilOrgVigilToAdd.idUserIamGestito = :idUserIamGestito ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCor", idUserIamCor);
        q.setParameter("idUserIamGestito", idUserIamGestito);
        return q.getResultList();
    }

    /**
     * Aggiunge le abilitazioni alle organizzazioni (USR_ABIL_ORGANIZ) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilOrganizVigil(long idUserIamCor, long idUserIamGestito) {
        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ORGANIZ (id_abil_organiz, id_uso_user_applic, id_organiz_iam, fl_abil_automatica, id_vigil_ente_produt, ds_causale_abil) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ORGANIZ.nextval)), id_uso_user_applic_gestito, id_organiz_iam_strut, '1', id_vigil_ente_produt, ds_causale_dich FROM USR_V_ABIL_ORG_VIGIL_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");
        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);
        q.executeUpdate();
    }

    public List<UsrVAbilOrgVigilToAdd> getAbilOrganizToVigil(long idUserIamCor, long idUserIamGestito) {
        Query q = getEntityManager().createQuery(
                "SELECT u FROM UsrVAbilOrgVigilToAdd u WHERE u.id.idUserIamCor = :idUserIamCor AND u.id.idUserIamGestito = :idUserIamGestito ");
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idUserIamGestito", bigDecimalFrom(idUserIamGestito));
        return q.getResultList();
    }

    /**
     * Aggiunge le abilitazioni agli enti convenzionati (USR_ABIL_ENTE_SIAM) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     * @param idEnteFornitEst
     *            id ente
     * @param idEnteProdut
     *            id produttore
     */
    public void aggiungiAbilEntiSuptFornit(long idUserIamCor, long idUserIamGestito, long idEnteFornitEst,
            long idEnteProdut) {
        // Ricavo dalla vista gli elementi in base ai dati in input
        Query q = getEntityManager().createQuery("SELECT u FROM UsrVAbilEnteSupportoToAdd u "
                + "WHERE u.id.idUserIamCor = :idUserIamCor " + "AND u.id.idUserIamGestito = :idUserIamGestito "
                + "AND u.idEnteFornitEst = :idEnteFornitEst " + "AND u.id.idEnteConvenz = :idEnteProdut ");
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idUserIamGestito", bigDecimalFrom(idUserIamGestito));
        q.setParameter("idEnteFornitEst", bigDecimalFrom(idEnteFornitEst));
        q.setParameter("idEnteProdut", bigDecimalFrom(idEnteProdut));
        List<UsrVAbilEnteSupportoToAdd> lista = q.getResultList();

        // Scorro la lista e se il record non è già presente nella tabella delle abilitazioni, lo inserisco
        lista.forEach(u -> {
            boolean esiste = existsAbilEnteSiam(idUserIamGestito, idEnteProdut);
            if (!esiste) {
                UsrAbilEnteSiam abilEnteSiam = new UsrAbilEnteSiam();
                abilEnteSiam.setDsCausaleAbil(u.getDsCausaleAbil());
                abilEnteSiam.setFlAbilAutomatica("1");
                abilEnteSiam.setOrgEnteSiam(this.findById(OrgEnteSiam.class, idEnteProdut));
                abilEnteSiam.setUsrUser(this.findById(UsrUser.class, idUserIamGestito));
                abilEnteSiam.setOrgSuptEsternoEnteConvenz(
                        this.findById(OrgSuptEsternoEnteConvenz.class, u.getIdSuptEstEnteConvenz()));
                getEntityManager().persist(abilEnteSiam);
            }
        });

    }

    private boolean existsAbilEnteSiam(long idUserIamGestito, long idEnteProdut) {
        Query q = getEntityManager().createQuery("SELECT u FROM UsrAbilEnteSiam u "
                + "WHERE u.usrUser.idUserIam = :idUserIamGestito " + "AND u.orgEnteSiam.idEnteSiam = :idEnteProdut");
        q.setParameter("idUserIamGestito", idUserIamGestito);
        q.setParameter("idEnteProdut", idEnteProdut);
        return !q.getResultList().isEmpty();
    }

    /**
     * Aggiunge le abilitazioni alle organizzazioni (USR_ABIL_ORGANIZ) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilOrganizFornit(long idUserIamCor, long idUserIamGestito) {
        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ORGANIZ (id_abil_organiz, id_uso_user_applic, id_organiz_iam, fl_abil_automatica, id_supt_est_ente_convenz, ds_causale_abil) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ORGANIZ.nextval)), id_uso_user_applic_gestito, id_organiz_iam_strut, '1', id_supt_est_ente_convenz, ds_causale_abil FROM USR_V_ABIL_SUPPORTO_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);

        q.executeUpdate();

    }

    /**
     * Aggiunge le abilitazioni ai tipi dato (USR_ABIL_DATI) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilDatiVigil(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_DATI (id_abil_dati, id_uso_user_applic, id_tipo_dato_iam, fl_abil_automatica, id_vigil_ente_produt, ds_causale_abil) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_DATI.nextval)), id_uso_user_applic_gestito, id_tipo_dato_iam, '1', id_vigil_ente_produt, ds_causale_dich FROM USR_V_ABIL_DATI_VIGIL_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);

        q.executeUpdate();

    }

    public List<UsrVAbilDatiVigilToAdd> getAbilDatiVigil(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createQuery(
                "SELECT u FROM UsrVAbilDatiVigilToAdd u WHERE u.id.idUserIamCor = :idUserIamCor AND u.id.idUserIamGestito = :idUserIamGestito ");

        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idUserIamGestito", bigDecimalFrom(idUserIamGestito));

        return q.getResultList();
    }

    /**
     * Aggiunge le abilitazioni ai tipi dato (USR_ABIL_DATI) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilDatiFornit(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_DATI (id_abil_dati, id_uso_user_applic, id_tipo_dato_iam, fl_abil_automatica, id_supt_est_ente_convenz, ds_causale_abil) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_DATI.nextval)), id_uso_user_applic_gestito, id_tipo_dato_iam, '1', id_ente_fornit_est, ds_causale_dich FROM USR_V_ABIL_DATI_SUPPORTO_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);

        q.executeUpdate();

    }

    /**
     * Aggiunge le abilitazioni agli enti convenzionati (USR_ABIL_ENTE_SIAM) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilEnteColleg(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, ds_causale_abil, id_appart_colleg_enti, fl_abil_automatica) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ENTE_SIAM.nextval)), id_user_iam_gestito, id_ente_convenz, ds_causale_abil, id_appart_colleg_enti, '1' FROM USR_V_ABIL_ENTE_COLLEG_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);
        q.executeUpdate();

    }

    /**
     * Aggiunge le abilitazioni agli enti convenzionati (USR_ABIL_ENTE_SIAM)
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idCollegEntiConvenz
     *            id del collegamento dal quale ricavare le abilitazioni da assegnare agli utenti
     * @param idEnteConvenzExcluded
     *            id dell'ente inserito nel collegamento che non deve essere considerato
     */
    public void aggiungiAbilEnteColleg(long idUserIamCor, long idCollegEntiConvenz, long idEnteConvenzExcluded) {
        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, ds_causale_abil, id_appart_colleg_enti, fl_abil_automatica) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ENTE_SIAM.nextval)) id_abil_ente_siam, tab1.* from "
                        + "(SELECT DISTINCT id_user_iam_gestito, id_ente_convenz, ds_causale_abil, id_appart_colleg_enti, '1' "
                        + "FROM USR_V_ABIL_ENTE_COLLEG_TO_ADD u WHERE u.ID_USER_IAM_COR = ? "
                        + "AND u.ID_USER_IAM_GESTITO IN "
                        + "(SELECT DISTINCT utente.id_user_iam FROM Org_Appart_Colleg_Enti appart, Usr_User utente "
                        + "WHERE appart.id_ente_convenz = utente.id_ente_siam "
                        + "AND appart.id_Colleg_Enti_Convenz = ? " + "AND utente.fl_Abil_Enti_Colleg_Autom = '1' "
                        + "AND appart.id_Ente_Convenz != ? )) tab1 ");
        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idCollegEntiConvenz);
        q.setParameter(3, idEnteConvenzExcluded);
        q.executeUpdate();
    }

    public void aggiungiAbilEnteColleg2(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, ds_causale_abil, id_appart_colleg_enti, fl_abil_automatica) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ENTE_SIAM.nextval)), id_user_iam_gestito, id_ente_convenz, ds_causale_abil, id_appart_colleg_enti, '1' FROM USR_V_ABIL_ENTE_COLLEG_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);
        q.executeUpdate();

    }

    /**
     * Aggiunge le abilitazioni agli enti convenzionati (USR_ABIL_ENTE_SIAM) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilEnteFornit(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, ds_causale_abil, id_supt_est_ente_convenz, fl_abil_automatica) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ENTE_SIAM.nextval)), id_user_iam_gestito, id_ente_fornit_est, ds_causale_abil, id_supt_est_ente_convenz, '1' FROM USR_V_ABIL_ENTE_FORNIT_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);
        q.executeUpdate();

    }

    /**
     * Aggiunge le abilitazioni agli enti convenzionati (USR_ABIL_ENTE_SIAM) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilEnteVigil(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, ds_causale_abil, id_accordo_vigil, fl_abil_automatica) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ENTE_SIAM.nextval)), id_user_iam_gestito, id_ente_organo_vigil, ds_causale_abil, id_accordo_vigil, '1' FROM USR_V_ABIL_ENTE_VIGIL_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);
        q.executeUpdate();

    }

    /**
     * Aggiunge le abilitazioni agli enti convenzionati (USR_ABIL_ENTE_SIAM) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id versamento
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilEnteNoconv(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, ds_causale_abil, fl_abil_automatica) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ENTE_SIAM.nextval)), id_user_iam_gestito, id_ente_non_convenz, ds_causale_abil, '1' FROM USR_V_ABIL_ENTE_NOCONV_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);
        q.executeUpdate();

    }

    /**
     * Aggiunge le abilitazioni agli enti convenzionati (USR_ABIL_ENTE_SIAM) ricavandole dalla relativa vista
     *
     * @param idUserIamCor
     *            id versamento
     * @param idUserIamGestito
     *            id user IAM gestito
     */
    public void aggiungiAbilEnteCorrisp(long idUserIamCor, long idUserIamGestito) {

        Query q = getEntityManager().createNativeQuery(
                "INSERT INTO USR_ABIL_ENTE_SIAM (id_abil_ente_siam, id_user_iam, id_ente_siam, ds_causale_abil, fl_abil_automatica) "
                        + "SELECT to_number('" + randInt()
                        + "' || to_char(SUSR_ABIL_ENTE_SIAM.nextval)), id_user_iam_gestito, id_ente_siam, ds_causale_abil, '1' FROM USR_V_ABIL_ENTE_CORRISP_TO_ADD u WHERE u.ID_USER_IAM_COR = ? AND u.ID_USER_IAM_GESTITO = ? ");

        q.setParameter(1, idUserIamCor);
        q.setParameter(2, idUserIamGestito);
        q.executeUpdate();

    }

    /**
     * Elimina abilitazioni alle organizzazioni per l'utente specificato
     *
     * @param idUserIam
     *            id user IAM
     */
    public void eliminaAbilOrganizVigil(long idUserIam) {
        String queryStr = "DELETE FROM UsrAbilOrganiz abilOrganiz WHERE abilOrganiz.idAbilOrganiz IN "
                + "(SELECT orgVigilToDel.idAbilOrganiz FROM UsrVAbilOrgVigilToDel orgVigilToDel WHERE orgVigilToDel.idUserIam = :idUserIam) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Elimina abilitazioni all'organizzazione vigilata per l'utente specificato
     *
     * @param idUserIam
     *            id user IAM dell'utente interessato alla rimozione dell'abilitazione
     * @param idVigilEnteProdut
     *            id della vigilanza (ORG_VIGIL_ENTE_PRODUT)
     */
    public void eliminaAbilOrganizVigil(long idUserIam, long idVigilEnteProdut) {
        String queryStr = "DELETE FROM UsrAbilOrganiz abilOrganiz WHERE abilOrganiz.idAbilOrganiz IN "
                + "(SELECT orgVigilToDel.idAbilOrganiz FROM UsrVAbilOrgVigilToDel orgVigilToDel WHERE orgVigilToDel.idUserIam = :idUserIam AND orgVigilToDel.idVigilEnteProdut = :idVigilEnteProdut) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.setParameter("idVigilEnteProdut", bigDecimalFrom(idVigilEnteProdut));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Elimina l’abilitazione all'utente dell’ente siam che non è più supportato dal fornitore
     *
     * @param idUserIam
     *            id user IAM
     * @param idEnteSiam
     *            id ente
     */
    public void eliminaAbilEnteSupest(long idUserIam, long idEnteSiam) {
        String queryStr = "DELETE FROM UsrAbilEnteSiam abilEnteSiam WHERE abilEnteSiam.idAbilEnteSiam IN "
                + "(SELECT abilEnteSupportoToDel.idAbilEnteSiam FROM UsrVAbilEnteSupportoToDel abilEnteSupportoToDel WHERE abilEnteSupportoToDel.idUserIam = :idUserIam AND abilEnteSupportoToDel.idEnteSiam = :idEnteSiam) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", BigDecimal.valueOf(idUserIam));
        q.setParameter("idEnteSiam", BigDecimal.valueOf(idEnteSiam));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Elimina abilitazioni alle organizzazioni per l'utente specificato
     *
     * @param idUserIam
     *            id user IAM
     */
    public void eliminaAbilOrganizSupest(long idUserIam) {
        String queryStr = "DELETE FROM UsrAbilOrganiz abilOrganiz WHERE abilOrganiz.idAbilOrganiz IN "
                + "(SELECT orgSupestToDel.idAbilOrganiz FROM UsrVAbilOrgSupestToDel orgSupestToDel WHERE orgSupestToDel.idUserIam = :idUserIam) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Elimina abilitazioni ai tipi dato per l'utente specificato
     *
     * @param idUserIam
     *            id user IAM
     */
    public void eliminaAbilTipiDatoVigil(long idUserIam) {
        String queryStr = "DELETE FROM UsrAbilDati abilDati WHERE abilDati.idAbilDati IN "
                + "(SELECT datiVigilToDel.idAbilDati FROM UsrVAbilDatiVigilToDel datiVigilToDel WHERE datiVigilToDel.idUserIam = :idUserIam) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Elimina abilitazioni ai tipi dato vigilati per l'utente specificato
     *
     * @param idUserIam
     *            id user IAM
     * @param idVigilEnteProdut
     *            id della vigilanza (ORG_VIGIL_ENTE_PRODUT)
     */
    public void eliminaAbilTipiDatoVigil(long idUserIam, long idVigilEnteProdut) {
        String queryStr = "DELETE FROM UsrAbilDati abilDati WHERE abilDati.idAbilDati IN "
                + "(SELECT datiVigilToDel.idAbilDati FROM UsrVAbilDatiVigilToDel datiVigilToDel WHERE datiVigilToDel.idUserIam = :idUserIam AND datiVigilToDel.idVigilEnteProdut = :idVigilEnteProdut) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.setParameter("idVigilEnteProdut", bigDecimalFrom(idVigilEnteProdut));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Elimina abilitazioni ai tipi dato per l'utente specificato
     *
     * @param idUserIam
     *            id user IAM
     */
    public void eliminaAbilTipiDatoSupest(long idUserIam) {
        String queryStr = "DELETE FROM UsrAbilDati abilDati WHERE abilDati.idAbilDati IN "
                + "(SELECT datiSupestToDel.idAbilDati FROM UsrVAbilDatiSupestToDel datiSupestToDel WHERE datiSupestToDel.idUserIam = :idUserIam) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Elimina abilitazioni agli enti per l'utente specificato
     *
     * @param idUserIam
     *            id user IAM
     */
    public void eliminaAbilEntiConv(long idUserIam) {
        String queryStr = "DELETE FROM UsrAbilEnteSiam abilEnteSiam WHERE abilEnteSiam.idAbilEnteSiam IN "
                + "(SELECT enteCollegToDel.idAbilEnteSiam FROM UsrVAbilEnteCollegToDel enteCollegToDel WHERE enteCollegToDel.idUserIam = :idUserIam) ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Controlla l'abilitazione agli enti per l'utente e l'ente specificati
     *
     * @param idUserIam
     *            id user IAM
     * @param idEnteSiam
     *            id ente
     *
     * @return true se abilitato
     */
    public boolean isAbilEntiConv(long idUserIam, long idEnteSiam) {
        String queryStr = "SELECT abilEnteSiam FROM UsrAbilEnteSiam abilEnteSiam "
                + "WHERE abilEnteSiam.usrUser.idUserIam = :idUserIam "
                + "AND abilEnteSiam.orgEnteSiam.idEnteSiam = :idEnteSiam";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idEnteSiam", idEnteSiam);

        return !query.getResultList().isEmpty();
    }

    /**
     * Controlla l'abilitazione alle organizzazioni per l'utente e l'ente specificati
     *
     * @param idUsoUserApplic
     *            id user su applicazione
     * @param idOrganizIam
     *            id organizzazione
     *
     * @return true se abilitato
     */
    public boolean isAbilEntiConvOrg(long idUsoUserApplic, long idOrganizIam) {
        String queryStr = "SELECT abilOrganiz FROM UsrAbilOrganiz abilOrganiz "
                + "JOIN abilOrganiz.usrUsoUserApplic usoUserApplic "
                + "WHERE abilOrganiz.usrOrganizIam.idOrganizIam = :idOrganizIam "
                + "AND usoUserApplic.idUsoUserApplic = :idUsoUserApplic ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUsoUserApplic", idUsoUserApplic);
        query.setParameter("idOrganizIam", idOrganizIam);

        return !query.getResultList().isEmpty();
    }

    /**
     * Controlla l'abilitazione ai dati per l'utente e l'ente specificati
     *
     * @param idUsoUserApplic
     *            id user IAM
     * @param idTipoDatoIam
     *            id ente
     *
     * @return true se abilitato
     */
    public boolean isAbilEntiConvDati(long idUsoUserApplic, long idTipoDatoIam) {
        String queryStr = "SELECT abilDati FROM UsrAbilDati abilDati " + "JOIN abilDati.usrUsoUserApplic usoUserApplic "
                + "WHERE usoUserApplic.idUsoUserApplic = :idUsoUserApplic "
                + "AND abilDati.usrTipoDatoIam.idTipoDatoIam = :idTipoDatoIam";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUsoUserApplic", idUsoUserApplic);
        query.setParameter("idTipoDatoIam", idTipoDatoIam);

        return !query.getResultList().isEmpty();
    }

    /**
     * Elimina l'abilitazione agli enti per l'utente e l'ente specificati
     *
     * @param idUserIam
     *            id user IAM
     * @param idEnteSiam
     *            id ente
     */
    public void eliminaAbilEntiConv(long idUserIam, long idEnteSiam) {
        String queryStr = "DELETE FROM UsrAbilEnteSiam abilEnteSiam "
                + "WHERE abilEnteSiam.usrUser.idUserIam = :idUserIam "
                + "AND abilEnteSiam.orgEnteSiam.idEnteSiam = :idEnteSiam";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idEnteSiam", idEnteSiam);
        query.executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Recupera la dichiarazione di tipo UN_ENTE per l'utente, l'ambiente e l'ente specificati
     *
     * @deprecated
     *
     * @param idUserIam
     *            id user IAM
     * @param idAmbienteEnteConvenz
     *            id ambiente convenzionato
     * @param idEnteSiam
     *            id ente
     *
     * @return entity {@link UsrDichAbilEnteConvenz}
     */
    @Deprecated
    public UsrDichAbilEnteConvenz retrieveDichAbilEnteConv(long idUserIam, long idAmbienteEnteConvenz,
            long idEnteSiam) {
        String queryStr = "SELECT abilEnteSiam FROM UsrDichAbilEnteConvenz dichAbilEnteConvenz "
                + "WHERE dichAbilEnteConvenz.usrUser.idUserIam = :idUserIam "
                + "AND dichAbilEnteConvenz.orgAmbienteEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz "
                + "AND dichAbilEnteConvenz.orgEnteSiam.idEnteSiam = :idEnteSiam "
                + "AND dichAbilEnteConvenz.tiScopoDichAbilEnte = 'UN_ENTE'";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        query.setParameter("idEnteSiam", idEnteSiam);

        UsrDichAbilEnteConvenz dichAbilEnteConvenz = null;
        if (!query.getResultList().isEmpty()) {
            dichAbilEnteConvenz = (UsrDichAbilEnteConvenz) query.getResultList().get(0);
        }
        return dichAbilEnteConvenz;
    }

    public List<UsrAbilOrganiz> getUsrAbilOrganizList(long idUsoUserApplic) {
        Query query = getEntityManager().createQuery("SELECT abilOrganiz FROM UsrAbilOrganiz abilOrganiz "
                + "WHERE abilOrganiz.usrUsoUserApplic.idUsoUserApplic = :idUsoUserApplic ");
        query.setParameter("idUsoUserApplic", idUsoUserApplic);
        return query.getResultList();
    }

    public List<UsrAbilDati> getUsrAbilDatiList(long idUsoUserApplic) {
        Query query = getEntityManager().createQuery("SELECT abilDati FROM UsrAbilDati abilDati "
                + "WHERE abilDati.usrUsoUserApplic.idUsoUserApplic = :idUsoUserApplic ");
        query.setParameter("idUsoUserApplic", idUsoUserApplic);
        return query.getResultList();
    }

    /**
     * @deprecated
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente ente convenzionato
     *
     * @return lista degli id
     */
    @Deprecated
    public List<BigDecimal> getUsrVLisAbilEnteAmbienteList(BigDecimal idAmbienteEnteConvenz) {
        Query query = getEntityManager()
                .createQuery("SELECT DISTINCT abilEnte.id.idUserIamGestito FROM UsrVLisAbilEnte abilEnte "
                        + "WHERE abilEnte.id.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        return query.getResultList();
    }

    public List<Object[]> getUtentiDichAbilUnAmbiente(BigDecimal idAmbienteEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT dich.usrUser.idUserIam, dich.idDichAbilEnteConvenz "
                + "FROM UsrDichAbilEnteConvenz dich " + "WHERE dich.tiScopoDichAbilEnte = 'UN_AMBIENTE' "
                + "AND dich.orgAmbienteEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        query.setParameter("idAmbienteEnteConvenz", longFrom(idAmbienteEnteConvenz));
        return query.getResultList();
    }

    public void deleteAbilitazioniUtenteUnAmbiente(long idEnteSiam, long idUserIam, long idDichAbilEnteConvenz) {

        String queryStr = "DELETE FROM UsrAbilEnteSiam abilEnteSiam "
                + "WHERE abilEnteSiam.orgEnteSiam.idEnteSiam = :idEnteSiam "
                + "AND abilEnteSiam.usrUser.idUserIam = :idUserIam "
                + "AND abilEnteSiam.usrDichAbilEnteConvenz.idDichAbilEnteConvenz = :idDichAbilEnteConvenz ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idEnteSiam", idEnteSiam);
        q.setParameter("idUserIam", idUserIam);
        q.setParameter("idDichAbilEnteConvenz", idDichAbilEnteConvenz);
        q.executeUpdate();
        getEntityManager().flush();
    }

    public Long getIdApplicByUsrUsoUserApplic(BigDecimal idUsoUsrApplic) {
        Query q = getEntityManager().createQuery(
                "SELECT a.idApplic FROM UsrUsoUserApplic u  JOIN u.aplApplic a WHERE u.idUsoUserApplic = :idUsoUsrApplic");
        q.setParameter("idUsoUsrApplic", longFrom(idUsoUsrApplic));
        return (Long) q.getSingleResult();
    }

    public List<String> getUsrVAbilEnteCollegToDel(BigDecimal idEnteSiam) {
        Query q = getEntityManager()
                .createQuery("SELECT user.nmUserid " + "FROM UsrVAbilEnteCollegToDel abilEnteCollegToDel, UsrUser user "
                        + "WHERE abilEnteCollegToDel.idEnteSiam = :idEnteSiam "
                        + "AND abilEnteCollegToDel.idEnteSiam = user.idEnteSiam ");
        q.setParameter("idEnteSiam", idEnteSiam);
        return (List<String>) q.getSingleResult();
    }

    public List<String> getUsrVAbilEnteCollegToDel2(BigDecimal idAppartCollegEnti) {
        Query q = getEntityManager().createQuery(
                "SELECT DISTINCT user.nmUserid " + "FROM UsrVAbilEnteCollegToDel abilEnteCollegToDel, UsrUser user "
                        + "WHERE abilEnteCollegToDel.idAppartCollegEnti = :idAppartCollegEnti "
                        + "AND abilEnteCollegToDel.idUserIam = user.idUserIam ");
        q.setParameter("idAppartCollegEnti", idAppartCollegEnti);
        return (List<String>) q.getResultList();
    }
}
