package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.OffsetDateTimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameGroupingFactory {
    private final IdGenerator idGenerator;
    private final OffsetDateTimeProvider offsetDateTimeProvider;

    public GameGrouping create(Lobby lobby, List<GameGroup> gameGroups, int expectedGameMembersAmount, GameGroupSizeRange gameGroupSizeRange) {
        GameGrouping gameGrouping = GameGrouping.builder()
            .gameGroupingId(idGenerator.randomUUID())
            .minimumGameMembersAmount(expectedGameMembersAmount)
            .gameGroupSizeRange(gameGroupSizeRange)
            .gameMode(lobby.getGameMode())
            .data(lobby.getData())
            .createdAt(offsetDateTimeProvider.getCurrentDate())
            .build()
            .lockLobby(lobby.getLobbyId())
            .addGroups(gameGroups);
        log.debug("Created GameGrouping: {}", gameGrouping);
        return gameGrouping;
    }
}
