package com.github.saphyra.skyxplore.game.gamecreator;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@Slf4j
@Service
@RequiredArgsConstructor
//TODO unit test
public class GameCreatorService {
    private final FillGroupingService fillGroupingService;

    @Scheduled(fixedDelay = 4000L)
    void createGames() {
        fillGroupingService.fillGroupings();
    }
}
