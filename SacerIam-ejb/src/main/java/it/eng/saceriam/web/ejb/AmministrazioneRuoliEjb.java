package it.eng.saceriam.web.ejb;

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
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteConvenz;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteNonConvenz;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser.TiStatotUser;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplAzionePaginaTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplEntryMenuTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplPaginaWebTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplServizioWebTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfDichAutorRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloCategoriaRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloCategoriaTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicTableBean;
import it.eng.saceriam.slite.gen.viewbean.AplVTreeEntryMenuTableBean;
import it.eng.saceriam.slite.gen.viewbean.AplVTreeMenuPagAzioTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorRowBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisRuoloRowBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisRuoloTableBean;
import it.eng.saceriam.viewEntity.AplVTreeEntryMenu;
import it.eng.saceriam.viewEntity.AplVTreeMenuPagAzio;
import it.eng.saceriam.viewEntity.PrfVLisDichAutor;
import it.eng.saceriam.viewEntity.PrfVLisDichAutorDaAllin;
import it.eng.saceriam.viewEntity.PrfVLisRuolo;
import it.eng.saceriam.web.dto.PairAuth;
import it.eng.saceriam.web.helper.AmministrazioneRuoliHelper;
import it.eng.saceriam.web.util.Transform;
import it.eng.saceriam.ws.client.allineaRuolo.ListaCategRuolo;
import it.eng.saceriam.ws.client.allineaRuolo.Applic;
import it.eng.saceriam.ws.client.allineaRuolo.DichAutor;
import it.eng.saceriam.ws.client.allineaRuolo.ListaApplic;
import it.eng.saceriam.ws.client.allineaRuolo.ListaDichAutor;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.form.base.BaseElements.Status;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ejb Amministrazione Ruoli di SacerIam
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class AmministrazioneRuoliEjb {

    public AmministrazioneRuoliEjb() {
    }

    @EJB
    private AmministrazioneRuoliHelper amministrazioneRuoliHelper;

    private static final Logger log = LoggerFactory.getLogger(AmministrazioneRuoliEjb.class);

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome
     *
     * @return il table bean delle applicazioni
     * 
     * @throws EMFError
     *             errore generico
     */
    public AplApplicTableBean getAplApplicTableBean() throws EMFError {
        AplApplicTableBean applicTableBean = new AplApplicTableBean();
        List<AplApplic> applicList = amministrazioneRuoliHelper.getAplApplicList();
        try {
            if (applicList != null && !applicList.isEmpty()) {
                applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicTableBean;
    }

    public PrfVLisRuoloTableBean getPrfVLisRuoloTableBean(Set<String> nmApplics, String tiStatoAllinea1,
            String tiStatoAllinea2, String nmRuolo, String tiRuolo, String tiCategRuolo) throws EMFError {
        PrfVLisRuoloTableBean ruoliTB = new PrfVLisRuoloTableBean();
        List<PrfVLisRuolo> ruoliList = amministrazioneRuoliHelper.getPrfVLisRuoloList(nmApplics, tiStatoAllinea1,
                tiStatoAllinea2, nmRuolo, tiRuolo, tiCategRuolo);
        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                // Scorro la lista e rielaboro i dati
                for (PrfVLisRuolo ruolo : ruoliList) {
                    PrfVLisRuoloRowBean ruoloRB = (PrfVLisRuoloRowBean) Transform.entity2RowBean(ruolo);
                    Set<BigDecimal> idUsoRuoloApplicSet = amministrazioneRuoliHelper
                            .getIdUsoRuoloApplicSet(ruolo.getIdRuolo());
                    ruoloRB.setObject("set_usi", idUsoRuoloApplicSet);
                    //// // Gestione categoria
                    // Set<String> ticategRuoloSet = amministrazioneRuoliHelper
                    // .getTiCategRuoloList(ruolo.getIdRuolo());
                    // ruoloRB.setObject("set_cat", ticategRuoloSet);

                    // List<String> filtroCatList = new ArrayList<>();
                    // List<String> listaCatTotale = amministrazioneRuoliHelper.getTiCategRuoloList(ruolo.getIdRuolo());
                    // for (String cat : listaCatTotale) {
                    // if (listaCatTotale.contains(cat)) {
                    // filtroCatList.add(cat);
                    // }
                    // }
                    // String lista = "";
                    // for (String cat : filtroCatList) {
                    // if (!lista.equals("")) {
                    // lista = lista + ", " + cat;
                    // } else {
                    // lista = cat;
                    // }
                    // }
                    // ruoloRB.setTiCategRuolo(lista);
                    ruoliTB.add(ruoloRB);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    /**
     * Recupero il ruolo in base all'idRuolo utilizzando questa vista che mi restituisce tutti i valori di cui ho
     * bisogno per il dettaglio
     *
     * @param idRuolo
     *            id ruolo
     * 
     * @return row bean PrfVLisRuoloRowBean
     * 
     * @throws EMFError
     *             errore generico
     */
    public PrfVLisRuoloRowBean getPrfVLisRuoloRowBean(BigDecimal idRuolo) throws EMFError {
        PrfVLisRuolo ruolo = amministrazioneRuoliHelper.getPrfVLisRuolo(idRuolo);
        PrfVLisRuoloRowBean ruoloRB = null;
        try {
            ruoloRB = (PrfVLisRuoloRowBean) Transform.entity2RowBean(ruolo);
            Set<BigDecimal> idUsoRuoloApplicSet = amministrazioneRuoliHelper.getIdUsoRuoloApplicSet(ruolo.getIdRuolo());
            ruoloRB.setObject("set_usi", idUsoRuoloApplicSet);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException("Impossibile recuperare il ruolo");
        }
        return ruoloRB;
    }

    public PrfRuoloTableBean getPrfRuoliTableBean() throws EMFError {
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        List<PrfRuolo> ruoliList = amministrazioneRuoliHelper.getPrfRuoli();
        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliTB = (PrfRuoloTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    /**
     *
     * @param idUsoRuoloSet
     *            lista id distinti
     * @param tiDichAutor
     *            autore
     * @param idApplic
     *            id applicazione
     *
     * @return table bean {@link PrfVLisDichAutorTableBean}
     */
    // TODO: DA ELIMINARE
    public PrfVLisDichAutorTableBean getPrfDichAutorViewBean(Set<BigDecimal> idUsoRuoloSet, String tiDichAutor,
            BigDecimal idApplic) {
        List<PrfVLisDichAutor> dichAutorList = amministrazioneRuoliHelper.getPrfVLisDichAutorList(idUsoRuoloSet,
                tiDichAutor, idApplic);
        PrfVLisDichAutorTableBean dichsTB = new PrfVLisDichAutorTableBean();
        try {
            if (dichAutorList != null && !dichAutorList.isEmpty()) {
                dichsTB = (PrfVLisDichAutorTableBean) Transform.entities2TableBean(dichAutorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    public PrfVLisDichAutorTableBean getPrfDichAutorViewBean(BigDecimal idRuolo, String tiDichAutor,
            BigDecimal idApplic) {
        List<PrfVLisDichAutor> dichAutorList = amministrazioneRuoliHelper.getPrfVLisDichAutorList(idRuolo, tiDichAutor,
                idApplic);
        PrfVLisDichAutorTableBean dichsTB = new PrfVLisDichAutorTableBean();
        try {
            if (dichAutorList != null && !dichAutorList.isEmpty()) {
                for (PrfVLisDichAutor record : dichAutorList) {
                    PrfVLisDichAutorRowBean row = (PrfVLisDichAutorRowBean) Transform.entity2RowBean(record);
                    if (row.getTiScopoDichAutor().equals(ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name())) {
                        AplEntryMenu entryMenu = amministrazioneRuoliHelper.getRootEntryMenu(idApplic);
                        row.setIdEntryMenu(new BigDecimal(entryMenu.getIdEntryMenu()));
                    }
                    dichsTB.add(row);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    /**
     *
     * @param usoRuoloApplicTB
     *            table bean {@link PrfUsoRuoloApplicTableBean}
     * @param tiDichAutor
     *            autore
     *
     * @return table bean {@link PrfVLisDichAutorTableBean}
     * 
     * @throws EMFError
     *             errore generico
     */
    public PrfVLisDichAutorTableBean getPrfDichAutorEntryMenuOrdinati(PrfUsoRuoloApplicTableBean usoRuoloApplicTB,
            String tiDichAutor) throws EMFError {
        PrfVLisDichAutorTableBean dichsTB = new PrfVLisDichAutorTableBean();

        // Per ogni applicazione tra quelle considerate in questo ruolo
        for (PrfUsoRuoloApplicRowBean usoRuoloApplicRB : usoRuoloApplicTB) {
            // Recupero le abilitazioni sui menu
            List<Object[]> aplVTreeList = amministrazioneRuoliHelper
                    .getAbilitazioniEntryMenu4Applic(usoRuoloApplicRB/*
                                                                      * , tiDichAutor
                                                                      */);
            // Se il risultato della query è vuoto, significa che potrei stare considerando una abilitazione
            // ALL_ABILITAZIONI
            if (aplVTreeList.isEmpty()) {
                // La ricavo
                Set<BigDecimal> idUsoRuoloApplic = new HashSet<>();
                idUsoRuoloApplic.add(usoRuoloApplicRB.getIdUsoRuoloApplic());
                List<PrfVLisDichAutor> dichAutorList = amministrazioneRuoliHelper
                        .getPrfVLisDichAutorList(idUsoRuoloApplic, tiDichAutor, usoRuoloApplicRB.getIdApplic());

                if (dichAutorList.size() == 1) {
                    try {
                        PrfVLisDichAutorRowBean dichsRB = new PrfVLisDichAutorRowBean();
                        dichsRB = (PrfVLisDichAutorRowBean) Transform.entity2RowBean(dichAutorList.get(0));
                        dichsTB.add(dichsRB);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            } else {
                for (Object[] aplTreeObject : aplVTreeList) {
                    PrfVLisDichAutorRowBean dichsRB = new PrfVLisDichAutorRowBean();
                    dichsRB.setIdDichAutor(
                            (BigDecimal) (aplTreeObject[0] != null ? aplTreeObject[0] : aplTreeObject[1]));
                    dichsRB.setIdApplic(usoRuoloApplicRB.getIdApplic());
                    dichsRB.setString("nm_applic", usoRuoloApplicRB.getString("nm_applic"));
                    dichsRB.setTiScopoDichAutor(
                            (String) (aplTreeObject[2] != null ? aplTreeObject[2] : aplTreeObject[3]));
                    dichsRB.setDsEntryMenu((String) (aplTreeObject[4]));
                    dichsRB.setIdEntryMenu((BigDecimal) (aplTreeObject[5]));
                    dichsRB.setTiDichAutor("ENTRY_MENU");
                    dichsTB.add(dichsRB);
                }
            }
        }
        return dichsTB;
    }

    /**
     * 
     * @param idEntryMenu
     *            id menu
     * 
     * @return table bean {@link AplEntryMenuTableBean}
     * 
     * @throws EMFError
     *             errore generico
     */
    @Deprecated
    public AplEntryMenuTableBean getEntryMenuParents(BigDecimal idEntryMenu) throws EMFError {
        List<AplEntryMenu> entries = amministrazioneRuoliHelper.getEntryMenuParentsList(idEntryMenu);
        AplEntryMenuTableBean entriesTB = new AplEntryMenuTableBean();
        try {
            if (entries != null && !entries.isEmpty()) {
                entriesTB = (AplEntryMenuTableBean) Transform.entities2TableBean(entries);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entriesTB;
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base al ruolo
     *
     * @param idUsoRuoloApplic
     *            lista distinta id ruolo applicativo
     * 
     * @return il table bean delle applicazioni
     * 
     * @throws EMFError
     *             errore generico
     */
    public PrfUsoRuoloApplicTableBean getPrfUsoRuoloApplicTableBean(Set<BigDecimal> idUsoRuoloApplic) throws EMFError {
        PrfUsoRuoloApplicTableBean usoRuoloApplicTableBean = new PrfUsoRuoloApplicTableBean();
        List<PrfUsoRuoloApplic> usoRuoloApplicList = amministrazioneRuoliHelper
                .getPrfUsoRuoloApplicList(idUsoRuoloApplic);
        try {
            for (PrfUsoRuoloApplic usoRuoloApplic : usoRuoloApplicList) {
                PrfUsoRuoloApplicRowBean usoRuoloApplicRowBean = (PrfUsoRuoloApplicRowBean) Transform
                        .entity2RowBean(usoRuoloApplic);
                usoRuoloApplicRowBean.setString("nm_applic", usoRuoloApplic.getAplApplic().getNmApplic());
                usoRuoloApplicRowBean.setString("ds_applic", usoRuoloApplic.getAplApplic().getDsApplic());
                usoRuoloApplicRowBean.setString("cd_versione_comp", usoRuoloApplic.getAplApplic().getCdVersioneComp());
                usoRuoloApplicTableBean.add(usoRuoloApplicRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return usoRuoloApplicTableBean;
    }

    public PrfRuoloCategoriaRowBean getPrfRuoloCategoriaRowBean(BigDecimal idRuoloCategoria) {
        PrfRuoloCategoriaRowBean ruoloCategRowBean = new PrfRuoloCategoriaRowBean();
        PrfRuoloCategoria ruoloCateg = amministrazioneRuoliHelper.findById(PrfRuoloCategoria.class, idRuoloCategoria);
        try {
            if (ruoloCateg != null) {
                ruoloCategRowBean = (PrfRuoloCategoriaRowBean) Transform.entity2RowBean(ruoloCateg);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoloCategRowBean;
    }

    public AplApplicRowBean getAplApplicRowBean(BigDecimal idApplic) {
        AplApplicRowBean applicRowBean = new AplApplicRowBean();
        AplApplic applic = amministrazioneRuoliHelper.getAplApplicById(idApplic);
        try {
            if (applic != null) {
                applicRowBean = (AplApplicRowBean) Transform.entity2RowBean(applic);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicRowBean;
    }

    public PrfDichAutorRowBean getPrfDichAutorRowBean(String tiScopoDichAutor, String tiDichAutor, BigDecimal idObject,
            BigDecimal idObject2, BigDecimal idPrfUsoRuoloApplic) {
        PrfDichAutorRowBean rb = null;
        List<PrfDichAutor> dichs = amministrazioneRuoliHelper.getPrfDichAutor(tiScopoDichAutor, tiDichAutor, idObject,
                idObject2, idPrfUsoRuoloApplic);
        try {
            if (dichs != null && !dichs.isEmpty() && dichs.size() == 1) {
                rb = (PrfDichAutorRowBean) Transform.entity2RowBean(dichs.get(0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rb;
    }

    /**
     * Metodo che ritorna dato un'entry menu la lista dei suoi figli
     *
     * @deprecated
     * 
     * @param idEntryMenu
     *            id menu
     * 
     * @return il tableBean contenente i figli dell'entry menu specificata
     */
    @Deprecated
    public AplEntryMenuTableBean getEntryMenuChilds(BigDecimal idEntryMenu) {
        AplEntryMenuTableBean entriesTB = new AplEntryMenuTableBean();
        List<AplEntryMenu> entries = amministrazioneRuoliHelper.getEntryMenuChilds(idEntryMenu);
        try {
            if (entries != null && !entries.isEmpty()) {
                entriesTB = (AplEntryMenuTableBean) Transform.entities2TableBean(entries);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entriesTB;
    }

    /**
     * Metodo che ritorna tutte le azioni di una pagina data come parametro
     *
     * @param idPaginaWeb
     *            l'id della pagina web
     * 
     * @return il tableBean contenente la lista di azioni
     */
    public AplAzionePaginaTableBean getActionChilds(BigDecimal idPaginaWeb) {
        AplAzionePaginaTableBean actionsTB = new AplAzionePaginaTableBean();
        List<AplAzionePagina> actions = amministrazioneRuoliHelper.getActionChilds(idPaginaWeb);
        try {
            if (actions != null && !actions.isEmpty()) {
                actionsTB = (AplAzionePaginaTableBean) Transform.entities2TableBean(actions);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return actionsTB;
    }

    /**
     * Metodo che ritorna la lista di azioni dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * @param idPaginaWeb
     *            id pagina web
     * 
     * @return il tableBean della lista di azioni
     */
    public AplAzionePaginaTableBean getActionsList(BigDecimal idApplicazione, BigDecimal idPaginaWeb) {
        AplAzionePaginaTableBean actionsTb = new AplAzionePaginaTableBean();
        List<AplAzionePagina> actions = amministrazioneRuoliHelper.getActionsList(idApplicazione, idPaginaWeb);
        try {
            if (actions != null && !actions.isEmpty()) {
                actionsTb = (AplAzionePaginaTableBean) Transform.entities2TableBean(actions);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return actionsTb;
    }

    public AplAzionePaginaTableBean getActionsFromPageList(BigDecimal idApplicazione, BigDecimal idPaginaWeb,
            Set<BigDecimal> azioniGiaPresenti) {
        AplAzionePaginaTableBean actionsTb = new AplAzionePaginaTableBean();
        List<AplAzionePagina> actions = amministrazioneRuoliHelper.getActionsPagesList(idApplicazione, idPaginaWeb,
                azioniGiaPresenti);
        try {
            if (actions != null && !actions.isEmpty()) {
                actionsTb = (AplAzionePaginaTableBean) Transform.entities2TableBean(actions);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return actionsTb;
    }

    public AplVTreeEntryMenuTableBean getEntryMenuList(BigDecimal idApplicazione, boolean ricercaFoglie,
            Set<String> nodiGiaPresenti) {
        AplVTreeEntryMenuTableBean entriesTB = new AplVTreeEntryMenuTableBean();
        List<AplVTreeEntryMenu> entries = amministrazioneRuoliHelper.getEntryMenuList2(idApplicazione, ricercaFoglie,
                nodiGiaPresenti);
        try {
            if (entries != null && !entries.isEmpty()) {
                entriesTB = (AplVTreeEntryMenuTableBean) Transform.entities2TableBean(entries);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entriesTB;
    }

    public AplVTreeMenuPagAzioTableBean getRoleTreeTableBean(BigDecimal idApplicazione, String tiDichAutor) {
        AplVTreeMenuPagAzioTableBean entriesTB = new AplVTreeMenuPagAzioTableBean();
        List<AplVTreeMenuPagAzio> entries = amministrazioneRuoliHelper.getRoleTreeNodesList(idApplicazione,
                tiDichAutor);
        try {
            if (entries != null && !entries.isEmpty()) {
                entriesTB = (AplVTreeMenuPagAzioTableBean) Transform.entities2TableBean(entries);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entriesTB;
    }

    // public AplVTreeEntryMenuTableBean getRoleTreeTableBean(BigDecimal idApplicazione, BigDecimal idUsoRuoloApplic) {
    // AplVTreeEntryMenuTableBean entriesTB = new AplVTreeEntryMenuTableBean();
    // List<AplVTreeEntryMenuCustom> entries = amministrazioneRuoliHelper.getRoleTreeTableBean(idApplicazione,
    // idUsoRuoloApplic);
    // try {
    // if (entries != null && !entries.isEmpty()) {
    // for (AplVTreeEntryMenuCustom entry : entries) {
    // AplVTreeEntryMenuCustomRowBean row = (AplVTreeEntryMenuCustomRowBean) Transform.entity2RowBean(entry);
    // row.setBigDecimal("id_dich_autor", entry.getIdDichAutor());
    // entriesTB.add(row);
    // }
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage(), e);
    // }
    // return entriesTB;
    // }
    public AplPaginaWebTableBean getPagesList(BigDecimal idApplicazione, Set<String> nodiGiaPresenti) {
        AplPaginaWebTableBean pagesTb = new AplPaginaWebTableBean();
        List<AplPaginaWeb> pages = amministrazioneRuoliHelper.getPagesList(idApplicazione, nodiGiaPresenti);
        try {
            if (pages != null && !pages.isEmpty()) {
                pagesTb = (AplPaginaWebTableBean) Transform.entities2TableBean(pages);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pagesTb;
    }

    public Set<PairAuth<Map<BigDecimal, String>, String>> getAllPagesListPairAuth(BigDecimal idApplicazione,
            Set<PairAuth<Map<BigDecimal, String>, String>> paginaAzioneGiaInserite) {

        Set<PairAuth<Map<BigDecimal, String>, String>> paginaAzioneDaDB = new HashSet<PairAuth<Map<BigDecimal, String>, String>>();
        List<AplPaginaWeb> pages = amministrazioneRuoliHelper.getPagesList(idApplicazione, null);
        for (AplPaginaWeb page : pages) {
            for (AplAzionePagina azione : page.getAplAzionePaginas()) {
                Map<BigDecimal, String> pagina = new HashMap<BigDecimal, String>();
                pagina.put(new BigDecimal(page.getIdPaginaWeb()), page.getDsPaginaWeb());
                PairAuth pair = new PairAuth(pagina, azione.getDsAzionePagina());
                paginaAzioneDaDB.add(pair);
            }
        }

        paginaAzioneDaDB.removeAll(paginaAzioneGiaInserite);
        return paginaAzioneDaDB;
    }

    public AplPaginaWebTableBean getPagesAzioniList(BigDecimal idApplicazione, Set<BigDecimal> nodiGiaPresenti) {
        AplPaginaWebTableBean pagesTb = new AplPaginaWebTableBean();
        List<AplPaginaWeb> pages = amministrazioneRuoliHelper.getPagesAzioniList(idApplicazione, nodiGiaPresenti);
        try {
            if (pages != null && !pages.isEmpty()) {
                pagesTb = (AplPaginaWebTableBean) Transform.entities2TableBean(pages);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pagesTb;
    }

    public AplServizioWebTableBean getServicesList(BigDecimal idApplicazione, Set<String> nodiGiaPresenti) {
        AplServizioWebTableBean servicesTb = new AplServizioWebTableBean();
        List<AplServizioWeb> services = amministrazioneRuoliHelper.getServicesList(idApplicazione, nodiGiaPresenti);
        try {
            if (services != null && !services.isEmpty()) {
                servicesTb = (AplServizioWebTableBean) Transform.entities2TableBean(services);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return servicesTb;
    }

    public BigDecimal getIdUsoRuoloApplic(BigDecimal idRuolo, BigDecimal idAppl) {
        List<Long> usoRuoloApplic = amministrazioneRuoliHelper.getIdUsoRuoloApplic(idRuolo, idAppl);
        if (!usoRuoloApplic.isEmpty() && usoRuoloApplic.size() == 1) {
            return new BigDecimal(usoRuoloApplic.get(0).longValue());
        } else {
            return null;
        }
    }

    public String getAplApplicFromAction(BigDecimal idAzionePagina) {
        AplAzionePagina azione = amministrazioneRuoliHelper.getAplAzionePagina(idAzionePagina);
        return azione.getAplPaginaWeb().getAplApplic().getNmApplic();
    }

    public boolean checkIsUniqueRole(String nmRuoloPreMod, String nmRuoloPostMod, Status status) {
        return amministrazioneRuoliHelper.checkIsUniqueRole(nmRuoloPreMod, nmRuoloPostMod, status);
    }

    public BigDecimal getTotalMenus(BigDecimal idApplic) {
        return new BigDecimal(amministrazioneRuoliHelper.countMenusList(idApplic));
    }

    public BigDecimal getTotalPages(BigDecimal idApplic) {
        return new BigDecimal(amministrazioneRuoliHelper.countPagesList(idApplic));
    }

    public BigDecimal getTotalActions(BigDecimal idApplic) {
        return new BigDecimal(amministrazioneRuoliHelper.countActionsList(idApplic));
    }

    public enum AllineaAmbiente {
        ALLINEA_AMBIENTE_1, ALLINEA_AMBIENTE_2;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateStatoAllineamentoPrfRuolo(BigDecimal idRuolo, String statoAllinea, String esitoAllineamento,
            AllineaAmbiente ambiente) {
        PrfRuolo ruolo = amministrazioneRuoliHelper.findById(PrfRuolo.class, idRuolo);
        switch (ambiente) {
        case ALLINEA_AMBIENTE_1:
            ruolo.setTiStatoRichAllineaRuoli_1(statoAllinea);
            ruolo.setDsEsitoRichAllineaRuoli_1(esitoAllineamento);
            break;
        case ALLINEA_AMBIENTE_2:
            ruolo.setTiStatoRichAllineaRuoli_2(statoAllinea);
            ruolo.setDsEsitoRichAllineaRuoli_2(esitoAllineamento);
            break;
        default:
            throw new AssertionError(ambiente.name());
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateEsitoAllineamentoPrfRuolo(BigDecimal idRuolo, String esitoAllineamento,
            AllineaAmbiente ambiente) {
        PrfRuolo ruolo = amministrazioneRuoliHelper.findById(PrfRuolo.class, idRuolo);
        switch (ambiente) {
        case ALLINEA_AMBIENTE_1:
            ruolo.setDsEsitoRichAllineaRuoli_1(esitoAllineamento);
            break;
        case ALLINEA_AMBIENTE_2:
            ruolo.setDsEsitoRichAllineaRuoli_2(esitoAllineamento);
            break;
        default:
            throw new AssertionError(ambiente.name());
        }
    }

    public ListaApplic getListaApplics(BigDecimal idRuolo) {
        ListaApplic listaApplics = new ListaApplic();
        Set<String> nmApplics = new HashSet<>();
        List<PrfVLisDichAutorDaAllin> dichAutors = amministrazioneRuoliHelper.retrievePrfVLisDichAutorDaAllin(idRuolo);
        Applic applic;
        List<DichAutor> dichAutorsApplic = new ArrayList<>();
        for (PrfVLisDichAutorDaAllin dichAutor : dichAutors) {
            if (nmApplics.add(dichAutor.getNmApplic())) {
                applic = new Applic();
                applic.setNmApplic(dichAutor.getNmApplic());
                applic.setCdVersioneComp(dichAutor.getCdVersioneComp());
                applic.setListaDichAutor(new ListaDichAutor());
                dichAutorsApplic = applic.getListaDichAutor().getDichAutor();
                listaApplics.getApplic().add(applic);
            }
            DichAutor autor = new DichAutor();
            autor.setDsPathEntryMenuFoglia(dichAutor.getDsPathEntryMenuFoglia());
            autor.setDsPathEntryMenuPadre(dichAutor.getDsPathEntryMenuPadre());
            autor.setNmAzionePagina(dichAutor.getNmAzionePagina());
            autor.setNmPaginaWeb(dichAutor.getNmPaginaWeb());
            autor.setNmServizioWeb(dichAutor.getNmServizioWeb());
            autor.setTiDichAutor(dichAutor.getTiDichAutor());
            autor.setTiScopoDichAutor(dichAutor.getTiScopoDichAutor());
            dichAutorsApplic.add(autor);
        }
        return listaApplics;
    }

    public ListaCategRuolo getListaCategRuolos(BigDecimal idRuolo) {
        ListaCategRuolo listaCategRuolos = new ListaCategRuolo();
        Set<String> tiCategRuolos = new HashSet<>();
        List<PrfRuoloCategoria> lista = amministrazioneRuoliHelper.retrievePrfRuoloCategoria(idRuolo);
        // CategRuolo categRuolo;

        for (PrfRuoloCategoria ruoloCategoria : lista) {
            if (tiCategRuolos.add(ruoloCategoria.getTiCategRuolo())) {
                // categRuolo = new CategRuolo();
                // categRuolo.setTiCategRuolo(ruoloCategoria.getTiCategRuolo());
                listaCategRuolos.getTiCategRuolo().add(ruoloCategoria.getTiCategRuolo());
            }
        }

        return listaCategRuolos;
    }

    public String checkCategoriaRuoloModificata(BigDecimal idRuolo,
            PrfRuoloCategoriaTableBean ruoloCategoriaTableBean) {
        String error = "";
        PrfRuolo ruolo = amministrazioneRuoliHelper.findById(PrfRuolo.class, idRuolo);
        List<PrfRuoloCategoria> ruoloCategoriaList = ruolo.getPrfRuoloCategorias();
        List<String> tiCategRuoloList = new ArrayList<>();
        for (PrfRuoloCategoria ruoloCategoria : ruoloCategoriaList) {
            tiCategRuoloList.add(ruoloCategoria.getTiCategRuolo());
        }

        // Se ho modificato la categoria
        for (PrfRuoloCategoriaRowBean rb : ruoloCategoriaTableBean) {
            String tiCategRuolo = rb.getTiCategRuolo();
            if (!tiCategRuoloList.contains(tiCategRuolo)) {
                // Determina gli utenti con stato corrente diverso da CESSATO che utilizzano il ruolo come ruolo di
                // default
                List<UsrUser> listaUtenti = amministrazioneRuoliHelper.getUtentiUsoRuoloUserDefaultList(idRuolo,
                        Arrays.asList(TiStatotUser.CESSATO.name()));

                for (UsrUser user : listaUtenti) {
                    if (tiCategRuolo.equals("amministrazione")
                            && !TiEnteConvenz.AMMINISTRATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())) {
                        error = "Il ruolo non può assumere categoria pari a amministrazione perché usato da utenti che non appartengono ad ente di tipo amministratore";
                        break;
                    }

                    if (tiCategRuolo.equals("conservazione")
                            && !TiEnteConvenz.AMMINISTRATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())
                            && !TiEnteConvenz.CONSERVATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())) {
                        error = "Il ruolo non può assumere categoria pari a conservazione perché usato da utenti che non appartengono ad ente di tipo amministratore o conservatore";
                        break;
                    }

                    if (tiCategRuolo.equals("gestione")
                            && !TiEnteConvenz.AMMINISTRATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())
                            && !TiEnteConvenz.CONSERVATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())
                            && !TiEnteConvenz.GESTORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())) {
                        error = "Il ruolo non può assumere categoria pari a gestione perché usato da utenti che non appartengono ad ente di tipo amministratore o conservatore o gestore";
                        break;
                    }

                    if (tiCategRuolo.equals("vigilanza")
                            && !TiEnteNonConvenz.ORGANO_VIGILANZA.equals(user.getOrgEnteSiam().getTiEnteNonConvenz())) {
                        error = "Il ruolo non può assumere categoria pari a vigilanza perché usato da utenti che non appartengono ad ente di tipo organo di vigilanza";
                        break;
                    }
                }

                if (error.equals("")) {
                    // Determina gli utenti con stato corrente diverso da CESSATO che utilizzano il ruolo come ruolo per
                    // organizzazione
                    List<UsrUser> listaUtentiOrganiz = amministrazioneRuoliHelper.getUtentiUsoRuoloOrganizList(idRuolo,
                            Arrays.asList(TiStatotUser.CESSATO.name()));
                    for (UsrUser user : listaUtentiOrganiz) {
                        if (tiCategRuolo.equals("amministrazione")
                                && !TiEnteConvenz.AMMINISTRATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())) {
                            error = "Il ruolo non può assumere categoria pari a amministrazione perché usato da utenti che non appartengono ad ente di tipo amministratore";
                            break;
                        }

                        if (tiCategRuolo.equals("conservazione")
                                && !TiEnteConvenz.AMMINISTRATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())
                                && !TiEnteConvenz.CONSERVATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())) {
                            error = "Il ruolo non può assumere categoria pari a conservazione perché usato da utenti che non appartengono ad ente di tipo amministratore o conservatore";
                            break;
                        }

                        if (tiCategRuolo.equals("gestione")
                                && !TiEnteConvenz.AMMINISTRATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())
                                && !TiEnteConvenz.CONSERVATORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())
                                && !TiEnteConvenz.GESTORE.equals(user.getOrgEnteSiam().getTiEnteConvenz())) {
                            error = "Il ruolo non può assumere categoria pari a gestione perché usato da utenti che non appartengono ad ente di tipo amministratore o conservatore o gestore";
                            break;
                        }

                        if (tiCategRuolo.equals("vigilanza") && !TiEnteNonConvenz.ORGANO_VIGILANZA
                                .equals(user.getOrgEnteSiam().getTiEnteNonConvenz())) {
                            error = "Il ruolo non può assumere categoria pari a vigilanza perché usato da utenti che non appartengono ad ente di tipo organo di vigilanza";
                            break;
                        }
                    }
                }
            }
        }
        return error;
    }

    public String checkTipoRuoloModificato(BigDecimal idRuolo, String tiRuolo) {
        String error = "";
        PrfRuolo ruolo = amministrazioneRuoliHelper.findById(PrfRuolo.class, idRuolo);

        // Se ho modificato il tipo ruolo
        if (!ruolo.getTiRuolo().equals(tiRuolo)) {
            // Determina gli utenti con stato corrente diverso da CESSATO che utilizzano il ruolo come ruolo di default
            List<UsrUser> listaUtenti = amministrazioneRuoliHelper.getUtentiUsoRuoloUserDefaultList(idRuolo,
                    Arrays.asList(TiStatotUser.CESSATO.name()));

            for (UsrUser user : listaUtenti) {
                if (tiRuolo.equals("AUTOMA") && user.getTipoUser().equals("PERSONA_FISICA")) {
                    error = "Il ruolo non può diventare di tipo AUTOMA perché usato da utenti di tipo PERSONA_FISICA";
                    break;
                }

                if (tiRuolo.equals("PERSONA_FISICA") && user.getTipoUser().equals("AUTOMA")) {
                    error = "Il ruolo non può diventare di tipo PERSONA_FISICA perché usato da utenti di tipo AUTOMA";
                    break;
                }

            }

            if (!error.equals("")) {
                // Determina gli utenti con stato corrente diverso da CESSATO che utilizzano il ruolo come ruolo per
                // organizzazione
                List<UsrUser> listaUtentiOrganiz = amministrazioneRuoliHelper.getUtentiUsoRuoloOrganizList(idRuolo,
                        Arrays.asList(TiStatotUser.CESSATO.name()));
                for (UsrUser user : listaUtentiOrganiz) {
                    if (tiRuolo.equals("AUTOMA") && user.getTipoUser().equals("PERSONA_FISICA")) {
                        error = "Il ruolo non può diventare di tipo AUTOMA perché usato da utenti di tipo PERSONA_FISICA";
                        break;
                    }

                    if (tiRuolo.equals("PERSONA_FISICA") && user.getTipoUser().equals("AUTOMA")) {
                        error = "Il ruolo non può diventare di tipo PERSONA_FISICA perché usato da utenti di tipo AUTOMA";
                        break;
                    }

                }
            }
        }
        return error;
    }

    public PrfRuoloCategoriaTableBean getPrfRuoloCategoriaTableBean(BigDecimal idRuolo) throws EMFError {
        List<PrfRuoloCategoria> categorie = amministrazioneRuoliHelper.getPrfRuoloCategoriaList(idRuolo);
        PrfRuoloCategoriaTableBean categorieTB = new PrfRuoloCategoriaTableBean();
        try {
            if (!categorie.isEmpty()) {
                categorieTB = (PrfRuoloCategoriaTableBean) Transform.entities2TableBean(categorie);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException("Impossibile recuperare le categorie del ruolo");
        }
        return categorieTB;
    }

}
