package skyxplore.service.accesstoken;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.LoginRequest;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.service.UserFacade;
import skyxplore.util.DateTimeUtil;
import skyxplore.util.IdGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final DateTimeUtil accessTokenDateResolver;
    private final AccessTokenDao accessTokenDao;
    private final IdGenerator idGenerator;
    private final UserFacade userFacade;

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

    public void logout(String userId, String accessTokenId) {
        log.info("Logging out user {}", userId);
        AccessToken token = accessTokenDao.findByUserIdOrTokenId(userId, accessTokenId);
        if(token != null){
            log.info("Deleting token...");
            accessTokenDao.delete(token);
        }else{
            log.info("Token not found for user ", userId);
        }
    }
}
