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

package it.eng.saceriam.ws.ejb;

import static it.eng.paginator.util.HibernateUtils.bigDecimalFrom;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import it.eng.paginator.util.HibernateUtils;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.constraint.ConstAplApplic;
import it.eng.saceriam.viewEntity.UsrVAllAutor;
import it.eng.saceriam.viewEntity.constraint.ConstAplVVisHelpOnLine;
import it.eng.saceriam.web.util.Constants;
import it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta;
import it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.HelpDips;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "RecuperoAutorizzazioniEjb")
@LocalBean
public class RecuperoAutorizzazioniEjb {

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    public RecuperoAutorizzazioniRisposta recuperoAutorizzazioniPerId(Integer idUserIam, String nmApplic,
            Integer idOrganiz) {
        return recuperoAutorizzazioni(idUserIam, null, nmApplic, idOrganiz, null);
    }

    public RecuperoAutorizzazioniRisposta recuperoAutorizzazioniPerNome(String nmUserIAM, String nmApplic,
            Integer idOrganiz) {
        return recuperoAutorizzazioni(null, nmUserIAM, nmApplic, idOrganiz, null);
    }

    private RecuperoAutorizzazioniRisposta recuperoAutorizzazioni(Integer idUserIam, String nmUserIAM, String nmApplic,
            Integer idOrganiz, String nmOrganiz) {
        if (StringUtils.isEmpty(nmUserIAM) && idUserIam == null) {
            throw new IllegalArgumentException("Il parametro idUserIam o nmUserIAM deve essere definito");
        }
        if (StringUtils.isEmpty(nmApplic)) {
            throw new IllegalArgumentException("Il parametro nmApplic non è definito");
        }
        List<UsrVAllAutor> pagineAzioniList = this.retrievePagineAzioni(idUserIam, nmUserIAM, nmApplic, idOrganiz,
                nmOrganiz);
        List<AplEntryMenu> entryMenuList = this.retrieveMenu(idUserIam, nmUserIAM, nmApplic, idOrganiz, nmOrganiz);

        RecuperoAutorizzazioniRisposta resp = fillAutorizzazioni(pagineAzioniList, entryMenuList, nmApplic);
        if (resp.getCdEsito().equals(Constants.EsitoServizio.KO)) {
            if (!userExist(idUserIam, nmUserIAM)) {
                resp.setCdErr(MessaggiWSBundle.RECUP_001);
                resp.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.RECUP_001));
            }
            if (!appExist(nmApplic)) {
                resp.setCdErr(MessaggiWSBundle.RECUP_002);
                resp.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.RECUP_002));
            }
            if (!organizExist(idOrganiz, nmOrganiz)) {
                resp.setCdErr(MessaggiWSBundle.RECUP_003);
                resp.setDsErr(MessaggiWSBundle.getString(MessaggiWSBundle.RECUP_003));
            }
        }

        return resp;
    }

    private RecuperoAutorizzazioniRisposta fillAutorizzazioni(List<UsrVAllAutor> userAuths,
            List<AplEntryMenu> entryMenuList, String nmApplic) {
        RecuperoAutorizzazioniRisposta resp = new RecuperoAutorizzazioniRisposta();
        if (userAuths != null && !userAuths.isEmpty() && entryMenuList != null && !entryMenuList.isEmpty()) {
            resp.setCdEsito(Constants.EsitoServizio.OK);
            for (UsrVAllAutor auth : userAuths) {
                if (auth.getUsrVAllAutorId().getTiDichAutor().equals("PAGINA")) {
                    RecuperoAutorizzazioniRisposta.Pagina pagina = new RecuperoAutorizzazioniRisposta.Pagina();
                    pagina.setNmPaginaWeb(auth.getNmAutor());
                    if (auth.getFlHelpPresente() != null && auth.getFlHelpPresente().trim().equals("1")) {
                        pagina.setFlHelpPresente(true);
                    } else {
                        pagina.setFlHelpPresente(false);
                    }
                    if (nmApplic.equals(ConstAplApplic.NmApplic.SACER_RIC.name())) {
                        // Cerca tutti i menu associati alla pagina nella tabella degli Help
                        List<HelpDips> listaHelp = retrieveMenuPerPage(nmApplic, pagina.getNmPaginaWeb());
                        pagina.setHelpDipsList(listaHelp);
                    }
                    resp.getPagineList().add(pagina);
                    continue;
                }
                if (auth.getUsrVAllAutorId().getTiDichAutor().equals("AZIONE")) {
                    RecuperoAutorizzazioniRisposta.Azione azione = new RecuperoAutorizzazioniRisposta.Azione();
                    azione.setNmAzionePagina(auth.getNmAutor());
                    azione.setNmPaginaWeb(auth.getNmPaginaWeb());
                    resp.getAzioniList().add(azione);
                    continue;
                }
            }
            for (AplEntryMenu entryMenu : entryMenuList) {
                RecuperoAutorizzazioniRisposta.Menu menu = new RecuperoAutorizzazioniRisposta.Menu();
                menu.setDsEntryMenu(entryMenu.getDsEntryMenu());
                menu.setDsLinkEntryMenu(entryMenu.getDlLinkEntryMenu());
                menu.setNiLivelloEntryMenu(entryMenu.getNiLivelloEntryMenu().intValue());
                menu.setNiOrdEntryMenu(entryMenu.getNiOrdEntryMenu().intValue());
                menu.setNmEntryMenu(entryMenu.getNmEntryMenu());
                // menu.setFlHelpPresente( (entryMenu.getFlagHelp()==1) ? true : false );
                resp.getMenuList().add(menu);
            }
        } else {
            resp.setCdEsito(Constants.EsitoServizio.KO);
        }
        return resp;
    }

    private boolean userExist(Integer idUserIam, String nmUserIAM) {
        String queryStr = "SELECT 1 FROM UsrUser u WHERE u.idUserIam = :idUserIam OR u.nmUserid = :nmUserId";
        Query query = em.createQuery(queryStr.toString());
        query.setParameter("idUserIam", HibernateUtils.longFrom(idUserIam));
        query.setParameter("nmUserId", nmUserIAM);
        List list = query.getResultList();
        return !list.isEmpty();

    }

    private boolean appExist(String nmApplic) {
        String queryStr = "SELECT 1 FROM AplApplic a WHERE a.nmApplic = :nmApplic";
        Query query = em.createQuery(queryStr.toString());
        query.setParameter("nmApplic", nmApplic);
        List list = query.getResultList();
        return !list.isEmpty();
    }

    private boolean organizExist(Integer idOrganiz, String nmOrganiz) {
        String queryStr = "SELECT 1 FROM UsrOrganizIam uoi WHERE uoi.idOrganizApplic = :idOrganiz OR uoi.nmOrganiz = :nmOrganiz";
        Query query = em.createQuery(queryStr.toString());
        query.setParameter("idOrganiz", bigDecimalFrom(idOrganiz));
        query.setParameter("nmOrganiz", nmOrganiz);
        List list = query.getResultList();
        return !list.isEmpty();
    }

    private List<UsrVAllAutor> retrievePagineAzioni(Integer idUserIam, String nmUserIAM, String nmApplic,
            Integer idOrganiz, String nmOrganiz) {

        // String tipoOrganiz = getTipoOrganizApplic(nmApplic);
        // MEV #10608 - Ruoli specifici su organizzazioni in sostituzione di quelli standard
        String tipoOrganiz = getTipoOrganizzazione(nmApplic);
        StringBuilder queryStr = new StringBuilder();
        queryStr.append("SELECT DISTINCT u FROM UsrVAllAutor u WHERE ")
                .append(idUserIam != null ? " u.id.idUserIam = :idUserIam " : "")
                .append(!StringUtils.isEmpty(nmUserIAM) ? " u.nmUserid = :nmUserid  " : "")
                .append(" AND u.nmApplic = :nmApplic ").append(" AND u.id.tiDichAutor != 'ENTRY_MENU' ");
        // Se sono vuoti il nome dell'organizzazione e anche l'ID organiz
        if (StringUtils.isEmpty(nmOrganiz) && idOrganiz == null) {
            queryStr.append(" AND u.id.tiUsoRuo = 'DEF' ").append(" AND u.nmTipoOrganizApplic IS NULL ")
                    .append(" AND u.idOrganizApplic IS NULL ");
        } else {
            queryStr.append(" AND u.id.tiUsoRuo = 'DICH' ").append(" AND u.nmTipoOrganizApplic = :tipoOrganiz ")
                    .append(!StringUtils.isEmpty(nmOrganiz) ? " AND u.nmOrganizApplic = :nmOrganiz " : "")
                    .append(idOrganiz != null ? " AND u.idOrganizApplic = :idOrganizApplic " : "");
        }
        Query query = em.createQuery(queryStr.toString());
        if (!StringUtils.isEmpty(nmUserIAM)) {
            query.setParameter("nmUserid", nmUserIAM);
        }
        if (idUserIam != null) {
            query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        }
        query.setParameter("nmApplic", nmApplic);

        if (!(StringUtils.isEmpty(nmOrganiz) && idOrganiz == null)) {
            query.setParameter("tipoOrganiz", tipoOrganiz);
            if (!StringUtils.isEmpty(nmOrganiz)) {
                query.setParameter("nmOrganiz", nmOrganiz);
            }
            if (idOrganiz != null) {
                query.setParameter("idOrganizApplic", bigDecimalFrom(idOrganiz));
            }
        }
        List<UsrVAllAutor> l = query.getResultList();
        // MEV #10608 - Ruoli specifici su organizzazioni in sostituzione di quelli standard
        if (l.size() == 0) {
            queryStr = new StringBuilder();
            queryStr.append("SELECT DISTINCT u FROM UsrVAllAutor u WHERE ")
                    .append(idUserIam != null ? " u.id.idUserIam = :idUserIam " : "")
                    .append(!StringUtils.isEmpty(nmUserIAM) ? " u.nmUserid = :nmUserid  " : "")
                    .append(" AND u.nmApplic = :nmApplic ").append(" AND u.id.tiDichAutor != 'ENTRY_MENU' ")
                    .append(" AND u.id.tiUsoRuo = 'DEF' ").append(" AND u.nmTipoOrganizApplic IS NULL ")
                    .append(" AND u.idOrganizApplic IS NULL ");
            query = em.createQuery(queryStr.toString());
            if (!StringUtils.isEmpty(nmUserIAM)) {
                query.setParameter("nmUserid", nmUserIAM);
            }
            if (idUserIam != null) {
                query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
            }
            query.setParameter("nmApplic", nmApplic);
            l = query.getResultList();
        }
        return l;
    }

    private List<AplEntryMenu> retrieveMenu(Integer idUserIam, String nmUserIAM, String nmApplic, Integer idOrganiz,
            String nmOrganiz) {
        // String tipoOrganiz = getTipoOrganizApplic(nmApplic);
        // MEV #10608 - Ruoli specifici su organizzazioni in sostituzione di quelli standard
        String tipoOrganiz = getTipoOrganizzazione(nmApplic);
        StringBuilder queryStr = new StringBuilder();
        queryStr.append("SELECT * FROM APL_ENTRY_MENU t0 WHERE EXISTS (")
                .append("SELECT 1 FROM USR_V_ALL_AUTOR u WHERE ").append(idUserIam != null ? " u.id_user_iam = ? " : "")
                .append(!StringUtils.isEmpty(nmUserIAM) ? " u.nm_userid = ?  " : "").append(" AND u.nm_applic = ? ")
                .append(" AND u.id_applic = t0.id_applic ").append(" AND u.ti_dich_autor = 'ENTRY_MENU' ")
                .append(" AND u.id_autor = t0.id_entry_menu ");
        // Se sono vuoti il nome dell'organizzazione e anche l'ID organiz
        if (StringUtils.isEmpty(nmOrganiz) && idOrganiz == null) {
            queryStr.append(" AND u.ti_uso_ruo = 'DEF' ").append(" AND u.nm_tipo_organiz_applic IS NULL ")
                    .append(" AND u.id_organiz_applic IS NULL ");
        } else {
            queryStr.append(" AND u.ti_uso_ruo = 'DICH' ").append(" AND u.nm_tipo_organiz_applic = ? ")
                    .append(!StringUtils.isEmpty(nmOrganiz) ? " AND u.nm_organiz_applic = ? " : "")
                    .append(idOrganiz != null ? " AND u.id_organiz_applic = ? " : "");
        }
        queryStr.append(" )").append(
                " CONNECT BY PRIOR t0.id_entry_menu = t0.id_entry_menu_padre START WITH t0.id_entry_menu_padre IS NULL ")
                .append(" ORDER SIBLINGS BY t0.ni_ord_entry_menu");
        Query query = em.createNativeQuery(queryStr.toString(), AplEntryMenu.class);
        if (!StringUtils.isEmpty(nmUserIAM)) {
            query.setParameter(1, nmUserIAM);
        }
        if (idUserIam != null) {
            query.setParameter(1, bigDecimalFrom(idUserIam));
        }
        query.setParameter(2, nmApplic);
        if (!(StringUtils.isEmpty(nmOrganiz) && idOrganiz == null)) {
            query.setParameter(3, tipoOrganiz);
            if (!StringUtils.isEmpty(nmOrganiz)) {
                query.setParameter(4, nmOrganiz);
            }
            if (idOrganiz != null) {
                query.setParameter(4, bigDecimalFrom(idOrganiz));
            }
        }
        List<AplEntryMenu> l = query.getResultList();
        // MEV #10608 - Ruoli specifici su organizzazioni in sostituzione di quelli standard
        if (l.size() == 0) {
            queryStr = new StringBuilder();
            queryStr.append("SELECT * FROM APL_ENTRY_MENU t0 WHERE EXISTS (")
                    .append("SELECT 1 FROM USR_V_ALL_AUTOR u WHERE ")
                    .append(idUserIam != null ? " u.id_user_iam = ? " : "")
                    .append(!StringUtils.isEmpty(nmUserIAM) ? " u.nm_userid = ?  " : "").append(" AND u.nm_applic = ? ")
                    .append(" AND u.id_applic = t0.id_applic ").append(" AND u.ti_dich_autor = 'ENTRY_MENU' ")
                    .append(" AND u.id_autor = t0.id_entry_menu ").append(" AND u.ti_uso_ruo = 'DEF' ")
                    .append(" AND u.nm_tipo_organiz_applic IS NULL ").append(" AND u.id_organiz_applic IS NULL ")
                    .append(" )")
                    .append(" CONNECT BY PRIOR t0.id_entry_menu = t0.id_entry_menu_padre START WITH t0.id_entry_menu_padre IS NULL ")
                    .append(" ORDER SIBLINGS BY t0.ni_ord_entry_menu");
            query = em.createNativeQuery(queryStr.toString(), AplEntryMenu.class);
            if (!StringUtils.isEmpty(nmUserIAM)) {
                query.setParameter(1, nmUserIAM);
            }
            if (idUserIam != null) {
                query.setParameter(1, bigDecimalFrom(idUserIam));
            }
            query.setParameter(2, nmApplic);
            l = query.getResultList();
        }
        return l;
    }

    /*
     * Estrae tutti i menu associati alla pagina dell'applicazione ad oggi che hanno un Help Associato in sacerDips.
     */
    private List<HelpDips> retrieveMenuPerPage(String nmApplic, String nmPaginaWeb) {
        ArrayList<HelpDips> al = new ArrayList<>();

        Query query = em.createNamedQuery("selMenuByIdApplicAndIdPaginaWebDips");
        query.setParameter("nmApplic", nmApplic);
        query.setParameter("tiHelpOnLine", ConstAplVVisHelpOnLine.TiHelpOnLine.HELP_RICERCA_DIPS.name());
        query.setParameter("nmPaginaWeb", nmPaginaWeb);
        query.setParameter("dtRiferimento", new Date());

        List<String> lista = query.getResultList();
        Iterator<String> it = lista.iterator();
        while (it.hasNext()) {
            String next = it.next();
            al.add(new HelpDips(next));
        }

        return al;
    }

    private String getTipoOrganizzazione(String nmApplic) {
        String tipoOrganiz = "";
        if (nmApplic.equals("SACER")) {
            tipoOrganiz = "STRUTTURA";
        } else if (nmApplic.equals("SACER_PREINGEST")) {
            tipoOrganiz = "VERSATORE";
        } else if (nmApplic.startsWith("DPI_")) {
            tipoOrganiz = "DPI";
        }
        return tipoOrganiz;
    }

}
