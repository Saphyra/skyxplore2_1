package com.github.saphyra.skyxplore.user.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class SkyXpUser {
    @NonNull
    private final String userId;

    @NonNull
    private String email;

    @NonNull
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
