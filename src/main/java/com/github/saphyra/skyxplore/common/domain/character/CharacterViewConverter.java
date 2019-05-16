package com.github.saphyra.skyxplore.common.domain.character;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.AbstractViewConverter;

@Component
public class CharacterViewConverter extends AbstractViewConverter<SkyXpCharacter, CharacterView> {

    @Override
    public CharacterView convertDomain(SkyXpCharacter domain) {
        return CharacterView.builder()
            .characterId(domain.getCharacterId())
            .characterName(domain.getCharacterName())
            .build();
    }
}
