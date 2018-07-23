package skyxplore.service.accesstoken;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.exception.AccessTokenExpiredException;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadRequestAuthException;
import skyxplore.exception.UserNotFoundException;
import skyxplore.filter.AuthFilter;
import skyxplore.service.UserFacade;
import skyxplore.util.AccessTokenDateResolver;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final Cache<String, Optional<AccessToken>> accessTokenCache;
    private final AccessTokenDao accessTokenDao;
    private final AccessTokenDateResolver accessTokenDateResolver;

    private final UserFacade userFacade;

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
        } catch (ExecutionException e) {
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

    private void updateTokenExpiration(AccessToken token) {
        if (token == null) {
            throw new IllegalArgumentException("token must not be null.");
        }
        log.debug("Token expiration date refreshed");
        token.setLastAccess(accessTokenDateResolver.getActualDate());
        accessTokenDao.update(token);
    }
}
