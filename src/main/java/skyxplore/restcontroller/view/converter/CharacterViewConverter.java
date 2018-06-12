package skyxplore.restcontroller.view.converter;

import org.springframework.stereotype.Component;
import skyxplore.restcontroller.view.CharacterView;
import skyxplore.domain.character.SkyXpCharacter;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CharacterViewConverter {
    public List<CharacterView> convertDomain(List<SkyXpCharacter> domains){
        return domains.stream().map(this::convertDomain).collect(Collectors.toList());
    }

    public CharacterView convertDomain(SkyXpCharacter domain){
        CharacterView view = new CharacterView();
        view.setCharacterId(domain.getCharacterId());
        view.setCharacterName(domain.getCharacterName());
        return view;
    }
}
