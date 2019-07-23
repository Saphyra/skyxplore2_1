package com.github.saphyra.skyxplore.userdata.character;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.util.CookieUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_CHARACTER_ID;
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