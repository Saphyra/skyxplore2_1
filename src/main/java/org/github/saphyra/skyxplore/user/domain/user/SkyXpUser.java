package org.github.saphyra.skyxplore.user.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkyXpUser {
    private String userId;
    private String email;
    private HashSet<Role> roles;
}
