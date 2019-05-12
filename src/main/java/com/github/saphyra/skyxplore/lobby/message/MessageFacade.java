package com.github.saphyra.skyxplore.lobby.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MessageFacade {
    private final MessageDeletionService messageDeletionService;

    public void deleteByLobbyId(UUID lobbyId) {
        messageDeletionService.deleteByLobbyId(lobbyId);
    }
}
