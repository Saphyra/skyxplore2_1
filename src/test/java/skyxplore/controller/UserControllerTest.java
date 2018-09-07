package skyxplore.controller;

import com.google.common.cache.Cache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.user.*;
import skyxplore.service.UserFacade;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private Cache<String, Boolean> userNameCache;

    @Mock
    private Cache<String, Boolean> emailCache;

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private UserController underTest;

    @Before
    public void init(){
        underTest = new UserController(userNameCache, emailCache, userFacade);
    }

    @Test
    public void testChangeEmailShouldCallFacade(){
        //GIVEN
        ChangeEmailRequest request = createChangeEmailRequest();
        //WHEN
        underTest.changeEmail(request, USER_ID);
        //THEN
        verify(userFacade).changeEmail(request, USER_ID);
    }

    @Test
    public void testChangePasswordShouldCallFacade(){
        //GIVEN
        ChangePasswordRequest request = createChangePasswordRequest();
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(userFacade).changePassword(request, USER_ID);
    }

    @Test
    public void testChangeUserNameShouldCallFacadeAndInvalidate(){
        //GIVEN
        ChangeUserNameRequest request = createChangeUserNameRequest();
        //WHEN
        underTest.changeUserName(request, USER_ID);
        //THEN
        verify(userFacade).changeUserName(request, USER_ID);
        verify(userNameCache).invalidate(request.getNewUserName());
    }

    @Test
    public void testDeleteAccountShouldCallFacade(){
        //GIVEN
        AccountDeleteRequest request = createAccountDeleteRequest();
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(userFacade).deleteAccount(request, USER_ID);
    }

    @Test
    public void testIsEmailExistsShouldCallCacheAndReturn() throws ExecutionException {
        //GIVEN
        when(emailCache.get(USER_EMAIL)).thenReturn(true);
        //WHEN
        boolean result = underTest.isEmailExists(new OneStringParamRequest(USER_EMAIL));
        //THEN
        verify(emailCache).get(USER_EMAIL);
        assertTrue(result);
    }

    @Test
    public void testRegistrationShouldCallFacadeAndInvalidate(){
        //GIVEN
        UserRegistrationRequest registrationRequest = createUserRegistrationRequest();
        //WHEN
        underTest.registration(registrationRequest);
        //THEN
        verify(userFacade).registrateUser(registrationRequest);
        verify(userNameCache).invalidate(registrationRequest.getUsername());
        verify(emailCache).invalidate(registrationRequest.getEmail());
    }

    @Test
    public void testIsUserNameExistsShouldCallFacadeAndReturn() throws ExecutionException {
        //GIVEN
        when(userNameCache.get(USER_NAME)).thenReturn(true);
        //WHEN
        boolean result = underTest.isUsernameExists(new OneStringParamRequest(USER_NAME));
        //THEN
        assertTrue(result);
        verify(userNameCache).get(USER_NAME);
    }
}
