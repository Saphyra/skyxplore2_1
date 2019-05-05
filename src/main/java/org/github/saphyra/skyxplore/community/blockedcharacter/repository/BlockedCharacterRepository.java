package org.github.saphyra.skyxplore.community.blockedcharacter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
interface BlockedCharacterRepository extends JpaRepository<BlockedCharacterEntity, Long> {
    @Modifying
    @Transactional
    @Query("DELETE BlockedCharacterEntity b WHERE b.characterId = :characterId OR b.blockedCharacterId = :characterId")
    void deleteByCharacterId(@Param("characterId") String characterId);

    List<BlockedCharacterEntity> getByCharacterId(@Param("characterId") String characterId);

    Optional<BlockedCharacterEntity> findByCharacterIdAndBlockedCharacterId(String characterId, String blockedCharacterId);

    @Query("SELECT b FROM BlockedCharacterEntity b WHERE " +
        "(b.characterId = :characterId AND b.blockedCharacterId = :blockedCharacterId)" +
        " OR " +
        "(b.characterId = :blockedCharacterId AND b.blockedCharacterId = :characterId)")
    List<BlockedCharacterEntity> getByCharacterIdOrBlockedCharacterId(@Param("characterId") String characterId, @Param("blockedCharacterId") String blockedCharacterId);
}
