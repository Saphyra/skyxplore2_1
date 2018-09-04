package skyxplore.controller.request.character;

import lombok.Data;

@Data
public class CreateLobbyRequest {
    private String gameMode;
    private String data;
}
