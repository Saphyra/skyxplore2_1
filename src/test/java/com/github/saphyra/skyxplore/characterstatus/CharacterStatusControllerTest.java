package com.github.saphyra.skyxplore.characterstatus;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CharacterStatusControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";
    private static final String FAKE_USER_ID = "fake_user_id";
    @Mock
    private AccessTokenDao accessTokenDao;

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