package com.github.saphyra.skyxplore.user.repository.credentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface CredentialsRepository extends JpaRepository<CredentialsEntity, String> {
    Optional<CredentialsEntity> findByUserName(String userName);
}
