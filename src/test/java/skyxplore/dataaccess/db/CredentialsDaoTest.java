package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.CredentialsRepository;
import skyxplore.domain.credentials.Credentials;
import skyxplore.domain.credentials.CredentialsConverter;
import skyxplore.domain.credentials.CredentialsEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class CredentialsDaoTest {
    @Mock
    private CredentialsConverter credentialsConverter;

    @Mock
    private CredentialsRepository credentialsRepository;

    @InjectMocks
    private CredentialsDao underTest;

    @Test
    public void testDeleteShouldCallRepository(){
        //WHEN
        underTest.deleteById(USER_ID);
        //THEN
        verify(credentialsRepository).deleteById(USER_ID);
    }

    @Test
    public void testGetByUserIdShouldReturnNull(){
        //GIVEN
        when(credentialsRepository.findById(USER_ID)).thenReturn(Optional.empty());
        //WHEN
        Credentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsRepository).findById(USER_ID);
        assertNull(result);
    }

    @Test
    public void testGetByUserIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        CredentialsEntity entity = createCredentialsEntity();
        when(credentialsRepository.findById(USER_ID)).thenReturn(Optional.of(entity));

        Credentials credentials = createCredentials();
        when(credentialsConverter.convertEntity(entity)).thenReturn(credentials);
        //WHEN
        Credentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsRepository).findById(USER_ID);
        verify(credentialsConverter).convertEntity(entity);
        assertEquals(credentials, result);
    }

    @Test
    public void testGetByNameShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        CredentialsEntity entity = createCredentialsEntity();
        when(credentialsRepository.getByUserName(USER_NAME)).thenReturn(entity);

        Credentials credentials = createCredentials();
        when(credentialsConverter.convertEntity(entity)).thenReturn(credentials);
        //WHEN
        Credentials result = underTest.getCredentialsByName(USER_NAME);
        //THEN
        verify(credentialsRepository).getByUserName(USER_NAME);
        verify(credentialsConverter).convertEntity(entity);
        assertEquals(credentials, result);
    }

    @Test
    public void testSaveShouldCallRepository(){
        //GIVEN
        Credentials credentials = createCredentials();

        CredentialsEntity entity = createCredentialsEntity();
        when(credentialsConverter.convertDomain(credentials)).thenReturn(entity);
        //WHEN
        underTest.save(credentials);
        //THEN
        verify(credentialsConverter).convertDomain(credentials);
        verify(credentialsRepository).save(entity);
    }
}
