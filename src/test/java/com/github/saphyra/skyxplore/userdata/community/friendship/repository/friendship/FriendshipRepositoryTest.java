package com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;

import com.github.saphyra.testing.configuration.DataSourceConfiguration;
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
public class FriendshipRepositoryTest {
    private static final String FRIENDSHIP_ID_1 = "friendship_id_1";
    private static final String FRIENDSHIP_ID_2 = "friendship_id_2";
    private static final String FRIENDSHIP_ID_3 = "friendship_id_3";
    private static final String FRIENDSHIP_ID_4 = "friendship_id_4";
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final String CHARACTER_ID_3 = "character_id_3";

    @Autowired
    private FriendshipRepository underTest;

    @After
    public void tearDown(){
        underTest.deleteAll();
    }

    @Test
    public void deleteByCharacterId(){
        //GIVEN
        FriendshipEntity entity1 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_1)
            .characterId(CHARACTER_ID_1)
            .friendId(CHARACTER_ID_2)
            .build();

        FriendshipEntity entity2 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_2)
            .characterId(CHARACTER_ID_2)
            .friendId(CHARACTER_ID_1)
            .build();

        FriendshipEntity entity3 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_3)
            .characterId(CHARACTER_ID_2)
            .friendId(CHARACTER_ID_2)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(underTest.findAll()).containsExactly(entity3);
    }

    @Test
    public void getByCharacterIdOrFriendId(){
        //GIVEN
        FriendshipEntity entity1 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_1)
            .characterId(CHARACTER_ID_1)
            .friendId(CHARACTER_ID_2)
            .build();

        FriendshipEntity entity2 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_2)
            .characterId(CHARACTER_ID_2)
            .friendId(CHARACTER_ID_1)
            .build();

        FriendshipEntity entity3 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_3)
            .characterId(CHARACTER_ID_2)
            .friendId(CHARACTER_ID_3)
            .build();

        FriendshipEntity entity4 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_4)
            .characterId(CHARACTER_ID_3)
            .friendId(CHARACTER_ID_2)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3, entity4));
        //WHEN
        List<FriendshipEntity> result = underTest.getByCharacterIdOrFriendId(CHARACTER_ID_1, CHARACTER_ID_2);
        //THEN
        Assertions.assertThat(result).containsOnly(entity1, entity2);
    }

    @Test
    public void getFriendshipsOfCharacter(){
        //GIVEN
        FriendshipEntity entity1 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_1)
            .characterId(CHARACTER_ID_1)
            .friendId(CHARACTER_ID_2)
            .build();

        FriendshipEntity entity2 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_2)
            .characterId(CHARACTER_ID_2)
            .friendId(CHARACTER_ID_1)
            .build();

        FriendshipEntity entity3 = FriendshipEntity.builder()
            .friendshipId(FRIENDSHIP_ID_3)
            .characterId(CHARACTER_ID_2)
            .friendId(CHARACTER_ID_2)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        List<FriendshipEntity> result = underTest.getFriendshipsOfCharacter(CHARACTER_ID_1);
        //THEN
        Assertions.assertThat(result).containsOnly(entity1, entity2);
    }


    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = FriendshipRepository.class)
    @EntityScan(basePackageClasses = FriendshipEntity.class)
    @Import(DataSourceConfiguration.class)
    @Profile("int-test")
    static class TestConfig{

    }
}