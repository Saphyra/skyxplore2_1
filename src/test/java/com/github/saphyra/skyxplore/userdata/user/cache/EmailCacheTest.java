package com.github.saphyra.skyxplore.userdata.user.cache;

import com.github.saphyra.skyxplore.userdata.user.UserQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EmailCacheTest {
    private static final String EMAIL = "email";
    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private EmailCache underTest;

    @Test
    public void get() {
        //GIVEN
        given(userQueryService.isEmailExists(EMAIL)).willReturn(true);
        //WHEN
        Optional<Boolean> result = underTest.get(EMAIL);
        //THEN
        assertThat(result).contains(true);
    }
}