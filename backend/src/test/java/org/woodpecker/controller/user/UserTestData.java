package org.woodpecker.controller.user;


import org.woodpecker.model.user.Role;
import org.woodpecker.model.user.User;
import org.woodpecker.util.JsonUtil;
import org.woodpecker.controller.MatcherFactory;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password", "name");

    public static final int USER_ID = 5;
    public static final int ADMIN_ID = 1;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "olya@gmail.com";

    public static final User user = new User(USER_ID, "user", USER_MAIL, "password");
    public static final User admin = new User(ADMIN_ID, "Оля", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "NewUser", "newUser@gmail.com", "newPassForUser", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
