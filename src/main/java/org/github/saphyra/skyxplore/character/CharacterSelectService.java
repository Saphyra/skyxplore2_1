package org.github.saphyra.skyxplore.character;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

import javax.servlet.http.HttpServletResponse;

import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.github.saphyra.skyxplore.common.CookieUtil;
import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class CharacterSelectService {
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;
    private final CookieUtil cookieUtil;

    void selectCharacter(String characterId, String userId, HttpServletResponse response) {
        SkyXpAccessToken skyXpAccessToken = accessTokenDao.findByUserId(userId)
            .orElseThrow(
                () -> new UnauthorizedException("SkyXpAccessToken not found with userId " + userId)
            );
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        skyXpAccessToken.setCharacterId(characterId);
        accessTokenDao.save(skyXpAccessToken);
        cookieUtil.setCookie(response, COOKIE_CHARACTER_ID, characterId);
    }
}
