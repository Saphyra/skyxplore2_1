package com.github.saphyra.skyxplore.userdata.settings.repository;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSettingsDao extends AbstractDao<UserSettingsEntity, UserSettings, UserSettingsKeyEntity, UserSettingsRepository> {
    public UserSettingsDao(
        UserSettingsConverter converter,
        UserSettingsRepository repository
    ) {
        super(converter, repository);
    }

    //TODO unit test
    public Optional<UserSettings> findByUserIdAndUserSettingKey(String userId, UserSettingKey settingKey) {
        return findById(new UserSettingsKeyEntity(userId, settingKey));
    }
}
