package com.github.saphyra.skyxplore.auth;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.AuthDao;
import com.github.saphyra.authservice.domain.AccessToken;
import com.github.saphyra.authservice.domain.User;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import com.github.saphyra.skyxplore.user.repository.user.UserDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthDaoImpl implements AuthDao {
    private final AccessTokenConverter accessTokenConverter;
    private final AccessTokenDao accessTokenDao;
    private final CredentialsDao credentialsDao;
    private final UserDao userDao;
    private final UserConverter userConverter;
    private final PasswordService passwordService;

    @Override
    public Optional<User> findUserById(String userId) {
        return userConverter.convertEntity(userDao.findById(userId));
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return credentialsDao.findByName(userName)
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

    @Override
    public boolean authenticate(String password, String hash) {
        return passwordService.authenticate(password, hash);
    }
}
