package com.github.saphyra.skyxplore.userdata.settings.locale;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import com.github.saphyra.skyxplore.userdata.settings.repository.UserSettingsDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class LocaleQueryService {
    private final UserSettingsDao userSettingsDao;

    //TODO unit test
    Optional<String> getLocale(String userId) {
        return userSettingsDao.findByUserIdAndUserSettingKey(userId, UserSettingKey.LOCALE)
            .map(UserSettings::getValue);
    }
}
