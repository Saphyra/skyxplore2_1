package com.github.saphyra.skyxplore.game.gamecreator;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;

@Component
public class GameGroupingStorage extends ConcurrentHashMap<UUID, GameGrouping> {
}
