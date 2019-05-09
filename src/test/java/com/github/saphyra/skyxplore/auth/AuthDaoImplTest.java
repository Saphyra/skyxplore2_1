package com.github.saphyra.skyxplore.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.authservice.domain.AccessToken;
import com.github.saphyra.authservice.domain.User;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import com.github.saphyra.skyxplore.user.repository.user.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class AuthDaoImplTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_HASH = "password_hash";

    @Mock
    private AccessTokenConverter accessTokenConverter;

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private CredentialsDao credentialsDao;

    @Mock
    private UserDao userDao;

    @Mock
    private UserConverter userConverter;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private AuthDaoImpl underTest;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<User> optionalUser;

    @Before
    public void init() {
        SkyXpUser skyXpUser = SkyXpUser.builder()
            .userId(USER_ID)
            .build();
        Optional<SkyXpUser> optionalSkyXpUser = Optional.of(skyXpUser);
        when(userDao.findById(USER_ID)).thenReturn(optionalSkyXpUser);

        User user = User.builder().userId(USER_ID).build();
        optionalUser = Optional.of(user);
        when(userConverter.convertEntity(optionalSkyXpUser)).thenReturn(optionalUser);
    }

    @Test
    public void testFindUserById() {
        //WHEN
        Optional<User> result = underTest.findUserById(USER_ID);
        //THEN
        assertThat(result).isEqualTo(optionalUser);
    }

    @Test
    public void testFindUserByUserNameShouldReturnEmptyWhenCredentialsNotFound() {
        //GIVEN
        when(credentialsDao.findByName(USER_NAME)).thenReturn(Optional.empty());
        //WHEN
        Optional<User> result = underTest.findUserByUserName(USER_NAME);
        //THEN
        verify(credentialsDao).findByName(USER_NAME);
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindUserByUserName() {
        //GIVEN
        when(credentialsDao.findByName(USER_NAME)).thenReturn(Optional.of(new SkyXpCredentials(USER_ID, USER_NAME, PASSWORD)));
        //WHEN
        Optional<User> result = underTest.findUserByUserName(USER_NAME);
        //THEN
        assertThat(result).isEqualTo(optionalUser);
    }

    @Test
    public void testDeleteAccessToken() {
        //WHEN
        underTest.deleteAccessToken(AccessToken.builder().accessTokenId(ACCESS_TOKEN_ID).build());
        //THEN
        verify(accessTokenDao).deleteById(ACCESS_TOKEN_ID);
    }

    @Test
    public void testDeleteAccessTokenByUserId() {
        //WHEN
        underTest.deleteAccessTokenByUserId(USER_ID);
        //THEN
        verify(accessTokenDao).deleteByUserId(USER_ID);
    }

    @Test
    public void testDeleteExpiredAccessTokens() {
        //GIVEN
        OffsetDateTime expiration = OffsetDateTime.now(ZoneOffset.UTC);
        //WHEN
        underTest.deleteExpiredAccessTokens(expiration);
        //THEN
        verify(accessTokenDao).deleteExpired(expiration);
    }

    @Test
    public void testFindAccessTokenByTokenId() {
        //GIVEN
        SkyXpAccessToken skyXpAccessToken = SkyXpAccessToken.builder().accessTokenId(ACCESS_TOKEN_ID).build();
        Optional<SkyXpAccessToken> optionalSkyXpAccessToken = Optional.of(skyXpAccessToken);
        when(accessTokenDao.findById(ACCESS_TOKEN_ID)).thenReturn(optionalSkyXpAccessToken);

        AccessToken accessToken = AccessToken.builder().accessTokenId(ACCESS_TOKEN_ID).build();
        when(accessTokenConverter.convertEntity(optionalSkyXpAccessToken)).thenReturn(Optional.of(accessToken));
        //WHEN
        Optional<AccessToken> result = underTest.findAccessTokenByTokenId(ACCESS_TOKEN_ID);
        //THEN
        assertThat(result.isPresent());
        assertThat(result).contains(accessToken);
    }

    @Test
    public void testSaveAccessToken() {
        //GIVEN
        AccessToken accessToken = AccessToken.builder().accessTokenId(ACCESS_TOKEN_ID).build();

        SkyXpAccessToken skyXpAccessToken = SkyXpAccessToken.builder().accessTokenId(ACCESS_TOKEN_ID).build();
        when(accessTokenConverter.convertDomain(accessToken)).thenReturn(skyXpAccessToken);

        //WHEN
        underTest.saveAccessToken(accessToken);
        //THEN
        ArgumentCaptor<SkyXpAccessToken> argumentCaptor = ArgumentCaptor.forClass(SkyXpAccessToken.class);
        verify(accessTokenDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(skyXpAccessToken);
    }

    @Test
    public void authenticate() {
        //GIVEN
        given(passwordService.authenticate(PASSWORD, PASSWORD_HASH)).willReturn(true);
        //WHEN
        Boolean result = underTest.authenticate(PASSWORD, PASSWORD_HASH);
        //THEN
        assertThat(result).isTrue();
    }
}