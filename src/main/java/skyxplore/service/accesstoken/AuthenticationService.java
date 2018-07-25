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
import skyxplore.exception.base.ServerErrorException;
import skyxplore.filter.AuthFilter;
import skyxplore.service.UserFacade;
import skyxplore.util.AccessTokenDateResolver;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AccessTokenDao accessTokenDao;
    private final AccessTokenDateResolver accessTokenDateResolver;
    private final Cache<String, Optional<AccessToken>> accessTokenCache;
    private final UserFacade userFacade;

    public boolean isAuthenticated(String userId, String accessTokenId) {
        log.debug("Authenticating user {}", userId);
        AccessToken accessToken;
        try {
            validateArguments(userId, accessTokenId);
            accessToken = getAccessToken(userId);
            validateToken(accessToken, accessTokenId);

            //Check if user exists. (Tokens are not deleted upon user deletion)
            userFacade.getUserById(userId);
            log.debug("Authentication successful.");
            updateTokenExpiration(accessToken);
        } catch (UserNotFoundException | BadCredentialsException | BadRequestAuthException | AccessTokenExpiredException e) {
            log.info("Authentication failed for user {}: {}", userId, e.getMessage());
            return false;
        }

        return true;
    }

    private AccessToken getAccessToken(String userId) {
        AccessToken result;
        try {
            Optional<AccessToken> accessTokenOpt = accessTokenCache.get(userId);
            if (!accessTokenOpt.isPresent()) {
                throw new BadCredentialsException("No valid accessToken for user " + userId);
            }
            result = accessTokenOpt.get();
        } catch (ExecutionException e) {
            throw new ServerErrorException("Exception occured by resolving access token: " + e.getMessage());
        }
        return result;
    }

    private void validateArguments(String userId, String accessTokenId) {
        if (userId == null) {
            throw new BadRequestAuthException("Required cookies not found:" + AuthFilter.COOKIE_USER_ID);
        }
        if (accessTokenId == null) {
            throw new BadRequestAuthException("Required cookie not found:" + AuthFilter.COOKIE_ACCESS_TOKEN);
        }
    }

    private void validateToken(AccessToken accessToken, String tokenId){
        if (accessToken == null) {
            throw new BadRequestAuthException("Acces token not found.");
        }
        if (isTokenExpired(accessToken)) {
            throw new AccessTokenExpiredException("Access token expired.");
        }
        if (!accessToken.getAccessTokenId().equals(tokenId)) {
            throw new BadCredentialsException("Invalid accessToken");
        }
    }

    private boolean isTokenExpired(AccessToken token) {
        return token.getLastAccess().before(accessTokenDateResolver.getExpirationDate());
    }

    private void updateTokenExpiration(AccessToken token) {
        log.debug("Token expiration date refreshed");
        token.setLastAccess(accessTokenDateResolver.getActualDate());
        accessTokenDao.update(token);
    }
}
