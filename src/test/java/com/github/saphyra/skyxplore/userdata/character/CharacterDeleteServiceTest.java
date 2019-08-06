package com.github.saphyra.skyxplore.userdata.character;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;

@RunWith(MockitoJUnitRunner.class)
public class CharacterDeleteServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String USER_ID = "user_id";

    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private CharacterDeleteService underTest;

    @Test
    public void testDeleteCharacterShouldDelete() {
        //GIVEN
        given(character.getCharacterId()).willReturn(CHARACTER_ID);
        when(characterQueryService.findCharacterByIdAuthorized(CHARACTER_ID, USER_ID)).thenReturn(character);
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterQueryService).findCharacterByIdAuthorized(CHARACTER_ID, USER_ID);
        verify(characterDao).deleteById(CHARACTER_ID);
    }
}