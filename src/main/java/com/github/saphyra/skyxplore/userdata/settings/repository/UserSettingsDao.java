package com.github.saphyra.skyxplore.userdata.settings.repository;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import org.springframework.stereotype.Component;

@Component
public class UserSettingsDao extends AbstractDao<UserSettingsEntity, UserSettings, UserSettingsKeyEntity, UserSettingsRepository> {
    public UserSettingsDao(
        UserSettingsConverter converter,
        UserSettingsRepository repository
    ) {
        super(converter, repository);
    }
}
