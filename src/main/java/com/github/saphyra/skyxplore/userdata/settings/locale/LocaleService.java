package com.github.saphyra.skyxplore.userdata.settings.locale;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import com.github.saphyra.skyxplore.userdata.settings.repository.UserSettingsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Component
class LocaleService {
    private final UserSettingsDao userSettingsDao;

    //TODO unit test
    Optional<String> getLocale(String userId) {
        return userSettingsDao.findByUserIdAndUserSettingKey(userId, UserSettingKey.LOCALE)
            .map(UserSettings::getValue);
    }

    //TODO unit test
    void setLocale(String userId, String locale) {
        UserSettings userSettings = getOrCreate(userId);
        userSettings.setValue(locale);
        userSettingsDao.save(userSettings);
    }

    private UserSettings getOrCreate(String userId) {
        return userSettingsDao.findByUserIdAndUserSettingKey(userId, UserSettingKey.LOCALE)
            .orElseGet(() -> UserSettings.builder()
                .userId(userId)
                .settingKey(UserSettingKey.LOCALE)
                .build()
            );
    }
}
