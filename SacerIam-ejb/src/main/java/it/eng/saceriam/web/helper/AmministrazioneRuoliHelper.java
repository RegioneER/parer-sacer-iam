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
import static it.eng.paginator.util.HibernateUtils.bigDecimalListFrom;
import static it.eng.paginator.util.HibernateUtils.isCollectionOf;
import static it.eng.paginator.util.HibernateUtils.longFrom;
import static it.eng.paginator.util.HibernateUtils.longListFrom;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.PrfDichAutor;
import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.PrfRuoloCategoria;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoRuoloUserDefault;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicRowBean;
import it.eng.saceriam.viewEntity.AplVTreeEntryMenu;
import it.eng.saceriam.viewEntity.AplVTreeMenuPagAzio;
import it.eng.saceriam.viewEntity.PrfVChkAllineaRuolo;
import it.eng.saceriam.viewEntity.PrfVLisDichAllineaToadd;
import it.eng.saceriam.viewEntity.PrfVLisDichAutor;
import it.eng.saceriam.viewEntity.PrfVLisDichAutorDaAllin;
import it.eng.saceriam.viewEntity.PrfVLisRuolo;
import it.eng.saceriam.viewEntity.PrfVLisUserDaReplic;
import it.eng.spagoLite.form.base.BaseElements.Status;

/**
 * Session Bean implementation class AmministrazioneHelper Contiene i metodi, per la gestione della persistenza su DB
 * per le operazioni CRUD
 *
 */
@Stateless
@LocalBean
public class AmministrazioneRuoliHelper extends GenericHelper {

    /**
     * Ritorno i ruoli di un utente in base all'applicazione La vista gestisce il "raggruppamento" dei ruoli che
     * gestiscono più applicazioni
     *
     * @param nmApplics
     *            lista distinta nome applicazioni
     * @param tiStatoAllinea1
     *            tipo stato
     * @param tiStatoAllinea2
     *            tipo stato
     * @param nmRuolo
     *            nome ruolo
     * @param tiRuolo
     *            tipo ruolo
     * @param tiCategRuolo
     *            categoria
     *
     * @return lista elementi di tipo {@link PrfVLisRuolo}
     */
    public List<PrfVLisRuolo> getPrfVLisRuoloList(Set<String> nmApplics, String tiStatoAllinea1, String tiStatoAllinea2,
            String nmRuolo, String tiRuolo, String tiCategRuolo) {
        String queryStr = "SELECT DISTINCT new it.eng.saceriam.viewEntity.PrfVLisRuolo"
                + "(ruoli.idRuolo, ruoli.nmRuolo, ruoli.dsRuolo, ruoli.tiRuolo, ruoli.tiCategRuolo, "
                + "ruoli.dsApplic, ruoli.flAllineamentoInCorso, ruoli.tiStatoRichAllineaRuoli_1, ruoli.tiStatoRichAllineaRuoli_2, "
                + "ruoli.dsEsitoRichAllineaRuoli_1, ruoli.dsEsitoRichAllineaRuoli_2, ruoli.dsMsgAllineamentoParz) "
                + "FROM PrfVLisRuolo ruoli ";
        String clause = " WHERE ";
        if (StringUtils.isNotBlank(nmRuolo)) {
            queryStr += clause + " UPPER(ruoli.nmRuolo) LIKE :nmRuolo ";
            clause = " AND ";
        }
        if (tiRuolo != null) {
            queryStr += clause + " ruoli.tiRuolo = :tiRuolo ";
            clause = " AND ";
        }
        if (tiCategRuolo != null) {
            queryStr += clause + " ruoli.tiCateg IN (:tiCategRuolo) ";
            clause = " AND ";
        }
        if (nmApplics != null) {
            queryStr += clause + " ruoli.nmApplic IN (:nmApplics) ";
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(tiStatoAllinea1)) {
            queryStr += clause + " ruoli.tiStatoRichAllineaRuoli_1 = :tiStatoAllinea1 ";
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(tiStatoAllinea2)) {
            queryStr += clause + " ruoli.tiStatoRichAllineaRuoli_2 = :tiStatoAllinea2 ";
        }

        queryStr = queryStr + "ORDER BY ruoli.nmRuolo ";

        Query q = getEntityManager().createQuery(queryStr);
        if (StringUtils.isNotBlank(nmRuolo)) {
            q.setParameter("nmRuolo", "%" + nmRuolo.toUpperCase() + "%");
        }
        if (tiRuolo != null) {
            q.setParameter("tiRuolo", tiRuolo);
        }
        if (tiCategRuolo != null) {
            List<String> tiCategRuoloList = Arrays.asList(tiCategRuolo);
            q.setParameter("tiCategRuolo", tiCategRuoloList);
        }
        if (nmApplics != null) {
            q.setParameter("nmApplics", nmApplics);
        }
        if (StringUtils.isNotBlank(tiStatoAllinea1)) {
            q.setParameter("tiStatoAllinea1", tiStatoAllinea1);
        }
        if (StringUtils.isNotBlank(tiStatoAllinea2)) {
            q.setParameter("tiStatoAllinea2", tiStatoAllinea2);
        }
        return q.getResultList();
    }

