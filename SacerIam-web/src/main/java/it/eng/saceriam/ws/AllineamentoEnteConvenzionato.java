package it.eng.saceriam.ws;

import it.eng.saceriam.ws.allineamentoEnteConvenzionato.dto.RispostaWSAllineamentoEnteConvenzionato;
import it.eng.saceriam.ws.allineamentoEnteConvenzionato.ejb.AllineamentoEnteConvenzionatoEjb;
import it.eng.spagoLite.security.exception.AuthWSException;
import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author gilioli_p
 */
@WebService(serviceName = "AllineamentoEnteConvenzionato")
@HandlerChain(file = "/ws_handler.xml")
public class AllineamentoEnteConvenzionato {

    @EJB
    private AllineamentoEnteConvenzionatoEjb aecEjb;

    @WebMethod(operationName = "ricalcoloServiziErogati")
    public RispostaWSAllineamentoEnteConvenzionato ricalcoloServiziErogati(
            @WebParam(name = "idEnteConvenz") Integer idEnteConvenz) throws AuthWSException {
        return aecEjb.ricalcoloServiziErogati(idEnteConvenz);
    }
}
