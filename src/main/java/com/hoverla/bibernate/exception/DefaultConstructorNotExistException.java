package com.hoverla.bibernate.exception;

public class DefaultConstructorNotExistException extends BibernateApplicationException {

    public DefaultConstructorNotExistException(String message, Exception ex) {
        super(message, ex);
    }

}
