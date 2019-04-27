package org.github.saphyra.skyxplore.user.repository.credentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//TODO unit test
interface CredentialsRepository extends JpaRepository<CredentialsEntity, String> {
    CredentialsEntity getByUserName(String userName);
}
