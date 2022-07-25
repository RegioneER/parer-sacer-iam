package it.eng.saceriam.aop;

import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.exception.ParerUserError;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            e.printStackTrace();
            ctx.setRollbackOnly();
            throw e;
        }
    }
}