package skyxplore.controller.request.community;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SendMailRequest {
    @NotNull
    private String characterId;

    @NotNull
    private String addresseeId;

    @NotNull
    @Size(min = 1, max = 100)
    private String subject;

    @NotNull
    @Size(min = 1, max = 4000)
    private String message;
}
