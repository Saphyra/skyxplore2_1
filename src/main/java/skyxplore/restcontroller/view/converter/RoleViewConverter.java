package skyxplore.restcontroller.view.converter;

import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.entity.Role;
import skyxplore.restcontroller.view.RoleView;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleViewConverter {
    public Set<RoleView> convertDomain(Set<Role> roles){
        return roles.stream().map(this::convertDomain).collect(Collectors.toSet());
    }

    public RoleView convertDomain(Role role){
        return new RoleView(role.name());
    }
}
