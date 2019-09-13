package com.github.saphyra.skyxplore.game.gamecreator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GroupingCreatorConfiguration;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.util.OffsetDateTimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameGroupingQueryService {
    private final GroupingCreatorConfiguration configuration;
    private final GameGroupingStorage gameGroupingStorage;
    private final OffsetDateTimeProvider offsetDateTimeProvider;

    public List<GameGrouping> getAutoFillableGameGroupings() {
        return gameGroupingStorage.values().stream()
            .filter(this::isAutoFillDelayElapsed)
            .filter(gameGrouping -> !gameGrouping.hasEnoughMembers())
            .collect(Collectors.toList());
    }

    private boolean isAutoFillDelayElapsed(GameGrouping gameGrouping) {
        return gameGrouping.getCreatedAt().plusSeconds(configuration.getAutoFillDelaySeconds()).isBefore(offsetDateTimeProvider.getCurrentDate());
    }

    public List<GameGrouping> getGroupingsByGameMode(GameMode gameMode) {
        return gameGroupingStorage.values().stream()
            .filter(gameGrouping -> gameGrouping.getGameMode() == gameMode)
            .collect(Collectors.toList());
    }

    public List<GameGrouping> getGroupingsWithEnoughMembers() {
        return gameGroupingStorage.values().stream()
            .filter(GameGrouping::hasEnoughMembers)
            .collect(Collectors.toList());
    }
}
