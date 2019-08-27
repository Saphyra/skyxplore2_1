package com.github.saphyra.skyxplore.userdata.settings.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor
public class UserSettings {
    @NonNull
    private final String userId;

    @NonNull
    private final UserSettingKey settingKey;
    private String value;
}
