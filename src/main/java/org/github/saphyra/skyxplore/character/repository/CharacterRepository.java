package org.github.saphyra.skyxplore.character.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    Optional<CharacterEntity> findByCharacterName(String characterName);

    List<CharacterEntity> findByUserId(String userId);

    List<CharacterEntity> findByCharacterNameContaining(String name);
}
