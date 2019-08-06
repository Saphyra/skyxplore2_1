package com.github.saphyra.skyxplore.game.lobby.lobby;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class LobbyQueueService {
    private final LobbyQueryService lobbyQueryService;

    void startQueue(String characterId, Boolean autoFill) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        if(!lobby.getOwnerId().equals(characterId)){
            throw new ForbiddenException(characterId + " is not the owner of lobby " + lobby.getLobbyId());
        }

        lobby.startQueueing(autoFill);
    }
}
