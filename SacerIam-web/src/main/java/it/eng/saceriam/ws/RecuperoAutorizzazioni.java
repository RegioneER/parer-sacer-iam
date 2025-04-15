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

package it.eng.saceriam.ws;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta;
import it.eng.saceriam.ws.ejb.RecuperoAutorizzazioniEjb;
import it.eng.spagoLite.security.exception.AuthWSException;

@WebService(serviceName = "RecuperoAutorizzazioni")
@HandlerChain(file = "/ws_handler.xml")
public class RecuperoAutorizzazioni {

    private static final Logger log = LoggerFactory.getLogger(RecuperoAutorizzazioni.class);

    @EJB
    private RecuperoAutorizzazioniEjb ejbRef;

    @WebMethod(operationName = "recuperoAutorizzazioniPerNome")
    public RecuperoAutorizzazioniRisposta recuperoAutorizzazioniPerNome(
	    @WebParam(name = "nmUserIam") String nmUserIam,
	    @WebParam(name = "nmApplic") String nmApplic,
	    @WebParam(name = "idOrganiz") Integer idOrganiz) throws AuthWSException {
	log.debug(
		"Chiamata all'operazione recuperoAutorizzazioniPerNome del WS RecuperoAutorizzazioni con i parametri nmUserIam "
			+ nmUserIam + " nmApplic " + nmApplic + " idOrganiz" + idOrganiz);
	return ejbRef.recuperoAutorizzazioniPerNome(nmUserIam, nmApplic, idOrganiz);
    }

    @WebMethod(operationName = "recuperoAutorizzazioniPerID")
    public RecuperoAutorizzazioniRisposta recuperoAutorizzazioniPerID(
	    @WebParam(name = "idUserIam") Integer idUserIam,
	    @WebParam(name = "nmApplic") String nmApplic,
	    @WebParam(name = "idOrganiz") Integer idOrganiz) throws AuthWSException {
	log.debug(
		"Chiamata all'operazione recuperoAutorizzazioniPerID del WS RecuperoAutorizzazioni con i parametri idUserIam "
			+ idUserIam + " nmApplic " + nmApplic + " idOrganiz" + idOrganiz);
	return ejbRef.recuperoAutorizzazioniPerId(idUserIam, nmApplic, idOrganiz);
    }

}
