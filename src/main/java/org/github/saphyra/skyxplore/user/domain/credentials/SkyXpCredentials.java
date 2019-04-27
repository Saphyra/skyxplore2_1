package org.github.saphyra.skyxplore.user.domain.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkyXpCredentials {
    private String userId;
    private String userName;
    private String password;
}
