package com.github.saphyra.skyxplore.game.lobby.message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessageView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class LobbyMessageViewQueryService {
    private final LobbyQueryService lobbyQueryService;
    private final LobbyMessageViewConverter lobbyMessageViewConverter;

    List<LobbyMessageView> getMessages(String characterId, Boolean queryAll) {
        return lobbyQueryService.findByCharacterIdValidated(characterId).getLobbyMessages().stream()
            .filter(message -> queryAll || !message.getQueriedBy().contains(characterId))
            .peek(message -> message.addQueriedBy(characterId))
            .map(lobbyMessageViewConverter::convertDomain)
            .collect(Collectors.toList());
    }
}
