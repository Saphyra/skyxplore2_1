package com.github.saphyra.skyxplore.userdata.community.blockedcharacter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.repository.BlockedCharacterDao;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    @Mock
    private BlockedCharacterDao blockedCharacterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private SkyXpCharacter character;

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
        when(blockedCharacterDao.getByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID)).thenReturn(Arrays.asList(blockedCharacter));
        //WHEN
        List<BlockedCharacter> result = underTest.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        //THEN
        verify(blockedCharacterDao).getByCharacterIdOrBlockedCharacterId(CHARACTER_ID, BLOCKED_CHARACTER_ID);
        assertThat(result).containsOnly(blockedCharacter);
    }

    @Test
    public void getBlockedCharacters() {
        //GIVEN
        BlockedCharacter blockedCharacter = BlockedCharacter.builder()
            .characterId("")
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();
        given(blockedCharacterDao.getBlockedCharacters(CHARACTER_ID)).willReturn(Arrays.asList(blockedCharacter));

        given(characterQueryService.findByCharacterIdValidated(BLOCKED_CHARACTER_ID)).willReturn(character);
        //WHEN
        List<SkyXpCharacter> result = underTest.getBlockedCharacters(CHARACTER_ID);
        //THEN
        assertThat(result).containsExactly(character);
    }

    private BlockedCharacter createBlockedCharacter() {
        return BlockedCharacter.builder()
            .characterId(CHARACTER_ID)
            .blockedCharacterId("")
            .build();
    }
}