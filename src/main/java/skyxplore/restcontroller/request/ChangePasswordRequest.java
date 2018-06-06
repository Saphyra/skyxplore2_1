package skyxplore.restcontroller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {
    @NotNull
    @Size(min = 6)
    private String newPassword;

    @NotNull
    @Size(min = 6)
    private String confirmPassword;

    @NotNull
    @Size(min = 6)
    private String oldPassword;
}
