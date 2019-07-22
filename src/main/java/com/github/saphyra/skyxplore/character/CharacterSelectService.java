package com.github.saphyra.skyxplore.character;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

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
        cookieUtil.setCookie(response, RequestConstants.COOKIE_CHARACTER_ID, characterId);
    }
}
