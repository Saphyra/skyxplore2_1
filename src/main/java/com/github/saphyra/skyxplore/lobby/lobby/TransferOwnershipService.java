package com.github.saphyra.skyxplore.lobby.lobby;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class TransferOwnershipService {
    private final LobbyQueryService lobbyQueryService;

    void transferOwnership(String characterId, String newOwnerId) {
        lobbyQueryService.findByCharacterIdValidated(characterId)
            .transferOwnership(characterId, newOwnerId);
    }
}
