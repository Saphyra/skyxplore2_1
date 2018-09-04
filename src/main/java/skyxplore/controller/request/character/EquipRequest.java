package skyxplore.controller.request.character;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EquipRequest {
    @NotNull
    private String itemId;

    @NotNull
    private String equipTo;

}
