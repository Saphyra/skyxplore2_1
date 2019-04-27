package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.user.*;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import skyxplore.service.user.*;


import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeTest {
    @Mock
    private ChangeEmailService changeEmailService;

    @Mock
    private ChangePasswordService changePasswordService;

    @Mock
    private ChangeUserNameService changeUserNameService;

    @Mock
    private DeleteAccountService deleteAccountService;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private UserFacade underTest;

    @Test
    public void testChangeEmailShouldCallService() {
        //GIVEN
        ChangeEmailRequest request = createChangeEmailRequest();
        //WHEN
        underTest.changeEmail(request, USER_ID);
        //THEN
        verify(changeEmailService).changeEmail(request, USER_ID);
    }

    @Test
    public void testChangePassword() {
        //GIVEN
        ChangePasswordRequest request = createChangePasswordRequest();
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(changePasswordService).changePassword(request, USER_ID);
    }

    @Test
    public void testChangeUserName() {
        //GIVEN
        ChangeUserNameRequest request = createChangeUserNameRequest();
        //WHEN
        underTest.changeUserName(request, USER_ID);
        //THEN
        verify(changeUserNameService).changeUserName(request, USER_ID);
    }

    @Test
    public void testDeleteAccount() {
        //GIVEN
        AccountDeleteRequest request = createAccountDeleteRequest();
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(deleteAccountService).deleteAccount(request, USER_ID);
    }

    @Test
    public void testGetUserById() {
        //GIVEN
        SkyXpUser user = createUser();
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);
        //WHEN
        SkyXpUser result = underTest.getUserById(USER_ID);
        //THEN
        verify(userQueryService).getUserById(USER_ID);
        assertEquals(user, result);
    }

    @Test
    public void testIsEmailExists() {
        //GIVEN
        when(userQueryService.isEmailExists(USER_EMAIL)).thenReturn(true);
        //WHEN
        assertTrue(underTest.isEmailExists(USER_EMAIL));
        verify(userQueryService).isEmailExists(USER_EMAIL);
    }

    @Test
    public void testRegistrateUser() {
        //GIVEN
        UserRegistrationRequest registrationRequest = createUserRegistrationRequest();
        //WHEN
        underTest.registrateUser(registrationRequest);
        //THEN
        verify(registrationService).registerUser(registrationRequest);
    }
}