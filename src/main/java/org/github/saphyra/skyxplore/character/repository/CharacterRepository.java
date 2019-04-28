package org.github.saphyra.skyxplore.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//TODO unit test
interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    CharacterEntity findByCharacterName(String characterName);

    List<CharacterEntity> findByUserId(String userId);

    List<CharacterEntity> findByCharacterNameContaining(String name);
}
