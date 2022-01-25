package com.hoverla.bibernate.testutil.factory;

import com.hoverla.bibernate.testutil.entity.User;

public class UserFactory {

    public final static Long USER_ID = 1L;
    public final static String USER_EMAIL = "bobouser@gmail.com";
    public final static String USER_FIRST_NAME = "Bobo";
    public final static String USER_LAST_NAME = "Bibernate";

    public static User getTestUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        return user;
    }
}
