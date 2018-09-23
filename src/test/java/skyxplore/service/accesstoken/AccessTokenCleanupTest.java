package skyxplore.service.accesstoken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.util.DateTimeUtil;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_EXPIRATION;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_EXPIRATION_EPOCH;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenCleanupTest {
    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private AccessTokenCleanup underTesr;

    @Test
    public void testShouldCallDao() {
        //GIVEN
        when(dateTimeUtil.getExpirationDate()).thenReturn(ACCESS_TOKEN_EXPIRATION);
        when(dateTimeUtil.convertDomain(ACCESS_TOKEN_EXPIRATION)).thenReturn(ACCESS_TOKEN_EXPIRATION_EPOCH);
        //WHEN
        underTesr.deleteOutDatedTokens();
        //THEN
        verify(dateTimeUtil).getExpirationDate();
        verify(accessTokenDao).deleteExpired(ACCESS_TOKEN_EXPIRATION_EPOCH);
    }
}