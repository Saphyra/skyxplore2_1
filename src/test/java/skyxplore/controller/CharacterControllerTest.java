package skyxplore.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.character.CreateCharacterRequest;
import skyxplore.controller.request.character.RenameCharacterRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.service.CharacterFacade;
import org.github.saphyra.skyxplore.common.CookieUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.CHARACTER_MONEY;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.CHARACTER_NEW_NAME;
import static skyxplore.testutil.TestUtils.EQUIP_ITEM_ID;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createCharacterView;
import static skyxplore.testutil.TestUtils.createRenameCharacterRequest;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    @Mock
    private CharacterFacade characterFacade;

    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private CharacterNameCache characterNameCache;

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletResponse httpServletResponse;

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

        SkyXpCharacter character = createCharacter();
        when(characterFacade.createCharacter(request, USER_ID)).thenReturn(character);

        CharacterView characterView = createCharacterView(character);
        when(characterViewConverter.convertDomain(character)).thenReturn(characterView);
        //WHEN
        CharacterView result = underTest.createCharacter(request, USER_ID);
        //THEN
        verify(characterFacade).createCharacter(request, USER_ID);
        assertEquals(characterView, result);
    }

    @Test
    public void testDeleteCharacterShouldCallFacade() {
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID_1, USER_ID);
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
        List<String> equipments = Arrays.asList(EQUIP_ITEM_ID);
        when(characterFacade.getEquipmentsOfCharacter(CHARACTER_ID_1)).thenReturn(equipments);
        //WHEN
        List<String> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID_1);
        //THEN
        verify(characterFacade).getEquipmentsOfCharacter(CHARACTER_ID_1);
        assertEquals(equipments, result);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallFacadeAndReturnResponse() {
        //GIVEN
        when(characterFacade.getMoneyOfCharacter(CHARACTER_ID_1)).thenReturn(CHARACTER_MONEY);
        //WHEN
        Integer money = underTest.getMoney(CHARACTER_ID_1);
        //THEN
        verify(characterFacade).getMoneyOfCharacter(CHARACTER_ID_1);
        assertEquals(CHARACTER_MONEY, money);
    }

    @Test
    public void testIsCharNameExistsShouldCallCacheAndReturnResponse() {
        //GIVEN
        when(characterNameCache.get(CHARACTER_NAME)).thenReturn(Optional.of(true));
        //WHEN
        boolean result = underTest.isCharNameExists(new OneStringParamRequest(CHARACTER_NAME));
        //THEN
        verify(characterNameCache).get(CHARACTER_NAME);
        assertTrue(result);
    }

    @Test
    public void testRenameCharacterShouldCallFacadeAndInvalidateCache() {
        //GIVEN
        RenameCharacterRequest request = createRenameCharacterRequest();

        SkyXpCharacter character = createCharacter();
        when(characterFacade.renameCharacter(request, USER_ID)).thenReturn(character);

        CharacterView characterView = createCharacterView(character);
        when(characterViewConverter.convertDomain(character)).thenReturn(characterView);
        //WHEN
        CharacterView result = underTest.renameCharacter(request, USER_ID);
        //THEN
        verify(characterFacade).renameCharacter(request, USER_ID);
        verify(characterNameCache).invalidate(CHARACTER_NEW_NAME);
        assertEquals(characterView, result);
    }

    @Test
    public void testSelectCharacterShouldCallFacadeAndSetCookie() {
        //WHEN
        underTest.selectCharacter(CHARACTER_ID_1, USER_ID, httpServletResponse);
        //THEN
        verify(characterFacade).selectCharacter(CHARACTER_ID_1, USER_ID);
        verify(cookieUtil).setCookie(httpServletResponse, COOKIE_CHARACTER_ID, CHARACTER_ID_1);
    }
}
