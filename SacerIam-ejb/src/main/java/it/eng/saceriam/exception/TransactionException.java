package it.eng.saceriam.exception;

/**
 *
 * @author Gilioli_P
 */
public class TransactionException extends Exception {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
