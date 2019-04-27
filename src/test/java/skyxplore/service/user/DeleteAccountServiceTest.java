package skyxplore.service.user;

import com.github.saphyra.encryption.impl.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.user.AccountDeleteRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.service.credentials.CredentialsService;

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
    private CharacterDao characterDao;

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserDao userDao;

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
        verify(characterDao).deleteByUserId(USER_ID);
        verify(userDao).delete(USER_ID);
    }
}
