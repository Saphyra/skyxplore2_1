package com.github.saphyra.skyxplore.platform.redirection;

import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import com.github.saphyra.skyxplore.userdata.characterstatus.CharacterStatusQueryService;
import com.github.saphyra.skyxplore.userdata.characterstatus.domain.CharacterStatus;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSelectedFilterSettingTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private CharacterStatusQueryService characterStatusQueryService;

    @InjectMocks
    private CharacterSelectedFilterSetting underTest;

    @Mock
    private RedirectionContext redirectionContext;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        given(redirectionContext.getRequestUri()).willReturn(PageController.LOBBY_QUEUE_MAPPING);
        given(redirectionContext.getRequest()).willReturn(request);

        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_CHARACTER_ID)).willReturn(Optional.of(CHARACTER_ID));
    }

    @Test
    public void shouldRedirect_allowedUdi() {
        //GIVEN
        given(redirectionContext.getRequestUri()).willReturn(PageController.SHOP_MAPPING);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldRedirect_characterIdCookieNotFound() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldRedirect_characterNotStatusNotFound() {
        //GIVEN
        given(characterStatusQueryService.getCharacterStatus(CHARACTER_ID)).willReturn(CharacterStatus.INACTIVE);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldRedirect_characterNotActive() {
        //GIVEN
        given(characterStatusQueryService.getCharacterStatus(CHARACTER_ID)).willReturn(CharacterStatus.IN_LOBBY);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldRedirect_characterActive() {
        //GIVEN
        given(characterStatusQueryService.getCharacterStatus(CHARACTER_ID)).willReturn(CharacterStatus.ACTIVE);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isTrue();
    }
}