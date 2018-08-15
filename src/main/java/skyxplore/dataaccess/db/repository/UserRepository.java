package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.domain.user.UserEntity;

@Repository
//TODO unit test
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String userName);
}
