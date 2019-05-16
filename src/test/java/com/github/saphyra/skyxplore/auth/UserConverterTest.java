package com.github.saphyra.skyxplore.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.authservice.domain.User;
import com.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import com.github.saphyra.skyxplore.user.domain.SkyXpUser;
import com.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";

    @Mock
    private CredentialsDao credentialsDao;

    @Mock
    private SkyXpUser skyXpUser;

    @InjectMocks
    private UserConverter underTest;

    @Test
    public void testConvert() {
        //GIVEN
        given(skyXpUser.getUserId()).willReturn(USER_ID);

        SkyXpCredentials skyXpCredentials = SkyXpCredentials.builder()
            .userName(USER_NAME)
            .userId(USER_ID)
            .password(PASSWORD)
            .build();
        when(credentialsDao.findById(USER_ID)).thenReturn(Optional.of(skyXpCredentials));
        //WHEN
        User result = underTest.convertEntity(skyXpUser);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getCredentials().getUserName()).isEqualTo(USER_NAME);
        assertThat(result.getCredentials().getPassword()).isEqualTo(PASSWORD);
        assertThat(result.getRoles()).isEmpty();
    }
}