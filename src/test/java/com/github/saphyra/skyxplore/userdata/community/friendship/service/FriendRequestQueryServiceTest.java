package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestQueryServiceTest {
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";

    @Mock
    private FriendRequestDao friendRequestDao;

    @InjectMocks
    private FriendRequestQueryService underTest;

    @Test
    public void testFindFriendRequestByIdShouldThrowExceptionWhenNull() {
        //GIVEN
        when(friendRequestDao.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByFriendRequestId(FRIEND_REQUEST_ID));
        //THEN
        verifyException(ex, NotFoundException.class, ErrorCode.FRIEND_REQUEST_NOT_FOUND);
    }

    @Test
    public void testFindFriendRequestShouldCallDao() {
        //GIVEN
        FriendRequest request = createFriendRequest();
        when(friendRequestDao.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.of(request));
        //WHEN
        FriendRequest result = underTest.findByFriendRequestId(FRIEND_REQUEST_ID);
        //THEN
        assertThat(result).isEqualTo(request);
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

    private FriendRequest createFriendRequest() {
        return FriendRequest.builder()
            .friendRequestId("")
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
    }
}