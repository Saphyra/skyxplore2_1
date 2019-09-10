package com.github.saphyra.skyxplore.game.lobby.message;

import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessage;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessageView;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LobbyMessageViewConverter extends AbstractViewConverter<LobbyMessage, LobbyMessageView> {
    private final CharacterQueryService characterQueryService;

    @Override
    public LobbyMessageView convertDomain(LobbyMessage lobbyMessage) {
        return LobbyMessageView.builder()
            .senderId(lobbyMessage.getSender())
            //TODO replace with cache
            .senderName(characterQueryService.findByCharacterIdValidated(lobbyMessage.getSender()).getCharacterName())
            .message(lobbyMessage.getMessage())
            .createdAt(lobbyMessage.getCreatedAt())
            .build();
    }
}
