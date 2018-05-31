package skyxplore.dataaccess.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skyxplore.dataaccess.character.entity.CharacterEntity;
import skyxplore.dataaccess.user.entity.UserEntity;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    CharacterEntity findByCharacterName(String characterName);
    List<CharacterEntity> findByUser(UserEntity user);
}
