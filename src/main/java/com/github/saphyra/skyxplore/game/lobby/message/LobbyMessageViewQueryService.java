package com.github.saphyra.skyxplore.game.lobby.message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.domain.message.MessageView;
import com.github.saphyra.skyxplore.common.domain.message.MessageViewConverter;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class LobbyMessageViewQueryService {
    private final LobbyQueryService lobbyQueryService;
    private final MessageViewConverter messageViewConverter;

    List<MessageView> getMessages(String characterId, Boolean queryAll) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        return lobby.getMessages().stream()
            .filter(message -> queryAll || !message.getQueriedBy().contains(characterId))
            .peek(message -> message.addQueriedBy(characterId))
            .map(messageViewConverter::convertDomain)
            .collect(Collectors.toList());
    }
}
