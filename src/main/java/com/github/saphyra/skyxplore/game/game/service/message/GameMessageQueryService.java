package com.github.saphyra.skyxplore.game.game.service.message;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.game.GameQueryService;
import com.github.saphyra.skyxplore.game.game.domain.message.ChatRoom;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class GameMessageQueryService {
    private final GameQueryService gameQueryService;

    Map<UUID, List<GameMessage>> getMessages(String characterId, boolean queryAll) {
        Map<UUID, List<GameMessage>> messageMapping = gameQueryService.findByCharacterIdValidated(characterId)
            .getMessages()
            .getRoomsOfCharacter(characterId)
            .stream()
            .collect(Collectors.toMap(ChatRoom::getRoomId, ChatRoom::getMessages));

        if(!queryAll){
            messageMapping.values()
                .forEach(gameMessages -> gameMessages.removeIf(gameMessage -> gameMessage.isQueriedBy(characterId)));
        }

        messageMapping.values()
            .stream()
            .flatMap(Collection::stream)
            .forEach(gameMessage -> gameMessage.addQueriedBy(characterId));
        return messageMapping;
    }
}
