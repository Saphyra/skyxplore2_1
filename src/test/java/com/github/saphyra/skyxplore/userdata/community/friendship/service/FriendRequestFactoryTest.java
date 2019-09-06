package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestFactoryTest {
    private static final String CHARACTER_ID = "character-id";
    private static final String FRIEND_ID = "friend-id";
    private static final String FRIEND_REQUEST_ID = "friend-request-id";

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private FriendRequestFactory underTest;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.generateRandomId()).willReturn(FRIEND_REQUEST_ID);
        //WHEN
        FriendRequest result = underTest.create(CHARACTER_ID, FRIEND_ID);
        //THEN
        assertThat(result.getFriendRequestId()).isEqualTo(FRIEND_REQUEST_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getFriendId()).isEqualTo(FRIEND_ID);
    }
}