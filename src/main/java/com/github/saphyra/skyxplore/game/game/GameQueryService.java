package com.github.saphyra.skyxplore.game.game;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//TODO unit test
public class GameQueryService {
    private final GameStorage gameStorage;

    public Optional<Game> findByCharacterId(String characterId) {
        return gameStorage.values().stream()
            .filter(game -> game.containsCharacter(characterId))
            .findFirst();
    }
}
