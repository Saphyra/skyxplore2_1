package skyxplore.home.dataaccess.converter;

import org.springframework.stereotype.Component;
import skyxplore.home.dataaccess.user.entity.GrantedRole;
import skyxplore.home.dataaccess.user.entity.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleConverter {

    public Set<Role> convertEntity(Set<GrantedRole> entities){
        return entities.stream().map(this::convertEntity).collect(Collectors.toSet());
    }

    public Role convertEntity(GrantedRole entity){
        return entity.getRole();
    }
}
