package com.github.saphyra.skyxplore.lobby;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LobbyQueryService {
    private final LobbyStorage lobbyStorage;

    Lobby findByCharacterIdValidated(String characterId) {
        return findByCharacterId(characterId)
            .orElseThrow(() -> new NotFoundException("Lobby not found for character " + characterId));
    }

    public Optional<Lobby> findByCharacterId(String characterId) {
        return lobbyStorage.values().stream()
            .filter(lobby -> containsCharacter(lobby, characterId))
            .findAny();
    }

    private boolean containsCharacter(Lobby lobby, String characterId) {
        return lobby.getUsers().contains(characterId);
    }
}
