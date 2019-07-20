package com.github.saphyra.skyxplore.characterstatus;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class CharacterStatusController {
    private static final String CHARACTER_DESELECT_MAPPING = API_PREFIX + "//character/deselect";

    private final AccessTokenDao accessTokenDao;
    private final CharacterStatusCache characterStatusCache;
    private final CookieUtil cookieUtil;

    @DeleteMapping(CHARACTER_DESELECT_MAPPING)
    void characterDeselect(
        @CookieValue(RequestConstants.COOKIE_USER_ID) String userId,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId,
        HttpServletResponse response
    ) {
        log.debug("CharacterDeselect request arrived with characterId {}", characterId);
        accessTokenDao.findByCharacterId(characterId).ifPresent(accessToken -> {
            if (!accessToken.getUserId().equals(userId)) {
                throw new ForbiddenException(userId + " cannot deselect character " + characterId);
            }
            accessToken.setCharacterId(null);
            accessTokenDao.save(accessToken);
            characterStatusCache.invalidate(characterId);
            cookieUtil.setCookie(response, RequestConstants.COOKIE_CHARACTER_ID, "");
        });
    }
}
