package skyxplore.service.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterDeleteService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;

    @Transactional
    public void deleteCharacter(String characterId, String userId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        characterDao.deleteById(character.getCharacterId());
    }
}
