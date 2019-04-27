package org.github.saphyra.skyxplore.auth.domain.accesstoken;

import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
public class SkyXpSkyXpAccessTokenConverterTest {
    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private SkyXpAccessTokenConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        AccessTokenEntity entity = null;
        //WHEN
        SkyXpAccessToken result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(dateTimeUtil.convertEntity(ACCESS_TOKEN_LAST_ACCESS_EPOCH)).thenReturn(ACCESS_TOKEN_LAST_ACCESS);
        //WHEN
        SkyXpAccessToken result = underTest.convertEntity(entity);
        //THEN
        verify(dateTimeUtil).convertEntity(ACCESS_TOKEN_LAST_ACCESS_EPOCH);
        assertEquals(ACCESS_TOKEN_ID, result.getAccessTokenId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS, result.getLastAccess());
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        when(dateTimeUtil.convertDomain(ACCESS_TOKEN_LAST_ACCESS)).thenReturn(ACCESS_TOKEN_LAST_ACCESS_EPOCH);
        //WHEN
        AccessTokenEntity result = underTest.convertDomain(skyXpAccessToken);
        //THEN
        verify(dateTimeUtil).convertDomain(ACCESS_TOKEN_LAST_ACCESS);
        assertEquals(ACCESS_TOKEN_ID, result.getAccessTokenId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(ACCESS_TOKEN_LAST_ACCESS_EPOCH, result.getLastAccess());
    }
}
