package com.github.saphyra.skyxplore.userdata.settings.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettings;

@RunWith(MockitoJUnitRunner.class)
public class UserSettingsDaoTest {
    private static final String USER_ID = "user-id";

    @Mock
    private UserSettingsRepository userSettingsRepository;

    @Mock
    private UserSettingsConverter userSettingsConverter;

    @InjectMocks
    private UserSettingsDao underTest;

    @Mock
    private UserSettings userSettings;

    @Mock
    private UserSettingsEntity userSettingsEntity;

    @Test
    public void findByUserIdAndUserSettingKey() {
        //GIVEN
        given(userSettingsRepository.findById(any(UserSettingsKeyEntity.class))).willReturn(Optional.of(userSettingsEntity));
        given(userSettingsConverter.convertEntity(userSettingsEntity)).willReturn(userSettings);
        //WHEN
        Optional<UserSettings> result = underTest.findByUserIdAndUserSettingKey(USER_ID, UserSettingKey.LOCALE);
        //THEN
        ArgumentCaptor<UserSettingsKeyEntity> argumentCaptor = ArgumentCaptor.forClass(UserSettingsKeyEntity.class);
        verify(userSettingsRepository).findById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(USER_ID);
        assertThat(argumentCaptor.getValue().getSettingKey()).isEqualTo(UserSettingKey.LOCALE);

        assertThat(result).contains(userSettings);
    }
}