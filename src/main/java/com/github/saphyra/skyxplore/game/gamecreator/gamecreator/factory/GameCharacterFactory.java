package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.domain.GameCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.ainame.CharacterNameResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GameCharacterFactory {
    private final CharacterNameResolver characterNameResolver;

    public List<GameCharacter> create(List<GameGroupCharacter> characters) {
        return characters.stream()
            .map(this::create)
            .collect(Collectors.toList());
    }

    private GameCharacter create(GameGroupCharacter gameGroupCharacter) {
        GameCharacter gameCharacter = GameCharacter.builder()
            .characterId(gameGroupCharacter.getCharacterId())
            .characterName(characterNameResolver.getCharacterName(gameGroupCharacter))
            .originallyAi(gameGroupCharacter.isAi())
            .build();
        log.debug("Created GameCharacter: {}", gameCharacter);
        return gameCharacter;
    }
}
