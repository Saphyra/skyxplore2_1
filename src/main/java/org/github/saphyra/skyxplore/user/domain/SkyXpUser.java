package org.github.saphyra.skyxplore.user.domain;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkyXpUser {
    private String userId;
    private String email;
    private Set<Role> roles;
}
