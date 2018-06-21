package skyxplore.restcontroller.view.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserView {
    private String userId;
    private String userName;
    private String email;
    private Set<RoleView> roles;
}
