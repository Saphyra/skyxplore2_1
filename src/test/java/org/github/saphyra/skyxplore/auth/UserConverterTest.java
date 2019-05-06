package org.github.saphyra.skyxplore.auth;

import com.github.saphyra.authservice.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.user.repository.credentials.CredentialsDao;
import org.github.saphyra.skyxplore.user.domain.SkyXpCredentials;
import org.github.saphyra.skyxplore.user.domain.SkyXpUser;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";

    @Mock
    private CredentialsDao credentialsDao;

    @InjectMocks
    private UserConverter underTest;

    @Test
    public void testConvert() {
        //GIVEN
        SkyXpUser skyXpUser = SkyXpUser.builder()
            .userId(USER_ID)
            .build();

        SkyXpCredentials skyXpCredentials = new SkyXpCredentials(USER_ID, USER_NAME, PASSWORD);
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