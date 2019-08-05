package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
class AvailableGroupingProvider {
    private final GameGroupingQueryService gameGroupingQueryService;

    Optional<GameGrouping> getAvailableGrouping(GameMode gameMode, int lobbySize, int expectedGameMembersAmount) {
        return gameGroupingQueryService.getGroupingsByGameMode(gameMode).stream()
            .filter(gameGrouping -> gameGrouping.getGameGroups().size() + lobbySize <= expectedGameMembersAmount)
            .findAny();
    }
}
