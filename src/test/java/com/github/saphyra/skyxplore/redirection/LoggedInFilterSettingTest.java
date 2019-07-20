package com.github.saphyra.skyxplore.redirection;

import com.github.saphyra.authservice.auth.AuthService;
import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LoggedInFilterSettingTest {
    private static final String ACCESS_TOKEN_ID = "access_token_id";
    private static final String USER_ID = "user_id";
    private static final String REQUEST_URI = "request_uri";

    @Mock
    private AuthService authService;

    @InjectMocks
    private LoggedInFilterSetting underTest;

    @Mock
    private RedirectionContext redirectionContext;

    @Before
    public void setUp() {
        given(redirectionContext.getAccessTokenId()).willReturn(Optional.of(ACCESS_TOKEN_ID));
        given(redirectionContext.getUserId()).willReturn(Optional.of(USER_ID));
        given(redirectionContext.getRequestUri()).willReturn(REQUEST_URI);
        given(redirectionContext.getRequestMethod()).willReturn(HttpMethod.GET);
    }

    @Test
    public void shouldRedirect_emptyAccessTokenId() {
        //GIVEN
        given(redirectionContext.getAccessTokenId()).willReturn(Optional.empty());
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldRedirect_emptyUserId() {
        //GIVEN
        given(redirectionContext.getUserId()).willReturn(Optional.empty());
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldRedirect_notGranted() {
        //GIVEN
        given(authService.canAccess(REQUEST_URI, HttpMethod.GET, USER_ID, ACCESS_TOKEN_ID)).willReturn(AccessStatus.UNAUTHORIZED);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldRedirect_granted() {
        //GIVEN
        given(authService.canAccess(REQUEST_URI, HttpMethod.GET, USER_ID, ACCESS_TOKEN_ID)).willReturn(AccessStatus.GRANTED);
        //WHEN
        boolean result = underTest.shouldRedirect(redirectionContext);
        //THEN
        assertThat(result).isTrue();
    }
}