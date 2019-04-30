package org.github.saphyra.skyxplore.character;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSelectServiceTest {
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final OffsetDateTime LAST_ACCESS = OffsetDateTime.now(ZoneOffset.UTC);
    private static final String USER_ID = "user_id";
    private static final String CHARACTER_ID = "character_id";
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
        underTest.selectCharacter(CHARACTER_ID, USER_ID);
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
        underTest.selectCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID, USER_ID);
        verify(accessTokenDao).save(accessToken);
        assertThat(accessToken.getCharacterId()).isEqualTo(CHARACTER_ID);
    }
}