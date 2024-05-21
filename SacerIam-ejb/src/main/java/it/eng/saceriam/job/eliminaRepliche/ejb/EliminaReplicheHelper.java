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

package it.eng.saceriam.job.eliminaRepliche.ejb;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.eng.saceriam.helper.GenericHelper;

@Stateless(mappedName = "EliminaReplicheHelper")
@LocalBean
public class EliminaReplicheHelper extends GenericHelper {
    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    /**
     * 
     * @param dtLogUserDaReplic
     *            data LogUserDaReplic
     */
    public void deleteOldReplicheUtenti(Date dtLogUserDaReplic) {
        Query q = entityManager
                .createQuery("DELETE FROM LogUserDaReplic log " + "WHERE log.dtLogUserDaReplic < :dtLogUserDaReplic ");

        q.setParameter("dtLogUserDaReplic", dtLogUserDaReplic);
        q.executeUpdate();
        entityManager.flush();
    }

}
