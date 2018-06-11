package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.dataaccess.db.entity.CharacterEntity;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    CharacterEntity findByCharacterName(String characterName);
    List<CharacterEntity> findByUserId(String userId);
}