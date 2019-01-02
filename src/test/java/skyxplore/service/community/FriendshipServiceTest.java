package skyxplore.service.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.exception.CharacterBlockedException;
import skyxplore.exception.FriendshipAlreadyExistsException;
import skyxplore.exception.base.UnauthorizedException;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.IdGenerator;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.BLOCKED_CHARACTER_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_2;
import static skyxplore.testutil.TestUtils.FRIENDSHIP_ID;
import static skyxplore.testutil.TestUtils.FRIEND_ID;
import static skyxplore.testutil.TestUtils.FRIEND_REQUEST_ID;
import static skyxplore.testutil.TestUtils.createFriendRequest;
import static skyxplore.testutil.TestUtils.createFriendship;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipServiceTest {
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
        FriendRequest friendRequest = createFriendRequest();
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_2);
    }

    @Test
    public void testAcceptFriendRequestShouldAccept() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        friendRequest.setFriendId(CHARACTER_ID_1);
        friendRequest.setCharacterId(FRIEND_ID);
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);

        when(idGenerator.getRandomId()).thenReturn(FRIENDSHIP_ID);
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_1);
        //THEN
        verify(friendshipQueryService).findFriendRequestById(FRIEND_REQUEST_ID);

        ArgumentCaptor<Friendship> argumentCaptor = ArgumentCaptor.forClass(Friendship.class);
        verify(friendshipDao).save(argumentCaptor.capture());
        assertEquals(FRIENDSHIP_ID, argumentCaptor.getValue().getFriendshipId());
        assertEquals(FRIEND_ID, argumentCaptor.getValue().getCharacterId());
        assertEquals(CHARACTER_ID_1, argumentCaptor.getValue().getFriendId());

        verify(friendRequestDao).delete(friendRequest);
    }

    @Test(expected = CharacterBlockedException.class)
    public void testAddFriendRequestShouldThrowExceptionWhenCharacterBlocked() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, FRIEND_ID)).thenReturn(Arrays.asList(new BlockedCharacter()));
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID_1);
    }

    @Test(expected = FriendshipAlreadyExistsException.class)
    public void testAddFriendRequestShouldThrowExceptionWhenAlreadyExists() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, FRIEND_ID)).thenReturn(Collections.emptyList());
        when(friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID_1, FRIEND_ID)).thenReturn(true);
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID_1);
    }

    @Test
    public void testAddFriendRequestShouldAdd() {
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, FRIEND_ID)).thenReturn(Collections.emptyList());
        when(friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID_1, FRIEND_ID)).thenReturn(false);
        when(idGenerator.getRandomId()).thenReturn(FRIEND_REQUEST_ID);
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        verify(blockedCharacterQueryService).findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, FRIEND_ID);
        verify(friendshipQueryService).isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID_1, FRIEND_ID);

        ArgumentCaptor<FriendRequest> argumentCaptor = ArgumentCaptor.forClass(FriendRequest.class);
        verify(friendRequestDao).save(argumentCaptor.capture());

        assertEquals(FRIEND_REQUEST_ID, argumentCaptor.getValue().getFriendRequestId());
        assertEquals(CHARACTER_ID_1, argumentCaptor.getValue().getCharacterId());
        assertEquals(FRIEND_ID, argumentCaptor.getValue().getFriendId());
    }

    @Test(expected = UnauthorizedException.class)
    public void testDeclineFriendRequestShouldThrowExceptionWhenWrongId() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_2);
    }

    @Test
    public void testDeclineFriendRequestShouldDelete() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendshipQueryService.findFriendRequestById(FRIEND_REQUEST_ID)).thenReturn(friendRequest);
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, FRIEND_ID);
        //tTHEN
        verify(friendRequestDao).delete(friendRequest);
    }

    @Test(expected = UnauthorizedException.class)
    public void testDeleteFriendshipShouldThrowExceptionWhenWrongId() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipQueryService.findFriendshipById(FRIENDSHIP_ID)).thenReturn(friendship);
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID_2);
    }

    @Test
    public void testDeleteFriendshipShouldDelete() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipQueryService.findFriendshipById(FRIENDSHIP_ID)).thenReturn(friendship);
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID_1);
        //THEN
        verify(friendshipQueryService).findFriendshipById(FRIENDSHIP_ID);
        verify(friendshipDao).delete(friendship);
    }

    @Test
    public void testRemoveContactsBetweenShouldDelete(){
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(Arrays.asList(friendship));

        FriendRequest friendRequest = createFriendRequest();
        when(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        underTest.removeContactsBetween(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        //THEN
        verify(friendshipDao).delete(friendship);
        verify(friendRequestDao).delete(friendRequest);
    }
}