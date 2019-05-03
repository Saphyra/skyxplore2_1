package org.github.saphyra.skyxplore.character.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.github.saphyra.skyxplore.testing.configuration.DataSourceConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CharacterRepositoryTest.TestConfig.class)
public class CharacterRepositoryTest {
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final String CHARACTER_NAME_1 = "character_name_1";
    private static final String CHARACTER_NAME_2 = "character_name_2";
    private static final String USER_ID_1 = "user_id_1";
    private static final String USER_ID_2 = "user_id_2";
    private static final String CHARACTER_ID_3 = "character_id_3";

    @Autowired
    private CharacterRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void findByCharacterName() {
        //GIVEN
        CharacterEntity entity1 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .characterName(CHARACTER_NAME_1)
            .userId(USER_ID_1)
            .equipments("")
            .money("")
            .build();

        CharacterEntity entity2 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .characterName(CHARACTER_NAME_2)
            .userId(USER_ID_1)
            .equipments("")
            .money("")
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        Optional<CharacterEntity> result = underTest.findByCharacterName(CHARACTER_NAME_1);
        //THEN
        assertThat(result).contains(entity1);
    }

    @Test
    public void findByUserId(){
        //GIVEN
        CharacterEntity entity1 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .characterName(CHARACTER_NAME_1)
            .userId(USER_ID_1)
            .equipments("")
            .money("")
            .build();

        CharacterEntity entity2 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .characterName(CHARACTER_NAME_2)
            .userId(USER_ID_1)
            .equipments("")
            .money("")
            .build();

        CharacterEntity entity3 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_3)
            .characterName(CHARACTER_NAME_2)
            .userId(USER_ID_2)
            .equipments("")
            .money("")
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        List<CharacterEntity> result = underTest.findByUserId(USER_ID_1);
        //THEN
        assertThat(result).containsOnly(entity1, entity2);
    }

    @Test
    public void findByCharacterNameContaining(){
        //GIVEN
        CharacterEntity entity1 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_1)
            .characterName(CHARACTER_NAME_1)
            .userId(USER_ID_1)
            .equipments("")
            .money("")
            .build();

        CharacterEntity entity2 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_2)
            .characterName(CHARACTER_NAME_2)
            .userId(USER_ID_1)
            .equipments("")
            .money("")
            .build();

        CharacterEntity entity3 = CharacterEntity.builder()
            .characterId(CHARACTER_ID_3)
            .characterName("asvr")
            .userId(USER_ID_2)
            .equipments("")
            .money("")
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        List<CharacterEntity> result = underTest.findByCharacterNameContaining("name");
        //THEN
        assertThat(result).containsOnly(entity1, entity2);
    }

    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = CharacterRepository.class)
    @EntityScan(basePackageClasses = CharacterEntity.class)
    @Import(DataSourceConfiguration.class)
    static class TestConfig {
    }

}