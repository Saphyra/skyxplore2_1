package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupSizeRangeProvider;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupingFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
class GameGroupingCreator {
    private final GameGroupingStorage gameGroupingStorage;
    private final GameGroupFactory gameGroupFactory;
    private final GameGroupingFactory gameGroupingFactory;
    private final GameGroupSizeRangeProvider gameGroupSizeRangeProvider;

    void createGrouping(Lobby lobby, int maxGroupSize, int expectedGameMembersAmount) {
        GameGroupSizeRange gameGroupSizeRange = gameGroupSizeRangeProvider.getGameModeSizeRange(lobby.getGameMode());
        List<GameGroup> gameGroups = gameGroupFactory.createGroups(lobby, maxGroupSize);
        GameGrouping gameGrouping = gameGroupingFactory.create(lobby, gameGroups, expectedGameMembersAmount, gameGroupSizeRange);
        gameGroupingStorage.put(gameGrouping.getGameGroupingId(), gameGrouping);
    }
}
