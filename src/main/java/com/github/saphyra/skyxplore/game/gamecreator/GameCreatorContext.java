package com.github.saphyra.skyxplore.game.gamecreator;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.subservice.ExtenderService;
import com.github.saphyra.skyxplore.data.subservice.ShipService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@Getter
@RequiredArgsConstructor
public class GameCreatorContext {
    private final ExtenderService extenderService;
    private final ShipService shipService;
}
