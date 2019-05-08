package com.github.saphyra.skyxplore.character;

import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.character.repository.CharacterDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterDeleteServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @InjectMocks
    private CharacterDeleteService underTest;

    @Test
    public void testDeleteCharacterShouldDelete() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder()
            .characterId(CHARACTER_ID)
            .build();

        when(characterQueryService.findCharacterByIdAuthorized(CHARACTER_ID, USER_ID)).thenReturn(character);
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID, USER_ID);
        verify(characterDao).deleteById(CHARACTER_ID);
    }
}