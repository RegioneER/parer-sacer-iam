package it.eng;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class RollbackException extends RuntimeException {
}
