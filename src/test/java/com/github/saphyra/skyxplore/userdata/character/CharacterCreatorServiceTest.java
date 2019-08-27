package com.github.saphyra.skyxplore.userdata.character;

import static com.github.saphyra.testing.ExceptionValidator.verifyException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.LockedException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.userdata.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.userdata.character.domain.CreateCharacterRequest;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;

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

    @Test
    public void createCharacter_throwException_nameAlreadyExists(){
        //GIVEN
        given(characterQueryService.isCharNameExists(CHARACTER_NAME)).willReturn(true);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.createCharacter(new CreateCharacterRequest(CHARACTER_NAME), USER_ID));
        //THEN
        verifyException(ex, LockedException.class, ErrorCode.CHARACTER_NAME_ALREADY_EXISTS);
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