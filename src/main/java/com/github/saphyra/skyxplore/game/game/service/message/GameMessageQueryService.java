package com.github.saphyra.skyxplore.game.game.service.message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.game.GameQueryService;
import com.github.saphyra.skyxplore.game.game.domain.message.ChatRoom;
import com.github.saphyra.skyxplore.game.game.view.ChatRoomView;
import com.github.saphyra.skyxplore.game.game.view.GameMessageView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class GameMessageQueryService {
    private final GameMessageViewConverter gameMessageViewConverter;
    private final GameQueryService gameQueryService;

    List<ChatRoomView> getMessages(String characterId, boolean queryAll) {
        return gameQueryService.findByCharacterIdValidated(characterId)
            .getMessages()
            .getRoomsOfCharacter(characterId)
            .stream()
            .map(chatRoom -> convertChatRoom(chatRoom, characterId, queryAll))
            .collect(Collectors.toList());
    }

    private ChatRoomView convertChatRoom(ChatRoom chatRoom, String characterId, boolean queryAll) {
        return ChatRoomView.builder()
            .roomId(chatRoom.getRoomId())
            .roomName(chatRoom.getDisplayName())
            .messages(fetchMessages(chatRoom, characterId, queryAll))
            .defaultRoom(chatRoom.isDefaultRoom())
            .build();
    }

    private List<GameMessageView> fetchMessages(ChatRoom chatRoom, String characterId, boolean queryAll) {
        return chatRoom.getMessages().stream()
            .filter(gameMessage -> queryAll || !gameMessage.isQueriedBy(characterId))
            .peek(gameMessage -> gameMessage.addQueriedBy(characterId))
            .map(gameMessageViewConverter::convertDomain)
            .collect(Collectors.toList());
    }
}
