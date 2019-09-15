package com.github.saphyra.skyxplore.userdata.settings.repository;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
class UserSettingsKeyEntity implements Serializable {
    private String userId;

    @Enumerated(EnumType.STRING)
    private UserSettingKey settingKey;
}
