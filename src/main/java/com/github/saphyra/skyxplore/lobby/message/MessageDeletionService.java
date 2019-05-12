package com.github.saphyra.skyxplore.lobby.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class MessageDeletionService {
    private final MessageStorage messageStorage;

    void deleteByLobbyId(UUID lobbyId) {
        log.info("Deleting messages belong to lobby {}", lobbyId);
        messageStorage.values().stream()
            .filter(message -> message.getLobbyId().equals(lobbyId))
            .collect(Collectors.toList())
            .forEach(message -> messageStorage.remove(message.getMessageId()));
    }
}
