package com.hoverla.bibernate.exception;

public class ConnectionException extends BibernateApplicationException {

    public ConnectionException(String message, Exception cause) {
        super(message, cause);
    }
}
