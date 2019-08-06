package com.github.saphyra.skyxplore.userdata.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SkyXpCredentials {
    @NonNull
    private final String userId;

    @NonNull
    private String userName;

    @NonNull
    private String password;
}
