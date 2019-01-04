package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.BlockCharacterService;
import skyxplore.service.community.FriendshipQueryService;
import skyxplore.service.community.FriendshipService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.BLOCKED_CHARACTER_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.FRIENDSHIP_ID;
import static skyxplore.testutil.TestUtils.FRIEND_ID;
import static skyxplore.testutil.TestUtils.FRIEND_REQUEST_ID;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createFriendRequest;
import static skyxplore.testutil.TestUtils.createFriendship;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class CommunityFacadeTest {
    @Mock
    private BlockCharacterService blockCharacterService;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private CommunityFacade underTest;

    @Test
    public void testAcceptFriendRequestShouldCallService() {
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_1);
        //THEN
        verify(friendshipService).acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_1);
    }

    @Test
    public void testAllowBlockedCharacterShouldCallService() {
        //WHEN
        underTest.allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
        //THEN
        verify(blockCharacterService).allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
    }

    @Test
    public void testAddFriendRequestShouldCallService() {
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID_1, USER_ID);
        //THEN
        verify(friendshipService).addFriendRequest(FRIEND_ID, CHARACTER_ID_1, USER_ID);
    }

    @Test
    public void testBlockCharacterShouldCallService() {
        //WHEN
        underTest.blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
        //THEN
        verify(blockCharacterService).blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
    }

    @Test
    public void testDeclineFriendRequestShouldCallService() {
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_1);
        //THEN
        verify(friendshipService).declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_1);
    }

    @Test
    public void testDeleteFriendshipShouldCallService() {
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID_1);
        //THEN
        verify(friendshipService).deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID_1);
    }

    @Test
    public void testGetBlockedCharactersShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterQueryService.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getBlockedCharacters(CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).getBlockedCharacters(CHARACTER_ID_1);
        assertEquals(1, result.size());
        assertEquals(character, result.get(0));
    }

    @Test
    public void testGetCharactersCanBeBlockedShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterQueryService.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID_1)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID_1);
        assertEquals(1, result.size());
        assertEquals(character, result.get(0));
    }

    @Test
    public void testGetCharactersCanBeFriendsShouldCallServiceAndReturn() {
        ///GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterQueryService.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID_1)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID_1);
        assertEquals(1, result.size());
        assertEquals(character, result.get(0));
    }

    @Test
    public void testGetFriendsShouldCallServiceAndReturn() {
        //GIVEN
        Friendship friendship = createFriendship();
        when(friendshipQueryService.getFriends(CHARACTER_ID_1)).thenReturn(Arrays.asList(friendship));
        //WHEN
        List<Friendship> result = underTest.getFriends(CHARACTER_ID_1);
        //THEN
        verify(friendshipQueryService).getFriends(CHARACTER_ID_1);
        assertEquals(1, result.size());
        assertEquals(friendship, result.get(0));
    }

    @Test
    public void testGetNumberOfFriendRequestsShouldCallServiceAndResult() {
        //GIVEN
        when(friendshipQueryService.getNumberOfFriendRequests(CHARACTER_ID_1)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID_1);
        //THEN
        verify(friendshipQueryService).getNumberOfFriendRequests(CHARACTER_ID_1);
        assertEquals((Integer) 2, result);
    }

    @Test
    public void testGetReceivedFriendRequestsShouldCallServiceAndReturn() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendshipQueryService.getReceivedFriendRequests(CHARACTER_ID_1)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getReceivedFriendRequests(CHARACTER_ID_1);
        //THEN
        verify(friendshipQueryService).getReceivedFriendRequests(CHARACTER_ID_1);
        assertEquals(1, result.size());
        assertTrue(result.contains(friendRequest));
    }

    @Test
    public void testGetSentFriendRequestsShouldCallServiceAndReturn() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        when(friendshipQueryService.getSentFriendRequests(CHARACTER_ID_1)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getSentFriendRequests(CHARACTER_ID_1);
        //THEN
        verify(friendshipQueryService).getSentFriendRequests(CHARACTER_ID_1);
        assertEquals(1, result.size());
        assertTrue(result.contains(friendRequest));
    }
}