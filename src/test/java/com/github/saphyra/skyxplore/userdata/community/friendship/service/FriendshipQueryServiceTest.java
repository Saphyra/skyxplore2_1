package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
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
public class FriendshipQueryServiceTest {
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";

    @Mock
    private FriendshipDao friendshipDao;

    @InjectMocks
    private FriendshipQueryService underTest;

    @Test
    public void testFindFriendshipByIdShouldThrowExceptionWhenNull() {
        //GIVEN
        when(friendshipDao.findById(FRIEND_REQUEST_ID)).thenReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findFriendshipById(FRIEND_REQUEST_ID));
        //THEN
        verifyException(ex, NotFoundException.class, ErrorCode.FRIENDSHIP_NOT_FOUND);
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

    private Friendship createFriendship() {
        return Friendship.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
    }
}