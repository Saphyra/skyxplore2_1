package com.github.saphyra.skyxplore.lobby;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.storage.LobbyStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
//TODO unit test
public class LobbyQueryService {
    private final LobbyStorage lobbyStorage;

    public Lobby findByCharacterIdValidated(String characterId) {
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

    public Lobby findById(UUID lobbyId) {
        return Optional.ofNullable(lobbyStorage.get(lobbyId))
            .orElseThrow(() -> new NotFoundException("Lobby not found with id " + lobbyId));
    }
}
