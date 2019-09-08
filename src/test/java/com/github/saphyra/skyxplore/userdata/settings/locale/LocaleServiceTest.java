package com.github.saphyra.skyxplore.userdata.settings.locale;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.settings.UserSettingsFactory;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;
import com.github.saphyra.skyxplore.userdata.settings.repository.UserSettingsDao;

@RunWith(MockitoJUnitRunner.class)
public class LocaleServiceTest {
    private static final String USER_ID = "user_id";
    private static final String LOCALE = "locale";

    @Mock
    private LocaleCache localeCache;

    @Mock
    private UserSettingsDao userSettingsDao;

    @Mock
    private UserSettingsFactory userSettingsFactory;

    @InjectMocks
    private LocaleService underTest;

    @Mock
    private UserSettings userSettings;

    @Test
    public void setLocale() {
        //GIVEN
        given(userSettingsDao.findByUserIdAndUserSettingKey(USER_ID, UserSettingKey.LOCALE)).willReturn(Optional.of(userSettings));
        //WHEN
        underTest.setLocale(USER_ID, LOCALE);
        //THEN
        verify(userSettings).setValue(LOCALE);
        verify(userSettingsDao).save(userSettings);
        verify(localeCache).invalidate(USER_ID);
        verifyZeroInteractions(userSettingsFactory);
    }

    @Test
    public void setLocale_storedSettingsNotFound() {
        //GIVEN
        given(userSettingsDao.findByUserIdAndUserSettingKey(USER_ID, UserSettingKey.LOCALE)).willReturn(Optional.empty());
        given(userSettingsFactory.create(USER_ID, UserSettingKey.LOCALE)).willReturn(userSettings);
        //WHEN
        underTest.setLocale(USER_ID, LOCALE);
        //THEN
        verify(userSettings).setValue(LOCALE);
        verify(userSettingsDao).save(userSettings);
        verify(localeCache).invalidate(USER_ID);
    }
}