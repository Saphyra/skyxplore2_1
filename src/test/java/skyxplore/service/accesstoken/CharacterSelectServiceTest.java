package skyxplore.service.accesstoken;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.AccessTokenCache;
import skyxplore.dataaccess.db.AccessTokenDao;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.character.CharacterQueryService;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createAccessToken;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSelectServiceTest {
    @Mock
    private AccessTokenCache accessTokenCache;

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private CharacterSelectService underTest;

    @Test(expected = UnauthorizedException.class)
    public void testSelectCharacterShouldThrowExceptionWhenAccessTokenNotFound() throws ExecutionException {
        //GIVEN
        when(accessTokenCache.get(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID);
    }

    @Test
    public void testSelectCharacterShouldUpdateAccessToken() throws ExecutionException {
        //GIVEN
        AccessToken accessToken = createAccessToken();
        accessToken.setCharacterId(null);
        when(accessTokenCache.get(USER_ID)).thenReturn(Optional.of(accessToken));
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
        verify(accessTokenCache).invalidate(USER_ID);
        verify(accessTokenDao).save(accessToken);
        assertEquals(CHARACTER_ID_1, accessToken.getCharacterId());
    }
}