package it.eng.saceriam.web.helper;

import static it.eng.paginator.util.HibernateUtils.*;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplClasseTipoDato;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.PrfDichAutor;
import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.saceriam.entity.UsrAppartUserRich;
import it.eng.saceriam.entity.UsrDichAbilDati;
import it.eng.saceriam.entity.UsrDichAbilEnteConvenz;
import it.eng.saceriam.entity.UsrDichAbilOrganiz;
import it.eng.saceriam.entity.UsrIndIpUser;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrRichGestUser;
import it.eng.saceriam.entity.UsrTipoDatoIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoRuoloUserDefault;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteConvenz;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteNonConvenz;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.entity.constraint.ConstUsrAppartUserRich;
import it.eng.saceriam.grantedEntity.LogEventoLoginUser;
import it.eng.saceriam.grantedViewEntity.LogVRicAccessi;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm.FiltriReplica;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm.FiltriUtenti;
import it.eng.saceriam.viewEntity.OrgVChkRefEnte;
import it.eng.saceriam.viewEntity.PrfVLisDichAutor;
import it.eng.saceriam.viewEntity.UsrVAbilDati;
import it.eng.saceriam.viewEntity.UsrVAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVAbilOrganizNolastLiv;
import it.eng.saceriam.viewEntity.UsrVLisAbilEnte;
import it.eng.saceriam.viewEntity.UsrVLisAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVLisEntiSiamAppEnte;
import it.eng.saceriam.viewEntity.UsrVLisEntiSiamPerAzio;
import it.eng.saceriam.viewEntity.UsrVLisSched;
import it.eng.saceriam.viewEntity.UsrVLisStatoUser;
import it.eng.saceriam.viewEntity.UsrVLisUser;
import it.eng.saceriam.viewEntity.UsrVLisUserEnteConvenz;
import it.eng.saceriam.viewEntity.UsrVLisUserReplic;
import it.eng.saceriam.viewEntity.UsrVLisUsoRuoloDich;
import it.eng.saceriam.viewEntity.UsrVRicRichieste;
import it.eng.saceriam.viewEntity.UsrVTreeOrganizIam;
import it.eng.saceriam.viewEntity.UsrVVisDichAbil;
import it.eng.saceriam.viewEntity.UsrVVisLastRichGestUser;
import it.eng.saceriam.viewEntity.UsrVVisLastSched;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.spagoCore.error.EMFError;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.StringUtils;

/**
 * Session Bean implementation class AmministrazioneHelper Contiene i metodi, per la gestione della persistenza su DB
 * per le operazioni CRUD
 *
 */
@Stateless
@LocalBean
public class AmministrazioneUtentiHelper extends GenericHelper {

    public AmministrazioneUtentiHelper() {
        /*
         * Default constructor.
         */
    }

    /**
     * Metodo che ritorna i ruoli di un utente in base all'id applicazione
     *
     * @param idAppl
     *            l'id applicazione
     * 
     * @return la lista di ruoli
     */
    public List<PrfRuolo> getPrfRuoli(BigDecimal idAppl) {
        List<PrfRuolo> ruoli = new ArrayList<>();
        if (idAppl != null) {
            String queryStr = "SELECT ruoli " + "FROM PrfUsoRuoloApplic usoruoli JOIN usoruoli.prfRuolo ruoli "
                    + "WHERE usoruoli.aplApplic.idApplic = :idappl " + "ORDER BY ruoli.nmRuolo";

            Query q = getEntityManager().createQuery(queryStr);
            q.setParameter("idappl", longFrom(idAppl));
            ruoli = q.getResultList();
        }
        return ruoli;
    }

    /**
     * Metodo che ritorna i ruoli di un utente in base all'id applicazione, al tipo ente e tipo utente
     *
     * @param idAppl
     *            l'id applicazione
     * @param tipoEnte
     *            tipo ente
     * @param tipoUtente
     *            tipo utente
     * 
     * @return la lista di ruoli
     */
    public List<PrfRuolo> getPrfRuoliUtente(BigDecimal idAppl, String tipoEnte, String tipoUtente) {
        List<PrfRuolo> ruoli = new ArrayList<>();
        if (idAppl != null) {

            String queryStr = "SELECT ruoli " + "FROM PrfUsoRuoloApplic usoruoli JOIN usoruoli.prfRuolo ruoli, "
                    + "PrfRuoloCategoria cat WHERE cat.prfRuolo.idRuolo = ruoli.idRuolo "
                    + "AND usoruoli.aplApplic.idApplic = :idappl ";

            switch (tipoEnte) {
            case "AMMINISTRATORE":
                queryStr = queryStr
                        + "AND cat.tiCategRuolo IN ('amministrazione', 'conservazione', 'gestione', 'produzione') ";
                break;
            case "CONSERVATORE":
                queryStr = queryStr + "AND cat.tiCategRuolo IN ('conservazione', 'gestione', 'produzione') ";
                break;
            case "GESTORE":
                queryStr = queryStr + "AND cat.tiCategRuolo IN ('gestione', 'produzione') ";
                break;
            case "PRODUTTORE":
                queryStr = queryStr + "AND cat.tiCategRuolo IN ('produzione') ";
                break;
            case "ORGANO_VIGILANZA":
                queryStr = queryStr + "AND cat.tiCategRuolo IN ('vigilanza') ";
                break;
            case "FORNITORE_ESTERNO":
                queryStr = queryStr + "AND cat.tiCategRuolo IN ('supporto_esterno') ";
                break;
            default:
                break;
            }

            switch (tipoUtente) {
            case "AUTOMA":
                queryStr = queryStr + "AND ruoli.tiRuolo IN ('AUTOMA') ";
                break;
            case "PERSONA_FISICA":
                queryStr = queryStr + "AND ruoli.tiRuolo IN ('PERSONA_FISICA') ";
                break;
            default:
                break;
            }

            queryStr = queryStr + "ORDER BY ruoli.nmRuolo";

            Query q = getEntityManager().createQuery(queryStr);
            q.setParameter("idappl", longFrom(idAppl));
            ruoli = q.getResultList();
        }
        return ruoli;
    }

    public List<PrfRuolo> getPrfRuoli(String nmApplic) {
        List<PrfRuolo> ruoli = new ArrayList<>();
        if (nmApplic != null) {
            String queryStr = "SELECT ruoli " + "FROM PrfUsoRuoloApplic usoruoli JOIN usoruoli.prfRuolo ruoli "
                    + "WHERE usoruoli.aplApplic.nmApplic = :nmApplic " + "ORDER BY ruoli.nmRuolo";

            Query q = getEntityManager().createQuery(queryStr);
            q.setParameter("nmApplic", nmApplic);
            ruoli = q.getResultList();
        }
        return ruoli;
    }

    public List<PrfRuolo> getRuoliDefaultUtenteApplic(String nmApplic, Long idUserIam) {
        List<PrfRuolo> ruoli = new ArrayList<>();
        if (nmApplic != null && idUserIam != null) {
            String queryStr = "SELECT ruolo FROM UsrUsoRuoloUserDefault usoRuoloUserDefault "
                    + "JOIN usoRuoloUserDefault.usrUsoUserApplic usoUserApplic "
                    + "JOIN usoRuoloUserDefault.prfRuolo ruolo " + "WHERE usoUserApplic.aplApplic.nmApplic = :nmApplic "
                    + "AND usoUserApplic.usrUser.idUserIam = :idUserIam " + "ORDER BY ruolo.nmRuolo ";

            Query q = getEntityManager().createQuery(queryStr);
            q.setParameter("nmApplic", nmApplic);
            q.setParameter("idUserIam", longFrom(idUserIam));
            ruoli = q.getResultList();
        }
        return ruoli;
    }

    public List<PrfRuolo> getPrfRuoli() {
        String queryStr = "SELECT ruoli " + "FROM PrfRuolo ruoli " + "ORDER BY ruoli.nmRuolo";
        Query q = getEntityManager().createQuery(queryStr);
        List<PrfRuolo> ruoli = q.getResultList();
        return ruoli;
    }

    public List<PrfRuolo> getPrfRuoloById(BigDecimal idRuolo) {
        String queryStr = "SELECT p FROM PrfRuolo p " + "WHERE p.idRuolo = :ruolo";
        // CREO LA QUERY ATTRAVERSO L'ENTITY MANAGER
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("ruolo", longFrom(idRuolo));
        List<PrfRuolo> ruoli = query.getResultList();
        return ruoli;
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
        List<AplApplic> list = query.getResultList();
        return list;
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base al ruolo
     *
     * @param idUsoRuoloApplic
     *            id ruolo
     * 
     * @return la lista delle applicazioni
     *
     */
    public List<PrfUsoRuoloApplic> getPrfUsoRuoloApplicList(Set<BigDecimal> idUsoRuoloApplic) {
        String queryStr = "SELECT usoapplic FROM PrfUsoRuoloApplic usoapplic "
                + "WHERE usoapplic.idUsoRuoloApplic IN (:idUsoRuoloApplic) ORDER BY usoapplic.aplApplic.nmApplic ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUsoRuoloApplic", longListFrom(idUsoRuoloApplic));
        List<PrfUsoRuoloApplic> list = query.getResultList();
        return list;
    }

    /**
     * Metodo che ritorna in base la lista di dichiarazioni in base al ruolo e al tipo dichiarazione dati come parametri
     *
     * @param idUsoRuoloSet
     *            id ruolo
     * @param tiDichAutor
     *            il tipo di dichiarazione
     * @param idApplic
     *            id applicazione
     * 
     * @return la lista di dichiarazioni
     */
    public List<PrfVLisDichAutor> getPrfDichAutorList(Set<BigDecimal> idUsoRuoloSet, String tiDichAutor,
            BigDecimal idApplic) {
        String queryStr = "SELECT usoruoli " + "FROM PrfVLisDichAutor usoruoli "
                + "WHERE usoruoli.idUsoRuoloApplic IN (:idusoruolo) " + "AND usoruoli.tiDichAutor = :dichautor ";

        if (idApplic != null) {
            queryStr = queryStr + "AND usoruoli.idApplic = :idApplic ";
        }

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idusoruolo", idUsoRuoloSet);
        q.setParameter("dichautor", tiDichAutor);
        if (idApplic != null) {
            q.setParameter("idApplic", idApplic);
        }
        List<PrfVLisDichAutor> dichiarazioni = q.getResultList();
        return dichiarazioni;
    }

