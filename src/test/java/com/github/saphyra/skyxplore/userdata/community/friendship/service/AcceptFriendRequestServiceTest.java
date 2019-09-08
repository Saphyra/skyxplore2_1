package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AcceptFriendRequestServiceTest {
    private static final String FRIEND_REQUEST_ID = "friend-request-id";
    private static final String CHARACTER_ID = "character-id";
    private static final String FAKE_CHARACTER_ID = "fake-character-id";

    @Mock
    private FriendRequestDao friendRequestDao;

    @Mock
    private FriendshipDao friendshipDao;

    @Mock
    private FriendshipFactory friendshipFactory;

    @Mock
    private FriendRequestQueryService friendRequestQueryService;

    @InjectMocks
    private AcceptFriendRequestService underTest;

    @Mock
    private FriendRequest friendRequest;

    @Mock
    private Friendship friendship;

    @Before
    public void setUp() {
        given(friendRequestQueryService.findByFriendRequestId(FRIEND_REQUEST_ID)).willReturn(friendRequest);
        given(friendRequest.getFriendId()).willReturn(CHARACTER_ID);
    }

    @Test
    public void acceptFriendRequest_invalidAccess() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.acceptFriendRequest(FRIEND_REQUEST_ID, FAKE_CHARACTER_ID));
        //THEN
        verifyException(ex, ForbiddenException.class, ErrorCode.INVALID_FRIEND_REQUEST_ACCESS);
    }

    @Test
    public void acceptFriendRequest() {
        //GIVEN
        given(friendshipFactory.create(friendRequest)).willReturn(friendship);
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
        //THEN
        verify(friendshipDao).save(friendship);
        verify(friendRequestDao).delete(friendRequest);
    }
}