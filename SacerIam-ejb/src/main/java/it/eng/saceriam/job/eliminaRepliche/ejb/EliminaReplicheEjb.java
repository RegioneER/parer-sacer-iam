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

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.job.ejb.JobLogger;

@Stateless(mappedName = "EliminaReplicheEjb")
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class EliminaReplicheEjb {

    private static final Logger log = LoggerFactory.getLogger(EliminaReplicheEjb.class);

    @EJB
    private JobLogger jobLoggerEjb;
    @EJB
    private EliminaReplicheHelper elReplicheHelper;
    @EJB
    private ParamHelper paramHelper;

    public void eliminaRepliche() {
        log.info("Eliminazione dei log delle repliche utenti più vecchi di un numero di anni da parametro applicativo");

        Integer numAnniCancRepUt = Integer.parseInt(
                paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.NUM_ANNI_CANC_REPLICHE_UT.name(),
                        null, null, Constants.TipoIamVGetValAppart.APPLIC));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -numAnniCancRepUt);
        Date dateRifCanc = cal.getTime();
        elReplicheHelper.deleteOldReplicheUtenti(dateRifCanc);

        /* Scrivo nel log del job l'esito finale */
        jobLoggerEjb.writeAtomicLog(Constants.NomiJob.ELIMINA_REPLICHE_UTENTI,
                Constants.TipiRegLogJob.FINE_SCHEDULAZIONE, null);
    }

}
