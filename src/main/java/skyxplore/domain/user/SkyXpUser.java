package skyxplore.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkyXpUser {
    public SkyXpUser(String userName, String password, String email, HashSet<Role> roles){
        this.username = userName;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    private String userId;
    private String username;
    private String password;
    private String email;
    private HashSet<Role> roles;
}
