package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.domain.community.mail.MailEntity;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, String> {
}
