package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.controller.request.LoginRequest;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.AccessTokenFacade;
import skyxplore.filter.AuthFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class LoginController {
    private final AccessTokenFacade accessTokenFacade;

    @PostMapping("login")
    public void login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response){
        log.info("Login friendrequest arrived.");
        AccessToken accessToken = accessTokenFacade.login(loginRequest);
        response.addCookie(createLoginCookie(AuthFilter.COOKIE_USER_ID, accessToken.getUserId()));
        response.addCookie(createLoginCookie(AuthFilter.COOKIE_ACCESS_TOKEN, accessToken.getAccessTokenId()));
        log.info("Access token successfully created, and sent for the client.");
    }

    private Cookie createLoginCookie(String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        return cookie;
    }
}
