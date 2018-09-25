package skyxplore.service.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.FriendRequestDao;
import skyxplore.dataaccess.db.FriendshipDao;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.exception.FriendRequestNotFoundException;
import skyxplore.exception.FriendshipNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class FriendshipQueryServiceTest {
    @Mock
    private FriendRequestDao friendRequestDao;

    @Mock
    private FriendshipDao friendshipDao;

    @InjectMocks
    private FriendshipQueryService underTest;

    @Test(expected = FriendRequestNotFoundException.class)
    public void testFindFriendRequestByIdShouldThrowExceptionWhenNull(){
        //GIVEN
        when(friendRequestDao.findById(FRIEND_REQUEST_ID)).thenReturn(null);
        //WHEN
        underTest.findFriendRequestById(FRIEND_REQUEST_ID);
    }

    @Test
    public void testFindFriendRequestShouldCallDao(){
        //GIVEN
        FriendRequest request = createFriendRequest();
        when(friendRequestDao.findById(FRIEND_REQUEST_ID)).thenReturn(request);
        //WHEN
        FriendRequest result = underTest.findFriendRequestById(FRIEND_REQUEST_ID);
        //THEN
        assertEquals(request, result);
    }

    @Test(expected = FriendshipNotFoundException.class)
    public void testFindFriendshipByIdShouldThrowExceptionWhenNull(){
        //GIVEN
        when(friendshipDao.findById(FRIEND_REQUEST_ID)).thenReturn(null);
        //WHEN
        underTest.findFriendshipById(FRIEND_REQUEST_ID);
    }

    @Test
    public void testFindFriendshipShouldCallDao(){
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipDao.findById(FRIEND_REQUEST_ID)).thenReturn(friendship);
        //WHEN
        Friendship result = underTest.findFriendshipById(FRIEND_REQUEST_ID);
        //THEN
        assertEquals(friendship, result);
    }

    @Test
    public void testGetFriendsShouldCallDao() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipDao.getFriendshipsOfCharacter(CHARACTER_ID_1)).thenReturn(Arrays.asList(friendship));
        //WHEN
        List<Friendship> result = underTest.getFriends(CHARACTER_ID_1);
        //THEN
        assertEquals(1, result.size());
        assertTrue(result.contains(friendship));
    }

    @Test
    public void testGetNumberOfFriendRequests() {
        //GIVEN
        when(friendRequestDao.getByFriendId(CHARACTER_ID_1)).thenReturn(Arrays.asList(new FriendRequest()));
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID_1);
        //THEN
        assertEquals((Integer) 1, result);
    }

    @Test
    public void testReceivedFriendRequestShouldQueryAndSwapIds() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendRequestDao.getByFriendId(CHARACTER_ID_1)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getReceivedFriendRequests(CHARACTER_ID_1);
        //THEN
        assertEquals(1, result.size());
        assertTrue(result.contains(friendRequest));
        assertEquals(CHARACTER_ID_1, friendRequest.getFriendId());
        assertEquals(FRIEND_ID, friendRequest.getCharacterId());
    }

    @Test
    public void testGetSentFriendRequests() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendRequestDao.getByCharacterId(CHARACTER_ID_1)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getSentFriendRequests(CHARACTER_ID_1);
        //THEN
        assertEquals(1, result.size());
        assertTrue(result.contains(friendRequest));
    }

    @Test
    public void testIsFriendshipAlreadyExists() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID)).thenReturn(Arrays.asList(friendship));
        //WHEN
        assertTrue(underTest.isFriendshipAlreadyExists(CHARACTER_ID_1, FRIEND_ID));
        //THEN
        verify(friendshipDao).getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID);
    }

    @Test
    public void testIsFriendRequestAlreadyExists() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        assertTrue(underTest.isFriendRequestAlreadyExists(CHARACTER_ID_1, FRIEND_ID));
        //THEN
        verify(friendRequestDao).getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID);
    }

    @Test
    public void testIsFriendshipOrFriendRequestAlreadyExists() {
        //GIVEN
        when(friendRequestDao.getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID)).thenReturn(Collections.emptyList());
        when(friendshipDao.getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID)).thenReturn(Collections.emptyList());
        //WHEN
        assertFalse(underTest.isFriendshipOrFriendRequestAlreadyExists(CHARACTER_ID_1, FRIEND_ID));
        //THEN
        verify(friendRequestDao).getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID);
        verify(friendshipDao).getByCharacterIdOrFriendId(CHARACTER_ID_1, FRIEND_ID);
    }
}