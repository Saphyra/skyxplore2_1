package skyxplore.service.credentials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.createCredentials;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.domain.credentials.Credentials;
import skyxplore.exception.BadCredentialsException;

@RunWith(MockitoJUnitRunner.class)
public class CredentialsServiceTest {
    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private CredentialsService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testGetByUserIdShouldThrowExceptionWhenNotFound(){
        //GIVEN
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(null);
        //WHEN
        underTest.getByUserId(USER_ID);
    }

    @Test
    public void testGetByUserIdShouldCallDaoAndReturn(){
        //GIVEN
        Credentials credentials = createCredentials();
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(credentials);
        //WHEN
        Credentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsDao).getByUserId(USER_ID);
        assertEquals(credentials, result);
    }

    @Test(expected = BadCredentialsException.class)
    public void testGetCredentialsByNameShouldThrowExceptionWhenNotFound(){
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(null);
        //WHEN
        underTest.getCredentialsByName(USER_NAME);
    }

    @Test
    public void testGetCredentialsByNameShouldCallDaoAndReturn(){
        //GIVEN
        Credentials credentials = createCredentials();
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(credentials);
        //WHEN
        Credentials result = underTest.getCredentialsByName(USER_NAME);
        //THEN
        verify(credentialsDao).getCredentialsByName(USER_NAME);
        assertEquals(credentials, result);
    }

    @Test
    public void testIsUserNameExistsShouldReturn(){
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(createCredentials());
        //WHEN
        Boolean result = underTest.isUserNameExists(USER_NAME);
        //THEN
        assertTrue(result);
        verify(credentialsDao).getCredentialsByName(USER_NAME);
    }

    @Test
    public void testSaveShouldCallDao(){
        //GIVEN
        Credentials credentials = createCredentials();
        //WHEN
        underTest.save(credentials);
        //THEN
        verify(credentialsDao).save(credentials);
    }
}