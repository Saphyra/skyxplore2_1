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
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.userdata.user.repository.credentials.CredentialsDao;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCredentialsServiceTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final SkyXpCredentials CREDENTIALS = SkyXpCredentials.builder()
        .userName(USER_NAME)
        .userId(USER_ID)
        .password(PASSWORD)
        .build();

    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private CredentialsService underTest;

    @Test
    public void testGetByUserIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(credentialsDao.findById(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByUserId(USER_ID));
        //THEN
        verifyException(ex, NotFoundException.class, ErrorCode.CREDENTIALS_NOT_FOUND);
    }

    @Test
    public void testGetByUserIdShouldCallDaoAndReturn() {
        //GIVEN
        when(credentialsDao.findById(USER_ID)).thenReturn(Optional.of(CREDENTIALS));
        //WHEN
        SkyXpCredentials result = underTest.findByUserId(USER_ID);
        //THEN
        verify(credentialsDao).findById(USER_ID);
        assertThat(result).isEqualTo(CREDENTIALS);
    }

    @Test
    public void testIsUserNameExistsShouldReturn() {
        //GIVEN
        when(credentialsDao.findByName(USER_NAME)).thenReturn(Optional.of(CREDENTIALS));
        //WHEN
        boolean result = underTest.isUserNameExists(USER_NAME);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void testSaveShouldCallDao() {
        //GIVEN
        //WHEN
        underTest.save(CREDENTIALS);
        //THEN
        verify(credentialsDao).save(CREDENTIALS);
    }
}