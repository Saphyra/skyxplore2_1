package skyxplore.dataaccess.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.user.entity.Role;
import skyxplore.dataaccess.user.entity.GrantedRole;
import skyxplore.dataaccess.user.repository.RoleRepository;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class RoleDao {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init(){
        roleRepository.save(new GrantedRole(0L, Role.USER));
        roleRepository.save(new GrantedRole(1L, Role.ADMIN));
    }
}
