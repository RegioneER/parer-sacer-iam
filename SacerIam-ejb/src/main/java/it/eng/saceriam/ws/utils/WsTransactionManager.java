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

package it.eng.saceriam.ws.utils;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.ws.dto.IRispostaWS;

/**
 *
 * @author Bonora_L
 */
public class WsTransactionManager {

    private UserTransaction utx;

    private static final Logger log = LoggerFactory.getLogger(WsTransactionManager.class);

    public WsTransactionManager(UserTransaction utx) {
        this.utx = utx;
    }

    public void beginTrans(IRispostaWS rispostaWs) {
        try {
            utx.begin();
        } catch (NotSupportedException e) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di apertura transazione db del EJB " + e.getMessage());
            log.error("Eccezione nell'init ejb versamento ", e);
        } catch (SystemException e) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di apertura transazione db del EJB " + e.getMessage());
            log.error("Eccezione nell'init ejb versamento ", e);
        }
    }

    public void commit(IRispostaWS rispostaWs) {
        try {
            utx.commit();
        } catch (RollbackException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di commit del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        } catch (HeuristicMixedException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di commit del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        } catch (HeuristicRollbackException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di commit del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        } catch (SecurityException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di commit del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        } catch (IllegalStateException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di commit del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        } catch (SystemException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di commit del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        }
    }

    public void rollback(IRispostaWS rispostaWs) {
        try {
            utx.rollback();
        } catch (IllegalStateException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di rollback del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        } catch (SecurityException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di rollback del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        } catch (SystemException ex) {
            rispostaWs.setSeverity(IRispostaWS.SeverityEnum.ERROR);
            rispostaWs.setErrorType(IRispostaWS.ErrorTypeEnum.DB_FATAL);
            rispostaWs.setErrorMessage("Errore nella fase di rollback del EJB " + ex.getMessage());
            log.error("Eccezione nell'init ejb versamento ", ex);
        }
    }
}
