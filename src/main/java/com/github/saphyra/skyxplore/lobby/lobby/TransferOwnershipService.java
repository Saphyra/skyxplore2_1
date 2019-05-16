package com.github.saphyra.skyxplore.lobby.lobby;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class TransferOwnershipService {
    private final LobbyQueryService lobbyQueryService;

    void transferOwnership(String characterId, String newOwnerId) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        if(!lobby.getOwnerId() .equals(characterId)){
            throw new ForbiddenException(characterId + " is not the owner of lobby " + lobby.getLobbyId());
        }
        lobby.transferOwnership(newOwnerId);
    }
}
