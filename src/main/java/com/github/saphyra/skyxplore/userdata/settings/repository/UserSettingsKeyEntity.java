package com.github.saphyra.skyxplore.userdata.settings.repository;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
class UserSettingsKeyEntity {
    private String uid;
    private UserSettingKey settingKey;
}
