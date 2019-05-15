package com.github.saphyra.skyxplore.lobby.lobby;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.character.CharacterViewQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyEvent;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyEventView;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class LobbyViewQueryService {
    private final CharacterViewQueryService characterViewQueryService;
    private final LobbyQueryService lobbyQueryService;

    LobbyView getLobbyView(String characterId) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        lobby.getEvents().forEach(lobbyEvent -> lobbyEvent.adQueriedBy(characterId));
        return LobbyView.builder()
            .gameMode(lobby.getGameMode())
            .data(lobby.getData())
            .ownerId(lobby.getOwnerId())
            .build();
    }

    List<LobbyEventView> getEvents(String characterId) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        return lobby.getEvents().stream()
            .filter(lobbyEvent -> !lobbyEvent.getQueriedBy().contains(characterId))
            .peek(lobbyEvent -> lobbyEvent.adQueriedBy(characterId))
            .map(this::createLobbyEventView)
            .collect(Collectors.toList());
    }

    private LobbyEventView createLobbyEventView(LobbyEvent lobbyEvent) {
        return LobbyEventView.builder()
            .eventType(lobbyEvent.getEventType())
            .data(characterViewQueryService.findByCharacterId(lobbyEvent.getData().toString()))
            .build();
    }
}