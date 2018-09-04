package skyxplore.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.user.UserRegistrationRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.user.Role;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadlyConfirmedPasswordException;
import skyxplore.exception.EmailAlreadyExistsException;
import skyxplore.exception.UserNameAlreadyExistsException;
import skyxplore.service.credentials.CredentialsService;
import skyxplore.util.IdGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {
    @Mock
    private CredentialsService credentialsService;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private UserDao userDao;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private RegistrationService underTest;

    @Test(expected = BadlyConfirmedPasswordException.class)
    public void testRegistrateUserShouldThrowExceptionWhenConfirmPasswordNotEquals() {
        //GIVEN
        UserRegistrationRequest request = createUserRegistrationRequest();
        request.setConfirmPassword(USER_FAKE_PASSWORD);
        //WHEN
        underTest.registrateUser(request);
    }

    @Test(expected = UserNameAlreadyExistsException.class)
    public void testRegistrateUserShouldThrowExceptionWhenUserNameExists() {
        //GIVEN
        UserRegistrationRequest request = createUserRegistrationRequest();
        when(credentialsService.isUserNameExists(USER_NAME)).thenReturn(true);
        //WHEN
        underTest.registrateUser(request);
    }

    @Test(expected = EmailAlreadyExistsException.class)
    public void testRegistrateUserShouldThrowExceptionWhenEmailExists() {
        //GIVEN
        UserRegistrationRequest request = createUserRegistrationRequest();
        when(credentialsService.isUserNameExists(USER_NAME)).thenReturn(false);
        when(userQueryService.isEmailExists(USER_EMAIL)).thenReturn(true);
        //WHEN
        underTest.registrateUser(request);
    }

    @Test
    public void testRegistrateUserShouldSave() {
        //GIVEN
        UserRegistrationRequest request = createUserRegistrationRequest();
        when(credentialsService.isUserNameExists(USER_NAME)).thenReturn(false);
        when(userQueryService.isEmailExists(USER_EMAIL)).thenReturn(false);
        when(idGenerator.getRandomId()).thenReturn(USER_ID);
        //WHEN
        underTest.registrateUser(request);
        //THEN
        ArgumentCaptor<SkyXpUser> userCaptor = ArgumentCaptor.forClass(SkyXpUser.class);
        verify(userDao).registrateUser(userCaptor.capture());
        assertEquals(USER_ID, userCaptor.getValue().getUserId());
        assertEquals(USER_EMAIL, userCaptor.getValue().getEmail());
        assertEquals(1, userCaptor.getValue().getRoles().size());
        assertTrue(userCaptor.getValue().getRoles().contains(Role.USER));

        ArgumentCaptor<Credentials> credentialsCaptor = ArgumentCaptor.forClass(Credentials.class);
        verify(credentialsService).save(credentialsCaptor.capture());
        assertEquals(USER_PASSWORD, credentialsCaptor.getValue().getPassword());
        assertEquals(USER_NAME, credentialsCaptor.getValue().getUserName());
    }
}
