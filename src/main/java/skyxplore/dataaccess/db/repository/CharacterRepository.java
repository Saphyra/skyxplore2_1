package skyxplore.dataaccess.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skyxplore.domain.character.CharacterEntity;

import java.util.List;

@Repository
//TODO unit test
public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    CharacterEntity findByCharacterName(String characterName);

    List<CharacterEntity> findByUserId(String userId);

    List<CharacterEntity> findByCharacterNameContaining(String name);
}
