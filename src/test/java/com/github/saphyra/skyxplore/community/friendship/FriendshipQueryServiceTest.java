package com.github.saphyra.skyxplore.community.friendship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.community.friendship.repository.friendship.FriendshipDao;
import com.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.common.exception.FriendRequestNotFoundException;
import com.github.saphyra.skyxplore.common.exception.FriendshipNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipQueryServiceTest {
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";

    @Mock
    private FriendRequestDao friendRequestDao;

    @Mock
    private FriendshipDao friendshipDao;

    @InjectMocks
    private FriendshipQueryService underTest;

    @Test(expected = FriendRequestNotFoundException.class)
    public void testFindFriendRequestByIdShouldThrowExceptionWhenNull() {
        //GIVEN
        when(friendRequestDao.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.findFriendRequestById(FRIEND_REQUEST_ID);
    }

    @Test
    public void testFindFriendRequestShouldCallDao() {
        //GIVEN
        FriendRequest request = createFriendRequest();
        when(friendRequestDao.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.of(request));
        //WHEN
        FriendRequest result = underTest.findFriendRequestById(FRIEND_REQUEST_ID);
        //THEN
        assertThat(result).isEqualTo(request);
    }

    @Test(expected = FriendshipNotFoundException.class)
    public void testFindFriendshipByIdShouldThrowExceptionWhenNull() {
        //GIVEN
        when(friendshipDao.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.empty());
        //WHEN
        underTest.findFriendshipById(FRIEND_REQUEST_ID);
    }

    @Test
    public void testFindFriendshipShouldCallDao() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipDao.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.of(friendship));
        //WHEN
        Friendship result = underTest.findFriendshipById(FRIEND_REQUEST_ID);
        //THEN
        assertThat(result).isEqualTo(friendship);
    }

    @Test
    public void testGetFriendsShouldCallDao() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipDao.getFriendshipsOfCharacter(CHARACTER_ID)).thenReturn(Arrays.asList(friendship));
        //WHEN
        List<Friendship> result = underTest.getFriends(CHARACTER_ID);
        //THEN
        assertThat(result).containsOnly(friendship);
    }

    @Test
    public void testGetNumberOfFriendRequests() {
        //GIVEN
        when(friendRequestDao.getByFriendId(CHARACTER_ID)).thenReturn(Arrays.asList(createFriendRequest()));
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testReceivedFriendRequestShouldQueryAndSwapIds() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendRequestDao.getByFriendId(CHARACTER_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getReceivedFriendRequests(CHARACTER_ID);
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFriendId()).isEqualTo(CHARACTER_ID);
        assertThat(result.get(0).getCharacterId()).isEqualTo(FRIEND_ID);
    }

    @Test
    public void testGetSentFriendRequests() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendRequestDao.getByCharacterId(CHARACTER_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getSentFriendRequests(CHARACTER_ID);
        //THEN
        assertThat(result).containsOnly(friendRequest);
    }

    @Test
    public void testIsFriendshipAlreadyExists() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).thenReturn(Arrays.asList(friendship));
        //WHEN
        boolean result = underTest.isFriendshipAlreadyExists(CHARACTER_ID, FRIEND_ID);
        //THEN
        assertThat(result).isTrue();
        verify(friendshipDao).getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
    }

    @Test
    public void testIsFriendRequestAlreadyExists() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        boolean result = underTest.isFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID);
        //THEN
        assertThat(result).isTrue();
        verify(friendRequestDao).getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
    }

    @Test
    public void testIsFriendshipOrFriendRequestAlreadyExists() {
        //GIVEN
        when(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).thenReturn(Collections.emptyList());
        when(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID)).thenReturn(Collections.emptyList());
        //WHEN
        boolean result = underTest.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID);
        //THEN
        assertThat(result).isFalse();
        verify(friendRequestDao).getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
        verify(friendshipDao).getByCharacterIdOrFriendId(CHARACTER_ID, FRIEND_ID);
    }

    private FriendRequest createFriendRequest() {
        return FriendRequest.builder()
            .friendRequestId("")
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
    }

    private Friendship createFriendship() {
        return Friendship.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
    }
}