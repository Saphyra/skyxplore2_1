package com.github.saphyra.skyxplore.userdata.settings.repository;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;

@Component
class UserSettingsConverter extends ConverterBase<UserSettingsEntity, UserSettings> {
    @Override
    protected UserSettings processEntityConversion(UserSettingsEntity userSettingsEntity) {
        return UserSettings.builder()
            .userId(userSettingsEntity.getUserSettingsKey().getUserId())
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
