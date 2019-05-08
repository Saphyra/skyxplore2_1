package com.github.saphyra.skyxplore.character;

import com.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import com.github.saphyra.skyxplore.character.domain.CreateCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.exception.CharacterNameAlreadyExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CharacterCreatorServiceTest {
    private static final String CHARACTER_NAME = "character_name";
    private static final String USER_ID = "user_id";

    @Mock
    private CharacterNameCache characterNameCache;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private NewCharacterGenerator newCharacterGenerator;

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
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        given(newCharacterGenerator.createCharacter(USER_ID, CHARACTER_NAME)).willReturn(character);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(new CreateCharacterRequest(CHARACTER_NAME), USER_ID);
        //THEN
        assertThat(result).isEqualTo(character);
        verify(characterNameCache).invalidate(CHARACTER_NAME);
    }
}