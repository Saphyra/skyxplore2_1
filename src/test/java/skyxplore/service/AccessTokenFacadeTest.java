package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.user.LoginRequest;
import skyxplore.service.accesstoken.AuthenticationService;
import skyxplore.service.accesstoken.CharacterSelectService;
import skyxplore.service.accesstoken.LoginService;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class AccessTokenFacadeTest {
    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private CharacterSelectService characterSelectService;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private AccessTokenFacade underTest;

    @Test
    public void testIsAuthenticatedShouldCallServiceAndReturn(){
        //GIVEN
        when(authenticationService.isAuthenticated(USER_ID, CHARACTER_ID_1)).thenReturn(true);
        //WHEN
        assertTrue(underTest.isAuthenticated(USER_ID, CHARACTER_ID_1));
        verify(authenticationService).isAuthenticated(USER_ID, CHARACTER_ID_1);
    }

    @Test
    public void testIsCharacterActiveShouldCallServiceAndReturn(){
        //GIVEN
        when(authenticationService.isCharacterActive(CHARACTER_ID_1)).thenReturn(true);
        //WHEN
        assertTrue(underTest.isCharacterActive(CHARACTER_ID_1));
        verify(authenticationService).isCharacterActive(CHARACTER_ID_1);
    }

    @Test
    public void testLoginShouldCallService(){
        //GIVEN
        LoginRequest request = createLoginRequest();
        //WHEN
        underTest.login(request);
        //THEN
        verify(loginService).login(request);
    }

    @Test
    public void testLogoutShouldCallService(){
        //WHEN
        underTest.logout(USER_ID, ACCESS_TOKEN_ID);
        //THEN
        verify(loginService).logout(USER_ID, ACCESS_TOKEN_ID);
    }

    @Test
    public void testSelectCharacterShouldCallService(){
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID);
        //THEN
        verify(characterSelectService).selectCharacter(CHARACTER_ID_1, USER_ID);
    }

}