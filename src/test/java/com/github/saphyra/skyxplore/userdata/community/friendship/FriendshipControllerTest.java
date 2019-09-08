package com.github.saphyra.skyxplore.userdata.community.friendship;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequestView;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendView;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendRequestQueryService;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendshipQueryService;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendshipServiceFacade;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendshipControllerTest {
    private static final String FRIEND_REQUEST_ID = "friend_request_id";
    private static final String CHARACTER_ID = "character_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String USER_ID = "user_id";
    private static final String FRIENDSHIP_ID = "friendship_id";
    private static final String CHARACTER_NAME = "character_name";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private FriendshipServiceFacade friendshipServiceFacade;

    @Mock
    private FriendshipQueryService friendshipQueryService;

    @Mock
    private FriendRequestQueryService friendRequestQueryService;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private FriendViewConverter friendViewConverter;

    @Mock
    private FriendRequestViewConverter friendRequestViewConverter;

    @Mock
    private FriendRequest friendRequest;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private CharacterView characterView;

    @Mock
    private Friendship friendship;

    @Mock
    private FriendRequestView friendRequestView;

    @Mock
    private FriendView friendView;

    @InjectMocks
    private FriendshipController underTest;

    @Test
    public void testAcceptFriendRequestShouldCallFacade() {
        //WHEN
        underTest.acceptFriendRequest(new OneStringParamRequest(FRIEND_REQUEST_ID), CHARACTER_ID);
        //THEN
        verify(friendshipServiceFacade).acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
    }

    @Test
    public void testAddFriendShouldCallFacade() {
        //WHEN
        underTest.addFriend(new OneStringParamRequest(FRIEND_ID), CHARACTER_ID, USER_ID);
        //THEN
        verify(friendshipServiceFacade).addFriendRequest(FRIEND_ID, CHARACTER_ID, USER_ID);
    }

    @Test
    public void testDeclineFriendRequestShouldCallFacade() {
        //WHEN
        underTest.declineFriendRequestMapping(new OneStringParamRequest(FRIEND_REQUEST_ID), CHARACTER_ID);
        //THEN
        verify(friendshipServiceFacade).declineFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
    }

    @Test
    public void testDeleteFriendShouldCallFacade() {
        //GIVEN
        underTest.deleteFriend(new OneStringParamRequest(FRIENDSHIP_ID), CHARACTER_ID);
        //WHEN
        verify(friendshipServiceFacade).deleteFriendship(FRIENDSHIP_ID, CHARACTER_ID);
    }

    @Test
    public void testGetCharactersCanBeFriendShouldCallFacadeAndReturnView() {
        //GIVEN
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterQueryService.getCharactersCanBeFriend(CHARACTER_NAME, CHARACTER_ID)).thenReturn(characterList);

        List<CharacterView> viewList = Arrays.asList(characterView);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getCharactersCanBeFriend(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID);
        //THEN
        verify(characterViewConverter).convertDomain(characterList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetFriendsShouldCallFacadeAndReturnView() {
        //GIVEN
        List<Friendship> friendshipList = Arrays.asList(friendship);
        when(friendshipQueryService.getFriends(CHARACTER_ID)).thenReturn(friendshipList);

        List<FriendView> friendViewList = Arrays.asList(friendView);
        when(friendViewConverter.convertDomain(friendshipList, CHARACTER_ID)).thenReturn(friendViewList);
        //WHEN
        List<FriendView> result = underTest.getFriends(CHARACTER_ID);
        //THEN
        verify(friendViewConverter).convertDomain(friendshipList, CHARACTER_ID);
        assertThat(result).isEqualTo(friendViewList);
    }

    @Test
    public void testGetReceivedFriendRequestsShouldCallFacadeAndReturnView() {
        //GIVEN
        List<FriendRequest> friendRequestList = Arrays.asList(friendRequest);
        when(friendRequestQueryService.getReceivedFriendRequests(CHARACTER_ID)).thenReturn(friendRequestList);

        List<FriendRequestView> viewList = Arrays.asList(friendRequestView);
        when(friendRequestViewConverter.convertDomain(friendRequestList)).thenReturn(viewList);
        //WHEN
        List<FriendRequestView> result = underTest.getReceivedFriendRequests(CHARACTER_ID);
        //THEN
        verify(friendRequestViewConverter).convertDomain(friendRequestList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void testGetSentFriendRequestsShouldCallFacadeAndReturnView() {
        //GIVEN
        List<FriendRequest> friendRequestList = Arrays.asList(friendRequest);
        when(friendRequestQueryService.getSentFriendRequests(CHARACTER_ID)).thenReturn(friendRequestList);

        List<FriendRequestView> viewList = Arrays.asList(friendRequestView);
        when(friendRequestViewConverter.convertDomain(friendRequestList)).thenReturn(viewList);
        //WHEN
        List<FriendRequestView> result = underTest.getSentFriendRequests(CHARACTER_ID);
        //THEN
        verify(friendRequestViewConverter).convertDomain(friendRequestList);
        assertThat(result).isEqualTo(viewList);
    }

    @Test
    public void getActiveFriends() {
        //GIVEN
        List<SkyXpCharacter> characters = Arrays.asList(character);
        given(friendshipServiceFacade.getActiveFriends(CHARACTER_ID)).willReturn(characters);

        List<CharacterView> characterViews = Arrays.asList(characterView);
        given(characterViewConverter.convertDomain(characters)).willReturn(characterViews);
        //WHEN
        List<CharacterView> result = underTest.getActiveFriends(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(characterViews);
    }
}