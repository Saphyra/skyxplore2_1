package skyxplore.dataaccess.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import skyxplore.domain.friend.blockeduser.BlockedCharacteEntity;

@Repository
public interface BlockedCharacterRepository extends JpaRepository<BlockedCharacteEntity, Long> {
    List<BlockedCharacteEntity> findByCharacterId(String characterId);

    BlockedCharacteEntity findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId);
}