    /**
     * Ritorno il ruolo in base al suo id
     *
     * @param idRuolo
     *            id ruolo
     *
     * @return entity {@link PrfVLisRuolo}
     */
    public PrfVLisRuolo getPrfVLisRuolo(BigDecimal idRuolo) {
        String queryStr = "SELECT DISTINCT new it.eng.saceriam.viewEntity.PrfVLisRuolo"
                + "(ruoli.idRuolo, ruoli.nmRuolo, ruoli.dsRuolo, ruoli.tiRuolo, ruoli.tiCategRuolo, "
                + "ruoli.dsApplic, ruoli.flAllineamentoInCorso, ruoli.tiStatoRichAllineaRuoli_1, ruoli.tiStatoRichAllineaRuoli_2, "
                + "ruoli.dsEsitoRichAllineaRuoli_1, ruoli.dsEsitoRichAllineaRuoli_2, ruoli.dsMsgAllineamentoParz) "
                + "FROM PrfVLisRuolo ruoli " + "WHERE ruoli.idRuolo = :idRuolo ";

        Query q = getEntityManager().createQuery(queryStr);
        if (idRuolo != null) {
            q.setParameter("idRuolo", idRuolo);
        }

        final List resultList = q.getResultList();
        if (resultList.isEmpty()) {
            throw new NoResultException("Nessun PrfVLisRuolo trovato con idRuolo " + idRuolo);
        }
        return (PrfVLisRuolo) (resultList.get(0));
    }

    public Set<BigDecimal> getIdUsoRuoloApplicSet(BigDecimal idRuolo) {
        String queryStr = "SELECT ur.idUsoRuoloApplic " + "FROM PrfUsoRuoloApplic ur "
                + "WHERE ur.prfRuolo.idRuolo = :idRuolo ";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idRuolo", longFrom(idRuolo));
        return new HashSet<>(q.getResultList());
    }

