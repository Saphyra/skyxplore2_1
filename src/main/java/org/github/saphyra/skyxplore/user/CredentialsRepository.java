package org.github.saphyra.skyxplore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.github.saphyra.skyxplore.user.domain.credentials.CredentialsEntity;

@Repository
//TODO unit test
interface CredentialsRepository extends JpaRepository<CredentialsEntity, String> {
    CredentialsEntity getByUserName(String userName);
}
