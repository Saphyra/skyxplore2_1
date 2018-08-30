package skyxplore.controller.request.community;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SendMailRequest {
    @NotNull
    private String characterId;

    @NotNull
    private String addresseeId;

    @NotNull
    @Size(min = 1)
    private String subject;

    @NotNull
    @Size(min = 1)
    private String message;
}
