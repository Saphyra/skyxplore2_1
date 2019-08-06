package com.github.saphyra.skyxplore.common.domain.message;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageViewConverter extends AbstractViewConverter<Message, MessageView> {
    private final CharacterQueryService characterQueryService;

    @Override
    public MessageView convertDomain(Message message) {
        return MessageView.builder()
            .senderId(message.getSender())
            .senderName(characterQueryService.findByCharacterId(message.getSender()).getCharacterName())
            .message(message.getMessage())
            .createdAt(message.getCreatedAt())
            .build();
    }
}
