package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
@Builder
public class FillFromLobbyService {
    private final LobbyQueryService lobbyQueryService;
    private final GameGroupingStorage gameGroupingStorage;
    private final List<AddToGroupingStrategy> addToGroupingStrategies;

    public void fillGroupingsWithLobbies() {
        log.info("Creating groupings from lobbies...");
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
            log.error("Exception occurred during fillGroupingsWithLobbies:", e);
        }
    }

    private boolean isLocked(Lobby lobby) {
        return gameGroupingStorage.values().stream()
            .anyMatch(gameGrouping -> gameGrouping.getLockedLobbyIds().contains(lobby.getLobbyId()));
    }

    private void addToGrouping(Lobby lobby) {
        log.info("Adding to GameGrouping: {}, GameMode: {}", lobby.getLobbyId(), lobby.getGameMode());
        addToGroupingStrategies.stream()
            .filter(addToGroupingStrategy -> addToGroupingStrategy.canAdd(lobby.getGameMode()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("AddToGroupingStrategy not found for GameMode " + lobby.getGameMode()))
            .add(lobby);
    }
}
