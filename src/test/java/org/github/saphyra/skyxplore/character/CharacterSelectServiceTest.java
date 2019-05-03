package org.github.saphyra.skyxplore.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.github.saphyra.skyxplore.common.CookieUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;

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

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CharacterSelectService underTest;

    @Test(expected = UnauthorizedException.class)
    public void testSelectCharacterShouldThrowExceptionWhenAccessTokenNotFound() {
        //GIVEN
        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.selectCharacter(CHARACTER_ID, USER_ID, response);
    }

    @Test
    public void testSelectCharacterShouldUpdateAccessToken() {
        //GIVEN
        SkyXpAccessToken accessToken = SkyXpAccessToken.builder()
            .accessTokenId(ACCESS_TOKEN_ID)
            .userId(USER_ID)
            .lastAccess(LAST_ACCESS)
            .build();
        when(accessTokenDao.findByUserId(USER_ID)).thenReturn(Optional.of(accessToken));
        //WHEN
        underTest.selectCharacter(CHARACTER_ID, USER_ID, response);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID, USER_ID);
        verify(accessTokenDao).save(accessToken);
        verify(cookieUtil).setCookie(response, COOKIE_CHARACTER_ID, CHARACTER_ID);
        assertThat(accessToken.getCharacterId()).isEqualTo(CHARACTER_ID);
    }
}