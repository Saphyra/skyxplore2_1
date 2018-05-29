package skyxplore.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.auth.controller.request.LoginRequest;
import skyxplore.auth.domain.AccessToken;
import skyxplore.auth.domain.exception.BadCredentialsException;
import skyxplore.auth.domain.exception.BadRequestAuthException;
import skyxplore.dataaccess.auth.AccessTokenDao;
import skyxplore.global.filter.AuthFilter;
import skyxplore.pages.index.domain.SkyXpUser;
import skyxplore.pages.index.service.UserService;
import skyxplore.util.IdGenerator;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTokenService {

    private final AccessTokenDao accessTokenDao;
    private final UserService userService;
    private final IdGenerator idGenerator;

    public AccessToken getAccessTokenByUserId(Long userId) {
        return accessTokenDao.findByUserId(userId);
    }

    public boolean isAuthenticated(String userIdValue, String accessTokenId, HttpServletResponse response) {
        log.info("Authenticating user {}", userIdValue);
        try {
            Long userId = validate(userIdValue, accessTokenId);

            AccessToken accessToken = getAccessTokenByUserId(userId);
            if (accessToken == null) {
                throw new BadCredentialsException("No valid accessToken for user " + userIdValue);
            } else if (!accessToken.getAccessTokenId().equals(accessTokenId)) {
                throw new BadCredentialsException("Invalid accessToken for user " + userIdValue);
            }
        } catch (BadRequestAuthException e) {
            log.info("Authentication failed: {}", e.getMessage());
            return false;
        }
        log.info("Authentication successful.");
        return true;
    }

    private Long validate(String userIdValue, String accessTokenId) {
        if (userIdValue == null) {
            throw new BadRequestAuthException("Required cookies not found:" + AuthFilter.COOKIE_USER_ID);
        }
        if (accessTokenId == null) {
            throw new BadRequestAuthException("Required cookie not found:" + AuthFilter.COOKIE_ACCESS_TOKEN);
        }
        Long userId;
        try {
            userId = Long.valueOf(userIdValue);
        } catch (NumberFormatException e) {
            throw new BadRequestAuthException("Invalid userId type.");
        }
        return userId;
    }

    public AccessToken login(LoginRequest loginRequest) {
        SkyXpUser user = getAuthenticatedUser(loginRequest);
        log.info("{} authentication successful.", user.getUserId());
        AccessToken accessToken = accessTokenDao.findByUserId(user.getUserId());
        if (accessToken != null) {
            log.info("Access token already exists. Deleting...");
            accessTokenDao.delete(accessToken);
        }
        accessToken = createAccessToken(user);
        accessTokenDao.save(accessToken);
        return accessToken;
    }

    private SkyXpUser getAuthenticatedUser(LoginRequest loginRequest) {
        SkyXpUser user = userService.getUserByName(loginRequest.getUserName());
        if (user == null) {
            throw new BadCredentialsException("User cannot be found. Username: " + loginRequest.getUserName());
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new BadCredentialsException("Password is incorrect.");
        }
        return user;
    }

    private AccessToken createAccessToken(SkyXpUser user) {
        AccessToken token = new AccessToken();
        token.setAccessTokenId(idGenerator.getRandomId());
        token.setUserId(user.getUserId());
        token.setLastAccess(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        return token;
    }

}
