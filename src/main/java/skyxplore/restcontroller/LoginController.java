package skyxplore.restcontroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skyxplore.restcontroller.request.LoginRequest;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.AccessTokenService;
import skyxplore.filter.AuthFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final AccessTokenService accessTokenService;

    @PostMapping("login")
    public void login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response){
        log.info("Login request arrived.");
        AccessToken accessToken = accessTokenService.login(loginRequest);
        response.addCookie(createLoginCookie(AuthFilter.COOKIE_USER_ID, accessToken.getUserId().toString()));
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
