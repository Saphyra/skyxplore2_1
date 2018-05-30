package skyxplore.restcontroller.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationRequest {

    @NotNull
    @Size(min=3)
    private String username;

    @NotNull
    @Size(min=6)
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
