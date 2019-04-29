package skyxplore.service.accesstoken;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterSelectService {
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;

    public void selectCharacter(String characterId, String userId) {
        SkyXpAccessToken skyXpAccessToken = accessTokenDao.findByUserId(userId)
            .orElseThrow(
                () -> new UnauthorizedException("SkyXpAccessToken not found with userId " + userId)
            );
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        skyXpAccessToken.setCharacterId(characterId);
        accessTokenDao.save(skyXpAccessToken);
    }
}
