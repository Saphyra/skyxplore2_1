package skyxplore.service.accesstoken;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSelectServiceTest {
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final OffsetDateTime LAST_ACCESS = OffsetDateTime.now(ZoneOffset.UTC);
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
        SkyXpAccessToken accessToken = new SkyXpAccessToken();
        accessToken.setAccessTokenId(ACCESS_TOKEN_ID);
        accessToken.setUserId(USER_ID);
        accessToken.setLastAccess(LAST_ACCESS);
        accessToken.setCharacterId(null);
        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.of(accessToken));
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID_1, USER_ID);
        verify(accessTokenDao).save(accessToken);
        assertEquals(CHARACTER_ID_1, accessToken.getCharacterId());
    }
}