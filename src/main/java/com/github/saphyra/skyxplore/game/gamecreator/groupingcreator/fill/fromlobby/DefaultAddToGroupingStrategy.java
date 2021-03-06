package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
class DefaultAddToGroupingStrategy implements AddToGroupingStrategy {
    private static final int MAX_GROUP_SIZE = 1;
    private static final int EXPECTED_GAME_MEMBERS_AMOUNT = 2;

    private final AddToExistingGroupingService addToExistingGroupingService;
    private final AvailableGroupingProvider availableGroupingProvider;
    private final GameGroupingCreator gameGroupingCreator;

    @Override
    public boolean canAdd(GameMode gameMode) {
        return gameMode == GameMode.VS;
    }

    @Override
    public void add(Lobby lobby) {
        Optional<GameGrouping> availableGrouping = availableGroupingProvider.getAvailableGrouping(lobby.getGameMode(), lobby.getMembers().size(), EXPECTED_GAME_MEMBERS_AMOUNT);

        if (availableGrouping.isPresent()) {
            GameGrouping gameGrouping = availableGrouping.get();
            log.info("Adding lobby {} to existing grouping {}", lobby.getLobbyId(), gameGrouping.getGameGroupingId());
            addToExistingGroupingService.addToExistingGrouping(lobby, gameGrouping, MAX_GROUP_SIZE);
        } else {
            log.info("Creating new GameGrouping from lobby {}", lobby.getLobbyId());
            gameGroupingCreator.createGrouping(lobby, MAX_GROUP_SIZE, EXPECTED_GAME_MEMBERS_AMOUNT);
        }
    }
}
