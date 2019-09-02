package com.github.saphyra.skyxplore.userdata.settings.locale;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import com.github.saphyra.skyxplore.userdata.settings.repository.UserSettingsDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
class LocaleService {
    private final UserSettingsDao userSettingsDao;
    private final LocaleCache localeCache;

    //TODO unit test
    void setLocale(String userId, String locale) {
        UserSettings userSettings = getOrCreate(userId);
        userSettings.setValue(locale);
        userSettingsDao.save(userSettings);
        localeCache.invalidate(userId);
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
