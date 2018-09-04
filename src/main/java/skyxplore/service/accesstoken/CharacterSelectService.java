package skyxplore.service.accesstoken;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.character.CharacterQueryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class CharacterSelectService {
    private final Cache<String, Optional<AccessToken>> accessTokenCache;
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;

    public void selectCharacter(String characterId, String userId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        AccessToken accessToken = accessTokenDao.findByUserId(userId);
        accessToken.setCharacterId(characterId);
        accessTokenCache.invalidate(accessToken.getUserId());
        accessTokenDao.save(accessToken);
    }
}
