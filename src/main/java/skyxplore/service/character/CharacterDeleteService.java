package skyxplore.service.character;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.CharacterDeleteRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.domain.character.SkyXpCharacter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterDeleteService {
    private final CharacterDao characterDao;
    private final CharacterQueryService characterQueryService;

    @Transactional
    public void deleteCharacter(CharacterDeleteRequest request, String userId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        characterDao.deleteById(character.getCharacterId());
    }
}
