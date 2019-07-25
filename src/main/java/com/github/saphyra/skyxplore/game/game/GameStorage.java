package com.github.saphyra.skyxplore.game.game;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.Game;

@Component
public class GameStorage extends ConcurrentHashMap<UUID, Game> {
}
