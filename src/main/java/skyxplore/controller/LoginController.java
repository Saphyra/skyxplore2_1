package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.user.LoginRequest;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.AccessTokenFacade;
import skyxplore.util.CookieUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static skyxplore.filter.FilterHelper.*;

@SuppressWarnings("WeakerAccess")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private static final String LOGIN_MAPPING = "login";
    private static final String SELECT_CHARACTER_MAPPING = "character/select/{characterId}";

    private final AccessTokenFacade accessTokenFacade;
    private final CookieUtil cookieUtil;

    @PostMapping(LOGIN_MAPPING)
    public void login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Login request arrived.");
        AccessToken accessToken = accessTokenFacade.login(loginRequest);
        cookieUtil.setCookie(response, COOKIE_USER_ID, accessToken.getUserId());
        cookieUtil.setCookie(response, COOKIE_ACCESS_TOKEN, accessToken.getAccessTokenId());
        log.info("Access token successfully created, and sent for the client.");
    }

    @PostMapping(SELECT_CHARACTER_MAPPING)
    public void selectCharacter(
        @PathVariable("characterId") String characterId,
        @CookieValue(COOKIE_USER_ID) String userId,
        HttpServletResponse response
    ) {
        log.info("{} selected character {}", userId, characterId);
        accessTokenFacade.selectCharacter(characterId, userId);
        cookieUtil.setCookie(response, COOKIE_CHARACTER_ID, characterId);
    }
}
