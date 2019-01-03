package skyxplore.service.accesstoken;

import com.github.saphyra.encryption.impl.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.AccessTokenCache;
import skyxplore.controller.request.user.LoginRequest;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.credentials.Credentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.service.credentials.CredentialsService;
import skyxplore.util.DateTimeUtil;
import skyxplore.util.IdGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_FAKE_ID;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_ID;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_LAST_ACCESS;
import static skyxplore.testutil.TestUtils.CREDENTIALS_HASHED_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;
import static skyxplore.testutil.TestUtils.createAccessToken;
import static skyxplore.testutil.TestUtils.createCredentials;
import static skyxplore.testutil.TestUtils.createLoginRequest;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    @Mock
    private AccessTokenCache accessTokenCache;

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private PasswordService passwordService;

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private LoginService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testLoginShouldThrowExceptionWhenBadPassword(){
        //GIVEN
        Credentials credentials = createCredentials();
        credentials.setPassword(USER_FAKE_PASSWORD);
        when(credentialsService.getCredentialsByName(USER_NAME)).thenReturn(credentials);

        LoginRequest request = createLoginRequest();

        when(passwordService.authenticate(USER_PASSWORD, USER_FAKE_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.login(request);
    }

    @Test
    public void testLoginShouldDeleteAccessTokenIfExistsAndCreateNewOne(){
        //GIVEN
        LoginRequest request = createLoginRequest();

        Credentials credentials = createCredentials();
        when(credentialsService.getCredentialsByName(USER_NAME)).thenReturn(credentials);

        when(passwordService.authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(true);

        AccessToken accessToken = createAccessToken();
        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(accessToken);

        when(idGenerator.getRandomId()).thenReturn(ACCESS_TOKEN_FAKE_ID);
        when(dateTimeUtil.now()).thenReturn(ACCESS_TOKEN_LAST_ACCESS);
        //WHEN
        underTest.login(request);
        //THEN
        verify(credentialsService).getCredentialsByName(USER_NAME);
        verify(passwordService).authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD);
        verify(accessTokenDao).findByUserId(USER_ID);
        verify(accessTokenDao).delete(accessToken);
        verify(idGenerator).getRandomId();
        verify(dateTimeUtil).now();

        ArgumentCaptor<AccessToken> argumentCaptor = ArgumentCaptor.forClass(AccessToken.class);
        verify(accessTokenDao).save(argumentCaptor.capture());
        assertEquals(ACCESS_TOKEN_FAKE_ID, argumentCaptor.getValue().getAccessTokenId());
        assertEquals(USER_ID, argumentCaptor.getValue().getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS, argumentCaptor.getValue().getLastAccess());
        assertNull(argumentCaptor.getValue().getCharacterId());
    }

    @Test
    public void testLogoutShouldDeleteTokenIfExists(){
        //GIVEN
        AccessToken accessToken = createAccessToken();
        when(accessTokenDao.findByUserIdOrTokenId(USER_ID, ACCESS_TOKEN_ID)).thenReturn(accessToken);
        //WHEN
        underTest.logout(USER_ID, ACCESS_TOKEN_ID);
        //THEN
        verify(accessTokenCache).invalidate(USER_ID);
        verify(accessTokenDao).findByUserIdOrTokenId(USER_ID, ACCESS_TOKEN_ID);
        verify(accessTokenDao).delete(accessToken);
    }
}