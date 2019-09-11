package com.github.saphyra.skyxplore.game.game.service.message;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.game.GameQueryService;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessage;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class GameMessageFactory {
    private final DateTimeUtil dateTimeUtil;
    private final GameQueryService gameQueryService;
    private final IdGenerator idGenerator;

    public GameMessage create(String senderId, String message) {
        return GameMessage.builder()
            .messageId(idGenerator.randomUUID())
            .senderId(senderId)
            .senderName(gameQueryService.findByCharacterIdValidated(senderId).getNameOfCharacter(senderId))
            .message(message)
            .createdAt(dateTimeUtil.nowEpoch())
            .build();
    }
}
