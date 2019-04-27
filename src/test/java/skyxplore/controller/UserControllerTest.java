package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.EmailCache;
import skyxplore.cache.UserNameCache;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.user.AccountDeleteRequest;
import skyxplore.controller.request.user.ChangeEmailRequest;
import skyxplore.controller.request.user.ChangePasswordRequest;
import skyxplore.controller.request.user.ChangeUserNameRequest;
import skyxplore.controller.request.user.UserRegistrationRequest;
import skyxplore.service.UserFacade;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_EMAIL;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.createAccountDeleteRequest;
import static skyxplore.testutil.TestUtils.createChangeEmailRequest;
import static skyxplore.testutil.TestUtils.createChangePasswordRequest;
import static skyxplore.testutil.TestUtils.createChangeUserNameRequest;
import static skyxplore.testutil.TestUtils.createUserRegistrationRequest;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserNameCache userNameCache;

    @Mock
    private EmailCache emailCache;

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private UserController underTest;

    @Test
    public void testChangeEmailShouldCallFacade() {
        //GIVEN
        ChangeEmailRequest request = createChangeEmailRequest();
        //WHEN
        underTest.changeEmail(request, USER_ID);
        //THEN
        verify(userFacade).changeEmail(request, USER_ID);
    }

    @Test
    public void testChangePasswordShouldCallFacade() {
        //GIVEN
        ChangePasswordRequest request = createChangePasswordRequest();
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(userFacade).changePassword(request, USER_ID);
    }

    @Test
    public void testChangeUserNameShouldCallFacadeAndInvalidate() {
        //GIVEN
        ChangeUserNameRequest request = createChangeUserNameRequest();
        //WHEN
        underTest.changeUserName(request, USER_ID);
        //THEN
        verify(userFacade).changeUserName(request, USER_ID);
        verify(userNameCache).invalidate(request.getNewUserName());
    }

    @Test
    public void testDeleteAccountShouldCallFacade() {
        //GIVEN
        AccountDeleteRequest request = createAccountDeleteRequest();
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(userFacade).deleteAccount(request, USER_ID);
    }

    @Test
    public void testIsEmailExistsShouldCallCacheAndReturn() {
        //GIVEN
        when(emailCache.get(USER_EMAIL)).thenReturn(Optional.of(true));
        //WHEN
        boolean result = underTest.isEmailExists(new OneStringParamRequest(USER_EMAIL));
        //THEN
        verify(emailCache).get(USER_EMAIL);
        assertTrue(result);
    }

    @Test
    public void testRegistrationShouldCallFacadeAndInvalidate() {
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
    public void testIsUserNameExistsShouldCallFacadeAndReturn() {
        //GIVEN
        when(userNameCache.get(USER_NAME)).thenReturn(Optional.of(true));
        //WHEN
        boolean result = underTest.isUsernameExists(new OneStringParamRequest(USER_NAME));
        //THEN
        assertTrue(result);
        verify(userNameCache).get(USER_NAME);
    }
}
