package com.github.saphyra.skyxplore.user;

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
import com.github.saphyra.skyxplore.user.domain.ChangePasswordRequest;
import com.github.saphyra.skyxplore.user.domain.SkyXpCredentials;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordServiceTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String HASHED_PASSWORD = "hashed_password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String PASSWORD = "password";
    private static final String NEW_HASHED_PASSWORD = "new_hashed_password";

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private ChangePasswordService underTest;

    private SkyXpCredentials credentials;

    @Before
    public void setUp() {
        credentials = SkyXpCredentials.builder()
            .userId(USER_ID)
            .userName(USER_NAME)
            .password(HASHED_PASSWORD)
            .build();
    }

    @Test(expected = BadCredentialsException.class)
    public void testChangePasswordShouldThrowExceptionWhenBadPassword() {
        //GIVEN
        when(credentialsService.findByUserId(USER_ID)).thenReturn(credentials);

        ChangePasswordRequest request = new ChangePasswordRequest(NEW_PASSWORD, FAKE_PASSWORD);

        when(passwordService.authenticate(FAKE_PASSWORD, HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.changePassword(request, USER_ID);
    }

    @Test
    public void testChangePasswordShouldUpdateCredentials() {
        //GIVEN
        when(credentialsService.findByUserId(USER_ID)).thenReturn(credentials);

        ChangePasswordRequest request = new ChangePasswordRequest(NEW_PASSWORD, PASSWORD);

        when(passwordService.authenticate(PASSWORD, HASHED_PASSWORD)).thenReturn(true);
        when(passwordService.hashPassword(NEW_PASSWORD)).thenReturn(NEW_HASHED_PASSWORD);
        //WHEN
        underTest.changePassword(request, USER_ID);
        //THEN
        verify(credentialsService).findByUserId(USER_ID);
        verify(credentialsService).save(credentials);
        assertThat(credentials.getPassword()).isEqualTo(NEW_HASHED_PASSWORD);
    }
}
