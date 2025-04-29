/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package it.eng.saceriam.web.util;

import javax.ejb.EJB;

import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.spagoLite.actions.application.ApplicationBaseProperties;
import it.eng.spagoLite.actions.application.IApplicationBasePropertiesSevice;

/**
 *
 * @author Iacolucci_M
 *
 *         Implementazione che fornisce al framework SpagoLite i dati essenziali dell'applicazione
 *         per poter utilizzare l'Help on line da IAM
 */
public class ApplicationBasePropertiesSeviceImpl implements IApplicationBasePropertiesSevice {

    @EJB(mappedName = "java:app/SacerIam-ejb/ParamHelper")
    private ParamHelper paramHelper;

    @Override
    public ApplicationBaseProperties getApplicationBaseProperties() {
	String nmApplic = paramHelper
		.getValoreParamApplicByApplic(ConstIamParamApplic.NmParamApplic.NM_APPLIC.name()); // NM_APPLIC
	String user = paramHelper.getValoreParamApplicByApplic(
		ConstIamParamApplic.NmParamApplic.USERID_RECUP_INFO.name());
	String password = paramHelper.getValoreParamApplicByApplic(
		ConstIamParamApplic.NmParamApplic.PSW_RECUP_INFO.name());
	String url = paramHelper.getValoreParamApplicByApplic(
		ConstIamParamApplic.NmParamApplic.URL_RECUP_HELP.name());

	return new ApplicationBaseProperties(nmApplic, user, password, url);
    }

}
