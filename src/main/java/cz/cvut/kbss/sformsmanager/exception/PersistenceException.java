package cz.cvut.kbss.sformsmanager.exception;

public class PersistenceException extends RuntimeException {

    public PersistenceException(Throwable throwable) {
        super(throwable);
    }

    public PersistenceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}