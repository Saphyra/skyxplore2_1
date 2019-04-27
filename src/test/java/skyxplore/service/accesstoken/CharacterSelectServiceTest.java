package skyxplore.service.accesstoken;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.auth.AccessTokenDao;
import org.github.saphyra.skyxplore.auth.domain.accesstoken.SkyXpAccessToken;
import skyxplore.service.character.CharacterQueryService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createAccessToken;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSelectServiceTest {
    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private CharacterSelectService underTest;

    @Test(expected = UnauthorizedException.class)
    public void testSelectCharacterShouldThrowExceptionWhenAccessTokenNotFound() {
        //GIVEN
        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID);
    }

    @Test
    public void testSelectCharacterShouldUpdateAccessToken() {
        //GIVEN
        SkyXpAccessToken skyXpAccessToken = createAccessToken();
        skyXpAccessToken.setCharacterId(null);
        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.of(skyXpAccessToken));
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
        verify(accessTokenDao).save(skyXpAccessToken);
        assertEquals(CHARACTER_ID_1, skyXpAccessToken.getCharacterId());
    }
}