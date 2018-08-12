package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import skyxplore.domain.character.CharacterEntity;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    CharacterEntity findByCharacterName(String characterName);
    List<CharacterEntity> findByUserId(String userId);

    //@Query("SELECT c FROM CharacterEntity c WHERE c.characterName LIKE :name")
    List<CharacterEntity> findByCharacterNameContaining(@Param("name") String name);
}
