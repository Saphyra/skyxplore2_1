package skyxplore.controller.request.user;

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
    @Size(min = 1)
    private String password;
}
