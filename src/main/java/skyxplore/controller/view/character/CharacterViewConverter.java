package skyxplore.controller.view.character;

import org.springframework.stereotype.Component;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.controller.view.AbstractViewConverter;

@Component
public class CharacterViewConverter extends AbstractViewConverter<SkyXpCharacter, CharacterView> {

    @Override
    public CharacterView convertDomain(SkyXpCharacter domain){
        CharacterView view = new CharacterView();
        view.setCharacterId(domain.getCharacterId());
        view.setCharacterName(domain.getCharacterName());
        return view;
    }
}