    public List<PrfRuolo> getPrfRuoli() {
        String queryStr = "SELECT ruoli " + "FROM PrfRuolo ruoli ";
        Query q = getEntityManager().createQuery(queryStr);
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome
     *
     * @return la lista delle applicazioni
     *
     */
    public List<AplApplic> getAplApplicList() {
        String queryStr = "SELECT applic FROM AplApplic applic ORDER BY applic.nmApplic";
        Query query = getEntityManager().createQuery(queryStr);
        return query.getResultList();
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base al ruolo
     *
     * @param idUsoRuoloApplic
     *            id ruolo
     *
     * @return la lista delle applicazioni
     */
    public List<PrfUsoRuoloApplic> getPrfUsoRuoloApplicList(Set idUsoRuoloApplic) {
        String queryStr = "SELECT usoapplic FROM PrfUsoRuoloApplic usoapplic "
                + "WHERE usoapplic.idUsoRuoloApplic IN (:idUsoRuoloApplic) ORDER BY usoapplic.aplApplic.nmApplic ";
        Query query = getEntityManager().createQuery(queryStr);
        // a volte arrivano long altre bigdecimal ...
        if (isCollectionOf(Long.class, idUsoRuoloApplic)) {
            query.setParameter("idUsoRuoloApplic", (Set<Long>) idUsoRuoloApplic);
        } else {
            query.setParameter("idUsoRuoloApplic", longListFrom(idUsoRuoloApplic));
        }
        return query.getResultList();
    }

    /**
     * Metodo che ritorna la lista di dichiarazioni in base al ruolo e al tipo dichiarazione dati come parametri
     *
     * @param idUsoRuoloSet
     *            la collezione di idUsoRuoloApplic
     * @param idApplic
     *            id applicazione
     * @param tiDichAutor
     *            il tipo di dichiarazione
     *
     * @return la lista di dichiarazioni
     */
    public List<PrfVLisDichAutor> getPrfVLisDichAutorList(Set idUsoRuoloSet, String tiDichAutor, BigDecimal idApplic) {
        String queryStr = "SELECT usoruoli " + "FROM PrfVLisDichAutor usoruoli "
                + "WHERE usoruoli.idUsoRuoloApplic IN (:idusoruolo) " + "AND usoruoli.tiDichAutor = :dichautor ";

        if (idApplic != null) {
            queryStr = queryStr + "AND usoruoli.idApplic = :idApplic ";
        }
        queryStr += "ORDER BY usoruoli.nmApplic ";

        Query q = getEntityManager().createQuery(queryStr);
        // a volte arrivano long altre bigdecimal ...
        if (isCollectionOf(BigDecimal.class, idUsoRuoloSet)) {
            q.setParameter("idusoruolo", (Set<BigDecimal>) idUsoRuoloSet);
        } else {
            q.setParameter("idusoruolo", bigDecimalListFrom(idUsoRuoloSet));
        }
        q.setParameter("dichautor", tiDichAutor);
        if (idApplic != null) {
            q.setParameter("idApplic", idApplic);
        }
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista di dichiarazioni in base al ruolo e al tipo dichiarazione dati come parametri
     *
     * @param idRuolo
     *            id del ruolo
     * @param idApplic
     *            id applicazione
     * @param tiDichAutor
     *            il tipo di dichiarazione
     *
     * @return la lista di dichiarazioni
     */
    public List<PrfVLisDichAutor> getPrfVLisDichAutorList(BigDecimal idRuolo, String tiDichAutor, BigDecimal idApplic) {
        String queryStr = "SELECT usoruoli " + "FROM PrfVLisDichAutor usoruoli " + "WHERE usoruoli.idRuolo = :idruolo "
                + "AND usoruoli.tiDichAutor = :dichautor ";

        if (idApplic != null) {
            queryStr = queryStr + "AND usoruoli.idApplic = :idApplic ";
        }
        queryStr += "ORDER BY usoruoli.nmApplic ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idruolo", idRuolo);
        q.setParameter("dichautor", tiDichAutor);
        if (idApplic != null) {
            q.setParameter("idApplic", idApplic);
        }
        return q.getResultList();
    }

    /**
     * @deprecated tiDichAutor non viene mai usato, richiamare il metodo senza questo parametro
     *
     * @param usoRuoloApplicRowBean
     *            uso ruolo
     * @param tiDichAutor
     *            tipo dich autore
     *
     * @return voci di menu delle abilitazioni
     */
    @Deprecated
    public List<Object[]> getAbilitazioniEntryMenu4Applic(PrfUsoRuoloApplicRowBean usoRuoloApplicRowBean,
            String tiDichAutor) {
        return getAbilitazioniEntryMenu4Applic(usoRuoloApplicRowBean);
    }

    /**
     * @param usoRuoloApplicRowBean
     *            uso Ruolo applicazione
     *
     * @return voci di menu delle abilitazioni
     */
    public List<Object[]> getAbilitazioniEntryMenu4Applic(PrfUsoRuoloApplicRowBean usoRuoloApplicRowBean) {
        return getAbilitazioniEntryMenu4Applic(usoRuoloApplicRowBean.getIdUsoRuoloApplic(),
                usoRuoloApplicRowBean.getString("nm_applic"), usoRuoloApplicRowBean.getIdRuolo());
    }

    /**
     * @param idUsoRuoloApplic
     *            is uso ruolo
     * @param nmApplic
     *            nome applicazione
     * @param idRuolo
     *            id ruolo
     *
     * @return voci di menu delle abilitazioni
     */
    public List<Object[]> getAbilitazioniEntryMenu4Applic(BigDecimal idUsoRuoloApplic, String nmApplic,
            BigDecimal idRuolo) {

        String nativeQuery = "SELECT dich_padre.id_dich_autor as id_dich_autor_padre, dich_foglia.id_dich_autor, "
                + "dich_padre.ti_scopo_dich_autor as ti_scopo_dich_autor_padre, dich_foglia.ti_scopo_dich_autor, tree.dl_composito_entry_menu, tree.id_entry_menu "
                + "from APL_V_TREE_ENTRY_MENU tree " + "left join prf_dich_autor dich_padre "
                + "on (dich_padre.id_entry_menu_padre = tree.id_entry_menu "
                + "and dich_padre.id_uso_ruolo_applic = :idUsoRuoloApplic ) " + "left join prf_dich_autor dich_foglia "
                + "on (dich_foglia.id_entry_menu_foglia = tree.id_entry_menu "
                + "and dich_foglia.id_uso_ruolo_applic = :idUsoRuoloApplic ) " + "where id_entry_menu in ( "
                + "select id_entry_menu " + "from PRF_V_LIS_DICH_AUTOR " + "where id_ruolo = :idRuolo "
                + "and nm_applic = :nmApplic " + "and ti_dich_autor = 'ENTRY_MENU') ";

        Query q = getEntityManager().createNativeQuery(nativeQuery);
        q.setParameter("idUsoRuoloApplic", idUsoRuoloApplic);
        q.setParameter("nmApplic", nmApplic);
        q.setParameter("idRuolo", idRuolo);
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista di entry menu dato l'idApplicazione Il parametro ricercaFoglie serve a determinare se
     * bisogna ritornare la lista di entryMenu che sono foglie (ricercaFoglie = true) o non sono foglie (ricercaFoglie =
     * false).
     *
     * @param idApplicazione
     *            id applicazione
     * @param ricercaFoglie
     *            ricerca
     *
     * @return il tableBean della lista di entry menu
     */
    public List<AplEntryMenu> getEntryMenuList(BigDecimal idApplicazione, boolean ricercaFoglie) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT nodo.ID_ENTRY_MENU, " + "nodo.ID_APPLIC, " + "nodo.NM_ENTRY_MENU, " + "nodo.DS_ENTRY_MENU, "
                        + "nodo.NI_LIVELLO_ENTRY_MENU, " + "nodo.NI_ORD_ENTRY_MENU, " + "nodo.ID_ENTRY_MENU_PADRE, "
                        + "nodo.DL_LINK_ENTRY_MENU " + "FROM APL_ENTRY_MENU nodo, APL_ENTRY_MENU padre "
                        + "WHERE nodo.id_applic = :idappl AND padre.id_entry_menu (+) = nodo.id_entry_menu_padre AND ");
        queryStr.append(ricercaFoglie ? "NOT " : "");
        queryStr.append("EXISTS (" + "SELECT * from APL_ENTRY_MENU nodo_padre "
                + "WHERE nodo_padre.id_entry_menu_padre = nodo.id_entry_menu" + ") "
                + "START WITH nodo.nm_entry_menu = 'Menu' "
                + "CONNECT BY PRIOR nodo.id_entry_menu = nodo.id_entry_menu_padre "
                + "ORDER SIBLINGS BY nodo.ni_ord_entry_menu");
        Query q = getEntityManager().createNativeQuery(queryStr.toString(), AplEntryMenu.class);
        q.setParameter("idappl", idApplicazione);
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista di entry menu dato l'idApplicazione Il parametro ricercaFoglie serve a determinare se
     * bisogna ritornare la lista di entryMenu che sono foglie (ricercaFoglie = true) o non sono foglie (ricercaFoglie =
     * false).
     *
     * @param idApplic
     *            id applicazione
     * @param ricercaFoglie
     *            ricerca
     * @param nodiGiaPresenti
     *            lista nodi
     *
     * @return il tableBean della lista di entry menu
     */
    public List<AplVTreeEntryMenu> getEntryMenuList2(BigDecimal idApplic, boolean ricercaFoglie,
            Set<String> nodiGiaPresenti) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT apl FROM AplVTreeEntryMenu apl " + "WHERE apl.idApplic = :idApplic ");

        if (ricercaFoglie) {
            queryStr.append("AND apl.dlLinkEntryMenu IS NOT NULL ");
        } else {
            queryStr.append("AND apl.dlLinkEntryMenu IS NULL ");
        }

        queryStr.append("AND apl.idEntryMenuPadre IS NOT NULL ");
        if (!nodiGiaPresenti.isEmpty()) {
            queryStr.append("AND apl.dlCompositoEntryMenu NOT IN (:nodiGiaPresenti) ");
        }

        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idApplic", idApplic);
        if (!nodiGiaPresenti.isEmpty()) {
            q.setParameter("nodiGiaPresenti", nodiGiaPresenti);
        }

        return q.getResultList();
    }

    public List<AplVTreeMenuPagAzio> getRoleTreeNodesList(BigDecimal idApplic, String tiDichAutor) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT apl FROM AplVTreeMenuPagAzio apl WHERE apl.idApplic = :idApplic ");
        ConstPrfDichAutor.TiDichAutor tipoDich = ConstPrfDichAutor.TiDichAutor.valueOf(tiDichAutor);
        switch (tipoDich) {
        case ENTRY_MENU:
            queryStr.append("AND apl.tipoNodo NOT IN ('P', 'A')");
            break;
        case PAGINA:
            queryStr.append("AND apl.tipoNodo != 'A'");
            break;
        case AZIONE:
            break;
        case SERVIZIO_WEB:
        default:
            throw new AssertionError(tipoDich.name());
        }
        Query q = getEntityManager().createQuery(queryStr.toString(), AplVTreeMenuPagAzio.class);
        q.setParameter("idApplic", idApplic);
        return q.getResultList();
    }

