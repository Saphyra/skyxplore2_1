package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.restcontroller.request.LoginRequest;
import skyxplore.service.domain.AccessToken;
import skyxplore.exception.AccessTokenExpiredException;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadRequestAuthException;
import skyxplore.dataaccess.accesstoken.AccessTokenDao;
import skyxplore.filter.AuthFilter;
import skyxplore.service.domain.SkyXpUser;
import skyxplore.util.AccessTokenDateResolver;
import skyxplore.util.IdGenerator;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTokenService {

    private final AccessTokenDateResolver accessTokenDateResolver;
    private final AccessTokenDao accessTokenDao;
    private final UserService userService;
    private final IdGenerator idGenerator;

    public void deleteOutDatedTokens(){
        Calendar expiration = accessTokenDateResolver.getExpirationDate();
        accessTokenDao.deleteExpired(expiration);
    }

    public AccessToken getAccessTokenByUserId(Long userId) {
        return accessTokenDao.findByUserId(userId);
    }

    public boolean isAuthenticated(String userIdValue, String accessTokenId) {
        log.info("Authenticating user {}", userIdValue);
        AccessToken accessToken;
        try {
            Long userId = validate(userIdValue, accessTokenId);

            accessToken = getAccessTokenByUserId(userId);
            if (accessToken == null) {
                throw new BadCredentialsException("No valid accessToken for user " + userIdValue);
            } else if (isTokenValid(accessToken)) {
                throw new AccessTokenExpiredException("Access token expired.");
            } else if (!accessToken.getAccessTokenId().equals(accessTokenId)) {
                throw new BadCredentialsException("Invalid accessToken for user " + userIdValue);
            }
        } catch (BadCredentialsException | BadRequestAuthException | AccessTokenExpiredException e) {
            log.info("Authentication failed: {}", e.getMessage());
            return false;
        }
        log.info("Authentication successful.");
        updateTokenExpiration(accessToken);
        return true;
    }

    private Long validate(String userIdValue, String accessTokenId) {
        if (userIdValue == null) {
            throw new BadRequestAuthException("Required cookies not found:" + AuthFilter.COOKIE_USER_ID);
        }
        if (accessTokenId == null) {
            throw new BadRequestAuthException("Required cookie not found:" + AuthFilter.COOKIE_ACCESS_TOKEN);
        }
        Long userId = convertUserId(userIdValue);
        return userId;
    }

    private Long convertUserId(String userIdValue) {
        try {
            return Long.valueOf(userIdValue);
        } catch (NumberFormatException e) {
            throw new BadRequestAuthException("Invalid userId type.");
        }
    }

    private boolean isTokenValid(AccessToken token) {
        return token.getLastAccess().before(accessTokenDateResolver.getExpirationDate());
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
        token.setLastAccess(accessTokenDateResolver.getActualDate());
        return token;
    }

    private void updateTokenExpiration(AccessToken token) {
        if (token == null) {
            throw new IllegalArgumentException("token must not be null.");
        }
        log.debug("Token expiration date refreshed");
        token.setLastAccess(accessTokenDateResolver.getActualDate());
        accessTokenDao.update(token);
    }

    public void logout(String userIdValue, String accessTokenId) {
        if (userIdValue == null && accessTokenId == null) {
            log.info("User is not logged in.");
        } else if (userIdValue == null) {
            log.info("UserId is null. Deleting by accessTokenId...");
            accessTokenDao.deleteById(accessTokenId);
        } else if (accessTokenId == null) {
            Long userId = convertUserId(userIdValue);
            accessTokenDao.deleteByUserId(userId);
        } else {
            accessTokenDao.deleteById(accessTokenId);
        }
    }
}
