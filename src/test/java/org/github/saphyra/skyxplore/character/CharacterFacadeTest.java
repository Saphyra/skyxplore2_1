package org.github.saphyra.skyxplore.character;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.domain.request.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.domain.request.RenameCharacterRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CharacterFacadeTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String NEW_CHARACTER_NAME = "new_character_name";
    private static final String USER_ID = "user_id";
    private static final String EQUIPMENT = "eqipment";
    private static final Integer MONEY = 5;

    @Mock
    private BuyItemService buyItemService;

    @Mock
    private CharacterCreatorService characterCreatorService;

    @Mock
    private CharacterDeleteService characterDeleteService;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private CharacterRenameService characterRenameService;

    @Mock
    private CharacterSelectService characterSelectService;

    @InjectMocks
    private CharacterFacade underTest;

    @Test
    public void testBuyItemsShouldCallService() {
        //GIVEN
        Map<String, Integer> map = new HashMap<>();
        //WHEN
        underTest.buyItems(map, CHARACTER_ID);
        //THEN
        verify(buyItemService).buyItems(map, CHARACTER_ID);
    }

    @Test
    public void testCreateCharacterShouldCallService() {
        //GIVEN
        CreateCharacterRequest request = new CreateCharacterRequest(NEW_CHARACTER_NAME);

        SkyXpCharacter character = SkyXpCharacter.builder().userId(USER_ID).build();
        when(characterCreatorService.createCharacter(request, USER_ID)).thenReturn(character);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(request, USER_ID);
        //THEN
        verify(characterCreatorService).createCharacter(request, USER_ID);
        assertThat(result).isEqualTo(character);
    }

    @Test
    public void testDeleteCharacterShouldCallService() {
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterDeleteService).deleteCharacter(CHARACTER_ID, USER_ID);
    }

    @Test
    public void testGetCharactersByUserIdShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().userId(USER_ID).build();
        List<SkyXpCharacter> characters = Arrays.asList(character);

        when(characterQueryService.getCharactersByUserId(USER_ID)).thenReturn(characters);
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersByUserId(USER_ID);
        //THEN
        verify(characterQueryService).getCharactersByUserId(USER_ID);
        assertThat(result).containsOnly(character);
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldCallServiceAndReturn() {
        //GIVEN
        List<String> equipments = Arrays.asList(EQUIPMENT);
        when(characterQueryService.getEquipmentsOfCharacter(CHARACTER_ID)).thenReturn(equipments);
        //WHEN
        List<String> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID);
        //THEN
        verify(characterQueryService).getEquipmentsOfCharacter(CHARACTER_ID);
        assertThat(result).isEqualTo(equipments);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallServiceAndReturn() {
        //GIVEN
        when(characterQueryService.getMoneyOfCharacter(CHARACTER_ID)).thenReturn(MONEY);
        //WHEN
        Integer result = underTest.getMoneyOfCharacter(CHARACTER_ID);
        //THEN
        verify(characterQueryService).getMoneyOfCharacter(CHARACTER_ID);
        assertThat(result).isEqualTo(MONEY);
    }

    @Test
    public void testRenameCharacterShouldCallService() {
        //GIVEN
        RenameCharacterRequest request = new RenameCharacterRequest(NEW_CHARACTER_NAME, CHARACTER_ID);
        SkyXpCharacter character = SkyXpCharacter.builder().userId(USER_ID).build();
        when(characterRenameService.renameCharacter(request, USER_ID)).thenReturn(character);
        //WHEN
        SkyXpCharacter result = underTest.renameCharacter(request, USER_ID);
        //THEN
        verify(characterRenameService).renameCharacter(request, USER_ID);
        assertThat(result).isEqualTo(character);
    }

    @Test
    public void testSelectCharacter() {
        //WHEN
        underTest.selectCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterSelectService).selectCharacter(CHARACTER_ID, USER_ID);
    }
}