    public AplEntryMenu getRootEntryMenu(BigDecimal idApplic) {
        Query q = getEntityManager().createQuery(
                "SELECT apl FROM AplEntryMenu apl WHERE apl.aplApplic.idApplic = :idApplic AND apl.entryMenuPadre IS NULL");
        q.setParameter("idApplic", longFrom(idApplic));
        List<AplEntryMenu> entryMenus = q.getResultList();
        if (!entryMenus.isEmpty()) {
            return entryMenus.get(0);
        }
        return null;
    }

    /**
     * Metodo che ritorna dato un'entry menu la lista dei suoi figli
     *
     * @param idEntryMenu
     *            id entry menu
     *
     * @return il tableBean contenente i figli dell'entry menu specificata
     */
    public List<AplEntryMenu> getEntryMenuChilds(BigDecimal idEntryMenu) {
        String queryStr = "SELECT menu.ID_ENTRY_MENU, " + "menu.ID_APPLIC, " + "menu.NM_ENTRY_MENU, "
                + "menu.DS_ENTRY_MENU, " + "menu.NI_LIVELLO_ENTRY_MENU, " + "menu.NI_ORD_ENTRY_MENU, "
                + "menu.ID_ENTRY_MENU_PADRE, " + "menu.DL_LINK_ENTRY_MENU FROM APL_ENTRY_MENU menu "
                + "START WITH menu.id_entry_menu = :identrymenu"
                + " CONNECT BY PRIOR menu.id_entry_menu = menu.id_entry_menu_padre";
        Query q = getEntityManager().createNativeQuery(queryStr, AplEntryMenu.class);
        q.setParameter("identrymenu", idEntryMenu);
        return q.getResultList();
    }

    /**
     * Metodo che ritorna dato un'entry menu la lista dei suoi padri
     *
     * @param idEntryMenu
     *            id entry menu
     *
     * @return il tableBean contenente i padri dell'entry menu specificata
     */
    public List<AplEntryMenu> getEntryMenuParentsList(BigDecimal idEntryMenu) {
        String queryStr = "SELECT entrata.ID_ENTRY_MENU, " + "entrata.ID_APPLIC, " + "entrata.NM_ENTRY_MENU, "
                + "entrata.DS_ENTRY_MENU, " + "entrata.NI_LIVELLO_ENTRY_MENU, " + "entrata.NI_ORD_ENTRY_MENU, "
                + "entrata.ID_ENTRY_MENU_PADRE, " + "entrata.DL_LINK_ENTRY_MENU " + "FROM APL_ENTRY_MENU entrata "
                + "WHERE entrata.id_entry_menu IN " + "(SELECT CONNECT_BY_ROOT nodo.id_entry_menu "
                + "FROM APL_ENTRY_MENU nodo " + "WHERE nodo.id_entry_menu = :identrymenu "
                + "CONNECT BY PRIOR nodo.id_entry_menu = nodo.id_entry_menu_padre) "
                + "AND entrata.id_entry_menu != :identrymenu " + "ORDER BY ni_livello_entry_menu";

        Query q = getEntityManager().createNativeQuery(queryStr, AplEntryMenu.class);
        q.setParameter("identrymenu", idEntryMenu);
        return q.getResultList();

    }

    /**
     * Metodo che ritorna tutte le azioni di una pagina data come parametro
     *
     * @param idPaginaWeb
     *            l'id della pagina web
     *
     * @return il tableBean contenente la lista di azioni
     */
    public List<AplAzionePagina> getActionChilds(BigDecimal idPaginaWeb) {
        String queryStr = "SELECT action FROM AplAzionePagina action "
                + "WHERE action.aplPaginaWeb.idPaginaWeb = :idpagina";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idpagina", longFrom(idPaginaWeb));
        return q.getResultList();
    }

    /**
     * Metodo che ritorna l'azione in base al suo id
     *
     * @param idAzionePagina
     *            id azione per pagina
     *
     * @return bean {@link AplAzionePagina}
     */
    public AplAzionePagina getAplAzionePagina(BigDecimal idAzionePagina) {
        return getEntityManager().find(AplAzionePagina.class, idAzionePagina.longValue());
    }

