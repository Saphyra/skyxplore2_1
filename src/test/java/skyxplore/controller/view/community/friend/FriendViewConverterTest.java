package skyxplore.controller.view.community.friend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import org.github.saphyra.skyxplore.auth.domain.SkyXpAccessToken;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.service.character.CharacterQueryService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class FriendViewConverterTest {
    @Mock
    private AccessTokenDao accessTokenDao;

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
        when(accessTokenDao.findByCharacterId(FRIEND_ID)).thenReturn(Optional.of(new SkyXpAccessToken()));
        //WHEN
        List<FriendView> result = underTest.convertDomain(friendshipList, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
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
        friendship.setFriendId(CHARACTER_ID_1);
        friendship.setCharacterId(FRIEND_ID);
        List<Friendship> friendshipList = Arrays.asList(friendship);

        SkyXpCharacter friend = new SkyXpCharacter();
        friend.setCharacterName(FRIEND_NAME);

        when(characterQueryService.findByCharacterId(FRIEND_ID)).thenReturn(friend);
        when(accessTokenDao.findByCharacterId(FRIEND_ID)).thenReturn(Optional.of(new SkyXpAccessToken()));
        //WHEN
        List<FriendView> result = underTest.convertDomain(friendshipList, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        assertEquals(1, result.size());
        FriendView view = result.get(0);
        assertEquals(FRIENDSHIP_ID, view.getFriendshipId());
        assertEquals(FRIEND_ID, view.getFriendId());
        assertEquals(FRIEND_NAME, view.getFriendName());
        assertTrue(view.getActive());
    }
}
