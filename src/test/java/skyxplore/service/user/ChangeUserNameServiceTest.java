package skyxplore.service.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NEW_NAME;
import static skyxplore.testutil.TestUtils.createChangeUserNameRequest;
import static skyxplore.testutil.TestUtils.createUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.user.ChangeUserNameRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.UserNameAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class ChangeUserNameServiceTest {
    @Mock
    private UserDao userDao;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private ChangeUserNameService underTest;

    @Test(expected = UserNameAlreadyExistsException.class)
    public void testChangeUserNameShouldThrowExceptionWhenUserNameExists() {
        //GIVEN
        when(userQueryService.isUserNameExists(USER_NEW_NAME)).thenReturn(true);
        //WHEN
        underTest.changeUserName(createChangeUserNameRequest(), USER_ID);
    }

    @Test(expected = BadCredentialsException.class)
    public void testChangeUserNameShouldThrowExceptionWhenWrongPassword() {
        //GIVEN
        ChangeUserNameRequest request = createChangeUserNameRequest();
        request.setPassword(USER_FAKE_PASSWORD);

        SkyXpUser user = createUser();

        when(userQueryService.getUserById(USER_ID)).thenReturn(user);
        when(userQueryService.isUserNameExists(USER_NEW_NAME)).thenReturn(false);
        //WHEN
        underTest.changeUserName(request, USER_ID);
    }

    @Test
    public void testChangeUserNameShouldSaveChangedUser() {
        //GIVEN
        ChangeUserNameRequest request = createChangeUserNameRequest();

        SkyXpUser user = createUser();

        when(userQueryService.getUserById(USER_ID)).thenReturn(user);
        when(userQueryService.isUserNameExists(USER_NEW_NAME)).thenReturn(false);
        //WHEN
        underTest.changeUserName(request, USER_ID);
        //THEN
        verify(userQueryService).getUserById(USER_ID);
        verify(userQueryService).isUserNameExists(USER_NEW_NAME);
        verify(userDao).update(user);
        assertEquals(USER_NEW_NAME, user.getUsername());
    }
}
