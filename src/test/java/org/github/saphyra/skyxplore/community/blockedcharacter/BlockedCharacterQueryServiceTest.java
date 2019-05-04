package org.github.saphyra.skyxplore.community.blockedcharacter;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.blockedcharacter.repository.BlockedCharacterDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    @Mock
    private BlockedCharacterDao blockedCharacterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private BlockedCharacterQueryService underTest;

    @Test
    public void testFindByCharacterIdAndBlockedCharacterIdShouldQueryAndReturn() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterDao.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Optional.of(blockedCharacter));
        //WHEN
        Optional<BlockedCharacter> result = underTest.findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterDao).findByCharacterIdAndBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        assertThat(result).contains(blockedCharacter);
    }

    @Test
    public void testFindByCharacterIdOrBlockedCharacterIdShouldQueryAndReturn() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterDao.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Arrays.asList(blockedCharacter));
        //WHEN
        List<BlockedCharacter> result = underTest.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterDao).findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        assertThat(result).containsOnly(blockedCharacter);
    }

    @Test
    public void testGetBlockedCharactersOf() {
        //GIVEN
        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID)).thenReturn(Arrays.asList(blockedCharacter));
        //WHEN
        List<BlockedCharacter> result = underTest.getBlockedCharactersOf(CHARACTER_ID);
        //THEN
        verify(blockedCharacterDao).getBlockedCharacters(CHARACTER_ID);
        assertThat(result).containsOnly(blockedCharacter);
    }

    @Test
    public void getBlockedCharacters(){
        //GIVEN
        BlockedCharacter blockedCharacter = BlockedCharacter.builder()
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();
        given(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID)).willReturn(Arrays.asList(blockedCharacter));

        SkyXpCharacter character = SkyXpCharacter.builder().build();
        given(characterQueryService.findByCharacterId(BLOCKED_CHARACTER_ID)).willReturn(character);
        //WHEN
        List<SkyXpCharacter> result = underTest.getBlockedCharacters(CHARACTER_ID);
        //THEN
        assertThat(result).containsExactly(character);
    }

    private BlockedCharacter createBlockedCharacter() {
        return BlockedCharacter.builder()
            .characterId(CHARACTER_ID)
            .build();
    }
}