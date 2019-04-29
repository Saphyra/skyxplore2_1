package skyxplore.controller.view.community.friendrequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class FriendRequestViewConverterTest {
    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private FriendRequestViewConverter underTest;

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        FriendRequest friendRequest = createFriendRequest();

        SkyXpCharacter character = createCharacter();

        character.setCharacterId(FRIEND_ID);
        character.setCharacterName(FRIEND_NAME);
        when(characterQueryService.findByCharacterId(FRIEND_ID)).thenReturn(character);
        //WHEN
        FriendRequestView result = underTest.convertDomain(friendRequest);
        //THEN
        verify(characterQueryService).findByCharacterId(FRIEND_ID);
        assertEquals(CHARACTER_ID_1, result.getCharacterId());
        assertEquals(FRIEND_REQUEST_ID, result.getFriendRequestId());
        assertEquals(FRIEND_ID, result.getFriendId());
        assertEquals(FRIEND_NAME, result.getFriendName());
    }
}
