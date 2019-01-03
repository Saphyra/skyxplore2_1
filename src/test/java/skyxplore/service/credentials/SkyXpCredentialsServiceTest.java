package skyxplore.service.credentials;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.CredentialsDao;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.exception.BadCredentialsException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.USER_NAME;
import static skyxplore.testutil.TestUtils.createCredentials;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCredentialsServiceTest {
    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private CredentialsService underTest;

    @Test(expected = BadCredentialsException.class)
    public void testGetByUserIdShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(null);
        //WHEN
        underTest.getByUserId(USER_ID);
    }

    @Test
    public void testGetByUserIdShouldCallDaoAndReturn() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsDao.getByUserId(USER_ID)).thenReturn(skyXpCredentials);
        //WHEN
        SkyXpCredentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsDao).getByUserId(USER_ID);
        assertEquals(skyXpCredentials, result);
    }

    @Test(expected = BadCredentialsException.class)
    public void testGetCredentialsByNameShouldThrowExceptionWhenNotFound() {
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(Optional.empty());
        //WHEN
        underTest.getCredentialsByName(USER_NAME);
    }

    @Test
    public void testGetCredentialsByNameShouldCallDaoAndReturn() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(Optional.of(skyXpCredentials));
        //WHEN
        SkyXpCredentials result = underTest.getCredentialsByName(USER_NAME);
        //THEN
        verify(credentialsDao).getCredentialsByName(USER_NAME);
        assertEquals(skyXpCredentials, result);
    }

    @Test
    public void testIsUserNameExistsShouldReturn() {
        //GIVEN
        when(credentialsDao.getCredentialsByName(USER_NAME)).thenReturn(Optional.of(createCredentials()));
        //WHEN
        boolean result = underTest.isUserNameExists(USER_NAME);
        //THEN
        assertTrue(result);
        verify(credentialsDao).getCredentialsByName(USER_NAME);
    }

    @Test
    public void testSaveShouldCallDao() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        //WHEN
        underTest.save(skyXpCredentials);
        //THEN
        verify(credentialsDao).save(skyXpCredentials);
    }
}