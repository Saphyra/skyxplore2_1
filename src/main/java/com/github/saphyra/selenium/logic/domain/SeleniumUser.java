package com.github.saphyra.selenium.logic.domain;

import static com.github.saphyra.selenium.logic.util.Util.crop;
import static com.github.saphyra.selenium.logic.util.Util.randomUID;
import static com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest.PASSWORD_MAX_LENGTH;
import static com.github.saphyra.skyxplore.userdata.user.domain.UserRegistrationRequest.USER_NAME_MAX_LENGTH;

import lombok.Data;

@Data
public class SeleniumUser {
    private static final String USER_NAME_PREFIX = "user-";
    private static final String PASSWORD_PREFIX = "pw-";
    private static final String USER_EMAIL_SUFFIX = "@test.com";

    private String userName;
    private String password;
    private String email;

    public static SeleniumUser create() {
        SeleniumUser user = new SeleniumUser();
        user.setUserName(createRandomUserName());
        user.setPassword(createRandomPassword());
        user.setEmail(createRandomEmail());
        return user;
    }

    public static String createRandomUserName() {
        return crop(USER_NAME_PREFIX + randomUID(), USER_NAME_MAX_LENGTH);
    }

    public static String createRandomPassword() {
        return crop(PASSWORD_PREFIX + randomUID(), PASSWORD_MAX_LENGTH);
    }

    public static String createRandomEmail() {
        return randomUID() + USER_EMAIL_SUFFIX;
    }

    public SeleniumUser cloneUser() {
        SeleniumUser clone = new SeleniumUser();
        clone.setUserName(userName);
        clone.setPassword(password);
        clone.setEmail(email);
        return clone;
    }
}
