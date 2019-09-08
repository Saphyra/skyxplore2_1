package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipServiceFacadeTest {
    private static final String FRIEND_REQUEST_ID = "friend-request-id";
    private static final String CHARACTER_ID = "character-id";
    private static final String FRIEND_ID = "friend-id";
    private static final String USER_ID = "user-id";
    private static final String FRIENDSHIP_ID = "friendship-id";
    private static final String BLOCKED_CHARACTER_ID = "blocked-character-id";

    @Mock
    private AcceptFriendRequestService acceptFriendRequestService;

    @Mock
    private ActiveFriendsQueryService activeFriendsQueryService;

    @Mock
    private AddFriendRequestService addFriendRequestService;

    @Mock
    private ContactRemovalService contactRemovalService;

    @Mock
    private DeclineFriendRequestService declineFriendRequestService;

    @Mock
    private DeleteFriendshipService deleteFriendshipService;

    @InjectMocks
    private FriendshipServiceFacade underTest;

    @Mock
    private SkyXpCharacter character;

    @Test
    public void acceptFriendRequest() {
        //WHEN
        underTest.acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
        //THEN
        verify(acceptFriendRequestService).acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
    }

    @Test
    public void getActiveFriends() {
        //GIVEN
        given(activeFriendsQueryService.getActiveFriends(CHARACTER_ID)).willReturn(Arrays.asList(character));
        //WHEN
        List<SkyXpCharacter> result = underTest.getActiveFriends(CHARACTER_ID);
        //THEN
        assertThat(result).containsExactly(character);
    }

    @Test
    public void addFriendRequest() {
        //WHEN
        underTest.addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
        //THEN
        verify(addFriendRequestService).addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
    }

    @Test
    public void declineFriendRequest() {
        //WHEN
        underTest.declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
        //THEN
        verify(declineFriendRequestService).declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
    }

    @Test
    public void deleteFriendship() {
        //WHEN
        underTest.deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID);
        //THEN
        verify(deleteFriendshipService).deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID);
    }

    @Test
    public void removeContactsBetween() {
        //GIVEN
        underTest.removeContactsBetween(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(contactRemovalService).removeContactsBetween(CHARACTER_ID, BLOCKED_CHARACTER_ID);
    }
}