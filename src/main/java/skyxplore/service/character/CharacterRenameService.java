package skyxplore.service.character;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.controller.request.RenameCharacterRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.exception.CharacterNameAlreadyExistsException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterRenameService {
    private final CharacterDao characterDao;
    private final Cache<String, Boolean> characterNameCache;
    private final CharacterQueryService characterQueryService;

    public void renameCharacter(RenameCharacterRequest request, String userId) {
        if (characterQueryService.isCharNameExists(request.getNewCharacterName())) {
            throw new CharacterNameAlreadyExistsException("Character name already exists: " + request.getNewCharacterName());
        }
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        characterNameCache.invalidate(character.getCharacterName());
        character.setCharacterName(request.getNewCharacterName());
        characterDao.renameCharacter(request.getCharacterId(), request.getNewCharacterName());
    }
}
