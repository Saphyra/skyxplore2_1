package com.github.saphyra.skyxplore.auth.domain;

import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SkyXpAccessToken {
    private final String accessTokenId;
    private final String userId;
    private OffsetDateTime lastAccess;
    private String characterId;
}
