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

package it.eng.saceriam.ws.ejb;

import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.eng.parer.idpjaas.logutils.IdpConfigLog;
import it.eng.parer.idpjaas.logutils.IdpLogger;
import it.eng.parer.idpjaas.logutils.LogDto;
import it.eng.parer.sacerlog.ejb.common.AppServerInstance;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.spagoCore.util.JpaUtils;

/**
 *
 * @author fioravanti_f
 */
@Stateless(mappedName = "WsIdpLogger")
@LocalBean
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class WsIdpLogger {

    @EJB
    private ParamHelper paramHelper;

    @EJB
    private AppServerInstance asi;

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public IdpLogger.EsitiLog scriviLog(LogDto logDto) {
	IdpLogger.EsitiLog risposta = IdpLogger.EsitiLog.UTENTE_OK;
	String idpMaxTentativiFalliti = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.IDP_MAX_TENTATIVI_FALLITI, null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	String idpQueryDisableUser = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.IDP_QRY_DISABLE_USER, null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	String idpQueryRegistraEventoUtente = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.IDP_QRY_REGISTRA_EVENTO_UTENTE, null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	String idpQueryVerificaDisattivazioneUtente = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.IDP_QRY_VERIFICA_DISATTIVAZIONE_UTENTE, null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	String idpMaxGiornilogger = paramHelper.getValoreParamApplic(
		ConstIamParamApplic.IDP_MAX_GIORNILOGGER, null, null,
		Constants.TipoIamVGetValAppart.APPLIC);
	if (idpQueryRegistraEventoUtente != null && !idpQueryRegistraEventoUtente.isEmpty()
		&& idpQueryVerificaDisattivazioneUtente != null
		&& !idpQueryVerificaDisattivazioneUtente.isEmpty() && idpQueryDisableUser != null
		&& !idpQueryDisableUser.isEmpty() && idpMaxGiornilogger != null
		&& !idpMaxGiornilogger.isEmpty() && idpMaxTentativiFalliti != null
		&& !idpMaxTentativiFalliti.isEmpty()) {
	    try {
		IdpConfigLog icl = new IdpConfigLog();
		icl.setQryRegistraEventoUtente(idpQueryRegistraEventoUtente);
		icl.setQryVerificaDisattivazioneUtente(idpQueryVerificaDisattivazioneUtente);
		icl.setQryDisabilitaUtente(idpQueryDisableUser);
		icl.setMaxTentativi(Integer.parseInt(idpMaxTentativiFalliti));
		icl.setMaxGiorni(Integer.parseInt(idpMaxGiornilogger));

		logDto.setServername(asi.getName());

		try (java.sql.Connection connection = JpaUtils
			.provideConnectionFrom(entityManager)) {
		    risposta = new IdpLogger(icl).scriviLog(logDto, connection);
		}

	    } catch (UnknownHostException ex) {
		throw new RuntimeException(
			"WsIdpLogger: Errore nel determinare il nome host per il server: "
				+ ex.getMessage());
	    } catch (SQLException ex) {
		throw new RuntimeException(
			"WsIdpLogger: Errore nell'accesso ai dati di log: " + ex.getMessage());
	    } catch (Exception ex) {
		throw new RuntimeException("WsIdpLogger: Errore: " + ex.getMessage());
	    }
	}
	return risposta;
    }

}
