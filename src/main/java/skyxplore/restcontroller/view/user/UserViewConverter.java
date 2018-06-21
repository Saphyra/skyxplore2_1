package skyxplore.restcontroller.view.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.user.SkyXpUser;

@Component
@AllArgsConstructor
public class UserViewConverter {
    private final RoleViewConverter roleViewConverter;

    public UserView convertDomain(SkyXpUser domain){
        UserView view = new UserView();
        view.setUserId(domain.getUserId());
        view.setUserName(domain.getUsername());
        view.setEmail(domain.getEmail());
        view.setRoles(roleViewConverter.convertDomain(domain.getRoles()));
        return view;
    }
}
