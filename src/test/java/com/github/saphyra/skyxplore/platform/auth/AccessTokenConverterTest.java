package com.github.saphyra.skyxplore.platform.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.authservice.auth.domain.AccessToken;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenConverterTest {
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final String USER_ID = "user_id";
    private static final OffsetDateTime LAST_ACCESS = OffsetDateTime.now(ZoneOffset.UTC);
    private static final String CHARACTER_ID = "character_id";

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
            .lastAccess(LAST_ACCESS)
            .build();
        //WHEN
        AccessToken result = underTest.convertEntity(skyXpAccessToken);
        //THEN
        assertThat(result.getAccessTokenId()).isEqualTo(ACCESS_TOKEN_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS);
        assertThat(result.isPersistent()).isFalse();
    }

    @Test
    public void testConvertDomain() {
        //GIVEN
        AccessToken accessToken = AccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(LAST_ACCESS)
            .build();

        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        SkyXpAccessToken result = underTest.convertDomain(accessToken);
        //THEN
        verify(accessTokenDao).findByUserId(USER_ID);
        assertThat(result.getAccessTokenId()).isEqualTo(ACCESS_TOKEN_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS);
        assertThat(result.getCharacterId()).isNull();
    }

    @Test
    public void testConvertDomainShouldFillCharacterId() {
        //GIVEN
        AccessToken accessToken = AccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(LAST_ACCESS)
            .build();

        SkyXpAccessToken skyXpAccessToken = SkyXpAccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(LAST_ACCESS)
            .characterId(CHARACTER_ID)
            .build();

        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        SkyXpAccessToken result = underTest.convertDomain(accessToken);
        //THEN
        verify(accessTokenDao).findByUserId(USER_ID);
        assertThat(result.getAccessTokenId()).isEqualTo(ACCESS_TOKEN_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
    }
}