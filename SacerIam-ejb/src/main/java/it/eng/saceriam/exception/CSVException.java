package it.eng.saceriam.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class CSVException extends Exception {

    public CSVException(String message) {
        super(message);
    }

}
