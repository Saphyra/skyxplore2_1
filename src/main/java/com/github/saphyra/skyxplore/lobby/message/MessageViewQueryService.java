package com.github.saphyra.skyxplore.lobby.message;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.message.domain.Message;
import com.github.saphyra.skyxplore.lobby.message.domain.MessageView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class MessageViewQueryService {
    private final CharacterQueryService characterQueryService;
    private final LobbyQueryService lobbyQueryService;
    private final MessageStorage messageStorage;

    List<MessageView> getMessages(String characterId, Boolean queryAll) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        return messageStorage.values().stream()
            .filter(message -> message.getLobbyId().equals(lobby.getLobbyId()))
            .filter(message -> queryAll || !message.getQueriedBy().contains(characterId))
            .peek(message -> message.addQueriedBy(characterId))
            .map(this::convertToView)
            .collect(Collectors.toList());
    }

    private MessageView convertToView(Message message) {
        return MessageView.builder()
            .senderId(message.getSender())
            .senderName(characterQueryService.findByCharacterId(message.getSender()).getCharacterName())
            .message(message.getMessage())
            .createdAt(message.getCreatedAt())
            .build();
    }
}
