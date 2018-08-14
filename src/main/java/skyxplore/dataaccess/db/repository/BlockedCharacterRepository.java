package skyxplore.dataaccess.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import skyxplore.domain.friend.blockeduser.BlockedCharacteEntity;

@Repository
public interface BlockedCharacterRepository extends JpaRepository<BlockedCharacteEntity, Long> {
    List<BlockedCharacteEntity> findByCharacterId(String characterId);

    BlockedCharacteEntity findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId);

    @Query("SELECT b FROM BlockedCharacterEntity b WHERE " +
        "(b.characterId = :characterId AND b.blockedCharacterId = :blockedCharacterId)" +
        " OR " +
        "(b.characterId = :blockedCharacterId AND b.blockedCharacterId = :characterId)")
    List<BlockedCharacteEntity> findByCharacterIdOrBlockedCharacterId(@Param("characterId") String characterId, @Param("blockedCharacterId") String blockedCharacterId);
}
