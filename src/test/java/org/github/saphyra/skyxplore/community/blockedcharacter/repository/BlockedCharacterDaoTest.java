package org.github.saphyra.skyxplore.community.blockedcharacter.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterDaoTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

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
        assertThat(result).isEqualTo(blockedCharacter);
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
        assertThat(result).isEqualTo(blockedCharacterList);
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
        assertThat(result).isEqualTo(blockedCharacterList);
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

    private BlockedCharacter createBlockedCharacter() {
        return BlockedCharacter.builder()
            .characterId(CHARACTER_ID)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();
    }

    private BlockedCharacterEntity createBlockedCharacterEntity() {
        return BlockedCharacterEntity.builder()
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .characterId(CHARACTER_ID)
            .build();
    }
}