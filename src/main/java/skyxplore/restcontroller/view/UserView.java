package skyxplore.restcontroller.view;

import lombok.Data;

import java.util.Set;

@Data
public class UserView {
    private Long userId;
    private String userName;
    private String email;
    private Set<RoleView> roles;
}
