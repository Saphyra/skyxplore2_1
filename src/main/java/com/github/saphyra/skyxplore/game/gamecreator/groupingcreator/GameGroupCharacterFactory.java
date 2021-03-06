package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameGroupCharacterFactory {
    private final IdGenerator idGenerator;

    GameGroupCharacter createGameGroupCharacter(String characterId, boolean isAi) {
        GameGroupCharacter gameGroupCharacter = GameGroupCharacter.builder()
            .characterId(characterId)
            .isAi(isAi)
            .build();
        log.debug("GameGroupCharacter created: {}", gameGroupCharacter);
        return gameGroupCharacter;
    }

    public GameGroupCharacter createAi() {
        return createGameGroupCharacter(idGenerator.generateRandomId(), true);
    }
}
