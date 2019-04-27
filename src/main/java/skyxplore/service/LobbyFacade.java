package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.character.CreateLobbyRequest;
import skyxplore.service.lobby.LobbyCreatorService;

@RequiredArgsConstructor
@Slf4j
@Service
//TODO unit test
public class LobbyFacade {
    private final LobbyCreatorService lobbyCreatorService;

    public void createLobby(CreateLobbyRequest request, String characterId) {
        lobbyCreatorService.createLobby(request, characterId);
    }
}
