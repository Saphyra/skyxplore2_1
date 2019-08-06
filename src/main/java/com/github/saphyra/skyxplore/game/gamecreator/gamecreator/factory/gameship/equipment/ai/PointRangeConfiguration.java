package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.gameship.equipment.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class PointRangeConfiguration {

    @Value("${game.creation.point-range.min-multiplier}")
    private Double minMultiplier;

    @Value("${game.creation.point-range.max-multiplier}")
    private Double maxMultiplier;
}
