package com.customorm.factory;

import com.customorm.entity.Person;
import com.hoverla.bibernate.session.EntityKey;


public class PersonFactory {

    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_FIRST_NAME = "BUBU";
    public static final String DEFAULT_LAST_NAME = "GUGU";
    public static final EntityKey<Person> DEFAULT_KEY = new EntityKey<>(Person.class, DEFAULT_ID);


    public static Person createNewDefaultPerson(){
        Person person = new Person();
        person.setId(DEFAULT_ID);
        person.setFirstName(DEFAULT_FIRST_NAME);
        person.setLastName(DEFAULT_LAST_NAME);
        return person;
    }
}
