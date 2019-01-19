package skyxplore.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.user.SkyXpUser;
import skyxplore.exception.UserNotFoundException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class UserQueryServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserQueryService underTest;

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByIdShouldThrowExceptionWhenNotFound(){
        //GIVEN
        when(userDao.findById(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.getUserById(USER_ID);
    }

    @Test
    public void testGetUserByIdShouldQueryAndReturn(){
        //GIVEN
        SkyXpUser user = createUser();
        when(userDao.findById(USER_ID)).thenReturn(Optional.of(user));
        //WHEN
        SkyXpUser result = underTest.getUserById(USER_ID);
        //THEN
        verify(userDao).findById(USER_ID);
        assertEquals(user, result);
    }

    @Test
    public void testIsEmailExists(){
        //GIVEN
        SkyXpUser user = createUser();
        when(userDao.findUserByEmail(USER_EMAIL)).thenReturn(user);
        //WHEN
        assertTrue(underTest.isEmailExists(USER_EMAIL));
        verify(userDao).findUserByEmail(USER_EMAIL);
    }
}