package skyxplore.auth;

import com.github.saphyra.authservice.AuthDao;
import com.github.saphyra.authservice.domain.AccessToken;
import com.github.saphyra.authservice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.dataaccess.db.UserDao;

import java.time.OffsetDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
//TODO unit test
public class AuthDaoImpl implements AuthDao {
    private final AccessTokenConverter accessTokenConverter;
    private final AccessTokenDao accessTokenDao;
    private final CredentialsDao credentialsDao;
    private final UserDao userDao;
    private final UserConverter userConverter;

    @Override
    public Optional<User> findUserById(String userId) {
        return userConverter.convertEntity(userDao.findById(userId));
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return credentialsDao.getCredentialsByName(userName)
            .flatMap(skyXpCredentials -> userConverter.convertEntity(userDao.findById(skyXpCredentials.getUserId())));
    }

    @Override
    public void deleteAccessToken(AccessToken accessToken) {
        accessTokenDao.deleteById(accessToken.getAccessTokenId());
    }

    @Override
    public void deleteAccessTokenByUserId(String userId) {
        accessTokenDao.deleteByUserId(userId);
    }

    @Override
    public void deleteExpiredAccessTokens(OffsetDateTime expiration) {
        accessTokenDao.deleteExpired(expiration);
    }

    @Override
    public Optional<AccessToken> findAccessTokenByTokenId(String accessTokenId) {
        return accessTokenConverter.convertEntity(accessTokenDao.findById(accessTokenId));
    }

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        accessTokenDao.save(accessTokenConverter.convertDomain(accessToken));
    }
}
