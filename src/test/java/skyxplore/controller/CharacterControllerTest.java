package skyxplore.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.cache.CharacterNameCache;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.CharacterFacade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    @Mock
    private CharacterFacade characterFacade;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private CharacterNameCache characterNameCache;

    @InjectMocks
    private CharacterController underTest;

    @Test
    public void testBuyEquipmentsShouldCallFacade() {
        //GIVEN
        HashMap<String, Integer> inputMap = new HashMap<>();
        //WHEN
        underTest.buyEquipments(inputMap, CHARACTER_ID_1);
        //THEN
        verify(characterFacade).buyItems(inputMap, CHARACTER_ID_1);
    }

    @Test
    public void testCreateCharacterShouldCallFacadeAndInvalidateCache() {
        //GIVEN
        CreateCharacterRequest request = new CreateCharacterRequest(CHARACTER_NAME);
        //WHEN
        underTest.createCharacter(request, USER_ID);
        //THEN
        verify(characterFacade).createCharacter(request, USER_ID);
        verify(characterNameCache).invalidate(CHARACTER_NAME);
    }

    @Test
    public void testDeleteCharacterShouldCallFacade() {
        //GIVEN
        OneStringParamRequest request = new OneStringParamRequest(CHARACTER_ID_1);
        //WHEN
        underTest.deleteCharacter(request, USER_ID);
        //THEN
        verify(characterFacade).deleteCharacter(CHARACTER_ID_1, USER_ID);
    }

    @Test
    public void testGetCharactersShouldCallFacadeAndConverterAndReturnResponse() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);

        CharacterView view = createCharacterView(character);
        List<CharacterView> viewList = Arrays.asList(view);

        when(characterFacade.getCharactersByUserId(USER_ID)).thenReturn(characterList);
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getCharacters(USER_ID);
        //THEN
        verify(characterFacade).getCharactersByUserId(USER_ID);
        verify(characterViewConverter).convertDomain(Mockito.anyList());
        assertEquals(1, result.size());
        assertEquals(view, result.get(0));
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldCallFacadeAndReturnResponse() {
        //GIVEN
        View<EquipmentViewList> view = new View<>(
            new EquipmentViewList(),
            new HashMap<>()
        );
        when(characterFacade.getEquipmentsOfCharacter(CHARACTER_ID_1)).thenReturn(view);
        //WHEN
        View<EquipmentViewList> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID_1);
        //THEN
        verify(characterFacade).getEquipmentsOfCharacter(CHARACTER_ID_1);
        assertEquals(view, result);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallFacadeAndReturnResponse(){
        //GIVEN
        when(characterFacade.getMoneyOfCharacter(CHARACTER_ID_1)).thenReturn(CHARACTER_MONEY);
        //WHEN
        Integer money = underTest.getMoney(CHARACTER_ID_1);
        //THEN
        verify(characterFacade).getMoneyOfCharacter(CHARACTER_ID_1);
        assertEquals(CHARACTER_MONEY, money);
    }

    @Test
    public void testIsCharNameExistsShouldCallCacheAndReturnResponse() throws ExecutionException {
        //GIVEN
        when(characterNameCache.get(CHARACTER_NAME)).thenReturn(true);
        //WHEN
        boolean result = underTest.isCharNameExists(new OneStringParamRequest(CHARACTER_NAME));
        //THEN
        verify(characterNameCache).get(CHARACTER_NAME);
        assertTrue(result);
    }

    @Test
    public void testRenameCharacterShouldCallFacadeAndInvalidateCache(){
        //GIVEN
        RenameCharacterRequest request = createRenameCharacterRequest();
        //WHEN
        underTest.renameCharacter(request, CHARACTER_ID_1);
        //THEN
        verify(characterFacade).renameCharacter(request, CHARACTER_ID_1);
        verify(characterNameCache).invalidate(CHARACTER_NEW_NAME);
    }
}
