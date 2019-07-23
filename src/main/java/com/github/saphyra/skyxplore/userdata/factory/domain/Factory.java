package com.github.saphyra.skyxplore.userdata.factory.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Factory {
    @NonNull
    private final String factoryId;

    @NonNull
    private final String characterId;

    @NonNull
    private final Materials materials;
}
