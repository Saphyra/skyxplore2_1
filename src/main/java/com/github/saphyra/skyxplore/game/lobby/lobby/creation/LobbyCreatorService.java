package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.NotImplementedException;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyStorage;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.CreateLobbyRequest;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Builder
public class LobbyCreatorService {
    private final LobbyQueryService lobbyQueryService;
    private final LobbyStorage lobbyStorage;
    private final List<LobbyFactory> lobbyFactories;

    public void createLobby(CreateLobbyRequest request, String characterId) {
        if (lobbyQueryService.findByCharacterId(characterId).isPresent()) {
            throw new BadRequestException("Character with id " + characterId + " is already in lobby.");
        }

        Lobby lobby = lobbyFactories.stream()
            .filter(lobbyFactory -> lobbyFactory.canCreate(request.getGameMode()))
            .findFirst()
            .orElseThrow(() -> new NotImplementedException("Unsupported gameMode: " + request.getGameMode()))
            .create(request.getGameMode(), characterId, request.getData());

        log.info("lobby created: {}", lobby);
        lobbyStorage.put(lobby.getLobbyId(), lobby);
    }
}
