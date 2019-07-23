package com.github.saphyra.skyxplore.game.lobby.lobby;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
class MemberStatusService {
    private final LobbyQueryService lobbyQueryService;

     void setReady(String characterId) {
         lobbyQueryService.findByCharacterIdValidated(characterId).setMemberReady(characterId);
    }

    void setUnready(String characterId) {
        lobbyQueryService.findByCharacterIdValidated(characterId).setMemberUnready(characterId);
    }
}
