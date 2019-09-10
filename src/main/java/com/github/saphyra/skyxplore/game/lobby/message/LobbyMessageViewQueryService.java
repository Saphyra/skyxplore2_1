package com.github.saphyra.skyxplore.game.lobby.message;

import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessageView;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class LobbyMessageViewQueryService {
    private final LobbyQueryService lobbyQueryService;
    private final LobbyMessageViewConverter lobbyMessageViewConverter;

    List<LobbyMessageView> getMessages(String characterId, Boolean queryAll) {
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);
        return lobby.getLobbyMessages().stream()
            .filter(message -> queryAll || !message.getQueriedBy().contains(characterId))
            .peek(message -> message.addQueriedBy(characterId))
            .map(lobbyMessageViewConverter::convertDomain)
            .collect(Collectors.toList());
    }
}
