package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;

@RunWith(MockitoJUnitRunner.class)
public class ContactRemovalServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character-id";

    @Mock
    private FriendRequestDao friendRequestDao;

    @Mock
    private FriendshipDao friendshipDao;

    @InjectMocks
    private ContactRemovalService underTest;

    @Mock
    private FriendRequest friendRequest;

    @Mock
    private Friendship friendship;

    @Test
    public void removeContactsBetween() {
        //GIVEN
        given(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).willReturn(Arrays.asList(friendship));
        given(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).willReturn(Arrays.asList(friendRequest));
        //WHEN
        underTest.removeContactsBetween(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(friendRequestDao).delete(friendRequest);
        verify(friendshipDao).delete(friendship);
    }
}