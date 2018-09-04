package skyxplore.controller.request.character;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UnequipRequest {
    @NotNull
    private String slot;
    @NotNull
    private String itemId;
}
