package skyxplore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.CharacterQueryService;

import java.util.List;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MailFacade {
    private final CharacterQueryService characterQueryService;

    public List<SkyXpCharacter> getAddressees(String characterId, String userId, String name) {
        return characterQueryService.getCharactersCanBeAddressee(name, characterId, userId);
    }
}
