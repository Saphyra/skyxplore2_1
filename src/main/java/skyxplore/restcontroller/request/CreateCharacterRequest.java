package skyxplore.restcontroller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCharacterRequest {
    @NotNull
    private String characterName;
}
