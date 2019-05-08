package com.github.saphyra.skyxplore.lobby;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LobbyQueryService {
    private final LobbyStorage lobbyStorage;

    public Optional<Lobby> findByCharacterId(String characterId) {
        return lobbyStorage.values().stream()
            .filter(lobby -> containsCharacter(lobby, characterId))
            .findAny();
    }

    private boolean containsCharacter(Lobby lobby, String characterId) {
        return lobby.getUsers().stream()
            .anyMatch(s -> s.equals(characterId));
    }
}
