package org.github.saphyra.skyxplore.community.blockedcharacter.repository;

import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.event.CharacterDeletedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        underTest.characterDeletedEventProcessor(new CharacterDeletedEvent(CHARACTER_ID));
        //THEN
        verify(blockedCharacterRepository).deleteByCharacterId(CHARACTER_ID);
    }

    @Test
    public void testFindByCharacterIdAndBlockedCharacterIdShouldReturnDomain() {
        //GIVEN
        Optional<BlockedCharacterEntity> entity = Optional.of(createBlockedCharacterEntity());
        when(blockedCharacterRepository.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(entity);

        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterConverter.convertEntity(entity)).thenReturn(Optional.of(blockedCharacter));
        //WHEN
        Optional<BlockedCharacter> result = underTest.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterRepository).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        verify(blockedCharacterConverter).convertEntity(entity);
        assertThat(result).contains(blockedCharacter);
    }

    @Test
    public void testGetByCharacterIdOrBlockedCharacterIdShouldReturnDomain() {
        //GIVEN
        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        List<BlockedCharacterEntity> entityList = Arrays.asList(entity);
        when(blockedCharacterRepository.getByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(entityList);

        BlockedCharacter blockedCharacter = createBlockedCharacter();
        List<BlockedCharacter> blockedCharacterList = Arrays.asList(blockedCharacter);
        when(blockedCharacterConverter.convertEntity(entityList)).thenReturn(blockedCharacterList);
        //WHEN
        List<BlockedCharacter> result = underTest.getByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterRepository).getByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        verify(blockedCharacterConverter).convertEntity(entityList);
        assertThat(result).isEqualTo(blockedCharacterList);
    }

    @Test
    public void testGetBlockedCharactersOfShouldReturnDomain() {
        //GIVEN
        BlockedCharacterEntity entity = createBlockedCharacterEntity();
        List<BlockedCharacterEntity> entityList = Arrays.asList(entity);
        when(blockedCharacterRepository.getByCharacterId(CHARACTER_ID)).thenReturn(entityList);

        BlockedCharacter blockedCharacter = createBlockedCharacter();
        List<BlockedCharacter> blockedCharacterList = Arrays.asList(blockedCharacter);
        when(blockedCharacterConverter.convertEntity(entityList)).thenReturn(blockedCharacterList);
        //WHEN
        List<BlockedCharacter> result = underTest.getBlockedCharacters(CHARACTER_ID);
        //THEN
        verify(blockedCharacterRepository).getByCharacterId(CHARACTER_ID);
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
