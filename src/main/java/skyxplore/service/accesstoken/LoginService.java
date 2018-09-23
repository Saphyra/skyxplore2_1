package skyxplore.service.accesstoken;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.LoginRequest;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.credentials.Credentials;
import skyxplore.encryption.base.PasswordService;
import skyxplore.exception.BadCredentialsException;
import skyxplore.service.credentials.CredentialsService;
import skyxplore.util.DateTimeUtil;
import skyxplore.util.IdGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
//TODO unit test
public class LoginService {
    private final AccessTokenDao accessTokenDao;
    private final PasswordService passwordService;
    private final CredentialsService credentialsService;
    private final DateTimeUtil accessTokenDateResolver;
    private final IdGenerator idGenerator;

    public AccessToken login(LoginRequest loginRequest) {
        Credentials credentials = getCredentials(loginRequest);
        log.info("{} authentication successful.", credentials.getUserId());
        AccessToken accessToken = accessTokenDao.findByUserId(credentials.getUserId());
        if (accessToken != null) {
            log.info("Access token already exists. Deleting...");
            accessTokenDao.delete(accessToken);
        }
        accessToken = createAccessToken(credentials);
        accessTokenDao.save(accessToken);
        return accessToken;
    }

    private Credentials getCredentials(LoginRequest loginRequest) {
        Credentials credentials = credentialsService.getCredentialsByName(loginRequest.getUserName());

        if (!passwordService.authenticate(loginRequest.getPassword(), credentials.getPassword())) {
            throw new BadCredentialsException("Password is incorrect.");
        }
        return credentials;
    }

    private AccessToken createAccessToken(Credentials user) {
        AccessToken token = new AccessToken();
        token.setAccessTokenId(idGenerator.getRandomId());
        token.setUserId(user.getUserId());
        token.setLastAccess(accessTokenDateResolver.now());
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
