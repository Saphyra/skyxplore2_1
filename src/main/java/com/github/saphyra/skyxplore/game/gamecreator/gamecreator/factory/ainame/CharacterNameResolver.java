package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.ainame;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CharacterNameResolver {
    private final AiNameGenerator aiNameGenerator;
    private final CharacterQueryService characterQueryService;

    public String getCharacterName(GameGroupCharacter gameGroupCharacter) {
        return gameGroupCharacter.isAi() ?
            aiNameGenerator.generateCharacterName()
            : characterQueryService.findByCharacterIdValidated(gameGroupCharacter.getCharacterId()).getCharacterName();
    }
}