    public boolean hasServicesAuthorization(BigDecimal idRuolo, BigDecimal idApplic) {
        boolean hasServicesAuthorization = false;
        String queryStr = "SELECT dich FROM PrfUsoRuoloApplic usa, PrfDichAutor dich "
                + "WHERE usa.prfRuolo.idRuolo = :idRuolo " + "AND usa.aplApplic.idApplic = :idApplic "
                + "AND usa.idUsoRuoloApplic = dich.prfUsoRuoloApplic.idUsoRuoloApplic "
                + "AND dich.tiDichAutor = 'SERVIZIO_WEB' ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idRuolo", longFrom(idRuolo));
        q.setParameter("idApplic", longFrom(idApplic));
        List<PrfDichAutor> lista = q.getResultList();
        if (!lista.isEmpty()) {
            hasServicesAuthorization = true;
        }
        return hasServicesAuthorization;
    }

    /**
     * Metodo che ritorna la lista di entry menu dato l'idApplicazione Il parametro ricercaFoglie serve a determinare se
     * bisogna ritornare la lista di entryMenu che sono foglie (ricercaFoglie = true) o non sono foglie (ricercaFoglie =
     * false).
     *
     * @param idApplicazione
     *            id applicazione
     * @param ricercaFoglie
     *            true se ricerca foglie
     * 
     * @return il tableBean della lista di entry menu
     */
    @Deprecated
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
        q.setParameter("idappl", longFrom(idApplicazione));
        List<AplEntryMenu> entries = q.getResultList();
        return entries;
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
        q.setParameter("identrymenu", longFrom(idEntryMenu));
        List<AplEntryMenu> entries = q.getResultList();
        return entries;
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
                + "FROM APL_ENTRY_MENU nodo " + "WHERE nodo.id_entry_menu =:identrymenu "
                + "CONNECT BY PRIOR nodo.id_entry_menu = nodo.id_entry_menu_padre) "
                + "AND entrata.id_entry_menu != :identrymenu " + "ORDER BY ni_livello_entry_menu";

        Query q = getEntityManager().createNativeQuery(queryStr, AplEntryMenu.class);
        q.setParameter("identrymenu", longFrom(idEntryMenu));
        List<AplEntryMenu> entries = q.getResultList();
        return entries;

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
        List<AplAzionePagina> actions = q.getResultList();
        return actions;
    }

    /**
     * Metodo che ritorna l'azione in base al suo id
     *
     * @param idAzionePagina
     *            id azione pagina
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
     * 
     * @return il tableBean della lista di pagine
     */
    public List<AplPaginaWeb> getPagesList(BigDecimal idApplicazione) {
        String queryStr = "SELECT pag " + "FROM AplPaginaWeb pag " + "WHERE pag.aplApplic.idApplic = :idappl "
                + "ORDER BY pag.dsPaginaWeb";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idappl", longFrom(idApplicazione));
        List<AplPaginaWeb> pages = q.getResultList();
        return pages;
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
        List<AplAzionePagina> actions = q.getResultList();
        return actions;
    }

    /**
     * Metodo che ritorna la lista di servizi dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * 
     * @return il tableBean della lista di servizi
     */
    public List<AplServizioWeb> getServicesList(BigDecimal idApplicazione) {
        String queryStr = "SELECT serv " + "FROM AplServizioWeb serv " + "WHERE serv.aplApplic.idApplic = :idappl "
                + "ORDER BY serv.dsServizioWeb";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idappl", longFrom(idApplicazione));
        List<AplServizioWeb> services = q.getResultList();
        return services;
    }

    public List<UsrVAbilOrganiz> getUsrVAbilOrganizList(BigDecimal idUserIamCorrente, BigDecimal idApplic) {
        return getUsrVAbilOrganizList(idUserIamCorrente, idApplic, null, null, false);
    }

    public List<UsrVAbilOrganiz> getUsrVAbilOrganizList(BigDecimal idUserIamCorrente, BigDecimal idApplic,
            String nmTipoOrganiz, List<BigDecimal> idOrganizIamDaTogliere, boolean excludeEntiConvenzionati) {
        String queryStr = "SELECT abil " + "FROM UsrVAbilOrganiz abil "
                + "WHERE abil.id.idUserIam = :idUserIamCorrente " + "AND abil.idApplic = :idApplic ";
        if (nmTipoOrganiz != null) {
            queryStr += "AND abil.nmTipoOrganiz = :nmTipoOrganiz ";
        }
        if (idOrganizIamDaTogliere != null) {
            queryStr += "AND abil.id.idOrganizIam NOT IN (:idOrganizIamDaTogliere) ";
        }
        if (excludeEntiConvenzionati) {
            queryStr += "AND abil.nmOrganiz NOT LIKE 'Template %' "
                    + "AND NOT EXISTS (SELECT enteConvenzOrg FROM OrgEnteConvenzOrg enteConvenzOrg WHERE enteConvenzOrg.dtIniVal >= :today AND enteConvenzOrg.dtFineVal <= :today AND enteConvenzOrg.usrOrganizIam.idOrganizIam = abil.idOrganizIam) ";
        }
        queryStr += "ORDER BY abil.dlCompositoOrganiz ";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCorrente", idUserIamCorrente);
        q.setParameter("idApplic", idApplic);
        if (nmTipoOrganiz != null) {
            q.setParameter("nmTipoOrganiz", nmTipoOrganiz);
        }
        if (idOrganizIamDaTogliere != null) {
            q.setParameter("idOrganizIamDaTogliere", idOrganizIamDaTogliere);
        }
        if (excludeEntiConvenzionati) {
            q.setParameter("today", new Date());
        }
        List<UsrVAbilOrganiz> organizAbilitateList = q.getResultList();
        return organizAbilitateList;
    }

    public List<PrfRuolo> getRuoliDefaultByApplic(BigDecimal idApplic) {
        String queryStr = "SELECT uso_ruolo.prfRuolo " + "FROM UsrUsoRuoloUserDefault uso_ruolo "
                + "WHERE uso_ruolo.usrUsoUserApplic.aplApplic.idApplic = :idApplic "
                + "ORDER BY uso_ruolo.prfRuolo.nmRuolo ";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplic", longFrom(idApplic));
        List<PrfRuolo> ruoliDefaultList = q.getResultList();
        return ruoliDefaultList;
    }

    public List<PrfRuolo> getRuoliDichByApplic(BigDecimal idApplic) {
        String queryStr = "SELECT uso_ruolo.prfRuolo " + "FROM UsrUsoRuoloDich uso_ruolo "
                + "WHERE uso_ruolo.usrDichAbilOrganiz.usrUsoUserApplic.aplApplic.idApplic = :idApplic "
                + "ORDER BY uso_ruolo.prfRuolo.nmRuolo ";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplic", longFrom(idApplic));
        List<PrfRuolo> ruoliDefaultList = q.getResultList();
        return ruoliDefaultList;
    }

    public List<UsrVTreeOrganizIam> getUsrVTreeOrganizIamNoLastLevelList(long idUserIam, BigDecimal idApplicazione) {
        String queryStr = "SELECT usrVTreeOrgIam "
                + "FROM UsrVTreeOrganizIam usrVTreeOrgIam, UsrVAbilOrganizNolastLiv usrOrgIam "
                + "WHERE usrVTreeOrgIam.idOrganizIam = usrOrgIam.id.idOrganizIam " + "AND usrOrgIam.idApplic = :idappl "
                + "AND usrOrgIam.id.idUserIam = :idUserIam ";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idappl", idApplicazione);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        List<UsrVTreeOrganizIam> usrVTreeOrgIam = q.getResultList();
        return usrVTreeOrgIam;
    }

    /*
     * TOFIX: refactory del metodo senza utilizzo dello String.format. Per questioni di leggibilità è preferibile
     * scrivere interamente la query per ogni caso
     */
    public List<Object[]> getUsrVTreeOrganizIamAllOrgChildList(long idUserIamCor, BigDecimal idApplic, String tipoEnte,
            BigDecimal idEnte, String flAbilOrganizAutom, String flAbilOrganizEntiAutom, BigDecimal idRichiesta) {
        String nomeVista = "";
        String andClause = "";
        String idOrganizIam = "";
        String altriEnti = "";
        String rifRichiesta = "";
        BigDecimal idRichGestUser = null;
        BigDecimal idEntePerQuery = null;
        switch (tipoEnte) {
        case "AMMINISTRATORE":
            nomeVista = "UsrVAllorgchildByAmmin";
            idOrganizIam = "abil.id.idOrganizIamAmbEnte";
            break;
        case "CONSERVATORE":
            nomeVista = "UsrVAllorgchildByConserv";
            andClause = "AND abil.idEnteConserv = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIamAmbEnte";
            idEntePerQuery = idEnte;
            break;
        case "GESTORE":
            nomeVista = "UsrVAllorgchildByGestore";
            andClause = "AND abil.idEnteGestore = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIamAmbEnte";
            idEntePerQuery = idEnte;
            break;
        case "PRODUTTORE":
            nomeVista = "UsrVAllorgchildByProdut";
            andClause = "AND abil.idEnteProdut = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIamEnte";
            altriEnti = ",abil.idAppartCollegEnti";
            idEntePerQuery = idEnte;
            break;
        case "ORGANO_VIGILANZA":
            if (flAbilOrganizAutom == null || flAbilOrganizAutom.equals("0")) {
                nomeVista = "UsrVAllorgchildByVigil";
                rifRichiesta = "AND abil.idRichGestUser = :idRichGestUser ";
                altriEnti = ",abil.idVigilEnteProdut";
                idRichGestUser = idRichiesta;
            } else {
                nomeVista = "UsrVAllorgchildByVigilCor";
            }
            andClause = "AND abil.idEnteOrganoVigil = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIamEnte";
            idEntePerQuery = idEnte;
            break;
        case "FORNITORE_ESTERNO":
        case "SOGGETTO_ATTUATORE":
            if (flAbilOrganizEntiAutom == null || flAbilOrganizEntiAutom.equals("0")) {
                nomeVista = "UsrVAllorgchildByFornit";
                rifRichiesta = "AND abil.idRichGestUser = :idRichGestUser ";
                altriEnti = ",abil.idSuptEstEnteConvenz";
                idRichGestUser = idRichiesta;
                andClause = "AND abil.idEnteFornitEsterno = :idEnte ";
            } else {
                nomeVista = "UsrVAllorgchildByFornitCor";
                andClause = "AND abil.idEnteFornitEst = :idEnte ";
            }

            idOrganizIam = "abil.id.idOrganizIamEnte";
            idEntePerQuery = idEnte;
            break;
        default:
            break;
        }

        String queryStr = String.format(
                "SELECT usrVTreeOrgIam.idOrganizIam, usrVTreeOrgIam.dlCompositoOrganiz, abil.dsCausaleDich %4$s "
                        + " FROM UsrVTreeOrganizIam usrVTreeOrgIam " + ", %1$s abil  "
                        + "WHERE usrVTreeOrgIam.idOrganizIam = %3$s " + "AND abil.idApplic = :idApplic "
                        + "AND abil.id.idUserIamCor = :idUserIamCor %2$s %5$s "
                        + "ORDER BY usrVTreeOrgIam.dlCompositoOrganiz",
                nomeVista, andClause, idOrganizIam, altriEnti, rifRichiesta);

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idApplic", idApplic);
        if (idEntePerQuery != null) {
            q.setParameter("idEnte", idEntePerQuery);
        }
        if (idRichGestUser != null) {
            q.setParameter("idRichGestUser", idRichGestUser);
        }
        List<Object[]> usrVTreeOrgIam = q.getResultList();
        return usrVTreeOrgIam;
    }

    /*
     * TOFIX: refactory del metodo senza utilizzo dello String.format. Per questioni di leggibilità è preferibile
     * scrivere interamente la query per ogni caso
     */
    public List<Object[]> getUsrVTreeOrganizIamUnaOrgList(long idUserIamCor, BigDecimal idApplic, String tipoEnte,
            BigDecimal idEnte, String flAbilOrganizAutom, String flAbilOrganizEntiAutom, BigDecimal idRichiesta) {
        String nomeVista = "";
        String andClause = "";
        String idOrganizIam = "";
        String altriEnti = "";
        String rifRichiesta = "";
        BigDecimal idRichGestUser = null;
        BigDecimal idEntePerQuery = null;
        switch (tipoEnte) {
        case "AMMINISTRATORE":
            nomeVista = "UsrVUnaorgByAmmin";
            idOrganizIam = "abil.id.idOrganizIam";
            break;
        case "CONSERVATORE":
            nomeVista = "UsrVUnaorgByConserv";
            andClause = "AND abil.idEnteConserv = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIam";
            idEntePerQuery = idEnte;
            break;
        case "GESTORE":
            nomeVista = "UsrVUnaorgByGestore";
            andClause = "AND abil.idEnteGestore = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIam";
            idEntePerQuery = idEnte;
            break;
        case "PRODUTTORE":
            nomeVista = "UsrVUnaorgByProdut";
            andClause = "AND abil.idEnteProdut = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIamStrut";
            altriEnti = ",abil.idAppartCollegEnti";
            idEntePerQuery = idEnte;
            break;
        case "ORGANO_VIGILANZA":
            if (flAbilOrganizAutom == null || flAbilOrganizAutom.equals("0")) {
                nomeVista = "UsrVUnaorgByVigil";
                altriEnti = ",abil.idVigilEnteProdut";
                rifRichiesta = "AND abil.idRichGestUser = :idRichGestUser ";
                idRichGestUser = idRichiesta;
            } else {
                nomeVista = "UsrVUnaorgByVigilCor";
            }
            andClause = "AND abil.idEnteOrganoVigil = :idEnte ";
            idOrganizIam = "abil.id.idOrganizIamStrut";
            idEntePerQuery = idEnte;
            break;
        case "FORNITORE_ESTERNO":
        case "SOGGETTO_ATTUATORE":
            if (flAbilOrganizEntiAutom == null || flAbilOrganizEntiAutom.equals("0")) {
                nomeVista = "UsrVUnaorgByFornit";
                rifRichiesta = "AND abil.idRichGestUser = :idRichGestUser ";
                altriEnti = ",abil.idSuptEstEnteConvenz";
                idRichGestUser = idRichiesta;
                andClause = "AND abil.idEnteFornitEst = :idEnte ";
            } else {
                nomeVista = "UsrVUnaorgByFornitCor";
                andClause = "AND abil.idEnteFornitoreEst = :idEnte ";
            }
            idOrganizIam = "abil.id.idOrganizIamStrut";
            idEntePerQuery = idEnte;
            break;
        default:
            break;
        }

        String queryStr = String.format(
                "SELECT usrVTreeOrgIam.id.idOrganizIam, usrVTreeOrgIam.dlCompositoOrganiz, abil.dsCausaleDich %4$s "
                        + "FROM UsrVTreeOrganizIam usrVTreeOrgIam " + ", %1$s abil  "
                        + "WHERE usrVTreeOrgIam.id.idOrganizIam = %3$s " + "AND abil.idApplic = :idApplic "
                        + "AND abil.id.idUserIamCor = :idUserIamCor %2$s %5$s"
                        + "ORDER BY usrVTreeOrgIam.dlCompositoOrganiz",
                nomeVista, andClause, idOrganizIam, altriEnti, rifRichiesta);

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idApplic", idApplic);
        if (idEntePerQuery != null) {
            q.setParameter("idEnte", idEntePerQuery);
        }
        if (idRichGestUser != null) {
            q.setParameter("idRichGestUser", idRichGestUser);
        }
        List<Object[]> usrVTreeOrgIam = q.getResultList();
        return usrVTreeOrgIam;
    }

    public List<UsrVAbilOrganizNolastLiv> getUsrVAbilOrganizNolastLivList(long idUserIam, BigDecimal idApplic,
            String dlPathIdOrganizIam) {
        String queryStr = "SELECT abilOrganizNolastLiv FROM UsrVAbilOrganizNolastLiv abilOrganizNolastLiv "
                + "WHERE abilOrganizNolastLiv.idApplic = :idApplic "
                + "AND abilOrganizNolastLiv.id.idUserIam = :idUserIam "
                + "AND abilOrganizNolastLiv.dlPathIdOrganizIam LIKE :dlPathIdOrganizIam "
                + "ORDER BY abilOrganizNolastLiv.dlCompositoOrganiz";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplic", idApplic);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.setParameter("dlPathIdOrganizIam", dlPathIdOrganizIam + "%");
        List<UsrVAbilOrganizNolastLiv> abilOrganizNolastLiv = (List<UsrVAbilOrganizNolastLiv>) q.getResultList();
        return abilOrganizNolastLiv;
    }

    public List<UsrVAbilOrganiz> getUsrVAbilOrganizLastLevelList(long idUserIam, BigDecimal idApplicazione) {
        String queryStr = "SELECT usrOrgIam " + "FROM UsrVAbilOrganiz usrOrgIam "
                + "WHERE usrOrgIam.idApplic = :idappl " + "AND usrOrgIam.id.idUserIam = :idUserIam "
                + "ORDER BY usrOrgIam.dlCompositoOrganiz";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idappl", idApplicazione);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        List<UsrVAbilOrganiz> usrVAbilOrganiz = q.getResultList();
        return usrVAbilOrganiz;
    }

    public List<UsrVAbilOrganiz> getUsrVAbilOrganizLastLevelList(long idUserIam, BigDecimal idApplic,
            String dlPathIdOrganizIam) {
        String queryStr = "SELECT abilOrganiz FROM UsrVAbilOrganiz abilOrganiz "
                + "WHERE abilOrganiz.idApplic = :idApplic " + "AND abilOrganiz.id.idUserIam = :idUserIam "
                + "AND abilOrganiz.dlPathIdOrganizIam LIKE :dlPathIdOrganizIam "
                + "ORDER BY abilOrganiz.dlCompositoOrganiz";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idApplic", idApplic);
        q.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        q.setParameter("dlPathIdOrganizIam", dlPathIdOrganizIam + "%");
        List<UsrVAbilOrganiz> usrVAbilOrganiz = q.getResultList();
        return usrVAbilOrganiz;
    }

    public List<UsrVLisUser> getUsrVLisUserList(FiltriUtenti filtri, Set<BigDecimal> idEnteConvenzAppart,
            Date[] dateValidate, long idUserIamCorr, List<BigDecimal> idEntiConvenzionatiAmministratori)
            throws EMFError {
        return getUsrVLisUserList(new FiltriUtentiPlain(idEnteConvenzAppart, idUserIamCorr,
                idEntiConvenzionatiAmministratori, filtri.getNm_cognome_user().parse(),
                filtri.getNm_nome_user().parse(), filtri.getNm_userid().parse(), filtri.getCd_fisc().parse(),
                filtri.getNm_applic().parse(), filtri.getNm_ruolo_default().parse(), filtri.getFl_attivo().parse(),
                filtri.getDs_email().parse(), filtri.getTipo_user().parse(), filtri.getTi_stato_user().parse(),
                filtri.getDl_composito_organiz().parse(), filtri.getNm_ruolo_dich().parse(),
                filtri.getFl_err_replic().parse(), filtri.getId_sistema_versante().parse(),
                filtri.getId_amb_ente_convenz_appart().parse(), filtri.getId_ente_convenz_abil().parse(),
                filtri.getCd_rich_gest_user().parse(), (dateValidate != null ? dateValidate[0] : null),
                (dateValidate != null ? dateValidate[1] : null), filtri.getId_organiz_iam_rich().parse(),
                filtri.getCd_registro_rich_gest_user().parse(), filtri.getAa_rich_gest_user().parse(),
                filtri.getCd_key_rich_gest_user().parse(), filtri.getId_ente_convenz_rich().parse(),
                filtri.getFl_resp_ente_convenz().parse()));
    }

    /**
     * ritorna gli UsrVLisUser corrispondenti ai filtri di ricerca. Questo metodo indipendente da FiltriUtenti consente
     * un test automatico più "pulito"
     * 
     * @param filtriUsrVLisUser
     *            filtri
     * 
     * @return lista di {@link UsrVLisUser}
     */
    public List<UsrVLisUser> getUsrVLisUserList(FiltriUtentiPlain filtriUsrVLisUser) {
        String whereWord = "WHERE ";
        StringBuilder queryStr = new StringBuilder("SELECT DISTINCT new it.eng.saceriam.viewEntity.UsrVLisUser "
                + "(utenti.idUserIam, utenti.nmCognomeUser, utenti.nmNomeUser, utenti.nmUserid, utenti.cdFisc, utenti.nmRuoloDefault, "
                + "utenti.flAttivo,  utenti.flErrReplic, utenti.nmSistemaVersante, utenti.tipoUser, "
                + "utenti.nmEnteSiamAppart, utenti.idLastRichGestUser , utenti.cdLastRichGestUser, "
                + "utenti.dsListaAzioni , utenti.dtLastRichGestUser , utenti.flAzioniEvase, utenti.dsEmail, utenti.dsEmailSecondaria ) "
                + "FROM UsrVLisUser utenti ");

        String cognome = filtriUsrVLisUser.getCognome();
        if (cognome != null) {
            queryStr.append(whereWord).append("UPPER(utenti.nmCognomeUser) like :cognome");
            whereWord = " AND ";
        }
        String nome = filtriUsrVLisUser.getNome();
        if (nome != null) {
            queryStr.append(whereWord).append("UPPER(utenti.nmNomeUser) like :nome");
            whereWord = " AND ";
        }
        String userId = filtriUsrVLisUser.getUserId();
        if (userId != null) {
            queryStr.append(whereWord).append("UPPER(utenti.nmUserid) like :userid");
            whereWord = " AND ";
        }
        String cdFisc = filtriUsrVLisUser.getCodiceFiscale();
        if (cdFisc != null) {
            queryStr.append(whereWord).append("UPPER(utenti.cdFisc) like :cdFisc");
            whereWord = " AND ";
        }
        BigDecimal idApplic = filtriUsrVLisUser.getIdApplic();
        if (idApplic != null) {
            queryStr.append(whereWord).append("utenti.idApplic = :idapplic");
            whereWord = " AND ";
        }
        BigDecimal ruoloDefault = filtriUsrVLisUser.getRuoloDefault();
        if (ruoloDefault != null) {
            queryStr.append(whereWord).append("utenti.id.idRuoloDefault = :ruolodefault");
            whereWord = " AND ";
        }
        String attivo = filtriUsrVLisUser.getAttivo();
        if (attivo != null) {
            queryStr.append(whereWord).append("utenti.flAttivo = :attivo");
            whereWord = " AND ";
        }
        String dsEmail = filtriUsrVLisUser.getDsEmail();
        if (StringUtils.isNotBlank(dsEmail)) {
            queryStr.append(whereWord)
                    .append("(UPPER(utenti.dsEmail) like :dsEmail OR UPPER(utenti.dsEmailSecondaria) like :dsEmail) ");
            whereWord = " AND ";
        }
        List<String> tipoUser = filtriUsrVLisUser.getTipoUser();
        if (tipoUser != null && !tipoUser.isEmpty()) {
            queryStr.append(whereWord).append("utenti.tipoUser IN (:tipoUser)");
            whereWord = " AND ";
        }
        String statoUser = filtriUsrVLisUser.getStatoUser();
        if (StringUtils.isNotBlank(statoUser)) {
            queryStr.append(whereWord).append("utenti.tiStatoUser = :tiStatoUser");
            whereWord = " AND ";
        } else {
            queryStr.append(whereWord).append("utenti.tiStatoUser IN ('ATTIVO', 'DISATTIVO')");
            whereWord = " AND ";
        }
        BigDecimal idOrganizIam = filtriUsrVLisUser.getIdOrganizIam();
        if (idOrganizIam != null) {
            queryStr.append(whereWord).append("utenti.idOrganizIam = :idorganiziam");
            whereWord = " AND ";
        }
        BigDecimal ruoloDich = filtriUsrVLisUser.getRuoloDich();
        if (ruoloDich != null) {
            queryStr.append(whereWord).append("utenti.id.idRuoloDich = :ruolodich");
            whereWord = " AND ";
        }
        String errReplic = filtriUsrVLisUser.getErrReplic();
        if (errReplic != null) {
            queryStr.append(whereWord).append("utenti.flErrReplic = :errreplic");
            whereWord = " AND ";
        }

        BigDecimal idSistemaVersante = filtriUsrVLisUser.getIdSistemaVersante();
        if (idSistemaVersante != null) {
            queryStr.append(whereWord).append("utenti.idSistemaVersante = :idSistemaVersante");
            whereWord = " AND ";
        }
        List<BigDecimal> idAmbienteEnteConvenzAppart = filtriUsrVLisUser.getIdAmbienteEnteConvenzAppart();
        if (idAmbienteEnteConvenzAppart != null && !idAmbienteEnteConvenzAppart.isEmpty()) {
            queryStr.append(whereWord).append("utenti.idAmbEnteConvenzAppart IN (:idAmbienteEnteConvenzAppart)");
            whereWord = " AND ";
        }
        if (filtriUsrVLisUser.getIdEnteConvenzAppart() != null
                && !filtriUsrVLisUser.getIdEnteConvenzAppart().isEmpty()) {
            queryStr.append(whereWord).append("utenti.idEnteSiamAppart IN (:idEnteConvenzAppart)");
            whereWord = " AND ";
        } else if (filtriUsrVLisUser.getIdEntiConvenzionatiAmministratori() != null
                && !filtriUsrVLisUser.getIdEntiConvenzionatiAmministratori().isEmpty()) {
            queryStr.append(whereWord).append("utenti.idEnteSiamAppart IN (:idEnteConvenzAppart)");
        }
        BigDecimal idEnteConvenzAbil = filtriUsrVLisUser.getIdEnteConvenzAbil();
        if (idEnteConvenzAbil != null) {
            queryStr.append(whereWord).append("utenti.idEnteConvenzAbil = :idEnteConvenzAbil");
            whereWord = " AND ";
        }
        String cdRichGestUser = filtriUsrVLisUser.getCdRichGestUser();
        if (StringUtils.isNotBlank(cdRichGestUser)) {
            queryStr.append(whereWord).append("utenti.cdRichGestUser = :cdRichGestUser");
            whereWord = " AND ";
        }
        Date dataOrarioDa = filtriUsrVLisUser.getDataOrarioDa();
        Date dataOrarioA = filtriUsrVLisUser.getDataOrarioA();
        if ((dataOrarioDa != null) && (dataOrarioA != null)) {
            queryStr.append(whereWord).append("utenti.dtRichGestUser between :datada AND :dataa");
            whereWord = " AND ";
        }
        BigDecimal idOrganizIamRich = filtriUsrVLisUser.getIdOrganizIamRich();
        if (idOrganizIamRich != null) {
            queryStr.append(whereWord).append("utenti.idOrganizIamRich = :idOrganizIamRich");
            whereWord = " AND ";
        }
        String cdRegistroRichGestUser = filtriUsrVLisUser.getCdRegistroRichGestUser();
        if (StringUtils.isNotBlank(cdRegistroRichGestUser)) {
            queryStr.append(whereWord).append("utenti.cdRegistroRichGestUser = :cdRegistroRichGestUser");
            whereWord = " AND ";
        }
        BigDecimal aaRichGestUser = filtriUsrVLisUser.getAaRichGestUser();
        if (aaRichGestUser != null) {
            queryStr.append(whereWord).append("utenti.aaRichGestUser = :aaRichGestUser");
            whereWord = " AND ";
        }
        String cdKeyRichGestUser = filtriUsrVLisUser.getCdKeyRichGestUser();
        if (StringUtils.isNotBlank(cdKeyRichGestUser)) {
            queryStr.append(whereWord).append("utenti.cdKeyRichGestUser = :cdKeyRichGestUser");
            whereWord = " AND ";
        }
        BigDecimal idEnteConvenzRich = filtriUsrVLisUser.getIdEnteConvenzRich();
        if (idEnteConvenzRich != null) {
            queryStr.append(whereWord).append("utenti.idEnteRich = :idEnteConvenzRich");
            whereWord = " AND ";
        }
        String flRespEnteConvenz = filtriUsrVLisUser.getFlRespEnteConvenz();
        if (StringUtils.isNotBlank(flRespEnteConvenz)) {
            queryStr.append(whereWord).append("utenti.flRespEnteConvenz = :flRespEnteConvenz");
            whereWord = " AND ";
        }

        queryStr.append(whereWord).append("utenti.idUserIamCorr = :idUserIamCorr ");
        queryStr.append(" ORDER BY utenti.nmCognomeUser, utenti.nmNomeUser");

        // CREO LA QUERY ATTRAVERSO L'ENTITY MANAGER
        Query query = getEntityManager().createQuery(queryStr.toString());
        if (cognome != null) {
            query.setParameter("cognome", "%" + cognome.toUpperCase() + "%");
        }
        if (nome != null) {
            query.setParameter("nome", "%" + nome.toUpperCase() + "%");
        }
        if (userId != null) {
            query.setParameter("userid", "%" + userId.toUpperCase() + "%");
        }
        if (cdFisc != null) {
            query.setParameter("cdFisc", cdFisc.toUpperCase() + "%");
        }
        if (idApplic != null) {
            query.setParameter("idapplic", idApplic);
        }
        if (ruoloDefault != null) {
            query.setParameter("ruolodefault", ruoloDefault);
        }
        if (attivo != null) {
            query.setParameter("attivo", attivo);
        }
        if (StringUtils.isNotBlank(dsEmail)) {
            query.setParameter("dsEmail", "%" + dsEmail.toUpperCase() + "%");
        }
        if (tipoUser != null && !tipoUser.isEmpty()) {
            query.setParameter("tipoUser", tipoUser);
        }
        if (idOrganizIam != null) {
            query.setParameter("idorganiziam", idOrganizIam);
        }
        if (ruoloDich != null) {
            query.setParameter("ruolodich", ruoloDich);
        }
        if (errReplic != null) {
            query.setParameter("errreplic", errReplic);
        }

        if (idSistemaVersante != null) {
            query.setParameter("idSistemaVersante", idSistemaVersante);
        }
        if (idAmbienteEnteConvenzAppart != null && !idAmbienteEnteConvenzAppart.isEmpty()) {
            query.setParameter("idAmbienteEnteConvenzAppart", idAmbienteEnteConvenzAppart);
        }
        if (filtriUsrVLisUser.getIdEnteConvenzAppart() != null
                && !filtriUsrVLisUser.getIdEnteConvenzAppart().isEmpty()) {
            query.setParameter("idEnteConvenzAppart", filtriUsrVLisUser.getIdEnteConvenzAppart());
        } else if (filtriUsrVLisUser.getIdEntiConvenzionatiAmministratori() != null
                && !filtriUsrVLisUser.getIdEntiConvenzionatiAmministratori().isEmpty()) {
            query.setParameter("idEnteConvenzAppart", filtriUsrVLisUser.getIdEntiConvenzionatiAmministratori());
        }
        if (idEnteConvenzAbil != null) {
            query.setParameter("idEnteConvenzAbil", idEnteConvenzAbil);
        }
        if (StringUtils.isNotBlank(cdRichGestUser)) {
            query.setParameter("cdRichGestUser", cdRichGestUser);
        }
        if ((dataOrarioDa != null) && (dataOrarioA != null)) {
            query.setParameter("datada", dataOrarioDa);
            query.setParameter("dataa", dataOrarioA);
        }
        if (idOrganizIamRich != null) {
            query.setParameter("idOrganizIamRich", idOrganizIamRich);
        }
        if (StringUtils.isNotBlank(cdRegistroRichGestUser)) {
            query.setParameter("cdRegistroRichGestUser", cdRegistroRichGestUser);
        }
        if (aaRichGestUser != null) {
            query.setParameter("aaRichGestUser", aaRichGestUser);
        }
        if (StringUtils.isNotBlank(cdKeyRichGestUser)) {
            query.setParameter("cdKeyRichGestUser", cdKeyRichGestUser);
        }
        if (idEnteConvenzRich != null) {
            query.setParameter("idEnteConvenzRich", idEnteConvenzRich);
        }
        if (StringUtils.isNotBlank(statoUser)) {
            query.setParameter("tiStatoUser", statoUser);
        }
        if (StringUtils.isNotBlank(flRespEnteConvenz)) {
            query.setParameter("flRespEnteConvenz", flRespEnteConvenz);
        }

        query.setParameter("idUserIamCorr", bigDecimalFrom(filtriUsrVLisUser.getIdUserIamCorr()));

        // ESEGUO LA QUERY E PIAZZO I RISULTATI IN UNA LISTA DI UTENTI
        List<UsrVLisUser> listaUtenti = (List<UsrVLisUser>) query.getResultList();

        return listaUtenti;
    }

    public List<UsrVLisUserEnteConvenz> retrieveUsrVLisUserEnteConvenz(BigDecimal idEnteConvenz,
            List<String> tiStatoUser, List<String> tipoUser, Set<BigDecimal> idUserIamEsclusi) {
        StringBuilder queryStr = new StringBuilder("SELECT new it.eng.saceriam.viewEntity.UsrVLisUserEnteConvenz "
                + "(utenti.id.idEnteConvenz, utenti.id.idUserIam, utenti.nmCognomeUser, utenti.nmNomeUser, utenti.nmUserid, utenti.nmApplic, utenti.nmRuoloDefault, utenti.dlCompositoOrganiz, "
                + "utenti.tiStatoUser, utenti.tipoUser, utenti.keyRichGestUser, utenti.dsListaAzioni, utenti.dtRichGestUser, utenti.flAzioniEvase) "
                + "FROM UsrVLisUserEnteConvenz utenti " + "WHERE utenti.id.idEnteConvenz = :idEnteConvenz ");

        if (tipoUser != null) {
            queryStr.append("AND utenti.tipoUser IN (:tipoUser) ");
        }
        if (tiStatoUser != null) {
            queryStr.append("AND utenti.tiStatoUser IN (:tiStatoUser) ");
        }
        if (idUserIamEsclusi != null && !idUserIamEsclusi.isEmpty()) {
            queryStr.append("AND utenti.id.idUserIam NOT IN (:idUserIamEsclusi) ");
        }
        queryStr.append("ORDER BY utenti.nmCognomeUser, utenti.nmNomeUser ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idEnteConvenz", idEnteConvenz);
        if (tipoUser != null) {
            query.setParameter("tipoUser", tipoUser);
        }
        if (tiStatoUser != null) {
            query.setParameter("tiStatoUser", tiStatoUser);
        }
        if (idUserIamEsclusi != null && !idUserIamEsclusi.isEmpty()) {
            query.setParameter("idUserIamEsclusi", idUserIamEsclusi);
        }
        List<UsrVLisUserEnteConvenz> utenti = query.getResultList();
        return utenti;
    }

    public List<UsrUser> retrieveUsrUserEnteSiamAppart(BigDecimal idEnteSiam, List<String> tiStatoUser,
            List<String> tipoUser) {
        StringBuilder queryStr = new StringBuilder("SELECT DISTINCT user FROM UsrStatoUser statoUser, UsrUser user "
                + "JOIN user.orgEnteSiam enteSiam " + "WHERE enteSiam.idEnteSiam = :idEnteSiam "
                + "AND user.idStatoUserCor = statoUser.idStatoUser ");
        if (tiStatoUser != null) {
            queryStr.append("AND statoUser.tiStatoUser IN (:tiStatoUser) ");
        }
        if (tipoUser != null) {
            queryStr.append("AND user.tipoUser IN (:tipoUser) ");
        }
        queryStr.append("ORDER BY user.nmCognomeUser, user.nmNomeUser ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        if (tiStatoUser != null) {
            query.setParameter("tiStatoUser", tiStatoUser);
        }
        if (tipoUser != null) {
            query.setParameter("tipoUser", tipoUser);
        }
        List<UsrUser> utenti = query.getResultList();
        return utenti;
    }

    public List<UsrUser> retrieveUsrUserEnteSiamAppart(BigDecimal idEnteSiam,
            Map<String, List<TiEnteConvenz>> tiEnteConvenz, Map<String, List<String>> tiStatoUser,
            Map<String, List<String>> tipoUser) {
        StringBuilder queryStr = new StringBuilder("SELECT DISTINCT user FROM UsrStatoUser statoUser, UsrUser user "
                + "JOIN user.orgEnteSiam enteSiam " + "WHERE user.idStatoUserCor = statoUser.idStatoUser ");
        if (idEnteSiam != null) {
            queryStr.append("AND enteSiam.idEnteSiam = :idEnteSiam  ");
        }
        if (tiEnteConvenz != null) {
            String keyClause = tiEnteConvenz.keySet().stream().findFirst().get();
            queryStr.append("AND enteSiam.tiEnteConvenz ").append(keyClause).append(" :tiEnteConvenz ");
        }
        if (tiStatoUser != null) {
            String keyClause = tiStatoUser.keySet().stream().findFirst().get();
            queryStr.append("AND statoUser.tiStatoUser ").append(keyClause).append(" :tiStatoUser ");
        }
        if (tipoUser != null) {
            String keyClause = tipoUser.keySet().stream().findFirst().get();
            queryStr.append("AND user.tipoUser ").append(keyClause).append(" :tipoUser ");
        }
        queryStr.append("ORDER BY user.nmCognomeUser, user.nmNomeUser ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idEnteSiam != null) {
            query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        }
        if (tiEnteConvenz != null) {
            List<TiEnteConvenz> values = tiEnteConvenz.values().stream().findFirst().get();
            query.setParameter("tiEnteConvenz", values);
        }
        if (tiStatoUser != null) {
            List<String> values = tiStatoUser.values().stream().findFirst().get();
            query.setParameter("tiStatoUser", values);
        }
        if (tipoUser != null) {
            List<String> values = tipoUser.values().stream().findFirst().get();
            query.setParameter("tipoUser", values);
        }
        List<UsrUser> utenti = query.getResultList();
        return utenti;
    }

    public List<BigDecimal> retrieveIdOrganizIamUserEnteSiamAppartList(BigDecimal idEnteSiam, List<String> tiStatoUser,
            List<String> tipoUser) {
        StringBuilder queryStr = new StringBuilder("SELECT DISTINCT abilOrganiz.id.idOrganizIam "
                + "FROM UsrVAbilOrganiz abilOrganiz, UsrStatoUser statoUser, UsrUser user "
                + "JOIN user.orgEnteSiam enteSiam WHERE enteSiam.idEnteSiam = :idEnteSiam "
                + "AND abilOrganiz.id.idUserIam = user.idUserIam "
                + "AND user.idStatoUserCor = statoUser.idStatoUser ");
        if (tiStatoUser != null) {
            queryStr.append("AND statoUser.tiStatoUser NOT IN (:tiStatoUser) ");
        }
        if (tipoUser != null) {
            queryStr.append("AND user.tipoUser IN (:tipoUser) ");
        }
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        if (tiStatoUser != null) {
            query.setParameter("tiStatoUser", tiStatoUser);
        }
        if (tipoUser != null) {
            query.setParameter("tipoUser", tipoUser);
        }
        List<BigDecimal> utenti = query.getResultList();
        return utenti;
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
        List<PrfDichAutor> dichs = q.getResultList();
        return dichs;
    }

    public AplApplic getAplApplicById(BigDecimal idApplic) {
        return getEntityManager().find(AplApplic.class, idApplic.longValue());
    }

    public AplApplic getAplApplicByName(String nmApplic) {
        String queryStr = "SELECT applic FROM AplApplic applic " + "WHERE applic.nmApplic = :nmApplic ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("nmApplic", nmApplic);
        List<AplApplic> applic = query.getResultList();
        if (applic != null && applic.size() == 1) {
            return applic.get(0);
        } else {
            return null;
        }
    }

    public AplApplic getAplApplic(String name) {
        String queryStr = "SELECT applic FROM AplApplic applic WHERE applic.nmApplic = :nomeappl";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nomeappl", name);
        AplApplic applic = (AplApplic) q.getSingleResult();
        return applic;
    }

    public UsrUser getUsrUser(BigDecimal idUser) {
        return getEntityManager().find(UsrUser.class, idUser.longValue());
    }

    public UsrUser getUsrUserByNmUserid(String nmUserid) {
        String queryStr = "SELECT user FROM UsrUser user " + "WHERE user.nmUserid = :nmUserid ";
        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("nmUserid", nmUserid);
        List<UsrUser> user = (List<UsrUser>) q.getResultList();
        if (!user.isEmpty()) {
            return user.get(0);
        }
        return null;
    }

    public List<UsrIndIpUser> getUsrIndIpUserList(BigDecimal idUserIam) {
        String queryStr = "SELECT uiia FROM UsrIndIpUser uiia "
                + "WHERE uiia.usrUser.idUserIam = :idUserIam ORDER BY uiia.cdIndIpUser ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<UsrIndIpUser> list = query.getResultList();
        return list;
    }

    public List<UsrUsoUserApplic> getUsrUsoUserApplicList(BigDecimal idUserIam) {
        String queryStr = "SELECT uuua FROM UsrUsoUserApplic uuua "
                + "WHERE uuua.usrUser.idUserIam = :idUserIam ORDER BY uuua.aplApplic.nmApplic ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<UsrUsoUserApplic> list = query.getResultList();
        return list;
    }

    public List<Long> getIdUsrUsoUserApplicList(BigDecimal idUserIam) {
        String queryStr = "SELECT uuua.idUsoUserApplic FROM UsrUsoUserApplic uuua "
                + "WHERE uuua.usrUser.idUserIam = :idUserIam ORDER BY uuua.aplApplic.nmApplic ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<Long> list = query.getResultList();
        return list;
    }

    public List<UsrUsoRuoloUserDefault> getUsrUsoRuoloUserDefaultList(Set<BigDecimal> uuuaSet) {
        List<UsrUsoRuoloUserDefault> list = new ArrayList<>();
        if (!uuuaSet.isEmpty()) {
            String queryStr = "SELECT uurud FROM UsrUsoRuoloUserDefault uurud "
                    + "WHERE uurud.usrUsoUserApplic.idUsoUserApplic IN (:uuuaSet) "
                    + "ORDER BY uurud.usrUsoUserApplic.aplApplic.nmApplic, uurud.prfRuolo.nmRuolo ";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("uuuaSet", longListFrom(uuuaSet));
            list = query.getResultList();
        }
        return list;
    }

    public List<UsrDichAbilOrganiz> getUsrDichAbilOrganizList(Set<BigDecimal> uuuaSet) {
        List<UsrDichAbilOrganiz> list = new ArrayList<>();
        if (!uuuaSet.isEmpty()) {

            String queryStr = "SELECT dich_abil " + "FROM UsrDichAbilOrganiz dich_abil "
                    + "WHERE dich_abil.usrUsoUserApplic.idUsoUserApplic IN (:uuuaSet) ";

            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("uuuaSet", longListFrom(uuuaSet));
            list = query.getResultList();
        }
        return list;
    }

    public List<UsrDichAbilDati> getUsrDichAbilDatiList(Set<BigDecimal> uuuaSet) {
        List<UsrDichAbilDati> list = new ArrayList<>();
        if (!uuuaSet.isEmpty()) {
            String queryStr = "SELECT dich_abil "
                    + "FROM UsrDichAbilDati dich_abil JOIN dich_abil.usrUsoUserApplic user_applic JOIN user_applic.usrUser user "
                    + "WHERE dich_abil.usrUsoUserApplic.idUsoUserApplic IN (:uuuaSet) ";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("uuuaSet", longListFrom(uuuaSet));
            list = query.getResultList();
        }
        return list;
    }

    /**
     * Verifica l'esistenza dello username nel database
     *
     * @param userName
     *            user name
     * 
     * @return true se l'utente esiste già
     */
    public boolean checkUserExists(String userName) {
        Query query = getEntityManager().createQuery("SELECT user FROM UsrUser user WHERE user.nmUserid = :username");
        query.setParameter("username", userName);
        List<UsrUser> utenti = query.getResultList();
        if (utenti.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifica l'esistenza dello username nel database
     *
     * @param userName
     *            user name
     * @param nmCognomeUser
     *            cognome
     * @param nmNomeUser
     *            nome
     * @param cdFisc
     *            codice fiscale
     * @param dsEmail
     *            email
     * @param flContrIp
     *            flag 1/0 (true/false) controllo ip
     * 
     * @return true se l'utente esiste
     */
    public boolean checkUserExists(String userName, String nmCognomeUser, String nmNomeUser, String cdFisc,
            String dsEmail, String flContrIp) {
        Query query = getEntityManager().createQuery(
                "SELECT user FROM UsrUser user WHERE user.nmUserid = :username AND user.nmCognomeUser = :nmCognomeUser AND user.nmNomeUser = :nmNomeUser AND user.cdFisc = :cdFisc AND user.dsEmail = :dsEmail AND user.flContrIp = :flContrIp");
        query.setParameter("username", userName);
        query.setParameter("nmCognomeUser", nmCognomeUser);
        query.setParameter("nmNomeUser", nmNomeUser);
        query.setParameter("cdFisc", cdFisc);
        query.setParameter("dsEmail", dsEmail);
        query.setParameter("flContrIp", flContrIp);
        List<UsrUser> utenti = query.getResultList();
        if (utenti.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Data in input un'organizzazione, il metodo ritorna la lista dei suoi figli (di qualunque livello)
     *
     * (Si poteva anche usare la vista UsrVTreeOrganizIam)
     *
     * @param idOrganizIam
     *            id organizzazione
     * 
     * @return il tableBean contenente i figli dell'organizzazione specificata
     */
    public List<UsrOrganizIam> getUsrOrganizIamChilds(BigDecimal idOrganizIam) {
        String queryStr = "SELECT * FROM USR_ORGANIZ_IAM organiz " + "START WITH organiz.id_organiz_iam = :idorganiziam"
                + " CONNECT BY PRIOR organiz.id_organiz_iam = organiz.id_organiz_iam_padre";
        Query q = getEntityManager().createNativeQuery(queryStr, UsrOrganizIam.class);
        q.setParameter("idorganiziam", longFrom(idOrganizIam));
        List<UsrOrganizIam> organizs = q.getResultList();
        return organizs;
    }

    /**
     * Dato in input l'id organizzazione di una organizzazione ne vengono restituiti tutti i figli
     *
     * @param idOrganizIam
     *            id organizzazione IAM
     * 
     * @return lista elementi di tipo {@link UsrVTreeOrganizIam}
     */
    public List<UsrVTreeOrganizIam> getOrganizIamChilds(BigDecimal idOrganizIam) {
        String queryStr = "SELECT usrVTreeOrgIam " + "FROM UsrVTreeOrganizIam usrVTreeOrgIam "
                + "WHERE usrVTreeOrgIam.dlPathIdOrganizIam LIKE :idOrganizIam "
                + "AND usrVTreeOrgIam.flLastLivello = 1 " + "ORDER BY usrVTreeOrgIam.dlCompositoOrganiz ";

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idOrganizIam", "%" + idOrganizIam + "%");
        List<UsrVTreeOrganizIam> usrVTreeOrgIam = q.getResultList();
        return usrVTreeOrgIam;
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

    public List<AplClasseTipoDato> getAplClasseTipoDatoList(BigDecimal idApplic) {
        Query query = getEntityManager().createQuery("SELECT aplClasse FROM AplClasseTipoDato aplClasse "
                + "WHERE aplClasse.aplApplic.idApplic = :idApplic ");

        query.setParameter("idApplic", longFrom(idApplic));
        List<AplClasseTipoDato> listaClasse = query.getResultList();
        return listaClasse;
    }

    public List<UsrVAbilDati> getUsrVAbilDatiList(long idUserIam, String nmClasseTipoDato, BigDecimal idApplic,
            BigDecimal idOrganizIam) {
        Query query = getEntityManager().createQuery(
                "SELECT tipoDato FROM UsrVAbilDati tipoDato " + "WHERE tipoDato.nmClasseTipoDato = :nmClasseTipoDato "
                        + "AND tipoDato.idOrganizIam = :idOrganizIam " + "AND tipoDato.idApplic = :idApplic "
                        + "AND tipoDato.id.idUserIam = :idUserIam " + "ORDER BY tipoDato.nmTipoDato");

        query.setParameter("nmClasseTipoDato", nmClasseTipoDato);
        query.setParameter("idOrganizIam", idOrganizIam);
        query.setParameter("idUserIam", bigDecimalFrom(idUserIam));
        query.setParameter("idApplic", idApplic);
        List<UsrVAbilDati> listaTipoDatoIam = query.getResultList();
        return listaTipoDatoIam;
    }

    public List<UsrTipoDatoIam> getUsrTipoDatoIamList(BigDecimal idApplic, String nmClasseTipoDato,
            BigDecimal idOrganizIam) {
        Query query = getEntityManager().createQuery("SELECT tipoDato FROM UsrTipoDatoIam tipoDato "
                + "WHERE tipoDato.aplClasseTipoDato.nmClasseTipoDato = :nmClasseTipoDato "
                + "AND tipoDato.aplClasseTipoDato.aplApplic.idApplic = :idApplic "
                + "AND tipoDato.usrOrganizIam.idOrganizIam = :idOrganizIam");

        query.setParameter("idApplic", longFrom(idApplic));
        query.setParameter("nmClasseTipoDato", nmClasseTipoDato);
        query.setParameter("idOrganizIam", longFrom(idOrganizIam));
        List<UsrTipoDatoIam> listaTipoDatoIam = query.getResultList();
        return listaTipoDatoIam;
    }

    public List<UsrVAbilDati> getUsrVAbilDatiListByApplicClasseTipoDatoUser(BigDecimal idApplic,
            String nmClasseTipoDato, BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT tipoDato FROM UsrVAbilDati tipoDato "
                + "WHERE tipoDato.nmClasseTipoDato = :nmClasseTipoDato " + "AND tipoDato.idApplic = :idApplic "
                + "AND tipoDato.id.idUserIam = :idUserIam " + "ORDER BY tipoDato.nmTipoDato");

        query.setParameter("nmClasseTipoDato", nmClasseTipoDato);
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idApplic", idApplic);
        List<UsrVAbilDati> listaTipoDatoIam = query.getResultList();
        return listaTipoDatoIam;
    }

    public List<Object[]> getRuoliAbilOrganizList(BigDecimal idDichAbilOrganiz) {
        Query query = getEntityManager()
                .createQuery("SELECT p, r.idUsoRuoloDich " + "FROM UsrUsoRuoloDich r JOIN r.prfRuolo p "
                        + "WHERE r.usrDichAbilOrganiz.idDichAbilOrganiz = :idDichAbilOrganiz " + "ORDER BY p.nmRuolo ");
        query.setParameter("idDichAbilOrganiz", longFrom(idDichAbilOrganiz));
        return query.getResultList();
    }

    public List<UsrVLisUsoRuoloDich> getUsrVLisUsoRuoloDichList(BigDecimal idDichAbilOrganiz) {
        Query query = getEntityManager().createQuery("SELECT lisUsoRuoloDich FROM UsrVLisUsoRuoloDich lisUsoRuoloDich "
                + "WHERE lisUsoRuoloDich.idDichAbilOrganiz = :idDichAbilOrganiz "
                + "ORDER BY lisUsoRuoloDich.dlCompositoOrganiz, lisUsoRuoloDich.nmRuolo ");
        query.setParameter("idDichAbilOrganiz", idDichAbilOrganiz);
        return query.getResultList();
    }

    public UsrVVisDichAbil getUsrVVisDichAbil(BigDecimal idDichAbilOrg) {
        return getEntityManager().find(UsrVVisDichAbil.class, idDichAbilOrg);
    }

    public List<UsrVLisSched> getUsrVLisSchedList(ApplEnum.NmJob nmJob, Date[] dateValidate,
            boolean soloSbloccoRepliche) {
        StringBuilder queryStr = new StringBuilder("SELECT u FROM UsrVLisSched u " + "WHERE u.nmJob LIKE :nmJob ");

        Date data_orario_da = (dateValidate != null ? dateValidate[0] : null);
        Date data_orario_a = (dateValidate != null ? dateValidate[1] : null);

        /* Inserimento nella query del filtro data già impostato con data e ora */
        if ((data_orario_da != null) && (data_orario_a != null)) {
            queryStr.append("AND (u.dtRegLogJobIni between :datada AND :dataa) ");
        }
        if (soloSbloccoRepliche) {
            queryStr.append(
                    "AND u.tiBlocco IS NOT NULL AND (u.dlMsgErr IS NULL OR u.dlMsgErr != 'Job terminato forzatamente per sblocco repliche in corso non eseguite entro tempo limite')");
        }

        queryStr.append("ORDER BY u.dtRegLogJobIni DESC ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("nmJob", nmJob.name() + '%');

        if (data_orario_da != null && data_orario_a != null) {
            query.setParameter("datada", data_orario_da, TemporalType.TIMESTAMP);
            query.setParameter("dataa", data_orario_a, TemporalType.TIMESTAMP);
        }

        List<UsrVLisSched> listaSched = query.getResultList();
        return listaSched;
    }

    public UsrVVisLastSched getUsrVVisLastSched(ApplEnum.NmJob nmJob) {
        Query query = getEntityManager().createQuery("SELECT u FROM UsrVVisLastSched u " + "WHERE u.nmJob = :nmJob");
        query.setParameter("nmJob", nmJob.name());
        List<UsrVVisLastSched> utenti = query.getResultList();
        if (utenti != null && !utenti.isEmpty()) {
            return utenti.get(0);
        } else {
            return null;
        }
    }

    public List<UsrVLisUserReplic> getUsrVLisUserReplicList(FiltriReplica filtri, Date dataValidata,
            boolean isFromDettaglioUtente) throws EMFError {
        return getUsrVLisUserReplicList(dataValidata, isFromDettaglioUtente, filtri.getNm_userid().parse(),
                filtri.getNm_applic().parse(), filtri.getTi_oper_replic().parse(), filtri.getTi_stato_replic().parse());
    }

    /**
     * ritorna gli UsrVLisUserReplic corrispondenti ai filtri di ricerca. Questo metodo indipendente da FiltriReplica
     * consente un test automatico più "pulito"
     * 
     * @param dataValidata
     *            data validata
     * @param isFromDettaglioUtente
     *            true se proviene dal dettaglio utente
     * @param userId
     *            user id
     * @param idApplic
     *            id applicazione
     * @param tiOperReplic
     *            tipo operazione replica
     * @param tiStatoReplic
     *            tipo stato replica
     * 
     * @return lista di {@link UsrVLisUserReplic}
     */
    public List<UsrVLisUserReplic> getUsrVLisUserReplicList(Date dataValidata, boolean isFromDettaglioUtente,
            String userId, BigDecimal idApplic, String tiOperReplic, List<String> tiStatoReplic) {
        StringBuilder queryStr = new StringBuilder("SELECT u FROM UsrVLisUserReplic u ");
        String whereWord = "WHERE ";

        /* Inserimento nella query dei filtri */
        if (userId != null) {
            queryStr.append(whereWord).append("UPPER(u.nmUserid) LIKE :userId ");
            whereWord = "AND ";
        }

        if (idApplic != null) {
            queryStr.append(whereWord).append("u.idApplic = :idApplic ");
            whereWord = "AND ";
        }

        if (tiOperReplic != null) {
            queryStr.append(whereWord).append("u.tiOperReplic = :tiOperReplic ");
            whereWord = "AND ";
        }

        if (tiStatoReplic != null && !tiStatoReplic.isEmpty()) {
            queryStr.append(whereWord).append("u.tiStatoReplic IN (:tiStatoReplic) ");
            whereWord = "AND ";
        }

        if (dataValidata != null) {
            queryStr.append(whereWord).append("u.dtLogUserDaReplic > :dataValidata ");
        }
        queryStr.append("ORDER BY u.nmUserid, u.nmApplic, u.dtLogUserDaReplic DESC ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        if (userId != null) {
            query.setParameter("userId",
                    isFromDettaglioUtente ? userId.toUpperCase() : "%" + userId.toUpperCase() + "%");
        }
        if (idApplic != null) {
            query.setParameter("idApplic", idApplic);
        }
        if (tiOperReplic != null) {
            query.setParameter("tiOperReplic", tiOperReplic);
        }
        if (tiStatoReplic != null && !tiStatoReplic.isEmpty()) {
            query.setParameter("tiStatoReplic", tiStatoReplic);
        }
        if (dataValidata != null) {
            query.setParameter("dataValidata", dataValidata);
        }

        List<UsrVLisUserReplic> listaReplica = query.getResultList();
        return listaReplica;
    }

    public boolean hasErroriReplica(BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT NVL(count(u), 0) FROM LogUserDaReplic u "
                + "WHERE u.idUserIam = :idUserIam "
                + "AND u.tiStatoReplic IN ('REPLICA_IN_ERRORE', 'REPLICA_IN_TIMEOUT', 'REPLICA_NON_POSSIBILE') ");

        query.setParameter("idUserIam", idUserIam);
        Long numErrori = Long.class.cast(query.getSingleResult());
        return numErrori > 0L;
    }

    public int aggiornaReplicheInCorso(BigDecimal idLogJob) {
        Query query = getEntityManager().createQuery(
                "UPDATE LogUserDaReplic log SET log.logJob = NULL , log.tiStatoReplic = 'REPLICA_IN_ERRORE' WHERE log.logJob.idLogJob = :idLogJob AND log.tiStatoReplic = 'REPLICA_IN_CORSO'");
        query.setParameter("idLogJob", longFrom(idLogJob));
        return query.executeUpdate();
    }

    public List<UsrDichAbilEnteConvenz> getUsrDichAbilEnteConvenz(BigDecimal idUserIam) {
        List<UsrDichAbilEnteConvenz> list = new ArrayList<>();
        if (idUserIam != null) {
            String queryStr = "SELECT dichAbilEnteConvenz FROM UsrDichAbilEnteConvenz dichAbilEnteConvenz "
                    + "JOIN dichAbilEnteConvenz.usrUser user "
                    + "LEFT JOIN dichAbilEnteConvenz.orgAmbienteEnteConvenz ambienteEnteConvenz "
                    + "LEFT JOIN dichAbilEnteConvenz.orgEnteSiam enteSiam " + "WHERE user.idUserIam = :idUserIam "
                    + "ORDER BY ambienteEnteConvenz.nmAmbienteEnteConvenz ASC, enteSiam.nmEnteSiam ASC ";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("idUserIam", idUserIam.longValue());
            list = (List<UsrDichAbilEnteConvenz>) query.getResultList();
        }
        return list;
    }

    public List<UsrDichAbilEnteConvenz> getUsrDichAbilEnteConvenzUnEnteAttivi(BigDecimal idEnteConvenz) {
        List<UsrDichAbilEnteConvenz> list = new ArrayList<>();
        if (idEnteConvenz != null) {
            String queryStr = "SELECT dichAbilEnteConvenz FROM UsrDichAbilEnteConvenz dichAbilEnteConvenz, UsrStatoUser statoUser "
                    + "JOIN dichAbilEnteConvenz.usrUser user " + "JOIN dichAbilEnteConvenz.orgEnteSiam enteSiam "
                    + "WHERE user.idStatoUserCor = statoUser.idStatoUser " + "AND enteSiam.idEnteSiam = :idEnteConvenz "
                    + "AND dichAbilEnteConvenz.tiScopoDichAbilEnte = 'UN_ENTE' "
                    + "AND statoUser.tiStatoUser = 'ATTIVO'";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
            list = (List<UsrDichAbilEnteConvenz>) query.getResultList();
        }
        return list;
    }
    // login
    // /**
    // * Restituisce la lista dei record di login utente in base ai filtri di ricerca in ingresso
    // *
    // * @param dtEventoDa
    // * data evento da
    // * @param dtEventoA
    // * data evento a
    // * @param nmUserid
    // * user id
    // * @param tipiEventoSet
    // * tipo evento
    // * @param inclUtentiAutomi
    // * automi
    // *
    // * @return la lista dei record di login utente
    // */
    // public List<LogEventoLoginUser> retrieveLogEventoLoginUserList(Date dtEventoDa, Date dtEventoA, String nmUserid,
    // Set<String> tipiEventoSet, String inclUtentiAutomi) {
    // StringBuilder queryStr = new StringBuilder("SELECT logEvento FROM LogEventoLoginUser logEvento, UsrUser user "
    // + "WHERE logEvento.nmUserid = user.nmUserid "
    // + "AND logEvento.dtEvento BETWEEN :dtEventoDa AND :dtEventoA ");
    //
    // if (StringUtils.isNotBlank(nmUserid)) {
    // queryStr.append("AND UPPER(logEvento.nmUserid) LIKE :nmUserid ");
    // }
    //
    // if (tipiEventoSet != null && !tipiEventoSet.isEmpty()) {
    // queryStr.append("AND logEvento.tipoEvento IN (:tipiEventoSet) ");
    // }
    //
    // if (inclUtentiAutomi.equals("0")) {
    // queryStr.append("AND user.tipoUser != :tipoUser ");
    // }
    //
    // queryStr.append("ORDER BY logEvento.nmUserid ASC, logEvento.dtEvento DESC");
    //
    // Query query = getEntityManager().createQuery(queryStr.toString());
    //
    // query.setParameter("dtEventoDa", dtEventoDa);
    // query.setParameter("dtEventoA", dtEventoA);
    // if (StringUtils.isNotBlank(nmUserid)) {
    // query.setParameter("nmUserid", "%" + nmUserid.toUpperCase() + "%");
    // }
    // if (tipiEventoSet != null && !tipiEventoSet.isEmpty()) {
    // query.setParameter("tipiEventoSet", tipiEventoSet);
    // }
    // if (inclUtentiAutomi.equals("0")) {
    // query.setParameter("tipoUser", ApplEnum.TipoUser.AUTOMA.name());
    // }
    // return (List<LogEventoLoginUser>) query.getResultList();
    // }

    /**
     * Restituisce la lista dei record di login utente in base ai filtri di ricerca in ingresso
     *
     * @param dtEventoDa
     *            data evento da
     * @param dtEventoA
     *            data evento a
     * @param nmUserid
     *            user id
     * @param tipiEventoSet
     *            tipo evento
     * @param inclUtentiAutomi
     *            automi
     * 
     * @return la lista dei record di login utente
     */
    public List<LogVRicAccessi> retrieveLogVRicAccessiList(Date dtEventoDa, Date dtEventoA, String nmUserid,
            Set<String> tipiEventoSet, String inclUtentiAutomi) {
        StringBuilder queryStr = new StringBuilder("SELECT logEvento FROM LogVRicAccessi logEvento, UsrUser user "
                + "WHERE logEvento.nmUserid = user.nmUserid "
                + "AND logEvento.dtEvento BETWEEN :dtEventoDa AND :dtEventoA ");

        if (StringUtils.isNotBlank(nmUserid)) {
            queryStr.append("AND UPPER(logEvento.nmUserid) LIKE :nmUserid ");
        }

        if (tipiEventoSet != null && !tipiEventoSet.isEmpty()) {
            if (tipiEventoSet.contains("LOGIN_OK")) {
                tipiEventoSet.add("LOGIN");
            }
            queryStr.append("AND logEvento.tipoEvento IN (:tipiEventoSet) ");
        }

        if (inclUtentiAutomi.equals("0")) {
            queryStr.append("AND user.tipoUser != :tipoUser ");
        }

        queryStr.append("ORDER BY logEvento.nmUserid ASC, logEvento.dtEvento DESC");

        Query query = getEntityManager().createQuery(queryStr.toString());

        query.setParameter("dtEventoDa", dtEventoDa);
        query.setParameter("dtEventoA", dtEventoA);
        if (StringUtils.isNotBlank(nmUserid)) {
            query.setParameter("nmUserid", "%" + nmUserid.toUpperCase() + "%");
        }
        if (tipiEventoSet != null && !tipiEventoSet.isEmpty()) {
            query.setParameter("tipiEventoSet", tipiEventoSet);
        }
        if (inclUtentiAutomi.equals("0")) {
            query.setParameter("tipoUser", ApplEnum.TipoUser.AUTOMA.name());
        }
        return (List<LogVRicAccessi>) query.getResultList();
    }

    public List<UsrVLisStatoUser> getUsrVLisStatoUserList(BigDecimal idUserIam) {
        List<UsrVLisStatoUser> list = new ArrayList();
        if (idUserIam != null) {
            String queryStr = "SELECT statoUser FROM UsrVLisStatoUser statoUser "
                    + "WHERE statoUser.idUserIam = :idUserIam " + "ORDER BY statoUser.tsStato DESC ";
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("idUserIam", idUserIam);
            list = (List<UsrVLisStatoUser>) query.getResultList();
        }
        return list;
    }

    public List<UsrVRicRichieste> getUsrVRicRichiesteList(BigDecimal idUserIamCor, String nmCognomeUserAppRich,
            String nmNomeUserAppRich, String nmUseridAppRich, BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteSiam,
            String nmCognomeUserRich, String nmNomeUserRich, String nmUseridRich, BigDecimal idOrganizIam,
            String cdRegistroRichGestUser, BigDecimal aaRichGestUser, String cdKeyRichGestUser, String cdRichGestUser,
            String tiStatoRichGestUser) {
        List<UsrVRicRichieste> list = new ArrayList();
        String whereWord = "WHERE ";
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.UsrVRicRichieste(ricRichieste.id.idRichGestUser, "
                        + "ricRichieste.nmAmbienteEnteConvenz, ricRichieste.nmEnteSiam, ricRichieste.dlCompositoOrganiz, ricRichieste.cdRegistroRichGestUser, "
                        + "ricRichieste.aaRichGestUser, ricRichieste.cdKeyRichGestUser, ricRichieste.cdRichGestUser, ricRichieste.nmCognomeUserRich, ricRichieste.nmNomeUserRich, "
                        + "ricRichieste.nmUserRich, ricRichieste.dtRichGestUser, ricRichieste.tiStatoRichGestUser) FROM UsrVRicRichieste ricRichieste ");
        if (idUserIamCor != null) {
            queryStr.append(whereWord).append("ricRichieste.id.idUserIamCor = :idUserIamCor ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(nmCognomeUserAppRich)) {
            queryStr.append(whereWord).append("UPPER(ricRichieste.nmCognomeUserAppRich) LIKE :nmCognomeUserAppRich ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(nmNomeUserAppRich)) {
            queryStr.append(whereWord).append("UPPER(ricRichieste.nmNomeUserAppRich) LIKE :nmNomeUserAppRich ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(nmUseridAppRich)) {
            queryStr.append(whereWord).append("UPPER(ricRichieste.nmUseridAppRich) LIKE :nmUseridAppRich ");
            whereWord = " AND ";
        }
        if (idAmbienteEnteConvenz != null) {
            queryStr.append(whereWord).append("ricRichieste.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
            whereWord = " AND ";
        }
        if (idEnteSiam != null) {
            queryStr.append(whereWord).append("ricRichieste.idEnteSiam = :idEnteSiam ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(nmCognomeUserRich)) {
            queryStr.append(whereWord).append(
                    "(UPPER(ricRichieste.nmCognomeUserRich) LIKE :nmCognomeUserRich OR UPPER(ricRichieste.nmUserRich) LIKE :nmCognomeUserRich) ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(nmNomeUserRich)) {
            queryStr.append(whereWord).append(
                    "(UPPER(ricRichieste.nmNomeUserRich) LIKE :nmNomeUserRich OR UPPER(ricRichieste.nmUserRich) LIKE :nmNomeUserRich) ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(nmUseridRich)) {
            queryStr.append(whereWord).append("UPPER(ricRichieste.nmUseridRich) LIKE :nmUseridRich ");
            whereWord = " AND ";
        }
        if (idOrganizIam != null) {
            queryStr.append(whereWord).append("ricRichieste.idOrganizIam = :idOrganizIam ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(cdRegistroRichGestUser)) {
            queryStr.append(whereWord)
                    .append("UPPER(ricRichieste.cdRegistroRichGestUser) LIKE :cdRegistroRichGestUser ");
            whereWord = " AND ";
        }
        if (aaRichGestUser != null) {
            queryStr.append(whereWord).append("ricRichieste.aaRichGestUser = :aaRichGestUser ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(cdKeyRichGestUser)) {
            queryStr.append(whereWord).append("UPPER(ricRichieste.cdKeyRichGestUser) LIKE :cdKeyRichGestUser ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(cdRichGestUser)) {
            queryStr.append(whereWord).append("UPPER(ricRichieste.cdRichGestUser) LIKE :cdRichGestUser ");
            whereWord = " AND ";
        }
        if (StringUtils.isNotBlank(tiStatoRichGestUser)) {
            queryStr.append(whereWord).append("ricRichieste.tiStatoRichGestUser = :tiStatoRichGestUser ");
            whereWord = " AND ";
        }

        queryStr.append("ORDER BY ricRichieste.dtRichGestUser DESC");

        Query query = getEntityManager().createQuery(queryStr.toString());

        if (idUserIamCor != null) {
            query.setParameter("idUserIamCor", idUserIamCor);
        }
        if (StringUtils.isNotBlank(nmCognomeUserAppRich)) {
            query.setParameter("nmCognomeUserAppRich", "%" + nmCognomeUserAppRich.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(nmNomeUserAppRich)) {
            query.setParameter("nmNomeUserAppRich", "%" + nmNomeUserAppRich.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(nmUseridAppRich)) {
            query.setParameter("nmUseridAppRich", "%" + nmUseridAppRich.toUpperCase() + "%");
        }
        if (idAmbienteEnteConvenz != null) {
            query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        if (idEnteSiam != null) {
            query.setParameter("idEnteSiam", idEnteSiam);
        }
        if (StringUtils.isNotBlank(nmCognomeUserRich)) {
            query.setParameter("nmCognomeUserRich", "%" + nmCognomeUserRich.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(nmNomeUserRich)) {
            query.setParameter("nmNomeUserRich", "%" + nmNomeUserRich.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(nmUseridRich)) {
            query.setParameter("nmUseridRich", "%" + nmUseridRich.toUpperCase() + "%");
        }
        if (idOrganizIam != null) {
            query.setParameter("idOrganizIam", idOrganizIam);
        }
        if (StringUtils.isNotBlank(cdRegistroRichGestUser)) {
            query.setParameter("cdRegistroRichGestUser", "%" + cdRegistroRichGestUser.toUpperCase() + "%");
        }
        if (aaRichGestUser != null) {
            query.setParameter("aaRichGestUser", aaRichGestUser);
        }
        if (StringUtils.isNotBlank(cdKeyRichGestUser)) {
            query.setParameter("cdKeyRichGestUser", "%" + cdKeyRichGestUser.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(cdRichGestUser)) {
            query.setParameter("cdRichGestUser", "%" + cdRichGestUser.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(tiStatoRichGestUser)) {
            query.setParameter("tiStatoRichGestUser", tiStatoRichGestUser);
        }

        list = (List<UsrVRicRichieste>) query.getResultList();
        return list;
    }

    public List<UsrAppartUserRich> getUsrAppartUserRichList(BigDecimal idRichGestUser) {
        String queryStr = "SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        List<UsrAppartUserRich> list = query.getResultList();
        return list;
    }

    public List<UsrAppartUserRich> getUsrAppartUserRichList(BigDecimal idRichGestUser, String tiAzioneRich,
            String flAzioneRichEvasa) {
        Query query = getEntityManager().createQuery(
                "SELECT appartUserRich FROM UsrAppartUserRich appartUserRich WHERE appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser AND appartUserRich.tiAzioneRich = :tiAzioneRich AND appartUserRich.flAzioneRichEvasa = :flAzioneRichEvasa");
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("tiAzioneRich", tiAzioneRich);
        query.setParameter("flAzioneRichEvasa", flAzioneRichEvasa);
        List<UsrAppartUserRich> list = query.getResultList();
        return list;
    }

    public String getStatoCorrenteUser(BigDecimal idUserIam) {
        String queryStr = "SELECT statoUser.tiStatoUser FROM UsrStatoUser statoUser " + "JOIN statoUser.usrUser user "
                + "WHERE statoUser.idStatoUser = user.idStatoUserCor " + "AND user.idUserIam = :idUserIam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<String> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public boolean existsAzioneUtente(BigDecimal idRichGestUser, BigDecimal idAppartUserRich, List<String> tiAzioneRich,
            BigDecimal idUserIam, String nmCognomeUser, String nmNomeUser, String nmUserid, String flAzioneRichEvasa) {
        StringBuilder queryStr = new StringBuilder("SELECT COUNT(appartUserRich) FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser AND appartUserRich.tiAzioneRich IN (:tiAzioneRich) ");

        if (idUserIam != null) {
            queryStr.append("AND appartUserRich.usrUser.idUserIam = :idUserIam ");
        } else {
            queryStr.append(
                    "AND appartUserRich.nmCognomeUser = :nmCognomeUser AND appartUserRich.nmNomeUser = :nmNomeUser AND appartUserRich.nmUserid = :nmUserid ");
        }
        if (StringUtils.isNotBlank(flAzioneRichEvasa)) {
            queryStr.append("AND appartUserRich.flAzioneRichEvasa = :flAzioneRichEvasa ");
        }
        if (idAppartUserRich != null) {
            queryStr.append("AND appartUserRich.idAppartUserRich = :idAppartUserRich ");
        }

        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("tiAzioneRich", tiAzioneRich);
        if (idUserIam != null) {
            query.setParameter("idUserIam", longFrom(idUserIam));
        } else {
            query.setParameter("nmCognomeUser", nmCognomeUser);
            query.setParameter("nmNomeUser", nmNomeUser);
            query.setParameter("nmUserid", nmUserid);
        }
        if (StringUtils.isNotBlank(flAzioneRichEvasa)) {
            query.setParameter("flAzioneRichEvasa", flAzioneRichEvasa);
        }
        if (idAppartUserRich != null) {
            query.setParameter("idAppartUserRich", longFrom(idAppartUserRich));
        }
        return (Long) query.getSingleResult() > 0L;
    }

    public UsrAppartUserRich getAzioneUtente(BigDecimal idRichGestUser, List<String> tiAzioneRich, BigDecimal idUserIam,
            String nmCognomeUser, String nmNomeUser, String nmUserid, String flAzioneRichEvasa) {
        StringBuilder queryStr = new StringBuilder("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser AND appartUserRich.tiAzioneRich IN (:tiAzioneRich) ");

        if (idUserIam != null) {
            queryStr.append("AND appartUserRich.usrUser.idUserIam = :idUserIam ");
        } else {
            queryStr.append(
                    "AND appartUserRich.nmCognomeUser = :nmCognomeUser AND appartUserRich.nmNomeUser = :nmNomeUser AND appartUserRich.nmUserid = :nmUserid ");
        }
        if (StringUtils.isNotBlank(flAzioneRichEvasa)) {
            queryStr.append("AND appartUserRich.flAzioneRichEvasa = :flAzioneRichEvasa ");
        }

        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("tiAzioneRich", tiAzioneRich);
        if (idUserIam != null) {
            query.setParameter("idUserIam", longFrom(idUserIam));
        } else {
            query.setParameter("nmCognomeUser", nmCognomeUser);
            query.setParameter("nmNomeUser", nmNomeUser);
            query.setParameter("nmUserid", nmUserid);
        }
        if (StringUtils.isNotBlank(flAzioneRichEvasa)) {
            query.setParameter("flAzioneRichEvasa", flAzioneRichEvasa);
        }
        List<UsrAppartUserRich> lista = (List<UsrAppartUserRich>) query.getResultList();
        if (lista.size() == 1) {
            return lista.get(0);
        }
        return null;
    }

    public UsrAppartUserRich getAzioneUtente(BigDecimal idRichGestUser, List<String> tiAzioneRich,
            BigDecimal idUserIam) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT appartUserRich FROM UsrAppartUserRich appartUserRich JOIN appartUserRich.usrUser user "
                        + "WHERE appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser AND appartUserRich.tiAzioneRich IN (:tiAzioneRich) ");
        queryStr.append("AND user.idUserIam = :idUserIam ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("tiAzioneRich", tiAzioneRich);
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<UsrAppartUserRich> lista = (List<UsrAppartUserRich>) query.getResultList();
        if (lista.size() == 1) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Restituisce la richiesta sulla base dell'organizzazione e della terna registro-anno-numero escludendo,
     * eventualmente, la richiesta passata come parametro
     *
     * @param idOrganizIam
     *            id organizzazione IAM
     * @param cdRegistroRichGestUser
     *            registro
     * @param aaRichGestUser
     *            anno richieta
     * @param cdKeyRichGestUser
     *            numero
     * @param idRichGestUserExcluded
     *            id richiesta utente da escludere
     * 
     * @return lista elementi di tipo {@link UsrRichGestUser}
     */
    public List<UsrRichGestUser> getUsrRichGestUserByOrganizzazioneAndUd(BigDecimal idOrganizIam,
            String cdRegistroRichGestUser, BigDecimal aaRichGestUser, String cdKeyRichGestUser,
            BigDecimal idRichGestUserExcluded) {
        StringBuilder queryStr = new StringBuilder("SELECT richGestUser FROM UsrRichGestUser richGestUser ");
        String whereWord = " WHERE ";
        if (idOrganizIam != null) {
            queryStr.append(whereWord).append("richGestUser.usrOrganizIam.idOrganizIam = :idOrganizIam ");
            whereWord = " AND ";
        }
        if (cdRegistroRichGestUser != null) {
            queryStr.append(whereWord).append("richGestUser.cdRegistroRichGestUser = :cdRegistroRichGestUser ");
            whereWord = " AND ";
        }
        if (aaRichGestUser != null) {
            queryStr.append(whereWord).append("richGestUser.aaRichGestUser = :aaRichGestUser ");
            whereWord = " AND ";
        }
        if (cdKeyRichGestUser != null) {
            queryStr.append(whereWord).append("richGestUser.cdKeyRichGestUser = :cdKeyRichGestUser ");
            whereWord = " AND ";
        }
        if (idRichGestUserExcluded != null) {
            queryStr.append(whereWord).append("richGestUser.idRichGestUser != :idRichGestUserExcluded ");
            whereWord = " AND ";
        }
        queryStr.append(" ORDER BY richGestUser.dtRichGestUser DESC");
        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idOrganizIam != null) {
            query.setParameter("idOrganizIam", longFrom(idOrganizIam));
        }
        if (cdRegistroRichGestUser != null) {
            query.setParameter("cdRegistroRichGestUser", cdRegistroRichGestUser);
        }
        if (aaRichGestUser != null) {
            query.setParameter("aaRichGestUser", aaRichGestUser);
        }
        if (cdKeyRichGestUser != null) {
            query.setParameter("cdKeyRichGestUser", cdKeyRichGestUser);
        }
        if (idRichGestUserExcluded != null) {
            query.setParameter("idRichGestUserExcluded", longFrom(idRichGestUserExcluded));
        }
        return (List<UsrRichGestUser>) query.getResultList();
    }

    /**
     * Restituisce la richiesta sulla base dell'ente convenzionato e dell'identificativo richiesta escludendo,
     * eventualmente, la richiesta passata come parametro
     *
     * @param cdRichGestUser
     *            codire richiesta utente
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param idRichGestUserExcluded
     *            id richiesat utente da escludere
     * 
     * @return lista elementi di tipo {@link UsrRichGestUser}
     */
    public List<UsrRichGestUser> getUsrRichGestUserByRichEnte(String cdRichGestUser, BigDecimal idEnteConvenz,
            BigDecimal idRichGestUserExcluded) {
        StringBuilder queryStr = new StringBuilder("SELECT richGestUser FROM UsrRichGestUser richGestUser ");
        String whereWord = " WHERE ";
        if (cdRichGestUser != null) {
            queryStr.append(whereWord).append("richGestUser.cdRichGestUser = :cdRichGestUser ");
            whereWord = " AND ";
        }
        if (idEnteConvenz != null) {
            queryStr.append(whereWord).append("richGestUser.orgEnteSiam.idEnteSiam = :idEnteConvenz ");
            whereWord = " AND ";
        }
        if (idRichGestUserExcluded != null) {
            queryStr.append(whereWord).append("richGestUser.idRichGestUser != :idRichGestUserExcluded ");
            whereWord = " AND ";
        }
        queryStr.append(" ORDER BY richGestUser.dtRichGestUser DESC");
        Query query = getEntityManager().createQuery(queryStr.toString());
        if (cdRichGestUser != null) {
            query.setParameter("cdRichGestUser", cdRichGestUser);
        }
        if (idEnteConvenz != null) {
            query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        }
        if (idRichGestUserExcluded != null) {
            query.setParameter("idRichGestUserExcluded", longFrom(idRichGestUserExcluded));
        }
        return (List<UsrRichGestUser>) query.getResultList();
    }

    public UsrAppartUserRich getRichiestaDaEvadere(BigDecimal idUserIam, String tiAzioneRich) {
        String queryStr = "SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.tiAzioneRich = :tiAzioneRich "
                + "AND appartUserRich.usrRichGestUser.tiStatoRichGestUser = 'DA_EVADERE' "
                + "AND appartUserRich.usrUser.idUserIam = :idUserIam " + "AND appartUserRich.flAzioneRichEvasa = '0'";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("tiAzioneRich", tiAzioneRich);
        List<UsrAppartUserRich> resultList = query.getResultList();
        UsrAppartUserRich result = null;
        if (!resultList.isEmpty()) {
            // Prendo già il primo risultato, non possono essercene di più
            result = resultList.get(0);
        }
        return result;
    }

    /**
     * Controlla se una richiesta viene considerata idonea per procedere all'azione relativa
     *
     * @param idUserIam
     *            utente cui è riferita l'azione di
     * @param tiAzioneRich
     *            il tipo di azione da eseguire
     * 
     * @return un record, se esiste, relativo alla tabella delle azioni
     */
    public UsrAppartUserRich getRichiestaDaEseguire(BigDecimal idUserIam, String tiAzioneRich) {
        String queryStr = "SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.tiAzioneRich = :tiAzioneRich "
                + "AND appartUserRich.usrRichGestUser.tiStatoRichGestUser = 'DA_EVADERE' "
                + "AND appartUserRich.usrUser.idUserIam = :idUserIam " + "AND appartUserRich.flAzioneRichEvasa = '0' ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("tiAzioneRich", tiAzioneRich);
        List<UsrAppartUserRich> resultList = query.getResultList();
        UsrAppartUserRich result = null;
        if (!resultList.isEmpty()) {
            // Prendo già il primo risultato, non possono essercene di più
            result = resultList.get(0);
        } // Se non ci sono risultati, allora controllo se esistono richieste, di azioni evase o non,
          // verificando che la richiesta più recente sia in stato EVASA
        else {
            String queryStr2 = "SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                    // + "WHERE appartUserRich.tiAzioneRich = :tiAzioneRich "
                    + "WHERE appartUserRich.usrUser.idUserIam = :idUserIam "
                    + "ORDER BY appartUserRich.usrRichGestUser.dtRichGestUser DESC ";
            Query query2 = getEntityManager().createQuery(queryStr2);
            query2.setParameter("idUserIam", longFrom(idUserIam));
            // query2.setParameter("tiAzioneRich", tiAzioneRich);
            List<UsrAppartUserRich> resultList2 = query2.getResultList();
            if (!resultList2.isEmpty()) {
                result = resultList2.get(0);
                if (result.getTiAzioneRich()
                        .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD.getDescrizione())
                        && result.getUsrRichGestUser().getTiStatoRichGestUser().equals("EVASA")) {
                    return result;
                } else {
                    return null;
                }
            }

        }
        return result;
    }

    /**
     * Verifica se per una data richiesta esiste almeno una azione evasa o non evasa in base al valore del flag passato
     * in input
     *
     * @param idRichGestUser
     *            id richiesta
     * @param flAzioneRichEvasa
     *            flag 1/0 (true/false) se azione evasa
     * 
     * @return true se esiste una azione evasa o non evasa a seconda del valore passato in input
     */
    public boolean existAzioni(long idRichGestUser, String flAzioneRichEvasa) {
        Query query = getEntityManager()
                .createQuery("SELECT COUNT(appartUserRich) FROM UsrAppartUserRich appartUserRich "
                        + "WHERE appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser "
                        + "AND appartUserRich.flAzioneRichEvasa = :flAzioneRichEvasa ");
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("flAzioneRichEvasa", flAzioneRichEvasa);
        return (Long) query.getSingleResult() > 0;
    }

    /**
     * Determina se l'utente per l'applicazione passati in input è abilitato all'organizzazione passata in input
     *
     * @param idUserIam
     *            id user IAM
     * @param idOrganizApplic
     *            id organizzazione
     * 
     * @return la lista di entity
     */
    public UsrVAbilOrganiz getUsrVAbilOrganiz(BigDecimal idUserIam, BigDecimal idOrganizApplic) {
        String queryStr = "SELECT abilOrganiz FROM UsrVAbilOrganiz abilOrganiz "
                + "WHERE abilOrganiz.id.idUserIam = :idUserIam " + "AND abilOrganiz.idOrganizApplic = :idOrganizApplic "
                + "AND abilOrganiz.nmTipoOrganiz = 'STRUTTURA' " + "AND abilOrganiz.nmApplic = 'SACER' ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idOrganizApplic", idOrganizApplic);
        List<UsrVAbilOrganiz> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        } else {
            return null;
        }
    }

    public BigDecimal getSusrRichGestUserCd() {
        Query q = getEntityManager().createNativeQuery("SELECT SUSR_RICH_GEST_USER_CD.NEXTVAL FROM DUAL");
        return (BigDecimal) q.getSingleResult();
    }

    /**
     * Restituisce l'elenco delle azioni associate ad un determinato utente in una determinata richiesta, escludendo
     * eventualmente quella passata in ingresso. L'utente viene identificato o tramite nmUserid (non censito) o tramite
     * idUserIam (censito)
     *
     * @param idRichGestUser
     *            id richiesta utente
     * @param nmUserid
     *            user id
     * @param idUserIam
     *            id user IAM
     * @param idAppartUserRichExcluded
     *            id appartenenza utente richiesta
     * 
     * @return lista elementi di tipo {@link UsrAppartUserRich}
     */
    public List<UsrAppartUserRich> getUsrAppartUserRichByUser(BigDecimal idRichGestUser, String nmUserid,
            BigDecimal idUserIam, BigDecimal idAppartUserRichExcluded) {
        StringBuilder queryStr = new StringBuilder("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "JOIN appartUserRich.usrRichGestUser richGestUser ");
        String whereWord = " WHERE ";

        if (idRichGestUser != null) {
            queryStr.append(whereWord).append("richGestUser.idRichGestUser = :idRichGestUser ");
            whereWord = " AND ";
        }
        if (idAppartUserRichExcluded != null) {
            queryStr.append(whereWord).append("appartUserRich.idAppartUserRich != :idAppartUserRichExcluded ");
            whereWord = " AND ";
        }

        if (StringUtils.isNotBlank(nmUserid)) {
            queryStr.append(whereWord).append("appartUserRich.nmUserid = :nmUserid ");
            whereWord = " AND ";
        } else if (idUserIam != null) {
            queryStr.append(whereWord).append("appartUserRich.usrUser.idUserIam = :idUserIam ");
            whereWord = " AND ";
        }

        Query query = getEntityManager().createQuery(queryStr.toString());

        if (idRichGestUser != null) {
            query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        }
        if (idAppartUserRichExcluded != null) {
            query.setParameter("idAppartUserRichExcluded", longFrom(idAppartUserRichExcluded));
        }

        if (StringUtils.isNotBlank(nmUserid)) {
            query.setParameter("nmUserid", nmUserid);
        } else if (idUserIam != null) {
            query.setParameter("idUserIam", longFrom(idUserIam));
        }

        return (List<UsrAppartUserRich>) query.getResultList();
    }

    /**
     * Restituisce l'elenco delle azioni NON EVASE associate ad un determinato utente in richieste diverse da quella
     * passata in ingresso. L'utente viene identificato o tramite nmUserid (non censito) o tramite idUserIam (censito)
     *
     * @param idRichGestUser
     *            id richiesta utente
     * @param nmUserid
     *            user id
     * @param idUserIam
     *            id user IAM
     * 
     * @return lista elementi di tipo {@link UsrAppartUserRich}
     */
    public List<UsrAppartUserRich> getOtherUsrAppartUserRichByUser(BigDecimal idRichGestUser, String nmUserid,
            BigDecimal idUserIam) {
        StringBuilder queryStr = new StringBuilder("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "JOIN appartUserRich.usrRichGestUser richGestUser "
                + "WHERE appartUserRich.flAzioneRichEvasa = '0' ");
        String whereWord = " AND ";

        if (idRichGestUser != null) {
            queryStr.append(whereWord).append("richGestUser.idRichGestUser != :idRichGestUser ");
            whereWord = " AND ";
        }

        if (StringUtils.isNotBlank(nmUserid)) {
            queryStr.append(whereWord).append("appartUserRich.nmUserid = :nmUserid ");
            whereWord = " AND ";
        } else if (idUserIam != null) {
            queryStr.append(whereWord).append("appartUserRich.usrUser.idUserIam = :idUserIam ");
            whereWord = " AND ";
        }

        Query query = getEntityManager().createQuery(queryStr.toString());

        if (idRichGestUser != null) {
            query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        }

        if (StringUtils.isNotBlank(nmUserid)) {
            query.setParameter("nmUserid", nmUserid);
        } else if (idUserIam != null) {
            query.setParameter("idUserIam", longFrom(idUserIam));
        }

        return (List<UsrAppartUserRich>) query.getResultList();
    }

    @Deprecated
    public boolean checkEsistenzaUd(BigDecimal idOrganizApplic, String registro, BigDecimal anno, String numero) {
        String queryStr = "SELECT COUNT(unitaDoc) FROM AroUnitaDoc unitaDoc "
                + "WHERE unitaDoc.idOrgStrut = :udOrganizApplic "
                + "AND unitaDoc.cdRegistroKeyUnitaDoc = :cdRegistroKeyUnitaDoc "
                + "AND unitaDoc.aaKeyUnitaDoc = :aaKeyUnitaDoc " + "AND unitaDoc.cdKeyUnitaDoc = :cdKeyUnitaDoc ";

        Query query = getEntityManager().createQuery(queryStr);

        query.setParameter("idOrganizApplic", idOrganizApplic.longValue());
        query.setParameter("registro", registro);
        query.setParameter("anno", anno);
        query.setParameter("numero", numero);

        return (Long) query.getSingleResult() > 0L;
    }

    public boolean existsUtenteByUseridAndTipo(String nmUserid, String... tipoUser) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT COUNT(user) FROM UsrUser user " + "WHERE user.nmUserid = :nmUserid ");
        if (tipoUser != null && tipoUser.length > 0) {
            queryStr.append("AND user.tipoUser IN (:tipoUser) ");
        }
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("nmUserid", nmUserid);
        if (tipoUser != null && tipoUser.length > 0) {
            query.setParameter("tipoUser", Arrays.asList(tipoUser));
        }
        return (Long) query.getSingleResult() > 0L;
    }

    /**
     * Restituisce l'elenco delle richieste associate ad un determinato utente con un determinato tipo di azione,
     * ordinate per data decrescente.
     *
     * L'utente viene identificato tramite idUserIam (censito)
     *
     * @param idUserIam
     *            id user IAM
     * @param tiAzioneRich
     *            tipo azione richiesta
     * 
     * @return lista id
     */
    public List<Long> getUsrRicGestUserByUser(BigDecimal idUserIam, List<String> tiAzioneRich) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT richGestUser.idRichGestUser, richGestUser.dtRichGestUser FROM UsrAppartUserRich appartUserRich JOIN appartUserRich.usrRichGestUser richGestUser WHERE appartUserRich.usrUser.idUserIam = :idUserIam AND appartUserRich.tiAzioneRich IN (:tiAzioneRich) ORDER BY richGestUser.dtRichGestUser DESC ");
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("tiAzioneRich", tiAzioneRich);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream().map(o -> Long.class.cast(o[0])).collect(Collectors.toList());
    }

    public UsrVVisLastRichGestUser getUsrVVisLastRichGestUser(BigDecimal idUserIam) {
        Query query = getEntityManager()
                .createQuery("SELECT view FROM UsrVVisLastRichGestUser view where view.idUserIam = :idUserIam");
        query.setParameter("idUserIam", idUserIam);
        List<UsrVVisLastRichGestUser> resultList = query.getResultList();
        UsrVVisLastRichGestUser record = null;
        if (resultList != null && !resultList.isEmpty()) {
            if (resultList.size() == 1) {
                record = resultList.get(0);
            } else {
                throw new IllegalStateException("Errore nel caricamento dell'ultima richiesta per l'utente");
            }
        }
        return record;
    }

    public List<UsrVLisEntiSiamPerAzio> getUsrVLisEntiSiamPerAzioList(BigDecimal idRichGestUser, long idUserIamCor) {
        Query query = getEntityManager()
                .createQuery("SELECT entiSiamPerAzio FROM UsrVLisEntiSiamPerAzio entiSiamPerAzio "
                        + "WHERE entiSiamPerAzio.idRichGestUser= :idRichGestUser "
                        + "AND entiSiamPerAzio.id.idUserIamCor = :idUserIamCor "
                        + "ORDER BY entiSiamPerAzio.nmEnteSiam ");
        query.setParameter("idRichGestUser", idRichGestUser);
        query.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        List<UsrVLisEntiSiamPerAzio> resultList = query.getResultList();
        return resultList;
    }

    public List<UsrVLisEntiSiamAppEnte> getUsrVLisEntiSiamAppEnteList(long idUserIamCor) {
        Query query = getEntityManager()
                .createQuery("SELECT entiSiamAppEnte FROM UsrVLisEntiSiamAppEnte entiSiamAppEnte "
                        + "WHERE entiSiamAppEnte.idUserIamCor = :idUserIamCor "
                        + "ORDER BY entiSiamAppEnte.nmEnteSiam ");
        query.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        List<UsrVLisEntiSiamAppEnte> resultList = query.getResultList();
        return resultList;
    }

    public List<UsrVLisAbilOrganiz> getUsrVLisAbilOrganizList(BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT abilOrganiz FROM UsrVLisAbilOrganiz abilOrganiz "
                + "WHERE abilOrganiz.id.idUserIamGestito= :idUserIam "
                + "ORDER BY abilOrganiz.nmApplic, abilOrganiz.dlCompositoOrganiz ");
        query.setParameter("idUserIam", idUserIam);
        List<UsrVLisAbilOrganiz> resultList = query.getResultList();
        return resultList;
    }

    public List<UsrVLisAbilEnte> getUsrVLisAbilEnteList(BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT abilEnte FROM UsrVLisAbilEnte abilEnte "
                + "WHERE abilEnte.id.idUserIamGestito = :idUserIam " + "ORDER BY abilEnte.nmCompositoEnte ");
        query.setParameter("idUserIam", idUserIam);
        List<UsrVLisAbilEnte> resultList = query.getResultList();
        return resultList;
    }

    public BigDecimal getIdOrganizIamByParam(String nmEnteUnitaDoc, String nmStrutUnitaDoc) {

        Query query = getEntityManager().createNativeQuery("SELECT org_strut.id_organiz_applic "
                + "FROM apl_applic apl join apl_tipo_organiz ti_org_ente on (ti_org_ente.id_applic = apl.id_applic and ti_org_ente.nm_tipo_organiz = 'ENTE') "
                + "join usr_organiz_iam org_ente on (org_ente.id_applic = apl.id_applic and org_ente.id_tipo_organiz = ti_org_ente.id_tipo_organiz) "
                + "join apl_tipo_organiz ti_org_strut on (ti_org_strut.id_applic = apl.id_applic and ti_org_strut.nm_tipo_organiz = 'STRUTTURA') "
                + "join usr_organiz_iam org_strut on (org_strut.id_applic = apl.id_applic and org_strut.id_tipo_organiz = ti_org_strut.id_tipo_organiz and org_strut.id_organiz_iam_padre = org_ente.id_organiz_iam) "
                + "where apl.nm_applic = 'SACER' and org_ente.nm_organiz = ? and org_strut.nm_organiz = ?");

        query.setParameter(1, nmEnteUnitaDoc);
        query.setParameter(2, nmStrutUnitaDoc);
        List<BigDecimal> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public boolean existsRichiestaUtente(BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrUser.idUserIam = :idUserIam ");
        query.setParameter("idUserIam", longFrom(idUserIam));
        return !query.getResultList().isEmpty();
    }

    public boolean existsRichiestaUtenteAzione(BigDecimal idUserIam, String azione) {
        Query query = getEntityManager().createQuery("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrUser.idUserIam = :idUserIam "
                + "AND appartUserRich.tiAzioneRich = :azione ");
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("azione", azione);
        return !query.getResultList().isEmpty();
    }

    public boolean existsRichiestaUtenteAzione(BigDecimal idRichGestUser, BigDecimal idUserIam, String azione) {
        Query query = getEntityManager().createQuery("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrUser.idUserIam = :idUserIam "
                + "AND appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser "
                + "AND appartUserRich.tiAzioneRich = :azione ");
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("azione", azione);
        return !query.getResultList().isEmpty();
    }

    public UsrAppartUserRich getRichiestaUtenteAzione(BigDecimal idRichGestUser, BigDecimal idUserIam, String azione) {
        Query query = getEntityManager().createQuery("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrUser.idUserIam = :idUserIam "
                + "AND appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser "
                + "AND appartUserRich.tiAzioneRich = :azione ");
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("azione", azione);
        List<UsrAppartUserRich> listaAzioni = (List<UsrAppartUserRich>) query.getResultList();
        if (!listaAzioni.isEmpty()) {
            return listaAzioni.get(0);
        }
        return null;
    }

    public boolean existsRichiestaUtenteDiversaAzione(BigDecimal idUserIam, String azione) {
        Query query = getEntityManager().createQuery("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrUser.idUserIam = :idUserIam "
                + "AND appartUserRich.tiAzioneRich != :azione ");
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("azione", azione);
        return !query.getResultList().isEmpty();
    }

    public boolean existsRichiestaUtenteDiversaAzione(BigDecimal idRichGestUser, BigDecimal idUserIam, String azione) {
        Query query = getEntityManager().createQuery("SELECT appartUserRich FROM UsrAppartUserRich appartUserRich "
                + "WHERE appartUserRich.usrUser.idUserIam = :idUserIam "
                + "AND appartUserRich.usrRichGestUser.idRichGestUser = :idRichGestUser "
                + "AND appartUserRich.tiAzioneRich != :azione ");
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setParameter("idRichGestUser", longFrom(idRichGestUser));
        query.setParameter("azione", azione);
        return !query.getResultList().isEmpty();
    }

    public OrgVChkRefEnte getOrgVChkRefEnte(BigDecimal idEnteSiam, BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery(
                "SELECT u FROM OrgVChkRefEnte u WHERE u.id.idEnteRef = :idEnteRef AND u.id.idUserIam = :idUserIam");
        query.setParameter("idEnteRef", idEnteSiam);
        query.setParameter("idUserIam", idUserIam);
        OrgVChkRefEnte record = (OrgVChkRefEnte) query.getSingleResult();
        return record;
    }

    public List<UsrUser> retrieveUsrUserEnteSup(BigDecimal idEnteSiam,
            Map<String, List<TiEnteNonConvenz>> tiEnteNonConvenz, Map<String, List<String>> tiStatoUser,
            Map<String, List<String>> tipoUser) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT user FROM UsrStatoUser statoUser, UsrUser user " + "JOIN user.orgEnteSiam enteSiam "
                        + "WHERE user.flAbilFornitAutom = '1' AND user.idStatoUserCor = statoUser.idStatoUser ");
        if (idEnteSiam != null) {
            queryStr.append("AND enteSiam.idEnteSiam = :idEnteSiam  ");
        }
        if (tiEnteNonConvenz != null) {
            String keyClause = tiEnteNonConvenz.keySet().stream().findFirst().get();
            queryStr.append("AND enteSiam.tiEnteNonConvenz ").append(keyClause).append(" :tiEnteNonConvenz ");
        }
        if (tiStatoUser != null) {
            String keyClause = tiStatoUser.keySet().stream().findFirst().get();
            queryStr.append("AND statoUser.tiStatoUser ").append(keyClause).append(" :tiStatoUser ");
        }
        if (tipoUser != null) {
            String keyClause = tipoUser.keySet().stream().findFirst().get();
            queryStr.append("AND user.tipoUser ").append(keyClause).append(" :tipoUser ");
        }
        queryStr.append("ORDER BY user.nmCognomeUser, user.nmNomeUser ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idEnteSiam != null) {
            query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        }
        if (tiEnteNonConvenz != null) {
            List<TiEnteNonConvenz> values = tiEnteNonConvenz.values().stream().findFirst().get();
            query.setParameter("tiEnteNonConvenz", values);
        }
        if (tiStatoUser != null) {
            List<String> values = tiStatoUser.values().stream().findFirst().get();
            query.setParameter("tiStatoUser", values);
        }
        if (tipoUser != null) {
            List<String> values = tipoUser.values().stream().findFirst().get();
            query.setParameter("tipoUser", values);
        }
        List<UsrUser> utenti = query.getResultList();
        return utenti;
    }

    /**
     * Controlla se l'utente passato in input ha effettuato versamenti di UD in Sacer
     * 
     * @param idUserIam
     *            l'utente versante
     * 
     * @return true se sono presenti versamenti in Sacer effettuati dall'utente
     */
    public boolean checkExistsVersamentiSacer(long idUserIam) {
        Query query = getEntityManager().createQuery(
                "SELECT unitaDoc FROM AroUnitaDoc unitaDoc " + "WHERE unitaDoc.iamUser.idUserIam = :idUserIam ");
        query.setParameter("idUserIam", idUserIam);
        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    /**
     * Controlla se l'utente passato in input ha effettuato versamenti di oggetti in Ping
     * 
     * @param idUserIam
     *            l'utente versante
     * 
     * @return true se sono presenti versamenti in Ping effettuati dall'utente
     */
    public boolean checkExistsVersamentiPing(long idUserIam) {
        Query query = getEntityManager().createQuery(
                "SELECT oggetto FROM PigObject oggetto " + "WHERE oggetto.iamUser.idUserIam = :idUserIam ");
        query.setParameter("idUserIam", idUserIam);
        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    public String getQualificaUser(long idUserIam) {
        Query query = getEntityManager().createQuery("SELECT enteUserRif.qualificaUser FROM OrgEnteUserRif enteUserRif "
                + "WHERE enteUserRif.usrUser.idUserIam = :idUserIam ");
        query.setParameter("idUserIam", idUserIam);
        List<String> qualificaList = (List<String>) query.getResultList();
        String qualificaResult = "";
        for (String qualifica : qualificaList) {
            qualificaResult = qualificaResult + qualifica + "; ";
        }
        // Rimuovo l'ultimo carattere
        qualificaResult = StringUtils.chop(qualificaResult.trim());
        return qualificaResult;
    }

    static class FiltriUtentiPlain {
        private final Set<BigDecimal> idEnteConvenzAppart;
        private final long idUserIamCorr;
        private final List<BigDecimal> idEntiConvenzionatiAmministratori;
        private final String cognome;
        private final String nome;
        private final String userId;
        private final String codiceFiscale;
        private final BigDecimal idApplic;
        private final BigDecimal ruoloDefault;
        private final String attivo;
        private final String dsEmail;
        private final List<String> tipoUser;
        private final String statoUser;
        private final BigDecimal idOrganizIam;
        private final BigDecimal ruoloDich;
        private final String errReplic;
        private final BigDecimal idSistemaVersante;
        private final List<BigDecimal> idAmbienteEnteConvenzAppart;
        private final BigDecimal idEnteConvenzAbil;
        private final String cdRichGestUser;
        private final Date dataOrarioDa;
        private final Date dataOrarioA;
        private final BigDecimal idOrganizIamRich;
        private final String cdRegistroRichGestUser;
        private final BigDecimal aaRichGestUser;
        private final String cdKeyRichGestUser;
        private final BigDecimal idEnteConvenzRich;
        private final String flRespEnteConvenz;

        FiltriUtentiPlain(Set<BigDecimal> idEnteConvenzAppart, long idUserIamCorr,
                List<BigDecimal> idEntiConvenzionatiAmministratori, String cognome, String nome, String userId,
                String codiceFiscale, BigDecimal idApplic, BigDecimal ruoloDefault, String attivo, String dsEmail,
                List<String> tipoUser, String statoUser, BigDecimal idOrganizIam, BigDecimal ruoloDich,
                String errReplic, BigDecimal idSistemaVersante, List<BigDecimal> idAmbienteEnteConvenzAppart,
                BigDecimal idEnteConvenzAbil, String cdRichGestUser, Date dataOrarioDa, Date dataOrarioA,
                BigDecimal idOrganizIamRich, String cdRegistroRichGestUser, BigDecimal aaRichGestUser,
                String cdKeyRichGestUser, BigDecimal idEnteConvenzRich, String flRespEnteConvenz) {
            this.idEnteConvenzAppart = idEnteConvenzAppart;
            this.idUserIamCorr = idUserIamCorr;
            this.idEntiConvenzionatiAmministratori = idEntiConvenzionatiAmministratori;
            this.cognome = cognome;
            this.nome = nome;
            this.userId = userId;
            this.codiceFiscale = codiceFiscale;
            this.idApplic = idApplic;
            this.ruoloDefault = ruoloDefault;
            this.attivo = attivo;
            this.dsEmail = dsEmail;
            this.tipoUser = tipoUser;
            this.statoUser = statoUser;
            this.idOrganizIam = idOrganizIam;
            this.ruoloDich = ruoloDich;
            this.errReplic = errReplic;
            this.idSistemaVersante = idSistemaVersante;
            this.idAmbienteEnteConvenzAppart = idAmbienteEnteConvenzAppart;
            this.idEnteConvenzAbil = idEnteConvenzAbil;
            this.cdRichGestUser = cdRichGestUser;
            this.dataOrarioDa = dataOrarioDa;
            this.dataOrarioA = dataOrarioA;
            this.idOrganizIamRich = idOrganizIamRich;
            this.cdRegistroRichGestUser = cdRegistroRichGestUser;
            this.aaRichGestUser = aaRichGestUser;
            this.cdKeyRichGestUser = cdKeyRichGestUser;
            this.idEnteConvenzRich = idEnteConvenzRich;
            this.flRespEnteConvenz = flRespEnteConvenz;
        }

        private Set<BigDecimal> getIdEnteConvenzAppart() {
            return idEnteConvenzAppart;
        }

        private long getIdUserIamCorr() {
            return idUserIamCorr;
        }

        private List<BigDecimal> getIdEntiConvenzionatiAmministratori() {
            return idEntiConvenzionatiAmministratori;
        }

        private String getCognome() {
            return cognome;
        }

        private String getNome() {
            return nome;
        }

        private String getUserId() {
            return userId;
        }

        private String getCodiceFiscale() {
            return codiceFiscale;
        }

        private BigDecimal getIdApplic() {
            return idApplic;
        }

        private BigDecimal getRuoloDefault() {
            return ruoloDefault;
        }

        private String getAttivo() {
            return attivo;
        }

        private String getDsEmail() {
            return dsEmail;
        }

        private List<String> getTipoUser() {
            return tipoUser;
        }

        private String getStatoUser() {
            return statoUser;
        }

        private BigDecimal getIdOrganizIam() {
            return idOrganizIam;
        }

        private BigDecimal getRuoloDich() {
            return ruoloDich;
        }

        private String getErrReplic() {
            return errReplic;
        }

        private BigDecimal getIdSistemaVersante() {
            return idSistemaVersante;
        }

        private List<BigDecimal> getIdAmbienteEnteConvenzAppart() {
            return idAmbienteEnteConvenzAppart;
        }

        private BigDecimal getIdEnteConvenzAbil() {
            return idEnteConvenzAbil;
        }

        private String getCdRichGestUser() {
            return cdRichGestUser;
        }

        private Date getDataOrarioDa() {
            return dataOrarioDa;
        }

        private Date getDataOrarioA() {
            return dataOrarioA;
        }

        private BigDecimal getIdOrganizIamRich() {
            return idOrganizIamRich;
        }

        private String getCdRegistroRichGestUser() {
            return cdRegistroRichGestUser;
        }

        private BigDecimal getAaRichGestUser() {
            return aaRichGestUser;
        }

        private String getCdKeyRichGestUser() {
            return cdKeyRichGestUser;
        }

        private BigDecimal getIdEnteConvenzRich() {
            return idEnteConvenzRich;
        }

        private String getFlRespEnteConvenz() {
            return flRespEnteConvenz;
        }
    }
}
