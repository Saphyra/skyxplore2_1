package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.service.accesstoken.CharacterSelectService;
import skyxplore.util.CookieUtil;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;
import static skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
    @Mock
    private CharacterSelectService characterSelectService;

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private LoginController underTest;

    @Test
    public void testSelectCharacterShouldCallFacadeAndSetCookie(){
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID, httpServletResponse);
        //THEN
        verify(characterSelectService).selectCharacter(CHARACTER_ID_1, USER_ID);
        verify(cookieUtil).setCookie(httpServletResponse, COOKIE_CHARACTER_ID, CHARACTER_ID_1);
    }
}
