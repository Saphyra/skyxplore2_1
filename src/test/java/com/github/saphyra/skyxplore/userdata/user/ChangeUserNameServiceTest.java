package com.github.saphyra.skyxplore.userdata.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.common.exception.BadCredentialsException;
import com.github.saphyra.skyxplore.common.exception.UserNameAlreadyExistsException;
import com.github.saphyra.skyxplore.userdata.user.cache.UserNameCache;
import com.github.saphyra.skyxplore.userdata.user.domain.ChangeUserNameRequest;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;

@RunWith(MockitoJUnitRunner.class)
public class ChangeUserNameServiceTest {
    private static final String NEW_USER_NAME = "new_username";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String USER_ID = "user_id";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String HASHED_PASSWORD = "hashed_password";

    private SkyXpCredentials credentials;

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserNameCache userNameCache;

    @InjectMocks
    private ChangeUserNameService underTest;

    @Before
    public void setUp() {
        credentials = SkyXpCredentials.builder()
            .userName(USER_NAME)
            .userId(USER_ID)
            .password(HASHED_PASSWORD)
            .build();
    }

    @Test(expected = UserNameAlreadyExistsException.class)
    public void testChangeUserNameShouldThrowExceptionWhenUserNameExists() {
        //GIVEN
        when(credentialsService.isUserNameExists(USER_NAME)).thenReturn(true);
        //WHEN
        underTest.changeUserName(new ChangeUserNameRequest(USER_NAME, PASSWORD), USER_ID);
    }

    @Test(expected = BadCredentialsException.class)
    public void testChangeUserNameShouldThrowExceptionWhenWrongPassword() {
        //GIVEN
        ChangeUserNameRequest request = new ChangeUserNameRequest(NEW_USER_NAME, FAKE_PASSWORD);


        when(credentialsService.findByUserId(USER_ID)).thenReturn(credentials);
        when(credentialsService.isUserNameExists(NEW_USER_NAME)).thenReturn(false);
        when(passwordService.authenticate(FAKE_PASSWORD, HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.changeUserName(request, USER_ID);
    }

    @Test
    public void testChangeUserNameShouldSaveChangedUser() {
        //GIVEN
        ChangeUserNameRequest request = new ChangeUserNameRequest(NEW_USER_NAME, PASSWORD);


        when(credentialsService.findByUserId(USER_ID)).thenReturn(credentials);
        when(credentialsService.isUserNameExists(NEW_USER_NAME)).thenReturn(false);
        when(passwordService.authenticate(PASSWORD, HASHED_PASSWORD)).thenReturn(true);
        //WHEN
        underTest.changeUserName(request, USER_ID);
        //THEN
        verify(passwordService).authenticate(PASSWORD, HASHED_PASSWORD);
        verify(credentialsService).findByUserId(USER_ID);
        verify(credentialsService).isUserNameExists(NEW_USER_NAME);
        verify(credentialsService).save(credentials);
        assertThat(credentials.getUserName()).isEqualTo(NEW_USER_NAME);
        verify(userNameCache).invalidate(NEW_USER_NAME);
    }
}
