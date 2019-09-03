package com.github.saphyra.skyxplore.userdata.settings.locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import com.github.saphyra.skyxplore.userdata.settings.repository.UserSettingsDao;

@RunWith(MockitoJUnitRunner.class)
public class LocaleQueryServiceTest {
    private static final String USER_ID = "user_id";
    private static final String LOCALE = "locale";

    @Mock
    private UserSettingsDao userSettingsDao;

    @InjectMocks
    private LocaleQueryService underTest;

    @Mock
    private UserSettings userSettings;

    @Test
    public void getLocale() {
        //GIVEN
        given(userSettingsDao.findByUserIdAndUserSettingKey(USER_ID, UserSettingKey.LOCALE)).willReturn(Optional.of(userSettings));
        given(userSettings.getValue()).willReturn(LOCALE);
        //WHEN
        Optional<String> result = underTest.getLocale(USER_ID);
        //THEN
        assertThat(result).contains(LOCALE);
    }

    @Test
    public void getLocale_notFound() {
        //GIVEN
        given(userSettingsDao.findByUserIdAndUserSettingKey(USER_ID, UserSettingKey.LOCALE)).willReturn(Optional.empty());
        //WHEN
        Optional<String> result = underTest.getLocale(USER_ID);
        //THEN
        assertThat(result).isEmpty();
    }
}