package skyxplore.service;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.exception.AccessTokenExpiredException;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadRequestAuthException;
import skyxplore.exception.UserNotFoundException;
import skyxplore.filter.AuthFilter;
import skyxplore.controller.request.LoginRequest;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.util.AccessTokenDateResolver;
import skyxplore.util.IdGenerator;

import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTokenFacade {

    private final AccessTokenDateResolver accessTokenDateResolver;
    private final AccessTokenDao accessTokenDao;
    private final UserFacade userFacade;
    private final IdGenerator idGenerator;
    private final Cache<String, Optional<AccessToken>> accessTokenCache;

    public void deleteOutDatedTokens(){
        Calendar expiration = accessTokenDateResolver.getExpirationDate();
        accessTokenDao.deleteExpired(expiration);
    }

    public boolean isAuthenticated(String userIdValue, String accessTokenId) {
        log.debug("Authenticating user {}", userIdValue);
        AccessToken accessToken;
        try {
            String userId = validate(userIdValue, accessTokenId);

            Optional<AccessToken> accessTokenOpt = accessTokenCache.get(userId);
            if (!accessTokenOpt.isPresent()) {
                throw new BadCredentialsException("No valid accessToken for user " + userIdValue);
            }

            accessToken = accessTokenOpt.get();
            if (isTokenValid(accessToken)) {
                throw new AccessTokenExpiredException("Access token expired.");
            } else if (!accessToken.getAccessTokenId().equals(accessTokenId)) {
                throw new BadCredentialsException("Invalid accessToken for user " + userIdValue);
            }

            //Check if user exists
            userFacade.getUserById(userId);
        } catch (UserNotFoundException | BadCredentialsException | BadRequestAuthException | AccessTokenExpiredException e) {
            log.info("Authentication failed: {}", e.getMessage());
            return false;
        } catch (ExecutionException e){
            throw new RuntimeException(e);
        }
        log.debug("Authentication successful.");
        updateTokenExpiration(accessToken);
        return true;
    }

    private String validate(String userId, String accessTokenId) {
        if (userId == null) {
            throw new BadRequestAuthException("Required cookies not found:" + AuthFilter.COOKIE_USER_ID);
        }
        if (accessTokenId == null) {
            throw new BadRequestAuthException("Required cookie not found:" + AuthFilter.COOKIE_ACCESS_TOKEN);
        }
        return userId;
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
        SkyXpUser user = userFacade.getUserByName(loginRequest.getUserName());
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

    public void logout(String userId, String accessTokenId) {
        if (userId == null && accessTokenId == null) {
            log.info("User is not logged in.");
        } else if (userId == null) {
            log.info("UserId is null. Deleting by accessTokenId...");
            accessTokenDao.deleteById(accessTokenId);
        } else if (accessTokenId == null) {
            accessTokenDao.deleteByUserId(userId);
        } else {
            accessTokenDao.deleteById(accessTokenId);
        }
    }
}
