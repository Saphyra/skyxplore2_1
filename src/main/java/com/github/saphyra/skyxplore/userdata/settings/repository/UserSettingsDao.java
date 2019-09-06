package com.github.saphyra.skyxplore.userdata.settings.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;

@Component
public class UserSettingsDao extends AbstractDao<UserSettingsEntity, UserSettings, UserSettingsKeyEntity, UserSettingsRepository> {
    public UserSettingsDao(
        UserSettingsConverter converter,
        UserSettingsRepository repository
    ) {
        super(converter, repository);
    }

    public Optional<UserSettings> findByUserIdAndUserSettingKey(String userId, UserSettingKey settingKey) {
        UserSettingsKeyEntity key = new UserSettingsKeyEntity(userId, settingKey);
        return findById(key);
    }
}
