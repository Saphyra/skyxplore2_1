package skyxplore.service.user;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_FAKE_PASSWORD;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createAccountDeleteRequest;
import static skyxplore.testutil.TestUtils.createCredentials;
import static skyxplore.testutil.TestUtils.createUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.user.AccountDeleteRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.exception.BadCredentialsException;
import skyxplore.service.credentials.CredentialsService;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccountServiceTest {
    @Mock
    private CharacterDao characterDao;

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private UserQueryService userQueryService;

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
        //WHEN
        underTest.deleteAccount(request, USER_ID);
    }

    @Test
    public void testDeleteAccountShouldDelete() {
        //GIVEN
        AccountDeleteRequest request = createAccountDeleteRequest();
        Credentials credentials = createCredentials();
        when(credentialsService.getByUserId(USER_ID)).thenReturn(credentials);
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(credentialsService).getByUserId(USER_ID);
        verify(characterDao).deleteByUserId(USER_ID);
        verify(userDao).delete(USER_ID);
    }
}
