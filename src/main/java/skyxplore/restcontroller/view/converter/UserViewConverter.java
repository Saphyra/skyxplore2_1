package skyxplore.restcontroller.view.converter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.service.domain.SkyXpUser;
import skyxplore.restcontroller.view.UserView;

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
