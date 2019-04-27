package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.EmailCache;
import skyxplore.controller.request.user.ChangeEmailRequest;
import org.github.saphyra.skyxplore.user.UserDao;
import org.github.saphyra.skyxplore.user.domain.credentials.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CREDENTIALS_HASHED_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NEW_EMAIL;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;
import static skyxplore.testutil.TestUtils.createChangeEmailRequest;
import static skyxplore.testutil.TestUtils.createCredentials;
import static skyxplore.testutil.TestUtils.createUser;

@RunWith(MockitoJUnitRunner.class)
public class ChangeEmailServiceTest {
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

    @Test(expected = EmailAlreadyExistsException.class)
    public void testChangeEmailShouldThrowExceptionWhenEmailExists() {
        //GIVEN
        when(userQueryService.isEmailExists(USER_NEW_EMAIL)).thenReturn(true);
        //WHEN
        underTest.changeEmail(createChangeEmailRequest(), USER_ID);
    }

    @Test(expected = BadCredentialsException.class)
    public void testChangeEmailShouldThrowExceptionWhenBadPassword() {
        //GIVEN
        ChangeEmailRequest request = createChangeEmailRequest();
        request.setPassword(USER_FAKE_PASSWORD);

        SkyXpCredentials skyXpCredentials = createCredentials();

        when(userQueryService.isEmailExists(USER_NEW_EMAIL)).thenReturn(false);
        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        when(passwordService.authenticate(USER_FAKE_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.changeEmail(request, USER_ID);
    }

    @Test
    public void testChangeEmailShouldSave() {
        //GIVEN
        ChangeEmailRequest request = createChangeEmailRequest();

        SkyXpUser user = createUser();
        SkyXpCredentials skyXpCredentials = createCredentials();

        when(userQueryService.isEmailExists(USER_NEW_EMAIL)).thenReturn(false);
        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);
        when(passwordService.authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(true);
        //WHEN
        underTest.changeEmail(request, USER_ID);
        //THEN
        verify(passwordService).authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD);
        verify(userQueryService).isEmailExists(USER_NEW_EMAIL);
        verify(credentialsService).getByUserId(USER_ID);
        verify(userDao).save(user);
        assertEquals(USER_NEW_EMAIL, user.getEmail());
        verify(emailCache).invalidate(USER_NEW_EMAIL);
    }
}
