package it.eng.saceriam.ws;

import it.eng.saceriam.ws.calcoloServiziErogati.ejb.CalcoloServiziErogatiEjb;
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
@WebService(serviceName = "CalcoloServiziErogati")
@HandlerChain(file = "/ws_handler.xml")
public class CalcoloServiziErogati {

    @EJB
    private CalcoloServiziErogatiEjb aecEjb;

    @WebMethod(operationName = "calcoloServiziErogati")
    public void calcoloServiziErogati(@WebParam(name = "idEnteConvenz") Integer idEnteConvenz) throws AuthWSException {
        aecEjb.calcoloServiziErogati(idEnteConvenz);
    }
}
