package skyxplore.controller.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddToQueueRequest {
    @NotNull
    private String elementId;

    @NotNull
    @Min(1)
    private Integer amount;
}
