package com.github.saphyra.skyxplore.lobby.message;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.message.domain.Message;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class MessageSenderService {
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final LobbyQueryService lobbyQueryService;
    private final MessageStorage messageStorage;

    void sendMessage(String characterId, String text) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        Message message = Message.builder()
            .messageId(idGenerator.randomUUID())
            .lobbyId(lobby.getLobbyId())
            .sender(characterId)
            .message(text)
            .createdAt(dateTimeUtil.convertDomain(dateTimeUtil.now()))
            .build();
        messageStorage.put(message.getMessageId(), message);
    }
}
