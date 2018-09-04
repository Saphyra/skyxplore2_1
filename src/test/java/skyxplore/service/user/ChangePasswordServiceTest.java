package skyxplore.service.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NEW_PASSWORD;
import static skyxplore.testutil.TestUtils.createChangePasswordRequest;
import static skyxplore.testutil.TestUtils.createCredentials;
import static skyxplore.testutil.TestUtils.createUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.user.ChangePasswordRequest;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.service.credentials.CredentialsService;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordServiceTest {
    @Mock
    private CredentialsService credentialsService;

    @InjectMocks
    private ChangePasswordService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testChangePasswordShouldThrowExceptionWhenBadPassword() {
        //GIVEN
        Credentials credentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(credentials);

        ChangePasswordRequest request = createChangePasswordRequest();
        request.setOldPassword(USER_FAKE_PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(credentialsService).getByUserId(USER_ID);
    }

    @Test(expected = BadlyConfirmedPasswordException.class)
    public void testChangePasswordShouldThrowExceptionWhenConfirmPasswordNotEquals() {
        //GIVEN
        Credentials credentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(credentials);

        ChangePasswordRequest request = createChangePasswordRequest();
        request.setConfirmPassword(USER_FAKE_PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(credentialsService).getByUserId(USER_ID);
    }

    @Test
    public void testChangePasswordShouldUpdateUser() {
        //GIVEN
        Credentials credentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(credentials);

        ChangePasswordRequest request = createChangePasswordRequest();
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(credentialsService).getByUserId(USER_ID);
        verify(credentialsService).save(credentials);
        assertEquals(USER_NEW_PASSWORD, credentials.getPassword());
    }
}
