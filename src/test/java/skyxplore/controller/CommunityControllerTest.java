package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friend.FriendViewConverter;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;
import skyxplore.controller.view.community.friendrequest.FriendRequestViewConverter;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import skyxplore.service.CommunityFacade;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class CommunityControllerTest {
    @Mock
    private CommunityFacade communityFacade;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private FriendRequestViewConverter friendRequestViewConverter;

    @Mock
    private FriendViewConverter friendViewConverter;

    @InjectMocks
    private CommunityController underTest;

    @Test
    public void testAcceptFriendRequestShouldCallFacade() {
        //WHEN
        underTest.acceptFriendRequest(new OneStringParamRequest(FRIEND_REQUEST_ID), CHARACTER_ID_1);
        //THEN
        verify(communityFacade).acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_1);
    }

    @Test
    public void testAddFriendShouldCallFacade() {
        //WHEN
        underTest.addFriend(new OneStringParamRequest(FRIEND_ID), CHARACTER_ID_1, USER_ID);
        //THEN
        verify(communityFacade).addFriendRequest(FRIEND_ID, CHARACTER_ID_1, USER_ID);
    }

    @Test
    public void testAllowBlockedCharacterShouldCallFacade() {
        //WHEN
        underTest.allowBlockedCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID_1);
        //THEN
        verify(communityFacade).allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
    }

    @Test
    public void testBlockCharacterShouldCallFacade() {
        //WHEN
        underTest.blockCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID_1);
        //THEN
        verify(communityFacade).blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
    }

    @Test
    public void testDeclineFriendRequestShouldCallFacade() {
        //WHEN
        underTest.declineFriendRequestMapping(new OneStringParamRequest(FRIEND_REQUEST_ID), CHARACTER_ID_1);
        //THEN
        verify(communityFacade).declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID_1);
    }

    @Test
    public void testDeleteFriendShouldCallFacade() {
        //GIVEN
        underTest.deleteFriend(new OneStringParamRequest(FRIENDSHIP_ID), CHARACTER_ID_1);
        //WHEN
        verify(communityFacade).deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID_1);
    }

    @Test
    public void testGetBlockedCharactersShouldCallFacadeAndReturnView() {
        //GIVEN
        SkyXpCharacter blockedCharacter = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(blockedCharacter);
        when(communityFacade.getBlockedCharacters(CHARACTER_ID_1)).thenReturn(characterList);

        CharacterView characterView = createCharacterView(blockedCharacter);
        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getBlockedCharacters(CHARACTER_ID_1);
        //THEN
        verify(communityFacade).getBlockedCharacters(CHARACTER_ID_1);
        verify(characterViewConverter).convertDomain(characterList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetCharactersCanBeBlockedShouldCallFacadeAndReturnView() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(communityFacade.getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID_1)).thenReturn(characterList);

        CharacterView characterView = createCharacterView(character);
        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getCharactersCanBeBlocked(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID_1);
        //THEN
        verify(communityFacade).getCharactersCanBeBlocked(CHARACTER_NAME, CHARACTER_ID_1);
        verify(characterViewConverter).convertDomain(characterList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetCharactersCanBeFriendShouldCallFacadeAndReturnView() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(communityFacade.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID_1)).thenReturn(characterList);

        CharacterView characterView = createCharacterView(character);
        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getCharactersCanBeFriend(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID_1);
        //THEN
        verify(communityFacade).getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID_1);
        verify(characterViewConverter).convertDomain(characterList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetCharactersShouldCallFacadeAndReturnView() {
        //GIVEN
        Friendship friendship = createFriendship();
        List<Friendship> friendshipList = Arrays.asList(friendship);
        when(communityFacade.getFriends(CHARACTER_ID_1)).thenReturn(friendshipList);

        FriendView friendView = createFriendView();
        List<FriendView> friendViewList = Arrays.asList(friendView);
        when(friendViewConverter.convertDomain(friendshipList, CHARACTER_ID_1)).thenReturn(friendViewList);
        //WHEN
        List<FriendView> result = underTest.getFriends(CHARACTER_ID_1);
        //THEN
        verify(communityFacade).getFriends(CHARACTER_ID_1);
        verify(friendViewConverter).convertDomain(friendshipList, CHARACTER_ID_1);
        assertEquals(friendViewList, result);
    }

    @Test
    public void testGetReceivedFriendRequestsShouldCallFacadeAndReturnView() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        List<FriendRequest> friendRequestList = Arrays.asList(friendRequest);
        when(communityFacade.getReceivedFriendRequests(CHARACTER_ID_1)).thenReturn(friendRequestList);

        FriendRequestView view = createFriendRequestView();
        List<FriendRequestView> viewList = Arrays.asList(view);
        when(friendRequestViewConverter.convertDomain(friendRequestList)).thenReturn(viewList);
        //WHEN
        List<FriendRequestView> result = underTest.getReceivedFriendRequests(CHARACTER_ID_1);
        //THEN
        verify(communityFacade).getReceivedFriendRequests(CHARACTER_ID_1);
        verify(friendRequestViewConverter).convertDomain(friendRequestList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetSentFriendRequestsShouldCallFacadeAndReturnView() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();
        List<FriendRequest> friendRequestList = Arrays.asList(friendRequest);
        when(communityFacade.getSentFriendRequests(CHARACTER_ID_1)).thenReturn(friendRequestList);

        FriendRequestView view = createFriendRequestView();
        List<FriendRequestView> viewList = Arrays.asList(view);
        when(friendRequestViewConverter.convertDomain(friendRequestList)).thenReturn(viewList);
        //WHEN
        List<FriendRequestView> result = underTest.getSentFriendRequests(CHARACTER_ID_1);
        //THEN
        verify(communityFacade).getSentFriendRequests(CHARACTER_ID_1);
        verify(friendRequestViewConverter).convertDomain(friendRequestList);
        assertEquals(viewList, result);
    }
}
