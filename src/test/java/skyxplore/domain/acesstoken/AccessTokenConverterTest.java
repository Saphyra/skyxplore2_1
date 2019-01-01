package skyxplore.domain.acesstoken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.accesstoken.AccessTokenConverter;
import skyxplore.domain.accesstoken.AccessTokenEntity;
import skyxplore.util.DateTimeUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_ID;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_LAST_ACCESS;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_LAST_ACCESS_EPOCH;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createAccessToken;
import static skyxplore.testutil.TestUtils.createAccessTokenEntity;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenConverterTest {
    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private AccessTokenConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        AccessTokenEntity entity = null;
        //WHEN
        AccessToken result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(dateTimeUtil.convertEntity(ACCESS_TOKEN_LAST_ACCESS_EPOCH)).thenReturn(ACCESS_TOKEN_LAST_ACCESS);
        //WHEN
        AccessToken result = underTest.convertEntity(entity);
        //THEN
        verify(dateTimeUtil).convertEntity(ACCESS_TOKEN_LAST_ACCESS_EPOCH);
        assertEquals(ACCESS_TOKEN_ID, result.getAccessTokenId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS, result.getLastAccess());
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        AccessToken accessToken = createAccessToken();
        when(dateTimeUtil.convertDomain(ACCESS_TOKEN_LAST_ACCESS)).thenReturn(ACCESS_TOKEN_LAST_ACCESS_EPOCH);
        //WHEN
        AccessTokenEntity result = underTest.convertDomain(accessToken);
        //THEN
        verify(dateTimeUtil).convertDomain(ACCESS_TOKEN_LAST_ACCESS);
        assertEquals(ACCESS_TOKEN_ID, result.getAccessTokenId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS_EPOCH, result.getLastAccess());
    }
}
