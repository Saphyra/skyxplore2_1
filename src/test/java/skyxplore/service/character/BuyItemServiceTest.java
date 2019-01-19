package skyxplore.service.character;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.CharacterDao;
import skyxplore.dataaccess.gamedata.entity.abstractentity.ShopData;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.GameDataFacade;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class BuyItemServiceTest {
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
    public void testBuyItemsShouldCountCostAndCallCharacter(){
        //GIVEN
        Map<String, Integer> map = new HashMap<>();
        map.put(DATA_ELEMENT, 2);
        map.put(DATA_CONNECTOR, 1);

        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);
        when(gameDataFacade.findBuyable(anyString())).thenReturn(buyable);
        when(buyable.getBuyPrice()).thenReturn(DATA_BUYPRICE);
        //WHEN
        underTest.buyItems(map, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).findByCharacterId(CHARACTER_ID_1);
        verify(gameDataFacade, times(2)).findBuyable(anyString());
        verify(character).buyEquipments(map, DATA_BUYPRICE * 3);
        verify(characterDao).save(character);
    }
}