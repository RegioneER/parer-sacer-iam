package it.eng;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ExpectedException extends RuntimeException {
}