    /**
     * Metodo che ritorna la lista di pagine dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * @param nodiGiaPresenti
     *            nodi presenti
     *
     * @return il tableBean della lista di pagine
     */
    public List<AplPaginaWeb> getPagesList(BigDecimal idApplicazione, Set<String> nodiGiaPresenti) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT pag " + "FROM AplPaginaWeb pag " + "WHERE pag.aplApplic.idApplic = :idappl ");
        if (!nodiGiaPresenti.isEmpty()) {
            queryStr.append("AND pag.dsPaginaWeb NOT IN (:nodiGiaPresenti) ");
        }
        queryStr.append("ORDER BY pag.dsPaginaWeb");
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idappl", longFrom(idApplicazione));
        if (!nodiGiaPresenti.isEmpty()) {
            q.setParameter("nodiGiaPresenti", nodiGiaPresenti);
        }
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista di azioni dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * @param azioniGiaPresenti
     *            lista azioni presenti
     *
     * @return il tableBean della lista di pagine
     */
    public List<AplPaginaWeb> getPagesAzioniList(BigDecimal idApplicazione, Set<BigDecimal> azioniGiaPresenti) {
        StringBuilder queryStr = new StringBuilder("SELECT DISTINCT pag " + "FROM AplAzionePagina azio "
                + "JOIN azio.aplPaginaWeb pag " + "WHERE pag.aplApplic.idApplic = :idappl ");
        if (!azioniGiaPresenti.isEmpty()) {
            queryStr.append("AND azio.idAzionePagina NOT IN (:azioniGiaPresenti) ");
        }
        queryStr.append("ORDER BY pag.dsPaginaWeb");
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idappl", longFrom(idApplicazione));
        if (!azioniGiaPresenti.isEmpty()) {
            q.setParameter("azioniGiaPresenti", longListFrom(azioniGiaPresenti));
        }
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista di azioni dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * @param idPaginaWeb
     *            id pagina
     *
     * @return il tableBean della lista di azioni
     */
    public List<AplAzionePagina> getActionsList(BigDecimal idApplicazione, BigDecimal idPaginaWeb) {
        String queryStr = "SELECT azio " + "FROM AplAzionePagina azio " + "JOIN azio.aplPaginaWeb pag "
                + "WHERE pag.aplApplic.idApplic = :idappl " + "AND pag.idPaginaWeb = :idpagina "
                + "ORDER BY azio.dsAzionePagina";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idappl", longFrom(idApplicazione));
        q.setParameter("idpagina", longFrom(idPaginaWeb));
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista di azioni dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * @param idPaginaWeb
     *            id pagina
     * @param azioniGiaInserite
     *            lista azioni inserite
     *
     * @return il tableBean della lista di azioni
     */
    public List<AplAzionePagina> getActionsPagesList(BigDecimal idApplicazione, BigDecimal idPaginaWeb,
            Set<BigDecimal> azioniGiaInserite) {
        String queryStr = "SELECT azio " + "FROM AplAzionePagina azio " + "JOIN azio.aplPaginaWeb pag "
                + "WHERE pag.aplApplic.idApplic = :idappl " + "AND pag.idPaginaWeb = :idpagina ";
        if (!azioniGiaInserite.isEmpty()) {
            queryStr = queryStr + "AND azio.idAzionePagina NOT IN (:azioniGiaInserite) ";
        }
        queryStr = queryStr + "ORDER BY azio.dsAzionePagina";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idappl", longFrom(idApplicazione));
        q.setParameter("idpagina", longFrom(idPaginaWeb));
        if (!azioniGiaInserite.isEmpty()) {
            q.setParameter("azioniGiaInserite", longListFrom(azioniGiaInserite));
        }
        return q.getResultList();
    }

    /**
     * Metodo che ritorna la lista di servizi dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * @param nodiGiaPresenti
     *            nodi presenti
     *
     * @return il tableBean della lista di servizi
     */
    public List<AplServizioWeb> getServicesList(BigDecimal idApplicazione, Set<String> nodiGiaPresenti) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT serv " + "FROM AplServizioWeb serv " + "WHERE serv.aplApplic.idApplic = :idappl ");
        if (!nodiGiaPresenti.isEmpty()) {
            queryStr.append("AND serv.dsServizioWeb NOT IN (:nodiGiaPresenti) ");
        }
        queryStr.append("ORDER BY serv.dsServizioWeb");
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idappl", longFrom(idApplicazione));
        if (!nodiGiaPresenti.isEmpty()) {
            q.setParameter("nodiGiaPresenti", nodiGiaPresenti);
        }
        return q.getResultList();
    }

    /**
     * Metodo che ritorna il rowBean della dichiarazione dati i seguenti parametri
     *
     * @param tiScopoDichAutor
     *            lo scopo della dichiarazione
     * @param tiDichAutor
     *            il tipo dichiarazione
     * @param idObject
     *            l'id associato alla dichiarazione, definito tra entry menu, pagine e servizi web
     * @param idObject2
     *            l'id azione associato alla dichiarazione, definito solo nel caso in cui il tipo dichiarazione sia
     *            AZIONE
     * @param idPrfUsoRuoloApplic
     *            l'id uso ruolo associato alla dichiarazione
     *
     * @return il rowBean della dichiarazione
     */
    public List<PrfDichAutor> getPrfDichAutor(String tiScopoDichAutor, String tiDichAutor, BigDecimal idObject,
            BigDecimal idObject2, BigDecimal idPrfUsoRuoloApplic) {
        String where = " AND ";
        StringBuilder queryStr = new StringBuilder("SELECT dich FROM PrfDichAutor dich "
                + "WHERE dich.tiScopoDichAutor = :scopodich " + "AND dich.tiDichAutor = :tipodich "
                + "AND dich.prfUsoRuoloApplic.idUsoRuoloApplic = :idusoruolo");
        if (tiDichAutor.equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())) {
            if (tiScopoDichAutor.equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name())) {
                queryStr.append(where).append("dich.aplEntryMenuPadre.idEntryMenu = :object");
            } else {
                queryStr.append(where).append("dich.aplEntryMenuFoglia.idEntryMenu = :object");
            }
        } else if (tiDichAutor.equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
            queryStr.append(where).append("dich.aplPaginaWeb.idPaginaWeb = :object");
        } else if (tiDichAutor.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
            queryStr.append(where).append("dich.aplPaginaWeb.idPaginaWeb = :object");
            queryStr.append(where).append("dich.aplAzionePagina.idAzionePagina = :object2");
        } else if (tiDichAutor.equals(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name())) {
            queryStr.append(where).append("dich.aplServizioWeb.idServizioWeb = :object");
        }
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("scopodich", tiScopoDichAutor);
        q.setParameter("tipodich", tiDichAutor);
        q.setParameter("idusoruolo", longFrom(idPrfUsoRuoloApplic));
        q.setParameter("object", longFrom(idObject));
        if (tiDichAutor.equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
            q.setParameter("object2", longFrom(idObject2));
        }
        return q.getResultList();
    }

    /**
     * Ritorna l'idUsoRuoloApplic dati idRuolo e idApplicazione
     *
     * @param idRuolo
     *            id ruolo
     * @param idAppl
     *            id applicazione
     *
     * @return idUsoRuoloApplic
     */
    public List<Long> getIdUsoRuoloApplic(BigDecimal idRuolo, BigDecimal idAppl) {
        String queryStr = "SELECT usoruoli.idUsoRuoloApplic FROM PrfUsoRuoloApplic usoruoli "
                + "JOIN usoruoli.prfRuolo ruoli " + "WHERE usoruoli.aplApplic.idApplic = :idappl "
                + "AND ruoli.idRuolo = :idrole";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idrole", longFrom(idRuolo));
        q.setParameter("idappl", longFrom(idAppl));
        return q.getResultList();
    }

    public AplApplic getAplApplicById(BigDecimal idApplic) {
        return getEntityManager().find(AplApplic.class, idApplic.longValue());
    }

    public PrfRuolo getPrfRuolo(String nmRuolo) {
        Query query = getEntityManager()
                .createQuery("SELECT ruolo FROM PrfRuolo ruolo WHERE ruolo.nmRuolo = :nmRuolo ");
        query.setParameter("nmRuolo", nmRuolo);
        List<PrfRuolo> ruoli = query.getResultList();
        PrfRuolo ruolo = null;
        if (ruoli != null && !ruoli.isEmpty()) {
            ruolo = ruoli.get(0);
        }
        return ruolo;
    }

    public boolean checkIsUniqueRole(String nmRuoloPreMod, String nmRuoloPostMod, Status status) {
        String queryStr = "SELECT ruolo FROM PrfRuolo ruolo " + "WHERE ruolo.nmRuolo = :nmRuolo ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("nmRuolo", nmRuoloPostMod);
        List<PrfRuolo> ruoli = query.getResultList();
        if (ruoli != null && ruoli.size() == 1) {
            if (status.equals(Status.insert)) {
                return false;
            } else {
                return nmRuoloPreMod.equals(nmRuoloPostMod);
            }
        } else {
            return true;
        }
    }

    public UsrUser getUsrUser(BigDecimal idUser) {
        return getEntityManager().find(UsrUser.class, idUser.longValue());
    }

    public List<UsrUsoUserApplic> getUsrUsoUserApplicList(BigDecimal idUserIam) {
        String queryStr = "SELECT uuua FROM UsrUsoUserApplic uuua "
                + "WHERE uuua.usrUser.idUserIam = :idUserIam ORDER BY uuua.aplApplic.nmApplic ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        return query.getResultList();
    }

    public List<UsrUsoRuoloUserDefault> getUsrUsoRuoloUserDefaultList(BigDecimal idUserIam) {
        String queryStr = "SELECT uurud FROM UsrUsoUserApplic uuua JOIN uuua.usrUsoRuoloUserDefaults uurud "
                + "WHERE uuua.usrUser.idUserIam = :idUserIam ORDER BY uuua.aplApplic.nmApplic, uurud.prfRuolo.nmRuolo ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        return query.getResultList();
    }

    public List<UsrUser> getUtentiUsoRuoloUserDefaultList(BigDecimal idRuolo, List<String> tiStatoUser) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT uuua.usrUser FROM UsrUsoUserApplic uuua, UsrStatoUser statoUser "
                        + "JOIN uuua.usrUsoRuoloUserDefaults uurud " + "WHERE uurud.prfRuolo.idRuolo = :idRuolo "
                        + "AND uuua.usrUser.idStatoUserCor = statoUser.idStatoUser ");
        if (tiStatoUser != null) {
            queryStr.append("AND statoUser.tiStatoUser NOT IN (:tiStatoUser) ");
        }
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idRuolo", longFrom(idRuolo));
        if (tiStatoUser != null) {
            query.setParameter("tiStatoUser", tiStatoUser);
        }
        return query.getResultList();
    }

    public List<UsrUser> getUtentiUsoRuoloOrganizList(BigDecimal idRuolo, List<String> tiStatoUser) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT user FROM UsrUsoRuoloDich usoRuoloDich, UsrStatoUser statoUser "
                        + "JOIN usoRuoloDich.usrDichAbilOrganiz dichAbilOrganiz "
                        + "JOIN dichAbilOrganiz.usrUsoUserApplic usoUserApplic " + "JOIN usoUserApplic.usrUser user "
                        + "WHERE usoRuoloDich.prfRuolo.idRuolo = :idRuolo "
                        + "AND user.idStatoUserCor = statoUser.idStatoUser ");
        if (tiStatoUser != null) {
            queryStr.append("AND statoUser.tiStatoUser NOT IN (:tiStatoUser) ");
        }
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idRuolo", longFrom(idRuolo));
        if (tiStatoUser != null) {
            query.setParameter("tiStatoUser", tiStatoUser);
        }
        return query.getResultList();
    }

    public List<Object[]> getUsrDichAbilOrganizList(BigDecimal idUserIam) {
        String queryStr = "SELECT dich_abil, user_tree.dlCompositoOrganiz "
                + "FROM UsrDichAbilOrganiz dich_abil JOIN dich_abil.usrUsoUserApplic user_applic, UsrVTreeOrganizIam user_tree "
                + "WHERE user_tree.idOrganizIam = dich_abil.usrOrganizIam.idOrganizIam "
                + "AND user_applic.usrUser.idUserIam = :idUserIam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        return query.getResultList();
    }

    public List<Object[]> getUsrDichAbilDatiList(BigDecimal idUserIam) {
        String queryStr = "SELECT dich_abil, tipo_dato.aplClasseTipoDato.nmClasseTipoDato, user_tree.dlCompositoOrganiz, tipo_dato.nmTipoDato "
                + "FROM UsrDichAbilDati dich_abil JOIN dich_abil.usrUsoUserApplic user_applic JOIN dich_abil.usrTipoDatoIam tipo_dato, UsrVTreeOrganizIam user_tree "
                + "WHERE user_tree.idOrganizIam = dich_abil.usrOrganizIam.idOrganizIam "
                + "AND user_applic.usrUser.idUserIam = :idUserIam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        return query.getResultList();
    }

    /**
     * Verifica l'esistenza dello username nel database
     *
     * @param userName
     *            username
     *
     * @return true se l'utente esiste già
     */
    public boolean checkUserExists(String userName) {
        Query query = getEntityManager().createQuery("SELECT user FROM UsrUser user WHERE user.nmUserid = :username");
        query.setParameter("username", userName);
        List<UsrUser> utenti = query.getResultList();
        return !utenti.isEmpty();
    }

    public Long countMenusList(BigDecimal idApplic) {
        Query query = getEntityManager()
                .createQuery("SELECT COUNT(apl) FROM AplEntryMenu apl WHERE apl.aplApplic.idApplic = :idApplic");
        query.setParameter("idApplic", longFrom(idApplic));
        return (Long) query.getSingleResult();
    }

    public Long countPagesList(BigDecimal idApplic) {
        Query query = getEntityManager()
                .createQuery("SELECT COUNT(pag) FROM AplPaginaWeb pag WHERE pag.aplApplic.idApplic = :idApplic ");
        query.setParameter("idApplic", longFrom(idApplic));
        return (Long) query.getSingleResult();
    }

    public Long countActionsList(BigDecimal idApplic) {
        Query query = getEntityManager().createQuery(
                "SELECT COUNT(azio) FROM AplAzionePagina azio WHERE azio.aplPaginaWeb.aplApplic.idApplic = :idApplic ");
        query.setParameter("idApplic", longFrom(idApplic));
        return (Long) query.getSingleResult();
    }

    /**
     * Ritorna l'oggetto UsoRuoloApplic dati idRuolo e idApplicazione
     *
     * @param idRuolo
     *            id ruolo
     * @param idAppl
     *            id applicazione
     *
     * @return UsoRuoloApplic
     */
    public PrfUsoRuoloApplic getPrfUsoRuoloApplic(long idRuolo, long idAppl) {
        String queryStr = "SELECT usoruoli FROM PrfUsoRuoloApplic usoruoli " + "JOIN usoruoli.prfRuolo ruoli "
                + "WHERE usoruoli.aplApplic.idApplic = :idappl " + "AND ruoli.idRuolo = :idrole";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idrole", idRuolo);
        q.setParameter("idappl", idAppl);
        PrfUsoRuoloApplic record = null;
        List<PrfUsoRuoloApplic> usoRuoloApplic = q.getResultList();
        if (usoRuoloApplic != null && !usoRuoloApplic.isEmpty()) {
            record = usoRuoloApplic.get(0);
        }
        return record;
    }

    public List<PrfVLisUserDaReplic> retrievePrfVLisUserDaReplic(Long idRuolo) {
        Query q = getEntityManager()
                .createQuery("SELECT prf FROM PrfVLisUserDaReplic prf WHERE prf.idRuolo = :idRuolo");
        q.setParameter("idRuolo", bigDecimalFrom(idRuolo));
        return q.getResultList();
    }

    /**
     * Esegue una bulk delete sulle dichiarazioni di abilitazione per il ruolo idRuolo sulla base della vista
     * PrfVLisDichAllineaTodel
     *
     * @param idRuolo
     *            id ruolo
     *
     * @return numero di record eliminati
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deletePrfDichAutorFromPrfVLisDichAllineaTodel(Long idRuolo) {
        Query q = getEntityManager().createQuery(
                "DELETE FROM PrfDichAutor dich WHERE dich.idDichAutor IN ( SELECT prf.idDichAutor FROM PrfVLisDichAllineaTodel prf WHERE prf.idRuolo = :idRuolo )");
        q.setParameter("idRuolo", bigDecimalFrom(idRuolo));
        int result = q.executeUpdate();
        getEntityManager().flush();
        return result;
    }

    public List<PrfVLisDichAllineaToadd> retrievePrfVLisDichAllineaToadd(Long idRuolo) {
        Query q = getEntityManager()
                .createQuery("SELECT prf FROM PrfVLisDichAllineaToadd prf WHERE prf.idRuolo = :idRuolo");
        q.setParameter("idRuolo", bigDecimalFrom(idRuolo));
        return q.getResultList();
    }

    /**
     * Esegue la bulk delete sui record di allineamento ruolo creati nella medesima transazione
     *
     * @param idRuolo
     *            id ruolo
     * @param nmApplic
     *            nome applicazione
     *
     * @return numero di record eliminati
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deletePrfAllineaRuolo(Long idRuolo, String nmApplic) {
        Query q = getEntityManager()
                .createQuery("DELETE FROM PrfAllineaRuolo allineaRuolo WHERE allineaRuolo.prfRuolo.idRuolo = :idRuolo"
                        + (StringUtils.isNotBlank(nmApplic) ? " AND allineaRuolo.nmApplic = :nmApplic" : ""));
        q.setParameter("idRuolo", idRuolo);
        if (StringUtils.isNotBlank(nmApplic)) {
            q.setParameter("nmApplic", nmApplic);
        }
        int result = q.executeUpdate();
        getEntityManager().flush();
        return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deletePrfRuoloCategoria(Long idRuoloCategoria) {
        Query q = getEntityManager().createQuery(
                "DELETE FROM PrfRuoloCategoria ruoloCategoria WHERE ruoloCategoria.idRuoloCategoria = :idRuoloCategoria");
        q.setParameter("idRuoloCategoria", idRuoloCategoria);
        int result = q.executeUpdate();
        getEntityManager().flush();
        return result;
    }

    public List<PrfVLisDichAutorDaAllin> retrievePrfVLisDichAutorDaAllin(BigDecimal idRuolo) {
        Query q = getEntityManager().createQuery(
                "SELECT prf FROM PrfVLisDichAutorDaAllin prf WHERE prf.idRuolo = :idRuolo ORDER BY prf.nmApplic, prf.tiDichAutor");
        q.setParameter("idRuolo", idRuolo);
        return q.getResultList();
    }

    public List<PrfRuoloCategoria> retrievePrfRuoloCategoria(BigDecimal idRuolo) {
        Query q = getEntityManager().createQuery(
                "SELECT prf FROM PrfRuoloCategoria prf WHERE prf.prfRuolo.idRuolo = :idRuolo ORDER BY prf.tiCategRuolo");
        q.setParameter("idRuolo", longFrom(idRuolo));
        return q.getResultList();
    }

    public PrfVChkAllineaRuolo getPrfVChkAllineaRuolo(Long idRuolo, String nmApplic) {
        return getEntityManager().createQuery(
                "SELECT prf FROM PrfVChkAllineaRuolo prf WHERE prf.idRuolo = :idRuolo AND prf.nmApplic = :nmApplic",
                PrfVChkAllineaRuolo.class).setParameter("idRuolo", bigDecimalFrom(idRuolo))
                .setParameter("nmApplic", nmApplic).getSingleResult();
    }

    public Long countPrfAllineaRuolo(Long idRuolo) {
        return getEntityManager()
                .createQuery("SELECT COUNT(prf) FROM PrfAllineaRuolo prf WHERE prf.prfRuolo.idRuolo = :idRuolo",
                        Long.class)
                .setParameter("idRuolo", idRuolo).getSingleResult();
    }

    public List<PrfRuoloCategoria> getPrfRuoloCategoriaList(BigDecimal idRuolo) {
        String queryStr = "SELECT ruoloCategoria FROM PrfRuoloCategoria ruoloCategoria "
                + "WHERE ruoloCategoria.prfRuolo.idRuolo = :idRuolo ORDER BY ruoloCategoria.tiCategRuolo ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idRuolo", longFrom(idRuolo));
        return query.getResultList();
    }

    public Set<String> getTiCategRuoloList(BigDecimal idRuolo) {
        String queryStr = "SELECT ruoloCategoria.tiCategRuolo FROM PrfRuoloCategoria ruoloCategoria "
                + "WHERE ruoloCategoria.prfRuolo.idRuolo = :idRuolo ORDER BY ruoloCategoria.tiCategRuolo ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idRuolo", longFrom(idRuolo));
        return new HashSet(query.getResultList());
    }

    /**
     * @deprecated
     *
     * @param idRuolo
     *            id ruolo
     * @param tiCategRuolo
     *            tipo categoria ruolo
     *
     * @return lista di {@link PrfRuoloCategoria}
     */
    @Deprecated
    public List<PrfRuoloCategoria> getPrfRuoloCategoriaList(BigDecimal idRuolo, List<String> tiCategRuolo) {
        String queryStr = "SELECT ruoloCategoria FROM PrfRuoloCategoria ruoloCategoria "
                + "WHERE ruoloCategoria.prfRuolo.idRuolo = :idRuolo ";

        if (tiCategRuolo != null && !tiCategRuolo.isEmpty()) {
            queryStr = queryStr + "AND (SELECT ruoloCategoria2 FROM PrfRuoloCategoria ruoloCategoria2 "
                    + "WHERE ruoloCategoria2.prfRuolo.idRuolo = ruoloCategoria.prfRuolo.idRuolo "
                    + "AND ruoloCategoria2.tiCategRuolo IN (:tiCategRuolo)) ";
        }
        queryStr = queryStr + "ORDER BY ruoloCategoria.tiCategRuolo ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idRuolo", idRuolo);
        if (tiCategRuolo != null && !tiCategRuolo.isEmpty()) {
            query.setParameter("tiCategRuolo", tiCategRuolo);
        }
        return query.getResultList();
    }

}
