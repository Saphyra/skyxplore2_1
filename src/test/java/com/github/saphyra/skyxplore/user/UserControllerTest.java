package com.github.saphyra.skyxplore.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.user.cache.EmailCache;
import com.github.saphyra.skyxplore.user.cache.UserNameCache;
import com.github.saphyra.skyxplore.user.domain.AccountDeleteRequest;
import com.github.saphyra.skyxplore.user.domain.ChangeEmailRequest;
import com.github.saphyra.skyxplore.user.domain.ChangePasswordRequest;
import com.github.saphyra.skyxplore.user.domain.ChangeUserNameRequest;
import com.github.saphyra.skyxplore.user.domain.UserRegistrationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private static final String NEW_EMAIL = "new_email";
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";
    private static final String NEW_PASSWORD = "new_password";
    private static final String NEW_USERNAME = "new_username";
    private static final String EMAIL = "email";
    private static final String USER_NAME = "user_name";
    @Mock
    private UserNameCache userNameCache;

    @Mock
    private EmailCache emailCache;

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

    @InjectMocks
    private UserController underTest;

    @Test
    public void testChangeEmailShouldCallFacade() {
        //GIVEN
        ChangeEmailRequest request = new ChangeEmailRequest(NEW_EMAIL, PASSWORD);
        //WHEN
        underTest.changeEmail(request, USER_ID);
        //THEN
        verify(changeEmailService).changeEmail(request, USER_ID);
    }

    @Test
    public void testChangePasswordShouldCallFacade() {
        //GIVEN
        ChangePasswordRequest request = new ChangePasswordRequest(NEW_PASSWORD, PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(changePasswordService).changePassword(request, USER_ID);
    }

    @Test
    public void testChangeUserNameShouldCallFacadeAndInvalidate() {
        //GIVEN
        ChangeUserNameRequest request = new ChangeUserNameRequest(NEW_USERNAME, PASSWORD);
        //WHEN
        underTest.changeUserName(request, USER_ID);
        //THEN
        verify(changeUserNameService).changeUserName(request, USER_ID);
        verify(userNameCache).invalidate(request.getNewUserName());
    }

    @Test
    public void testDeleteAccountShouldCallFacade() {
        //GIVEN
        AccountDeleteRequest request = new AccountDeleteRequest(PASSWORD);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(deleteAccountService).deleteAccount(request, USER_ID);
    }

    @Test
    public void testIsEmailExistsShouldCallCacheAndReturn() {
        //GIVEN
        when(emailCache.get(EMAIL)).thenReturn(Optional.of(true));
        //WHEN
        boolean result = underTest.isEmailExists(new OneStringParamRequest(EMAIL));
        //THEN
        verify(emailCache).get(EMAIL);
        assertThat(result).isTrue();
    }

    @Test
    public void testRegistrationShouldCallFacadeAndInvalidate() {
        //GIVEN
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest(USER_NAME, PASSWORD, EMAIL);
        //WHEN
        underTest.registration(registrationRequest);
        //THEN
        verify(registrationService).registerUser(registrationRequest);
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
        assertThat(result).isTrue();
        verify(userNameCache).get(USER_NAME);
    }
}
