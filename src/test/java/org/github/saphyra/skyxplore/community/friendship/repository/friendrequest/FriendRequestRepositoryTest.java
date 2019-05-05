package org.github.saphyra.skyxplore.community.friendship.repository.friendrequest;

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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FriendRequestRepositoryTest.TestConfig.class)
public class FriendRequestRepositoryTest {
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final String CHARACTER_ID_3 = "character_id_3";
    private static final String FRIEND_REQUEST_ID_1 = "friend_request_id_1";
    private static final String FRIEND_REQUEST_ID_2 = "friend_request_id_2";
    private static final String FRIEND_REQUEST_ID_3 = "friend_request_id_3";
    private static final String FRIEND_REQUEST_ID_4 = "friend_request_id_4";

    @Autowired
    private FriendRequestRepository underTest;

    @After
    public void tearDown() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByCharacterId() {
        //GIVEN
        FriendRequestEntity entity1 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_1)
            .friendId(CHARACTER_ID_1)
            .characterId(CHARACTER_ID_2)
            .build();

        FriendRequestEntity entity2 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_2)
            .friendId(CHARACTER_ID_2)
            .characterId(CHARACTER_ID_1)
            .build();

        FriendRequestEntity entity3 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_3)
            .friendId(CHARACTER_ID_2)
            .characterId(CHARACTER_ID_2)
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
        FriendRequestEntity entity1 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_1)
            .friendId(CHARACTER_ID_2)
            .characterId(CHARACTER_ID_1)
            .build();

        FriendRequestEntity entity2 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_2)
            .friendId(CHARACTER_ID_2)
            .characterId(CHARACTER_ID_1)
            .build();

        FriendRequestEntity entity3 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_3)
            .friendId(CHARACTER_ID_1)
            .characterId(CHARACTER_ID_2)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3));
        //WHEN
        List<FriendRequestEntity> result = underTest.getByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(result).containsOnly(entity1, entity2);
    }

    @Test
    public void getByCharacterIdOrFriendId() {
        //GIVEN
        FriendRequestEntity entity1 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_1)
            .friendId(CHARACTER_ID_2)
            .characterId(CHARACTER_ID_1)
            .build();

        FriendRequestEntity entity2 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_2)
            .friendId(CHARACTER_ID_1)
            .characterId(CHARACTER_ID_2)
            .build();

        FriendRequestEntity entity3 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_3)
            .friendId(CHARACTER_ID_1)
            .characterId(CHARACTER_ID_3)
            .build();

        FriendRequestEntity entity4 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_4)
            .friendId(CHARACTER_ID_3)
            .characterId(CHARACTER_ID_1)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2, entity3, entity4));
        //WHEN
        List<FriendRequestEntity> result = underTest.getByCharacterIdOrFriendId(CHARACTER_ID_1, CHARACTER_ID_2);
        //THEN
        assertThat(result).containsOnly(entity1, entity2);
    }

    @Test
    public void getByFriendId() {
        //GIVEN
        FriendRequestEntity entity1 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_1)
            .friendId(CHARACTER_ID_2)
            .characterId(CHARACTER_ID_1)
            .build();

        FriendRequestEntity entity2 = FriendRequestEntity.builder()
            .friendRequestId(FRIEND_REQUEST_ID_2)
            .friendId(CHARACTER_ID_1)
            .characterId(CHARACTER_ID_2)
            .build();

        underTest.saveAll(Arrays.asList(entity1, entity2));
        //WHEN
        List<FriendRequestEntity> result = underTest.getByFriendId(CHARACTER_ID_1);
        //THEN
        assertThat(result).containsOnly(entity2);
    }

    @TestConfiguration
    @EnableJpaRepositories(basePackageClasses = FriendRequestRepository.class)
    @EntityScan(basePackageClasses = FriendRequestEntity.class)
    @Import(DataSourceConfiguration.class)
    static class TestConfig {

    }
}