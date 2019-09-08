package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";

    @Mock
    private FriendRequestQueryService friendRequestQueryService;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private ContactQueryService underTest;

    @Test
    public void testIsFriendshipOrFriendRequestAlreadyExists() {
        //GIVEN
        given(friendRequestQueryService.isFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID)).willReturn(false);
        given(friendshipQueryService.isFriendshipAlreadyExists(CHARACTER_ID, FRIEND_ID)).willReturn(false);
        //WHEN
        boolean result = underTest.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID);
        //THEN
        assertThat(result).isFalse();
        verify(friendRequestQueryService).isFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID);
        verify(friendshipQueryService).isFriendshipAlreadyExists(CHARACTER_ID, FRIEND_ID);
    }
}