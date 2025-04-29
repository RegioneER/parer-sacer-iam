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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.spagoCore.error.EMFError;

/**
 * Ejb Modifica Password di SacerIam
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class ModificaPswEjb {

    @EJB
    private UserHelper userHelper;

    public Set<BigDecimal> getIdApplicSet(Long idUserIam) throws EMFError {
        Set<BigDecimal> idApplicSet = new HashSet<>();
        List<UsrUsoUserApplic> applicList = userHelper.getUsrUsoUserApplic(new BigDecimal(idUserIam));
        for (UsrUsoUserApplic applic : applicList) {
            idApplicSet.add(new BigDecimal(applic.getAplApplic().getIdApplic()));
        }
        return idApplicSet;
    }

    public void registraLogUserDaReplic(String username) throws Exception {
        userHelper.registraLogUserDaReplic(username, ApplEnum.TiOperReplic.MOD);
    }

}
