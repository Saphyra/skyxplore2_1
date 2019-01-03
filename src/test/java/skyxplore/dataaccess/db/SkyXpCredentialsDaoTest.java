package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.CredentialsRepository;
import skyxplore.domain.credentials.SkyXpCredentials;
import skyxplore.domain.credentials.CredentialsConverter;
import skyxplore.domain.credentials.CredentialsEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCredentialsDaoTest {
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
        SkyXpCredentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsRepository).findById(USER_ID);
        assertNull(result);
    }

    @Test
    public void testGetByUserIdShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        CredentialsEntity entity = createCredentialsEntity();
        when(credentialsRepository.findById(USER_ID)).thenReturn(Optional.of(entity));

        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsConverter.convertEntity(entity)).thenReturn(skyXpCredentials);
        //WHEN
        SkyXpCredentials result = underTest.getByUserId(USER_ID);
        //THEN
        verify(credentialsRepository).findById(USER_ID);
        verify(credentialsConverter).convertEntity(entity);
        assertEquals(skyXpCredentials, result);
    }

    @Test
    public void testGetByNameShouldCallRepositoryAndReturnDomain(){
        //GIVEN
        CredentialsEntity entity = createCredentialsEntity();
        when(credentialsRepository.getByUserName(USER_NAME)).thenReturn(entity);

        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsConverter.convertEntityToOptional(entity)).thenReturn(Optional.of(skyXpCredentials));
        //WHEN
        Optional<SkyXpCredentials> result = underTest.getCredentialsByName(USER_NAME);
        //THEN
        verify(credentialsRepository).getByUserName(USER_NAME);
        verify(credentialsConverter).convertEntityToOptional(entity);
        assertTrue(result.isPresent());
        assertEquals(skyXpCredentials, result.get());
    }

    @Test
    public void testSaveShouldCallRepository(){
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();

        CredentialsEntity entity = createCredentialsEntity();
        when(credentialsConverter.convertDomain(skyXpCredentials)).thenReturn(entity);
        //WHEN
        underTest.save(skyXpCredentials);
        //THEN
        verify(credentialsConverter).convertDomain(skyXpCredentials);
        verify(credentialsRepository).save(entity);
    }
}
