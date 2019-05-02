package org.github.saphyra.skyxplore.character;


import static org.assertj.core.api.Assertions.assertThat;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.github.saphyra.skyxplore.character.cache.CharacterNameCache;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.common.CookieUtil;
import org.github.saphyra.skyxplore.common.OneStringParamRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.github.saphyra.skyxplore.character.domain.request.CreateCharacterRequest;
import org.github.saphyra.skyxplore.character.domain.request.RenameCharacterRequest;
import org.github.saphyra.skyxplore.common.domain.character.CharacterView;
import org.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String USER_ID = "user_id";
    private static final String EQUIP_ITEM_ID = "equip_item_id";
    private static final int MONEY = 5;
    private static final String NEW_CHARACTER_NAME = "new_character_name";

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
        underTest.buyEquipments(inputMap, CHARACTER_ID);
        //THEN
        verify(characterFacade).buyItems(inputMap, CHARACTER_ID);
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
        assertThat(result).isEqualTo(characterView);
    }

    @Test
    public void testDeleteCharacterShouldCallFacade() {
        //WHEN
        underTest.deleteCharacter(CHARACTER_ID, USER_ID);
        //THEN
        verify(characterFacade).deleteCharacter(CHARACTER_ID, USER_ID);
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
        assertThat(result).containsOnly(view);
    }

    @Test
    public void testGetEquipmentsOfCharacterShouldCallFacadeAndReturnResponse() {
        //GIVEN
        List<String> equipments = Arrays.asList(EQUIP_ITEM_ID);
        when(characterFacade.getEquipmentsOfCharacter(CHARACTER_ID)).thenReturn(equipments);
        //WHEN
        List<String> result = underTest.getEquipmentsOfCharacter(CHARACTER_ID);
        //THEN
        verify(characterFacade).getEquipmentsOfCharacter(CHARACTER_ID);
        assertThat(result).isEqualTo(equipments);
    }

    @Test
    public void testGetMoneyOfCharacterShouldCallFacadeAndReturnResponse() {
        //GIVEN
        when(characterFacade.getMoneyOfCharacter(CHARACTER_ID)).thenReturn(MONEY);
        //WHEN
        Integer result = underTest.getMoney(CHARACTER_ID);
        //THEN
        verify(characterFacade).getMoneyOfCharacter(CHARACTER_ID);
        assertThat(result).isEqualTo(MONEY);
    }

    @Test
    public void testIsCharNameExistsShouldCallCacheAndReturnResponse() {
        //GIVEN
        when(characterNameCache.get(CHARACTER_NAME)).thenReturn(Optional.of(true));
        //WHEN
        boolean result = underTest.isCharNameExists(new OneStringParamRequest(CHARACTER_NAME));
        //THEN
        verify(characterNameCache).get(CHARACTER_NAME);
        assertThat(result).isTrue();
    }

    @Test
    public void testRenameCharacterShouldCallFacadeAndInvalidateCache() {
        //GIVEN
        RenameCharacterRequest request = new RenameCharacterRequest(NEW_CHARACTER_NAME, CHARACTER_ID);

        SkyXpCharacter character = createCharacter();
        when(characterFacade.renameCharacter(request, USER_ID)).thenReturn(character);

        CharacterView characterView = createCharacterView(character);
        when(characterViewConverter.convertDomain(character)).thenReturn(characterView);
        //WHEN
        CharacterView result = underTest.renameCharacter(request, USER_ID);
        //THEN
        verify(characterFacade).renameCharacter(request, USER_ID);
        verify(characterNameCache).invalidate(NEW_CHARACTER_NAME);
        assertThat(result).isEqualTo(characterView);
    }

    @Test
    public void testSelectCharacterShouldCallFacadeAndSetCookie() {
        //WHEN
        underTest.selectCharacter(CHARACTER_ID, USER_ID, httpServletResponse);
        //THEN
        verify(characterFacade).selectCharacter(CHARACTER_ID, USER_ID);
        verify(cookieUtil).setCookie(httpServletResponse, COOKIE_CHARACTER_ID, CHARACTER_ID);
    }

    private CharacterView createCharacterView(SkyXpCharacter character) {
        return new CharacterView(character.getCharacterId(), character.getCharacterName());
    }

    private SkyXpCharacter createCharacter() {
        return SkyXpCharacter.builder()

            .build();
    }
}
