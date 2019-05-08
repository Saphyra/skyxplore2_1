package com.github.saphyra.skyxplore.community.blockedcharacter.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

import com.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BlockedCharacterConverterTest {
    private static final Long BLOCKED_CHARACTER_ENTITY_ID = 5L;
    private static final String BLOCKED_CHARACTER_ID = "blocked_character_id";
    private static final String CHARACTER_ID = "character_id";
    @InjectMocks
    private BlockedCharacterConverter underTest;

    @Test
    public void testConvertEntityShouldReturnNullWhenNull() {
        //GIVEN
        BlockedCharacterEntity entity = null;
        //WHEN
        BlockedCharacter result = underTest.convertEntity(entity);
        //THEN
        assertNull(result);
    }

    @Test
    public void testConvertEntityShouldConvert() {
        //GIVEN
        BlockedCharacterEntity entity = BlockedCharacterEntity.builder()
            .blockedCharacterEntityId(BLOCKED_CHARACTER_ENTITY_ID)
            .characterId(CHARACTER_ID)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();
        //WHEN
        BlockedCharacter result = underTest.convertEntity(entity);
        //THEN
        assertThat(result.getBlockedCharacterEntityId()).isEqualTo(BLOCKED_CHARACTER_ENTITY_ID);
        assertThat(result.getBlockedCharacterId()).isEqualTo(BLOCKED_CHARACTER_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
    }

    @Test
    public void testConvertDomainShouldConvert() {
        //GIVEN
        BlockedCharacter character = BlockedCharacter.builder()
            .blockedCharacterEntityId(BLOCKED_CHARACTER_ENTITY_ID)
            .characterId(CHARACTER_ID)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .build();
        //WHEN
        BlockedCharacterEntity result = underTest.convertDomain(character);
        //THEN
        assertThat(result.getBlockedCharacterEntityId()).isEqualTo(BLOCKED_CHARACTER_ENTITY_ID);
        assertThat(result.getBlockedCharacterId()).isEqualTo(BLOCKED_CHARACTER_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
    }
}
