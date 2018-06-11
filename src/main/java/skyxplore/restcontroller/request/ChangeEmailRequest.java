package skyxplore.restcontroller.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangeEmailRequest {
    @Email
    @NotNull
    private String newEmail;

    @NotNull
    @Size(min = 3)
    private String password;
}
