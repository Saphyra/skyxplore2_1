package com.github.saphyra.skyxplore.lobby.lobby;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
//TODO unit test
class MemberStatusService {
    private final LobbyQueryService lobbyQueryService;

     void setReady(String characterId) {
         lobbyQueryService.findByCharacterIdValidated(characterId).setMemberReady(characterId);
    }

    void setUnready(String characterId) {
        lobbyQueryService.findByCharacterIdValidated(characterId).setMemberUnready(characterId);
    }
}
