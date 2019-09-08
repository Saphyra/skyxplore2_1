package com.github.saphyra.skyxplore.userdata.user.cache;

import com.github.saphyra.skyxplore.userdata.user.CredentialsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserNameCacheTest {
    private static final String USER_NAME = "user_name";

    @Mock
    private CredentialsService credentialsService;

    @InjectMocks
    private UserNameCache underTest;

    @Test
    public void get() {
        //GIVEN
        given(credentialsService.isUserNameExists(USER_NAME)).willReturn(true);
        //WHEN
        Optional<Boolean> result = underTest.get(USER_NAME);
        //THEN
        assertThat(result).contains(true);
    }
}