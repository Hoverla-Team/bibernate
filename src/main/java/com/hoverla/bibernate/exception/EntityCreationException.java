package com.hoverla.bibernate.exception;

public class EntityCreationException extends BibernateApplicationException {

    public EntityCreationException(String message, Exception ex) {
        super(message, ex);
    }

}
