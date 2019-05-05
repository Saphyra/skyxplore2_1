package org.github.saphyra.skyxplore.user;

import com.github.saphyra.encryption.impl.PasswordService;
import org.github.saphyra.skyxplore.common.exception.BadCredentialsException;
import org.github.saphyra.skyxplore.common.exception.EmailAlreadyExistsException;
import org.github.saphyra.skyxplore.user.cache.EmailCache;
import org.github.saphyra.skyxplore.user.domain.ChangeEmailRequest;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangeEmailServiceTest {
    private static final String NEW_EMAIL = "new_email";
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String HASHED_PASSWORD = "hashed_password";
    private static final String EMAIL = "email";

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

    @Before
    public void setUp() {
        credentials = new SkyXpCredentials(USER_ID, null, HASHED_PASSWORD);
    }

    @Test(expected = EmailAlreadyExistsException.class)
    public void testChangeEmailShouldThrowExceptionWhenEmailExists() {
        //GIVEN
        when(userQueryService.isEmailExists(NEW_EMAIL)).thenReturn(true);
        ChangeEmailRequest request = new ChangeEmailRequest(NEW_EMAIL, PASSWORD);
        //WHEN
        underTest.changeEmail(request, USER_ID);
    }

    @Test(expected = BadCredentialsException.class)
    public void testChangeEmailShouldThrowExceptionWhenBadPassword() {
        //GIVEN
        ChangeEmailRequest request = new ChangeEmailRequest(NEW_EMAIL, FAKE_PASSWORD);


        when(userQueryService.isEmailExists(NEW_EMAIL)).thenReturn(false);
        when(credentialsService.findByUserId(USER_ID)).thenReturn(credentials);
        when(passwordService.authenticate(FAKE_PASSWORD, HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.changeEmail(request, USER_ID);
    }

    @Test
    public void testChangeEmailShouldSave() {
        //GIVEN
        ChangeEmailRequest request = new ChangeEmailRequest(NEW_EMAIL, PASSWORD);

        SkyXpUser user = SkyXpUser.builder()
            .email(EMAIL)
            .build();

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
        assertThat(user.getEmail()).isEqualTo(NEW_EMAIL);
    }
}
