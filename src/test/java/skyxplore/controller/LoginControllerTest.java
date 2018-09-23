package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.LoginRequest;
import skyxplore.domain.accesstoken.AccessToken;
import skyxplore.service.AccessTokenFacade;
import skyxplore.util.CookieUtil;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.filter.FilterHelper.*;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
    @Mock
    private AccessTokenFacade accessTokenFacade;

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private LoginController underTest;

    @Test
    public void testLoginShouldCallFacadeAndSetCookies(){
        //GIVEN
        LoginRequest request = createLoginRequest();

        AccessToken accessToken = createAccessToken();
        when(accessTokenFacade.login(request)).thenReturn(accessToken);
        //WHEN
        underTest.login(request, httpServletResponse);
        //THEN
        verify(accessTokenFacade).login(request);
        verify(cookieUtil).setCookie(httpServletResponse, COOKIE_USER_ID, USER_ID);
        verify(cookieUtil).setCookie(httpServletResponse, COOKIE_ACCESS_TOKEN, ACCESS_TOKEN_ID);
    }

    @Test
    public void testSelectCharacterShouldCallFacadeAndSetCookie(){
        //WHEN
        underTest.selectCharacter(CHARACTER_ID, USER_ID, httpServletResponse);
        //THEN
        verify(accessTokenFacade).selectCharacter(CHARACTER_ID, USER_ID);
        verify(cookieUtil).setCookie(httpServletResponse, COOKIE_CHARACTER_ID, CHARACTER_ID);
    }
}
