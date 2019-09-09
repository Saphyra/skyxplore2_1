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
class GameCreatorService {
    private final FillGroupingService fillGroupingService;
    private final CreateGameService createGameService;

    @Scheduled(fixedDelayString = "${game.creation.interval:4000}")
    void createGames() {
        log.debug("Creating games...");
        fillGroupingService.fillGroupingsWithLobbies();
        fillGroupingService.fillGroupingsWithAis();
        createGameService.createGameFromGroupings();
        log.debug("Creating games finished...");
    }
}
