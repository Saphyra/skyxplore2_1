package skyxplore.service.accesstoken;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.cache.AccessTokenCache;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.character.CharacterQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterSelectService {
    private final AccessTokenCache accessTokenCache;
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;

    public void selectCharacter(String characterId, String userId) {
        AccessToken accessToken = accessTokenCache.get(userId)
            .orElseThrow(
                () -> new UnauthorizedException("AccessToken not found with userId " + userId)
            );
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        accessToken.setCharacterId(characterId);
        accessTokenCache.invalidate(accessToken.getUserId());
        accessTokenDao.save(accessToken);
    }
}
