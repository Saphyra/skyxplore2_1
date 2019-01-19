package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.AccessTokenRepository;
import skyxplore.domain.accesstoken.SkyXpAccessToken;
import skyxplore.domain.accesstoken.SkyXpAccessTokenConverter;
import skyxplore.domain.accesstoken.AccessTokenEntity;
import skyxplore.util.DateTimeUtil;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.ACCESS_TOKEN_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createAccessToken;
import static skyxplore.testutil.TestUtils.createAccessTokenEntity;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpAccessTokenDaoTest {
    @Mock
    private AccessTokenRepository accessTokenRepository;

    @Mock
    private SkyXpAccessTokenConverter skyXpAccessTokenConverter;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private AccessTokenDao underTest;

    @Test
    public void testDeleteByIdShouldCallRepository(){
        //WHEN
        underTest.deleteById(ACCESS_TOKEN_ID);
        //THEN
        verify(accessTokenRepository).deleteById(ACCESS_TOKEN_ID);
    }

    @Test
    public void testDeleteByUserIdShouldCallRepository(){
        //WHEN
        underTest.deleteByUserId(USER_ID);
        //THEN
        verify(accessTokenRepository).deleteByUserId(USER_ID);
    }

    @Test
    public void testDeleteExpiredShouldCallRepository(){
        //GIVEN
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        when(dateTimeUtil.convertDomain(now)).thenReturn(0L);
        //WHEN
        underTest.deleteExpired(now);
        //THEN
        verify(accessTokenRepository).deleteExpired(0L);
    }

    @Test
    public void testFindByTokenIdShouldReturnNull(){
        //GIVEN
        when(accessTokenRepository.findById(ACCESS_TOKEN_ID)).thenReturn(Optional.empty());
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findById(ACCESS_TOKEN_ID);
        //THEN
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByCharacterIdShouldReturnDomain(){
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findByCharacterId(CHARACTER_ID_1)).thenReturn(entity);

        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        when(skyXpAccessTokenConverter.convertEntityToOptional(entity)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findByCharacterId(CHARACTER_ID_1);
        //THEN
        verify(accessTokenRepository).findByCharacterId(CHARACTER_ID_1);
        verify(skyXpAccessTokenConverter).convertEntityToOptional(entity);
        assertTrue(result.isPresent());
        assertEquals(skyXpAccessToken, result.get());
    }

    @Test
    public void testFindByTokenIdShouldReturnDomain(){
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
        assertTrue(result.isPresent());
        assertEquals(skyXpAccessToken, result.get());
    }

    @Test
    public void testFindByUserIdShouldReturnDomain(){
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findByUserId(USER_ID)).thenReturn(entity);

        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        when(skyXpAccessTokenConverter.convertEntityToOptional(entity)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findByUserId(USER_ID);
        //THEN
        verify(accessTokenRepository).findByUserId(USER_ID);
        verify(skyXpAccessTokenConverter).convertEntityToOptional(entity);
        assertTrue(result.isPresent());
        assertEquals(skyXpAccessToken, result.get());
    }

    @Test
    public void testFindByUserIdOrTokenIdShouldReturnDomain(){
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findByUserIdOrAccessTokenId(USER_ID, ACCESS_TOKEN_ID)).thenReturn(entity);

        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        when(skyXpAccessTokenConverter.convertEntityToOptional(entity)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        Optional<SkyXpAccessToken> result = underTest.findByUserIdOrTokenId(USER_ID, ACCESS_TOKEN_ID);
        //THEN
        verify(accessTokenRepository).findByUserIdOrAccessTokenId(USER_ID, ACCESS_TOKEN_ID);
        verify(skyXpAccessTokenConverter).convertEntityToOptional(entity);
        assertTrue(result.isPresent());
        assertEquals(skyXpAccessToken, result.get());
    }

    @Test
    public void testSaveShouldCallRepository(){
        //GIVEN
        SkyXpAccessToken token = createAccessToken();

        AccessTokenEntity entity = createAccessTokenEntity();
        when(skyXpAccessTokenConverter.convertDomain(token)).thenReturn(entity);
        //WHEN
        underTest.save(token);
        //THEN
        verify(accessTokenRepository).save(entity);
    }
}
