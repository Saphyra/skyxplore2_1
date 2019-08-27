package com.github.saphyra.skyxplore.userdata.settings.repository;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import org.springframework.stereotype.Component;

@Component
//TODO unit test
class UserSettingsConverter extends ConverterBase<UserSettingsEntity, UserSettings> {
    @Override
    protected UserSettings processEntityConversion(UserSettingsEntity userSettingsEntity) {
        return UserSettings.builder()
            .userId(userSettingsEntity.getUserSettingsKey().getUid())
            .settingKey(userSettingsEntity.getUserSettingsKey().getSettingKey())
            .value(userSettingsEntity.getValue())
            .build();
    }

    @Override
    protected UserSettingsEntity processDomainConversion(UserSettings userSettings) {
        return UserSettingsEntity.builder()
            .userSettingsKey(new UserSettingsKeyEntity(userSettings.getUserId(), userSettings.getSettingKey()))
            .value(userSettings.getValue())
            .build();
    }
}
