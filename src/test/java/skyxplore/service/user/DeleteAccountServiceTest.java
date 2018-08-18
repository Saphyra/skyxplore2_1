package skyxplore.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.AccountDeleteRequest;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.db.UserDao;
import skyxplore.exception.BadCredentialsException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccountServiceTest {
    @Mock
    private  CharacterDao characterDao;

    @Mock
    private  UserQueryService userQueryService;

    @Mock
    private  UserDao userDao;

    @InjectMocks
    private DeleteAccountService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testDeleteAccountShouldThrowExceptionWhenWrongPassword(){
        //GIVEN
        AccountDeleteRequest request  = createAccountDeleteRequest();
        request.setPassword(USER_FAKE_PASSWORD);

        when(userQueryService.getUserById(USER_ID)).thenReturn(createUser());
        //WHEN
        underTest.deleteAccount(request, USER_ID);
    }

    @Test
    public void testDeleteAccountShouldDelete(){
        //GIVEN
        AccountDeleteRequest request  = createAccountDeleteRequest();

        when(userQueryService.getUserById(USER_ID)).thenReturn(createUser());
        //WHEN
        underTest.deleteAccount(request, USER_ID);
        //THEN
        verify(userQueryService).getUserById(USER_ID);
        verify(characterDao).deleteByUserId(USER_ID);
        verify(userDao).delete(USER_ID);
    }
}
