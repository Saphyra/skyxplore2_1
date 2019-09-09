package com.github.saphyra.skyxplore.game.lobby.lobby;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class LobbyQueryService {
    private final LobbyStorage lobbyStorage;

    public Lobby findByCharacterIdValidated(String characterId) {
        return findByCharacterId(characterId)
            .orElseThrow(() -> ExceptionFactory.lobbyNotFound(characterId));
    }

    public Optional<Lobby> findByCharacterId(String characterId) {
        return lobbyStorage.values().stream()
            .filter(lobby -> containsCharacter(lobby, characterId))
            .findAny();
    }

    private boolean containsCharacter(Lobby lobby, String characterId) {
        return lobby.findMemberByCharacterId(characterId).isPresent();
    }

    public Lobby findByLobbyIdValidated(UUID lobbyId) {
        return Optional.ofNullable(lobbyStorage.get(lobbyId))
            .orElseThrow(() -> new NotFoundException("Lobby not found with id " + lobbyId));
    }

    public List<Lobby> getLobbiesInQueue() {
        return lobbyStorage.values().stream()
            .filter(Lobby::isInQueue)
            .collect(Collectors.toList());
    }
}
