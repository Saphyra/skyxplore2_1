package org.github.saphyra.skyxplore.user.repository.credentials;

import org.github.saphyra.skyxplore.event.AccountDeletedEvent;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkyXpCredentialsDaoTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";

    @Mock
    private CredentialsConverter credentialsConverter;

    @Mock
    private CredentialsRepository credentialsRepository;

    @InjectMocks
    private CredentialsDao underTest;

    @Test
    public void accountDeletedEventListener(){
        //WHEN
        underTest.accountDeletedEventListener(new AccountDeletedEvent(USER_ID));
        //THEN
        verify(credentialsRepository).deleteById(USER_ID);
    }

    @Test
    public void testDeleteShouldCallRepository() {
        //WHEN
        underTest.deleteById(USER_ID);
        //THEN
        verify(credentialsRepository).deleteById(USER_ID);
    }

    @Test
    public void testFindByNameShouldCallRepositoryAndReturnDomain() {
        //GIVEN
        Optional<CredentialsEntity> entity = Optional.of(createCredentialsEntity());
        when(credentialsRepository.findByUserName(USER_NAME)).thenReturn(entity);

        SkyXpCredentials skyXpCredentials = createCredentials();
        when(credentialsConverter.convertEntity(entity)).thenReturn(Optional.of(skyXpCredentials));
        //WHEN
        Optional<SkyXpCredentials> result = underTest.findByName(USER_NAME);
        //THEN
        assertThat(result).contains(skyXpCredentials);
    }

    @Test
    public void testSaveShouldCallRepository() {
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

    private CredentialsEntity createCredentialsEntity() {
        CredentialsEntity entity = new CredentialsEntity();
        entity.setUserId(USER_ID);
        entity.setPassword(PASSWORD);
        entity.setUserName(USER_NAME);
        return entity;
    }

    private SkyXpCredentials createCredentials() {
        SkyXpCredentials credentials = new SkyXpCredentials();
        credentials.setUserId(USER_ID);
        credentials.setPassword(PASSWORD);
        credentials.setUserName(USER_NAME);
        return credentials;
    }
}
