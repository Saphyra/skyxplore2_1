package skyxplore.home.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skyxplore.home.dataaccess.user.entity.Role;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkyXpUser {
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
