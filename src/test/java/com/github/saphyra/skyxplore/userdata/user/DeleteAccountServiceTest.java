package com.github.saphyra.skyxplore.userdata.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.skyxplore.common.exception.BadCredentialsException;
import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;
import com.github.saphyra.skyxplore.userdata.user.domain.AccountDeleteRequest;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccountServiceTest {
    private static final String PASSWORD = "password";
    private static final String FAKE_PASSWORD = "fake_password";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String HASHED_PASSWORD = "hashed_password";
    private static final SkyXpCredentials CREDENTIALS = SkyXpCredentials.builder()
        .userName(USER_NAME)
        .userId(USER_ID)
        .password(HASHED_PASSWORD)
        .build();

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

        when(credentialsService.findByUserId(USER_ID)).thenReturn(CREDENTIALS);
        when(passwordService.authenticate(FAKE_PASSWORD, HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
    }

    @Test
    public void testDeleteAccountShouldDelete() {
        //GIVEN
        AccountDeleteRequest request = new AccountDeleteRequest(PASSWORD);
        when(credentialsService.findByUserId(USER_ID)).thenReturn(CREDENTIALS);
        when(passwordService.authenticate(PASSWORD, HASHED_PASSWORD)).thenReturn(true);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(passwordService).authenticate(PASSWORD, HASHED_PASSWORD);
        verify(credentialsService).findByUserId(USER_ID);

        ArgumentCaptor<AccountDeletedEvent> argumentCaptor = ArgumentCaptor.forClass(AccountDeletedEvent.class);
        verify(applicationEventPublisher).publishEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(USER_ID);
    }
}
