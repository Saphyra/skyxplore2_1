package skyxplore.service.accesstoken;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.character.CharacterQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class CharacterSelectService {
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;

    public void selectCharacter(String characterId, String userId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        AccessToken accessToken = accessTokenDao.findByUserId(userId);
        accessToken.setCharacterId(characterId);
        accessTokenDao.save(accessToken);
    }
}
