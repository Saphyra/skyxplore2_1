package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameCharacter;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameCharacterFactory {
    private final IdGenerator idGenerator;

    public GameCharacter createGameCharacter(String characterId, boolean isAi) {
        GameCharacter gameCharacter = GameCharacter.builder()
            .characterId(characterId)
            .isAi(isAi)
            .build();
        log.debug("GameCharacter created: {}", gameCharacter);
        return gameCharacter;
    }

    public GameCharacter createAi() {
        return createGameCharacter(idGenerator.generateRandomId(), true);
    }
}
