package com.github.saphyra.skyxplore.game.gamecreator.gamecreator;

import com.github.saphyra.skyxplore.game.game.GameStorage;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.converter.GameGroupingToGameConverterStrategy;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyStorage;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Builder
public class CreateGameService {
    private final GameGroupingQueryService gameGroupingQueryService;
    private final GameGroupingStorage gameGroupingStorage;
    private final GameStorage gameStorage;
    private final List<GameGroupingToGameConverterStrategy> converterStrategies;
    private final LobbyStorage lobbyStorage;

    public void createGameFromGroupings() {
        log.debug("Creating Games for GameGroupings...");
        try {
            gameGroupingQueryService.getGroupingsWithEnoughMembers().forEach(this::createGameFromGrouping);
        } catch (Exception e) {
            log.error("Error occurred during converting GameGroupings to game:", e);
        }
    }

    private void createGameFromGrouping(GameGrouping gameGrouping) {
        try {
            log.info("Creating Game from GameGrouping {} with GameMode {}", gameGrouping.getGameGroupingId(), gameGrouping.getGameMode());
            Game game = converterStrategies.stream()
                .filter(converterStrategy -> converterStrategy.canConvert(gameGrouping.getGameMode()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("GameGroupingToGameConverterStrategy not found for GameMode " + gameGrouping.getGameMode()))
                .convert(gameGrouping);

            gameStorage.put(game.getGameId(), game);
            gameGroupingStorage.remove(gameGrouping.getGameGroupingId());
            gameGrouping.getLockedLobbyIds().forEach(lobbyStorage::remove);
        } catch (Exception e) {
            log.error("Error occurred during converting GameGrouping {} with GameMode {} to Game:", gameGrouping.getGameGroupingId(), gameGrouping.getGameMode(), e);
        }
    }
}
