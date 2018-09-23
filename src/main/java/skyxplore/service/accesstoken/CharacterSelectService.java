package skyxplore.service.accesstoken;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.exception.base.UnauthorizedException;
import skyxplore.service.character.CharacterQueryService;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterSelectService {
    private final Cache<String, Optional<AccessToken>> accessTokenCache;
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;

    public void selectCharacter(String characterId, String userId) {
        AccessToken accessToken = null;
        try {
            accessToken = accessTokenCache.get(userId)
                .orElseThrow(
                    () -> new UnauthorizedException("Accesstoken not found with userId " + userId)
                );
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        accessToken.setCharacterId(characterId);
        accessTokenCache.invalidate(accessToken.getUserId());
        accessTokenDao.save(accessToken);
    }
}
