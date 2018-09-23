package skyxplore.service.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.BlockedCharacterDao;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.exception.BlockedCharacterNotFoundException;
import skyxplore.exception.CharacterAlreadyBlockedException;
import skyxplore.exception.base.BadRequestException;
import skyxplore.service.character.CharacterQueryService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class BlockCharacterServiceTest {
    @Mock
    private  BlockedCharacterDao blockedCharacterDao;

    @Mock
    private  BlockedCharacterQueryService blockedCharacterQueryService;

    @Mock
    private  CharacterQueryService characterQueryService;

    @Mock
    private  FriendshipService friendshipService;

    @InjectMocks
    private BlockCharacterService underTest;

    @Test(expected = BlockedCharacterNotFoundException.class)
    public void testAllowBlockedCharacterShouldThrowExceptionWhenNotFound(){
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(null);
        //WHEN
        underTest.allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
    }

    @Test
    public void testAllowBlockedCharacterShouldDeleteBlock(){
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(blockedCharacter);
        //WHEN
        underTest.allowBlockedCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
        //THEN
        verify(blockedCharacterQueryService).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        verify(blockedCharacterDao).delete(blockedCharacter);
    }

    @Test(expected = BadRequestException.class)
    public void testBlockCharacterShouldThrowExceptionWhenIdsEqual(){
        underTest.blockCharacter(CHARACTER_ID_1, CHARACTER_ID_1);
    }

    @Test(expected = CharacterAlreadyBlockedException.class)
    public void testBlockCharacterShouldThrowExceptionWhenAlreadyBlocked(){
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(new BlockedCharacter());
        //WHEN
        underTest.blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
    }

    @Test
    public void testBlockCharacterShouldBlock(){
        //GIVEN
        when(blockedCharacterQueryService.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(null);
        //WHEN
        underTest.blockCharacter(BLOCKED_CHARACTER_ID, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).findByCharacterId(BLOCKED_CHARACTER_ID);
        verify(blockedCharacterQueryService).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        verify(friendshipService).removeContactsBetween(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);

        ArgumentCaptor<BlockedCharacter> argumentCaptor = ArgumentCaptor.forClass(BlockedCharacter.class);
        verify(blockedCharacterDao).save(argumentCaptor.capture());
        assertEquals(CHARACTER_ID_1, argumentCaptor.getValue().getCharacterId());
        assertEquals(BLOCKED_CHARACTER_ID, argumentCaptor.getValue().getBlockedCharacterId());
    }
}