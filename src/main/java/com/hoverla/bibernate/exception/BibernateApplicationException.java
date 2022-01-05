package com.hoverla.bibernate.exception;

public class BibernateApplicationException extends RuntimeException {

    public BibernateApplicationException(String message, Exception ex) {
        super(message, ex);
    }

    public BibernateApplicationException(String message) {
        super(message);
    }

}
