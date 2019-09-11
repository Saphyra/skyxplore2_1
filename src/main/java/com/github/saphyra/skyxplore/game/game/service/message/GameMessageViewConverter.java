package com.github.saphyra.skyxplore.game.game.service.message;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessage;
import com.github.saphyra.skyxplore.game.game.view.GameMessageView;

@Component
//TODO unit test
public class GameMessageViewConverter implements ViewConverter<GameMessage, GameMessageView> {
    @Override
    public GameMessageView convertDomain(GameMessage domain) {
        return GameMessageView.builder()
            .senderId(domain.getSenderId())
            .senderName(domain.getSenderName())
            .message(domain.getMessage())
            .createdAt(domain.getCreatedAt())
            .build();
    }
}
