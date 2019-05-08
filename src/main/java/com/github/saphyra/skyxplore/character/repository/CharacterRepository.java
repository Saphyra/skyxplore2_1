package com.github.saphyra.skyxplore.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    Optional<CharacterEntity> findByCharacterName(String characterName);

    List<CharacterEntity> getByUserId(String userId);

    List<CharacterEntity> getByCharacterNameContaining(String name);
}
