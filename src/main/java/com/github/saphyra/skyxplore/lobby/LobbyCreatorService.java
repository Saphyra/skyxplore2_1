package com.github.saphyra.skyxplore.lobby;

import com.github.saphyra.skyxplore.lobby.domain.CreateLobbyRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class LobbyCreatorService {

    void createLobby(CreateLobbyRequest request, String characterId) {
        //TODO implement
        throw new UnsupportedOperationException();
    }
}
