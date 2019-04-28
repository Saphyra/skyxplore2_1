package org.github.saphyra.skyxplore.user;

import org.github.saphyra.skyxplore.user.domain.SkyXpUser;
import org.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.exception.UserNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_EMAIL;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createUser;

@RunWith(MockitoJUnitRunner.class)
public class UserQueryServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserQueryService underTest;

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(userDao.findById(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.getUserById(USER_ID);
    }

    @Test
    public void testGetUserByIdShouldQueryAndReturn() {
        //GIVEN
        SkyXpUser user = createUser();
        when(userDao.findById(USER_ID)).thenReturn(Optional.of(user));
        //WHEN
        SkyXpUser result = underTest.getUserById(USER_ID);
        //THEN
        verify(userDao).findById(USER_ID);
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testIsEmailExists() {
        //GIVEN
        SkyXpUser user = createUser();
        when(userDao.findUserByEmail(USER_EMAIL)).thenReturn(user);
        //WHEN
        boolean result = underTest.isEmailExists(USER_EMAIL);
        //THEN
        assertThat(result).isTrue();
        verify(userDao).findUserByEmail(USER_EMAIL);
    }
}