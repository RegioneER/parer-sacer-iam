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

package it.eng.saceriam.ws.rest.ejb;

import it.eng.saceriam.entity.AplHelpOnLine;
import it.eng.saceriam.slite.gen.viewbean.AplVVisHelpOnLineRowBean;
import it.eng.saceriam.slite.gen.viewbean.AplVVisHelpOnLineTableBean;
import it.eng.saceriam.viewEntity.AplVVisHelpOnLine;
import it.eng.saceriam.web.util.Transform;
import it.eng.saceriam.ws.rest.helper.RecuperoHelpHelper;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.security.auth.WSLoginHandler;
import it.eng.spagoLite.security.exception.AuthWSException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Iacolucci_M
 */
@Stateless(mappedName = "RecuperoHelpEjb")
@LocalBean
public class RecuperoHelpEjb {

    private static final Logger log = LoggerFactory.getLogger(RecuperoHelpEjb.class);

    @EJB
    private RecuperoHelpHelper recuperoHelpHelper;

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    /*
     * Cerca l'help attivo ad Oggi
     */
    public AplVVisHelpOnLine recuperoHelp(String nmApplic, String tiHelpOnLine, String nmPaginaWeb,
            String nmEntryMenu) {

        return recuperoHelpHelper.recuperoHelp(nmApplic, tiHelpOnLine, nmPaginaWeb, nmEntryMenu, new Date());
    }

    public AplVVisHelpOnLineTableBean getAplVVisHelpOnLineTableBean(BigDecimal idApplic, String tiHelpOnLine,
            Date dtRiferimento, BigDecimal idPaginaWeb, BigDecimal idEntryMenu) throws EMFError {
        AplVVisHelpOnLineTableBean aplVVisHelpOnLineTableBean = new AplVVisHelpOnLineTableBean();
        List<AplVVisHelpOnLine> l = null;
        l = recuperoHelpHelper.getHelpBetweenDate(idApplic, tiHelpOnLine, dtRiferimento, idPaginaWeb, idEntryMenu);
        try {
            if (l != null && !l.isEmpty()) {
                aplVVisHelpOnLineTableBean = (AplVVisHelpOnLineTableBean) Transform.entities2TableBean(l);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return aplVVisHelpOnLineTableBean;
    }

    public AplHelpOnLine inserisciHelp(long idApplic, String idTipoHelp, String fileName, long idPaginaWeb,
            long idEntryMenu, Date dataIni, Date dataFine, String blHelpOnLine, byte[] blSorgenteHelpOnLine) {

        return recuperoHelpHelper.inserisciHelp(idApplic, idTipoHelp, fileName, idPaginaWeb, idEntryMenu, dataIni,
                dataFine, blHelpOnLine, blSorgenteHelpOnLine);
    }

    public AplHelpOnLine modificaHelp(BigDecimal idHelp, String fileName, Date dataIni, Date dataFine,
            String blHelpOnLine, byte[] blSorgenteHelpOnLine) {

        return recuperoHelpHelper.modificaHelp(idHelp, fileName, dataIni, dataFine, blHelpOnLine, blSorgenteHelpOnLine);
    }

    public Date findMostRecentDtFin(long idApplic, String idTipoHelp, long idPaginaWeb, long idEntryMenu) {
        Date data = recuperoHelpHelper.findMostRecentDtFin(idApplic, idTipoHelp, idPaginaWeb, idEntryMenu);

        return data;
    }

    /*
     * Verifica di intersezione in caso di modifica del record. deve escludere il record corrente
     */
    public boolean intersectsExistingHelp(BigDecimal idHelp, Date dataInizio, Date dataFine) {
        boolean retCode = false;

        AplHelpOnLine apl = recuperoHelpHelper.findById(idHelp);
        if (apl != null) {
            long idMenu = 0;
            if (apl.getAplEntryMenu() != null) {
                idMenu = apl.getAplEntryMenu().getIdEntryMenu();
            }
            retCode = recuperoHelpHelper.intersectsExistingHelp(idHelp.longValueExact(),
                    apl.getAplApplic().getIdApplic(), apl.getTiHelpOnLine(), apl.getAplPaginaWeb().getIdPaginaWeb(),
                    idMenu, dataInizio, dataFine);
        }
        return retCode;
    }

    /*
     * Verifica di intersezione in caso di inserimento del record. deve includere tutti i record
     */
    public boolean intersectsExistingHelp(long idApplic, String tiHelpOnLine, long idPaginaWeb, long idEntryMenu,
            Date dataInizio, Date dataFine) {
        return recuperoHelpHelper.intersectsExistingHelp(0, idApplic, tiHelpOnLine, idPaginaWeb, idEntryMenu,
                dataInizio, dataFine);
    }

    public AplVVisHelpOnLineRowBean findVistaById(BigDecimal idHelp) {

        AplVVisHelpOnLineRowBean rowBean = null;
        AplVVisHelpOnLine entity = recuperoHelpHelper.findVistaById(idHelp);
        try {
            rowBean = (AplVVisHelpOnLineRowBean) Transform.entity2RowBean(entity);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            log.error("Errore durante il recupero della traccia delle versioni serie"
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
        }
        return rowBean;
    }

    public AplHelpOnLine findById(BigDecimal idHelp) {
        return recuperoHelpHelper.findById(idHelp);
    }

    public void cancellaHelp(BigDecimal idHelp) {
        recuperoHelpHelper.cancellaHelp(idHelp);
    }

    public boolean loginAndAuth(String utente, String password, String servizioWeb) throws AuthWSException {
        return WSLoginHandler.loginAndCheckAuthzIAM(utente, password, servizioWeb, null, em);
    }

    public boolean appExists(String nomeApplicazione) throws AuthWSException {
        return WSLoginHandler.appExists(nomeApplicazione, em);
    }

    public long getIdPaginaWebInfoPrivacy(long idApplic) {
        return recuperoHelpHelper.getIdPaginaWebApplicativo(idApplic);
    }

}
