package org.github.saphyra.skyxplore.common.domain.character;


import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CharacterViewConverterTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";

    @InjectMocks
    private CharacterViewConverter underTest;

    @Test
    public void testConvertShouldReturnView() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(CHARACTER_ID)
            .characterName(CHARACTER_NAME)
            .build();
        //WHEN
        CharacterView result = underTest.convertDomain(character);
        //THEN
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getCharacterName()).isEqualTo(CHARACTER_NAME);
    }
}
