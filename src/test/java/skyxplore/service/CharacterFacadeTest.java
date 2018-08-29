package skyxplore.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_MONEY;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createCharacterDeleteRequest;
import static skyxplore.testutil.TestUtils.createCreateCharacterRequest;
import static skyxplore.testutil.TestUtils.createRenameCharacterRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.character.CharacterDeleteRequest;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.controller.request.user.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.BuyItemService;
import skyxplore.service.character.CharacterCreatorService;
import skyxplore.service.character.CharacterDeleteService;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.character.CharacterRenameService;

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

    @InjectMocks
    private CharacterFacade underTest;

    @Test
    public void testBuyItemsShouldCallService() {
        //GIVEN
        Map<String, Integer> map = new HashMap<>();
        //WHEN
        underTest.buyItems(map, CHARACTER_ID, USER_ID);
        //THEN
        verify(buyItemService).buyItems(map, CHARACTER_ID, USER_ID);
    }

    @Test
    public void testCreateCharacterShouldCallService() {
        //GIVEN
        CreateCharacterRequest request = createCreateCharacterRequest();
        //WHEN
        underTest.createCharacter(request, USER_ID);
        //THEN
        verify(characterCreatorService).createCharacter(request, USER_ID);
    }

    @Test
    public void testDeleteCharacterShouldCallService() {
        //GIVEN
        CharacterDeleteRequest request = createCharacterDeleteRequest();
        //WHEN
        underTest.deleteCharacter(request, USER_ID);
        //THEN
        verify(characterDeleteService).deleteCharacter(request, USER_ID);
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
        when(characterQueryService.getEquipmentsOfCharacter(USER_ID, CHARACTER_ID)).thenReturn(view);
        //WHEN
        View<EquipmentViewList> result = underTest.getEquipmentsOfCharacter(USER_ID, CHARACTER_ID);
        //THEN
        verify(characterQueryService).getEquipmentsOfCharacter(USER_ID, CHARACTER_ID);
        assertEquals(view, result);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallServiceAndReturn(){
        //GIVEN
        when(characterQueryService.getMoneyOfCharacter(USER_ID, CHARACTER_ID)).thenReturn(CHARACTER_MONEY);
        //WHEN
        Integer result = underTest.getMoneyOfCharacter(USER_ID, CHARACTER_ID);
        //THEN
        verify(characterQueryService).getMoneyOfCharacter(USER_ID, CHARACTER_ID);
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
}
