package it.eng.saceriam.ws.rest.helper;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplHelpOnLine;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.viewEntity.AplVVisHelpOnLine;
import it.eng.spagoLite.security.auth.WSLoginHandler;
import it.eng.spagoLite.security.exception.AuthWSException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author Iacolucci_M
 */
@Stateless(mappedName = "RecuperoHelpEjb")
@LocalBean
public class RecuperoHelpHelper {

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    public AplVVisHelpOnLine recuperoHelp(String nmApplic, String tiHelpOnLine, String nmPaginaWeb, String nmEntryMenu,
            Date dtRiferimento) {
        /*
         * Le ricerche possono avvenire per nome pagina oppure per nume pagina + nome entru menu, sia per dispenser che
         * per tutte le altre applicazioni
         */
        Query q = null;
        if (nmEntryMenu != null && (!nmEntryMenu.trim().equals(""))) {
            q = em.createNamedQuery("selHelpByIdApplicTiHelpNmPaginaNmEntryMenu", AplVVisHelpOnLine.class);
            q.setParameter("nmApplic", nmApplic);
            q.setParameter("tiHelpOnLine", tiHelpOnLine);
            q.setParameter("nmPaginaWeb", nmPaginaWeb);
            q.setParameter("nmEntryMenu", nmEntryMenu);
            q.setParameter("dtRiferimento", dtRiferimento);
        } else {
            q = em.createNamedQuery("selHelpByIdApplicTiHelpNmPagina", AplVVisHelpOnLine.class);
            q.setParameter("nmApplic", nmApplic);
            q.setParameter("tiHelpOnLine", tiHelpOnLine);
            q.setParameter("nmPaginaWeb", nmPaginaWeb);
            q.setParameter("dtRiferimento", dtRiferimento);
        }

        // String tipoOrganiz = getTipoOrganizApplic(nmApplic);
        List<AplVVisHelpOnLine> l = q.getResultList();
        AplVVisHelpOnLine v = null;
        if (l != null && l.size() > 0) {
            v = l.get(0);
        }

        return v;
    }

    public AplVVisHelpOnLine findVistaById(BigDecimal idHelp) {
        return em.find(AplVVisHelpOnLine.class, idHelp);
    }

    public AplHelpOnLine findById(BigDecimal idHelp) {
        return em.find(AplHelpOnLine.class, idHelp.longValueExact());
    }

    public List<AplVVisHelpOnLine> getHelpBetweenDate(BigDecimal idApplic, String tiHelpOnLine, Date dtRiferimento,
            BigDecimal idPaginaWeb, BigDecimal idEntryMenu) {
        List<AplVVisHelpOnLine> l = null;
        Query q = null;

        if (dtRiferimento == null) {
            if (idPaginaWeb == null) {
                // RICERCA SENZA DATA
                q = em.createNamedQuery("selHelpByIdApplicTiHelp", AplVVisHelpOnLine.class);
            } else if (idEntryMenu == null) {
                // RICERCA SENZA DATA PER PAGINA
                q = em.createNamedQuery("selHelpByIdApplicTiHelpAndPage", AplVVisHelpOnLine.class);
                q.setParameter("idPaginaWeb", idPaginaWeb);
            } else {
                // RICERCA SENZA DATA PER PAGINA e PER MENU
                q = em.createNamedQuery("selHelpByIdApplicTiHelpAndPageAndMenu", AplVVisHelpOnLine.class);
                q.setParameter("idPaginaWeb", idPaginaWeb);
                q.setParameter("idEntryMenu", idEntryMenu);
            }
        } else {
            if (idPaginaWeb == null) {
                if (idEntryMenu != null) {
                    // RICERCA PER DATA E PAGINA E MENU
                    q = em.createNamedQuery("selHelpByIdApplicTiHelpBetweenDateAndPageAndMenu",
                            AplVVisHelpOnLine.class);
                    q.setParameter("idPaginaWeb", idPaginaWeb);
                    q.setParameter("idEntryMenu", idEntryMenu);
                } else {
                    // RICERCA PER DATA
                    q = em.createNamedQuery("selHelpByIdApplicTiHelpBetweenDate", AplVVisHelpOnLine.class);
                }
            } else if (idEntryMenu == null) {
                // RICERCA PER DATA E PAGINA
                q = em.createNamedQuery("selHelpByIdApplicTiHelpBetweenDateAndPage", AplVVisHelpOnLine.class);
                q.setParameter("idPaginaWeb", idPaginaWeb);
            } else {
                // RICERCA PER DATA E PAGINA E MENU
                q = em.createNamedQuery("selHelpByIdApplicTiHelpBetweenDateAndPageAndMenu", AplVVisHelpOnLine.class);
                q.setParameter("idPaginaWeb", idPaginaWeb);
                q.setParameter("idEntryMenu", idEntryMenu);
            }

            q.setParameter("dtRiferimento", dtRiferimento);
        }
        q.setParameter("idApplic", idApplic);
        q.setParameter("tiHelpOnLine", tiHelpOnLine);
        l = q.getResultList();
        return l;
    }

    public boolean loginAndAuth(String utente, String password, String servizioWeb) throws AuthWSException {
        return WSLoginHandler.loginAndCheckAuthzIAM(utente, password, servizioWeb, null, em);
    }

