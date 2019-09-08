package com.github.saphyra.skyxplore.userdata.user;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.LockedException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.user.cache.EmailCache;
import com.github.saphyra.skyxplore.userdata.user.domain.ChangeEmailRequest;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.userdata.user.repository.user.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class ChangeEmailServiceTest {
    private static final String NEW_EMAIL = "new_email";
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String HASHED_PASSWORD = "hashed_password";

    @Mock
    private PasswordService passwordService;

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private UserDao userDao;

    @Mock
    private EmailCache emailCache;

    @InjectMocks
    private ChangeEmailService underTest;

    private SkyXpCredentials credentials;

    @Mock
    private SkyXpUser user;

    @Before
    public void setUp() {
        credentials = SkyXpCredentials.builder()
            .userId(USER_ID)
            .password(HASHED_PASSWORD)
            .userName("")
            .build();
    }

    @Test
    public void testChangeEmailShouldThrowExceptionWhenEmailExists() {
        //GIVEN
        when(userQueryService.isEmailExists(NEW_EMAIL)).thenReturn(true);
        ChangeEmailRequest request = new ChangeEmailRequest(NEW_EMAIL, PASSWORD);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.changeEmail(request, USER_ID));
        //THEN
        verifyException(ex, LockedException.class, ErrorCode.EMAIL_ALREADY_EXISTS);
    }

    @Test
    public void testChangeEmailShouldThrowExceptionWhenBadPassword() {
        //GIVEN
        ChangeEmailRequest request = new ChangeEmailRequest(NEW_EMAIL, FAKE_PASSWORD);


        when(userQueryService.isEmailExists(NEW_EMAIL)).thenReturn(false);
        when(credentialsService.findByUserId(USER_ID)).thenReturn(credentials);
        when(passwordService.authenticate(FAKE_PASSWORD, HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.changeEmail(request, USER_ID));
        //THEN
        verifyException(ex, UnauthorizedException.class, ErrorCode.WRONG_PASSWORD);
    }

    @Test
    public void testChangeEmailShouldSave() {
        //GIVEN
        ChangeEmailRequest request = new ChangeEmailRequest(NEW_EMAIL, PASSWORD);

        when(userQueryService.isEmailExists(NEW_EMAIL)).thenReturn(false);
        when(credentialsService.findByUserId(USER_ID)).thenReturn(credentials);
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);
        when(passwordService.authenticate(PASSWORD, HASHED_PASSWORD)).thenReturn(true);
        //WHEN
        underTest.changeEmail(request, USER_ID);
        //THEN
        verify(passwordService).authenticate(PASSWORD, HASHED_PASSWORD);
        verify(userQueryService).isEmailExists(NEW_EMAIL);
        verify(credentialsService).findByUserId(USER_ID);
        verify(userDao).save(user);
        verify(emailCache).invalidate(NEW_EMAIL);
        verify(user).setEmail(NEW_EMAIL);
    }
}
