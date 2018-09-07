package skyxplore.dataaccess.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.repository.BlockedCharacterRepository;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacterConverter;
import skyxplore.domain.community.blockedcharacter.BlockedCharacterEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterDaoTest {
    @Mock
    private BlockedCharacterConverter blockedCharacterConverter;

    @Mock
    private BlockedCharacterRepository blockedCharacterRepository;

    @InjectMocks
    private BlockedCharacterDao underTest;

    @Test
    public void testDeleteShouldCallRepository() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();

        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        when(blockedCharacterConverter.convertDomain(blockedCharacter)).thenReturn(entity);
        //WHEN
        underTest.delete(blockedCharacter);
        //THEN
        verify(blockedCharacterConverter).convertDomain(blockedCharacter);
        verify(blockedCharacterRepository).delete(entity);
    }

    @Test
    public void testDeleteByCharacterIdShouldCallRepository() {
        //WHEN
        underTest.deleteByCharacterId(CHARACTER_ID);
        //THEN
        verify(blockedCharacterRepository).deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testFindByCharacterIdAndBlockedCharacterIdShouldReturnDomain() {
        //GIVEN
        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        when(blockedCharacterRepository.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(entity);

        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterConverter.convertEntity(entity)).thenReturn(blockedCharacter);
        //WHEN
        BlockedCharacter result = underTest.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterRepository).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        verify(blockedCharacterConverter).convertEntity(entity);
        assertEquals(blockedCharacter, result);
    }

    @Test
    public void testFindByCharacterIdOrBlockedCharacterIdShouldReturnDomain() {
        //GIVEN
        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        List<BlockedCharacterEntity> entityList = Arrays.asList(entity);
        when(blockedCharacterRepository.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(entityList);

        BlockedCharacter blockedCharacter = createBlockedCharacter();
        List<BlockedCharacter> blockedCharacterList = Arrays.asList(blockedCharacter);
        when(blockedCharacterConverter.convertEntity(entityList)).thenReturn(blockedCharacterList);
        //WHEN
        List<BlockedCharacter> result = underTest.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterRepository).findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        verify(blockedCharacterConverter).convertEntity(entityList);
        assertEquals(blockedCharacterList, result);
    }

    @Test
    public void testGetBlockedCharactersOfShouldReturnDomain() {
        //GIVEN
        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        List<BlockedCharacterEntity> entityList = Arrays.asList(entity);
        when(blockedCharacterRepository.findByCharacterId(CHARACTER_ID)).thenReturn(entityList);

        BlockedCharacter blockedCharacter = createBlockedCharacter();
        List<BlockedCharacter> blockedCharacterList = Arrays.asList(blockedCharacter);
        when(blockedCharacterConverter.convertEntity(entityList)).thenReturn(blockedCharacterList);
        //WHEN
        List<BlockedCharacter> result = underTest.getBlockedCharactersOf(CHARACTER_ID);
        //THEN
        verify(blockedCharacterRepository).findByCharacterId(CHARACTER_ID);
        verify(blockedCharacterConverter).convertEntity(entityList);
        assertEquals(blockedCharacterList, result);
    }

    @Test
    public void testSaveShouldCallRepository() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();

        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        when(blockedCharacterConverter.convertDomain(blockedCharacter)).thenReturn(entity);
        //WHEN
        underTest.save(blockedCharacter);
        //THEN
        verify(blockedCharacterConverter).convertDomain(blockedCharacter);
        verify(blockedCharacterRepository).save(entity);
    }
}
