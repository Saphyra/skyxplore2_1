package skyxplore.controller;

import static org.mockito.Mockito.verify;
import static skyxplore.testutil.TestUtils.BLOCKED_CHARACTER_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID;
import static skyxplore.testutil.TestUtils.FRIEND_ID;
import static skyxplore.testutil.TestUtils.FRIEND_REQUEST_ID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.friend.FriendViewConverter;
import skyxplore.controller.view.community.friendrequest.FriendRequestViewConverter;
import skyxplore.service.CommunityFacade;

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
    public void testAcceptFriendRequestShouldCallFacade(){
        //WHEN
        underTest.acceptFriendRequest(new OneStringParamRequest(FRIEND_REQUEST_ID), CHARACTER_ID);
        //THEN
        verify(communityFacade).acceptFriendRequest(FRIEND_REQUEST_ID, CHARACTER_ID);
    }

    @Test
    public void testAddFriendShouldCallFacade(){
        //WHEN
        underTest.addFriend(new OneStringParamRequest(FRIEND_ID), CHARACTER_ID);
        //THEN
        verify(communityFacade).addFriendRequest(FRIEND_ID, CHARACTER_ID);
    }

    @Test
    public void testAllowBlockedCharacterShouldCallFacade(){
        //WHEN
        underTest.allowBlockedCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID);
        //THEN
        verify(communityFacade).allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }

    @Test
    public void testBlockCharacterShouldCallFacade(){
        //WHEN
        underTest.blockCharacter(new OneStringParamRequest(BLOCKED_CHARACTER_ID), CHARACTER_ID);
        //THEN
        verify(communityFacade).blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID);
    }
}
