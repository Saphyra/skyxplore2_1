package com.github.saphyra.skyxplore.community.blockedcharacter.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("int-test")
public class BlockedCharacterRepositoryTest {
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final String CHARACTER_ID_3 = "character_id_3";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    @Autowired
    private BlockedCharacterRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByCharacterId() {
        //GIVEN
        BlockedCharacterEntity entity1 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();

        BlockedCharacterEntity entity2 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_3)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();

        BlockedCharacterEntity entity3 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsExactly(entity3);
    }

    @Test
    public void getByCharacterId() {
        //GIVEN
        BlockedCharacterEntity entity1 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();

        BlockedCharacterEntity entity2 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();

        BlockedCharacterEntity entity3 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        List<BlockedCharacterEntity> result = underTest.getByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(result).containsOnly(entity1, entity2);
    }

    @Test
    public void findByCharacterIdAndBlockedCharacterId() {
        //GIVEN
        BlockedCharacterEntity entity1 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();

        BlockedCharacterEntity entity2 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();

        BlockedCharacterEntity entity3 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        Optional<BlockedCharacterEntity> result = underTest.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        //THEN
        assertThat(result).contains(entity1);
    }

    @Test
    public void getByCharacterIdOrBlockedCharacterId() {
        //GIVEN
        BlockedCharacterEntity entity1 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();

        BlockedCharacterEntity entity2 = BlockedCharacterEntity.builder()
            .characterId(BLOCKED_CHARACTER_ID)
            .blockedCharacterId(CHARACTER_ID_1)
            .build();

        BlockedCharacterEntity entity3 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();

        BlockedCharacterEntity entity4 = BlockedCharacterEntity.builder()
            .characterId(BLOCKED_CHARACTER_ID)
            .blockedCharacterId(CHARACTER_ID_2)
            .build();

        BlockedCharacterEntity entity5 = BlockedCharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .blockedCharacterId(CHARACTER_ID_3)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2, entity3, entity4, entity5));
        //WHEN
        List<BlockedCharacterEntity> result = underTest.getByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        //THEN
        assertThat(result).containsOnly(entity1, entity2);
    }

    @TestConfiguration
    @EntityScan(basePackageClasses = BlockedCharacterEntity.class)
    @EnableJpaRepositories(basePackageClasses = BlockedCharacterRepository.class)
    @Import(DataSourceConfiguration.class)
    @Profile("int-test")
    static class TestConfig {

    }
}