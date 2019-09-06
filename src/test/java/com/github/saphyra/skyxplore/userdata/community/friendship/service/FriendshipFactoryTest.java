package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipFactoryTest {
    private static final String FRIENDSHIP_ID = "friendship-id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend-id";

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private FriendshipFactory underTest;

    @Mock
    private FriendRequest friendRequest;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(FRIENDSHIP_ID);
        given(friendRequest.getCharacterId()).willReturn(CHARACTER_ID);
        given(friendRequest.getFriendId()).willReturn(FRIEND_ID);
        //WHEN
        Friendship result = underTest.create(friendRequest);
        //THEN
        assertThat(result.getFriendId()).isEqualTo(FRIEND_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getFriendshipId()).isEqualTo(FRIENDSHIP_ID);
    }
}