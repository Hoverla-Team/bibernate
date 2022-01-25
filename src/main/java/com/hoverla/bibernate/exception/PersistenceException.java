package com.hoverla.bibernate.exception;

public class PersistenceException  extends BibernateApplicationException{

    public PersistenceException(String message, Exception cause) {
        super(message, cause);
    }

    public PersistenceException(String message) {
        super(message);
    }
}
