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

package it.eng.saceriam.aop;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.exception.ParerUserError;

/**
 *
 * @author Agati_D
 */
public class TransactionInterceptor {

    @Resource
    SessionContext ctx;

    @AroundInvoke
    public Object catchException(InvocationContext inv) throws Exception {
        Logger logger = LoggerFactory.getLogger(inv.getTarget().getClass());

        try {
            Object obj = inv.proceed();
            return obj;
        } catch (ParerUserError ue) {
            logger.error("ParerUserError nel metodo " + inv.getMethod().getName() + ": " + ue.getDescription());
            ctx.setRollbackOnly();
            throw ue;
        } catch (ParerInternalError ie) {
            logger.error("ParerInternalError nel metodo " + inv.getMethod().getName() + ": " + ie.getMessage());
            ctx.setRollbackOnly();
            throw ie;
        } catch (Exception e) {
            logger.info("Exception nel metodo " + inv.getMethod().getName() + ": " + e.getMessage());
            ctx.setRollbackOnly();
            throw e;
        }
    }
}
