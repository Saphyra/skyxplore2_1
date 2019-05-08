package com.github.saphyra.skyxplore.common.domain.character;

import org.springframework.stereotype.Component;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.AbstractViewConverter;

@Component
public class CharacterViewConverter extends AbstractViewConverter<SkyXpCharacter, CharacterView> {

    @Override
    public CharacterView convertDomain(SkyXpCharacter domain) {
        CharacterView view = new CharacterView();
        view.setCharacterId(domain.getCharacterId());
        view.setCharacterName(domain.getCharacterName());
        return view;
    }
}
