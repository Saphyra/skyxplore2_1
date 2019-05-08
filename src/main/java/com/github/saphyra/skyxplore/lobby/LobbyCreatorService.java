package com.github.saphyra.skyxplore.lobby;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.lobby.domain.CreateLobbyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class LobbyCreatorService {
    private final LobbyQueryService lobbyQueryService;

    void createLobby(CreateLobbyRequest request, String characterId) {
        if (lobbyQueryService.findByCharacterId(characterId).isPresent()) {
            throw new BadRequestException("Character with id " + characterId + " is already in lobby.");
        }
    }
}
