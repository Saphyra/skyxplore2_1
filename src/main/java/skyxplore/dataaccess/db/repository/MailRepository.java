package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skyxplore.domain.community.mail.MailEntity;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, String> {
    @Query("SELECT m FROM MailEntity m WHERE m.to = :characterId AND m.archived = true AND m.deletedByAddressee = false")
    List<MailEntity> getArchivedMails(@Param("characterId") String characterId);

    @Query("SELECT m FROM MailEntity m WHERE m.to = :characterId AND m.archived = false AND m.deletedByAddressee = false")
    List<MailEntity> getMails(@Param("characterId") String characterId);

    @Query("SELECT m FROM MailEntity m WHERE m.to = :characterId AND m.read = false AND m.archived = false AND m.deletedByAddressee = false")
    List<MailEntity> getUnreadMails(@Param("characterId") String characterId);

    @Query("SELECT m FROM MailEntity m WHERE m.from = :characterId AND m.deletedBySender = false")
    List<MailEntity> getSentMails(@Param("characterId") String characterId);
}
