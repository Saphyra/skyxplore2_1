package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.service.accesstoken.CharacterSelectService;
import skyxplore.util.CookieUtil;

import javax.servlet.http.HttpServletResponse;

import static skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;

@SuppressWarnings("WeakerAccess")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private static final String SELECT_CHARACTER_MAPPING = "character/select/{characterId}";

    private final CharacterSelectService characterSelectService;
    private final CookieUtil cookieUtil;

    @PostMapping(SELECT_CHARACTER_MAPPING)
    public void selectCharacter(
        @PathVariable("characterId") String characterId,
        @CookieValue(COOKIE_USER_ID) String userId,
        HttpServletResponse response
    ) {
        log.info("{} selected character {}", userId, characterId);
        characterSelectService.selectCharacter(characterId, userId);
        cookieUtil.setCookie(response, COOKIE_CHARACTER_ID, characterId);
    }
}
