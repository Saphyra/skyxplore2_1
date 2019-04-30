package org.github.saphyra.skyxplore.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.user.domain.AccountDeleteRequest;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import com.github.saphyra.encryption.impl.PasswordService;
import skyxplore.exception.BadCredentialsException;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccountServiceTest {
    private static final String PASSWORD = "password";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String  HASHED_PASSWORD = "hashed_password";
    private static final SkyXpCredentials CREDENTIALS = new SkyXpCredentials(USER_ID, USER_NAME, HASHED_PASSWORD);

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private PasswordService passwordService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private DeleteAccountService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testDeleteAccountShouldThrowExceptionWhenWrongPassword() {
        //GIVEN
        AccountDeleteRequest request = new AccountDeleteRequest(FAKE_PASSWORD);
        request.setPassword(FAKE_PASSWORD);

        when(credentialsService.getByUserId(USER_ID)).thenReturn(CREDENTIALS);
        when(passwordService.authenticate(FAKE_PASSWORD, HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
    }

    @Test
    public void testDeleteAccountShouldDelete() {
        //GIVEN
        AccountDeleteRequest request = new AccountDeleteRequest(PASSWORD);
        when(credentialsService.getByUserId(USER_ID)).thenReturn(CREDENTIALS);
        when(passwordService.authenticate(PASSWORD, HASHED_PASSWORD)).thenReturn(true);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(passwordService).authenticate(PASSWORD, HASHED_PASSWORD);
        verify(credentialsService).getByUserId(USER_ID);

        ArgumentCaptor<AccountDeletedEvent> argumentCaptor = ArgumentCaptor.forClass(AccountDeletedEvent.class);
        verify(applicationEventPublisher).publishEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(USER_ID);
    }
}
