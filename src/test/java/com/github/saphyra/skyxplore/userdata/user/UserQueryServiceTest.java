package com.github.saphyra.skyxplore.userdata.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.exception.UserNotFoundException;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.userdata.user.repository.user.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class UserQueryServiceTest {
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserQueryService underTest;

    @Mock
    private SkyXpUser user;

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
        when(userDao.findUserByEmail(EMAIL)).thenReturn(Optional.of(user));
        //WHEN
        boolean result = underTest.isEmailExists(EMAIL);
        //THEN
        assertThat(result).isTrue();
        verify(userDao).findUserByEmail(EMAIL);
    }
}