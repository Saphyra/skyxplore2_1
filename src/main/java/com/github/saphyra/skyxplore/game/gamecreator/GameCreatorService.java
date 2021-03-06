package com.github.saphyra.skyxplore.game.gamecreator;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.CreateGameService;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.FillGroupingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@Slf4j
@Service
@RequiredArgsConstructor
public class GameCreatorService {
    private final FillGroupingService fillGroupingService;
    private final CreateGameService createGameService;

    @Scheduled(fixedDelay = 4000L)
    void createGames() {
        log.info("Creating games...");
        fillGroupingService.fillGroupingsWithLobbies();
        fillGroupingService.fillGroupingsWithAis();
        createGameService.createGameFromGroupings();
        log.info("Creating games finished...");
    }
}
