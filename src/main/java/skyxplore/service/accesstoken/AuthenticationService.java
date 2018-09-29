package skyxplore.service.accesstoken;

import com.google.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.exception.AccessTokenExpiredException;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadRequestAuthException;
import skyxplore.exception.UserNotFoundException;
import skyxplore.service.UserFacade;
import skyxplore.util.DateTimeUtil;

import java.util.Optional;

import static skyxplore.filter.FilterHelper.COOKIE_ACCESS_TOKEN;
import static skyxplore.filter.FilterHelper.COOKIE_USER_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AccessTokenDao accessTokenDao;
    private final DateTimeUtil dateTimeUtil;
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
        Optional<AccessToken> accessTokenOpt = accessTokenCache.getIfPresent(userId);
        if (!accessTokenOpt.isPresent()) {
            throw new BadCredentialsException("No valid accessToken for user " + userId);
        }
        result = accessTokenOpt.get();
        return result;
    }

    private void validateArguments(String userId, String accessTokenId) {
        if (userId == null) {
            throw new BadRequestAuthException("Required cookies not found:" + COOKIE_USER_ID);
        }
        if (accessTokenId == null) {
            throw new BadRequestAuthException("Required cookie not found:" + COOKIE_ACCESS_TOKEN);
        }
    }

    private void validateToken(AccessToken accessToken, String tokenId) {
        if (isTokenExpired(accessToken)) {
            throw new AccessTokenExpiredException("Access token expired.");
        }
        if (!accessToken.getAccessTokenId().equals(tokenId)) {
            throw new BadCredentialsException("Invalid accessToken");
        }
    }

    private boolean isTokenExpired(AccessToken token) {
        return token.getLastAccess().isBefore(dateTimeUtil.getExpirationDate());
    }

    private void updateTokenExpiration(AccessToken token) {
        log.debug("Token expiration date refreshed");
        token.setLastAccess(dateTimeUtil.now());
        accessTokenDao.save(token);
    }

    public Boolean isCharacterActive(String characterId) {
        return accessTokenDao.findByCharacterId(characterId) != null;
    }
}
