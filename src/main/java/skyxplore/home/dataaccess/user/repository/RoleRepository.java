package skyxplore.home.dataaccess.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skyxplore.home.dataaccess.user.entity.GrantedRole;

public interface RoleRepository extends JpaRepository<GrantedRole, Long> {
}
