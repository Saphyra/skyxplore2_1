package com.github.saphyra.skyxplore.userdata.settings.repository;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
class UserSettingsKeyEntity implements Serializable {
    private String userId;
    private UserSettingKey settingKey;
}
