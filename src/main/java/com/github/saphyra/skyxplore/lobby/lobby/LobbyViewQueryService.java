package com.github.saphyra.skyxplore.lobby.lobby;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class LobbyViewQueryService {
    private final LobbyQueryService lobbyQueryService;

    LobbyView getLobbyView(String characterId) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        return LobbyView.builder()
            .gameMode(lobby.getGameMode())
            .data(lobby.getData())
            .ownerId(lobby.getOwnerId())
            .build();
    }
}