    public boolean appExists(String nomeApplicazione) throws AuthWSException {
        return WSLoginHandler.appExists(nomeApplicazione, em);
    }

    /*
     * Inserisce un help
     */
    public AplHelpOnLine inserisciHelp(long idApplic, String idTipoHelp, String fileName, long idPaginaWeb,
            long idEntryMenu, Date dataIni, Date datafine, String blHelpOnLine, byte[] blSorgenteHelpOnLine) {
        AplHelpOnLine help = new AplHelpOnLine();
        help.setAplApplic(em.find(AplApplic.class, idApplic));
        help.setTiHelpOnLine(idTipoHelp);
        help.setDsFileHelpOnLine(fileName);
        help.setAplPaginaWeb(em.find(AplPaginaWeb.class, idPaginaWeb));
        help.setAplEntryMenu(em.find(AplEntryMenu.class, idEntryMenu));
        help.setDtIniVal(dataIni);
        help.setDtFineVal(datafine);
        help.setBlHelpOnLine(blHelpOnLine);
        help.setBlSorgenteHelpOnLine(blSorgenteHelpOnLine);
        em.persist(help);
        em.flush();
        return help;
    }

    /*
     * Modifica un help
     */
    public AplHelpOnLine modificaHelp(BigDecimal idHelp, String fileName, Date dataIni, Date datafine,
            String blHelpOnLine, byte[] blSorgenteHelpOnLine) {

        AplHelpOnLine help = em.find(AplHelpOnLine.class, idHelp.longValueExact());
        if (help != null) {
            if (fileName != null) {
                help.setDsFileHelpOnLine(fileName);
            }
            help.setDtIniVal(dataIni);
            help.setDtFineVal(datafine);
            if (blHelpOnLine != null) {
                help.setBlHelpOnLine(blHelpOnLine);
            }
            if (blSorgenteHelpOnLine != null) {
                help.setBlSorgenteHelpOnLine(blSorgenteHelpOnLine);
            }
            em.persist(help);
            em.flush();
        }
        return help;
    }

    public void cancellaHelp(BigDecimal idHelp) {
        AplHelpOnLine help = em.find(AplHelpOnLine.class, idHelp.longValueExact());
        if (help != null) {
            em.remove(help);
        }
    }

    /*
     * Torna NULL se non esiste alcun help per i parametri indicati oppure se c'è un help attivo con data fine infinita.
     */
    public Date findMostRecentDtFin(long idApplic, String tiHelpOnLine, long idPaginaWeb, long idEntryMenu) {
        Date data = null;

        Query q = null;
        if (idEntryMenu != 0) {
            q = em.createNamedQuery("AplHelpOnLine.findMaxDtFinWithMenu");
            q.setParameter("idEntryMenu", idEntryMenu);
        } else {
            q = em.createNamedQuery("AplHelpOnLine.findMaxDtFinWithoutMenu");
        }
        q.setParameter("idApplic", idApplic);
        q.setParameter("tiHelpOnLine", tiHelpOnLine);
        q.setParameter("idPaginaWeb", idPaginaWeb);

        List<Timestamp> list = q.getResultList();
        for (Timestamp timestamp : list) {
            if (timestamp == null) {
                break;
            }
            data = new Date(timestamp.getTime());
            data = DateUtils.truncate(data, Calendar.DATE);
            // Se c'è una data infinita annula la data ed esce...
            if (data.equals(it.eng.saceriam.util.DateUtil.MAX_DATE)) {
                data = null;
                break;
            }
        }

        return data;
    }

    /*
     * Torna TRUE se l'Help passato come parametro (applicazione + tipo + pagina + menu+ range di date)
     */
    public boolean intersectsExistingHelp(long idHelpOnLine, long idApplic, String tiHelpOnLine, long idPaginaWeb,
            long idEntryMenu, Date dataInizio, Date dataFine) {
        boolean recordExists = false;

        Query q = null;
        if (idEntryMenu != 0) {
            if (idHelpOnLine != 0) {
                q = em.createNamedQuery("AplHelpOnLine.existsIntersectionByMenuExcludingHelp");
                q.setParameter("idHelpOnLine", idHelpOnLine);
            } else {
                q = em.createNamedQuery("AplHelpOnLine.existsIntersectionByMenu");
            }
            q.setParameter("idEntryMenu", idEntryMenu);
        } else {
            if (idHelpOnLine != 0) {
                q = em.createNamedQuery("AplHelpOnLine.existsIntersectionByPageExcludingHelp");
                q.setParameter("idHelpOnLine", idHelpOnLine);
            } else {
                q = em.createNamedQuery("AplHelpOnLine.existsIntersectionByPage");
            }
        }
        q.setParameter("idApplic", idApplic);
        q.setParameter("tiHelpOnLine", tiHelpOnLine);
        q.setParameter("idPaginaWeb", idPaginaWeb);
        q.setParameter("dataInizio", dataInizio);
        q.setParameter("dataFine", dataFine);

        List list = q.getResultList();
        if (list.size() > 0) {
            recordExists = true;
        }
        return recordExists;
    }

}
