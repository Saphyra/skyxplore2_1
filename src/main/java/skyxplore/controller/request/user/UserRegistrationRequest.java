package skyxplore.controller.request.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationRequest {
    public static final int USER_NAME_MAX_LENGTH = 30;
    public static final int PASSWORD_MAX_LENGTH = 30;

    @NotNull
    @Size(min=3, max = USER_NAME_MAX_LENGTH)
    private String username;

    @NotNull
    @Size(min=6, max = PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull
    private String confirmPassword;

    @Email
    @NotNull
    private String email;

    @Override
    public String toString(){
        return "Username: " + username + ", Email: " + email;
    }
}
