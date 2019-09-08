package com.github.saphyra.skyxplore.userdata.settings.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;

@RunWith(MockitoJUnitRunner.class)
public class UserSettingsConverterTest {
    private static final String VALUE = "value";
    private static final String USER_ID = "user-id";

    @InjectMocks
    private UserSettingsConverter underTest;

    @Test
    public void processEntityConversion() {
        //GIVEN
        UserSettingsEntity entity = UserSettingsEntity.builder()
            .userSettingsKey(
                UserSettingsKeyEntity.builder()
                    .settingKey(UserSettingKey.LOCALE)
                    .userId(USER_ID)
                    .build()
            )
            .value(VALUE)
            .build();
        //WHEN
        UserSettings result = underTest.processEntityConversion(entity);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getValue()).isEqualTo(VALUE);
        assertThat(result.getSettingKey()).isEqualTo(UserSettingKey.LOCALE);
    }

    @Test
    public void processDomainConversion() {
        //GIVEN
        UserSettings domain = UserSettings.builder()
            .value(VALUE)
            .settingKey(UserSettingKey.LOCALE)
            .userId(USER_ID)
            .build();
        //WHEN
        UserSettingsEntity result = underTest.processDomainConversion(domain);
        //THEN
        assertThat(result.getValue()).isEqualTo(VALUE);
        assertThat(result.getUserSettingsKey().getUserId()).isEqualTo(USER_ID);
        assertThat(result.getUserSettingsKey().getSettingKey()).isEqualTo(UserSettingKey.LOCALE);
    }
}