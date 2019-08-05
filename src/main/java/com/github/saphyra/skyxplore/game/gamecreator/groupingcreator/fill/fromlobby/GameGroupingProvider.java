package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupSizeRangeProvider;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupingFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
//TODO unit test
class GameGroupingProvider {
    private final GameGroupingStorage gameGroupingStorage;
    private final GameGroupFactory gameGroupFactory;
    private final GameGroupingFactory gameGroupingFactory;
    private final GameGroupSizeRangeProvider gameGroupSizeRangeProvider;

    void createGroupingFrom(Lobby lobby, int maxGroupSize, int expectedGameMembersAmount) {
        GameGroupSizeRange gameGroupSizeRange = gameGroupSizeRangeProvider.getGameModeSizeRange(GameMode.VS);
        GameGrouping gameGrouping = gameGroupingFactory.create(lobby, gameGroupFactory.createGroups(lobby, maxGroupSize), expectedGameMembersAmount, gameGroupSizeRange);
        gameGroupingStorage.put(gameGrouping.getGameGroupingId(), gameGrouping);
    }
}
