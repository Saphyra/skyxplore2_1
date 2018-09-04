package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skyxplore.domain.credentials.CredentialsEntity;

@Repository
//TODO unit test
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, String> {
    CredentialsEntity getByUserName(String userName);
}
