package skyxplore.dataaccess.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skyxplore.domain.community.mail.MailEntity;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, String> {

    @Query("SELECT m FROM MailEntity m WHERE m.to = :characterId AND m.read = false")
    List<MailEntity> getUnreadMails(@Param("characterId") String characterId);
}
