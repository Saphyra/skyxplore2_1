package skyxplore.dataaccess.user.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.user.entity.GrantedRole;
import skyxplore.dataaccess.user.entity.Role;
import skyxplore.dataaccess.user.repository.RoleRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleConverter {
    private final RoleRepository roleRepository;

    public Set<Role> convertEntity(Set<GrantedRole> entities){
        return entities.stream().map(this::convertEntity).collect(Collectors.toSet());
    }

    public Role convertEntity(GrantedRole entity){
        return entity.getRole();
    }

    public Set<GrantedRole> convertDomain(Set<Role> roles){
        return roles.stream().map(this::convertDomain).collect(Collectors.toSet());
    }

    public GrantedRole convertDomain(Role role){
        return roleRepository.getRoleByRole(role);
    }
}
