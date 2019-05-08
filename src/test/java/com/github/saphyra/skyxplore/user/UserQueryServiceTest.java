package com.github.saphyra.skyxplore.user;

import com.github.saphyra.skyxplore.common.exception.UserNotFoundException;
import com.github.saphyra.skyxplore.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserQueryServiceTest {
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";

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
        SkyXpUser user = SkyXpUser.builder().build();
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
        SkyXpUser user = SkyXpUser.builder().build();
        when(userDao.findUserByEmail(EMAIL)).thenReturn(Optional.of(user));
        //WHEN
        boolean result = underTest.isEmailExists(EMAIL);
        //THEN
        assertThat(result).isTrue();
        verify(userDao).findUserByEmail(EMAIL);
    }
}