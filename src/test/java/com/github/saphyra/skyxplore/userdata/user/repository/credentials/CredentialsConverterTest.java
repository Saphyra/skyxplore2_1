package com.github.saphyra.skyxplore.userdata.user.repository.credentials;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.user.domain.SkyXpCredentials;


@RunWith(MockitoJUnitRunner.class)
public class CredentialsConverterTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";

    @InjectMocks
    private CredentialsConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        CredentialsEntity entity = null;
        //WHEN
        SkyXpCredentials result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void tstConvertEntityShouldConvert() {
        //GIVEN
        CredentialsEntity entity = createCredentialsEntity();
        //WHEN
        SkyXpCredentials result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getUserName()).isEqualTo(USER_NAME);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        SkyXpCredentials skyXpCredentials = createCredentials();
        //WHEN
        CredentialsEntity result = underTest.convertDomain(skyXpCredentials);
        //THEN
        assertThat(result.getUserName()).isEqualTo(USER_NAME);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
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
            .password(PASSWORD)
            .userId(USER_ID)
            .userName(USER_NAME)
            .build();
    }
}
