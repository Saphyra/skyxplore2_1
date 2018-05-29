package skyxplore.dataaccess.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.dataaccess.user.entity.GrantedRole;
import skyxplore.dataaccess.user.entity.Role;

public interface RoleRepository extends JpaRepository<GrantedRole, Long> {
    GrantedRole getRoleByRole(Role role);
}
