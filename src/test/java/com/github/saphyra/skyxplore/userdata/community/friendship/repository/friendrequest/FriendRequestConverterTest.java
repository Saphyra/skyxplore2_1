package com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestConverterTest {
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String CHARACTER_ID = "character_id";

    @InjectMocks
    private FriendRequestConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNull() {
        //GIVEN
        FriendRequestEntity entity = null;
        //WHEN
        FriendRequest result = underTest.convertEntity(entity);
        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        FriendRequestEntity entity = FriendRequestEntity.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .friendRequestId(FRIEND_REQUEST_ID)
            .build();
        //WHEN
        FriendRequest result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(result.getFriendRequestId()).isEqualTo(FRIEND_REQUEST_ID);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        FriendRequest request = FriendRequest.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .friendRequestId(FRIEND_REQUEST_ID)
            .build();
        //WHEN
        FriendRequestEntity result = underTest.convertDomain(request);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(result.getFriendRequestId()).isEqualTo(FRIEND_REQUEST_ID);
    }
}
