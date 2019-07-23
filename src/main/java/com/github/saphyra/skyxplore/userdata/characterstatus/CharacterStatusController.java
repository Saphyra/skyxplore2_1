package com.github.saphyra.skyxplore.userdata.characterstatus;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
class CharacterStatusController {
    private static final String CHARACTER_DESELECT_MAPPING = API_PREFIX + "/character/deselect";
    private static final String GET_CHARACTER_STATUS_MAPPING = API_PREFIX + "/character/status";

    private final AccessTokenDao accessTokenDao;
    private final CharacterStatusQueryService characterStatusQueryService;
    private final CookieUtil cookieUtil;

    @GetMapping(GET_CHARACTER_STATUS_MAPPING)
    String  getCharacterStatus(@CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his characterStatus", characterId);
        return characterStatusQueryService.getCharacterStatus(characterId).name();
    }

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
            cookieUtil.setCookie(response, RequestConstants.COOKIE_CHARACTER_ID, "");
        });
    }
}
