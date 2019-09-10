package com.github.saphyra.skyxplore.game.game;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameQueryService {
    private final GameStorage gameStorage;

    public Optional<Game> findByCharacterId(String characterId) {
        return gameStorage.values().stream()
            .filter(game -> game.containsCharacter(characterId))
            .findFirst();
    }

    public Game findByCharacterIdValidated(String characterId){
        return findByCharacterId(characterId)
            .orElseThrow(() -> ExceptionFactory.gameNotFound(characterId));
    }
}
