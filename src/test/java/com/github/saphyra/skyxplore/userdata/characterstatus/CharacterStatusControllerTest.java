package com.github.saphyra.skyxplore.userdata.characterstatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.userdata.characterstatus.domain.CharacterStatus;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class CharacterStatusControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";
    private static final String FAKE_USER_ID = "fake_user_id";

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private CharacterStatusQueryService characterStatusQueryService;

    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private CharacterStatusController underTest;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SkyXpAccessToken accessToken;

    @Before
    public void setUp() {
        given(accessToken.getUserId()).willReturn(USER_ID);
        given(accessTokenDao.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(accessToken));
    }

    @Test
    public void getCharacterStatus() {
        //WHEN
        given(characterStatusQueryService.getCharacterStatus(CHARACTER_ID)).willReturn(CharacterStatus.INACTIVE);
        //WHEN
        String  result = underTest.getCharacterStatus(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(CharacterStatus.INACTIVE.name());
    }

    @Test
    public void characterDeselect_accessTokenNotFound() {
        //GIVEN
        given(accessTokenDao.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        underTest.characterDeselect(USER_ID, CHARACTER_ID, response);
        //THEN
        verifyZeroInteractions(cookieUtil);
    }

    @Test(expected = ForbiddenException.class)
    public void characterDeselect_wrongUserId() {
        underTest.characterDeselect(FAKE_USER_ID, CHARACTER_ID, response);
    }

    @Test
    public void characterDeselect() {
        //WHEN
        underTest.characterDeselect(USER_ID, CHARACTER_ID, response);
        //THEN
        verify(accessToken).setCharacterId(null);
        verify(accessTokenDao).save(accessToken);
        verify(cookieUtil).setCookie(response, RequestConstants.COOKIE_CHARACTER_ID, "");
    }
}