package skyxplore.service.character;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.exception.CharacterNameAlreadyExistsException;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class CharacterRenameService {
    private final CharacterDao characterDao;
    private final Cache<String, Boolean> characterNameCache;
    private final CharacterQueryService characterQueryService;

    public void renameCharacter(RenameCharacterRequest request, String characterId) {
        if (characterQueryService.isCharNameExists(request.getNewCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character name already exists: " + request.getNewCharacterName());
        }
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        character.setCharacterName(request.getNewCharacterName());
        characterDao.save(character);
        characterNameCache.invalidate(character.getCharacterName());
    }
}
