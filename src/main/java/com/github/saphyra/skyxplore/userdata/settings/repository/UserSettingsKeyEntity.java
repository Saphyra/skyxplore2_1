package com.github.saphyra.skyxplore.userdata.settings.repository;

import com.github.saphyra.skyxplore.userdata.settings.domain.UserSettingKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
class UserSettingsKeyEntity implements Serializable {
    private String uid;
    private UserSettingKey settingKey;
}
