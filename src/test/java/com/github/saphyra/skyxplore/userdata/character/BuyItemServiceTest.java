package com.github.saphyra.skyxplore.userdata.character;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.character.repository.CharacterDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.entity.ShopData;
import com.github.saphyra.skyxplore.data.GameDataFacade;

@RunWith(MockitoJUnitRunner.class)
public class BuyItemServiceTest {
    private static final String ITEM = "item";
    private static final String CONNECTOR = "connector";
    private static final String CHARACTER_ID = "character_id";
    private static final Integer BUY_PRICE = 3;
    @Mock
    private CharacterDao characterDao;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private GameDataFacade gameDataFacade;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private ShopData buyable;

    @InjectMocks
    private BuyItemService underTest;

    @Test
    public void testBuyItemsShouldCountCostAndCallCharacter() {
        //GIVEN
        Map<String, Integer> map = new HashMap<>();
        map.put(ITEM, 2);
        map.put(CONNECTOR, 1);

        when(characterQueryService.findByCharacterId(CHARACTER_ID)).thenReturn(character);
        when(gameDataFacade.findBuyable(anyString())).thenReturn(buyable);
        when(buyable.getBuyPrice()).thenReturn(BUY_PRICE);
        //WHEN
        underTest.buyItems(map, CHARACTER_ID);
        //THEN
        verify(characterQueryService).findByCharacterId(CHARACTER_ID);
        verify(gameDataFacade, times(2)).findBuyable(anyString());
        verify(character).buyEquipments(map, BUY_PRICE * 3);
        verify(characterDao).save(character);
    }
}