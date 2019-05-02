package org.github.saphyra.skyxplore.community.friendship;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipFacadeTest {
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String USER_ID = "user_id";
    private static final String FRIENDSHIP_ID = "friendship_id";
    private static final String CHARACTER_NAME = "character_name";

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @InjectMocks
    private FriendshipFacade underTest;

    @Test
    public void testAcceptFriendRequestShouldCallService() {
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
        //THEN
        verify(friendshipService).acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
    }

    @Test
    public void testAddFriendRequestShouldCallService() {
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
        //THEN
        verify(friendshipService).addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
    }

    @Test
    public void testDeclineFriendRequestShouldCallService() {
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
        //THEN
        verify(friendshipService).declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
    }

    @Test
    public void testDeleteFriendshipShouldCallService() {
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID);
        //THEN
        verify(friendshipService).deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID);
    }

    @Test
    public void testGetCharactersCanBeFriendsShouldCallServiceAndReturn() {
        ///GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        when(characterQueryService.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID)).thenReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID);
        //THEN
        verify(characterQueryService).getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID);
        assertThat(result).containsOnly(character);
    }

    @Test
    public void testGetFriendsShouldCallServiceAndReturn() {
        //GIVEN
        Friendship friendship = Friendship.builder().build();
        when(friendshipQueryService.getFriends(CHARACTER_ID)).thenReturn(Arrays.asList(friendship));
        //WHEN
        List<Friendship> result = underTest.getFriends(CHARACTER_ID);
        //THEN
        verify(friendshipQueryService).getFriends(CHARACTER_ID);
        assertThat(result).containsOnly(friendship);
    }

    @Test
    public void testGetNumberOfFriendRequestsShouldCallServiceAndResult() {
        //GIVEN
        when(friendshipQueryService.getNumberOfFriendRequests(CHARACTER_ID)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfFriendRequests(CHARACTER_ID);
        //THEN
        verify(friendshipQueryService).getNumberOfFriendRequests(CHARACTER_ID);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testGetReceivedFriendRequestsShouldCallServiceAndReturn() {
        //GIVEN
        FriendRequest friendRequest = FriendRequest.builder().build();
        when(friendshipQueryService.getReceivedFriendRequests(CHARACTER_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getReceivedFriendRequests(CHARACTER_ID);
        //THEN
        verify(friendshipQueryService).getReceivedFriendRequests(CHARACTER_ID);
        assertThat(result).containsOnly(friendRequest);
    }

    @Test
    public void testGetSentFriendRequestsShouldCallServiceAndReturn() {
        //GIVEN
        FriendRequest friendRequest = FriendRequest.builder().build();
        when(friendshipQueryService.getSentFriendRequests(CHARACTER_ID)).thenReturn(Arrays.asList(friendRequest));
        //WHEN
        List<FriendRequest> result = underTest.getSentFriendRequests(CHARACTER_ID);
        //THEN
        verify(friendshipQueryService).getSentFriendRequests(CHARACTER_ID);
        assertThat(result).containsOnly(friendRequest);
    }
}