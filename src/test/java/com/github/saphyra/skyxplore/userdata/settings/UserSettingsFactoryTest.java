package com.github.saphyra.skyxplore.userdata.settings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;

@RunWith(MockitoJUnitRunner.class)
public class UserSettingsFactoryTest {
    private static final String USER_ID = "user_id";
    private static final String VALUE = "value";

    @InjectMocks
    private UserSettingsFactory underTest;

    @Test
    public void create() {
        //WHEN
        UserSettings result = underTest.create(USER_ID, UserSettingKey.LOCALE, VALUE);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getSettingKey()).isEqualTo(UserSettingKey.LOCALE);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }
}