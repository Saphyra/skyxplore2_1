package com.github.saphyra.skyxplore.community.friendship;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.exception.CharacterBlockedException;
import com.github.saphyra.skyxplore.common.exception.FriendshipAlreadyExistsException;
import com.github.saphyra.skyxplore.community.blockedcharacter.BlockedCharacterQueryService;
import com.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.community.friendship.repository.friendship.FriendshipDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipServiceTest {
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    private static final String FAKE_CHARACTER_ID = "fake_character_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String FRIENDSHIP_ID = "friendship_id";
    private static final String USER_ID = "user_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    @Mock
    private BlockedCharacterQueryService blockedCharacterQueryService;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FriendRequestDao friendRequestDao;

    @Mock
    private FriendshipDao friendshipDao;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private FriendshipService underTest;

    @Test(expected = UnauthorizedException.class)
    public void testAcceptFriendRequestShouldThrowExceptionWhenWrongCharacterId() {
        //GIVEN
        FriendRequest friendRequest = FriendRequest.builder().friendId(FRIEND_ID).build();
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, FAKE_CHARACTER_ID);
    }

    @Test
    public void testAcceptFriendRequestShouldAccept() {
        //GIVEN
        FriendRequest friendRequest = FriendRequest.builder()
            .characterId(FRIEND_ID)
            .friendId(CHARACTER_ID)
            .build();
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);

        when(idGenerator.generateRandomId()).thenReturn(FRIENDSHIP_ID);
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
        //THEN
        verify(friendshipQueryService).findFriendRequestById(FRIEND_REQUEST_ID);

        ArgumentCaptor<Friendship> argumentCaptor = ArgumentCaptor.forClass(Friendship.class);
        verify(friendshipDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getCharacterId()).isEqualTo(FRIEND_ID);
        assertThat(argumentCaptor.getValue().getFriendId()).isEqualTo(CHARACTER_ID);
        assertThat(argumentCaptor.getValue().getFriendshipId()).isEqualTo(FRIENDSHIP_ID);

        verify(friendRequestDao).delete(friendRequest);
    }

    @Test(expected = CharacterBlockedException.class)
    public void testAddFriendRequestShouldThrowExceptionWhenCharacterBlocked() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, FRIEND_ID)).thenReturn(Arrays.asList(new BlockedCharacter()));
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
    }

    @Test(expected = FriendshipAlreadyExistsException.class)
    public void testAddFriendRequestShouldThrowExceptionWhenAlreadyExists() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, FRIEND_ID)).thenReturn(Collections.emptyList());
        when(friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID)).thenReturn(true);
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
    }

    @Test(expected = BadRequestException.class)
    public void testAddFriendRequestShouldThrowExceptionWhenOwnCharacter() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, FRIEND_ID)).thenReturn(Collections.emptyList());
        when(friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID)).thenReturn(false);
        when(characterQueryService.getCharactersByUserId(USER_ID)).thenReturn(Arrays.asList(SkyXpCharacter.builder().characterId(FRIEND_ID).build()));
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
    }

    @Test
    public void testAddFriendRequestShouldAdd() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, FRIEND_ID)).thenReturn(Collections.emptyList());
        when(friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID)).thenReturn(false);
        when(idGenerator.generateRandomId()).thenReturn(FRIEND_REQUEST_ID);
        when(characterQueryService.getCharactersByUserId(USER_ID)).thenReturn(Collections.emptyList());
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        verify(blockedCharacterQueryService).findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, FRIEND_ID);
        verify(friendshipQueryService).isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID, FRIEND_ID);

        ArgumentCaptor<FriendRequest> argumentCaptor = ArgumentCaptor.forClass(FriendRequest.class);
        verify(friendRequestDao).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getFriendRequestId()).isEqualTo(FRIEND_REQUEST_ID);
        assertThat(argumentCaptor.getValue().getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(argumentCaptor.getValue().getFriendId()).isEqualTo(FRIEND_ID);
    }

    @Test(expected = UnauthorizedException.class)
    public void testDeclineFriendRequestShouldThrowExceptionWhenWrongId() {
        //GIVEN
        FriendRequest friendRequest = FriendRequest.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, FAKE_CHARACTER_ID);
    }

    @Test
    public void testDeclineFriendRequestShouldDelete() {
        //GIVEN
        FriendRequest friendRequest = FriendRequest.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, FRIEND_ID);
        //tTHEN
        verify(friendRequestDao).delete(friendRequest);
    }

    @Test(expected = UnauthorizedException.class)
    public void testDeleteFriendshipShouldThrowExceptionWhenWrongId() {
        //GIVEN
        Friendship friendship = Friendship.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
        when(friendshipQueryService.findFriendshipById(FRIENDSHIP_ID)).thenReturn(friendship);
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, FAKE_CHARACTER_ID);
    }

    @Test
    public void testDeleteFriendshipShouldDelete() {
        //GIVEN
        Friendship friendship = Friendship.builder()
            .characterId(CHARACTER_ID)
            .friendId(FRIEND_ID)
            .build();
        when(friendshipQueryService.findFriendshipById(FRIENDSHIP_ID)).thenReturn(friendship);
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID);
        //THEN
        verify(friendshipQueryService).findFriendshipById(FRIENDSHIP_ID);
        verify(friendshipDao).delete(friendship);
    }

    @Test
    public void testRemoveContactsBetweenShouldDelete() {
        //GIVEN
        Friendship friendship = Friendship.builder().build();
        when(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Arrays.asList(friendship));

        FriendRequest friendRequest = FriendRequest.builder().build();
        when(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        underTest.removeContactsBetween(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(friendshipDao).delete(friendship);
        verify(friendRequestDao).delete(friendRequest);
    }
}