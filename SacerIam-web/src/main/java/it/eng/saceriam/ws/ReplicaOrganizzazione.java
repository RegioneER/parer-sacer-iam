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

import java.util.Date;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import it.eng.saceriam.ws.replicaOrganizzazione.dto.CancellaOrganizzazioneRisposta;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.InserimentoOrganizzazioneRisposta;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ListaTipiDato;
import it.eng.saceriam.ws.replicaOrganizzazione.dto.ModificaOrganizzazioneRisposta;
import it.eng.saceriam.ws.replicaOrganizzazione.ejb.CancellaOrganizzazioneEjb;
import it.eng.saceriam.ws.replicaOrganizzazione.ejb.InserimentoOrganizzazioneEjb;
import it.eng.saceriam.ws.replicaOrganizzazione.ejb.ModificaOrganizzazioneEjb;
import it.eng.spagoLite.security.exception.AuthWSException;

/**
 *
 * @author Gilioli_P
 */
@WebService(serviceName = "ReplicaOrganizzazione")
@HandlerChain(file = "/ws_handler.xml")
public class ReplicaOrganizzazione {

    @EJB
    private InserimentoOrganizzazioneEjb ejbRefIns;// Add business logic below. (Right-click in editor and choose
    @EJB
    private ModificaOrganizzazioneEjb ejbRefMod;// Add business logic below. (Right-click in editor and choose
    @EJB
    private CancellaOrganizzazioneEjb ejbRefCanc;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "inserimentoOrganizzazione")
    public InserimentoOrganizzazioneRisposta inserimentoOrganizzazione(@WebParam(name = "nmApplic") String nmApplic,
            @WebParam(name = "idOrganizApplic") Integer idOrganizApplic,
            @WebParam(name = "nmTipoOrganiz") String nmTipoOrganiz,
            @WebParam(name = "idEnteConserv") Integer idEnteConserv,
            @WebParam(name = "idEnteGestore") Integer idEnteGestore,
            @WebParam(name = "idOrganizApplicPadre") Integer idOrganizApplicPadre,
            @WebParam(name = "nmTipoOrganizPadre") String nmTipoOrganizPadre,
            @WebParam(name = "nmOrganiz") String nmOrganiz, @WebParam(name = "dsOrganiz") String dsOrganiz,
            @WebParam(name = "idEnteConvenz") Integer idEnteConvenz, @WebParam(name = "dtIniVal") Date dtIniVal,
            @WebParam(name = "dtFineVal") Date dtFineVal, @WebParam(name = "listaTipiDato") ListaTipiDato listaTipiDato)
            throws AuthWSException {
        return ejbRefIns.inserimentoOrganizzazione(nmApplic, idOrganizApplic, nmTipoOrganiz, idEnteConserv,
                idEnteGestore, idOrganizApplicPadre, nmTipoOrganizPadre, nmOrganiz, dsOrganiz, idEnteConvenz, dtIniVal,
                dtFineVal, listaTipiDato);
    }

    @WebMethod(operationName = "modificaOrganizzazione")
    public ModificaOrganizzazioneRisposta modificaOrganizzazione(@WebParam(name = "nmApplic") String nmApplic,
            @WebParam(name = "idOrganizApplic") Integer idOrganizApplic,
            @WebParam(name = "nmTipoOrganiz") String nmTipoOrganiz,
            @WebParam(name = "idEnteConserv") Integer idEnteConserv,
            @WebParam(name = "idEnteGestore") Integer idEnteGestore, @WebParam(name = "nmOrganiz") String nmOrganiz,
            @WebParam(name = "dsOrganiz") String dsOrganiz,
            @WebParam(name = "idOrganizApplicPadre") Integer idOrganizApplicPadre,
            @WebParam(name = "nmTipoOrganizPadre") String nmTipoOrganizPadre,
            @WebParam(name = "idEnteConvenz") Integer idEnteConvenz, @WebParam(name = "dtIniVal") Date dtIniVal,
            @WebParam(name = "dtFineVal") Date dtFineVal, @WebParam(name = "listaTipiDato") ListaTipiDato listaTipiDato)
            throws AuthWSException {
        return ejbRefMod.modificaOrganizzazione(nmApplic, idOrganizApplic, nmTipoOrganiz, idEnteConserv, idEnteGestore,
                idOrganizApplicPadre, nmTipoOrganizPadre, nmOrganiz, dsOrganiz, idEnteConvenz, dtIniVal, dtFineVal,
                listaTipiDato);
    }

    @WebMethod(operationName = "cancellaOrganizzazione")
    public CancellaOrganizzazioneRisposta cancellaOrganizzazione(@WebParam(name = "nmApplic") String nmApplic,
            @WebParam(name = "idOrganizApplic") Integer idOrganizApplic,
            @WebParam(name = "nmTipoOrganiz") String nmTipoOrganiz) throws AuthWSException {
        return ejbRefCanc.cancellaOrganizzazione(nmApplic, idOrganizApplic, nmTipoOrganiz);
    }
}
