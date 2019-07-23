package com.github.saphyra.skyxplore.platform.auth.repository;

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

import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenDaoTest {
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";
    private static final Long LAST_ACCESS_EPOCH = 5L;
    private static final OffsetDateTime LAST_ACCESS = OffsetDateTime.now(ZoneOffset.UTC);

    @Mock
    private AccessTokenRepository accessTokenRepository;

    @Mock
    private SkyXpAccessTokenConverter skyXpAccessTokenConverter;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private AccessTokenDao underTest;

    @Test
    public void accountDeletedEventListener() {
        //WHEN
        underTest.accountDeletedEventListener(new AccountDeletedEvent(USER_ID));
        //THEN
        verify(accessTokenRepository).deleteByUserId(USER_ID);
    }

    @Test
    public void cleanupCharacterId() {
        //WHEN
        underTest.cleanupCharacterId(CHARACTER_ID);
        //THEN
        verify(accessTokenRepository).cleanUpCharacterId(CHARACTER_ID);
    }

    @Test
    public void testDeleteByIdShouldCallRepository() {
        //WHEN
        underTest.deleteById(ACCESS_TOKEN_ID);
        //THEN
        verify(accessTokenRepository).deleteById(ACCESS_TOKEN_ID);
    }

    @Test
    public void testDeleteByUserIdShouldCallRepository() {
        //WHEN
        underTest.deleteByUserId(USER_ID);
        //THEN
        verify(accessTokenRepository).deleteByUserId(USER_ID);
    }

    @Test
    public void testDeleteExpiredShouldCallRepository() {
        //GIVEN
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        when(dateTimeUtil.convertDomain(now)).thenReturn(0L);
        //WHEN
        underTest.deleteExpired(now);
        //THEN
        verify(accessTokenRepository).deleteExpired(0L);
    }

    @Test
    public void testFindByTokenIdShouldReturnNull() {
        //GIVEN
        when(accessTokenRepository.findById(ACCESS_TOKEN_ID)).thenReturn(Optional.empty());
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findById(ACCESS_TOKEN_ID);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindByCharacterIdShouldReturnDomain() {
        //GIVEN
        Optional<AccessTokenEntity> entity = Optional.of(createAccessTokenEntity());
        when(accessTokenRepository.findByCharacterId(CHARACTER_ID)).thenReturn(entity);

        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        when(skyXpAccessTokenConverter.convertEntity(entity)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        verify(accessTokenRepository).findByCharacterId(CHARACTER_ID);
        verify(skyXpAccessTokenConverter).convertEntity(entity);
        assertThat(result).contains(skyXpAccessToken);
    }

    @Test
    public void testFindByTokenIdShouldReturnDomain() {
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findById(ACCESS_TOKEN_ID)).thenReturn(Optional.of(entity));

        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        when(skyXpAccessTokenConverter.convertEntity(entity)).thenReturn(skyXpAccessToken);
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findById(ACCESS_TOKEN_ID);
        //THEN
        verify(accessTokenRepository).findById(ACCESS_TOKEN_ID);
        verify(skyXpAccessTokenConverter).convertEntity(entity);
        assertThat(result).contains(skyXpAccessToken);
    }

    @Test
    public void testFindByUserIdShouldReturnDomain() {
        //GIVEN
        Optional<AccessTokenEntity> entity = Optional.of(createAccessTokenEntity());
        when(accessTokenRepository.findByUserId(USER_ID)).thenReturn(entity);

        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        when(skyXpAccessTokenConverter.convertEntity(entity)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findByUserId(USER_ID);
        //THEN
        verify(accessTokenRepository).findByUserId(USER_ID);
        verify(skyXpAccessTokenConverter).convertEntity(entity);
        assertThat(result).contains(skyXpAccessToken);
    }

    @Test
    public void testSaveShouldCallRepository() {
        //GIVEN
        SkyXpAccessToken token = createAccessToken();

        AccessTokenEntity entity = createAccessTokenEntity();
        when(skyXpAccessTokenConverter.convertDomain(token)).thenReturn(entity);
        //WHEN
        underTest.save(token);
        //THEN
        verify(accessTokenRepository).save(entity);
    }

    private AccessTokenEntity createAccessTokenEntity() {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity.setAccessTokenId(ACCESS_TOKEN_ID);
        accessTokenEntity.setCharacterId(CHARACTER_ID);
        accessTokenEntity.setUserId(USER_ID);
        accessTokenEntity.setLastAccess(LAST_ACCESS_EPOCH);
        return accessTokenEntity;
    }

    private SkyXpAccessToken createAccessToken() {
        return SkyXpAccessToken.builder()
            .userId(USER_ID)
            .accessTokenId(ACCESS_TOKEN_ID)
            .characterId(CHARACTER_ID)
            .lastAccess(LAST_ACCESS)
            .characterId(CHARACTER_ID)
            .build();
    }
}
