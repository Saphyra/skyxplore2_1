package com.github.saphyra.skyxplore.character;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.cache.CharacterNameExistsCache;
import com.github.saphyra.skyxplore.character.domain.CreateCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.RenameCharacterRequest;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String USER_ID = "user_id";
    private static final String EQUIP_ITEM_ID = "equip_item_id";
    private static final int MONEY = 5;
    private static final String NEW_CHARACTER_NAME = "new_character_name";

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

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private CharacterNameExistsCache characterNameExistsCache;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private CharacterViewQueryService characterViewQueryService;

    @InjectMocks
    private CharacterController underTest;

    @Test
    public void testBuyEquipmentsShouldCallFacade() {
        //GIVEN
        HashMap<String, Integer> inputMap = new HashMap<>();
        //WHEN
        underTest.buyEquipments(inputMap, CHARACTER_ID);
        //THEN
        verify(buyItemService).buyItems(inputMap, CHARACTER_ID);
    }

    @Test
    public void testCreateCharacterShouldCallFacadeAndInvalidateCache() {
        //GIVEN
        CreateCharacterRequest request = new CreateCharacterRequest(CHARACTER_NAME);

        SkyXpCharacter character = SkyXpCharacter.builder().build();
        when(characterCreatorService.createCharacter(request, USER_ID)).thenReturn(character);

        CharacterView characterView = createCharacterView(character);
        when(characterViewConverter.convertDomain(character)).thenReturn(characterView);
        //WHEN
        CharacterView result = underTest.createCharacter(request, USER_ID);
        //THEN
        verify(characterCreatorService).createCharacter(request, USER_ID);
        assertThat(result).isEqualTo(characterView);
    }

    @Test
    public void testDeleteCharacterShouldCallFacade() {
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterDeleteService).deleteCharacter(CHARACTER_ID, USER_ID);
    }

    @Test
    public void testGetCharactersShouldCallFacadeAndConverterAndReturnResponse() {
        //GIVEN
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        CharacterView view = createCharacterView(character);
        List<CharacterView> viewList = Arrays.asList(view);

        when(characterViewQueryService.getCharactersByUserId(USER_ID)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getCharacters(USER_ID);
        //THEN
        assertThat(result).containsOnly(view);
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldCallFacadeAndReturnResponse() {
        //GIVEN
        List<String> equipments = Arrays.asList(EQUIP_ITEM_ID);
        when(characterQueryService.getEquipmentsOfCharacter(CHARACTER_ID)).thenReturn(equipments);
        //WHEN
        List<String> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(equipments);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallFacadeAndReturnResponse() {
        //GIVEN
        when(characterQueryService.getMoneyOfCharacter(CHARACTER_ID)).thenReturn(MONEY);
        //WHEN
        Integer result = underTest.getMoney(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(MONEY);
    }

    @Test
    public void testIsCharNameExistsShouldCallCacheAndReturnResponse() {
        //GIVEN
        when(characterNameExistsCache.get(CHARACTER_NAME)).thenReturn(Optional.of(true));
        //WHEN
        boolean result = underTest.isCharNameExists(new OneStringParamRequest(CHARACTER_NAME));
        //THEN
        verify(characterNameExistsCache).get(CHARACTER_NAME);
        assertThat(result).isTrue();
    }

    @Test
    public void testRenameCharacterShouldCallFacadeAndInvalidateCache() {
        //GIVEN
        RenameCharacterRequest request = new RenameCharacterRequest(NEW_CHARACTER_NAME, CHARACTER_ID);

        SkyXpCharacter character = SkyXpCharacter.builder().build();
        when(characterRenameService.renameCharacter(request, USER_ID)).thenReturn(character);

        CharacterView characterView = createCharacterView(character);
        when(characterViewConverter.convertDomain(character)).thenReturn(characterView);
        //WHEN
        CharacterView result = underTest.renameCharacter(request, USER_ID);
        //THEN
        verify(characterNameExistsCache).invalidate(NEW_CHARACTER_NAME);
        assertThat(result).isEqualTo(characterView);
    }

    @Test
    public void testSelectCharacterShouldCallFacadeAndSetCookie() {
        //WHEN
        underTest.selectCharacter(CHARACTER_ID, USER_ID, httpServletResponse);
        //THEN
        verify(characterSelectService).selectCharacter(CHARACTER_ID, USER_ID, httpServletResponse);
    }

    @Test
    public void getActiveCharactersByName() {
        //GIVEN
        CharacterView characterView = createCharacterView(SkyXpCharacter.builder().build());
        given(characterViewQueryService.getActiveCharactersByName(CHARACTER_ID, CHARACTER_NAME)).willReturn(Arrays.asList(characterView));
        //WHEN
        List<CharacterView> result = underTest.getActiveCharactersByName(CHARACTER_ID, new OneStringParamRequest(CHARACTER_NAME));
        //THEN
        assertThat(result).containsOnly(characterView);
    }

    @Test
    public void getCharacterId() {
        //WHEN
        String result = underTest.getCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(CHARACTER_ID);
    }

    private CharacterView createCharacterView(SkyXpCharacter character) {
        return new CharacterView(character.getCharacterId(), character.getCharacterName());
    }

}
