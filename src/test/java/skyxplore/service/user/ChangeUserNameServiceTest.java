package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.UserNameCache;
import skyxplore.controller.request.user.ChangeUserNameRequest;
import org.github.saphyra.skyxplore.user.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CREDENTIALS_HASHED_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NEW_NAME;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;
import static skyxplore.testutil.TestUtils.createChangeUserNameRequest;
import static skyxplore.testutil.TestUtils.createCredentials;

@RunWith(MockitoJUnitRunner.class)
public class ChangeUserNameServiceTest {
    @Mock
    private CredentialsService credentialsService;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserNameCache userNameCache;

    @InjectMocks
    private ChangeUserNameService underTest;

    @Test(expected = UserNameAlreadyExistsException.class)
    public void testChangeUserNameShouldThrowExceptionWhenUserNameExists() {
        //GIVEN
        when(credentialsService.isUserNameExists(USER_NEW_NAME)).thenReturn(true);
        //WHEN
        underTest.changeUserName(createChangeUserNameRequest(), USER_ID);
    }

    @Test(expected = BadCredentialsException.class)
    public void testChangeUserNameShouldThrowExceptionWhenWrongPassword() {
        //GIVEN
        ChangeUserNameRequest request = createChangeUserNameRequest();
        request.setPassword(USER_FAKE_PASSWORD);

        SkyXpCredentials skyXpCredentials = createCredentials();

        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        when(credentialsService.isUserNameExists(USER_NEW_NAME)).thenReturn(false);
        when(passwordService.authenticate(USER_FAKE_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.changeUserName(request, USER_ID);
    }

    @Test
    public void testChangeUserNameShouldSaveChangedUser() {
        //GIVEN
        ChangeUserNameRequest request = createChangeUserNameRequest();

        SkyXpCredentials skyXpCredentials = createCredentials();

        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        when(credentialsService.isUserNameExists(USER_NEW_NAME)).thenReturn(false);
        when(passwordService.authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(true);
        //WHEN
        underTest.changeUserName(request, USER_ID);
        //THEN
        verify(passwordService).authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD);
        verify(credentialsService).getByUserId(USER_ID);
        verify(credentialsService).isUserNameExists(USER_NEW_NAME);
        verify(credentialsService).save(skyXpCredentials);
        assertEquals(USER_NEW_NAME, skyXpCredentials.getUserName());
        verify(userNameCache).invalidate(USER_NEW_NAME);
    }
}
