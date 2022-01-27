package com.hoverla.bibernate.testutil.factory;

import com.hoverla.bibernate.testutil.entity.Person;

public class PersonFactory {

    public final static Long USER_ID = 1L;
    public final static String USER_EMAIL = "bobouser@gmail.com";
    public final static String USER_FIRST_NAME = "Bobo";
    public final static String USER_LAST_NAME = "Bibernate";

    public static Person getTestUser() {
        Person person = new Person();
        person.setId(USER_ID);
        person.setEmail(USER_EMAIL);
        person.setFirstName(USER_FIRST_NAME);
        person.setLastName(USER_LAST_NAME);
        return person;
    }
}
