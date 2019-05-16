package com.github.saphyra.skyxplore.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.character.domain.CreateCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.exception.CharacterNameAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class CharacterCreatorServiceTest {
    private static final String CHARACTER_NAME = "character_name";
    private static final String USER_ID = "user_id";

    @Mock
    private CharacterNameExistsCache characterNameExistsCache;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private NewCharacterGenerator newCharacterGenerator;

    @Mock
    private SkyXpCharacter character;

    @InjectMocks
    private CharacterCreatorService underTest;

    @Test(expected = CharacterNameAlreadyExistsException.class)
    public void createCharacter_throwException_nameAlreadyExists(){
        //GIVEN
        given(characterQueryService.isCharNameExists(CHARACTER_NAME)).willReturn(true);
        //WHEN
        underTest.createCharacter(new CreateCharacterRequest(CHARACTER_NAME), USER_ID);
    }

    @Test
    public void createCharacter(){
        //GIVEN
        given(characterQueryService.isCharNameExists(CHARACTER_NAME)).willReturn(false);
        given(newCharacterGenerator.createCharacter(USER_ID, CHARACTER_NAME)).willReturn(character);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(new CreateCharacterRequest(CHARACTER_NAME), USER_ID);
        //THEN
        assertThat(result).isEqualTo(character);
        verify(characterNameExistsCache).invalidate(CHARACTER_NAME);
    }
}