package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.CreateLobbyRequest;
import skyxplore.service.game.LobbyCreatorService;

@RequiredArgsConstructor
@Slf4j
@Service
public class GameFacade {
    private final LobbyCreatorService lobbyCreatorService;

    public void createNewGame(CreateLobbyRequest request, String userId, String characterId) {
        lobbyCreatorService.createLobby(request, userId, characterId);
    }
}