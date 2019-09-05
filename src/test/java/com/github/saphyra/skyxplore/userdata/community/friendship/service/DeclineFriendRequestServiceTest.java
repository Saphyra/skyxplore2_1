package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;

@RunWith(MockitoJUnitRunner.class)
public class DeclineFriendRequestServiceTest {
    private static final String FRIEND_REQUEST_ID = "friend-request-id";
    private static final String CHARACTER_ID = "character-id";

    @Mock
    private FriendRequestDao friendRequestDao;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private DeclineFriendRequestService underTest;

    @Mock
    private FriendRequest friendRequest;

    @Test
    public void declineFriendRequest_invalidAccess() {
        //GIVEN
        given(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).willReturn(friendRequest);
        given(friendRequest.getFriendId()).willReturn(FRIEND_REQUEST_ID);
        given(friendRequest.getCharacterId()).willReturn(FRIEND_REQUEST_ID);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID));
        //THEN
        verifyException(ex, ForbiddenException.class, ErrorCode.INVALID_FRIEND_REQUEST_ACCESS);
    }

    @Test
    public void declineFriendRequest() {
        //GIVEN
        given(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).willReturn(friendRequest);
        given(friendRequest.getCharacterId()).willReturn(CHARACTER_ID);
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
        //THEN
        verify(friendRequestDao).delete(friendRequest);
    }
}