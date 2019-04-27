package skyxplore.auth;

import com.github.saphyra.authservice.domain.AccessToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.SkyXpAccessToken;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_ID;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_LAST_ACCESS;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenConverterTest {
    @Mock
    private AccessTokenDao accessTokenDao;

    @InjectMocks
    private AccessTokenConverter underTest;

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        SkyXpAccessToken skyXpAccessToken = SkyXpAccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(ACCESS_TOKEN_LAST_ACCESS)
            .build();
        //WHEN
        AccessToken result = underTest.convertEntity(skyXpAccessToken);
        //THEN
        assertEquals(ACCESS_TOKEN_ID, result.getAccessTokenId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS, result.getLastAccess());
        assertFalse(result.isPersistent());
    }

    @Test
    public void testConvertDomain() {
        //GIVEN
        AccessToken accessToken = AccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(ACCESS_TOKEN_LAST_ACCESS)
            .build();

        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        SkyXpAccessToken result = underTest.convertDomain(accessToken);
        //THEN
        verify(accessTokenDao).findByUserId(USER_ID);
        assertEquals(ACCESS_TOKEN_ID, result.getAccessTokenId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS, result.getLastAccess());
        assertNull(result.getCharacterId());
    }

    @Test
    public void testConvertDomainShouldFillCharacterId() {
        //GIVEN
        AccessToken accessToken = AccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(ACCESS_TOKEN_LAST_ACCESS)
            .build();

        SkyXpAccessToken skyXpAccessToken = SkyXpAccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(ACCESS_TOKEN_LAST_ACCESS)
            .characterId(CHARACTER_ID_1)
            .build();

        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        SkyXpAccessToken result = underTest.convertDomain(accessToken);
        //THEN
        verify(accessTokenDao).findByUserId(USER_ID);
        assertEquals(ACCESS_TOKEN_ID, result.getAccessTokenId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS, result.getLastAccess());
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
    }
}