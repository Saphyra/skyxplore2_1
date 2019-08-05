package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
@Slf4j
class AddToExistingGroupingService {
    private final GameGroupFactory gameGroupFactory;

    void addToExistingGrouping(Lobby lobby, GameGrouping availableGrouping, int maxGroupSize) {
        lobby.getMembers().forEach(lobbyMember -> {
            GameGroupCharacter gameGroupCharacter = GameGroupCharacter.builder()
                .characterId(lobbyMember.getCharacterId())
                .build();
            GameGroup newGroup = gameGroupFactory.createGroup(Arrays.asList(gameGroupCharacter), lobby.isAutoFill(), maxGroupSize);
            availableGrouping.addGroup(newGroup);
        });
        availableGrouping.lockLobby(lobby.getLobbyId());
    }
}
