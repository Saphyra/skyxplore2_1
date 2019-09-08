package com.github.saphyra.skyxplore.userdata.user;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.common.ErrorCode;
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

    @Test
    public void testGetUserByIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(userDao.findById(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.getUserById(USER_ID));
        //THEN
        verifyException(ex, NotFoundException.class, ErrorCode.USER_NOT_FOUND);
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