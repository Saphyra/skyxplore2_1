package com.github.saphyra.skyxplore.game.gamecreator;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.gamecreator.strategies.AddToGroupingStrategy;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class FillGroupingService {
    private final LobbyQueryService lobbyQueryService;
    private final GameGroupingStorage gameGroupingStorage;
    private final List<AddToGroupingStrategy> addToGroupingStrategies;

    void fillGroupings() {
        try {
            lobbyQueryService.getLobbiesInQueue().stream()
                .filter(lobby -> !isLocked(lobby))
                .forEach(lobby -> {
                    try {
                        addToGrouping(lobby);
                    } catch (Exception e) {
                        log.error("Exception occurred during GameGroupingCreation:", e);
                    }
                });
        } catch (Exception e) {
            log.error("Exception occurred during fillGroupings:", e);
        }
    }

    private boolean isLocked(Lobby lobby) {
        return gameGroupingStorage.values().stream()
            .anyMatch(gameGrouping -> gameGrouping.getLockedLobbyIds().contains(lobby.getLobbyId()));
    }

    private void addToGrouping(Lobby lobby) {
        addToGroupingStrategies.stream()
            .filter(addToGroupingStrategy -> addToGroupingStrategy.canAdd(lobby.getGameMode()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("AddToGroupingStrategy not found for GameMode " + lobby.getGameMode()))
            .add(lobby);
    }
}
