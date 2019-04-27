package org.github.saphyra.skyxplore.user.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//TODO unit test
interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
}
