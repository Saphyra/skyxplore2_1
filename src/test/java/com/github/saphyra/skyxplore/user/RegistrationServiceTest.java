package com.github.saphyra.skyxplore.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.saphyra.skyxplore.user.domain.Role;
import com.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.user.domain.UserRegistrationRequest;
import com.github.saphyra.skyxplore.user.repository.user.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.skyxplore.common.exception.EmailAlreadyExistsException;
import com.github.saphyra.skyxplore.common.exception.UserNameAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final UserRegistrationRequest REGISTRATION_REQUEST = new UserRegistrationRequest(USER_NAME, PASSWORD, EMAIL);
    private static final String USER_ID = "user_id";
    private static final String HASHED_PASSWORD = "hashed_password";

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserDao userDao;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private RegistrationService underTest;

    @Test(expected = UserNameAlreadyExistsException.class)
    public void testRegistrateUserShouldThrowExceptionWhenUserNameExists() {
        //GIVEN
        when(credentialsService.isUserNameExists(USER_NAME)).thenReturn(true);
        //WHEN
        underTest.registerUser(REGISTRATION_REQUEST);
    }

    @Test(expected = EmailAlreadyExistsException.class)
    public void testRegistrateUserShouldThrowExceptionWhenEmailExists() {
        //GIVEN
        when(credentialsService.isUserNameExists(USER_NAME)).thenReturn(false);
        when(userQueryService.isEmailExists(EMAIL)).thenReturn(true);
        //WHEN
        underTest.registerUser(REGISTRATION_REQUEST);
    }

    @Test
    public void testRegistrateUserShouldSave() {
        //GIVEN
        when(credentialsService.isUserNameExists(USER_NAME)).thenReturn(false);
        when(userQueryService.isEmailExists(EMAIL)).thenReturn(false);
        when(idGenerator.generateRandomId()).thenReturn(USER_ID);
        when(passwordService.hashPassword(PASSWORD)).thenReturn(HASHED_PASSWORD);
        //WHEN
        underTest.registerUser(REGISTRATION_REQUEST);
        //THEN
        ArgumentCaptor<SkyXpUser> userCaptor = ArgumentCaptor.forClass(SkyXpUser.class);
        verify(userDao).save(userCaptor.capture());

        assertThat(userCaptor.getValue().getUserId()).isEqualTo(USER_ID);
        assertThat(userCaptor.getValue().getEmail()).isEqualTo(EMAIL);
        assertThat(userCaptor.getValue().getRoles()).containsExactly(Role.USER);

        ArgumentCaptor<SkyXpCredentials> credentialsCaptor = ArgumentCaptor.forClass(SkyXpCredentials.class);
        verify(credentialsService).save(credentialsCaptor.capture());
        assertThat(credentialsCaptor.getValue().getPassword()).isEqualTo(HASHED_PASSWORD);
        assertThat(credentialsCaptor.getValue().getUserName()).isEqualTo(USER_NAME);
    }
}
