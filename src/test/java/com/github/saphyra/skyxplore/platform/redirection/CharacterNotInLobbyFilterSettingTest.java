package com.github.saphyra.skyxplore.platform.redirection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import com.github.saphyra.skyxplore.userdata.characterstatus.CharacterStatusQueryService;
import com.github.saphyra.skyxplore.userdata.characterstatus.domain.CharacterStatus;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class CharacterNotInLobbyFilterSettingTest {
    private static final String CHARACTER_ID = "character_id";
    @Mock
    private CharacterStatusQueryService characterStatusQueryService;

    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private CharacterNotInLobbyFilterSetting underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private RedirectionContext redirectionContext;

    @Before
    public void setUp() {
        given(redirectionContext.getRequest()).willReturn(request);
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
    }

    @Test
    public void cookieNotFound() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void notInLobby() {
        //GIVEN
        given(characterStatusQueryService.getCharacterStatus(CHARACTER_ID)).willReturn(CharacterStatus.INACTIVE);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void inLobby() {
        //GIVEN
        given(characterStatusQueryService.getCharacterStatus(CHARACTER_ID)).willReturn(CharacterStatus.IN_LOBBY);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }
}