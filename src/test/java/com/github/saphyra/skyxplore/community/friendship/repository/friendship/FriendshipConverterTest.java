package com.github.saphyra.skyxplore.community.friendship.repository.friendship;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class FriendshipConverterTest {
    private static final String FRIEND_ID = "friend_id";
    private static final String FRIENDSHIP_ID = "friendship_id";
    private static final String CHARACTER_ID = "character_id";
    @InjectMocks
    private FriendshipConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        FriendshipEntity entity = null;
        //WHEN
        Friendship result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        FriendshipEntity entity = FriendshipEntity.builder()
            .friendId(FRIEND_ID)
            .friendshipId(FRIENDSHIP_ID)
            .characterId(CHARACTER_ID)
            .build();
        //WHEN
        Friendship result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(result.getFriendshipId()).isEqualTo(FRIENDSHIP_ID);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        Friendship friendship = Friendship.builder()
            .friendId(FRIEND_ID)
            .friendshipId(FRIENDSHIP_ID)
            .characterId(CHARACTER_ID)
            .build();
        //WHEN
        FriendshipEntity result = underTest.convertDomain(friendship);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(result.getFriendshipId()).isEqualTo(FRIENDSHIP_ID);
    }
}
