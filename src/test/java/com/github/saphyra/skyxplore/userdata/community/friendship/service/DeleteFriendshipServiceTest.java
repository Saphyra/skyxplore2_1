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
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;

@RunWith(MockitoJUnitRunner.class)
public class DeleteFriendshipServiceTest {
    private static final String FRIENDSHIP_ID = "friendship-id";
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private FriendshipDao friendshipDao;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private DeleteFriendshipService underTest;

    @Mock
    private Friendship friendship;

    @Test
    public void deleteFriendship_invalidAccess() {
        //GIVEN
        given(friendshipQueryService.findFriendshipById(FRIENDSHIP_ID)).willReturn(friendship);
        given(friendship.getFriendId()).willReturn(FRIENDSHIP_ID);
        given(friendship.getCharacterId()).willReturn(FRIENDSHIP_ID);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID));
        //THEN
        verifyException(ex, ForbiddenException.class, ErrorCode.INVALID_FRIENDSHIP_ACCESS);
    }

    @Test
    public void deleteFriendship() {
        //GIVEN
        given(friendshipQueryService.findFriendshipById(FRIENDSHIP_ID)).willReturn(friendship);
        given(friendship.getFriendId()).willReturn(CHARACTER_ID);
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID);
        //THEN
        verify(friendshipDao).delete(friendship);
    }
}