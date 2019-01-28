package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.accesstoken.CharacterSelectService;
import skyxplore.service.character.BuyItemService;
import skyxplore.service.character.CharacterCreatorService;
import skyxplore.service.character.CharacterDeleteService;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.character.CharacterRenameService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_MONEY;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createCreateCharacterRequest;
import static skyxplore.testutil.TestUtils.createRenameCharacterRequest;

@RunWith(MockitoJUnitRunner.class)
public class CharacterFacadeTest {
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
        underTest.buyItems(map, CHARACTER_ID_1);
        //THEN
        verify(buyItemService).buyItems(map, CHARACTER_ID_1);
    }

    @Test
    public void testCreateCharacterShouldCallService() {
        //GIVEN
        CreateCharacterRequest request = createCreateCharacterRequest();

        SkyXpCharacter character = createCharacter();
        when(characterCreatorService.createCharacter(request, USER_ID)).thenReturn(character);
        //WHEN
        SkyXpCharacter result = underTest.createCharacter(request, USER_ID);
        //THEN
        verify(characterCreatorService).createCharacter(request, USER_ID);
        assertEquals(character, result);
    }

    @Test
    public void testDeleteCharacterShouldCallService() {
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID_1, USER_ID);
        //THEN
        verify(characterDeleteService).deleteCharacter(CHARACTER_ID_1, USER_ID);
    }

    @Test
    public void testGetCharactersByUserIdShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characters = Arrays.asList(character);

        when(characterQueryService.getCharactersByUserId(USER_ID)).thenReturn(characters);
        //WHEN
        List<SkyXpCharacter> result = underTest.getCharactersByUserId(USER_ID);
        //THEN
        verify(characterQueryService).getCharactersByUserId(USER_ID);
        assertEquals(characters, result);
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldCallServiceAndReturn() {
        //GIVEN
        View<EquipmentViewList> view = new View<>(
            new EquipmentViewList(Arrays.asList(DATA_ELEMENT)),
            new HashMap<>()
        );
        when(characterQueryService.getEquipmentsOfCharacter(CHARACTER_ID_1)).thenReturn(view);
        //WHEN
        View<EquipmentViewList> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).getEquipmentsOfCharacter(CHARACTER_ID_1);
        assertEquals(view, result);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallServiceAndReturn(){
        //GIVEN
        when(characterQueryService.getMoneyOfCharacter(CHARACTER_ID_1)).thenReturn(CHARACTER_MONEY);
        //WHEN
        Integer result = underTest.getMoneyOfCharacter(CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).getMoneyOfCharacter(CHARACTER_ID_1);
        assertEquals(CHARACTER_MONEY, result);
    }

    @Test
    public void testRenameCharacterShouldCallService(){
        //GIVEN
        RenameCharacterRequest request = createRenameCharacterRequest();
        //WHEN
        underTest.renameCharacter(request, USER_ID);
        //THEN
        verify(characterRenameService).renameCharacter(request, USER_ID);
    }

    @Test
    public void testSelectCharacter(){
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID);
        //THEN
        verify(characterSelectService).selectCharacter(CHARACTER_ID_1, USER_ID);
    }
}
