package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.AccessTokenRepository;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.domain.accesstoken.AccessTokenConverter;
import skyxplore.domain.accesstoken.AccessTokenEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenDaoTest {
    @Mock
    private AccessTokenRepository accessTokenRepository;

    @Mock
    private AccessTokenConverter accessTokenConverter;

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
        //WHEN
        underTest.deleteExpired(0L);
        //THEN
        verify(accessTokenRepository).deleteExpired(0L);
    }

    @Test
    public void testFindByTokenIdShouldReturnNull(){
        //GIVEN
        when(accessTokenRepository.findById(ACCESS_TOKEN_ID)).thenReturn(Optional.empty());
        //WHEN
        AccessToken result = underTest.findById(ACCESS_TOKEN_ID);
        //THEN
        assertNull(result);
    }

    @Test
    public void testFindByCharacterIdShouldReturnDomain(){
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findByCharacterId(CHARACTER_ID)).thenReturn(entity);

        AccessToken accessToken = createAccessToken();
        when(accessTokenConverter.convertEntity(entity)).thenReturn(accessToken);
        //WHEN
        AccessToken result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        verify(accessTokenRepository).findByCharacterId(CHARACTER_ID);
        verify(accessTokenConverter).convertEntity(entity);
        assertEquals(accessToken, result);
    }

    @Test
    public void testFindByTokenIdShouldReturnDomain(){
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findById(ACCESS_TOKEN_ID)).thenReturn(Optional.of(entity));

        AccessToken accessToken = createAccessToken();
        when(accessTokenConverter.convertEntity(entity)).thenReturn(accessToken);
        //WHEN
        AccessToken result = underTest.findById(ACCESS_TOKEN_ID);
        //THEN
        verify(accessTokenRepository).findById(ACCESS_TOKEN_ID);
        verify(accessTokenConverter).convertEntity(entity);
        assertEquals(accessToken, result);
    }

    @Test
    public void testFindByUserIdShouldReturnDomain(){
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findByUserId(USER_ID)).thenReturn(entity);

        AccessToken accessToken = createAccessToken();
        when(accessTokenConverter.convertEntity(entity)).thenReturn(accessToken);
        //WHEN
        AccessToken result = underTest.findByUserId(USER_ID);
        //THEN
        verify(accessTokenRepository).findByUserId(USER_ID);
        verify(accessTokenConverter).convertEntity(entity);
        assertEquals(accessToken, result);
    }

    @Test
    public void testFindByUserIdOrTokenIdShouldReturnDomain(){
        //GIVEN
        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenRepository.findByUserIdOrAccessTokenId(USER_ID, ACCESS_TOKEN_ID)).thenReturn(entity);

        AccessToken accessToken = createAccessToken();
        when(accessTokenConverter.convertEntity(entity)).thenReturn(accessToken);
        //WHEN
        AccessToken result = underTest.findByUserIdOrTokenId(USER_ID, ACCESS_TOKEN_ID);
        //THEN
        verify(accessTokenRepository).findByUserIdOrAccessTokenId(USER_ID, ACCESS_TOKEN_ID);
        verify(accessTokenConverter).convertEntity(entity);
        assertEquals(accessToken, result);
    }

    @Test
    public void testSaceShouldCallRepositors(){
        //GIVEN
        AccessToken token = createAccessToken();

        AccessTokenEntity entity = createAccessTokenEntity();
        when(accessTokenConverter.convertDomain(token)).thenReturn(entity);
        //WHEN
        underTest.save(token);
        //THEN
        verify(accessTokenRepository).save(entity);
    }
}
