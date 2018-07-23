package skyxplore.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangeUserNameRequest {
    @NotNull
    @Size(min = 3)
    private String newUserName;

    @NotNull
    @Size(min = 1)
    private String password;
}
