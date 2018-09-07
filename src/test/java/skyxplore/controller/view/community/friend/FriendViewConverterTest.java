package skyxplore.controller.view.community.friend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.service.AccessTokenFacade;
import skyxplore.service.character.CharacterQueryService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class FriendViewConverterTest {
    @Mock
    private AccessTokenFacade accessTokenFacade;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private FriendViewConverter underTest;

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        Friendship friendship = createFriendship();
        List<Friendship> friendshipList = Arrays.asList(friendship);

        SkyXpCharacter friend = new SkyXpCharacter();
        friend.setCharacterName(FRIEND_NAME);

        when(characterQueryService.findByCharacterId(FRIEND_ID)).thenReturn(friend);
        when(accessTokenFacade.isCharacterActive(FRIEND_ID)).thenReturn(true);
        //WHEN
        List<FriendView> result = underTest.convertDomain(friendshipList, CHARACTER_ID);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        verify(accessTokenFacade).isCharacterActive(FRIEND_ID);
        assertEquals(1, result.size());
        FriendView view = result.get(0);
        assertEquals(FRIENDSHIP_ID, view.getFriendshipId());
        assertEquals(FRIEND_ID, view.getFriendId());
        assertEquals(FRIEND_NAME, view.getFriendName());
        assertTrue(view.getActive());
    }

    @Test
    public void testConvertDomainShouldSwapIdsAndConvert() {
        //GIVEN
        Friendship friendship = createFriendship();
        friendship.setFriendId(CHARACTER_ID);
        friendship.setCharacterId(FRIEND_ID);
        List<Friendship> friendshipList = Arrays.asList(friendship);

        SkyXpCharacter friend = new SkyXpCharacter();
        friend.setCharacterName(FRIEND_NAME);

        when(characterQueryService.findByCharacterId(FRIEND_ID)).thenReturn(friend);
        when(accessTokenFacade.isCharacterActive(FRIEND_ID)).thenReturn(true);
        //WHEN
        List<FriendView> result = underTest.convertDomain(friendshipList, CHARACTER_ID);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        verify(accessTokenFacade).isCharacterActive(FRIEND_ID);
        assertEquals(1, result.size());
        FriendView view = result.get(0);
        assertEquals(FRIENDSHIP_ID, view.getFriendshipId());
        assertEquals(FRIEND_ID, view.getFriendId());
        assertEquals(FRIEND_NAME, view.getFriendName());
        assertTrue(view.getActive());
    }
}
