package skyxplore.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.TestUtils.CHARACTER_ID;
import static skyxplore.TestUtils.CHARACTER_NAME;
import static skyxplore.TestUtils.MONEY;
import static skyxplore.TestUtils.USER_ID;
import static skyxplore.TestUtils.createCharacter;
import static skyxplore.TestUtils.createCharacterView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.cache.Cache;
import skyxplore.controller.request.CharacterDeleteRequest;
import skyxplore.controller.request.CreateCharacterRequest;
import skyxplore.controller.request.RenameCharacterRequest;
import skyxplore.controller.view.View;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.equipment.EquipmentViewList;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.CharacterFacade;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    @Mock
    private CharacterFacade characterFacade;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private Cache<String, Boolean> characterNameCache;

    @InjectMocks
    private CharacterController underTest;

    @Test
    public void testBuyEquipmentsShouldCallFacade() {
        //GIVEN
        HashMap<String, Integer> inputMap = new HashMap<>();
        //WHEN
        underTest.buyEquipments(inputMap, CHARACTER_ID, USER_ID);
        //THEN
        verify(characterFacade).buyItems(inputMap, CHARACTER_ID, USER_ID);
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
        CharacterDeleteRequest request = new CharacterDeleteRequest(CHARACTER_ID);
        //WHEN
        underTest.deleteCharacter(request, USER_ID);
        //THEN
        verify(characterFacade).deleteCharacter(request, USER_ID);
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
        when(characterFacade.getEquipmentsOfCharacter(USER_ID, CHARACTER_ID)).thenReturn(view);
        //WHEN
        View<EquipmentViewList> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterFacade).getEquipmentsOfCharacter(USER_ID, CHARACTER_ID);
        assertEquals(view, result);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallFacadeAndReturnResponse(){
        //GIVEN
        when(characterFacade.getMoneyOfCharacter(USER_ID, CHARACTER_ID)).thenReturn(MONEY);
        //WHEN
        Integer money = underTest.getMoney(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterFacade).getMoneyOfCharacter(USER_ID, CHARACTER_ID);
        assertEquals(MONEY, money);
    }

    @Test
    public void testIsCharNameExistsShouldCallCacheAndReturnResponse() throws ExecutionException {
        //GIVEN
        when(characterNameCache.get(CHARACTER_NAME)).thenReturn(true);
        //WHEN
        boolean result = underTest.isCharNameExists(CHARACTER_NAME);
        //THEN
        verify(characterNameCache).get(CHARACTER_NAME);
        assertTrue(result);
    }

    @Test
    public void testRenameCharacterShouldCallFacadeAndInvalidateCache(){
        //GIVEN
        RenameCharacterRequest request = new RenameCharacterRequest(CHARACTER_ID, CHARACTER_NAME);
        //WHEN
        underTest.renameCharacter(request, USER_ID);
        //THEN
        verify(characterFacade).renameCharacter(request, USER_ID);
        verify(characterNameCache).invalidate(CHARACTER_NAME);
    }
}
