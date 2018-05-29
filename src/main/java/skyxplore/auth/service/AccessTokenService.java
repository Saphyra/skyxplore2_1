package skyxplore.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.auth.controller.request.LoginRequest;
import skyxplore.auth.domain.AccessToken;
import skyxplore.auth.domain.exception.BadCredentialsException;
import skyxplore.dataaccess.auth.AccessTokenDao;
import skyxplore.home.domain.SkyXpUser;
import skyxplore.home.service.UserService;
import skyxplore.util.IdGenerator;

import java.util.Calendar;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTokenService {

    private final AccessTokenDao accessTokenDao;
    private final UserService userService;
    private final IdGenerator idGenerator;

    public AccessToken getAccessTokenByUserId(Long tokenId){
        return accessTokenDao.findByUserId(tokenId);
    }

    public AccessToken login(LoginRequest loginRequest){
        SkyXpUser user = getAuthenticatedUser(loginRequest);
        log.info("{} authentication successful.", user.getUserId());
        AccessToken accessToken = accessTokenDao.findByUserId(user.getUserId());
        if(accessToken != null){
            log.info("Access token already exists. Deleting...");
            accessTokenDao.delete(accessToken);
        }
        accessToken = createAccessToken(user);
        accessTokenDao.save(accessToken);
        return accessToken;
    }

    private SkyXpUser getAuthenticatedUser(LoginRequest loginRequest){
        SkyXpUser user = userService.getUserByName(loginRequest.getUserName());
        if(user == null){
            throw new BadCredentialsException("User cannot be found. Username: " + loginRequest.getUserName());
        }

        if(!user.getPassword().equals(loginRequest.getPassword())){
            throw new BadCredentialsException("Password is incorrect.");
        }
        return user;
    }

    private AccessToken createAccessToken(SkyXpUser user){
        AccessToken token = new AccessToken();
        token.setAccessTokenId(idGenerator.getRandomId());
        token.setUserId(user.getUserId());
        token.setLastAccess(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        return token;
    }

}
