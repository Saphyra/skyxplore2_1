package org.github.saphyra.skyxplore.auth.repository;

import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpSkyXpAccessTokenConverterTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final String USER_ID = "user_id";
    private static final Long LAST_ACCESS_EPOCH = 5L;
    private static final OffsetDateTime LAST_ACCESS = OffsetDateTime.now(ZoneOffset.UTC);
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
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity.setAccessTokenId(ACCESS_TOKEN_ID);
        accessTokenEntity.setCharacterId(CHARACTER_ID);
        accessTokenEntity.setUserId(USER_ID);
        accessTokenEntity.setLastAccess(LAST_ACCESS_EPOCH);
        when(dateTimeUtil.convertEntity(LAST_ACCESS_EPOCH)).thenReturn(LAST_ACCESS);
        //WHEN
        SkyXpAccessToken result = underTest.convertEntity(accessTokenEntity);
        //THEN
        verify(dateTimeUtil).convertEntity(LAST_ACCESS_EPOCH);
        assertThat(result.getAccessTokenId()).isEqualTo(ACCESS_TOKEN_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        SkyXpAccessToken accessToken = new SkyXpAccessToken();
        accessToken.setAccessTokenId(ACCESS_TOKEN_ID);
        accessToken.setUserId(USER_ID);
        accessToken.setLastAccess(LAST_ACCESS);
        accessToken.setCharacterId(CHARACTER_ID);
        when(dateTimeUtil.convertDomain(LAST_ACCESS)).thenReturn(LAST_ACCESS_EPOCH);
        //WHEN
        AccessTokenEntity result = underTest.convertDomain(accessToken);
        //THEN
        verify(dateTimeUtil).convertDomain(LAST_ACCESS);
        assertThat(result.getAccessTokenId()).isEqualTo(ACCESS_TOKEN_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS_EPOCH);
    }
}
