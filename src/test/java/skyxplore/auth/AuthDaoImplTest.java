package skyxplore.auth;

import com.github.saphyra.authservice.domain.AccessToken;
import com.github.saphyra.authservice.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.accesstoken.SkyXpAccessToken;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.domain.user.SkyXpUser;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_ID;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;
import static skyxplore.testutil.TestUtils.createUser;

@RunWith(MockitoJUnitRunner.class)
public class AuthDaoImplTest {
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

    @InjectMocks
    private AuthDaoImpl underTest;

    private Optional<User> optionalUser;

    @Before
    public void init() {
        SkyXpUser skyXpUser = createUser();
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
        assertEquals(optionalUser, result);
    }

    @Test
    public void testFindUserByUserNameShouldReturnEmptyWhenCredentialsNotFound() {
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(Optional.empty());
        //WHEN
        Optional<User> result = underTest.findUserByUserName(USER_NAME);
        //THEN
        verify(credentialsDao).getCredentialsByName(USER_NAME);
        assertFalse(result.isPresent());
    }

    @Test
    public void testFIndUserByUserName() {
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(Optional.of(new SkyXpCredentials(USER_ID, USER_NAME, USER_PASSWORD)));
        //WHEN
        Optional<User> result = underTest.findUserByUserName(USER_NAME);
        //THEN
        assertEquals(optionalUser, result);
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
        assertTrue(result.isPresent());
        assertEquals(accessToken, result.get());
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
        assertEquals(skyXpAccessToken, argumentCaptor.getValue());
    }
}