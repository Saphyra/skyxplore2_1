package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class GroupingCreatorConfiguration {
    @Value("${game.creation.auto-fill.delay-seconds}")
    private int autoFillDelaySeconds;
}
