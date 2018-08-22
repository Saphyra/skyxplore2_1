package skyxplore.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.user.ChangePasswordRequest;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.BadCredentialsException;
import skyxplore.exception.BadlyConfirmedPasswordException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordServiceTest {
    @Mock
    private UserDao userDao;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private ChangePasswordService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testChangePasswordShouldThrowExceptionWhenBadPassword(){
        //GIVEN
        SkyXpUser user = createUser();
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);

        ChangePasswordRequest request = createChangePasswordRequest();
        request.setOldPassword(USER_FAKE_PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(userQueryService).getUserById(USER_ID);
    }

    @Test(expected = BadlyConfirmedPasswordException.class)
    public void testChangePasswordShouldThrowExceptionWhenConfirmPasswordNotEquals(){
        //GIVEN
        SkyXpUser user = createUser();
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);

        ChangePasswordRequest request = createChangePasswordRequest();
        request.setConfirmPassword(USER_FAKE_PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(userQueryService).getUserById(USER_ID);
    }

    @Test
    public void testChangePasswordShouldUpdateUser(){
        //GIVEN
        SkyXpUser user = createUser();
        when(userQueryService.getUserById(USER_ID)).thenReturn(user);

        ChangePasswordRequest request = createChangePasswordRequest();
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(userQueryService).getUserById(USER_ID);
        verify(userDao).update(user);
        assertEquals(USER_NEW_PASSWORD, user.getPassword());
    }
}
