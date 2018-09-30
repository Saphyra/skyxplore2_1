package selenium.domain;

import lombok.Data;

import static selenium.util.StringUtil.crop;
import static selenium.util.UserUtil.randomUID;
import static skyxplore.controller.request.user.UserRegistrationRequest.PASSWORD_MAX_LENGTH;
import static skyxplore.controller.request.user.UserRegistrationRequest.USER_NAME_MAX_LENGTH;

@Data
public class SeleniumUser {
    private static final String USER_NAME_PREFIX = "user-";
    private static final String USER_EMAIL_SUFFIX = "@test.com";

    private String userName;
    private String password;
    private String email;

    public static SeleniumUser create(){
        SeleniumUser user = new SeleniumUser();
        user.setUserName(crop(USER_NAME_PREFIX + randomUID(), USER_NAME_MAX_LENGTH));
        user.setPassword(crop(randomUID(), PASSWORD_MAX_LENGTH));
        user.setEmail(randomUID() + USER_EMAIL_SUFFIX);
        return user;
    }

    public SeleniumUser cloneUser() {
        SeleniumUser clone = new SeleniumUser();
        clone.setUserName(userName);
        clone.setPassword(password);
        clone.setEmail(email);
        return clone;
    }
}
