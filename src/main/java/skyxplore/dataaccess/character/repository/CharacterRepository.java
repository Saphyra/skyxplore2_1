package skyxplore.dataaccess.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.dataaccess.character.entity.CharacterEntity;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    CharacterEntity findByCharacterName(String characterName);
}
