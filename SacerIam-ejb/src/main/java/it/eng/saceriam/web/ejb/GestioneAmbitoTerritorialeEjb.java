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

package it.eng.saceriam.web.ejb;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.entity.OrgAmbitoTerrit;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritTableBean;
import it.eng.saceriam.web.helper.GestioneAmbitoTerritorialeHelper;
import it.eng.saceriam.web.util.Transform;

/**
 * Ejb Gestione Ambito Territoriale di SacerIam
 *
 * @author DiLorenzo_F
 */
@Stateless
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class GestioneAmbitoTerritorialeEjb {

    public GestioneAmbitoTerritorialeEjb() {
    }

    @EJB
    private GestioneAmbitoTerritorialeHelper gestioneAmbitoTerritorialeHelper;
    private static final Logger log = LoggerFactory.getLogger(GestioneAmbitoTerritorialeEjb.class);

    public OrgAmbitoTerritTableBean getOrgAmbitoTerritTableBean(String tipo) {
        OrgAmbitoTerritTableBean tableBean = new OrgAmbitoTerritTableBean();
        List<OrgAmbitoTerrit> list = gestioneAmbitoTerritorialeHelper.getOrgAmbitoTerritList(tipo);

        if (list != null) {
            try {
                tableBean = (OrgAmbitoTerritTableBean) Transform.entities2TableBean(list);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return tableBean;
    }

    private boolean nodeOrgAmbitoTerritHasChild(String cdAmbitoTerritoriale, BigDecimal idToRemove) {
        if (idToRemove == null) {
            idToRemove = new BigDecimal(gestioneAmbitoTerritorialeHelper.getOrgAmbitoTerritByCode(cdAmbitoTerritoriale)
                    .getIdAmbitoTerrit());
        }
        if (gestioneAmbitoTerritorialeHelper.getOrgAmbitoTerritChildList(idToRemove) != null) {
            return true;
        }

        return false;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateOrgAmbitoTerritoriale(BigDecimal idToUpdate, OrgAmbitoTerritRowBean orgAmbitoTerritRowBean)
            throws ParerUserError {
        OrgAmbitoTerrit orgAmbitoTerritDB = gestioneAmbitoTerritorialeHelper
                .getOrgAmbitoTerritByCode(orgAmbitoTerritRowBean.getCdAmbitoTerrit());
        if (orgAmbitoTerritDB != null && orgAmbitoTerritDB.getIdAmbitoTerrit() != idToUpdate.longValue()) {
            throw new ParerUserError("Codice già utilizzato per altri nodi, usare altro codice </br>");
        }

        OrgAmbitoTerrit orgAmbitoTerritoriale = gestioneAmbitoTerritorialeHelper.findById(OrgAmbitoTerrit.class,
                idToUpdate);
        orgAmbitoTerritoriale.setCdAmbitoTerrit(orgAmbitoTerritRowBean.getCdAmbitoTerrit());
        orgAmbitoTerritoriale.setTiAmbitoTerrit(orgAmbitoTerritRowBean.getTiAmbitoTerrit());
        orgAmbitoTerritoriale.setOrgAmbitoTerrit(
                orgAmbitoTerritRowBean.getIdAmbitoTerritPadre() != null ? gestioneAmbitoTerritorialeHelper
                        .findById(OrgAmbitoTerrit.class, orgAmbitoTerritRowBean.getIdAmbitoTerritPadre()) : null);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertOrgAmbitoTerritoriale(OrgAmbitoTerritRowBean orgAmbitoTerritRowBean) throws ParerUserError {
        if (orgAmbitoTerritRowBean != null) {
            if (gestioneAmbitoTerritorialeHelper
                    .getOrgAmbitoTerritByCode(orgAmbitoTerritRowBean.getCdAmbitoTerrit()) != null) {
                throw new ParerUserError("Codice gi\u00E0 utilizzato per altri nodi, usare altro codice </br>");
            }
        }
        OrgAmbitoTerrit orgAmbitoTerritoriale = (OrgAmbitoTerrit) Transform.rowBean2Entity(orgAmbitoTerritRowBean);
        gestioneAmbitoTerritorialeHelper.insertEntity(orgAmbitoTerritoriale, true);
    }

    public OrgAmbitoTerritRowBean getOrgAmbitoTerritRowBean(String cdAmbitoTerritoriale) {
        OrgAmbitoTerritRowBean orgAmbitoTerritRowBean = new OrgAmbitoTerritRowBean();
        OrgAmbitoTerrit orgAmbitoTerrit = gestioneAmbitoTerritorialeHelper
                .getOrgAmbitoTerritByCode(cdAmbitoTerritoriale);

        if (orgAmbitoTerrit != null) {
            try {
                orgAmbitoTerritRowBean = (OrgAmbitoTerritRowBean) Transform.entity2RowBean(orgAmbitoTerrit);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                log.error("Errore", ex);
            }
        }
        return orgAmbitoTerritRowBean;
    }

    public OrgAmbitoTerritRowBean getOrgAmbitoTerritRowBean(BigDecimal idAmbitoTerritoriale) {
        OrgAmbitoTerritRowBean orgAmbitoTerritRowBean = new OrgAmbitoTerritRowBean();
        OrgAmbitoTerrit orgAmbitoTerrit = gestioneAmbitoTerritorialeHelper.findById(OrgAmbitoTerrit.class,
                idAmbitoTerritoriale);
        if (orgAmbitoTerrit != null) {
            try {
                orgAmbitoTerritRowBean = (OrgAmbitoTerritRowBean) Transform.entity2RowBean(orgAmbitoTerrit);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                log.error("Errore", ex);
            }
        }
        return orgAmbitoTerritRowBean;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgAmbitoTerrit(String cdAmbitoTerritoriale) throws ParerUserError {
        OrgAmbitoTerrit orgAmbitoTerritToRemove = gestioneAmbitoTerritorialeHelper
                .getOrgAmbitoTerritByCode(cdAmbitoTerritoriale);
        BigDecimal idToRemove = new BigDecimal(orgAmbitoTerritToRemove.getIdAmbitoTerrit());

        if (nodeOrgAmbitoTerritHasChild(cdAmbitoTerritoriale, idToRemove)) {
            throw new ParerUserError("Il nodo selezionato \u00E8 collegato ad altri nodi. Impossibile cancellare");
        }
        if (gestioneAmbitoTerritorialeHelper.countOrgEnteConvenzByAmbitoTerrit(idToRemove) > 0L) {
            throw new ParerUserError("Nodo associato ad enti convenzionati. Impossibile cancellare");
        }
        if (gestioneAmbitoTerritorialeHelper.countOrgStoEnteConvenzByAmbitoTerrit(idToRemove) > 0L) {
            throw new ParerUserError(
                    "Nodo associato ad informazioni storicizzate riferite a un ente convenzionato. Impossibile cancellare");
        }

        gestioneAmbitoTerritorialeHelper.removeEntity(orgAmbitoTerritToRemove, true);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void moveOrgAmbitoTerritorialeNode(BigDecimal nodeId, BigDecimal nodeDestId) throws ParerUserError {
        OrgAmbitoTerrit orgAmbitoTerritDB = gestioneAmbitoTerritorialeHelper.findById(OrgAmbitoTerrit.class, nodeId);
        OrgAmbitoTerrit orgAmbitoTerritDest = gestioneAmbitoTerritorialeHelper.findById(OrgAmbitoTerrit.class,
                nodeDestId);
        // se la profondità(nodi figli) del nodo di partenza è minore di quella del nodo di arrivo posso procedere
        if (orgAmbitoTerritDest != null) {
            if (deepChildNodes(orgAmbitoTerritDB) < totChildNodeLevels(orgAmbitoTerritDest)) {

                switch (orgAmbitoTerritDest.getTiAmbitoTerrit()) {
                // Forma Associata non può avere nodi figli
                case "FORMA_ASSOCIATA":
                    throw new ParerUserError(
                            "Impossibile inserire il nodo all'interno di un nodo marcato come \"FORMA ASSOCIATA\"");
                case "PROVINCIA":
                    changeTiAmbitoTerritoriale(orgAmbitoTerritDB, orgAmbitoTerritDest.getTiAmbitoTerrit());
                    break;
                case "REGIONE/STATO":
                    changeTiAmbitoTerritoriale(orgAmbitoTerritDB, orgAmbitoTerritDest.getTiAmbitoTerrit());
                    break;
                }

            } else {
                throw new ParerUserError("Il nodo destinazione non \u00E8 compatibile con il nodo selezionato");
            }
        } else {
            changeTiAmbitoTerritoriale(orgAmbitoTerritDB, "");
        }

        orgAmbitoTerritDB.setOrgAmbitoTerrit(orgAmbitoTerritDest);
    }

    private void changeTiAmbitoTerritoriale(OrgAmbitoTerrit orgAmbitoTerritDB, String tipoPadre) {
        switch (tipoPadre) {
        case "":
            orgAmbitoTerritDB.setTiAmbitoTerrit("REGIONE/STATO");
            for (OrgAmbitoTerrit orgAmbitoTerritChild : orgAmbitoTerritDB.getOrgAmbitoTerrits()) {
                changeTiAmbitoTerritoriale(orgAmbitoTerritChild, "REGIONE/STATO");
            }
            break;
        case "REGIONE/STATO":
            for (OrgAmbitoTerrit orgAmbitoTerritChild : orgAmbitoTerritDB.getOrgAmbitoTerrits()) {
                changeTiAmbitoTerritoriale(orgAmbitoTerritChild, "PROVINCIA");
            }
            orgAmbitoTerritDB.setTiAmbitoTerrit("PROVINCIA");
            break;
        case "PROVINCIA":
            orgAmbitoTerritDB.setTiAmbitoTerrit("FORMA_ASSOCIATA");
            break;
        }
    }

    private int deepChildNodes(OrgAmbitoTerrit orgAmbitoTerritoriale) {
        // se non ha nodi figli
        if (orgAmbitoTerritoriale.getOrgAmbitoTerrits().isEmpty()) {
            return 0;
        } else {

            for (OrgAmbitoTerrit row : orgAmbitoTerritoriale.getOrgAmbitoTerrits()) {
                // se un figlio ha figli a sua volta, la profondità è 3
                if (!row.getOrgAmbitoTerrits().isEmpty()) {
                    return 2;
                }
            }
            // se nessun nodo figlio ha figli a sua volta
            return 1;
        }
    }

    private int totChildNodeLevels(OrgAmbitoTerrit orgAmbitoTerritoriale) {

        switch (orgAmbitoTerritoriale.getTiAmbitoTerrit()) {
        case "REGIONE/STATO":
            return 2;
        case "PROVINCIA":
            return 1;
        default:
            return 0;
        }
    }
}
