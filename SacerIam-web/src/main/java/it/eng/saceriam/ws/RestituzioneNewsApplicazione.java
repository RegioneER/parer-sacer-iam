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

package it.eng.saceriam.ws;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import it.eng.saceriam.ws.restituzioneNewsApplicazione.dto.RestituzioneNewsApplicazioneRisposta;
import it.eng.saceriam.ws.restituzioneNewsApplicazione.ejb.RestituzioneNewsApplicazioneEjb;
import it.eng.spagoLite.security.exception.AuthWSException;

/**
 *
 * @author Gilioli_P
 */
@WebService(serviceName = "RestituzioneNewsApplicazione")
@HandlerChain(file = "/ws_handler.xml")
public class RestituzioneNewsApplicazione {
    @EJB
    private RestituzioneNewsApplicazioneEjb ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "restituzioneNewsApplicazione")
    public RestituzioneNewsApplicazioneRisposta restituzioneNewsApplicazione(
            @WebParam(name = "nmApplic") String nmApplic) throws AuthWSException {
        return ejbRef.restituzioneNewsApplicazione(nmApplic);
    }

}
