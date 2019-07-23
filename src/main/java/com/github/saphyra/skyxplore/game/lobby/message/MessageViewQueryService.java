package com.github.saphyra.skyxplore.game.lobby.message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.message.domain.Message;
import com.github.saphyra.skyxplore.game.lobby.message.domain.MessageView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class MessageViewQueryService {
    private final CharacterQueryService characterQueryService;
    private final LobbyQueryService lobbyQueryService;

    List<MessageView> getMessages(String characterId, Boolean queryAll) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        return lobby.getMessages().stream()
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
