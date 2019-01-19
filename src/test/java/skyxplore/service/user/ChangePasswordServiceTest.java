package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.user.ChangePasswordRequest;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.service.credentials.CredentialsService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CREDENTIALS_HASHED_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NEW_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;
import static skyxplore.testutil.TestUtils.createChangePasswordRequest;
import static skyxplore.testutil.TestUtils.createCredentials;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordServiceTest {
    @Mock
    private CredentialsService credentialsService;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private ChangePasswordService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testChangePasswordShouldThrowExceptionWhenBadPassword() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);

        ChangePasswordRequest request = createChangePasswordRequest();
        request.setOldPassword(USER_FAKE_PASSWORD);

        when(passwordService.authenticate(USER_FAKE_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(credentialsService).getByUserId(USER_ID);
        verify(passwordService).authenticate(USER_FAKE_PASSWORD, CREDENTIALS_HASHED_PASSWORD);
    }

    @Test(expected = BadlyConfirmedPasswordException.class)
    public void testChangePasswordShouldThrowExceptionWhenConfirmPasswordNotEquals() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);

        ChangePasswordRequest request = createChangePasswordRequest();
        request.setConfirmPassword(USER_FAKE_PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(credentialsService).getByUserId(USER_ID);
    }

    @Test
    public void testChangePasswordShouldCredentials() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);

        ChangePasswordRequest request = createChangePasswordRequest();

        when(passwordService.authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(true);
        when(passwordService.hashPassword(USER_NEW_PASSWORD)).thenReturn(CREDENTIALS_HASHED_PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(credentialsService).getByUserId(USER_ID);
        verify(credentialsService).save(skyXpCredentials);
        verify(passwordService).authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD);
        assertEquals(CREDENTIALS_HASHED_PASSWORD, skyXpCredentials.getPassword());
    }
}
