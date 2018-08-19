package skyxplore.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.user.ChangeEmailRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.EmailAlreadyExistsException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangeEmailServiceTest {
    @Mock
    private  UserQueryService userQueryService;

    @Mock
    private  UserDao userDao;

    @InjectMocks
    private ChangeEmailService underTest;

    @Test(expected = EmailAlreadyExistsException.class)
    public void testChangeEmailShouldThrowExceptionWhenEmailExists(){
        //GIVEN
        when(userQueryService.isEmailExists(USER_NEW_EMAIL)).thenReturn(true);
        //WHEN
        underTest.changeEmail(createChangeEmailRequest(), USER_ID);
    }

    @Test(expected = BadCredentialsException.class)
    public void testChangeEmailShouldThrowExceptionWhenBadPassword(){
        //GIVEN
        ChangeEmailRequest request = createChangeEmailRequest();
        request.setPassword(USER_FAKE_PASSWORD);

        SkyXpUser user = createUser();

        when(userQueryService.isEmailExists(USER_NEW_EMAIL)).thenReturn(false);
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);
        //WHEN
        underTest.changeEmail(request, USER_ID);
    }

    @Test
    public void testChangeEmailShouldSave(){
        //GIVEN
        ChangeEmailRequest request = createChangeEmailRequest();

        SkyXpUser user = createUser();

        when(userQueryService.isEmailExists(USER_NEW_EMAIL)).thenReturn(false);
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);
        //WHEN
        underTest.changeEmail(request, USER_ID);
        //THEN
        verify(userQueryService).isEmailExists(USER_NEW_EMAIL);
        verify(userQueryService).getUserById(USER_ID);
        verify(userDao).update(user);
        assertEquals(USER_NEW_EMAIL, user.getEmail());
    }
}
