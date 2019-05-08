package com.github.saphyra.skyxplore.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class SkyXpUser {
    private String userId;
    private String email;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
