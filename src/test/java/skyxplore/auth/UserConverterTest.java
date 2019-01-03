package skyxplore.auth;

import com.github.saphyra.authservice.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.domain.user.SkyXpUser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.USER_PASSWORD;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {
    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private UserConverter underTest;

    @Test
    public void testConvert(){
        //GIVEN
        SkyXpUser skyXpUser = new SkyXpUser();
        skyXpUser.setUserId(USER_ID);

        SkyXpCredentials skyXpCredentials = new SkyXpCredentials(USER_ID, USER_NAME, USER_PASSWORD);
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        //WHEN
        User result = underTest.convertEntity(skyXpUser);
        //THEN
        assertEquals(USER_ID, result.getUserId());
        assertEquals(USER_NAME, result.getCredentials().getUserName());
        assertEquals(USER_PASSWORD, result.getCredentials().getPassword());
        assertTrue(result.getRoles().isEmpty());
    }
}