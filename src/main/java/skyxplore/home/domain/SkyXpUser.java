package skyxplore.home.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skyxplore.dataaccess.user.entity.Role;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkyXpUser {
    public SkyXpUser(String userName, String password, String email, Set<Role> roles){
        this.username = userName;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    private Long userId;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
