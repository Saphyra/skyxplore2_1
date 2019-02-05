package skyxplore.service.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.BlockedCharacterDao;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterQueryServiceTest {
    @Mock
    private BlockedCharacterDao blockedCharacterDao;

    @InjectMocks
    private BlockedCharacterQueryService underTest;

    @Test
    public void testFindByCharacterIdAndBlockedCharacterIdShouldQueryAndReturn() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterDao.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(blockedCharacter);
        //WHEN
        BlockedCharacter result = underTest.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterDao).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        assertEquals(blockedCharacter, result);
    }

    @Test
    public void testFindByCharacterIdOrBlockedCharacterIdShouldQueryAndReturn() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterDao.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID)).thenReturn(Arrays.asList(blockedCharacter));
        //WHEN
        List<BlockedCharacter> result = underTest.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterDao).findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, BLOCKED_CHARACTER_ID);
        assertEquals(blockedCharacter, result.get(0));
    }

    @Test
    public void testGetBlockedCharactersOf() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterDao.getBlockedCharactersOf(CHARACTER_ID_1)).thenReturn(Arrays.asList(blockedCharacter));
        //WHEN
        List<BlockedCharacter> result = underTest.getBlockedCharactersOf(CHARACTER_ID_1);
        //THEN
        verify(blockedCharacterDao).getBlockedCharactersOf(CHARACTER_ID_1);
        assertEquals(blockedCharacter, result.get(0));
    }
}