package com.github.saphyra.skyxplore.userdata.user.repository.credentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.event.AccountDeletedEvent;
import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;

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
        return SkyXpCredentials.builder()
            .userId(USER_ID)
            .userName(USER_NAME)
            .password(PASSWORD)
            .build();
    }
}
