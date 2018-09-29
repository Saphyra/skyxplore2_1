package skyxplore.service.accesstoken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.AccessTokenCache;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.exception.UserNotFoundException;
import skyxplore.service.UserFacade;
import skyxplore.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private AccessTokenCache accessTokenCache;

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private AuthenticationService underTest;

    private AccessToken accessToken;

    @Before
    public void setUp() throws ExecutionException {
        accessToken = createAccessToken();
        when(accessTokenCache.get(USER_ID)).thenReturn(Optional.of(accessToken));
        when(dateTimeUtil.getExpirationDate()).thenReturn(LocalDateTime.ofEpochSecond(ACCESS_TOKEN_LAST_ACCESS_EPOCH - 1, 0, ZoneOffset.UTC));
    }

    @Test
    public void testIsAuthenticatedShouldReturnFalseWhenUserIdIsNull() {
        assertFalse(underTest.isAuthenticated(null, ACCESS_TOKEN_ID));
    }

    @Test
    public void testIsAuthenticatedShouldReturnFalseWhenAccessTokenIdIsNull() {
        assertFalse(underTest.isAuthenticated(USER_ID, null));
    }

    @Test
    public void testIsAuthenticatedShouldReturnFalseWhenAccessTokenIsNotFound() throws ExecutionException {
        //GIVEN
        when(accessTokenCache.get(USER_ID)).thenReturn(Optional.empty());
        //THEN
        assertFalse(underTest.isAuthenticated(USER_ID, ACCESS_TOKEN_ID));
        verify(accessTokenCache).get(USER_ID);
    }

    @Test
    public void testIsAuthenticatedShouldReturnFalseWhenTokenIsExpired() throws ExecutionException {
        //GIVEN
        when(dateTimeUtil.getExpirationDate()).thenReturn(LocalDateTime.ofEpochSecond(ACCESS_TOKEN_LAST_ACCESS_EPOCH + 1, 0, ZoneOffset.UTC));
        //WHEN
        assertFalse(underTest.isAuthenticated(USER_ID, ACCESS_TOKEN_ID));
        verify(accessTokenCache).get(USER_ID);
        verify(dateTimeUtil).getExpirationDate();
    }

    @Test
    public void testIsAuthenticatedShouldReturnFalseWhenWrongAccessTokenId() throws ExecutionException {
        assertFalse(underTest.isAuthenticated(USER_ID, ACCESS_TOKEN_FAKE_ID));
        verify(accessTokenCache).get(USER_ID);
        verify(dateTimeUtil).getExpirationDate();
    }

    @Test
    public void testIsAuthenticatedShouldReturnFalseWhenUserNotExists() throws ExecutionException {
        //GIVEN
        when(userFacade.getUserById(USER_ID)).thenThrow(new UserNotFoundException("asd"));
        //WHEN
        assertFalse(underTest.isAuthenticated(USER_ID, ACCESS_TOKEN_ID));
        verify(accessTokenCache).get(USER_ID);
        verify(dateTimeUtil).getExpirationDate();
        verify(userFacade).getUserById(USER_ID);
    }

    @Test
    public void testIsAuthenticatedShouldUpdateTokenAndReturnTrue() throws ExecutionException {
        //GIVEN
        when(dateTimeUtil.now()).thenReturn(ACCESS_TOKEN_EXPIRATION);
        //WHEN
        assertTrue(underTest.isAuthenticated(USER_ID, ACCESS_TOKEN_ID));
        verify(accessTokenCache).get(USER_ID);
        verify(dateTimeUtil).getExpirationDate();
        verify(userFacade).getUserById(USER_ID);
        verify(dateTimeUtil).now();
        verify(accessTokenDao).save(accessToken);
        assertEquals(ACCESS_TOKEN_EXPIRATION, accessToken.getLastAccess());
    }

    @Test
    public void testIsCharacterActiveShouldReturnTrueWhenActive(){
        //GIVEN
        when(accessTokenDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(createAccessToken());
        //WHEN
        assertTrue(underTest.isCharacterActive(CHARACTER_ID_1));
        verify(accessTokenDao).findByCharacterId(CHARACTER_ID_1);
    }

    @Test
    public void testIsCharacterActiveShouldReturnFalseWhenNotActive(){
        //GIVEN
        when(accessTokenDao.findByCharacterId(CHARACTER_ID_1)).thenReturn(null);
        //WHEN
        assertFalse(underTest.isCharacterActive(CHARACTER_ID_1));
        verify(accessTokenDao).findByCharacterId(CHARACTER_ID_1);
    }
}