package org.github.saphyra.skyxplore.user;

import com.github.saphyra.encryption.impl.PasswordService;
import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import skyxplore.controller.request.user.AccountDeleteRequest;
import skyxplore.exception.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CREDENTIALS_HASHED_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;
import static skyxplore.testutil.TestUtils.createAccountDeleteRequest;
import static skyxplore.testutil.TestUtils.createCredentials;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccountServiceTest {
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
        AccountDeleteRequest request = createAccountDeleteRequest();
        request.setPassword(USER_FAKE_PASSWORD);

        when(credentialsService.getByUserId(USER_ID)).thenReturn(createCredentials());
        when(passwordService.authenticate(USER_FAKE_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(false);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
    }

    @Test
    public void testDeleteAccountShouldDelete() {
        //GIVEN
        AccountDeleteRequest request = createAccountDeleteRequest();
        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        when(passwordService.authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD)).thenReturn(true);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(passwordService).authenticate(USER_PASSWORD, CREDENTIALS_HASHED_PASSWORD);
        verify(credentialsService).getByUserId(USER_ID);

        ArgumentCaptor<AccountDeletedEvent> argumentCaptor = ArgumentCaptor.forClass(AccountDeletedEvent.class);
        verify(applicationEventPublisher).publishEvent(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(USER_ID);
    }
}
