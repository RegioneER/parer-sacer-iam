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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.web.util.Transform;

/**
 * Session Bean implementation class AllineaComponentiHelper Contiene i metodi, per la gestione della persistenza su DB
 * per le operazioni CRUD
 *
 */
@Stateless
@LocalBean
public class AllineaComponentiHelper {

    public AllineaComponentiHelper() {
        /**
         * Default constructor.
         */
    }

    private static final Logger log = LoggerFactory.getLogger(AllineaComponentiHelper.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    public AplApplicTableBean getAplApplicTableBean() {
        String queryStr = "SELECT applic FROM AplApplic applic ORDER BY applic.nmApplic";
        Query q = em.createQuery(queryStr);
        List<AplApplic> applicList = q.getResultList();
        AplApplicTableBean applicTableBean = new AplApplicTableBean();
        try {
            if (applicList != null && !applicList.isEmpty()) {
                applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicTableBean;
    }

    public AplApplicRowBean getAplApplicRowBean(String nmApplic) {
        AplApplicRowBean row = null;
        Query q = em.createQuery("SELECT applic FROM AplApplic applic WHERE applic.nmApplic = :nmApplic");
        q.setParameter("nmApplic", nmApplic);
        List<AplApplic> applicList = q.getResultList();
        if (applicList != null && !applicList.isEmpty()) {
            try {
                row = (AplApplicRowBean) Transform.entity2RowBean(applicList.get(0));
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                throw new IllegalStateException("Impossibile recuperare l'applicazione");
            }
        }
        return row;
    }

}
