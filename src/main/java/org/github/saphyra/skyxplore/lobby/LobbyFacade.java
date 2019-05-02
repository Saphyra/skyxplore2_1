package org.github.saphyra.skyxplore.lobby;

import org.github.saphyra.skyxplore.lobby.domain.CreateLobbyRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
//TODO unit test
class LobbyFacade {
    private final LobbyCreatorService lobbyCreatorService;

    void createLobby(CreateLobbyRequest request, String characterId) {
        lobbyCreatorService.createLobby(request, characterId);
    }
}
