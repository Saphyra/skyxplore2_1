package com.github.saphyra.skyxplore.game.lobby.message;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.common.domain.message.Message;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class MessageSenderService {
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final LobbyQueryService lobbyQueryService;

    void sendMessage(String characterId, String text) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        Message message = Message.builder()
            .messageId(idGenerator.randomUUID())
            .sender(characterId)
            .message(text)
            .createdAt(dateTimeUtil.convertDomain(dateTimeUtil.now()))
            .build();
        lobby.addMessage(message);
    }
}
