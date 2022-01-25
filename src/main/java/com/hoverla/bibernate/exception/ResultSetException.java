package com.hoverla.bibernate.exception;

public class ResultSetException extends BibernateApplicationException{

    public ResultSetException(String message, Exception cause) {
        super(message, cause);
    }
}
