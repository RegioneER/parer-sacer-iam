package it.eng.saceriam.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class IncoherenceException extends Exception {

    public IncoherenceException(String message) {
        super(message);
    }

}
