package org.github.saphyra.skyxplore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.github.saphyra.skyxplore.user.domain.user.UserEntity;

@Repository
//TODO unit test
interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
}
