package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby.vs;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupSizeRangeProvider;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupingFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby.AddToGroupingStrategy;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
//TODO unit test
class VsAddToGroupingStrategy implements AddToGroupingStrategy {
    private static final int MAX_GROUP_SIZE = 1;
    private static final int EXPECTED_GAME_MEMBERS_AMOUNT = 2;

    private final GameGroupingQueryService gameGroupingQueryService;
    private final GameGroupingStorage gameGroupingStorage;
    private final GameGroupFactory gameGroupFactory;
    private final GameGroupingFactory gameGroupingFactory;
    private final GameGroupSizeRangeProvider gameGroupSizeRangeProvider;

    @Override
    public boolean canAdd(GameMode gameMode) {
        return gameMode == GameMode.VS;
    }

    @Override
    public void add(Lobby lobby) {
        Optional<GameGrouping> availableGrouping = getAvailableGrouping(lobby.getMembers().size());

        if (availableGrouping.isPresent()) {
            GameGrouping gameGrouping = availableGrouping.get();
            log.info("Adding lobby {} to existing grouping {}", lobby.getLobbyId(), gameGrouping.getGameGroupingId());
            addToExistingGrouping(lobby, gameGrouping);
        } else {
            log.info("Creating new GameGrouping from lobby {}", lobby.getLobbyId());
            createGroupingFrom(lobby);
        }
    }

    private Optional<GameGrouping> getAvailableGrouping(int lobbySize) {
        return gameGroupingQueryService.getGroupingsByGameMode(GameMode.VS).stream()
            .filter(gameGrouping -> gameGrouping.getGameGroups().size() + lobbySize <= EXPECTED_GAME_MEMBERS_AMOUNT)
            .findAny();
    }

    private void addToExistingGrouping(Lobby lobby, GameGrouping availableGrouping) {
        lobby.getMembers().forEach(lobbyMember -> {
            GameGroupCharacter gameGroupCharacter = GameGroupCharacter.builder().characterId(lobbyMember.getCharacterId()).build();
            availableGrouping.addGroup(gameGroupFactory.createGroup(Arrays.asList(gameGroupCharacter), false, MAX_GROUP_SIZE));
        });
        availableGrouping.lockLobby(lobby.getLobbyId());
    }

    private void createGroupingFrom(Lobby lobby) {
        GameGroupSizeRange gameGroupSizeRange = gameGroupSizeRangeProvider.getGameModeSizeRange(GameMode.VS);
        GameGrouping gameGrouping = gameGroupingFactory.create(lobby, gameGroupFactory.createGroups(lobby, MAX_GROUP_SIZE), EXPECTED_GAME_MEMBERS_AMOUNT, gameGroupSizeRange);
        gameGroupingStorage.put(gameGrouping.getGameGroupingId(), gameGrouping);
    }
}
