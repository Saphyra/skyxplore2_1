package com.github.saphyra.skyxplore.userdata.settings;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;

@Component
public class UserSettingsFactory {
    public UserSettings create(String userId, UserSettingKey key) {
        return create(userId, key, null);
    }

    public UserSettings create(String userId, UserSettingKey key, String value) {
        return UserSettings.builder()
            .userId(userId)
            .settingKey(key)
            .value(value)
            .build();
    }
}
