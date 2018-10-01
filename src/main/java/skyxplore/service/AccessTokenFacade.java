package skyxplore.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.user.LoginRequest;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.accesstoken.AuthenticationService;
import skyxplore.service.accesstoken.CharacterSelectService;
import skyxplore.service.accesstoken.LoginService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTokenFacade {
    private final AuthenticationService authenticationService;
    private final CharacterSelectService characterSelectService;
    private final LoginService loginService;

    public boolean isAuthenticated(String userId, String accessTokenId) {
        return authenticationService.isAuthenticated(userId, accessTokenId);
    }

    public Boolean isCharacterActive(String characterId) {
        return authenticationService.isCharacterActive(characterId);
    }

    public AccessToken login(LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

    public void logout(String userId, String accessTokenId) {
        loginService.logout(userId, accessTokenId);
    }

    public void selectCharacter(String characterId, String userId) {
        characterSelectService.selectCharacter(characterId, userId);
    }
}
